package com.example.screendef.fognl.android.screendef;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.screendef.fognl.android.screendef.attributes.LayoutAttributes;
import com.example.screendef.fognl.android.screendef.attributes.RadioGroupAttributes;
import com.example.screendef.fognl.android.screendef.attributes.SpinnerAttributes;
import com.example.screendef.fognl.android.screendef.attributes.TextViewAttributes;
import com.example.screendef.fognl.android.screendef.attributes.ViewAttributes;
import com.example.screendef.fognl.android.screendef.viewfactory.BaseViewFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewBuilder {
    static final String TAG = ViewBuilder.class.getSimpleName();

    private final Context mContext;

    private static final List<Class> sAttributeProcessors = new ArrayList<>();

    static {
        sAttributeProcessors.add(TextViewAttributes.class);
        sAttributeProcessors.add(RadioGroupAttributes.class);
        sAttributeProcessors.add(LayoutAttributes.class);
        sAttributeProcessors.add(SpinnerAttributes.class);
    }

    private static final Set<ViewFactory> sViewFactories = new HashSet<>();

    static {
        sViewFactories.add(new BaseViewFactory());
    }

    public static void addAttributeProcessor(Class<? extends ViewAttributes> type) {
        sAttributeProcessors.add(type);
    }

    public static void addViewFactory(ViewFactory factory) {
        sViewFactories.add(factory);
    }

    public ViewBuilder(Context context) {
        mContext = context;
    }

    /** Given the specified ViewDef, hydrate it into a view hierarchy. */
    public View buildViewFrom(Context context, ViewDef viewDef) throws ViewBuilderException {
        // Find a factory for this view type
        final View view = instantiateViewFrom(context, viewDef);

        if(view != null) {
            applyAttributes(context, view, viewDef);

            if(viewDef.hasChildren()) {
                for(ViewDef childDef: viewDef.getChildren()) {
                    final View child = buildViewFrom(context, childDef);

                    if(child != null) {
                        applyAttributes(context, child, childDef);

                        if(view instanceof ViewGroup) {
                            ((ViewGroup)view).addView(child, childDef.getLayoutParams(child, view));
                        }
                    }
                }
            }
        }

        return view;
    }

    static View instantiateViewFrom(Context context, ViewDef def) {
        for(ViewFactory factory: sViewFactories) {
            final View view = factory.instantiateViewFrom(context, def.type, def.attrs);
            if(view != null) {
                return view;
            }
        }

        return null;
    }

    public static void applyAttributes(Context context, View view, Values attrs) {
        for(ViewAttributes viewAttributes: findApplicableAttributes(view)) {
            viewAttributes.applyTo(context, view, attrs);
        }
    }

    public static void applyAttributes(Context context, View view, ViewDef viewDef) {
        for(ViewAttributes viewAttributes: findApplicableAttributes(view)) {
            viewAttributes.applyTo(context, view, viewDef.attrs);
        }
    }

    public static List<ViewAttributes> findApplicableAttributes(View view) {
        final List<ViewAttributes> list = new ArrayList<>();

        final Class type = view.getClass();

        // Applies to everything
        list.add(new ViewAttributes());

        for(Class attrClass: sAttributeProcessors) {
            try {
                final Method supports = attrClass.getDeclaredMethod("appliesTo", new Class[] { View.class });
                boolean ok = (Boolean)supports.invoke(null, view);
                if(ok) {
                    list.add((ViewAttributes)attrClass.newInstance());
                }
            } catch(Throwable ex) {
                Log.e(TAG, ex.getMessage(), ex);
            }
        }

        return list;
    }
}

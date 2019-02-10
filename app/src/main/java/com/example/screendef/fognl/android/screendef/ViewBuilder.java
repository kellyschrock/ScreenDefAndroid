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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ViewBuilder {
    static final String TAG = ViewBuilder.class.getSimpleName();

    private final Context mContext;
    private final ViewFactory mViewFactory;

    private static final List<Class> sAttributeProcessors = new ArrayList<>();

    static {
        sAttributeProcessors.add(TextViewAttributes.class);
        sAttributeProcessors.add(RadioGroupAttributes.class);
        sAttributeProcessors.add(LayoutAttributes.class);
        sAttributeProcessors.add(SpinnerAttributes.class);
    }

    public ViewBuilder(Context context, ViewFactory factory) {
        mContext = context;
        mViewFactory = factory;
    }

    /** Given the specified ViewDef, hydrate it into a view hierarchy. */
    public View buildViewFrom(Context context, ViewDef viewDef) throws ViewBuilderException {
        // Find a factory for this view type
        final View view = mViewFactory.instantiateViewFrom(viewDef.getType(), viewDef.attrs);

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

    void applyAttributes(Context context, View view, ViewDef viewDef) {
        for(ViewAttributes viewAttributes: findApplicableAttributes(view)) {
            viewAttributes.applyTo(context, view, viewDef.attrs);
        }
    }

    public List<ViewAttributes> findApplicableAttributes(View view) {
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

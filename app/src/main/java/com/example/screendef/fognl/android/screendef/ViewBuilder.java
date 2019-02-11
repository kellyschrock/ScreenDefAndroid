package com.example.screendef.fognl.android.screendef;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.screendef.fognl.android.screendef.attributes.EditTextAttributes;
import com.example.screendef.fognl.android.screendef.attributes.ImageViewAttributes;
import com.example.screendef.fognl.android.screendef.attributes.LayoutAttributes;
import com.example.screendef.fognl.android.screendef.attributes.RadioGroupAttributes;
import com.example.screendef.fognl.android.screendef.attributes.SpinnerAttributes;
import com.example.screendef.fognl.android.screendef.attributes.TextViewAttributes;
import com.example.screendef.fognl.android.screendef.attributes.ViewAttributes;
import com.example.screendef.fognl.android.screendef.viewfactory.BaseViewFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewBuilder {
    static final String TAG = ViewBuilder.class.getSimpleName();

    private final Context mContext;

    private static final List<ViewAttributes> sAttributeProcessors = new ArrayList<>();

    static {
        sAttributeProcessors.add(new TextViewAttributes());
        sAttributeProcessors.add(new EditTextAttributes());
        sAttributeProcessors.add(new RadioGroupAttributes());
        sAttributeProcessors.add(new LayoutAttributes());
        sAttributeProcessors.add(new ImageViewAttributes());
        sAttributeProcessors.add(new SpinnerAttributes());
    }

    private static final Set<ViewFactory> sViewFactories = new HashSet<>();

    static {
        sViewFactories.add(new BaseViewFactory());
    }

    public static void addAttributeProcessor(ViewAttributes type) {
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

        for(ViewAttributes a: sAttributeProcessors) {
            if(a.appliesTo(view)) {
                list.add(a);
            }
        }

        return list;
    }

    public static void setImageViewIcon(ImageView img, String url) {
        Glide.with(img.getContext())
                .load(url).into(img);
    }

    public interface BitmapCallback {
        void onBitmap(Bitmap bmp);
    }

    public static void getIcon(Context context, String url, final BitmapCallback callback) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                callback.onBitmap(resource);
            }
        });
    }

    public static void getIcon(Context context, String url, SimpleTarget<Bitmap> callback) {
        Glide.with(context).asBitmap().load(url).into(callback);
    }
}

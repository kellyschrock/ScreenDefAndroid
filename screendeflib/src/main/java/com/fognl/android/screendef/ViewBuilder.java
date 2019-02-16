package com.fognl.android.screendef;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fognl.android.screendef.attributes.EditTextAttributes;
import com.fognl.android.screendef.attributes.ImageViewAttributes;
import com.fognl.android.screendef.attributes.LayoutAttributes;
import com.fognl.android.screendef.attributes.ProgressBarAttributes;
import com.fognl.android.screendef.attributes.RadioGroupAttributes;
import com.fognl.android.screendef.attributes.SpinnerAttributes;
import com.fognl.android.screendef.attributes.TextViewAttributes;
import com.fognl.android.screendef.attributes.ViewAttributes;
import com.fognl.android.screendef.events.CheckBoxEventAttacher;
import com.fognl.android.screendef.events.EditTextEventAttacher;
import com.fognl.android.screendef.events.EventAttacher;
import com.fognl.android.screendef.events.RadioGroupEventAttacher;
import com.fognl.android.screendef.events.SeekBarEventAttacher;
import com.fognl.android.screendef.events.SpinnerEventAttacher;
import com.fognl.android.screendef.events.ViewEventAttacher;
import com.fognl.android.screendef.events.ViewEventListener;
import com.fognl.android.screendef.module.ViewBuilderModule;
import com.fognl.android.screendef.values.CheckBoxGetter;
import com.fognl.android.screendef.values.EditTextGetter;
import com.fognl.android.screendef.values.ProgressBarGetter;
import com.fognl.android.screendef.values.RadioGroupGetter;
import com.fognl.android.screendef.values.SpinnerGetter;
import com.fognl.android.screendef.values.ValueGetter;
import com.fognl.android.screendef.viewfactory.BaseViewFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewBuilder {
    static final String TAG = ViewBuilder.class.getSimpleName();
    private static ViewBuilder sInstance;

    public interface BitmapCallback {
        void onBitmap(Bitmap bmp);
    }

    public static class BuildResult {
        final View view;
        final Map<String, View> viewIds;

        BuildResult(View view, Map<String, View> viewIds) {
            this.view = view;
            this.viewIds = viewIds;
        }

        public View getView() {
            return view;
        }

        public Map<String, View> getViewIds() {
            return viewIds;
        }
        public boolean hasViewIds() { return (viewIds != null); }

        @Override
        public String toString() {
            return "BuildResult{" +
                    "view=" + view +
                    ", viewIds=" + viewIds +
                    '}';
        }
    }

    public static ViewBuilder init(Context context) {
        sInstance = new ViewBuilder(context);
        return sInstance;
    }

    public static ViewBuilder get() { return sInstance; }

    private final List<ViewAttributes> mAttributeProcessors = new ArrayList<>();
    private final Set<ViewFactory> mViewFactories = new HashSet<>();
    private final Set<ViewEventListener> mViewEventListeners = new HashSet<>();
    private final Set<EventAttacher> mEventAttachers = new HashSet<>();
    private final Set<ValueGetter> mValueGetters = new HashSet<>();

    private final Context mContext;

    private ViewBuilder(Context context) {
        mContext = context;
        initAttributeProcessors();
        initViewFactories();
        initEventAttachers();
        initValueGetters();
    }

    public ViewBuilder addModule(ViewBuilderModule module) {
        addAttributeProcessor(module.getAttributeProcessor());
        addEventAttacher(module.getEventAttacher());
        addValueGetter(module.getValueGetter());
        addViewFactory(module.getViewFactory());
        return this;
    }

    public ViewBuilder addAttributeProcessor(ViewAttributes type) {
        if(type != null) mAttributeProcessors.add(type);
        return this;
    }

    public ViewBuilder addViewFactory(ViewFactory factory) {
        if(factory != null) mViewFactories.add(factory);
        return this;
    }

    public ViewBuilder addViewEventListener(ViewEventListener listener) {
        if(listener != null) mViewEventListeners.add(listener);
        return this;
    }

    public ViewBuilder removeViewEventListener(ViewEventListener listener) {
        mViewEventListeners.remove(listener);
        return this;
    }

    public ViewBuilder addEventAttacher(EventAttacher attacher) {
        if(attacher != null) mEventAttachers.add(attacher);
        return this;
    }

    public ViewBuilder removeEventAttacher(EventAttacher attacher) {
        mEventAttachers.remove(attacher);
        return this;
    }

    public ViewBuilder addValueGetter(ValueGetter getter) {
        if(getter != null) mValueGetters.add(getter);
        return this;
    }

    public ViewBuilder removeValueGetter(ValueGetter getter) {
        mValueGetters.remove(getter);
        return this;
    }

    public BuildResult buildViewFrom(Context context, String screenId, ViewDef viewDef) throws ViewBuilderException {
        final Map<String, View> viewIds = new HashMap<>();
        final View view = doBuildViewFrom(context, screenId, viewDef, viewIds);

        for(String name: viewIds.keySet()) {
            final View v = viewIds.get(name);
            if(v != null) {
                attachEventsTo(v);
            }
        }

        return new BuildResult(view, viewIds);
    }

    public Values makeMessageBody(BuildResult buildResult) {
        final Values result = new Values();

        final Map<String, View> map = buildResult.getViewIds();
        for(String id: map.keySet()) {
            final View view = map.get(id);
            if(view == null) continue;

            final String name = (String)view.getTag(R.string.tag_view_name);
            if(name == null) continue;

            for(ValueGetter getter: mValueGetters) {
                if(getter.appliesTo(view)) {
                    getter.getValuesFrom(view, name, result);
                }
            }
        }

        return result;
    }

    void attachEventsTo(View view) {
        for(EventAttacher attacher: mEventAttachers) {
            if(attacher.appliesTo(view)) {
                attacher.attachEventsTo(view, mViewEventListeners);
            }
        }
    }

    /** Given the specified ViewDef, hydrate it into a view hierarchy. */
    View doBuildViewFrom(Context context, String screenId, ViewDef viewDef, Map<String, View> viewIds) throws ViewBuilderException {
        // Find a factory for this view type
        final View view = instantiateViewFrom(context, viewDef);

        if(view != null) {
            view.setTag(R.string.tag_view_screen, screenId);
            applyAttributesWithIds(context, view, viewDef, viewIds);

            if(viewDef.hasChildren()) {
                for(ViewDef childDef: viewDef.getChildren()) {
                    final View child = doBuildViewFrom(context, screenId, childDef, viewIds);

                    if(child != null) {
                        child.setTag(R.string.tag_view_screen, screenId);

                        applyAttributesWithIds(context, child, childDef, viewIds);

                        if(view instanceof ViewGroup) {
                            ((ViewGroup)view).addView(child, childDef.getLayoutParams(child, view));
                        }
                    }
                }
            }
        }

        return view;
    }

    View instantiateViewFrom(Context context, ViewDef def) {
        for(ViewFactory factory: mViewFactories) {
            final View view = factory.instantiateViewFrom(context, def.type, def.attrs);
            if(view != null) {
                return view;
            }
        }

        return null;
    }

    public void applyAttributes(Context context, View view, Values attrs) {
        // NOTE: The viewIds arg here is wasted. If there's a need for it to be used from a caller
        // elsewhere, expose it here as a parameter.
        for(ViewAttributes viewAttributes: findApplicableAttributesFor(view, new HashMap<String, View>())) {
            viewAttributes.applyTo(context, view, attrs);
        }
    }

    void applyAttributesWithIds(Context context, View view, ViewDef viewDef, Map<String, View> viewIds) {
        for(ViewAttributes viewAttributes: findApplicableAttributesFor(view, viewIds)) {
//            Log.v(TAG, String.format("%s applies to %s", viewAttributes.getClass().getSimpleName(), view.getClass().getSimpleName()));
            viewAttributes.applyTo(context, view, viewDef.attrs);
        }
    }

    public List<ViewAttributes> findApplicableAttributesFor(View view, Map<String, View> viewIds) {
        final List<ViewAttributes> list = new ArrayList<>();

        final Class type = view.getClass();

        // Applies to everything
        list.add(new ViewAttributes(viewIds));

        for(ViewAttributes a: mAttributeProcessors) {
            if(a.appliesTo(view)) {
                list.add(a);
            }
        }

        return list;
    }

    public void setImageViewIcon(ImageView img, String url) {
        Glide.with(img.getContext())
                .load(url).into(img);
    }

    public void getIcon(Context context, String url, final BitmapCallback callback) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                callback.onBitmap(resource);
            }
        });
    }

    public void getIcon(Context context, String url, SimpleTarget<Bitmap> callback) {
        Glide.with(context).asBitmap().load(url).into(callback);
    }

    void initAttributeProcessors() {
        mAttributeProcessors.add(new TextViewAttributes());
        mAttributeProcessors.add(new EditTextAttributes());
        mAttributeProcessors.add(new RadioGroupAttributes());
        mAttributeProcessors.add(new LayoutAttributes());
        mAttributeProcessors.add(new ImageViewAttributes());
        mAttributeProcessors.add(new SpinnerAttributes());
        mAttributeProcessors.add(new ProgressBarAttributes());
    }

    void initViewFactories() {
        mViewFactories.add(new BaseViewFactory());
    }

    void initValueGetters() {
        mValueGetters.add(new EditTextGetter());
        mValueGetters.add(new CheckBoxGetter());
        mValueGetters.add(new RadioGroupGetter());
        mValueGetters.add(new ProgressBarGetter());
        mValueGetters.add(new SpinnerGetter());
    }

    void initEventAttachers() {
        mEventAttachers.add(new ViewEventAttacher());
        mEventAttachers.add(new SeekBarEventAttacher());
        mEventAttachers.add(new SpinnerEventAttacher());
        mEventAttachers.add(new CheckBoxEventAttacher());
        mEventAttachers.add(new RadioGroupEventAttacher());
        mEventAttachers.add(new EditTextEventAttacher());
    }
}

package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewBuilder;
import com.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class ViewAttributes<ViewType extends View> {
    static final String TAG = ViewAttributes.class.getSimpleName();

    public interface Applicator<ViewType> {
        void apply(Context context, ViewType view, Values attrs, String name);
    }

    private final Map<String, Applicator> applicators = new HashMap<>();
    private final Map<String, View> mViewIds;

    public ViewAttributes() {
        this(null);
    }

    public ViewAttributes(Map<String, View> viewIds) {
        mViewIds = viewIds;

        applicators.put("background", new Applicator<View>() {
            public void apply(Context context, final View view, Values attrs, String name) {
                final String v = attrs.getString(name);

                if(v.startsWith("http")) {
                    ViewBuilder.get().getIcon(view.getContext(), v, new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            final BitmapDrawable bd = new BitmapDrawable(view.getResources(), resource);
                            view.setBackground(bd);
                        }
                    });
                } else if(v.equals("@null")) {
                    view.setBackgroundResource(0);
                } else {
                    view.setBackgroundColor(ViewUtils.parseColor(v, 0));
                }
            }
        });

        applicators.put("clickable", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setClickable(attrs.getBoolean(name, true));
            }
        });

        applicators.put("visibility", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setVisibility(ViewUtils.toVisibility(attrs.getString(name)));
            }
        });

        applicators.put("visible", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final int viz = (attrs.getBoolean(name, true))?
                        View.VISIBLE: View.INVISIBLE;
                view.setVisibility(viz);
            }
        });

        applicators.put("focusable", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setFocusable(attrs.getBoolean(name, true));
            }
        });

        applicators.put("minHeight", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setMinimumHeight(attrs.getInt(name, 0));
            }
        });

        applicators.put("minWidth", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setMinimumWidth(ViewUtils.toScaledSize(ViewBuilder.get().getDisplayMetrics(), attrs.getString(name)));
            }
        });

        applicators.put("alpha", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setAlpha(attrs.getFloat(name, 1f));
            }
        });

        applicators.put("rotation", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setRotation(attrs.getFloat(name, 1f));
            }
        });

        applicators.put("rotationX", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setRotationX(attrs.getFloat(name, 1f));
            }
        });

        applicators.put("rotationY", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setRotationY(attrs.getFloat(name, 1f));
            }
        });

        applicators.put("scaleX", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setScaleX(attrs.getFloat(name, 1f));
            }
        });

        applicators.put("scaleY", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setScaleY(attrs.getFloat(name, 1f));
            }
        });

        applicators.put("padding", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final int padding = ViewUtils.toScaledSize(ViewBuilder.get().getDisplayMetrics(), attrs.getString(name));
                view.setPadding(padding, padding, padding, padding);
            }
        });

        applicators.put("paddingLeft", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final int padding = ViewUtils.toScaledSize(ViewBuilder.get().getDisplayMetrics(), attrs.getString(name));
                view.setPadding(padding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
            }
        });

        applicators.put("paddingRight", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final int padding = ViewUtils.toScaledSize(ViewBuilder.get().getDisplayMetrics(), attrs.getString(name));
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), padding, view.getPaddingBottom());
            }
        });

        applicators.put("paddingTop", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final int padding = ViewUtils.toScaledSize(ViewBuilder.get().getDisplayMetrics(), attrs.getString(name));
                view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), view.getPaddingBottom());
            }
        });

        applicators.put("paddingBottom", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final int padding = ViewUtils.toScaledSize(ViewBuilder.get().getDisplayMetrics(), attrs.getString(name));
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), padding);
            }
        });

        applicators.put("tag", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setTag(R.string.tag_view_tag, attrs.get(name));
            }
        });

        applicators.put("enabled", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setEnabled(attrs.getBoolean(name, true));
            }
        });

        applicators.put("id", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                final String id = attrs.getString(name);
                view.setTag(R.string.tag_view_id, id);

                if(mViewIds != null) {
                    mViewIds.put(id, view);
                }
            }
        });

        applicators.put("name", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Values attrs, String name) {
                view.setTag(R.string.tag_view_name, attrs.getString(name));
            }
        });


        // TODO: And so on
    }

    public boolean appliesTo(View view) {
        return true;
    }

    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    public void applyTo(Context context, ViewType view, Values attrs) {
        final Map<String, Applicator> applicatorMap = getApplicators();

        for(String attr: attrs.keySet()) {
            final Applicator applicator = applicatorMap.get(attr);

            if(applicator != null) {
                try {
                    applicator.apply(context, view, attrs, attr);
                } catch(Throwable ex) {
                    Log.e(getClass().getSimpleName(), String.format("Unable to apply attribute %s", attr), ex);
                }
            }
        }

        view.setTag(attrs);

        Log.v(TAG, String.format("viewIds=%s", mViewIds));
    }
}

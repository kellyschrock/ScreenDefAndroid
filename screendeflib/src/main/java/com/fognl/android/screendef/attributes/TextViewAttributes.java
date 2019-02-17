package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewBuilder;
import com.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class TextViewAttributes extends ViewAttributes<TextView> {


    private final Map<String, Applicator> applicators = new HashMap<>();

    public TextViewAttributes() {
        super();

        applicators.put("text", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setText(attrs.getString(name));
            }
        });

        applicators.put("textSize", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setTextSize(attrs.getFloat(name, 10));
            }
        });

        applicators.put("textColor", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setTextColor(ViewUtils.parseColor(attrs.getString(name), 0));
            }
        });

        applicators.put("textColorHighlight", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setHighlightColor(ViewUtils.parseColor(attrs.getString(name), 0));
            }
        });

        applicators.put("textColorHint", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setHintTextColor(ViewUtils.parseColor(attrs.getString(name), 0));
            }
        });

        applicators.put("textColorLink", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setLinkTextColor(ViewUtils.parseColor(attrs.getString(name), 0));
            }
        });

        applicators.put("textAllCaps", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setAllCaps(attrs.getBoolean(name, false));
            }
        });

        applicators.put("textStyle", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                switch(attrs.getString(name)) {
                    case "bold": {
                        view.setTypeface(view.getTypeface(), Typeface.BOLD);
                        break;
                    }

                    case "italic": {
                        view.setTypeface(view.getTypeface(), Typeface.ITALIC);
                        break;
                    }

                    case "bold_italic": {
                        view.setTypeface(view.getTypeface(), Typeface.BOLD_ITALIC);
                        break;
                    }
                }
            }
        });

        applicators.put("shadow", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                final Values shadow = attrs.getObject(name, null);

                if(shadow != null) {
                    view.setShadowLayer(
                            shadow.getFloat("radius", 0f),
                            shadow.getFloat("dx", 0f),
                            shadow.getFloat("dy", 0f),
                            ViewUtils.parseColor(shadow.getString("color"), Color.BLACK)
                            );
                }
            }
        });

        applicators.put("gravity", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setGravity(ViewUtils.toGravity(attrs.getString("gravity")));
            }
        });

        applicators.put("lines", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setLines(attrs.getInt(name, 1));
            }
        });

        applicators.put("maxLines", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setMaxLines(attrs.getInt(name, 1));
            }
        });

        applicators.put("singleLine", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setSingleLine(attrs.getBoolean(name, false));
            }
        });

        applicators.put("ellipsize", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setEllipsize(ViewUtils.toTruncateAt(attrs.getString(name)));
            }
        });



        applicators.put("iconLeft", new Applicator<TextView>() {
            @Override
            public void apply(Context context, final TextView view, Values attrs, String name) {
                final String url = attrs.getString(name);
                ViewBuilder.get().getIcon(view.getContext(), url, new ViewBuilder.BitmapCallback() {
                    @Override
                    public void onBitmap(Bitmap bmp) {
                        final BitmapDrawable bd = new BitmapDrawable(view.getContext().getResources(), bmp);
                        view.setCompoundDrawablesWithIntrinsicBounds(bd, null, null, null);
                    }
                });
            }
        });

        applicators.put("iconRight", new Applicator<TextView>() {
            @Override
            public void apply(Context context, final TextView view, Values attrs, String name) {
                final String url = attrs.getString(name);
                ViewBuilder.get().getIcon(view.getContext(), url, new ViewBuilder.BitmapCallback() {
                    @Override
                    public void onBitmap(Bitmap bmp) {
                        final BitmapDrawable bd = new BitmapDrawable(view.getContext().getResources(), bmp);
                        view.setCompoundDrawablesWithIntrinsicBounds(null, null, bd, null);
                    }
                });
            }
        });

        applicators.put("iconPadding", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setCompoundDrawablePadding(attrs.getInt(name, 0));
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof TextView);
    }
}

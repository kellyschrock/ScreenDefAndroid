package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewBuilder;
import com.example.screendef.fognl.android.screendef.ViewUtils;

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

        applicators.put("gravity", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, String name) {
                view.setGravity(ViewUtils.toGravity(attrs.getString("gravity")));
            }
        });

        applicators.put("iconLeft", new Applicator<TextView>() {
            @Override
            public void apply(Context context, final TextView view, Values attrs, String name) {
                final String url = attrs.getString(name);
                ViewBuilder.getIcon(view.getContext(), url, new ViewBuilder.BitmapCallback() {
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
                ViewBuilder.getIcon(view.getContext(), url, new ViewBuilder.BitmapCallback() {
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

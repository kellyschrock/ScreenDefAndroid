package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewBuilder;
import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class ImageViewAttributes extends ViewAttributes<TextView> {

    private final Map<String, Applicator> applicators = new HashMap<>();

    public ImageViewAttributes() {
        super();

        applicators.put("icon", new Applicator<ImageView>() {
            @Override
            public void apply(Context context, ImageView view, Values attrs, String name) {
                final String url = attrs.getString(name);
                ViewBuilder.get().setImageViewIcon(view, url);
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof ImageView);
    }
}

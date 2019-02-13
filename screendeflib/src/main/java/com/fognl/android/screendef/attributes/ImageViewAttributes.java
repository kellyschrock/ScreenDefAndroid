package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewBuilder;

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

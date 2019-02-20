package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.fognl.android.screendef.Values;

import java.util.HashMap;
import java.util.Map;

public class ScrollViewAttributes extends ViewAttributes<ScrollView> {
    private final Map<String, Applicator> applicators = new HashMap<>();

    public ScrollViewAttributes() {
        super();

        applicators.put("fillViewport", new Applicator<ScrollView>() {
            @Override
            public void apply(Context context, ScrollView view, Values attrs, String name) {
                view.setFillViewport(attrs.getBoolean(name, false));
            }
        });

        applicators.put("smoothScrollingEnabled", new Applicator<ScrollView>() {
            @Override
            public void apply(Context context, ScrollView view, Values attrs, String name) {
                view.setSmoothScrollingEnabled(attrs.getBoolean(name, false));
            }
        });
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof ScrollView);
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

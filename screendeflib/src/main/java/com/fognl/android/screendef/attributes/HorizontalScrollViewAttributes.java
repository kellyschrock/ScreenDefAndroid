package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.fognl.android.screendef.Values;

import java.util.HashMap;
import java.util.Map;

public class HorizontalScrollViewAttributes extends ViewAttributes<HorizontalScrollView> {
    private final Map<String, Applicator> applicators = new HashMap<>();

    public HorizontalScrollViewAttributes() {
        super();

        applicators.put("fillViewport", new Applicator<HorizontalScrollView>() {
            @Override
            public void apply(Context context, HorizontalScrollView view, Values attrs, String name) {
                view.setFillViewport(attrs.getBoolean(name, false));
            }
        });

        applicators.put("smoothScrollingEnabled", new Applicator<HorizontalScrollView>() {
            @Override
            public void apply(Context context, HorizontalScrollView view, Values attrs, String name) {
                view.setSmoothScrollingEnabled(attrs.getBoolean(name, false));
            }
        });
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof HorizontalScrollView);
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

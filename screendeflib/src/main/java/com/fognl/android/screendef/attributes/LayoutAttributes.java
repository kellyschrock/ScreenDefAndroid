package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class LayoutAttributes extends ViewAttributes<ViewGroup> {

    private final Map<String, Applicator> applicators = new HashMap<>();

    public LayoutAttributes() {
        super();

        applicators.put("orientation", new Applicator<ViewGroup>() {
            @Override
            public void apply(Context context, ViewGroup view, Values attrs, String name) {
                ViewUtils.callIntSetter(view, "setOrientation", ViewUtils.toLinearOrientation(attrs.getString(name)));
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof ViewGroup) && (view.getClass().getName().endsWith("Layout"));
    }
}

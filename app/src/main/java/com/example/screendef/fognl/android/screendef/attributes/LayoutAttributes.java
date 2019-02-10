package com.example.screendef.fognl.android.screendef.attributes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class LayoutAttributes extends ViewAttributes<ViewGroup> {
    public static boolean appliesTo(View view) {
        return (view instanceof ViewGroup) && (view.getClass().getName().endsWith("Layout"));
    }

    private final Map<String, Applicator> applicators = new HashMap<>();

    public LayoutAttributes() {
        super();

        applicators.put("orientation", new Applicator<ViewGroup>() {
            @Override
            public void apply(ViewGroup view, Object value) {
                ViewUtils.callIntSetter(view, "setOrientation", ViewUtils.toLinearOrientation(value.toString()) );
            }
        });

        applicators.put("padding", new Applicator<ViewGroup>() {
            @Override
            public void apply(ViewGroup view, Object value) {
                final int padding = Integer.valueOf(value.toString()).intValue();
                view.setPadding(padding, padding, padding, padding);
            }
        });

        applicators.put("paddingLeft", new Applicator<ViewGroup>() {
            @Override
            public void apply(ViewGroup view, Object value) {
                final int padding = Integer.valueOf(value.toString()).intValue();
                view.setPadding(padding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
            }
        });

        applicators.put("paddingRight", new Applicator<ViewGroup>() {
            @Override
            public void apply(ViewGroup view, Object value) {
                final int padding = Integer.valueOf(value.toString()).intValue();
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), padding, view.getPaddingBottom());
            }
        });

        applicators.put("paddingTop", new Applicator<ViewGroup>() {
            @Override
            public void apply(ViewGroup view, Object value) {
                final int padding = Integer.valueOf(value.toString()).intValue();
                view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), view.getPaddingBottom());
            }
        });

        applicators.put("paddingBottom", new Applicator<ViewGroup>() {
            @Override
            public void apply(ViewGroup view, Object value) {
                final int padding = Integer.valueOf(value.toString()).intValue();
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), padding);
            }
        });


    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

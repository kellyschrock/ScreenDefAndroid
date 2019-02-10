package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class ViewAttributes<ViewType extends View> {
    public interface Applicator<ViewType> {
        void apply(Context context, ViewType view, Map<String, Object> attrs, Object value);
    }

    private final Map<String, Applicator> applicators = new HashMap<>();

    public ViewAttributes() {
        applicators.put("background", new Applicator<View>() {
            public void apply(Context context, View view, Map<String, Object> attrs, Object value) {
                view.setBackgroundColor(ViewUtils.parseColor(value.toString()));
            }
        });

        applicators.put("minHeight", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Map<String, Object> attrs, Object value) {
                view.setMinimumHeight(Integer.valueOf(value.toString()));
            }
        });

        applicators.put("minWidth", new Applicator<View>() {
            @Override
            public void apply(Context context, View view, Map<String, Object> attrs, Object value) {
                view.setMinimumWidth(Integer.valueOf(value.toString()));
            }
        });

        // TODO: And so on
    }

    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    public void applyTo(Context context, ViewType view, Map<String, Object> attrs) {
        final Map<String, Applicator> applicatorMap = getApplicators();

        for(String attr: attrs.keySet()) {
            final Applicator applicator = applicatorMap.get(attr);

            if(applicator != null) {
                final Object value = attrs.get(attr);
                if(value != null) {
                    try {
                        applicator.apply(context, view, attrs, value);
                    } catch(Throwable ex) {
                        Log.e(getClass().getSimpleName(), String.format("Unable to apply attribute %s", attr), ex);
                    }
                }
            }
        }
    }
}

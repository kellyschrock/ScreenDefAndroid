package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class ViewAttributes<ViewType extends View> {
    public interface Applicator<ViewType> {
        void apply(Context context, ViewType view, Values attrs, String name);
    }

    public static boolean appliesTo(View view) {
        return true;
    }

    private final Map<String, Applicator> applicators = new HashMap<>();

    public ViewAttributes() {
        applicators.put("background", new Applicator<View>() {
            public void apply(Context context, View view, Values attrs, String name) {
                view.setBackgroundColor(ViewUtils.parseColor(attrs.getString(name)));
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
                view.setMinimumWidth(attrs.getInt(name, 0));
            }
        });

        // TODO: And so on
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
    }
}

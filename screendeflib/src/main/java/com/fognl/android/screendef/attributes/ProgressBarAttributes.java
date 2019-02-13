package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.fognl.android.screendef.Values;

import java.util.HashMap;
import java.util.Map;

public class ProgressBarAttributes extends ViewAttributes<ProgressBar> {

    private final Map<String, Applicator> applicators = new HashMap<>();

    public ProgressBarAttributes() {
        super();

        applicators.put("max", new Applicator<ProgressBar>() {
            @Override
            public void apply(Context context, ProgressBar view, Values attrs, String name) {
                view.setMax(attrs.getInt(name, 100));
            }
        });
        
        applicators.put("progress", new Applicator<ProgressBar>() {
            @Override
            public void apply(Context context, ProgressBar view, Values attrs, String name) {
                view.setProgress(attrs.getInt(name, 100));
            }
        });

        applicators.put("indeterminate", new Applicator<ProgressBar>() {
            @Override
            public void apply(Context context, ProgressBar view, Values attrs, String name) {
                final boolean indeterm = attrs.getBoolean(name, false);
                view.setIndeterminate(indeterm);
            }
        });

    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof ProgressBar);
    }
}

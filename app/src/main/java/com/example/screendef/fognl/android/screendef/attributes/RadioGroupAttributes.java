package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class RadioGroupAttributes extends ViewAttributes<RadioGroup> {
    public static boolean appliesTo(View view) {
        return (view instanceof RadioGroup);
    }

    private final Map<String, Applicator> applicators = new HashMap<>();

    public RadioGroupAttributes() {
        super();

        applicators.put("orientation", new Applicator<RadioGroup>() {
            @Override
            public void apply(Context context, RadioGroup view, Map<String, Object> attrs, Object value) {
                view.setOrientation(ViewUtils.toRadioGroupOrientation(value.toString()));
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

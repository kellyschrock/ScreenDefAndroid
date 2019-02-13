package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;

import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class RadioGroupAttributes extends ViewAttributes<RadioGroup> {

    private final Map<String, Applicator> applicators = new HashMap<>();

    public RadioGroupAttributes() {
        super();

        applicators.put("orientation", new Applicator<RadioGroup>() {
            @Override
            public void apply(Context context, RadioGroup view, Values attrs, String name) {
                view.setOrientation(ViewUtils.toRadioGroupOrientation(attrs.getString(name)));
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof RadioGroup);
    }
}

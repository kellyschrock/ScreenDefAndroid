package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.fognl.android.screendef.Values;

import java.util.HashMap;
import java.util.Map;

public class CompoundButtonAttributes extends ViewAttributes<CompoundButton> {
    private final Map<String, Applicator> applicators = new HashMap<>();

    public CompoundButtonAttributes() {
        super();

        applicators.put("checked", new Applicator<CompoundButton>() {
            @Override
            public void apply(Context context, CompoundButton view, Values attrs, String name) {
                view.setChecked(attrs.getBoolean(name, false));
            }
        });
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof CompoundButton);
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

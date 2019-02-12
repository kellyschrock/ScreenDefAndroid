package com.example.screendef.fognl.android.screendef.values;

import android.view.View;
import android.widget.CheckBox;

import com.example.screendef.fognl.android.screendef.Values;

public class CheckBoxGetter implements ValueGetter<CheckBox> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof CheckBox);
    }

    @Override
    public void getValuesFrom(CheckBox view, String name, Values output) {
        output.put(name, view.isChecked());
    }
}

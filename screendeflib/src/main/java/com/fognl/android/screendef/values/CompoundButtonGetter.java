package com.fognl.android.screendef.values;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fognl.android.screendef.Values;

public class CompoundButtonGetter implements ValueGetter<CompoundButton> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof CompoundButton);
    }

    @Override
    public void getValuesFrom(CompoundButton view, String name, Values output) {
        output.put(name, view.isChecked());
    }
}

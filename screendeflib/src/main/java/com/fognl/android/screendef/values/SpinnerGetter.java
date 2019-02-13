package com.fognl.android.screendef.values;

import android.view.View;
import android.widget.Spinner;

import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.spinner.SpinnerItem;

public class SpinnerGetter implements ValueGetter<Spinner> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof Spinner);
    }

    @Override
    public void getValuesFrom(Spinner view, String name, Values output) {
        final Object selection = view.getSelectedItem();
        if(selection != null && (selection instanceof SpinnerItem)) {
            final SpinnerItem si = (SpinnerItem)selection;

            output.put(name, new Values().put("id", si.getId()).put("text", si.getText()));
        }
    }
}

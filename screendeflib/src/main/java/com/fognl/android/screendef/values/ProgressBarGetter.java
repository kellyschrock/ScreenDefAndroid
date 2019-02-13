package com.fognl.android.screendef.values;

import android.view.View;
import android.widget.ProgressBar;

import com.fognl.android.screendef.Values;

public class ProgressBarGetter implements ValueGetter<ProgressBar> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof ProgressBar);
    }

    @Override
    public void getValuesFrom(ProgressBar view, String name, Values output) {
        output.put(name, view.getProgress());
    }
}

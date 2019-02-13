package com.fognl.android.screendef.values;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;

public class RadioGroupGetter implements ValueGetter<RadioGroup> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof RadioGroup);
    }

    @Override
    public void getValuesFrom(RadioGroup view, String name, Values output) {
        final int checkedId = view.getCheckedRadioButtonId();
        final RadioButton button = view.findViewById(checkedId);
        if(button != null) {
            final String radioName = (String)button.getTag(R.string.tag_view_name);
            output.put(name, radioName);
        }
    }
}

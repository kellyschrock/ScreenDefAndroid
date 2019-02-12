package com.example.screendef.fognl.android.screendef.values;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.screendef.fognl.android.screendef.Values;

public class EditTextGetter implements ValueGetter<EditText> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof EditText);
    }

    @Override
    public void getValuesFrom(EditText view, String name, Values output) {
        final String text = view.getText().toString();
        if(!TextUtils.isEmpty(text)) {
            output.put(name, text);
        }
    }
}

package com.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class EditTextAttributes extends ViewAttributes<TextView> {


    private final Map<String, Applicator> applicators = new HashMap<>();

    public EditTextAttributes() {
        super();

        applicators.put("text", new Applicator<EditText>() {
            @Override
            public void apply(Context context, EditText view, Values attrs, String name) {
                view.setText(attrs.getString(name));
            }
        });

        applicators.put("hint", new Applicator<EditText>() {
            @Override
            public void apply(Context context, EditText view, Values attrs, String name) {
                view.setHint(attrs.getString(name));
            }
        });

        applicators.put("textColorHint", new Applicator<EditText>() {
            @Override
            public void apply(Context context, EditText view, Values attrs, String name) {
                view.setHintTextColor(ViewUtils.parseColor(attrs.getString(name), Color.WHITE));
            }
        });

        applicators.put("inputType", new Applicator<EditText>() {
            @Override
            public void apply(Context context, EditText view, Values attrs, String name) {
                view.setInputType(ViewUtils.toEditTextInputType(attrs.getString(name)));
            }
        });

        applicators.put("maxLength", new Applicator<EditText>() {
            @Override
            public void apply(Context context, EditText view, Values attrs, String name) {
                view.setFilters(new InputFilter[] { new InputFilter.LengthFilter(attrs.getInt(name, 100))});
            }
        });

        // TODO: Do imeOptions at some point
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof EditText);
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

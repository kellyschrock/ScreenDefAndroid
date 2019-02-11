package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewBuilder;
import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class EditTextAttributes extends ViewAttributes<TextView> {


    private final Map<String, Applicator> applicators = new HashMap<>();

    public static boolean appliesTo(View view) {
        return (view instanceof EditText);
    }

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
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

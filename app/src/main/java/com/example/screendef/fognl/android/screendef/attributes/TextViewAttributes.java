package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class TextViewAttributes extends ViewAttributes<TextView> {


    private final Map<String, Applicator> applicators = new HashMap<>();

    public static boolean appliesTo(View view) {
        return (view instanceof TextView);
    }

    public TextViewAttributes() {
        super();

        applicators.put("text", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, Object value) {
                view.setText(value.toString());
            }
        });

        applicators.put("textSize", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, Object value) {
                view.setTextSize(Float.valueOf(value.toString()));
            }
        });

        applicators.put("textColor", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, Object value) {
                view.setTextColor(ViewUtils.parseColor(value.toString()));
            }
        });

        applicators.put("textAllCaps", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, Object value) {
                view.setAllCaps(Boolean.valueOf(value.toString()));
            }
        });

        applicators.put("textStyle", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, Object value) {
                switch(value.toString()) {
                    case "bold": {
                        view.setTypeface(view.getTypeface(), Typeface.BOLD);
                        break;
                    }

                    case "italic": {
                        view.setTypeface(view.getTypeface(), Typeface.ITALIC);
                        break;
                    }

                    case "bold_italic": {
                        view.setTypeface(view.getTypeface(), Typeface.BOLD_ITALIC);
                        break;
                    }
                }
            }
        });

        applicators.put("gravity", new Applicator<TextView>() {
            @Override
            public void apply(Context context, TextView view, Values attrs, Object value) {
                view.setGravity(ViewUtils.toGravity(attrs.getString("gravity")));
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

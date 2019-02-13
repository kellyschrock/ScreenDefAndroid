package com.fognl.android.screendef;

import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewUtils {
    static final String TAG = ViewUtils.class.getSimpleName();

    static class Marklar {
        final String name;
        final int value;

        Marklar(String name, int g) {
            this.name = name;
            this.value = g;
        }
    }

    public static int toViewSize(String value) {
        if("parent".equals(value)) return ViewGroup.LayoutParams.MATCH_PARENT;
        if("match_parent".equals(value)) return ViewGroup.LayoutParams.MATCH_PARENT;
        if("wrap_content".equals(value)) return ViewGroup.LayoutParams.WRAP_CONTENT;

        String v = value;
        if(v.endsWith("dip")) {
            v = v.substring(0, v.indexOf("dip"));
        }

        return toInt(v, 0);
    }

    public static int toEditTextInputType(String value) {
        int inputType = 0;
        String[] parts = value.split("\\|");

        for(Marklar mm: new Marklar[] {
                new Marklar("textCapWords", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS),
                new Marklar("textCapSentences", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES),
                new Marklar("textCapCharacters", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS),
                new Marklar("textEmailAddress", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS),
                new Marklar("textPassword", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD),
                new Marklar("textPersonName", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME),
                new Marklar("textShortMessage", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE),
                new Marklar("textLongMessage", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE),
                new Marklar("number", InputType.TYPE_CLASS_NUMBER),
                new Marklar("numberDecimal", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL),
                new Marklar("numberPassword", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD)
        }) {
            if(containsString(parts, mm.name)) {
                inputType |= mm.value;
            }
        }

        return inputType;
    }

    public static int toLinearOrientation(String value) {
        if("vertical".equals(value)) return LinearLayout.VERTICAL;
        else return LinearLayout.HORIZONTAL;
    }

    public static int toRadioGroupOrientation(String value) {
        if("vertical".equals(value)) return RadioGroup.VERTICAL;
        return RadioGroup.HORIZONTAL;
    }

    public static int toInt(String value, int def) {
        try {
            return Integer.valueOf(value);
        } catch(Throwable ex) { return def; }
    }

    public static int parseColor(String color, int fallback) {
        try {
            return Color.parseColor(color);
        } catch(Throwable ex) {
            return fallback;
        }
    }

    static boolean containsString(String[] parts, String str) {
        for(String s: parts) {
            if(s.equals(str)) {
                return true;
            }
        }

        return false;
    }

    public static int toGravity(String grav) {
        int gravity = Gravity.NO_GRAVITY;

        final String[] parts = grav.split("\\|");

        for(Marklar derp: new Marklar[] {
                new Marklar("center_vertical", Gravity.CENTER_VERTICAL),
                new Marklar("center_horizontal", Gravity.CENTER_HORIZONTAL),
                new Marklar("center", Gravity.CENTER),
                new Marklar("left", Gravity.LEFT),
                new Marklar("top", Gravity.TOP),
                new Marklar("right", Gravity.RIGHT),
                new Marklar("bottom", Gravity.BOTTOM)
        }) {
            if(containsString(parts, derp.name)) {
                gravity |= derp.value;
            }
        }

        return gravity;
    }

    public static void setGravities(Values attrs, View view, ViewGroup.LayoutParams lp) {
        try {
            if(attrs.containsKey("layout_gravity")) {
                setFieldValue(lp, "gravity", new Integer[] {toGravity(attrs.get("layout_gravity").toString())} );
            }

            if(attrs.containsKey("gravity")) {
                callIntSetter(view, "setGravity", toGravity(attrs.get("gravity").toString()) );
            }

        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static void setWeights(Values attrs, ViewGroup.LayoutParams lp) {
        try {
            if(attrs.containsKey("layout_weight")) {
                ((LinearLayout.LayoutParams)lp).weight = Float.valueOf(attrs.get("layout_weight").toString());
            }
        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static void setMargins(Values attrs, ViewGroup.LayoutParams lp) throws Throwable {
        if(lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams)lp;

            if(attrs.containsKey("layout_margin")) {
                int margin = Integer.valueOf((String)attrs.get("layout_margin"));
                mp.leftMargin = mp.rightMargin = mp.topMargin = mp.bottomMargin = margin;
            }

            if(attrs.containsKey("layout_marginTop")) {
                mp.topMargin = Integer.valueOf((String)attrs.get("layout_marginTop"));
            }

            if(attrs.containsKey("layout_marginTop")) {
                mp.bottomMargin = Integer.valueOf((String)attrs.get("layout_marginBottom"));
            }

            if(attrs.containsKey("layout_marginLeft")) {
                mp.leftMargin = Integer.valueOf((String)attrs.get("layout_marginLeft"));
            }

            if(attrs.containsKey("layout_marginRight")) {
                mp.rightMargin = Integer.valueOf((String)attrs.get("layout_marginRight"));
            }
        }
    }

    public static void setFieldValue(Object lp, String name, Object value) {
        try {
            final Class type = lp.getClass();
            final Field field = type.getDeclaredField(name);
            field.set(lp, value);
        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static void callIntSetter(Object obj, String name, int value) {
        try {
            final Class type = obj.getClass();
            Log.v(TAG, String.format("type=%s", type.getSimpleName()));

            final Method method = type.getDeclaredMethod(name, new Class[] { Integer.TYPE });
            method.invoke(obj, value);
        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }
}

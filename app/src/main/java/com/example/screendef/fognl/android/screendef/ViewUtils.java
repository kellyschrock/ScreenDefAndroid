package com.example.screendef.fognl.android.screendef;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class ViewUtils {
    static final String TAG = ViewUtils.class.getSimpleName();

    static class Grav {
        final String name;
        final int gravity;
        Grav(String name, int g) {
            this.name = name;
            this.gravity = g;
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

    public static int parseColor(String color) {
        try {
            return Color.parseColor(color);
        } catch(Throwable ex) {
            return Color.RED;
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

        for(Grav derp: new Grav[] {
                new Grav("center_vertical", Gravity.CENTER_VERTICAL),
                new Grav("center_horizontal", Gravity.CENTER_HORIZONTAL),
                new Grav("center", Gravity.CENTER),
                new Grav("left", Gravity.LEFT),
                new Grav("top", Gravity.TOP),
                new Grav("right", Gravity.RIGHT),
                new Grav("bottom", Gravity.BOTTOM)
        }) {
            if(containsString(parts, derp.name)) {
                gravity |= derp.gravity;
            }
        }

        return gravity;
    }

    public static void setGravities(Map<String, Object> attrs, View view, ViewGroup.LayoutParams lp) {
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

    public static void setWeights(Map<String, Object> attrs, ViewGroup.LayoutParams lp) {
        try {
            if(attrs.containsKey("layout_weight")) {
                ((LinearLayout.LayoutParams)lp).weight = Float.valueOf(attrs.get("layout_weight").toString());
            }
        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static void setMargins(Map<String, Object> attrs, ViewGroup.LayoutParams lp) throws Throwable {
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

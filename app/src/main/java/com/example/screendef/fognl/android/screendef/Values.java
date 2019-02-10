package com.example.screendef.fognl.android.screendef;

import java.util.HashMap;

public class Values extends HashMap<String, Object> {
    public Values() {
        super();
    }

    public String getString(String name) {
        Object v = get(name);
        return (v != null)? v.toString(): null;
    }

    public int getInt(String name, int fallback) {
        Object v = get(name);
        return (v != null)?
                (v instanceof Integer)? (Integer)v: Integer.valueOf(v.toString()): fallback;
    }

    public float getFloat(String name, float fallback) {
        Object v = get(name);
        return (v != null)?
                (v instanceof Float)? (Float)v: Float.valueOf(v.toString()): fallback;
    }

    public double getDouble(String name, double fallback) {
        Object v = get(name);
        return (v != null)?
                (v instanceof Double)? (Double)v: Double.valueOf(v.toString()): fallback;
    }

    public boolean getBoolean(String name, boolean fallback) {
        Object v = get(name);
        return (v != null)?
                (v instanceof Boolean)? (Boolean)v: Boolean.valueOf(v.toString()): fallback;
    }

    public <T> T getObject(String name, T fallback) {
        Object v = get(name);
        return (v != null)? (T)v: fallback;
    }
}

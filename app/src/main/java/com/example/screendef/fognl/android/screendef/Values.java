package com.example.screendef.fognl.android.screendef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class Values extends HashMap<String, Object> {
    public static JSONObject populate(JSONObject jo, Values values) throws JSONException {

        for(String key: values.keySet()) {
            final Object value = values.get(key);

            if(value instanceof Values) {
                final JSONObject joItem = populate(new JSONObject(), (Values)value);
                jo.put(key, joItem);
            } else if(value instanceof List) {
                final JSONArray array = new JSONArray();

                final List list = (List)value;
                for(Object item: list) {
                    if(value instanceof Values) {
                        final JSONObject joItem = populate(new JSONObject(), (Values)item);
                        array.put(joItem);
                    } else {
                        array.put(value);
                    }
                }

                jo.put(key, array);
            } else {
                jo.put(key, value);
            }
        }

        return jo;
    }

    public Values() {
        super();
    }

    public boolean has(String name) {
        return (null != get(name));
    }

    public Values put(String name, Object value) {
        super.put(name, value);
        return this;
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

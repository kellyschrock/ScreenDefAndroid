package com.example.screendef.fognl.android.screendef.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    public interface Visitor {
        void visit(JSONObject jo);
    }

    public static void visit(JSONArray array, Visitor v) {
        if(array != null) {
            for(int i = 0, size = array.length(); i < size; ++i) {
                final JSONObject jo = array.optJSONObject(i);
                if(jo != null) {
                    v.visit(jo);
                }
            }
        }
    }

    public static Map<String, Object> toMap(JSONObject jo) {
        final Map<String, Object> map = new HashMap<>();

        final JSONArray names = jo.names();
        for(int i = 0, size = names.length(); i < size; ++i) {
            final String name = names.optString(i);

            Object value = jo.opt(name);
            if(value instanceof JSONObject) {
                map.put(name, toMap((JSONObject)value));
            } else if(value instanceof JSONArray) {
                map.put(name, toList((JSONArray)value));
            } else {
                map.put(name, value);
            }
        }

        return map;
    }

    public static List toList(JSONArray array) {
        final List list = new ArrayList();

        for(int i = 0, size = array.length(); i < size; ++i) {
            final Object value = array.opt(i);

            if(value instanceof JSONObject) {
                list.add(toMap((JSONObject)value));
            } else if(value instanceof JSONArray) {
                list.add(toList((JSONArray)value));
            } else {
                list.add(value);
            }
        }

        return list;
    }
}

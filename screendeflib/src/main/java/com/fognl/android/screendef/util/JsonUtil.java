package com.fognl.android.screendef.util;

import com.fognl.android.screendef.Values;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static Values toMap(JSONObject jo) {
        final Values map = new Values();

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

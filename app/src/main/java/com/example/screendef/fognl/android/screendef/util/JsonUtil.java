package com.example.screendef.fognl.android.screendef.util;

import org.json.JSONArray;
import org.json.JSONObject;

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
}

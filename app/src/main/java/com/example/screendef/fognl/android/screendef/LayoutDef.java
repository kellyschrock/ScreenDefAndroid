package com.example.screendef.fognl.android.screendef;

import com.example.screendef.fognl.android.screendef.util.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LayoutDef {
    public static LayoutDef populate(final LayoutDef s, JSONObject jo) {
        s.type = jo.optString("type");
        s.id = jo.optString("id");
        s.title = jo.optString("title");

        JsonUtil.visit(jo.optJSONArray("views"), new JsonUtil.Visitor() {
            @Override
            public void visit(JSONObject jo) {
                final ViewDef v = ViewDef.populate(new ViewDef(), jo);
                if(v != null) s.views.add(v);
            }
        });

        return s;
    }

    String type;
    String id;
    String title;
    private final List<ViewDef> views = new ArrayList<>();

    public LayoutDef() {
        super();
    }
}

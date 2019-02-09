package com.example.screendef.fognl.android.screendef;

import com.example.screendef.fognl.android.screendef.util.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScreenDef {
    public static ScreenDef populate(final ScreenDef s, JSONObject jo) {
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

    public ScreenDef() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<ViewDef> getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "ScreenDef{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", views=" + views +
                '}';
    }
}

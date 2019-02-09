package com.example.screendef.fognl.android.screendef;

import android.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;

import com.example.screendef.fognl.android.screendef.util.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewDef {
    public static ViewDef populate(final ViewDef v, JSONObject jo) {
        v.type = jo.optString("type");
        v.id = jo.optString("id");
        v.text = jo.optString("text");
        v.width = jo.optString("width");
        v.height = jo.optString("height");
        v.weight = (float)jo.optDouble("weight", -1);

        JsonUtil.visit(jo.optJSONArray("views"), new JsonUtil.Visitor() {
            @Override
            public void visit(JSONObject jo) {
                final ViewDef view = ViewDef.populate(new ViewDef(), jo);
                if(view != null) v.views.add(view);
            }
        });


        return v;
    }

    String type;
    String id;
    String text;
    String width;
    String height;
    float weight;
    final List<ViewDef> views = new ArrayList<>();

    public ViewDef() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public List<ViewDef> getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "ViewDef{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", weight=" + weight +
                ", views=" + views +
                '}';
    }
}

package com.example.screendef.fognl.android.screendef;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.screendef.fognl.android.screendef.util.JsonUtil;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewDef {
    static final String TAG = ViewDef.class.getSimpleName();

    public static ViewDef populate(final ViewDef v, JSONObject jo) {
        final Map<String, Object> map = JsonUtil.toMap(jo);
        return populate(v, map);
    }

    static ViewDef populate(final ViewDef v, Map<String, Object> map) {
        v.type = (String)map.get("type");

        final List<Map<String, Object>> children = (List<Map<String, Object>>)map.get("children");
        if(children != null) {
            for(Map<String, Object> child: children) {
                v.children.add(populate(new ViewDef(), child));
            }

            map.remove("children");
        }

        v.attrs.putAll(map);
        return v;
    }

    String type;
    final Values attrs = new Values();
    final List<ViewDef> children = new ArrayList<>();

    public ViewDef() {
        super();
    }

    public String getType() { return type; }

    public Object get(String name) {
        return attrs.get(name);
    }

    public <T> T get(String name, T defValue) {
        Object v = attrs.get(name);
        return (v == null) ? defValue: (T)v;
    }

    public List<ViewDef> getChildren() {
        return children;
    }

    public boolean hasChildren() { return !children.isEmpty(); }

    static Class findClassIn(Class type, String name) {
        try {
            final Class[] nestedTypes = type.getDeclaredClasses();
            if(nestedTypes != null) {
                for(Class nestedType: nestedTypes) {
                    if(nestedType.getName().endsWith(name)) {
                        return nestedType;
                    }
                }
            }

            return null;
        } catch(Throwable ex) {
            return null;
        }
    }

    public ViewGroup.LayoutParams getLayoutParams(View view, View parentView) {
        final int width = ViewUtils.toViewSize(attrs.getString("layout_width"));
        final int height = ViewUtils.toViewSize(attrs.getString("layout_height"));

        final Class paramsClass = findClassIn(parentView.getClass(), "LayoutParams");

        if(paramsClass != null) {
            try {
                Constructor ctor = paramsClass.getConstructor(Integer.TYPE, Integer.TYPE);
                final ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams)ctor.newInstance(width, height);

                ViewUtils.setMargins(attrs, lp);
                ViewUtils.setWeights(attrs, lp);
                ViewUtils.setGravities(attrs, view, lp);

                return lp;
            } catch(Throwable ex) {
                Log.e(TAG, ex.getMessage(), ex);
            }
        }

        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width, height);

        return lp;
    }

    @Override
    public String toString() {
        return "ViewDef{" +
                "type=" + type +
                "  attrs=" + attrs +
                ", children=" + children +
                '}';
    }
}

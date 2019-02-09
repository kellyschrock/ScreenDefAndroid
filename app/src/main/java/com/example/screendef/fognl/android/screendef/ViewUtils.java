package com.example.screendef.fognl.android.screendef;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewUtils {
    public static int toViewSize(String value) {
        if("parent".equals(value)) return ViewGroup.LayoutParams.MATCH_PARENT;
        if("match_parent".equals(value)) return ViewGroup.LayoutParams.MATCH_PARENT;
        if("wrap_content".equals(value)) return ViewGroup.LayoutParams.WRAP_CONTENT;
        return toInt(value, 0);
    }

    public static int toOrientation(String value) {
        if("vertical".equals(value)) return LinearLayout.VERTICAL;
        else return LinearLayout.HORIZONTAL;
    }

    static int toInt(String value, int def) {
        try {
            return Integer.valueOf(value);
        } catch(Throwable ex) { return def; }
    }
}

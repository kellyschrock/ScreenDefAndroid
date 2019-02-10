package com.example.screendef.fognl.android.screendef.recycler;

import com.example.screendef.fognl.android.screendef.Values;

public class RecyclerItem {
    public static RecyclerItem from(Values values) {
        return new RecyclerItem(values.getString("id"), values.getString("title"), values.getString("subtitle"));
    }

    public final String id;
    public final String title;
    public final String subtext;

    public RecyclerItem(String id, String title, String subtext) {
        this.id = id;
        this.title = title;
        this.subtext = subtext;
    }
}

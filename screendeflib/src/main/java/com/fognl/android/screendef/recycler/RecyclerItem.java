package com.fognl.android.screendef.recycler;

import com.fognl.android.screendef.Values;

public class RecyclerItem {
    public static RecyclerItem from(Values values) {
        return new RecyclerItem(values);
    }

    public final String id;
    public final String title;
    public final String subtext;
    public final Values values;

    RecyclerItem(Values values) {
        this.values = values;
        this.id = values.getString("id");
        this.title = values.getString("title");
        this.subtext = values.getString("subtitle");
    }
}

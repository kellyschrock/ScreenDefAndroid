package com.example.screendef.fognl.android.screendef.values;

import android.view.View;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerItem;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerViewWrapper;

public class RecyclerViewGetter implements ValueGetter<RecyclerViewWrapper> {
    @Override
    public boolean appliesTo(View view) {
        return (view instanceof RecyclerViewWrapper);
    }

    @Override
    public void getValuesFrom(RecyclerViewWrapper view, String name, Values output) {
        final RecyclerItem item = view.getSelectedItem();
        if(item != null) {
            output.put(name, new Values().put("id", item.id).put("title", item.title));
        }
    }
}

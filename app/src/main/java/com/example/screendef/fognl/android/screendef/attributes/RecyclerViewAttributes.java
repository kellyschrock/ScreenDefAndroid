package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerAdapter;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerItem;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerViewWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAttributes extends ViewAttributes<RecyclerViewWrapper> {
    private final Map<String, Applicator> applicators = new HashMap<>();

    public RecyclerViewAttributes() {
        super();

        applicators.put("adapter", new Applicator<RecyclerViewWrapper>() {
            @Override
            public void apply(Context context, RecyclerViewWrapper view, Values attrs, String name) {
                final Values adapter = (Values)attrs.get("adapter");
                if(adapter == null) return;

                final List<Values> items = adapter.getObject("items", null);
                if(items != null) {
                    final List<RecyclerItem> recyclerItems = new ArrayList<>();

                    for(Values item: items) {
                        recyclerItems.add(RecyclerItem.from(item));
                    }

                    final Values style = adapter.getObject("item_style", null);

                    view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    view.setAdapter(new RecyclerAdapter()
                            .setItemStyle(style)
                            .addAll(recyclerItems)
                            .setListener(view));
                }
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof RecyclerViewWrapper);
    }
}

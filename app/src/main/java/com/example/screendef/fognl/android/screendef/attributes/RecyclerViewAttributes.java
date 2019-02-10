package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerAdapter;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAttributes extends ViewAttributes<RecyclerView> {
    public static boolean appliesTo(View view) {
        return (view instanceof RecyclerView);
    }

    private final Map<String, Applicator> applicators = new HashMap<>();

    public RecyclerViewAttributes() {
        super();

        applicators.put("adapter", new Applicator<RecyclerView>() {
            @Override
            public void apply(Context context, RecyclerView view, Values attrs, Object value) {
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
                            .addAll(recyclerItems));
                }
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

package com.fognl.android.screendef.events;

import android.view.View;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.recycler.RecyclerAdapter;
import com.fognl.android.screendef.recycler.RecyclerItem;
import com.fognl.android.screendef.recycler.RecyclerViewWrapper;

public class RecyclerViewEventAttacher implements EventAttacher<RecyclerViewWrapper> {
    public static final String EVT_ON_ITEM_SELECTED = "on_item_selected";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof RecyclerViewWrapper);
    }

    @Override
    public void attachEventsTo(RecyclerViewWrapper view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_item_selected = attrs.getObject(EVT_ON_ITEM_SELECTED, null);
        if(on_item_selected == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.setItemListener(new RecyclerAdapter.ItemListener() {
            @Override
            public void onItemSelected(int position, RecyclerItem item) {
                Values itemValue = new Values().put("id", item.id).put("title", item.title);
                on_item_selected.put("item", itemValue);
                on_item_selected.put("position", position);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(viewId, EVT_ON_ITEM_SELECTED, on_item_selected);
                }
            }
        });
    }
}

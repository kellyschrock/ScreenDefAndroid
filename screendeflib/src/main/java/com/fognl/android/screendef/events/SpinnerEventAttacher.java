package com.fognl.android.screendef.events;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.spinner.SpinnerItem;

public class SpinnerEventAttacher implements EventAttacher<Spinner> {
    public static final String EVT_ON_ITEM_SELECTED = "on_item_selected";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof Spinner);
    }

    @Override
    public void attachEventsTo(Spinner view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_item_selected = attrs.getObject(EVT_ON_ITEM_SELECTED, null);
        if(on_item_selected == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final SpinnerItem item = (SpinnerItem)parent.getAdapter().getItem(position);
                Values itemValue = new Values().put("id", item.getId()).put("text", item.getText());
                final String screenId = (String)parent.getTag(R.string.tag_view_screen);

                on_item_selected.put("position", position);
                on_item_selected.put("item", itemValue);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(screenId, viewId, EVT_ON_ITEM_SELECTED, on_item_selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

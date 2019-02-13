package com.fognl.android.screendef.events;

import android.view.View;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;

public class ViewEventAttacher implements EventAttacher<View> {
    public static final String EVT_ON_CLICK = "on_click";

    @Override
    public boolean appliesTo(View view) {
        return true;
    }

    @Override
    public void attachEventsTo(View view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs != null) {
            final Values on_click = attrs.getObject(EVT_ON_CLICK, null);

            if(on_click != null) {
                final String id = (String)view.getTag(R.string.tag_view_id);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(ViewEventListener listener: listeners) {
                            listener.onViewEvent(id, EVT_ON_CLICK, on_click);
                        }
                    }
                });
            }
        }
    }
}

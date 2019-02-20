package com.fognl.android.screendef.events;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;

public class CompoundButtonEventAttacher implements EventAttacher<CompoundButton> {
    public static final String EVT_ON_CHECK = "on_check";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof CompoundButton);
    }

    @Override
    public void attachEventsTo(CompoundButton view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_check = attrs.getObject(EVT_ON_CHECK, null);
        if(on_check == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                on_check.put("checked", isChecked);
                final String screenId = (String)buttonView.getTag(R.string.tag_view_screen);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(screenId, viewId, EVT_ON_CHECK, on_check);
                }
            }
        });
    }
}

package com.fognl.android.screendef.events;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;

public class RadioGroupEventAttacher implements EventAttacher<RadioGroup> {
    public static final String EVT_ON_CHECK = "on_check";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof RadioGroup);
    }

    @Override
    public void attachEventsTo(RadioGroup view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_check = attrs.getObject(EVT_ON_CHECK, null);
        if(on_check == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = group.findViewById(checkedId);
                final String radioId = (button != null)? (String)button.getTag(R.string.tag_view_id): "unknown";
                final String screenId = (String)group.getTag(R.string.tag_view_screen);

                on_check.put("checked_id", radioId);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(screenId, viewId, EVT_ON_CHECK, on_check);
                }
            }
        });

    }
}

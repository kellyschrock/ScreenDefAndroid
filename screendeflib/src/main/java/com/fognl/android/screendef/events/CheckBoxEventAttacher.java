package com.fognl.android.screendef.events;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;

public class CheckBoxEventAttacher implements EventAttacher<CheckBox> {
    public static final String EVT_ON_CHECK = "on_check";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof CheckBox);
    }

    @Override
    public void attachEventsTo(CheckBox view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_check = attrs.getObject(EVT_ON_CHECK, null);
        if(on_check == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                on_check.put("checked", isChecked);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(viewId, EVT_ON_CHECK, on_check);
                }
            }
        });
    }
}

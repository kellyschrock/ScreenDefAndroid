package com.example.screendef.fognl.android.screendef.events;

import android.view.View;
import android.widget.SeekBar;

import com.example.screendef.R;
import com.example.screendef.fognl.android.screendef.Values;

public class SeekBarEventAttacher implements EventAttacher<SeekBar> {
    public static final String EVT_ON_PROGRESS = "on_progress";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof SeekBar);
    }

    @Override
    public void attachEventsTo(SeekBar view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_progress = attrs.getObject(EVT_ON_PROGRESS, null);
        if(on_progress == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                on_progress.put("value", progress);
                on_progress.put("from_user", fromUser);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(viewId, EVT_ON_PROGRESS, on_progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

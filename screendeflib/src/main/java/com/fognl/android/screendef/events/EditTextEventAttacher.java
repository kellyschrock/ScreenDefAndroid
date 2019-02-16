package com.fognl.android.screendef.events;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.fognl.android.screendef.R;
import com.fognl.android.screendef.Values;

public class EditTextEventAttacher implements EventAttacher<EditText> {
    public static final String EVT_ON_TEXT_CHANGED = "on_text_changed";

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof EditText);
    }

    @Override
    public void attachEventsTo(final EditText view, final Iterable<ViewEventListener> listeners) {
        final Values attrs = (Values)view.getTag();

        if(attrs == null) return;

        final Values on_text_changed = attrs.getObject(EVT_ON_TEXT_CHANGED, null);
        if(on_text_changed == null) return;

        final String viewId = (String)view.getTag(R.string.tag_view_id);

        view.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                final String screenId = (String)view.getTag(R.string.tag_view_screen);
                final String text = view.getText().toString();

                on_text_changed.put("text", text);

                for(ViewEventListener listener: listeners) {
                    listener.onViewEvent(screenId, viewId, EVT_ON_TEXT_CHANGED, on_text_changed);
                }
            }
        });
    }
}

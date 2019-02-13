package com.fognl.android.screendef.events;

import android.view.View;

public interface EventAttacher<ViewType extends View> {
    boolean appliesTo(View view);

    void attachEventsTo(ViewType view, Iterable<ViewEventListener> listeners);
}

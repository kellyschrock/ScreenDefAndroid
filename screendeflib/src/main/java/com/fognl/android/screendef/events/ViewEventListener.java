package com.fognl.android.screendef.events;

import com.fognl.android.screendef.Values;

public interface ViewEventListener {
    void onViewEvent(String screenId, String viewId, String event, Values data);
}

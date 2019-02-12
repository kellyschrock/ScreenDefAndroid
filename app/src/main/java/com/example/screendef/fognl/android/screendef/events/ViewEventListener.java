package com.example.screendef.fognl.android.screendef.events;

import com.example.screendef.fognl.android.screendef.Values;

public interface ViewEventListener {
    void onViewEvent(String viewId, String event, Values data);
}

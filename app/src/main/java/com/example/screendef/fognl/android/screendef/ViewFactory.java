package com.example.screendef.fognl.android.screendef;

import android.view.View;

import java.util.Map;

public interface ViewFactory {
    View instantiateViewFrom(String type, Map<String, Object> attrs);
}

package com.fognl.android.screendef;

import android.content.Context;
import android.view.View;

import java.util.Map;

public interface ViewFactory {
    View instantiateViewFrom(Context context, String type, Map<String, Object> attrs);
}

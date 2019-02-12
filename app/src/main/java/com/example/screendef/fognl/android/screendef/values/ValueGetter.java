package com.example.screendef.fognl.android.screendef.values;

import android.view.View;

import com.example.screendef.fognl.android.screendef.Values;

public interface ValueGetter<ViewType extends View> {
    boolean appliesTo(View view);

    void getValuesFrom(ViewType view, String name, Values output);
}

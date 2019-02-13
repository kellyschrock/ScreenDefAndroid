package com.fognl.android.screendef.module;

import com.fognl.android.screendef.ViewFactory;
import com.fognl.android.screendef.attributes.ViewAttributes;
import com.fognl.android.screendef.events.EventAttacher;
import com.fognl.android.screendef.values.ValueGetter;

public interface ViewBuilderModule {
    ViewFactory getViewFactory();
    ViewAttributes getAttributeProcessor();
    EventAttacher getEventAttacher();
    ValueGetter getValueGetter();
}

package com.fognl.android.screendef.recycler;

import com.fognl.android.screendef.ViewFactory;
import com.fognl.android.screendef.attributes.ViewAttributes;
import com.fognl.android.screendef.events.EventAttacher;
import com.fognl.android.screendef.module.ViewBuilderModule;
import com.fognl.android.screendef.values.RecyclerViewGetter;
import com.fognl.android.screendef.values.ValueGetter;
import com.fognl.android.screendef.viewfactory.RecyclerViewFactory;

public class RecyclerViewModule implements ViewBuilderModule {
    public RecyclerViewModule() {
        super();
    }

    @Override
    public ViewFactory getViewFactory() {
        return new RecyclerViewFactory();
    }

    @Override
    public ViewAttributes getAttributeProcessor() {
        return new RecyclerViewAttributes();
    }

    @Override
    public EventAttacher getEventAttacher() {
        return new RecyclerViewEventAttacher();
    }

    @Override
    public ValueGetter getValueGetter() {
        return new RecyclerViewGetter();
    }
}

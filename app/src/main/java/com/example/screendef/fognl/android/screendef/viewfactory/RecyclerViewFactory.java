package com.example.screendef.fognl.android.screendef.viewfactory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.screendef.fognl.android.screendef.ViewFactory;
import com.example.screendef.fognl.android.screendef.recycler.RecyclerViewWrapper;

import java.util.Map;

public class RecyclerViewFactory implements ViewFactory {
    @Override
    public View instantiateViewFrom(Context context, String type, Map<String, Object> attrs) {
        switch(type) {
            case "RecyclerView": return new RecyclerViewWrapper(context);

            default: {
                return null;
            }
        }
    }
}

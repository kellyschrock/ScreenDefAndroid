package com.example.screendef.fognl.android.screendef.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class RecyclerViewWrapper extends RecyclerView
    implements RecyclerAdapter.ItemListener {

    private RecyclerAdapter.ItemListener itemListener;
    private RecyclerItem selectedItem;

    public RecyclerViewWrapper(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setItemListener(RecyclerAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public void onItemSelected(int position, RecyclerItem item) {
        selectedItem = item;
        if(itemListener != null) itemListener.onItemSelected(position, item);
    }

    public RecyclerItem getSelectedItem() { return selectedItem; }
}

package com.fognl.android.screendef.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fognl.android.screendef.Values;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerItemHolder> {

    public interface ItemListener {
        void onItemSelected(int position, RecyclerItem item);
    }

    private final List<RecyclerItem> items = new ArrayList<>();
    private ItemListener itemListener;
    private Values itemStyle;

    public RecyclerAdapter() {
        super();
    }

    public RecyclerAdapter setItemStyle(Values style) {
        itemStyle = style;
        return this;
    }

    public RecyclerAdapter setListener(ItemListener listener) {
        this.itemListener = listener;
        return this;
    }

    public RecyclerAdapter addAll(List<RecyclerItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public RecyclerItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return RecyclerItemHolder.newInstance(viewGroup, itemStyle, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemHolder recyclerItemHolder, int position) {
        recyclerItemHolder.bind(position, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

package com.example.screendef.fognl.android.screendef.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.screendef.R;
import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewBuilder;
import com.example.screendef.fognl.android.screendef.attributes.TextViewAttributes;

public class RecyclerItemHolder extends RecyclerView.ViewHolder {
    static RecyclerItemHolder newInstance(ViewGroup parent, Values itemStyle, RecyclerAdapter.ItemListener listener) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_item, parent, false);
        return new RecyclerItemHolder(view, itemStyle, listener);
    }

    private final RecyclerAdapter.ItemListener itemListener;
    private int position;
    private RecyclerItem item;
    private final TextView title;
    private final TextView subtext;
    private final Values style;

    public RecyclerItemHolder(@NonNull View itemView, Values style, RecyclerAdapter.ItemListener listener) {
        super(itemView);
        itemListener = listener;
        this.style = style;

        title = itemView.findViewById(R.id.title);
        subtext = itemView.findViewById(R.id.subtext);

        if(style != null) {
            final Values titleStyle = style.getObject("title", null);
            final Values subStyle = style.getObject("subtext", null);

            if(titleStyle != null) {
                ViewBuilder.applyAttributes(itemView.getContext(), title, titleStyle);
            }

            if(subStyle != null) {
                ViewBuilder.applyAttributes(itemView.getContext(), subtext, subStyle);
            }
        }
    }

    void bind(int position, RecyclerItem item) {
        this.position = position;
        this.item = item;

        title.setText(item.title);
        subtext.setText(item.subtext);
    }
}

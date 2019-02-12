package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.screendef.fognl.android.screendef.SpinnerItem;
import com.example.screendef.fognl.android.screendef.Values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerAttributes extends ViewAttributes<Spinner> {
    private final Map<String, Applicator> applicators = new HashMap<>();

    public SpinnerAttributes() {
        super();

        applicators.put("items", new Applicator<Spinner>() {
            @Override
            public void apply(Context context, Spinner view, Values attrs, String name) {

                final List<Values> items = (List<Values>)attrs.get("items");
                if(items != null) {
                    final List<SpinnerItem> spinnerItems = new ArrayList<>();

                    for(Values item: items) {
                        spinnerItems.add(new SpinnerItem(item.get("id").toString(), item.get("text").toString()));
                    }

                    final ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(context,
                            android.R.layout.simple_spinner_item, spinnerItems);

                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    view.setAdapter(adapter);
                }
            }
        });

        applicators.put("selection", new Applicator<Spinner>() {
            @Override
            public void apply(Context context, Spinner view, Values attrs, String name) {
                final int sel = attrs.getInt(name, 0);
                view.setSelection(sel);
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }

    @Override
    public boolean appliesTo(View view) {
        return (view instanceof Spinner);
    }
}

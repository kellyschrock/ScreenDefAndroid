package com.example.screendef.fognl.android.screendef.attributes;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.screendef.fognl.android.screendef.SpinnerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerAttributes extends ViewAttributes<Spinner> {
    public static boolean appliesTo(View view) {
        return (view instanceof Spinner);
    }

    private final Map<String, Applicator> applicators = new HashMap<>();

    public SpinnerAttributes() {
        super();

        applicators.put("items", new Applicator<Spinner>() {
            @Override
            public void apply(Context context, Spinner view, Map<String, Object> attrs, Object value) {

                final List<Map<String, Object>> items = (List<Map<String, Object>>)attrs.get("items");
                if(items != null) {
                    final List<SpinnerItem> spinnerItems = new ArrayList<>();

                    for(Map<String, Object> item: items) {
                        spinnerItems.add(new SpinnerItem(item.get("id").toString(), item.get("text").toString()));
                    }

                    final ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(context,
                            android.R.layout.simple_spinner_item, spinnerItems);

                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    view.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public Map<String, Applicator> getApplicators() {
        return applicators;
    }
}

package com.example.screendef.fognl.android.screendef.spinner;

public class SpinnerItem {
    private final String id;
    private final String text;

    public SpinnerItem(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() { return id; }
    public String getText() { return text; }

    @Override
    public String toString() { return text; }
}

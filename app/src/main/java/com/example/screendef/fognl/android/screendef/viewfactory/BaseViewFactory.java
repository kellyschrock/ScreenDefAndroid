package com.example.screendef.fognl.android.screendef.viewfactory;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.screendef.fognl.android.screendef.ViewFactory;

import java.util.Map;

public class BaseViewFactory implements ViewFactory {
    static final String TAG = BaseViewFactory.class.getSimpleName();

    @Override
    public View instantiateViewFrom(Context context, String type, Map<String, Object> attrs) {
        switch (type) {
            case "Spinner": return new Spinner(context);
            case "TextView": return new TextView(context);
            case "EditText": return new EditText(context);
            case "ProgressBar": return new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            case "SeekBar": return new SeekBar(context);
            case "Button": return new Button(context);
            case "RadioGroup": return new RadioGroup(context);
            case "RadioButton": return new RadioButton(context);
            case "CheckBox": return new CheckBox(context);
            case "FrameLayout": return new FrameLayout(context);
            case "LinearLayout": return new LinearLayout(context);
            case "RelativeLayout": return new RelativeLayout(context);
            case "ImageButton": return new ImageButton(context);
            case "ImageView": return new ImageView(context);

            default: {
                Log.w(TAG, "unknown type " + type);
                return null;
            }
        }
    }
}

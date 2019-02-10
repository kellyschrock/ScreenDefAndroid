package com.example.screendef.fognl.android.screendef.viewfactory;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.screendef.fognl.android.screendef.ViewFactory;

import java.util.Map;

public class BaseViewFactory implements ViewFactory {
    static final String TAG = BaseViewFactory.class.getSimpleName();

    private final Context mContext;
    public BaseViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public View instantiateViewFrom(String type, Map<String, Object> attrs) {
        switch (type) {
            case "TextView": return new TextView(mContext);
            case "EditText": return new EditText(mContext);
            case "SeekBar": return new SeekBar(mContext);
            case "Button": return new Button(mContext);
            case "RadioGroup": return new RadioGroup(mContext);
            case "RadioButton": return new RadioButton(mContext);
            case "CheckBox": return new CheckBox(mContext);
            case "FrameLayout": return new FrameLayout(mContext);
            case "LinearLayout": return new LinearLayout(mContext);
            case "RelativeLayout": return new RelativeLayout(mContext);

            default: {
                Log.w(TAG, "unknown type " + type);
                return null;
            }
        }
    }
}

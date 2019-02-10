package com.example.screendef;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.screendef.fognl.android.screendef.ViewBuilder;
import com.example.screendef.fognl.android.screendef.ViewDef;
import com.example.screendef.fognl.android.screendef.ViewFactory;
import com.example.screendef.fognl.android.screendef.util.Streams;
import com.example.screendef.fognl.android.screendef.viewfactory.BaseViewFactory;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static final String TAG = MainActivity.class.getSimpleName();

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_test: {
                    doTest();
                    break;
                }
            }
        }
    };

    FrameLayout containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerView = findViewById(R.id.container);

        checkFileReadPermissions();

        for(int id: new int[] {
                R.id.btn_test
        }) {
            findViewById(id).setOnClickListener(mClickListener);
        }
    }

    void checkFileReadPermissions() {
        int permissionCheck = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Const.SDCARD_PERMISSIONS,
                    Const.REQ_READ_PERMISSIONS);
        }
    }

    void doTest() {
        try {
            final File file = new File(Const.BASE_DIR, "basic.json");
            final String content = Streams.copyAndClose(new FileInputStream(file), new ByteArrayOutputStream()).toString();
            final JSONObject jo = new JSONObject(content);

            final ViewDef def = ViewDef.populate(new ViewDef(), jo);
            Log.v(TAG, "def=" + def);

            final ViewBuilder builder = new ViewBuilder(getApplicationContext(), new BaseViewFactory(getApplicationContext()));

            final View view = builder.buildViewFrom(def);
            if(view != null) {
                containerView.removeAllViews();
                containerView.addView(view, def.getLayoutParams(view, containerView));
            }


        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

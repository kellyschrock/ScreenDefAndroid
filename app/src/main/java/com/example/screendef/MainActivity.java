package com.example.screendef;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.screendef.fognl.android.screendef.Values;
import com.example.screendef.fognl.android.screendef.ViewBuilder;
import com.example.screendef.fognl.android.screendef.ViewDef;
import com.example.screendef.fognl.android.screendef.attributes.RecyclerViewAttributes;
import com.example.screendef.fognl.android.screendef.util.Streams;
import com.example.screendef.fognl.android.screendef.viewfactory.RecyclerViewFactory;

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

                case R.id.btn_update_values: {
                    doUpdateValuesTest();
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

        ViewBuilder
                .init(getApplicationContext())
                .addViewFactory(new RecyclerViewFactory())
                .addAttributeProcessor(new RecyclerViewAttributes())
                ;

        checkFileReadPermissions();

        for(int id: new int[] {
                R.id.btn_test, R.id.btn_update_values
        }) {
            findViewById(id).setOnClickListener(mClickListener);
        }

        findViewById(R.id.btn_update_values).setEnabled(false);
    }

    void checkFileReadPermissions() {
        int permissionCheck = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Const.SDCARD_PERMISSIONS,
                    Const.REQ_READ_PERMISSIONS);
        }
    }

    private Map<String, View> mViewIds = null;

    void doTest() {
        try {
            final File file = new File(Const.BASE_DIR, "basic.json");
            final String content = Streams.copyAndClose(new FileInputStream(file), new ByteArrayOutputStream()).toString();
            final JSONObject jo = new JSONObject(content);

            final ViewDef def = ViewDef.populate(new ViewDef(), jo);
            Log.v(TAG, "def=" + def);

            final ViewBuilder.BuildResult buildResult = ViewBuilder.get().buildViewFrom(this, def);
            if(buildResult != null) {
                containerView.removeAllViews();
                final View view = buildResult.getView();
                containerView.addView(view, def.getLayoutParams(view, containerView));

                mViewIds = buildResult.getViewIds();
                Log.v(TAG, String.format("viewIds=%s", buildResult.getViewIds()));
                findViewById(R.id.btn_update_values).setEnabled(mViewIds != null);
            }


        } catch(Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void doUpdateValuesTest() {
        final Values seeker = new Values();
        seeker.put("progress", 90);

        final Values my_button = new Values();
        my_button.put("text", "I just updated");
        my_button.put("textColor", "white");

        final Values edit_name = new Values();
        edit_name.put("hint", "Your name here");
        edit_name.put("textColor", "#ff00ff");

        final Values map = new Values();
        map.put("my_button", my_button);
        map.put("seeker", seeker);
        map.put("edit_name", edit_name);

        for(String key: map.keySet()) {
            final Values attrs = map.getObject(key, null);
            final View view = mViewIds.get(key);
            if(view != null) {
                ViewBuilder.get().applyAttributes(this, view, attrs);
            }
        }
    }
}

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

import com.fognl.android.screendef.Values;
import com.fognl.android.screendef.ViewBuilder;
import com.fognl.android.screendef.ViewDef;
import com.fognl.android.screendef.recycler.RecyclerViewAttributes;
import com.fognl.android.screendef.recycler.RecyclerViewEventAttacher;
import com.fognl.android.screendef.events.ViewEventListener;
import com.fognl.android.screendef.recycler.RecyclerViewModule;
import com.fognl.android.screendef.util.Streams;
import com.fognl.android.screendef.values.RecyclerViewGetter;
import com.fognl.android.screendef.viewfactory.RecyclerViewFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

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
                .addModule(new RecyclerViewModule())
                .addViewEventListener(new ViewEventListener() {
                    @Override
                    public void onViewEvent(String screenId, String viewId, String event, Values data) {
                        Log.v(TAG, String.format("onViewEvent(%s, %s, %s, %s)", screenId, viewId, event, data));

                        // Decide what to do based on what was messed with
                        switch(viewId) {
                            case "my_button": {
                                switch(event) {
                                    case "on_click": {
                                        final Values body = ViewBuilder.get().makeMessageBody(mBuildResult);
                                        Log.v(TAG, String.format("body=%s", body));

                                        try {
                                            final JSONObject jo = Values.populate(new JSONObject(), body);
                                            Log.v(TAG, String.format("jo=%s", jo));
                                        } catch(JSONException ex) {
                                            Log.e(TAG, ex.getMessage(), ex);
                                        }

                                        break;
                                    }
                                }

                                break;
                            }
                        }
                    }
                })
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

    private ViewBuilder.BuildResult mBuildResult;

    void doTest() {
        try {
            // Get some data from an external source
            final File file = new File(Const.BASE_DIR, "basic.json");
            final String content = Streams.copyAndClose(new FileInputStream(file), new ByteArrayOutputStream()).toString();

            // Parse it into a ViewDef
            final JSONObject jo = new JSONObject(content);
            final ViewDef def = ViewDef.populate(new ViewDef(), jo);
//            Log.v(TAG, "def=" + def);

            mBuildResult = ViewBuilder.get().buildViewFrom(this, "my_screen", def);
            if(mBuildResult != null) {
                containerView.removeAllViews();
                final View view = mBuildResult.getView();
                containerView.addView(view, def.getLayoutParams(view, containerView));

                findViewById(R.id.btn_update_values).setEnabled(mBuildResult.hasViewIds());
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
        my_button.put("enabled", "false");

        final Values edit_name = new Values();
        edit_name.put("hint", "Your name here");
        edit_name.put("textColor", "#ff00ff");

        final Values map = new Values();
        map.put("my_button", my_button);
        map.put("seeker", seeker);
        map.put("edit_name", edit_name);

        for(String key: mBuildResult.getViewIds().keySet()) {
            final Values attrs = map.getObject(key, null);
            final View view = mBuildResult.getViewIds().get(key);
            if(view != null && attrs != null) {
                ViewBuilder.get().applyAttributes(this, view, attrs);
            }
        }
    }
}

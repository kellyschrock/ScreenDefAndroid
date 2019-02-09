package com.example.screendef;

import android.Manifest;
import android.os.Environment;

import java.io.File;

public class Const {
    public static final File BASE_DIR = new File(Environment.getExternalStorageDirectory(), "ScreenDef");

    public static final int REQ_READ_PERMISSIONS = 1001;

    public static final String[] SDCARD_PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}

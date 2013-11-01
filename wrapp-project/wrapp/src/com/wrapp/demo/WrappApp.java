package com.wrapp.demo;

import android.app.ActivityManager;
import android.content.Context;

import com.webimageloader.ext.ImageLoaderApplication;

import java.io.File;

public class WrappApp extends ImageLoaderApplication {

    private static WrappApp sInstance;

    public static WrappApp get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = am.getMemoryClass();

        // Use part of the available memory for memory cache.
        final int memoryCacheSize = 1024 * 1024 * memClass / 8;

        File cacheDir = new File(getExternalCacheDir(), "images");
        getBuilder()
                .enableDiskCache(cacheDir, 10 * 1024 * 1024)
                .enableMemoryCache(memoryCacheSize).build();
    }

}

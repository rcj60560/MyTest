package com.luocj.mytest.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.ArrayMap;
import android.util.LruCache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.RequiresApi;

public class BitmapCache {

    private final ReferenceQueue<Bitmap> queue;
    private final ArrayMap<String, MySoftRef> hasRefs;
    private static BitmapCache cache;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private BitmapCache() {
        hasRefs = new ArrayMap<String, MySoftRef>();
        queue = new ReferenceQueue<>();
        Thread thread = new Thread();
        try {
            RandomAccessFile raf = new RandomAccessFile("name", "rwd");
            raf.setLength(1L);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class MySoftRef extends SoftReference<Bitmap> {
        public MySoftRef(Bitmap referent, ReferenceQueue<? super Bitmap> q) {
            super(referent, q);
            int maxMemory = (int) (Runtime.getRuntime().totalMemory() / 8);
            LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(maxMemory) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight() / 1024;
                }
            };
        }
    }

    public static BitmapCache getInstance() {
        if (cache == null) {
            cache = new BitmapCache();
        }
        return cache;
    }
}

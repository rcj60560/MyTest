package com.luocj.mytest;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//        System.out.println("-=---------------->");
////        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
////        HashMap<String, String> map = new HashMap<>();
////        ArrayList<String> strings = new ArrayList<>();
////
////        String str = "你好hello";
////        int byte_len = str.getBytes().length;
////        int len = str.length();
////        System.out.println("字节长度为：" + byte_len);
////        System.out.println("字符长度为：" + len);
////        System.out.println("系统默认编码方式：" + System.getProperty("file.encoding"));
//
//
//        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, true);
//        map.put(0, 0);
//        map.put(1, 1);
//        map.put(2, 2);
//        map.put(3, 3);
//        map.put(4, 4);
//        map.put(5, 5);
//        map.put(6, 6);
//        map.get(1);
//        map.get(2);
//
//        long totalMemory = Runtime.getRuntime().totalMemory();
//        int maxSize = (int) (totalMemory / 8);
//
//        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(maxSize) {
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                return value.getRowBytes() * value.getHeight() / 1024;
//            }
//        };
//
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//
//        }
//
//        Handler handler = new Handler();

        int a  = 100;
        int b = 200;

        change(a,b);

        System.out.println("a:" + a);
        System.out.println("b:" + b);
    }

    public void change(Object t1, Object t2) {
        Object t = t1;
        t1 = t2;
        t2 = t;

    }
}
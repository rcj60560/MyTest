package com.luocj.mytest.activity.bitmap;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.luocj.mytest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class BitmapYouHuaActivity5 extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BitmapYouHuaActivity5.class.getSimpleName();
    private ImageView imageview1;
    private ImageView imageview2;
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap5);
        imageview1 = findViewById(R.id.iv1);
        imageview2 = findViewById(R.id.iv2);

        LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(9);
        LinkedHashMap<Object, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();


        Bitmap bitmap = BitmapFactory.decodeStream(null);
        cache.put("string",bitmap);

        btn1 = findViewById(R.id.show1);
        btn2 = findViewById(R.id.show2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //显示原图
            case R.id.show1:
                AssetManager assets1 = getResources().getAssets();
                Bitmap oldBitmap = null;
                InputStream inputStream = null;
                try {
                    inputStream = assets1.open("pic_5m.png");
                    oldBitmap = BitmapFactory.decodeStream(inputStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onClick: " + oldBitmap.getWidth());
                imageview1.setImageBitmap(oldBitmap);
                break;
            //显示压缩之后的图片
            case R.id.show2:
                AssetManager assets = getResources().getAssets();
                Bitmap bitmap = null;

                //保存图片到指定的文件夹
                String parentPath = Environment.getExternalStorageDirectory() + File.separator + "abitmap" + File.separator;
                String childName = "new_bitmap.png";
                File file = generateFilePath(parentPath);
                File picFile = new File(file, childName);
                try {
                    InputStream is = assets.open("pic_5m.png");
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = 8;
                    Log.i(TAG, "onClick: " + options.outWidth);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(is, null, options);

                    try {
                        if (!picFile.exists()) {
                            picFile.createNewFile();
                        }
                        FileOutputStream out = new FileOutputStream(picFile.getAbsolutePath());
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onClick: " + bitmap.getWidth());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                int exifOrientation = getExifOrientation(picFile.getAbsolutePath());
//                Log.i(TAG, "onClick: " + exifOrientation);
//                Bitmap bitmap1 = rotateBitmap(bitmap, picFile.getAbsolutePath());
//
                imageview2.setImageBitmap(bitmap);
                
                break;
            default:
                break;
        }
    }

    private static File generateFilePath(String parentPath) {
        File file = null;
        try {
            file = new File(parentPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取图片旋转角度
     */
    //判断图片的旋转角度
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            //Log.e("---->", ex.getMessage());
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 旋转图片
     */
    public static Bitmap rotateBitmap(Bitmap b, String filepath) {
        int degrees = getExifOrientation(filepath);
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {

            }
        }
        return b;
    }
}

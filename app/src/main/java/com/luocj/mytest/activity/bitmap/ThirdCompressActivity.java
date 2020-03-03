package com.luocj.mytest.activity.bitmap;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.luocj.mytest.R;
import com.luocj.mytest.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdCompressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ThirdCompressActivity.class.getSimpleName();
    private ImageView iv1;
    private ImageView iv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_compress);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
//                Bitmap bitmap = getPicFromAssets("testpic.png");
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inScaled = false;
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic, options);


//                Log.i(TAG, "onClick: width :" + bitmap.getWidth() +
//                        ",hethgt:" + bitmap.getHeight() +
//                        ",countByte :" + bitmap.getByteCount());

//                AssetManager assetManager = getAssets();

//                Bitmap bitmap= null;
//                try {
//                    InputStream inputStream = assetManager.open("testpic.png");
//                    Bitmap sourceBitmap = BitmapFactory.decodeStream(inputStream);
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;
//                    options.inScaled = false;
//
//                    Log.i(TAG, "onClick: width :" + sourceBitmap.getWidth() +
//                            ",hethgt:" + sourceBitmap.getHeight() +
//                            ",countByte :" + sourceBitmap.getByteCount());
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                BitmapAsytask bitmapAsytask = new BitmapAsytask();
                bitmapAsytask.execute();

                break;

            case R.id.btn2:
                Bitmap newbtp = decodeFileFromResore(getResources(), R.mipmap.pic);
                Log.i(TAG, "onClick: width :" + newbtp.getWidth() +
                        ",hethgt:" + newbtp.getHeight() +
                        ",countByte :" + newbtp.getByteCount());
                iv2.setImageBitmap(newbtp);
                break;

            case R.id.btn3:
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(externalStorageDirectory, "aaaa");

                if (!file.exists()) {
                    file.mkdirs();
                }

                File file1 = new File(file, "testpic.png");
                if (!file1.exists()) {
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private Bitmap getPicFromAssets(String s) {
        Bitmap bitmap = null;
        AssetManager am = getAssets();
        try {
            InputStream is = am.open(s);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return bitmap;
    }

    private Bitmap decodeFileFromResore(Resources resources, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = getInsimpleSize(id);
        options.inJustDecodeBounds = false;
        Bitmap newBitmap = BitmapFactory.decodeResource(resources, id, options);

        return newBitmap;
    }

    private int getInsimpleSize(int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        int byteCount = bitmap.getByteCount();
        int insimpleSize = (int) Math.sqrt(byteCount / 500 / 1024);

        Log.i(TAG, "getInsimpleSize:  " + insimpleSize);
        return insimpleSize;
    }

    public class BitmapAsytask extends AsyncTask<Void, Integer, Bitmap> {

        private InputStream inputStream;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute: ");
            AssetManager assetManager = getAssets();
            try {
                inputStream = assetManager.open("testpic.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground: ");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inScaled = false;
            BitmapFactory.decodeStream(inputStream, null, options);
            int insimpleSize = 1;
            int width = options.outWidth;
            int height = options.outHeight;
            while ((width * height) / 1024 / (insimpleSize * insimpleSize) * 1.f > 500) {
                insimpleSize *= 2;
                Log.i(TAG, "doInBackground: " + insimpleSize);
            }
            options.inSampleSize = insimpleSize;
            options.inJustDecodeBounds = false;
            AssetManager assetManager = getAssets();
            Bitmap resultBitmap = null;

            try {
                InputStream inputStream1 = assetManager.open("testpic.png");
                resultBitmap = BitmapFactory.decodeStream(inputStream1, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: ");
            return resultBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.i(TAG, "onPostExecute: ");
            iv1.setImageBitmap(bitmap);
        }
    }
}

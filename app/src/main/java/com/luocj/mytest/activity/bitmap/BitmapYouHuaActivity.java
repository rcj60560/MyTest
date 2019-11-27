package com.luocj.mytest.activity.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luocj.mytest.R;

import java.io.ByteArrayOutputStream;

public class BitmapYouHuaActivity extends
        AppCompatActivity implements View.OnClickListener {
    private static final String TAG = BitmapYouHuaActivity.class.getSimpleName();
    private ImageView iv1;
    private ImageView iv2;
    private TextView tvBtm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhua_bitmap);
        initView();
    }

    private void initView() {
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);

        findViewById(R.id.btn_get_info).setOnClickListener(this);
        tvBtm = findViewById(R.id.tv_btm);


        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                iv1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.mipmap.pic, 200, 200));
//                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.pic);
//                int byteCount = bm.getByteCount();
//                iv1.setImageBitmap(bm);
                break;

            case R.id.btn2:
                iv2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pic));
                break;

            case R.id.btn_get_info:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic);
                int byteCount1 = bitmap.getByteCount();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                tvBtm.setText("大小:" + byteCount1 + ",width : " + width + ",height:" + height);
                break;
            //质量压缩
            case R.id.btn3:
                Bitmap btp3 = BitmapFactory.decodeResource(getResources(), R.mipmap.pic);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                btp3.compress(Bitmap.CompressFormat.JPEG, 20, bos);

                byte[] bytes = bos.toByteArray();
                Bitmap resultBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                iv1.setImageBitmap(resultBitmap);
                break;
        }
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth,
                                            int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        Log.i(TAG, "calculateInSampleSize: " + inSampleSize);
        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId,
                                                         int reqWidth,
                                                         int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap1 = BitmapFactory.decodeResource(res, resId, options);
        return bitmap1;
    }
}

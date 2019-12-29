package com.luocj.mytest.activity.bitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luocj.mytest.R;
import com.luocj.mytest.utils.BitmapUtil;
import com.luocj.mytest.utils.FileUtil;
import com.luocj.mytest.utils.ImageUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

public class BitmapCompressActivity extends AppCompatActivity {
    private static final String TAG = BitmapCompressActivity.class.getSimpleName();
    private static final int FLAG_CHOOSE_IMG = 1001;
    private ImageView ivOld;
    private ImageView ivNew;
    private TextView tvOld;
    private TextView tvNew;
    private File newFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_compress);
        initPermission();
        initView();

    }

    private void initView() {
        ivOld = findViewById(R.id.iv_old);
        ivNew = findViewById(R.id.iv_new);

        tvOld = findViewById(R.id.tv_old);
        tvNew = findViewById(R.id.tv_new);

    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onAction: " + " granted ");
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onAction: " + " denied ");
                    }
                })
                .start();
    }


    /**
     * 加载图片
     *
     * @param view
     */
    public void btn1(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, FLAG_CHOOSE_IMG);
    }

    /**
     * 压缩图片
     *
     * @param view
     */

    public void btn2(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
            if (data == null) return;
            try {
                File oldFile = FileUtil.getTempFile(this, data.getData());
                Log.i(TAG, "onActivityResult: oldFile---path:" + oldFile.getPath());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(oldFile.getAbsolutePath(), options);
                options.inSampleSize = getInSampleSize(options, 800, 500);
                options.inJustDecodeBounds = false;
                Bitmap oldBitmap = BitmapFactory.decodeFile(oldFile.getAbsolutePath(), options);

                //将压缩的图片保存到手机
                newFile = saveBitmap(oldBitmap);

                ivOld.setImageBitmap(BitmapFactory.decodeFile(oldFile.getAbsolutePath()));
                tvOld.setText(String.format("Size : %s", getReadableFileSize(oldFile.length())));

                ivNew.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
                tvNew.setText(String.format("Size : %s", getReadableFileSize(newFile.length())));

                Log.i(TAG, "onActivityResult: ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File saveBitmap(Bitmap oldBitmap) {
        String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + File.separator + "Camera/";
        File newFile = new File(savePath, "newFile.jpg");
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            Log.i(TAG, "saveBitmap: " + savePath);

            FileOutputStream out = null;
            out = new FileOutputStream(newFile);
            oldBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "saveBitmap: " + newFile.getPath());
        return newFile;
    }

    private int getInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
        Log.i(TAG, "getInSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    private Object getReadableFileSize(long length) {
        if (length <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(length) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(length / Math.pow(1024, digitGroups)) + " " +
                units[digitGroups];
    }
}

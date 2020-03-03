package com.luocj.mytest.activity.project;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luocj.mytest.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProjectActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHOOSE = 0x01;
    private static final String TAG = ProjectActivity.class.getSimpleName();
    private List<Uri> mSelected;
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        iv = findViewById(R.id.iv);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)
                .onGranted(permission -> {
                    Log.i(TAG, "initPermission: ");
                })
                .onDenied(data -> {
                    Log.i(TAG, "initPermission: ");
                })
                .rationale((context, data, executor) -> {
                    Log.i(TAG, "initPermission: ");
                })
                .start();

        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader()).build());
    }

    //拍照
    public void doTakePhoto(View view) {
        Album.camera(this)
                .image()
//                .filePath()
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Glide.with(ProjectActivity.this)
                                .load(result)
                                .into(iv);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {

                    }
                })
                .start();
    }

    //获取照片
    public void doTakePhoto1(View view) {
//        Matisse.from(this)
//                .choose(MimeType.allOf()) // 选择 mime 的类型
//                .countable(true)
//                .maxSelectable(9) // 图片选择的最多数量
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f) // 缩略图的比例
//                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
//                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(1)
//                .checkedList(mAlbumFiles)
//                .filterSize() // Filter the file size.
//                .filterMimeType() // Filter file format.
                .afterFilterVisibility(true) // Show the filtered files, but they are not available.
                .onResult(result -> {
                    Log.i(TAG, "onAction: 1");
                    Glide.with(ProjectActivity.this)
                            .load(result.get(0).getPath())
                            .into(iv);
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Log.i(TAG, "onAction: " + result);
                    }
                })
                .start();
    }

    private class MediaLoader implements AlbumLoader {
        @Override
        public void load(ImageView imageView, AlbumFile albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(imageView);

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mSelected = Matisse.obtainResult(data);
//
//            Glide.with(this)
//                    .load(mSelected.get(0))
//                    .into(iv);
//            Log.d("Matisse", "mSelected: " + mSelected);
//
//            iv.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    int width = iv.getWidth();
//                    int height = iv.getHeight();
//                    Log.i(TAG, "run: " + width + ", :" + height);
//                }
//            },2000);
//        }
//    }
}

package com.luocj.mytest.activity.net;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    private static final String TAG = DownloadTask.class.getSimpleName();

    public static final int TYPE_SUCCESS = 0;

    public static final int TYPE_FAILED = 1;

    public static final int TYPE_PAUSED = 2;

    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled = false;

    private boolean isPaused = false;

    private int lastProgress;


    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        long downloadLength = 0;
        String downloadUrl = strings[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        //下载文件存放的目录
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        //创建一个文件
        file = new File(directory + fileName);

        if (file.exists()) {
            downloadLength = file.length();
        }

        long contentLength = getContentLength(downloadUrl);
        if (contentLength == 0) {
            return TYPE_FAILED;
        } else if (contentLength == downloadLength) {
            return TYPE_SUCCESS;
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                .url(downloadUrl)
                .build();

        try {
            Response response = okHttpClient.newCall(request)
                    .execute();
            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadLength);
                byte[] b = new byte[1024];
                int total = 0;
                int len;

                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        saveFile.write(b, 0, len);
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (is != null) {
                    is.close();
                }

                if (saveFile != null) {
                    saveFile.close();
                }

                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return TYPE_FAILED;
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    private long getContentLength(String downloadUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(downloadUrl)
                .tag("haha")
                .build();

        try {
            Response response = okHttpClient.newCall(request)
                    .execute();
            return response.body().contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public DownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }
}

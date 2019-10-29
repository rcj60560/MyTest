package com.luocj.mytest.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.luocj.mytest.R;

public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {


    private static final String TAG = MyAsyncTask.class.getSimpleName();
    private final ProgressDialog progressDialog;

    public MyAsyncTask(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(R.style.progress_h);
        progressDialog.setMax(100);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int pro = 0;
        try {
            while (pro < 10) {
                publishProgress(pro);
                Thread.sleep(1000);
                pro++;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("开始下载了");
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Integer value = values[0];
        Log.i(TAG, "onProgressUpdate: " + value);
        progressDialog.setProgress(value);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        if (aBoolean) {
            ToastUtils.showShort("成功");
        } else {
            ToastUtils.showShort("失败");
        }
        Log.i(TAG, "onPostExecute: " + aBoolean);
    }
}

package com.luocj.net.call;

import com.luocj.net.request.Request;
import com.luocj.net.response.Response;
import com.luocj.net.utils.OkLogger;

public abstract class AbsCallback<T> implements Callback<T> {
    @Override
    public void onStart(Request<T, ? extends Request> request) {
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }

    @Override
    public void onFinish() {
    }

//    @Override
//    public void uploadProgress(Progress progress) {
//    }
//
//    @Override
//    public void downloadProgress(Progress progress) {
//    }
}

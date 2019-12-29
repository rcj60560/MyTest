package com.luocj.net.request;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

public class CommonRequest {
    public static Request creatGetRequest(String url, RequestParams params) {
        return creatGetRequest(url, params, null);
    }

    private static Request creatGetRequest(String url, RequestParams params, RequestParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }

        Headers mHeader = mHeaderBuild.build();
        return new Request.Builder().get()
                .url(url)
                .headers(mHeader)
                .build();
    }
}

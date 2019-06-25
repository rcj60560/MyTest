package com.luocj.net.request;

import com.luocj.net.utils.HttpUtils;

import okhttp3.RequestBody;

public class GetRequest<T, R> extends Request<T, R> {


    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    public okhttp3.Request generateRequest(RequestBody requestBody) {
        url = HttpUtils.createUrlFromParams(baseUrl, params.urlParamsMap);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request.Builder requestBuilder = HttpUtils.appendHeaders(builder, headers);
        return requestBuilder.get().url(url).tag(tag).build();
    }


}

package com.luocj.net.request;

import com.luocj.net.Net;
import com.luocj.net.call.Call;
import com.luocj.net.call.Callback;
import com.luocj.net.model.HttpHeaders;
import com.luocj.net.model.HttpParams;
import com.luocj.net.utils.HttpUtils;

import java.io.Serializable;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class Request<T, R> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;
    protected String url;
    protected String baseUrl;
    protected transient OkHttpClient client;
    protected transient Object tag;
    protected transient okhttp3.Request mRequest;
    protected transient Call<T> call;
    protected transient Callback<T> callback;
    protected HttpParams params = new HttpParams();     //添加的param
    protected HttpHeaders headers = new HttpHeaders();  //添加的header

    public Request(String url) {
        this.url = url;
        baseUrl = url;
        Net net = Net.getInstance();

        if (net.getCommonsParams() != null) params(net.getCommonsParams());
    }

    /**
     * 根据不同的请求方式和参数，生成不同的RequestBody
     */
    protected abstract RequestBody generateRequestBody();

    /**
     * 根据不同的请求方式，将RequestBody转换成Request对象
     */
    public abstract okhttp3.Request generateRequest(RequestBody requestBody);

    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");
        this.callback = callback;
        call.execute(callback);
    }
}

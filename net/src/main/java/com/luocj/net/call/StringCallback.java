package com.luocj.net.call;


import com.luocj.net.utils.StringConvert;

import okhttp3.Response;

public abstract class StringCallback extends AbsCallback<String> {
    private StringConvert stringConvert;

    public StringCallback() {
        stringConvert = new StringConvert();
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String s = stringConvert.convertResponse(response);
        response.close();
        return s;
    }
}

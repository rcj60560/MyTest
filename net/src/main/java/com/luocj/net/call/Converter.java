package com.luocj.net.call;


import okhttp3.Response;

interface Converter<T> {

    T convertResponse(Response response) throws Throwable;
}

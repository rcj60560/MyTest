package com.luocj.mytest.activity.net;

import com.luocj.mytest.model.ArticleModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
//import rx.Observable;

public interface API {

    //https://wanandroid.com/wxarticle/chapters/json
    @GET("/wxarticle/chapters/json")
    Call<ArticleModel> getWXarticle();

    @GET("/wxarticle/chapters/json")
    Observable<ArticleModel> getWXarticleOb();

    @GET("/user/insert")
    Call<Object> getSave();

}

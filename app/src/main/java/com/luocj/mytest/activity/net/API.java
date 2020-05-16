package com.luocj.mytest.activity.net;

import com.luocj.mytest.model.ArticleModel;
import com.luocj.mytest.model.TestModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
//import rx.Observable;

public interface API {

    //https://wanandroid.com/wxarticle/chapters/json
    @GET("/wxarticle/chapters/json")
    Call<ArticleModel> getWXarticle();

    @GET("/wxarticle/chapters/json")
    Observable<ArticleModel> getWXarticleOb();

    @GET("/user/insert")
    Call<Object> getSave();

    @GET("/article/list/{page}/json")
    Call<TestModel> getDataList(@Path("page") String page);

}

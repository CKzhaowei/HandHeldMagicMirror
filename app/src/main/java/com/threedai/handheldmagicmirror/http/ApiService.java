package com.threedai.handheldmagicmirror.http;

import com.threedai.handheldmagicmirror.mvvm.model.bean.result.NewsResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {

    /**
     * 测试：获取公众号接口
     * @return
     */
    @FormUrlEncoded
    @POST("selfnews/newslist")
    Observable<HttpResult<NewsResult>> getNewsList(@Field("type") int type);

//    /**
//     * 测试：获取公众号接口
//     * @return
//     */
//    @GET("selfnews/newslist")
//    Observable<HttpResult<NewsResult>> getNewsList(@Query("type")int type);
}

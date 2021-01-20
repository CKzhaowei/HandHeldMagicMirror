package com.threedai.handheldmagicmirror.mvvm.model;

import com.threedai.handheldmagicmirror.mvvm.model.bean.result.NewsResult;
import com.threedai.handheldmagicmirror.http.ApiService;
import com.threedai.handheldmagicmirror.http.HttpResult;
import com.threedai.handheldmagicmirror.manager.RetrofitManager;

import io.reactivex.Observable;

/**
 * http model层实现类，加载http请求数据
 */
public class HttpModelImpl implements IHttpModel {

    private ApiService mApiService;

    private volatile static HttpModelImpl INSTANCE = null;

    private HttpModelImpl(){
        mApiService = RetrofitManager.getInstance().getApiService();
    }

    public static HttpModelImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpModelImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpModelImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<HttpResult<NewsResult>> getNewsList(int type) {
        return mApiService.getNewsList(type);
    }
}

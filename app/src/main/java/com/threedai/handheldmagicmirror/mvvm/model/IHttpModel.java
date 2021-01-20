package com.threedai.handheldmagicmirror.mvvm.model;

import com.threedai.handheldmagicmirror.mvvm.model.bean.result.NewsResult;
import com.threedai.handheldmagicmirror.http.HttpResult;
import io.reactivex.Observable;

/**
 * http model层
 */
public interface IHttpModel {

    Observable<HttpResult<NewsResult>> getNewsList(int type);
}

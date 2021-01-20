package com.threedai.handheldmagicmirror.mvvm.model;

import androidx.annotation.NonNull;
import com.threedai.handheldmagicmirror.mvvm.model.bean.result.NewsResult;
import com.threedai.handheldmagicmirror.http.HttpResult;

import io.reactivex.Observable;

/**
 * 数据仓库类，全局唯一。提供获取http和本地数据的接口
 */
public class DataRepository implements IHttpModel, ILocalModel {

    private volatile static DataRepository INSTANCE = null;
    private IHttpModel mIHttpModel;
    private ILocalModel mILocalModel;

    private DataRepository(@NonNull IHttpModel httpDataSource, @NonNull ILocalModel localDataSource) {
        this.mIHttpModel = httpDataSource;
        this.mILocalModel = localDataSource;
    }

    public static DataRepository getInstance(@NonNull IHttpModel httpDataSource, @NonNull ILocalModel localDataSource) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<HttpResult<NewsResult>> getNewsList(int type) {
        return mIHttpModel.getNewsList(type);
    }
}

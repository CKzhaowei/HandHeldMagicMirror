package com.threedai.handheldmagicmirror.app;

import com.threedai.handheldmagicmirror.mvvm.model.DataRepository;
import com.threedai.handheldmagicmirror.mvvm.model.HttpModelImpl;
import com.threedai.handheldmagicmirror.mvvm.model.IHttpModel;
import com.threedai.handheldmagicmirror.mvvm.model.ILocalModel;
import com.threedai.handheldmagicmirror.mvvm.model.LocalModelImpl;

/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。
 */
public class Injection {

    /**
     * 提供全局唯一的数据仓库实例
     * @return
     */
    public static DataRepository provideDataRepository() {
        //网络数据源
        IHttpModel httpDataSource = HttpModelImpl.getInstance();
        //本地数据源
        ILocalModel localDataSource = LocalModelImpl.getInstance();
        //两条分支组成一个数据仓库
        return DataRepository.getInstance(httpDataSource, localDataSource);
    }
}

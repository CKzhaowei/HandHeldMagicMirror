package com.threedai.handheldmagicmirror.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.threedai.handheldmagicmirror.mvvm.model.DataRepository;
import com.threedai.handheldmagicmirror.app.Injection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * VM层的公共基类
 */
public class BaseViewModel extends ViewModel implements IViewModel {

    protected final String TAG = this.getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    protected DataRepository mModel;
    private UIChangeLiveData mUIChangeLiveData;

    public BaseViewModel() {
        onStart();
    }


    @Override
    public void onStart() {
        this.mModel = Injection.provideDataRepository();
    }

    @Override
    public void onCleared() {
        unDispose();//解除订阅
    }

    /**
     * 获取ui切换的LiveData
     * @return
     */
    public UIChangeLiveData getUIChangeLiveData() {
        if (mUIChangeLiveData == null) {
            mUIChangeLiveData = new UIChangeLiveData();
        }
        return mUIChangeLiveData;
    }

    /**
     * 显示loading
     */
    public void showDialog() {
        mUIChangeLiveData.showDialog.setValue(null);
    }


    /**
     * 隐藏loading
     */
    public void hideDialog() {
        mUIChangeLiveData.hideDialog.setValue(null);
    }

    /**
     * 显示消息
     * @param msg 消息
     */
    public void showMsg(String msg){
        mUIChangeLiveData.showMsg.setValue(msg);
    }

    /**
     * 获取拼接的输出消息
     */
    protected String getOutputMsg(int code, String msg) {
        return "状态码:" + code + " 信息:" + msg;
    }

    /**
     * 停止正在执行的 RxJava 任务,避免内存泄漏
     */
    protected void addDispose(DisposableObserver disposableObserver) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposableObserver);//将所有 Disposable 放入容器集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    protected void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
        this.mCompositeDisposable = null;
    }

    /**
     * 封装ui切换的事件
     */
    public final class UIChangeLiveData extends MutableLiveData {

        private MutableLiveData<Void> showDialog;
        private MutableLiveData<Void> hideDialog;
        private MutableLiveData<String> showMsg;


        public MutableLiveData<Void> getShowDialog() {
            if(showDialog == null){
                showDialog = new MutableLiveData<>();
            }
            return showDialog;
        }

        public MutableLiveData<Void> getHideDialog() {
            if(hideDialog == null){
                hideDialog = new MutableLiveData<>();
            }
            return hideDialog;
        }

        public MutableLiveData<String> getShowMsg() {
            if(showMsg == null){
                showMsg = new MutableLiveData<>();
            }
            return showMsg;
        }
    }

}

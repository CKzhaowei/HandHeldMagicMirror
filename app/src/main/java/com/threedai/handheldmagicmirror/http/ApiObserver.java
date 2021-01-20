package com.threedai.handheldmagicmirror.http;

import android.net.ParseException;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 被观察者类  在这里rxjava请求网络对数据进行统一处理
 */
public abstract class ApiObserver<M> extends DisposableObserver<HttpResult<M>> {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 自定义统一的错误码
     */
    private final static int ERROR_CODE = -1;

    /**
     * 自定义统一的成功码，这里根据后台返回成功的code作为判断的标准，比如code=0是成功，有的是code=200是成功。
     */
    private final static int SUCCESS_CODE = 0;

    /**
     * 成功的回调
     * @param model 返回的数据
     */
    public abstract void onSuccess(M model);

    /**
     * 失败的回调
     * @param code 返回码
     * @param msg 返回的消息
     */
    public abstract void onFailure(int code,String msg);


    @Override
    protected void onStart() {
        super.onStart();
        //接下来可以检查网络连接等操作
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("当前网络不可用，请检查网络情况");
            onFailure(ERROR_CODE, "当前网络不可用，请检查网络情况");
        }
    }

    @Override
    public void onNext(HttpResult<M> mHttpResult) {
        if (mHttpResult.getCode() == SUCCESS_CODE) {
            if(mHttpResult.getData()!=null) {
                onSuccess(mHttpResult.getData());
            }
        } else {
            onFailure(mHttpResult.getCode(),mHttpResult.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        //请求失败,判断是不是网络异常,还是服务器异常
        LogUtils.e(TAG,"异常:"+e.getMessage());
        String msg = getCovertStatusMsg(e);
        onFailure(ERROR_CODE,msg);
        //请求失败后取消Disposable,防止内存泄漏
        if (!isDisposed()) {
            dispose();
        }
    }

    @Override
    public void onComplete() {
        //请求成功后取消Disposable,防止内存泄漏
        if(!isDisposed()) {
            dispose();
        }
    }

    /**
     * 获取自定义转换过的状态码
     * @param e
     * @return
     */
    private String getCovertStatusMsg(Throwable e){
        if(e!=null) {
            String msg = e.getMessage();
            if (e instanceof UnknownHostException) {
                msg = "网络连接错误，请检查网络";
            } else if (e instanceof SocketTimeoutException) {
                msg = "请求网络超时";
            } else if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                msg = getConvertHttpStatusCode(httpException);
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException) {
                msg = "数据解析错误";
            }
            return msg;
        }
        return "";
    }

    /**
     * 获取自定义转换http状态的code
     * @param httpException
     * @return
     */
    private String getConvertHttpStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}

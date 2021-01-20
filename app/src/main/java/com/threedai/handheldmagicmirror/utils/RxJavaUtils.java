package com.threedai.handheldmagicmirror.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * rxjava自定义封装类
 */
public final class RxJavaUtils {

    private RxJavaUtils(){}

    /**
     * 应用io和主线程的调度器
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyIoAndMainThreadScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 防止重复点击
     * @param second 秒数
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> preventRepeatedClicks(int second) {
        return upstream -> upstream.throttleFirst(second, TimeUnit.SECONDS);
    }

    /**
     * 实现重试延迟的类
     */
    public static class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

        public final String TAG = this.getClass().getSimpleName();
        private final int maxRetries;
        private final int retryDelaySecond;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelaySecond) {
            this.maxRetries = maxRetries;
            this.retryDelaySecond = retryDelaySecond;
        }

        @Override
        public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
            return throwableObservable
                    .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                        if (++retryCount <= maxRetries) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            Log.d(TAG, "Observable get error, it will try after " + retryDelaySecond
                                    + " second, retry count " + retryCount);
                            return Observable.timer(retryDelaySecond,
                                    TimeUnit.SECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    });
        }
    }
}

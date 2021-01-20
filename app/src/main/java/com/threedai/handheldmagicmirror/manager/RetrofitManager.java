package com.threedai.handheldmagicmirror.manager;

import com.threedai.handheldmagicmirror.BuildConfig;
import com.threedai.handheldmagicmirror.http.ApiService;
import com.threedai.handheldmagicmirror.http.interceptor.CommonParamsInterceptor;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit管理类
 */
public class RetrofitManager {

    private final static String BASE_URL = "https://www.jsanai.com/api/";
    private final static int DEFAULT_CONNECT_TIMEOUT = 60;//链接超时时间

    private Retrofit mRetrofit;
    private ApiService mApiService;

    private RetrofitManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (!BuildConfig.LOG_DEBUG){
            //Release 时,让框架不再打印 Http 请求和响应的信息
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        } else{
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new CommonParamsInterceptor());//添加公共参数拦截器
        builder.addInterceptor(logging);//日志拦截器
        //正式环境不允许抓包
        if (!BuildConfig.DEBUG) {
            builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
        }
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    /**
     * 提供外部 API 接口
    * @return
     */
    public ApiService getApiService(){
        return this.mApiService;
    }

    /**
     * 解决android 7.0无法抓包问题（https://www.jianshu.com/p/0159ed140344）
     */
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    /**
     * 证书类
     */
    public static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    public static RetrofitManager getInstance(){
        return Inner.INSTANCE;
    }

    /**
     * 静态内部类单例模式
     */
    private static class Inner{
        private static RetrofitManager INSTANCE = new RetrofitManager();
    }
}

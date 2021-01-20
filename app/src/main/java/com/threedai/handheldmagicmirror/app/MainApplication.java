package com.threedai.handheldmagicmirror.app;

import android.app.Application;

/**
 * 主应用入口
 */
public class MainApplication extends Application {

    public static MainApplication INSTANCE = null;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}

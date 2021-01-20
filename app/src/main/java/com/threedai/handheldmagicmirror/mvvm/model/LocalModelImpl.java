package com.threedai.handheldmagicmirror.mvvm.model;

/**
 * local model层实现，加载数据库，sharePreference等
 */
public class LocalModelImpl implements ILocalModel {

    private volatile static LocalModelImpl INSTANCE = null;

    public static LocalModelImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalModelImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalModelImpl();
                }
            }
        }
        return INSTANCE;
    }

    private LocalModelImpl() {
    }
}

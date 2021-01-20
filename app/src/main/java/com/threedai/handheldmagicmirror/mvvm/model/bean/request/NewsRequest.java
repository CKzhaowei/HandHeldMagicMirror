package com.threedai.handheldmagicmirror.mvvm.model.bean.request;

public class NewsRequest {

    private int type;
    private int page;
    private int psize;
    private String token;
    private String callback;

    public NewsRequest(){}

    public NewsRequest(int type, int page, int psize, String token, String callback) {
        this.type = type;
        this.page = page;
        this.psize = psize;
        this.token = token;
        this.callback = callback;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPsize() {
        return psize;
    }

    public void setPsize(int psize) {
        this.psize = psize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}

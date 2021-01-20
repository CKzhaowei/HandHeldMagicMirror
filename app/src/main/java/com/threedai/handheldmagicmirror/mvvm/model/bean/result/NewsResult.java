package com.threedai.handheldmagicmirror.mvvm.model.bean.result;

public class NewsResult {


    /**
     * title : 轰六-K身上的“晾衣绳”有啥用? 没有它, 核弹就投不了
     * headpic : //image.uczzd.cn/5808131478081926363.jpg?id=0&width=266&height=200,//image.uczzd.cn/18298041897097703016.jpg?id=0&width=266&height=200,//image.uczzd.cn/7058432589807642455.jpg?id=0&width=266&height=200
     * auto : 马斯洛NF
     * source : UC头条
     * description : 轰六-K身上的“晾衣绳”有啥用? 没有它, 核弹就投不了
     * publishtime : 1574422703
     * id : MjQw
     * typeid : 7
     */

    private String title;
    private String headpic;
    private String auto;
    private String source;
    private String description;
    private int publishtime;
    private String id;
    private int typeid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(int publishtime) {
        this.publishtime = publishtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    @Override
    public String toString() {
        return "NewsResult{" +
                "title='" + title + '\'' +
                ", headpic='" + headpic + '\'' +
                ", auto='" + auto + '\'' +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                ", publishtime=" + publishtime +
                ", id='" + id + '\'' +
                ", typeid=" + typeid +
                '}';
    }
}

package com.ql.mynews.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/28.
 */
public class NewsAllBean implements Serializable {

    private String title;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;
    private String url;
    private String uniquekey;
    private String type;
    private String realtype;
    public NewsAllBean() {
    }

    public NewsAllBean(String title, String date, String author_name, String thumbnail_pic_s, String thumbnail_pic_s02, String thumbnail_pic_s03, String url, String uniquekey, String type, String realtype) {
        this.title = title;
        this.date = date;
        this.author_name = author_name;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
        this.url = url;
        this.uniquekey = uniquekey;
        this.type = type;
        this.realtype = realtype;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public String getUrl() {
        return url;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public String getType() {
        return type;
    }

    public String getRealtype() {
        return realtype;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRealtype(String realtype) {
        this.realtype = realtype;
    }
}

package com.bdlm.yytx.entity;

/**
 * 旅游资讯列表
 * Created by yyj on 2018/3/13.
 */

public class TourInfoListBean {
    private String id;
    private String title;
    private String tour_url;
    private String tour_img;
    private String simple_introduction;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTour_url() {
        return tour_url;
    }

    public void setTour_url(String tour_url) {
        this.tour_url = tour_url;
    }

    public String getTour_img() {
        return tour_img;
    }

    public void setTour_img(String tour_img) {
        this.tour_img = tour_img;
    }

    public String getSimple_introduction() {
        return simple_introduction;
    }

    public void setSimple_introduction(String simple_introduction) {
        this.simple_introduction = simple_introduction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

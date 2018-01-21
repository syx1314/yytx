package com.bdlm.yytx.entity;

/**
 * Created by Adim on 2018/1/21.
 */

public class TicketBean {
    private String name;
    private String senic_id;
    private String short_title;
    private String thumbnail;
    private String price;
    private String address;
    private String level_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenic_id() {
        return senic_id;
    }

    public void setSenic_id(String senic_id) {
        this.senic_id = senic_id;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }
}

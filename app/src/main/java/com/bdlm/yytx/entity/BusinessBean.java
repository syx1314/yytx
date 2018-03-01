package com.bdlm.yytx.entity;

/**
 * 商家
 * Created by yyj on 2018/2/2.
 */

public class BusinessBean {

    private String id;
    private String shop_name;
    private String mobile;
    private String manage_type;
    private String longitude;
    private String latitude;
    private String address;
    private String logo_img;
    private String logo_thumb_img;
    private String discount_info;
    private String special_explain;
    private String manage_type_name;
    private String distance;
    private String register_time;

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getId() {
        return id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getManage_type() {
        return manage_type;
    }

    public void setManage_type(String manage_type) {
        this.manage_type = manage_type;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }

    public String getLogo_thumb_img() {
        return logo_thumb_img;
    }

    public void setLogo_thumb_img(String logo_thumb_img) {
        this.logo_thumb_img = logo_thumb_img;
    }

    public String getDiscount_info() {
        return discount_info;
    }

    public void setDiscount_info(String discount_info) {
        this.discount_info = discount_info;
    }

    public String getSpecial_explain() {
        return special_explain;
    }

    public void setSpecial_explain(String special_explain) {
        this.special_explain = special_explain;
    }

    public String getManage_type_name() {
        return manage_type_name;
    }

    public void setManage_type_name(String manage_type_name) {
        this.manage_type_name = manage_type_name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

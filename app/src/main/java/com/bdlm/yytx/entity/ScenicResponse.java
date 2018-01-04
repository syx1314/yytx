package com.bdlm.yytx.entity;

/**
 * Created by yyj on 2018/1/4.
 */

public class ScenicResponse {

    private String senic_id;
    private String name;
    private String longitude;
    private String latitude;
    private String thumbnail;
    private String short_description;
    private int advance;
    private String cate_name;
    private String level_name;
    private double distance;
    private String passport_type_name;

    public String getSenic_id() {
        return senic_id;
    }

    public void setSenic_id(String senic_id) {
        this.senic_id = senic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public int getAdvance() {
        return advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPassport_type_name() {
        return passport_type_name;
    }

    public void setPassport_type_name(String passport_type_name) {
        this.passport_type_name = passport_type_name;
    }
}

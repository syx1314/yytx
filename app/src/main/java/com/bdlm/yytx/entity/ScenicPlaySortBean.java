package com.bdlm.yytx.entity;

/**
 * 景区游玩排名
 * Created by yyj on 2018/2/8.
 * "senic_num": "401",
 * "senic_id": "156",
 * "name": "秦始皇兵马俑",
 * "long_title": "世界十大古墓稀世珍宝之一",
 * "thumbnail": "XXX.jpg"
 */

public class ScenicPlaySortBean {
    private String senic_num;
    private String senic_id;
    private String name;
    private String long_title;
    private String thumbnail;

    public String getSenic_num() {
        return senic_num;
    }

    public void setSenic_num(String senic_num) {
        this.senic_num = senic_num;
    }

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

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

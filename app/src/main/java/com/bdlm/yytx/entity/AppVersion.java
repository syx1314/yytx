package com.bdlm.yytx.entity;

/**
 * App版本
 * Created by Adim on 2018/1/22.
 */

public class AppVersion {
    private String explain;
    private String version;
    private String downurl;
    private String is_update;
    private String is_forced_update;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getIs_update() {
        return is_update;
    }

    public void setIs_update(String is_update) {
        this.is_update = is_update;
    }

    public String getIs_forced_update() {
        return is_forced_update;
    }

    public void setIs_forced_update(String is_forced_update) {
        this.is_forced_update = is_forced_update;
    }
}

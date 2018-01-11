package com.bdlm.yytx.entity;

/**
 * 位置实体类
 * Created by yyj on 2018/1/10.
 */

public class PositionBean {
    private int errorCode;//错误码
    private double longitude;
    private double latitude;
    private String province;
    private String errorInfo;//错误信息
    private String locationDetails;

    public PositionBean(int errorCode, double longitude, double latitude, String province, String errorInfo, String locationDetails) {
        this.errorCode = errorCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.province = province;
        this.errorInfo = errorInfo;
        this.locationDetails = locationDetails;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getProvince() {
        return province;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public String getLocationDetails() {
        return locationDetails;
    }
}

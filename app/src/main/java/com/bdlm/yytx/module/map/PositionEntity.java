/**  
 * Project Name:Android_Car_Example  
 * File Name:PositionEntity.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午9:50:28  
 *  
 */

package com.bdlm.yytx.module.map;

import java.io.Serializable;

/**
 * ClassName:PositionEntity <br/>
 * Function: 封装的关于位置的实体 <br/>
 * Date: 2015年4月3日 上午9:50:28 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class PositionEntity  implements Serializable{

	public double latitue;

	public double longitude;

	public String address;
	
	public String city;
	public String cityCode;
	public float bearing;//方向角
	public float speed;//速度

	public PositionEntity() {
	}

	public PositionEntity(double latitude, double longtitude, String address, String city) {
		this.latitue = latitude;
		this.longitude = longtitude;
		this.address = address;
		this.city=city;
	}

	public PositionEntity(double latitue, double longitude, String address, String city, float bearing, float speed) {
		this.latitue = latitue;
		this.longitude = longitude;
		this.address = address;
		this.city = city;
		this.bearing = bearing;
		this.speed = speed;
	}

	public double getLatitue() {
		return latitue;
	}

	public void setLatitue(double latitue) {
		this.latitue = latitue;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public float getBearing() {
		return bearing;
	}

	public void setBearing(float bearing) {
		this.bearing = bearing;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "PositionEntity{" +
				"latitue=" + latitue +
				", longitude=" + longitude +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", bearing=" + bearing +
				", speed=" + speed +
				'}';
	}
}

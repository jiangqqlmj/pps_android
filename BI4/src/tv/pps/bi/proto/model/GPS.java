package tv.pps.bi.proto.model;

import java.io.Serializable;

/**
 * GPS坐标，信息
 * @author jiangqingqing
 * @time 2013/09/03 14:57
 */
public class GPS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1370354716745490346L;
	private double latitude;//纬度
	private double longitude; //经度
	private double altitude;//海拔
	
	private long timestamp;//时间戳
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 
	 */
	public GPS() {
		super();
	}
	/**
	 * @param latitude
	 * @param longitude
	 * @param altitude
	 */
	public GPS(double latitude, double longitude, double altitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}
	
	
	/**
	 * @param latitude
	 * @param longitude
	 * @param altitude
	 * @param timestamp
	 */
	public GPS(double latitude, double longitude, double altitude,
			long timestamp) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.timestamp = timestamp;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GPS [latitude=" + latitude + ", longitude=" + longitude
				+ ", altitude=" + altitude + ", timestamp=" + timestamp + "]";
	}
	
	
}

package tv.pps.bi.proto.model;

import java.io.Serializable;

public class DeviceInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2871230711631620507L;
	private String imei; // 手机串号
	private String imsi; // 手机号
	private String manufacturer; // 制造商英文名字
	private String model; // 型号, 与外层的型号一致
	private String screen_resolution; // 分辨率
	private String os_version; // os版本
	private String os_custermize; // 可选值: "root|break" root是andorid， break是ios

	public DeviceInfo() {
		super();
	}

	public DeviceInfo(String imei, String imsi, String manufacturer,
			String model, String screen_resolution, String os_version,
			String os_custermize) {
		super();
		this.imei = imei;
		this.imsi = imsi;
		this.manufacturer = manufacturer;
		this.model = model;
		this.screen_resolution = screen_resolution;
		this.os_version = os_version;
		this.os_custermize = os_custermize;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getScreen_resolution() {
		return screen_resolution;
	}

	public void setScreen_resolution(String screen_resolution) {
		this.screen_resolution = screen_resolution;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getOs_custermize() {
		return os_custermize;
	}

	public void setOs_custermize(String os_custermize) {
		this.os_custermize = os_custermize;
	}

	@Override
	public String toString() {
		return "DeviceInfo [imei=" + imei + ", imsi=" + imsi
				+ ", manufacturer=" + manufacturer + ", model=" + model
				+ ", screen_resolution=" + screen_resolution + ", os_version="
				+ os_version + ", os_custermize=" + os_custermize + "]";
	}

}

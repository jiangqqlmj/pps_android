package com.jiangqq.bean;

public class UpdateInformation {
	public int localVersion = 1;// 本地版本
	public int serverVersion = 1;// 服务器版本
	public int serverFlag = 0;// 服务器标志
	public int lastForce = 0;// 之前强制升级版本
	public String Durl = "";// 升级包获取地址
	public String MD5 = "";// 升级包获取地址MD5值
	public String upgradeinfo = "";// 升级信息
	public String downloadDir = ".pps/PPStv_update";// 下载目录

	public UpdateInformation() {
		super();
	}

	public UpdateInformation(int localVersion, int serverFlag, int lastForce,
			String durl, String mD5, String upgradeinfo, String downloadDir) {
		super();
		this.localVersion = localVersion;
		this.serverFlag = serverFlag;
		this.lastForce = lastForce;
		Durl = durl;
		MD5 = mD5;
		this.upgradeinfo = upgradeinfo;
		this.downloadDir = downloadDir;
	}

	public int getLocalVersion() {
		return localVersion;
	}

	public void setLocalVersion(int localVersion) {
		this.localVersion = localVersion;
	}

	public  int getServerVersion() {
		return serverVersion;
	}

	public  void setServerVersion(int serverVersion) {
		this.serverVersion = serverVersion;
	}

	public int getServerFlag() {
		return serverFlag;
	}

	public void setServerFlag(int serverFlag) {
		this.serverFlag = serverFlag;
	}

	public int getLastForce() {
		return lastForce;
	}

	public void setLastForce(int lastForce) {
		this.lastForce = lastForce;
	}

	public String getDurl() {
		return Durl;
	}

	public void setDurl(String durl) {
		Durl = durl;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getUpgradeinfo() {
		return upgradeinfo;
	}

	public void setUpgradeinfo(String upgradeinfo) {
		this.upgradeinfo = upgradeinfo;
	}

	public String getDownloadDir() {
		return downloadDir;
	}

	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}

	@Override
	public String toString() {
		return "UpdateInformation [localVersion=" + localVersion
				+ ", serverFlag=" + serverFlag + ", lastForce=" + lastForce
				+ ", Durl=" + Durl + ", MD5=" + MD5 + ", upgradeinfo="
				+ upgradeinfo + ", downloadDir=" + downloadDir + "]";
	}

}

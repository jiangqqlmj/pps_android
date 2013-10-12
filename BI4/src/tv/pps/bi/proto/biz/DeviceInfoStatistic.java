package tv.pps.bi.proto.biz;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceInfoStatistic {// ok

	private Context mContext;
	private static String uid;
	private static String loginId;
	private static String platform;
		
	/**
	 * @return the uid
	 */
	public static String getUid() {
		return uid;
	}

	/**
	 * @return the loginId
	 */
	public static String getLoginId() {
		return loginId;
	}

	/**
	 * @param uid the uid to set
	 */
	public static void setUid(String uid,Context pContext) {
		//DeviceInfoStatistic.uid = uid;
	   pContext.getSharedPreferences("bi4sdk", pContext.MODE_PRIVATE).edit().putString("uuid", uid).commit();
		
	}

	/**
	 * @param loginId the loginId to set
	 */
	public static void setLoginId(String loginId,Context pContext) {
		//DeviceInfoStatistic.loginId = loginId;
		pContext.getSharedPreferences("bi4sdk", pContext.MODE_PRIVATE).edit().putString("loginID", loginId).commit();
	}

	/**
	 * @param platform the platform to set
	 */
	public static void setPlatform(String platform,Context pContext) {
		//DeviceInfoStatistic.platform = platform;
		pContext.getSharedPreferences("bi4sdk", pContext.MODE_PRIVATE).edit().putString("platform", platform).commit();
	}


	

	public DeviceInfoStatistic(Context pContext) {
		this.mContext = pContext;
	}

	public String getDeviceInfo() {

		String str = "model:" + getModel() + "\nplatform:" + getPlatform()
				+ "\nIMEI:" + getIMEI() + "\nIMSI:" + getIMSI()
				+ "\nmanufactorer:" + getManufacturer()
				+ "\nScreen_resolution:" + getScreen_resolution()
				+ "\nOS_VERSION:" + getOS_version() + "\nOS_CUSTOMIZE:"
				+ getOS_custermize() + "\nMAC:" + getMacAddress();
		return str;

	}

	public String getModel() {// 获取model
		return android.os.Build.MODEL;
	}

	public String getPlatform() {// 获取platform
		return platform;
	}

	public String getIMEI() {
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = tm.getDeviceId();
		if (str == null || str.equals(""))
			return "no IMEI";
		return str;
	}

	public String getIMSI() {
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = tm.getSubscriberId();
		if (str == null || str.equals(""))
			return "no IMSI";
		return str;
	}

	public String getManufacturer() {
		return android.os.Build.MANUFACTURER;

	}

	public String getScreen_resolution() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.widthPixels + " * " + dm.heightPixels;
	}

	@SuppressWarnings("deprecation")
	public String getOS_version() {
		return android.os.Build.VERSION.SDK;
	}

	public String getOS_custermize() {

		return "root";
	}

	public String getMacAddress() {// 获取mac 地址
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			macSerial = "null";
			ex.printStackTrace();
		}
		return macSerial;
	}
}

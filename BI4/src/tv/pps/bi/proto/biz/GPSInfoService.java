package tv.pps.bi.proto.biz;

import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.utils.LogUtils;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.util.Log;

public class GPSInfoService {
	public final static String TAG = "GPSInfo";
	Context con;

	public GPSInfoService(Context con) {
		this.con = con;
	}
	public  GPS getLocations(Context context) {
		GPS gps = new GPS();
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = null;
		boolean isGPSOpen = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkOpen = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (isGPSOpen) {
			LogUtils.i(TAG, "gps provider正常使用");
			location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				gps.setLatitude(location.getLatitude());
				gps.setLongitude(location.getLongitude());
				gps.setAltitude(location.getAltitude());
				gps.setTimestamp(System.currentTimeMillis());
				return gps;
			} else {
				return null;
			}
		}
		if (isNetworkOpen) {
			LogUtils.i(TAG, "network provider正常使用");
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (wifiManager.isWifiEnabled()) {
				LogUtils.i(TAG, "wifi正常开启");
				location = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location != null) {
					gps.setLatitude(location.getLatitude());
					gps.setLongitude(location.getLongitude());
					gps.setAltitude(location.getAltitude());
					gps.setTimestamp(System.currentTimeMillis());
					return gps;
				}
			} else {
				LogUtils.i(TAG, "wifi未开启");
				return null;
			}
		}
		return null;
	}
}

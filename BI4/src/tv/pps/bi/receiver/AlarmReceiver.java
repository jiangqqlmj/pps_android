package tv.pps.bi.receiver;

import java.util.List;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.service.ListenService;
import tv.pps.bi.utils.LogUtils;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {

	String receiveType;

	public void onReceive(Context context, Intent intent) {

		if (!IntervalTimeConstance.isSTART_SERVICE_SWITCH()) {

			if ("tv.pps.alarmReceiver".equals(intent.getAction())) {
				if (isServiceRunning(context, "tv.pps.bi.service.ListenService")) {

					receiveType = "监听服务运行中......即将被关闭";
					Intent deliver = new Intent();
					deliver.setClass(context, ListenService.class);
					context.stopService(deliver);
				} else {
					receiveType = "监听服务已关闭";
				}
			} else {
				receiveType = "在广播中关闭其他服务";
			}
			LogUtils.e(TagConstance.TAG_SERVICE, receiveType);
			return;
		}
		if ("tv.pps.alarmReceiver".equals(intent.getAction())) {
			Intent service = new Intent();
			service.setClass(context, ListenService.class);
			context.startService(service);
			LogUtils.v(TagConstance.TAG_SERVICE, "开启监听服务");
		}
//		if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
//			// 检测到网络变化
//			ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//			if (info != null && info.isAvailable()) {
//				String name = info.getTypeName();
//				LogUtils.v(TagConstance.TAG_SERVICE, "网络为:"+name);
//				//进行开启服务
//				Intent service = new Intent();
//				service.setClass(context, ListenService.class);
//				context.startService(service);
//				LogUtils.v(TagConstance.TAG_SERVICE, "开启监听服务");
//								
//			} else {
//				LogUtils.v(TagConstance.TAG_SERVICE, "没有网络");
//				 
//				
//				
//			}
//		}

	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className)) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	//

}

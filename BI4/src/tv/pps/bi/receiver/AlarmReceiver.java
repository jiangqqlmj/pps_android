package tv.pps.bi.receiver;

import java.util.List;

import tv.pps.bi.service.ListenService;
import tv.pps.bi.utils.NetworkUtils;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	private static final String TAG = "bi";
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		if("tv.pps.alarmReceiver".equals(intent.getAction())){
			Intent service = new Intent();
			service.setClass(context, ListenService.class);
			context.startService(service);
			Log.v(TAG, "start service");
		}else if("deliver".equals(intent.getAction())){
			if(NetworkUtils.isNetworkConnected(context)){//2个小时之后，有网络则进行开启服务
				Intent deliver = new Intent();
				deliver.setClass(context, tv.pps.bi.proto.SendUserActivityService.class);
				
				context.startService(deliver);
			}else{//没有网络停止服务
				//
				Intent deliver = new Intent();
				deliver.setClass(context, tv.pps.bi.proto.SendUserActivityService.class);
				context.stopService(deliver);
			}
		}
		
	}
	
	/**
     * 用来判断服务是否运行.
     * @param context
     * @param className 判断的服务名字
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

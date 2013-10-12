package tv.pps.bi.receiver;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.utils.LogUtils;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


public class ScreenBroadcastReceiver extends BroadcastReceiver {

	private static final  String TAG = "bi";
	private AlarmReceiver mAlarmReceiver = new AlarmReceiver();
	private boolean isRegisterReceiver = false;
	@Override
	public void onReceive(Context context, Intent intent) {
		
      if(intent.getAction().equals("android.intent.action.USER_PRESENT")){//android.intent.action.SCREEN_ON
    	   LogUtils.v(TAG, "解锁状态下开始发送广播");
    	   long triggerAtTime = System.currentTimeMillis();
   		   AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
           Intent intent_receiver = new Intent("tv.pps.alarmReceiver");
           PendingIntent sender = PendingIntent.getBroadcast(
                   context, 0, intent_receiver, PendingIntent.FLAG_CANCEL_CURRENT);
//           int interval =  1*60*60*1000;//时间间隔1小时，将每隔1小时发送一次广播
           am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, IntervalTimeConstance.START_LISTEN_SERVICE_TIME, sender);//在指定的时刻发送广播，并不唤醒设备
       }else if(intent.getAction().equals("android.intent,action.BOOT_COMPLETED")){//监听开机启动的广播 4.0
    	   LogUtils.v(TAG, "是否能够监听到开机广播");
    	   long triggerAtTime =  System.currentTimeMillis();
   		   AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
           Intent intent_receiver = new Intent("tv.pps.alarmReceiver");
           PendingIntent sender = PendingIntent.getBroadcast(
                   context, 0, intent_receiver, PendingIntent.FLAG_CANCEL_CURRENT);
//           int interval =  1*60*60*1000;//时间间隔1小时，将每隔1小时发送一次广播
           //第一个参数和第二个参数要一致
           am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, IntervalTimeConstance.START_LISTEN_SERVICE_TIME, sender);//在指定的时刻发送广播，并不唤醒设备
       }
         //registerAlarmReceiver(context);
	}
	
	/**
	 * 注册广播
	 * @param mContext
	 */
	public void registerAlarmReceiver(Context mContext) {
		if (!isRegisterReceiver) {
			isRegisterReceiver = true;
			IntentFilter filter = new IntentFilter();
			filter.addAction("tv.pps.alarmReceiver");
			filter.addAction("deliver");
			mContext.registerReceiver(mAlarmReceiver, filter);
		}
	}

	public void unRegisterAlarmReceiver(Context mContext) {
		if (isRegisterReceiver) {
			isRegisterReceiver = false;
			mContext.unregisterReceiver(mAlarmReceiver);
		}
	}
}

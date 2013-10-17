package tv.pps.bi.receiver;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.utils.LogUtils;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ScreenBroadcastReceiver extends BroadcastReceiver {

	private AlarmReceiver mAlarmReceiver = new AlarmReceiver();
	private boolean isRegisterReceiver = false;

	SharedPreferences sp;
	
	int killid;
    	
	
	public void onReceive(Context context, Intent intent) {
		
	
		
		
		if (!IntervalTimeConstance.isSTART_SERVICE_SWITCH()) { // 如果开关为false则不启动服务
			
			
			sp =  context.getSharedPreferences("bi4sdk", Context.MODE_PRIVATE);
			killid = sp.getInt("beKilled", 0);
			if(killid==1){
			IntervalTimeConstance.setStartServiceSwitch(context,sp.getBoolean("switch", false));
			LogUtils.e(TagConstance.TAG_SERVICE, "尝试开启监听服务onReceive() = "+"killid = "+killid+"--switch = "+sp.getBoolean("switch", true));
			Editor edit = sp.edit();
			edit.clear();
			edit.commit();
			
			}
			if(!IntervalTimeConstance.isSTART_SERVICE_SWITCH()){
				LogUtils.v(TagConstance.TAG_SERVICE, "-------------开关为false，不启动服务---------");
			return;
			}
		}
		LogUtils.v(TagConstance.TAG_SERVICE, "-------------开关为true，启动服务---------");
		if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {// android.intent.action.SCREEN_ON
			LogUtils.v(TagConstance.TAG_SERVICE, "解锁状态下开始发送广播");
			long triggerAtTime = System.currentTimeMillis();
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent_receiver = new Intent("tv.pps.alarmReceiver");
			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					intent_receiver, PendingIntent.FLAG_CANCEL_CURRENT);
			// int interval = 1*60*60*1000;//时间间隔1小时，将每隔1小时发送一次广播
			am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime,
					IntervalTimeConstance.START_LISTEN_SERVICE_TIME, sender);// 在指定的时刻发送广播，并不唤醒设备

		} else if (intent.getAction().equals(
				"android.intent,action.BOOT_COMPLETED")) {// 监听开机启动的广播 4.0
			LogUtils.v(TagConstance.TAG_SERVICE, "是否能够监听到开机广播");
			long triggerAtTime = System.currentTimeMillis();
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent_receiver = new Intent("tv.pps.alarmReceiver");
			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					intent_receiver, PendingIntent.FLAG_CANCEL_CURRENT);
			// int interval = 1*60*60*1000;//时间间隔1小时，将每隔1小时发送一次广播
			// 第一个参数和第二个参数要一致
			am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime,
					IntervalTimeConstance.START_LISTEN_SERVICE_TIME, sender);// 在指定的时刻发送广播，并不唤醒设备
		}
		// registerAlarmReceiver(context);
	}

	/**
	 * 注册广播
	 * 
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

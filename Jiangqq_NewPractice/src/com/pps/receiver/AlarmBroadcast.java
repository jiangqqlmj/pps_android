package com.pps.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * 启动闹钟定时器，每隔10分钟进行发送一次广播,请求去删除节目列表xml文件
 * @author jiangqingqing
 * 
 */
public class AlarmBroadcast extends BroadcastReceiver {

	public static String ALARM_ACTION = "com.pps.tv.delete";
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ALARM_ACTION)) {
			// 收到启动定时器请求，每隔30分钟发送一次删除节目列表的广播
			Log.i("jiangqq", "收到启动定时器请求，每隔10分钟发送一次删除节目列表的广播...");
			// 接受到闹钟每隔10S进行发送过来的广播，然后启动服务
			Intent _Intent = new Intent(context, DeleteXmlBroadcast.class);
			_Intent.setAction("com.pps.tv.delete.xml");
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, _Intent, 0);
			//long firstTime = SystemClock.elapsedRealtime();
			long firstTime=1000 * 60 * 30;
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+firstTime,
					11000 * 60 * 30  , pendingIntent);
		}
	}

}

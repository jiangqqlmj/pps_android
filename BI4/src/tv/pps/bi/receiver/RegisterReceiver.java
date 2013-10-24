package tv.pps.bi.receiver;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.service.ListenService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RegisterReceiver extends BroadcastReceiver {

	private static final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";
	private ListenService mListenService;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action  = intent.getAction();
		if(action.equals(SCREEN_OFF)){
			mListenService = new ListenService();
			/**锁屏状态下一分钟后投递一次数据*/
			mListenService.handler.sendEmptyMessageDelayed(ListenService.DELIVERY, IntervalTimeConstance.START_DELIVER_SERVICE_TIME_FIRST);
		}
	}

}

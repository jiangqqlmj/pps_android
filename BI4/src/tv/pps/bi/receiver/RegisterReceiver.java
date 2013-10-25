package tv.pps.bi.receiver;

import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.service.ListenService;
import tv.pps.bi.service.ManagerService;
import tv.pps.bi.utils.LogUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 锁屏广播接收
 * @author jiangqingqing
 *
 */
public class RegisterReceiver extends BroadcastReceiver {

	private static final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		LogUtils.i(TagConstance.TAG_SERVICE, "监听到广播 = " + action);
		if (action.equals(SCREEN_OFF)) {

//			mListenService = new ListenService();
//			/** 锁屏状态下一分钟后投递一次数据 */
//			mListenService.handler.sendEmptyMessageDelayed(
//					ListenService.DELIVERY,
//					IntervalTimeConstance.START_DELIVER_SERVICE_TIME_FIRST);
//			LogUtils.i(
//					TagConstance.TAG_SERVICE,
//					"锁屏下"
//							+ (IntervalTimeConstance.START_DELIVER_SERVICE_TIME_FIRST / 1000)
//							+ "s后发送数据");
			
			ManagerService.startService(context);
		}
	}

}

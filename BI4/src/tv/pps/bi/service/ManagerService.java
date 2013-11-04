package tv.pps.bi.service;

import tv.pps.bi.config.TagConstance;
import tv.pps.bi.utils.LogUtils;
import android.content.Context;
import android.content.Intent;

/**
 * 管理服务类
 * @author zhuchengjin
 *
 */
public class ManagerService {

	/**
	 * 启动服务调用方法
	 */
	public static void startService(Context context){
		LogUtils.e(TagConstance.TAG_SERVICE, "用户行为统计SDK1.1");
		LogUtils.i(TagConstance.TAG_SERVICE, "第一次启动监听service");
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.startService(intent);
	}
	
	/**
	 * 停止服务
	 * @param context
	 */
	public static void stopService(Context context){
		LogUtils.i(TagConstance.TAG_SERVICE, "手动停止监听service");
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.stopService(intent);
	}
	
}

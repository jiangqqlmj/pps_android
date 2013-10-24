package tv.pps.bi.service;

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
		
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.startService(intent);
	}
	
	/**
	 * 停止服务
	 * @param context
	 */
	public static void stopService(Context context){
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.stopService(intent);
	}
	
}

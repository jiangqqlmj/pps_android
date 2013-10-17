package tv.pps.bi.db.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class IntervalTimeConstance {

	/**监听到广播时，每隔一个小时进行开启一次服务*/
	public static final int  START_LISTEN_SERVICE_TIME 	= 1*60*60*1000;//一小时
	
	/**每隔两个小时开启投递数据service*/
	public static  int START_DELIVER_SERVICE_TIME 	= 2*60*60*1000;//两小时
	
	/**每隔三十分钟进行一次用户行为查询*/
	public static  int START_USERINFO_SEARCH_TIME 	= 30*60*1000;//三十分钟
	
	/**十秒中进行一次用户行为的查询*/
	public static final int START_APPUSEINFO_SEARCH_TIME= 10*1000;//十秒钟
	/**
	 * 开启或者关闭用户行为统计的开关
	 */
	public  static boolean START_SERVICE_SWITCH = false;//开启或关闭用户行为统计的开关
	


	public static boolean isSTART_SERVICE_SWITCH() {
		return START_SERVICE_SWITCH;
	}

	public static void setStartServiceSwitch(Context context,boolean sTART_SERVICE_SWITCH) {
		
		SharedPreferences sp = context.getSharedPreferences("bi4sdk",Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean("switch",sTART_SERVICE_SWITCH );
		edit.commit();
		START_SERVICE_SWITCH = sTART_SERVICE_SWITCH;
	}

	/**
	 * @return the sTART_DELIVER_SERVICE_TIME
	 */
	public static int getSTART_DELIVER_SERVICE_TIME() {
		return START_DELIVER_SERVICE_TIME;
	}

	/**
	 * @return the sTART_USERINFO_SEARCH_TIME
	 */
	public static int getSTART_USERINFO_SEARCH_TIME() {
		return START_USERINFO_SEARCH_TIME;
	}

	/**
	 * @param sTART_DELIVER_SERVICE_TIME the sTART_DELIVER_SERVICE_TIME to set
	 */
	public static void setStartDeliverServiceTime(int sTART_DELIVER_SERVICE_TIME) {
		START_DELIVER_SERVICE_TIME = sTART_DELIVER_SERVICE_TIME;
	}

	/**
	 * @param sTART_USERINFO_SEARCH_TIME the sTART_USERINFO_SEARCH_TIME to set
	 */
	public static void setStartUserInfoSearchTime(int sTART_USERINFO_SEARCH_TIME) {
		START_USERINFO_SEARCH_TIME = sTART_USERINFO_SEARCH_TIME;
	}
}

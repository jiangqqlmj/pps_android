package tv.pps.bi.config;

import android.content.Context;


/**
 * 配置各种时间间隔
 * @author jiangqingqing
 * @time 2013/10/24
 */
public class IntervalTimeConstance {

	/**监听到广播时，每隔一个小时进行开启一次服务*/
	public static final int  START_LISTEN_SERVICE_TIME 	= 1*60*60*1000;//1小时
	
	/**每隔两个小时开启投递数据service*/
	public static  int START_DELIVER_SERVICE_TIME 	= 1*60*60*1000;//1小时
	
	/**第一次启动服务时，一分钟后便开始投递*/
	public static  int START_DELIVER_SERVICE_TIME_FIRST 	= 1*60*1000;//一分钟
	
	/**每隔三十分钟进行一次用户行为查询*/
	public static  int START_USERINFO_SEARCH_TIME 	= 30*60*1000;//三十分钟
	
	/**十秒中进行一次用户行为的查询*/
	public static final int START_APPUSEINFO_SEARCH_TIME= 1*60*1000;//60秒钟
	
	/**默认为一分钟进行一次APP新的轮询*/
	public static  int START_APP_POLL_TIME= 1*60*1000;  //一分钟

	/**
	 * 开启或者关闭用户行为统计的开关
	 */
	public  static boolean START_SERVICE_SWITCH = false; //开启或关闭用户行为统计的开关
	
	
	
	public static int PRECURSOR_DELIVER_TIME=10*60*1000; //10分钟 
	public static long PRECURSOR_DELIVER_INIT=0L;
	
	
	public static int getStartAppPollTime() {
		return START_APP_POLL_TIME;
	}

	public static void setStartAppPollTime(int sTART_APP_POLL_TIME) {
		START_APP_POLL_TIME = sTART_APP_POLL_TIME;
	}

    
	public static void setPrecursor_Deliver_Init(long pPrecursor_Deliver_Init) {
		PRECURSOR_DELIVER_INIT = pPrecursor_Deliver_Init;
	}

	public static void setPrecursor_Deliver_Time(int pPrecursor_Deliver_Time) {
		PRECURSOR_DELIVER_TIME = pPrecursor_Deliver_Time;
	}

	public static boolean isSTART_SERVICE_SWITCH() {
		return START_SERVICE_SWITCH;
	}

	public static void setStartServiceSwitch(Context context,boolean sTART_SERVICE_SWITCH) {
		
//		SharedPreferences sp = context.getSharedPreferences("bi4sdk",Context.MODE_PRIVATE);
//		Editor edit = sp.edit();
//		edit.putBoolean("switch",sTART_SERVICE_SWITCH );
//		edit.commit();
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

package tv.pps.bi.db.config;

/**
 * 配置时间常量类
 * 
 * @author jiangqingqing
 * @time 2013/09/17 14:03
 */
public class IntervalTimeConstance {

	/** 监听到广播时，每隔一个小时进行开启一次服务 */
	public static int START_LISTEN_SERVICE_TIME = 1 * 60 * 60 * 1000;// 一小时
	/** 每隔两个小时开启投递数据service */
	public static int START_DELIVER_SERVICE_TIME = 2 * 60 * 60 * 1000;// 两小时
	/** 每隔三十分钟进行一次用户行为查询 */
	public static int START_USERINFO_SEARCH_TIME = 30 * 60 * 1000;// 三十分钟
	/** 十秒中进行一次使用APP行为的查询 */
	public static int START_APPUSEINFO_SEARCH_TIME = 10 * 1000;// 十秒钟

	/**
	 * @return the sTART_LISTEN_SERVICE_TIME
	 */
	public static int getSTART_LISTEN_SERVICE_TIME() {
		return START_LISTEN_SERVICE_TIME;
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
	 * @return the sTART_APPUSEINFO_SEARCH_TIME
	 */
	public static int getSTART_APPUSEINFO_SEARCH_TIME() {
		return START_APPUSEINFO_SEARCH_TIME;
	}

	
	/**
	 * 设置广播开启服务的时间间隔
	 * @param sTART_LISTEN_SERVICE_TIME  时间间隔 (毫秒)
	 */
	public static void setStartListenServiceTime(
			int sTART_LISTEN_SERVICE_TIME) {
		START_LISTEN_SERVICE_TIME = sTART_LISTEN_SERVICE_TIME;
	}

	/**
	 * 设置投递数据的时间间隔
	 * @param sTART_DELIVER_SERVICE_TIME 时间间隔 (毫秒)
	 */
	public static void setStartDeliverServiceTime(
			int sTART_DELIVER_SERVICE_TIME) {
		START_DELIVER_SERVICE_TIME = sTART_DELIVER_SERVICE_TIME;
	}

	/**
	 * 设置查询用户行为信息的时间间隔
	 * @param sTART_USERINFO_SEARCH_TIME 时间间隔(毫秒)
	 */
	public static void setStartUserInfoSearchTime(
			int sTART_USERINFO_SEARCH_TIME) {
		START_USERINFO_SEARCH_TIME = sTART_USERINFO_SEARCH_TIME;
	}

	/**
	 * 设置查询用户使用APP信息的时间间隔
	 * @param sTART_APPUSEINFO_SEARCH_TIME 时间间隔(毫秒)
	 */
	public static void setStartAppUseInfoSearchTime(
			int sTART_APPUSEINFO_SEARCH_TIME) {
		START_APPUSEINFO_SEARCH_TIME = sTART_APPUSEINFO_SEARCH_TIME;
	}

}

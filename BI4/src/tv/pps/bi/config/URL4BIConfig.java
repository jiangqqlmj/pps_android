package tv.pps.bi.config;

/**
 * URL接口
 * @author jiangqingqing
 * 
 */
public class URL4BIConfig {
    
	/**
	 * 投递接口
	 */
	//public static final String DELIVER_URL="http://c.uaa.iqiyi.com/m.gif";
	
	/**
	 * 测试接口
	 */
	public static final String DELIVER_URL="http://c.uaa.iqiyi.com/t.gif";
	
	// 服务器获取是否开启移动用户行为标志的URL
	public static String BI4_SIGN_URL="";  

	public static String getBI4_SIGN_URL() {
		return BI4_SIGN_URL;
	}

	public static void setBI4_SIGN_URL(String bI4_SIGN_URL) {
		BI4_SIGN_URL = bI4_SIGN_URL;
	} 
	
}

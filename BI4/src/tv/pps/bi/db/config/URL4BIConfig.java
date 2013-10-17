package tv.pps.bi.db.config;

/**
 * URL接口
 * @author jiangqingqing
 * 
 */
public class URL4BIConfig {
    
	// 服务器获取是否开启移动用户行为标志的URL
	public static String BI4_SIGN_URL="";  

	public static String getBI4_SIGN_URL() {
		return BI4_SIGN_URL;
	}

	public static void setBI4_SIGN_URL(String bI4_SIGN_URL) {
		BI4_SIGN_URL = bI4_SIGN_URL;
	} 
}

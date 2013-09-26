package tv.pps.bi.proto;

import java.util.List;

import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.WindowProto;

/**
 * 获取用户行为各项信息接口
 * @author jiangqingqing
 * 该接口只做定义方法,进行模块获获取接口方法整合参考
 * @time  2013/09/03  18:46
 */
public interface AchieveUserActivityManagerInterface {

	/**
	 * 唯一标示该客户端的匿名用户id
	 * @return 返回uid
	 */
	public String getUserUid();
	
	/**
	 * 注册用户登录id
	 * @return 用户登录ID
	 */
	public String getUserLogin(); 
	
	/**
	 * 可取值：pps_ios | pps_android | pps_pc | iqiyi_ios | iqiyi_android | iqiyi_pc
	 * @return 用户使用平台
	 */
	public String getUserPlatform();
	/**
	 * 设备mac地址
	 * @return 设备mac地址
	 */
	public String getUserMac();
	/**
	 * 设备型号
	 * @return 设备型号
	 */
	public String getUserModel();
	/**
	 * GPS坐标，mobile onlyc
	 * @return GPS坐标
	 */
	public GPS getUserGPS();
	
	/**
	 * 地图POI信息，gps坐标附近300米内的地图信息，mobile only
	 * @return 地图POI信息，gps坐标附近300米内的地图信息
	 */
	public List<String> getUserPoi();
	
	/**
	 * 安装的APP信息以及使用情况，mobile only
	 * @return 安装的APP信息以及使用情况
	 */
	public List<App> getUserInstalled_app();
	
	/**
	 * 在百度等搜索网站的搜索关键词
	 * @return 在百度等搜索网站的搜索关键词
	 */
	public List<String> getUserSearch_keyword();
	
    /**
     * 访问哪些网页
     * @return 访问哪些网页
     */
	public List<String> getUserUrl();
	/**
	 * 开机时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	 * @return 开机时间戳
	 */
	public List<String> getUserBoot_timestamp();
	
	/**
	 * 关机时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	 * @return 关机时间戳
	 */
	public List<String> getUserShutdown_timestamp();
	
	/**
	 * 打电话的时间，时长，mobile only
	 * @return 打电话的时间
	 */
	public List<PhoneActivity> getUserPhone_activity();
	/**
	 * 短信发送时间戳，格式：“20130601130122”（YYYYmmddhhMMss），mobile only
	 * @return 短信发送时间戳
	 */
	public List<String> getUserSms_sent_timestamp();
	/**
	 * 在优酷等竞品上看的视频情况
	 * @return 在优酷等竞品上看的视频情况
	 */
	public List<ThirdPartyVideoActivity>  getUserThird_party_video_activity();
	
	/**
	 * 启动了哪些进程，进程名称, client only
	 * @return 启动了哪些进程，进程名称
	 */
	public List<ProcessProto> getUserProcess();
	/**
	 * 活动窗口的标题名称, client only
	 * @return 活动窗口的标题名称
	 */
	public List<WindowProto> getUserWindow();
	/**
	 * 手机信息, mobile only
	 * @return 手机信息
	 */
	public DeviceInfo getUserDevice_info();
	
	
	
	public void close();
}

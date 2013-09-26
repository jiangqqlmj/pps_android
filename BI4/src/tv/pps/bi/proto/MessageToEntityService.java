package tv.pps.bi.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tv.pps.bi.proto.imp.AchieveUserActivityManagerImp;
import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.proto.model.WindowActivity;
import tv.pps.bi.proto.model.WindowProto;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 将获取的进行进行封装成用户行为实体对象
 * 
 * @author jiangqingqing
 * @time 2013/09/03 17:50
 */
public class MessageToEntityService {

	private UserActivity mUserActivity;
	private Context mContext;
	private AchieveUserActivityManagerInterface mActivityManagerInterface;
	private SharedPreferences mSharedPreferences;

	public void close() {
		mActivityManagerInterface.close();
	}

	// 初始化用户行为对象
	@SuppressWarnings("deprecation")
	public MessageToEntityService(Context pContext) {

		this.mContext = pContext;
		mUserActivity = new UserActivity();
		mSharedPreferences = mContext.getSharedPreferences("protobuff",
				Context.MODE_WORLD_READABLE);
		mActivityManagerInterface = new AchieveUserActivityManagerImp(mContext);
	}

	private String getUid(SharedPreferences pSharedPreferences) {
		return pSharedPreferences
				.getString("uid", UUID.randomUUID().toString());
	}

	private String getLoginId(SharedPreferences pSharedPreferences) {
		return pSharedPreferences.getString("login", "");
	}

	/**
	 * 把获取到的用户行为信息进行封装到标准UserActivity中，然后进行构造Protobuff格式数据
	 * 
	 * @return 封装好的用户行为对象
	 */
	public UserActivity getMsgUserEntity() {

		mUserActivity.setUid(getUid(mSharedPreferences)); // 唯一标示该客户端的匿名用户id。客户端为：flash
															// cookie
															// id；移动端为open udid
		mUserActivity.setLogin(getLoginId(mSharedPreferences)); // 注册用户登录id
		mUserActivity.setPlatform(mActivityManagerInterface.getUserPlatform()); // 可取值：pps_ios
																				// |
																				// pps_android
																				// |
																				// pps_pc
																				// |
																				// iqiyi_ios
																				// |
																				// iqiyi_android
																				// |
																				// iqiyi_pc
		mUserActivity.setMac(mActivityManagerInterface.getUserMac()); // 设备mac地址
		mUserActivity.setModel(mActivityManagerInterface.getUserModel()); // 设备型号

		// GPS坐标
		GPS mGps = mActivityManagerInterface.getUserGPS();
		if (null != mGps) {
			mUserActivity.setGps(mGps);
		}

		// 地图POI信息，gps坐标附近300米内的地图信息
		List<String> mPoiLists = mActivityManagerInterface.getUserPoi();
		mUserActivity.setPoi(mPoiLists);

		// 安装的APP信息以及使用情况
		List<App> mAppsLists = mActivityManagerInterface.getUserInstalled_app();
		if (null != mAppsLists) {
			mUserActivity.setInstalled_app(mAppsLists);
		}

		// 在百度等搜索网站的搜索关键词
		List<String> keywordLists = mActivityManagerInterface
				.getUserSearch_keyword();
		if (null != keywordLists) {
			mUserActivity.setSearch_keyword(keywordLists);
		}

		// 访问网页历史记录
		List<String> urlLists = mActivityManagerInterface.getUserUrl();
		if (null != urlLists) {
			mUserActivity.setUrl(urlLists);

		}

		// 开机时间戳
		List<String> boot_timestampLists = mActivityManagerInterface
				.getUserBoot_timestamp();
		if (null != boot_timestampLists) {
			mUserActivity.setBoot_timestamp(boot_timestampLists);
		}

		// 关机时间戳
		List<String> shutdown_timestampLists = mActivityManagerInterface
				.getUserShutdown_timestamp();
		if (null != shutdown_timestampLists) {
			mUserActivity.setShutdown_timestamp(shutdown_timestampLists);
		}

		// 打电话的时间，时长
		List<PhoneActivity> phone_activityLists = mActivityManagerInterface
				.getUserPhone_activity();
		if (null != phone_activityLists) {
			mUserActivity.setPhone_activity(phone_activityLists);
		}

		// 短信发送时间戳
		List<String> sms_sent_timestampLists = mActivityManagerInterface
				.getUserShutdown_timestamp();
		if (null != sms_sent_timestampLists) {
			mUserActivity.setSms_sent_timestamp(sms_sent_timestampLists);
		}
		// 第三方视频被客户端的播放历史记录
		// getThirdVideoActivity();
		// 第三方视频被客户端的播放历史记录
		// 进程名称等运行时间信息
		// getProcessActivity();
		// 活动窗口的标题名称
		// getWindowActivity();

		// 手机信息
		DeviceInfo mDeviceInfo = mActivityManagerInterface.getUserDevice_info();
		if (null != mDeviceInfo) {
			mUserActivity.setDevice_info(mDeviceInfo);
		}
		mActivityManagerInterface.close();  //进行关闭数据库
		return mUserActivity;
	}

	/**
	 * 第三方视频被客户端的播放历史记录
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void getThirdVideoActivity() {
		// 第三方视频被客户端的播放历史记录
		List<ThirdPartyVideoActivity> thirdLists = new ArrayList<ThirdPartyVideoActivity>();
		mUserActivity.setThird_party_video_activity(thirdLists);
	}

	/**
	 * 进程名称等运行时间信息
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void getProcessActivity() {
		// 进程名称等运行时间信息
		List<ProcessProto> processLists = new ArrayList<ProcessProto>();
		List<ProcessActivity> processActivities = new ArrayList<ProcessActivity>();
		ProcessActivity mProcessActivity = new ProcessActivity();
		mProcessActivity.setStart_timestamp("12333333");
		mProcessActivity.setDuration(2335223);
		processActivities.add(mProcessActivity);
		ProcessProto mProcessProto = new ProcessProto();
		mProcessProto.setName("PPS_Android");
		mProcessProto.setProcessActivities(processActivities);
		processLists.add(mProcessProto);
		mUserActivity.setProcess(processLists);
	}

	/**
	 * 活动窗口的标题名称
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void getWindowActivity() {
		// /活动窗口的标题名称
		List<WindowProto> windowLists = new ArrayList<WindowProto>();
		List<WindowActivity> windowActivities = new ArrayList<WindowActivity>();
		WindowActivity activity = new WindowActivity();
		activity.setStart_timestamp("1314564");
		activity.setDuration(12333);
		windowActivities.add(activity);
		WindowProto mWindowProto = new WindowProto();
		mWindowProto.setName("PPS_Android");
		mWindowProto.setActivity(windowActivities);
		windowLists.add(mWindowProto);
		mUserActivity.setWindow(windowLists);
	}
}

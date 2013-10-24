package tv.pps.bi.proto.imp;

import java.util.ArrayList;
import java.util.List;

import tv.pps.bi.db.DBOperation;
import tv.pps.bi.db.config.OtherConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.proto.AchieveUserActivityManagerInterface;
import tv.pps.bi.proto.biz.DeviceInfoService;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.proto.biz.InstalledAppService;
import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.Bootup;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.SMS;
import tv.pps.bi.proto.model.Shutdown;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.URLInfo;
import tv.pps.bi.proto.model.WindowProto;
import tv.pps.bi.utils.FileUtils;
import tv.pps.bi.utils.LogUtils;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 获取用户行为信息管理类接口
 * 
 * @author jiangqingqing
 * @time 2013/09/06 15:51
 */

public class AchieveUserActivityManagerImp implements
		AchieveUserActivityManagerInterface {

	private Context mContext;
	private DBOperation operation;
	
	private DeviceInfoStatistic mDeviceInfoStatistic;

	
	private SharedPreferences mSharedPreferences;
	private String [] uuidAndPlatform;
	public AchieveUserActivityManagerImp(Context pContext) {
		this.mContext = pContext;
		operation = new DBOperation(mContext);
		mDeviceInfoStatistic=new DeviceInfoStatistic(mContext);
		mSharedPreferences=pContext.getSharedPreferences("bi4sdk", Context.MODE_PRIVATE);
		uuidAndPlatform = FileUtils.fileToStrings(OtherConstance.SDCardFilename);
	
	}

	public void close(){
		if(operation!=null)
			operation.close();
	}
	/*
	 * (non-Javadoc) 唯一标示该客户端的匿名用户id
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserUid()
	 */
	@Override
	public String getUserUid() {
		if(mSharedPreferences.getString("uuid", "-").equals("-")){
			if(uuidAndPlatform!=null){
				if(uuidAndPlatform[0].indexOf(":")!=-1){
					LogUtils.i(TagConstance.TAG_ARCHIVE_DATA, "从sd卡获取uuid成功");
					mContext.getSharedPreferences("bi4sdk", Context.MODE_PRIVATE).edit().putString("uuid", uuidAndPlatform[0].substring(uuidAndPlatform[0].indexOf(":")+1)).commit();
				return uuidAndPlatform[0].substring(uuidAndPlatform[0].indexOf(":")+1);
				}else{
					return "-";
				}
			}
			else{
				return "-";
			}
		}
		else{
			LogUtils.i(TagConstance.TAG_ARCHIVE_DATA, "从sp获取uuid成功");
	      return mSharedPreferences.getString("uuid", "-");
		}
	}

	/*
	 * (non-Javadoc) 注册用户登录id
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserLogin()
	 */
	@Override
	public String getUserLogin() {

		return mSharedPreferences.getString("loginID", "-");
	}

	/*
	 * (non-Javadoc) 可取值：pps_ios | pps_android | pps_pc | iqiyi_ios |
	 * iqiyi_android | iqiyi_pc
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserPlatform()
	 */
	@Override
	public String getUserPlatform() {
		
		if(mSharedPreferences.getString("platform", "-").equals("-")){
			if(uuidAndPlatform!=null){
				if(uuidAndPlatform[1].indexOf(":")!=-1){
					LogUtils.i(TagConstance.TAG_ARCHIVE_DATA, "从sd卡获取platform成功");
					mContext.getSharedPreferences("bi4sdk", Context.MODE_PRIVATE).edit().putString("platform", uuidAndPlatform[1].substring(uuidAndPlatform[1].indexOf(":")+1)).commit();
					return uuidAndPlatform[1].substring(uuidAndPlatform[1].indexOf(":")+1);
				}else{
					return "-";
				}
			}
			else{
				return "-";
			}
		}
		else{
			LogUtils.i(TagConstance.TAG_ARCHIVE_DATA, "从sp获取platform成功");
			return mSharedPreferences.getString("platform", "-");
		}
		
		
	}

	/*
	 * (non-Javadoc) 设备mac地址
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserMac()
	 */
	@Override
	public String getUserMac() {
		return mDeviceInfoStatistic.getMacAddress()!=null?mDeviceInfoStatistic.getMacAddress():"-";
	}

	/*
	 * (non-Javadoc) 设备型号
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserModel()
	 */
	@Override
	public String getUserModel() {
		return mDeviceInfoStatistic.getModel()!=null?mDeviceInfoStatistic.getModel():"-";
	}

	/*
	 * (non-Javadoc) GPS坐标，mobile only
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserGPS()
	 */
	@Override
	public GPS getUserGPS() {
		List<GPS> mGpsLists=operation.queryTableGPS();
		if(null!=mGpsLists&&mGpsLists.size()>0)
		{
			return mGpsLists.get(mGpsLists.size()-1);
		} 
		return null;
	}

	/*
	 * (non-Javadoc) 地图POI信息，gps坐标附近300米内的地图信息，mobile only
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserPoi()
	 */
	@Override
	public List<String> getUserPoi() {
		List<String> mPoiLists=new ArrayList<String>();
		List<GPS> mGpsLists=operation.queryTableGPS();
		int size=mGpsLists!=null?mGpsLists.size():0;
		if(null!=mGpsLists&&size>0)
		{
//			for (GPS gps : mGpsLists) {
//				mPoiLists.add("-");
//			}
			for(int i=0;i<size;i++)
			{
				mPoiLists.add("-");
			}
			
			return mPoiLists;
			
		}
		return null;
	}

	/*
	 * (non-Javadoc) 安装的APP信息以及使用情况，mobile only
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserInstalled_app
	 * ()
	 */
	@Override
	public List<App> getUserInstalled_app() {
		InstalledAppService mInstalledAppService=new InstalledAppService(mContext);
		return mInstalledAppService.getUserInstalled_app();
	}

	/*
	 * (non-Javadoc) 在百度等搜索网站的搜索关键词
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserSearch_keyword
	 * ()
	 */
	@Override
	public List<String> getUserSearch_keyword() {
		List<String> keywordLists = new ArrayList<String>();
		List<URLInfo> mUrlInfos = operation.queryUrlInTable();
		if (null != mUrlInfos && mUrlInfos.size() > 0) {
			for (URLInfo urlInfo : mUrlInfos) {
				String keyInfo=urlInfo.getKeywords();
				if(null!=keyInfo&&!keyInfo.equals(""))
				{
					keywordLists.add(keyInfo);
				}
			}
		}
		return keywordLists;
	}

	/*
	 * (non-Javadoc) 访问哪些网页
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserUrl()
	 */
	@Override
	public List<String> getUserUrl() {
		List<String> urlLists = new ArrayList<String>();
		List<URLInfo> mUrlInfos = operation.queryUrlInTable();
		if (null != mUrlInfos && mUrlInfos.size() > 0) {
			for (URLInfo urlInfo : mUrlInfos) {
				urlLists.add(urlInfo.getUrl());
			}
		}
		return urlLists;
	}

	/*
	 * (non-Javadoc) 开机时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserBoot_timestamp
	 * ()
	 */
	@Override
	public List<String> getUserBoot_timestamp() {
		List<String> boot_timestampLists = new ArrayList<String>();
		List<Bootup> mBootups = operation.queryTableBootTime();
		if (null != mBootups && mBootups.size() > 0) {
			for (Bootup bootup : mBootups) {
				boot_timestampLists.add(bootup.getBoottime());
			}
		}
		return boot_timestampLists;
	}

	/*
	 * (non-Javadoc) 关机时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserShutdown_timestamp
	 * ()
	 */
	@Override
	public List<String> getUserShutdown_timestamp() {
		List<String> shutdown_timestampLists = new ArrayList<String>();
		List<Shutdown> mShutdowns = operation.queryShutTimeInTable();
		if (null != mShutdowns && mShutdowns.size() > 0) {
			for (Shutdown shutdown : mShutdowns) {
				shutdown_timestampLists.add(shutdown.getShutdowntime());
			}
		}
		return shutdown_timestampLists;
	}

	/*
	 * (non-Javadoc) 打电话的时间，时长，mobile only
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserPhone_activity
	 * ()
	 */
	@Override
	public List<PhoneActivity> getUserPhone_activity() {
		return operation.queryTablePhone();
	}

	/*
	 * (non-Javadoc) 短信发送时间戳，格式：“20130601130122”（YYYYmmddhhMMss），mobile only
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserSms_sent_timestamp
	 * ()
	 */
	@Override
	public List<String> getUserSms_sent_timestamp() {
		List<String> sms_sent_timestampLists = new ArrayList<String>();
		List<SMS> mSms = operation.queryTableSMS();
		if (null != mSms && mSms.size() > 0) {
			for (SMS sms : mSms) {
				sms_sent_timestampLists.add(sms.getSmstime());
			}
		}
		return sms_sent_timestampLists;
	}

	/*
	 * (non-Javadoc) 在优酷等竞品上看的视频情况
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#
	 * getUserThird_party_video_activity()
	 */
	@Override
	public List<ThirdPartyVideoActivity> getUserThird_party_video_activity() {
		return null;
	}

	/*
	 * (non-Javadoc) 启动了哪些进程，进程名称, client only
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserProcess()
	 */
	@Override
	public List<ProcessProto> getUserProcess() {
		return null;
	}

	/*
	 * (non-Javadoc) 活动窗口的标题名称, client only
	 * 
	 * @see tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserWindow()
	 */
	@Override
	public List<WindowProto> getUserWindow() {
		return null;
	}

	/*
	 * (non-Javadoc) 手机信息, mobile only
	 * 
	 * @see
	 * tv.pps.bi.proto.AchieveUserActivityManagerInterface#getUserDevice_info()
	 */
	@Override
	public DeviceInfo getUserDevice_info() {
		DeviceInfoService mDeviceInfoService = new DeviceInfoService(mContext);
		DeviceInfo mDeviceInfo = mDeviceInfoService.getUserDevice_info();
		if (null != mDeviceInfo) {
			return mDeviceInfo;
		}
		return null;
	}

}

package tv.pps.bi.proto;

import java.util.ArrayList;
import java.util.List;


import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.AppActivity;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.proto.model.WindowActivity;
import tv.pps.bi.proto.model.WindowProto;
import tv.pps.bi.utils.LogUtils;
import user_activity.UserActivityData;

/**
 * 进行构造ProtoBuff信息
 * @author jiangqingqing
 * @time 2013/09/03 14：11
 */
public class ProtoBuffUserActivityService {


	/**
	 * @deprecated 进程信息废弃,全部放入到getConstructorData(UserActivity pUserActivity)中进行获取
	 */
	private List<ProcessProto> mProcesses=new ArrayList<ProcessProto>();
	/**
	 * @deprecated 进程信息废弃,全部放入到getConstructorData(UserActivity pUserActivity)中进行获取
	 */
	private List<ProcessActivity> mProcessActivities = new ArrayList<ProcessActivity>();
	/**
	 * @deprecated 手机设备信息废弃，全部放入到getConstructorData(UserActivity pUserActivity)中进行获取
	 */
	private DeviceInfo mDeviceInfo = new DeviceInfo("111", "222", "333", "444",
			"555", "666", "root");
	/**
	 * @deprecated 第三方视频客户端播放历史记录废弃,全部放入到getConstructorData(UserActivity pUserActivity)中进行获取
	 */
	private List<ThirdPartyVideoActivity> mThirdPartyVideoActivities=new ArrayList<ThirdPartyVideoActivity>();
	/**
	 * @deprecated 手机中所有APP信息废弃,全部放入到getConstructorData(UserActivity pUserActivity)中进行获取 
	 */
	private List<App> mApps=new ArrayList<App>();
	/**
	 * @deprecated APP运行的情况信息废弃,全部放入到getConstructorData(UserActivity pUserActivity)中进行获取
	 */
	private List<AppActivity> mAppActivities=new ArrayList<AppActivity>();
	
	
	private UserActivityData.UserActivity.Builder builder_UserActivity;

	/**
	 * 构造函数,builder_UserActivity初始化,开始构造protobuff格式数据
	 */
	public ProtoBuffUserActivityService() {
		builder_UserActivity=UserActivityData.UserActivity.newBuilder();
	}

	/**
	 * 获取构造成功用户行为protobuff数据
	 * @param pUserActivity  需要进行构造的实体类对象
	 * @return byte[] 构造成功之后的protobuff数据
	 */
	public static int count = 0;
	public byte[] getConstructorData(UserActivity pUserActivity)
	{
		LogUtils.i("jiangqingqing", "第"+(++count)+"次发送数据 == "+pUserActivity.toString());
		builder_UserActivity.setUid(pUserActivity.getUid()); //唯一标示该客户端的匿名用户id。
		builder_UserActivity.setLogin(pUserActivity.getLogin()); //注册用户登录id
		builder_UserActivity.setPlatform(pUserActivity.getPlatform());//可取值：pps_ios | pps_android | pps_pc | iqiyi_ios | iqiyi_android | iqiyi_pc
		builder_UserActivity.setMac(pUserActivity.getMac());//设备mac地址
        builder_UserActivity.setModel(pUserActivity.getModel());//设备型号
        // GPS坐标，mobile only
		GPS mGps=pUserActivity.getGps();
		if(null!=mGps){
		UserActivityData.GPS.Builder builder_Gps=UserActivityData.GPS.newBuilder();
		builder_Gps.setAltitude(mGps.getAltitude());
		builder_Gps.setLatitude(mGps.getLatitude());
		builder_Gps.setLongitude(mGps.getLongitude());
		builder_UserActivity.setGps(builder_Gps);
		}
				
		//地图POI信息，gps坐标附近300米内的地图信息，mobile only
		List<String> mPois=pUserActivity.getPoi();
		int mPois_Size=mPois!=null?mPois.size():0;
		for (int i=0;i<mPois_Size;i++) {
			builder_UserActivity.addPoi(mPois.get(i));
		}

		//安装的APP信息以及使用情况，mobile only
	    List<App> mApps=pUserActivity.getInstalled_app();
	    if(null!=mApps&&mApps.size()>0)
	    {
	       
	       int mApps_Size=mApps.size();
	       for(int i=0;i<mApps_Size;i++)
	       {
	    	   UserActivityData.App.Builder builder_APP=UserActivityData.App.newBuilder();
	    	   builder_APP.setName(mApps.get(i).getName());
	    	   builder_APP.setVersion(mApps.get(i).getVersion());
	    	   
	    	   List<AppActivity> mAppActivities=mApps.get(i).getActivity();
	    	   if(null!=mAppActivities&&mAppActivities.size()>0)
	    	   {
	    		  int mAppActivity_Size=mAppActivities.size();
	    		  
	    		  for(int index=0;index<mAppActivity_Size;index++)
	    		  {
	    			  UserActivityData.AppActivity.Builder builder_AppActivity=UserActivityData.AppActivity.newBuilder();
	    			  builder_AppActivity.setStartTimestamp(mAppActivities.get(index).getStart_timestamp());
	    			  builder_AppActivity.setDuration(mAppActivities.get(index).getDuration());
	    			  builder_APP.addActivity(builder_AppActivity);

	    		  }
	    	   }
	    	   builder_UserActivity.addInstalledApp(builder_APP);
	       }			
		}
	    
	    //在百度等搜索网站的搜索关键词
	    List<String> mSearch_keywords=pUserActivity.getSearch_keyword();
	    int mSearch_keywords_Size=mSearch_keywords!=null?mSearch_keywords.size():0;
	    for(int i=0;i<mSearch_keywords_Size;i++)
	    {
	    	
	    	builder_UserActivity.addSearchKeyword(mSearch_keywords.get(i));
	    }
	    
	    //网页浏览记录
	    List<String> mUrls =pUserActivity.getUrl();
	    int mUrls_Size=mUrls!=null?mUrls.size():0;
	    for(int i=0;i>mUrls_Size;i++)
	    {
	    	builder_UserActivity.addUrl(mUrls.get(i));
	    }
	    
	    //开机时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	    List<String> mBoot_timestamps=pUserActivity.getBoot_timestamp();
	    int mBoot_timestamps_Size=mBoot_timestamps!=null?mBoot_timestamps.size():0;
	    for(int i=0;i<mBoot_timestamps_Size;i++)
	    {
	    	builder_UserActivity.addBootTimestamp(mBoot_timestamps.get(i));
	    }
	    
	    //关机时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	    List<String> mShutdown_timestamps = pUserActivity.getShutdown_timestamp();
	    int mShutdown_timestamps_Size=mShutdown_timestamps!=null?mShutdown_timestamps.size():0;
	    for(int i=0;i<mShutdown_timestamps_Size;i++)
	    {
	    	builder_UserActivity.addShutdownTimestamp(mShutdown_timestamps.get(i));	
	    }
	    
	    //打电话的时间，时长，mobile only
	    List<PhoneActivity> mPhoneActivities = pUserActivity.getPhone_activity();
	    if(null!=mPhoneActivities&&mPhoneActivities.size()>0)
	    {
	    	UserActivityData.PhoneActivity.Builder builder_PhoneActivity=UserActivityData.PhoneActivity.newBuilder();
	    	int mPhoneActivities_Size=mPhoneActivities.size();
	    	for(int i=0;i<mPhoneActivities_Size;i++)
	    	{
	    		builder_PhoneActivity.setStartTimestamp(mPhoneActivities.get(i).getStart_timestamp());
	    		builder_PhoneActivity.setDuration(mPhoneActivities.get(i).getDuration());
	    		builder_UserActivity.addPhoneActivity(builder_PhoneActivity);
	    	}
	    }

	    //短信发送时间戳，格式：“20130601130122”（YYYYmmddhhMMss），mobile only
	    List<String> mSms_sent_timestamps = pUserActivity.getSms_sent_timestamp();
	    int mSms_sent_timestamps_Size=mSms_sent_timestamps!=null?mSms_sent_timestamps.size():0;
        for(int i=0;i<mSms_sent_timestamps_Size;i++)
        {
        	builder_UserActivity.addSmsSentTimestamp(mSms_sent_timestamps.get(i));
        }
	    
        //在优酷等竞品上看的视频情况
        List<ThirdPartyVideoActivity> mThirdPartyVideoActivitys=pUserActivity.getThird_party_video_activity();
        if(null!=mThirdPartyVideoActivitys&&mThirdPartyVideoActivitys.size()>0)
        {
        	UserActivityData.ThirdPartyVideoActivity.Builder builder_ThirdPartyVideo=UserActivityData.ThirdPartyVideoActivity.newBuilder();
        	int mThirdPartyVideoActivitys_Size=mThirdPartyVideoActivitys.size();
        	for(int i=0;i<mThirdPartyVideoActivitys_Size;i++)
        	{
        		builder_ThirdPartyVideo.setProvider(mThirdPartyVideoActivitys.get(i).getProvider());
        		builder_ThirdPartyVideo.setTimestamp(mThirdPartyVideoActivitys.get(i).getTimestamp());
        		builder_ThirdPartyVideo.setVideoName(mThirdPartyVideoActivitys.get(i).getVideo_name());
        		builder_UserActivity.addThirdPartyVideoActivity(builder_ThirdPartyVideo);
        	}   	
        } 
         
        //启动了哪些进程，进程名称, client only
         List<ProcessProto> mProcesses=pUserActivity.getProcess();
         if(null!=mProcesses&&mProcesses.size()>0)
         {
        	 
        	 int mProcesses_Size=mProcesses.size();
        	 for(int i=0;i<mProcesses_Size;i++)
        	 {
        		 UserActivityData.Process.Builder builder_Process=UserActivityData.Process.newBuilder();
        		 builder_Process.setName(mProcesses.get(i).getName());
        		 List<ProcessActivity>  mProcessActivities= mProcesses.get(i).getProcessActivities();
        		 if(null!=mProcessActivities&&mProcessActivities.size()>0)
        		 {
        			 UserActivityData.ProcessActivity.Builder builder_ProcessActivity=UserActivityData.ProcessActivity.newBuilder();
        			 int mProcessActivities_Size=mProcessActivities.size();
        			 for(int index=0;index<mProcessActivities_Size;index++)
        			 {
        				 builder_ProcessActivity.setStartTimestamp(mProcessActivities.get(index).getStart_timestamp());
        				 builder_ProcessActivity.setDuration(mProcessActivities.get(index).getDuration());
        				 builder_Process.addActivity(builder_ProcessActivity);
        			 }
        		 }
        		 builder_UserActivity.addProcess(builder_Process);
        	}
         }
        
        //活动窗口的标题名称, client only
         List<WindowProto> mWindows=  pUserActivity.getWindow();
         if(null!=mWindows&&mWindows.size()>0)
         {
        	 
        	 int mWindows_Size=mWindows.size();
        	 for(int i=0;i<mWindows_Size;i++)
        	 {
        		 UserActivityData.Window.Builder builder_Window=UserActivityData.Window.newBuilder();
        		 builder_Window.setName(mWindows.get(i).getName());
        		 List<WindowActivity> mWindowActivities= mWindows.get(i).getActivity();
        		 if(null!=mWindowActivities&&mWindowActivities.size()>0)
        		 {
        			 UserActivityData.WindowActivity.Builder builder_WindowActivity=UserActivityData.WindowActivity.newBuilder();
        			 int mWindowActivities_Size=mWindowActivities.size();
        			 for(int index=0;index<mWindowActivities_Size;index++)
        			 {
        				 builder_WindowActivity.setStartTimestamp(mWindowActivities.get(index).getStart_timestamp());
        				 builder_WindowActivity.setDuration(mWindowActivities.get(index).getDuration());
        				 builder_Window.addActivity(builder_WindowActivity);
        			 }
        		 }
        		 builder_UserActivity.addWindow( builder_Window);
        	 }
        	 
         }
        //手机信息, mobile only
        DeviceInfo mDeviceInfo= pUserActivity.getDevice_info();
        if(null!=mDeviceInfo)
        {
            UserActivityData.DeviceInfo.Builder builder_DeviceInfo=UserActivityData.DeviceInfo.newBuilder();
            builder_DeviceInfo.setImei(mDeviceInfo.getImei());
            builder_DeviceInfo.setImsi(mDeviceInfo.getImsi());
            builder_DeviceInfo.setManufacturer(mDeviceInfo.getManufacturer());
            builder_DeviceInfo.setModel(mDeviceInfo.getModel());
            builder_DeviceInfo.setScreenResolution(mDeviceInfo.getScreen_resolution());
            builder_DeviceInfo.setOsVersion(mDeviceInfo.getOs_version());
            builder_DeviceInfo.setOsCustermize(mDeviceInfo.getOs_custermize());
            builder_UserActivity.setDeviceInfo(builder_DeviceInfo); 
        }
        
		UserActivityData.UserActivity info_UserActivity=builder_UserActivity.build();
		return info_UserActivity.toByteArray();
	}

	
	
	
	
	/**
	 * 构造手机中APP的信息
	 * @return builder_App
	 * @deprecated 数据在getConstructorData(UserActivity pUserActivity)中进行构造,本方法暂且废弃
	 */
	public UserActivityData.App.Builder getAppBuilder()
	{
		UserActivityData.App.Builder builder_App=UserActivityData.App.newBuilder();
	    //获取手机APP的使用时间
		//获取方法
		if(null!=mApps&&mApps.size()>=0)
		{
			int count=mApps.size();
			for(int i=0;i<count;i++)
			{
				builder_App.setName(mApps.get(i).getName());
				builder_App.setVersion(mApps.get(i).getVersion());
				mAppActivities=mApps.get(i).getActivity();
				if(null!=mAppActivities&&mAppActivities.size()>=0)
				{
				  	for (AppActivity mAppActivity : mAppActivities) {
						UserActivityData.AppActivity.Builder builder=UserActivityData.AppActivity.newBuilder();
						builder.setStartTimestamp(mAppActivity.getStart_timestamp());
						builder.setDuration(mAppActivity.getDuration());
						builder_App.addActivity(builder);
					}
				}
			}
		}
		
		return builder_App;
	}
	
	
	/**
	 * 获取第三方视频播放器的播放记录
	 * @return builder_ThirdPartyVideo
	 * @deprecated 数据在getConstructorData(UserActivity pUserActivity)中进行构造,本方法暂且废弃
	 */
	public UserActivityData.ThirdPartyVideoActivity.Builder getThirdPartyVideoBuilder()
	{
		UserActivityData.ThirdPartyVideoActivity.Builder builder_ThirdPartyVideo=UserActivityData.ThirdPartyVideoActivity.newBuilder();
        //获取播放记录对象集合信息
		//获取方法
		if(null!=mThirdPartyVideoActivities&&mThirdPartyVideoActivities.size()>=0)
		{
			
		}
		return builder_ThirdPartyVideo;
	}
	
	
	
	
	/**
	 * 构造手机信息
	 * 
	 * @return builder_DeviceInfo
	 * @deprecated 数据在getConstructorData(UserActivity pUserActivity)中进行构造,本方法暂且废弃
	 */
	public UserActivityData.DeviceInfo.Builder getDeviceInfoBuilder() {
		UserActivityData.DeviceInfo.Builder builder_DeviceInfo = UserActivityData.DeviceInfo
				.newBuilder();
		// 获取信息
		// 函数
		if (null != mDeviceInfo) {
			builder_DeviceInfo.setImei(mDeviceInfo.getImei());
			builder_DeviceInfo.setImsi(mDeviceInfo.getImsi());
			builder_DeviceInfo.setManufacturer(mDeviceInfo.getManufacturer());
			builder_DeviceInfo.setModel(mDeviceInfo.getModel());
			builder_DeviceInfo.setScreenResolution(mDeviceInfo
					.getScreen_resolution());
			builder_DeviceInfo.setOsVersion(mDeviceInfo.getOs_version());
			builder_DeviceInfo.setOsCustermize(mDeviceInfo.getOs_custermize());
		}
		return builder_DeviceInfo;
	}

	/**
	 * 获取进行情况信息
	 * @deprecated 数据在getConstructorData(UserActivity pUserActivity)中进行构造,本方法暂且废弃
	 * @return builder_Process
	 */
	@SuppressWarnings("unused")
	private UserActivityData.Process.Builder getProcessBuilder() {
		UserActivityData.Process.Builder builder_Process = UserActivityData.Process
				.newBuilder();
        
		if (null != mProcesses && mProcesses.size() >= 0) {
			int count = mProcesses.size();
			for (int i = 0; i < count; i++) {
				builder_Process.setName(mProcesses.get(i).getName());
			 	// 获取单个进程信息集合
				mProcessActivities = mProcesses.get(i).getProcessActivities();
				if (null != mProcessActivities
						&& mProcessActivities.size() >= 0) {
					for (ProcessActivity mProcessActivity : mProcessActivities) {
						UserActivityData.ProcessActivity.Builder builder = UserActivityData.ProcessActivity
								.newBuilder();
						builder.setStartTimestamp(mProcessActivity
								.getStart_timestamp());
						builder.setDuration(mProcessActivity.getDuration());
						builder_Process.addActivity(builder);
					}
				}
			}
		}
		return builder_Process;
	}

	/**
	 * 获取单独进程信息 Builder 包括(进程打开时间戳,打开时长)
	 * @deprecated 数据在getConstructorData(UserActivity pUserActivity)中进行构造,本方法暂且废弃
	 * @return
	 */
	@SuppressWarnings("unused")
	private UserActivityData.ProcessActivity.Builder getProcessActivityBuilder() {
		// 进程情况
		UserActivityData.ProcessActivity.Builder builder_ProcessActivity = UserActivityData.ProcessActivity
				.newBuilder();
		for (ProcessActivity processActivity : mProcessActivities) {
			builder_ProcessActivity.setStartTimestamp(processActivity
					.getStart_timestamp());
			builder_ProcessActivity.setDuration(processActivity.getDuration());
		}
		builder_ProcessActivity.build();

		return builder_ProcessActivity;
	}

}

package com.jiangqq.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import user_activity.UserActivityData;
import com.jiangqq.bean.App;
import com.jiangqq.bean.AppActivity;
import com.jiangqq.bean.DeviceInfo;
import com.jiangqq.bean.GPS;
import com.jiangqq.bean.PhoneActivity;
import com.jiangqq.bean.ProcessActivity;
import com.jiangqq.bean.ProcessProto;
import com.jiangqq.bean.ThirdPartyVideoActivity;
import com.jiangqq.bean.WindowActivity;
import com.jiangqq.bean.WindowProto;

/**
 * Servlet implementation class UserActivityServlet
 */
public class UserActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserActivityServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 接收来自客户端的post请求信息，进行解析
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        ServletInputStream sis=request.getInputStream();//获取post请求数据
        byte[] byteInfo=new byte[1024];
        int length=0;
        StringBuffer sb=new StringBuffer();
        while((length=sis.read(byteInfo))!=-1)
        {
        	sb.append(new String(byteInfo, 0, length));
        }
        
        System.out.println("投递的数据的为:"+sb.toString());
        
        
        Base64 base64=new Base64();
        byte[] debytes=base64.decodeBase64(new String(sb).getBytes());
        
        //进行逆反,解析传过来的ProtoBuff格式的数据
		UserActivityData.UserActivity infoUserActivity;
		try {
			infoUserActivity=UserActivityData.UserActivity.parseFrom(debytes);
			if(infoUserActivity!=null)
			{
	         String uid= infoUserActivity.getUid();
	         String login=infoUserActivity.getLogin();
	         String platform=infoUserActivity.getPlatform();
	         String mac=infoUserActivity.getMac();
	         String model=infoUserActivity.getModel();
	         System.out.println("UID = "+uid);
	         System.out.println("LOGIN = "+login);
	         System.out.println("PLATFORM = "+platform);
	         System.out.println("MAC = "+mac);
	         System.out.println("MODEL = "+model);
	         //GPS信息
	         GPS mGPS=new GPS();
	         mGPS.setAltitude(infoUserActivity.getGps().getAltitude());
	         mGPS.setLatitude(infoUserActivity.getGps().getLatitude());
	         mGPS.setLongitude(infoUserActivity.getGps().getLongitude());
	         
	         //POI信息
	         List<String>  mPOIs = infoUserActivity.getPoiList();
             for (String string : mPOIs) {
				System.out.println("POI = "+string);
			}
            //installed_app 
            //存储的集合
            List<App> mApps=new ArrayList<App>();
            List<AppActivity> mAppActivities=null;
            App mApp;
            AppActivity mActivity;
            List<user_activity.UserActivityData.App> mApps_proto= infoUserActivity.getInstalledAppList();
            for (user_activity.UserActivityData.App app_proto : mApps_proto) {
            	mApp=new App();
            	mApp.setName(app_proto.getName());
             	mApp.setVersion(app_proto.getVersion());
                List<user_activity.UserActivityData.AppActivity> mActivities_proto=app_proto.getActivityList();
                mAppActivities=new ArrayList<AppActivity>();
            	for (user_activity.UserActivityData.AppActivity appActivity : mActivities_proto) {
            		mActivity=new AppActivity();
            		mActivity.setStart_timestamp(appActivity.getStartTimestamp());
            		mActivity.setDuration(appActivity.getDuration());
            		mAppActivities.add(mActivity);
				}
            	mApp.setActivity(mAppActivities);
            	mApps.add(mApp);
			 } 
        	for (App app : mApps) {
				System.out.println("APP信息为:"+app.toString());
			}
        	
			//search_keyword
			List<String> mSearchKeywords = infoUserActivity.getSearchKeywordList();
			for (String string : mSearchKeywords) {
				System.out.println("SearchKey = "+string);
			}
			
			//url
			List<String> mUrls = infoUserActivity.getUrlList();
			for (String string : mUrls) {
				System.out.println("Url = "+string);
			}
			
			//boot_timestamp
			List<String> mBootTimestamps =  infoUserActivity.getBootTimestampList();
			for (String string : mBootTimestamps) {
				System.out.println("BootTimestamp = "+string);
			}
			
			//shutdown_timestamp
			List<String> mShutdownTimestamps = infoUserActivity.getShutdownTimestampList();
			for (String string : mShutdownTimestamps) {
				System.out.println("ShutdownTimestamp = "+string);
			}
			
			//phone_activity
			List<PhoneActivity> mPhoneActivities=new ArrayList<PhoneActivity>();
			PhoneActivity mPhoneActivity;
			List<user_activity.UserActivityData.PhoneActivity> mPhoneActivity_proto =  infoUserActivity.getPhoneActivityList();
			for (user_activity.UserActivityData.PhoneActivity phoneActivity : mPhoneActivity_proto) {
				mPhoneActivity=new PhoneActivity();
				mPhoneActivity.setStart_timestamp(phoneActivity.getStartTimestamp());
				mPhoneActivity.setDuration(phoneActivity.getDuration());
				mPhoneActivities.add(mPhoneActivity);
			}
			for (PhoneActivity phoneActivity : mPhoneActivities) {
				System.out.println("打电话信息为:"+phoneActivity.toString());
			}
			
			//sms_sent_timestamp
			List<String> mSmsSentTimestamps = infoUserActivity.getSmsSentTimestampList();
			for (String string : mSmsSentTimestamps) {
				System.out.println("SmsSentTimestamp = "+string);
			}
			
			//third_party_video_activity
			List<ThirdPartyVideoActivity> mThirdPartyVideoActivities=new ArrayList<ThirdPartyVideoActivity>();
			ThirdPartyVideoActivity mThirdPartyVideoActivity;
			List<user_activity.UserActivityData.ThirdPartyVideoActivity> mThirdPartyVideoActivity_proto = infoUserActivity.getThirdPartyVideoActivityList();
			for (user_activity.UserActivityData.ThirdPartyVideoActivity thirdPartyVideoActivity : mThirdPartyVideoActivity_proto) {
				mThirdPartyVideoActivity=new ThirdPartyVideoActivity();
				mThirdPartyVideoActivity.setProvider(thirdPartyVideoActivity.getProvider());
				mThirdPartyVideoActivity.setTimestamp(thirdPartyVideoActivity.getTimestamp());
				mThirdPartyVideoActivity.setVideo_name(thirdPartyVideoActivity.getVideoName());
				mThirdPartyVideoActivities.add(mThirdPartyVideoActivity);
			}
			for (ThirdPartyVideoActivity thirdPartyVideoActivity : mThirdPartyVideoActivities) {
				System.out.println("第三方视频的播放记录信息为:"+thirdPartyVideoActivity.toString());
			}
			
			//process
			List<ProcessProto> mProcessProtos=new ArrayList<ProcessProto>();
		    List<ProcessActivity> mProcessActivities=null;
			ProcessProto mProcessProto;
			ProcessActivity mProcessActivity;
			List<user_activity.UserActivityData.Process> mProcess_proto = infoUserActivity.getProcessList();
			for (user_activity.UserActivityData.Process process : mProcess_proto) {
				mProcessProto=new ProcessProto();
				mProcessProto.setName(process.getName());
				List<user_activity.UserActivityData.ProcessActivity> mActivities_proto= process.getActivityList();
				mProcessActivities=new ArrayList<ProcessActivity>();
				for (user_activity.UserActivityData.ProcessActivity processActivity : mActivities_proto) {
					mProcessActivity=new ProcessActivity();
					mProcessActivity.setStart_timestamp(processActivity.getStartTimestamp());
					mProcessActivity.setDuration(processActivity.getDuration());
					mProcessActivities.add(mProcessActivity);
				}
				mProcessProto.setProcessActivities(mProcessActivities);
				mProcessProtos.add(mProcessProto);
			}
			for (ProcessProto process : mProcessProtos) {
				System.out.println("进程信息为："+process.toString());
			}
			
			//window
			List<WindowProto> mWindowProtos=new ArrayList<WindowProto>();
			List<WindowActivity> mWindowActivities=new ArrayList<WindowActivity>();
			WindowProto mWindowProto;
            WindowActivity mWindowActivity;
			List<user_activity.UserActivityData.Window> mWindows_proto = infoUserActivity.getWindowList();
			for (user_activity.UserActivityData.Window window : mWindows_proto) {
				mWindowProto=new WindowProto();
				mWindowProto.setName(window.getName());
				List<user_activity.UserActivityData.WindowActivity> mWindowActivities_proto = window.getActivityList();
				for (user_activity.UserActivityData.WindowActivity windowActivity : mWindowActivities_proto) {
					mWindowActivity=new WindowActivity();
					mWindowActivity.setStart_timestamp(windowActivity.getStartTimestamp());
					mWindowActivity.setDuration(windowActivity.getDuration());
					mWindowActivities.add(mWindowActivity);
				}
				mWindowProto.setActivity(mWindowActivities);
				mWindowProtos.add(mWindowProto);
			}
			
           //device_info
			DeviceInfo mDeviceInfo=new DeviceInfo();
			user_activity.UserActivityData.DeviceInfo mDeviceInfo_proto= infoUserActivity.getDeviceInfo();
			mDeviceInfo.setImei(mDeviceInfo_proto.getImei());
			mDeviceInfo.setImsi(mDeviceInfo_proto.getImsi());
			mDeviceInfo.setManufacturer(mDeviceInfo_proto.getManufacturer());
			mDeviceInfo.setModel(mDeviceInfo_proto.getModel());
			mDeviceInfo.setOs_custermize(mDeviceInfo_proto.getOsCustermize());
			mDeviceInfo.setOs_version(mDeviceInfo_proto.getOsVersion());
			mDeviceInfo.setScreen_resolution(mDeviceInfo_proto.getScreenResolution());
			System.out.println("手机信息为:"+mDeviceInfo.toString());

			}
		} catch (Exception e) {
              e.printStackTrace();
		}
	}
}

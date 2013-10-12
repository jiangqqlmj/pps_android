package tv.pps.bi.service;

import java.util.Date;
import java.util.List;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.db.DBOperation;
import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.proto.model.AppActivity;
import tv.pps.bi.utils.DataFormat;
import tv.pps.bi.utils.LogUtils;
import tv.pps.bi.utils.Utils;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


@SuppressLint("HandlerLeak")
public class ListenService extends Service {
	private static final String TAG = "bi";
	private ActivityManager am;
	private String packageName = "tv.pps.bi.activity";//应用包名
	boolean isFirst = true;
	AppActivity data;
	DBAPPManager db;
	private Context mContext;
	private DBOperation operation;
	private static final int APPSTATUS =1;//app状态任务
 	private static final int USERINFO=3; //每隔十分分钟去获取数据
 	int count = 0;
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {

	}
	
	
	SharedPreferences sp;
	public void onCreate() {
		super.onCreate();
		mContext=this;

		System.currentTimeMillis();
		sendAlarmReceiver();
		data = new AppActivity();
		db = DBAPPManager.getDBManager(getApplicationContext());
		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		//启动任务
		handler.sendEmptyMessage(APPSTATUS);
		//启动查询用户信息任务
		handler.sendEmptyMessage(USERINFO);
		
		
		
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case APPSTATUS:
				try {
					getAppStatus();
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(APPSTATUS, IntervalTimeConstance.START_APPUSEINFO_SEARCH_TIME);//10s中监听一次，
				break;
			case USERINFO://每隔三十分钟进行一次用户行为查询，并且插入数据库
				count++;
				operation = new DBOperation(mContext);
				sp = getSharedPreferences("db",0);
				boolean isFirst = sp.getBoolean("isFirst", false);
				if(!isFirst){
					operation.initializeTableControl(mContext);
					Editor edit = sp.edit();
					edit.putBoolean("isFirst", true);
					edit.commit();
				}
				//gps信息无增量插入数据库
				LogUtils.v("bi","第"+count+ "次--开始插入数据库--"+Utils.formatTimeStamp(System.currentTimeMillis(),"yyyyMMddhhmmss"));
				operation.insertTableGPS();
				//url 信息增量信息插入数据库
				operation.insertUrlIntoTable();
				long url_timestamp = operation.queryTimestamp(mContext,DBConstance.TABLE_URL);
				if(url_timestamp!=0)
					operation.updateTimestampInControlTable(mContext,"url", url_timestamp);
				// 	开机时间增量信息插入数据库
				operation.insertBootTimeIntoTable();
				long boot_timestamp = operation.queryTimestamp(mContext,DBConstance.TABLE_BOOT_TIME);
				if(boot_timestamp!=0L)
					operation.updateTimestampInControlTable(mContext,"boot", boot_timestamp);
				//关机时间增量信息插入数据库
				operation.insertShutTimeIntoTable();
				long shut_timestamp = operation.queryTimestamp(mContext,DBConstance.TABLE_SHUT_TIME);
				if(shut_timestamp!=0L)
					operation.updateTimestampInControlTable(mContext,"shut", shut_timestamp);
				//电话增量信息插入数据库
//				operation.insertTablePhone();
//				long phone_timestamp = operation.queryTimestamp(mContext,DBConstance.TABLE_PHONE);
//				if(phone_timestamp!=0L)
//					operation.updateTimestampInControlTable(mContext,"phone", phone_timestamp);
				//短信增量信息插入数据库
//				operation.insertTableSMS();
//				long sms_timestamp = operation.queryTimestamp(mContext,DBConstance.TABLE_SMS);
//				if(sms_timestamp!=0L)
//					operation.updateTimestampInControlTable(mContext,"sms", sms_timestamp);
				operation.close();
				operation = null;
				handler.sendEmptyMessageDelayed(USERINFO, IntervalTimeConstance.START_USERINFO_SEARCH_TIME);
			 break;
			default:
				break;
			}
		}
	};
	
	long start = 0;
	long stop = 0;
	private void getAppStatus() throws Exception {
		List<RunningTaskInfo> taskinfo = am.getRunningTasks(1);
		if (taskinfo.size() > 0) {
			String running = taskinfo.get(0).topActivity.getPackageName();
			 boolean isUserApp = isUserApp(getPackageManager().getPackageInfo(running, 0));
			if (isFirst&&isUserApp) {// 程序第一次启动就记录下开始时间
				start = System.currentTimeMillis();
				Date curDate = new Date(start);
				String start_time = DataFormat.formatData(curDate);
				LogUtils.v(TAG, running + "  开始时间：" + start_time);
				data.setPackageName(running);
				data.setStart_timestamp(start_time);
				isFirst = false;
			} else if (!running.equals(packageName)) {
				stop = System.currentTimeMillis();
				Date curDate = new Date(stop);
				String stop_time = DataFormat.formatData(curDate);
				LogUtils.v(TAG, packageName + " 结束时间：" + stop_time);
				int duration = (int)(stop - start)/1000; //d单位为s
				data.setDuration(duration);
				db.save(data); //存放到数据库中
				isFirst = true;
			}
			packageName = running;
		}
	}

	/**
	 * 判断是否是用户安装的应用
	 * 
	 * @param pInfo
	 * @return
	 */
	public boolean isUserApp(PackageInfo pInfo) {
		if(null == pInfo){
			return false;
		}
		return (pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		removeAlarmReceiver();
		operation.close();
		operation = null;
		super.onDestroy();
	}

	private void sendAlarmReceiver(){
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent("deliver");
		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+IntervalTimeConstance.START_DELIVER_SERVICE_TIME , IntervalTimeConstance.START_DELIVER_SERVICE_TIME, sender);//从2个小时后开始、每隔2小时发送一次
	}
	
	private void removeAlarmReceiver(){
  	   AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
  	   Intent intent_receiver = new Intent("tv.pps.alarmReceiver");
  	   PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intent_receiver, PendingIntent.FLAG_UPDATE_CURRENT);
  	   alarmMgr.cancel(pendIntent);//取消intent发送的广播消息
	}

}

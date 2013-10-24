package tv.pps.bi.db;

import java.util.ArrayList;
import java.util.List;

import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.proto.biz.CallLogService;
import tv.pps.bi.proto.biz.GPSInfoService;
import tv.pps.bi.proto.biz.MessageInfoService;
import tv.pps.bi.proto.biz.ShutdownInfoService;
import tv.pps.bi.proto.biz.URLService;
import tv.pps.bi.proto.model.Bootup;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.NetTime;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.SMS;
import tv.pps.bi.proto.model.SendTime;
import tv.pps.bi.proto.model.Shutdown;
import tv.pps.bi.proto.model.URLInfo;
import tv.pps.bi.utils.LogUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBOperation {

	public final static String TAG = "DBOperation";
	SQLiteDatabase db;
	DBHelper helper;
	Context context;
	public static DBOperation operation;
	int count = 0;

	public DBOperation(Context context) {
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
		LogUtils.i(TagConstance.TAG_DATABASE, "数据库" + DBConstance.DB_NAME + "打开或创建成功");
	}

	public static DBOperation getDBOperation(Context context) {
		synchronized (operation) {

			if (operation == null) {
				operation = new DBOperation(context);
			}
		}
		return operation;
	}

	public void close() {

		try {
			if (db != null)
				db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtils.i(TagConstance.TAG_DATABASE, "关闭数据库" + DBConstance.DB_NAME);
	}

	
	/**
	 * 网络断开与否 实体进行插入
	 * @auther jiangqingqing
	 * @param pNetTime
	 * @return
	 */
	public boolean insertNetTime(NetTime pNetTime)
	{
	    try {
	    	ContentValues mContentValues=new ContentValues();
		    mContentValues.put("net_time", pNetTime.getNettime());
		    mContentValues.put("net_flag", pNetTime.getFlag());
		    long result = db.insert(DBConstance.TABLE_NET_INFO, null, mContentValues);
		    if(result!=-1)
		    {
		    	return true;
		    }
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "表数据插入异常");
		}
		
		return false;
	}
	

	/**
	 * 进行删除
	 * @auther jiangqingqing
	 * @param pNetTime
	 * @return
	 */
	public boolean deleteNetTime(NetTime pNetTime)
	{
		
		 try {
			int result= db.delete(DBConstance.TABLE_NET_INFO, "net_flag = ?", new String[]{String.valueOf(pNetTime.getFlag())});
		    if(result!=0)
		    {
		    	return true;
		    }
		 } catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "表数据删除异常");
		}
		 return false;
	}
	
	
	/**
	 * 
	 * @auther jiangqingqing
	 * @param pNetTime
	 * @return
	 */
	public boolean updateNetTime(NetTime pNetTime)
	{
		try {
			ContentValues mContentValues=new ContentValues();
			mContentValues.put("net_time", pNetTime.getNettime());
		    mContentValues.put("net_flag", pNetTime.getFlag());
			int result= db.update(DBConstance.TABLE_NET_INFO, mContentValues, "net_flag = ?", new String[]{String.valueOf(pNetTime.getFlag())});
			if(result!=0)
			{
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "表数据更新异常");
		}
		return false;
	}
	
	
	/**
	 * 删除所有的网络变化表中的数据
	 * @auther jiangqingqing
	 * @return
	 */
			
	public boolean delteAllNetTime()
	{
		try {
		  int result=db.delete(DBConstance.TABLE_NET_INFO, null, null);
		  if(result!=0)
		  {
			  return true;
		  }	
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "表数据删除异常");
		}
		return false;
	}
	
	
	/**
	 * 把投递记录 插入到数据库中
	 * @auther jiangqingqing
	 * @param pSendTime 投递数据实体类
	 * @return
	 */
	public boolean insertSendTime(SendTime pSendTime)
	{
		try {
			ContentValues mContentValues=new ContentValues(1);
			mContentValues.put("send_time", String.valueOf(pSendTime.getSendtime()));
			long result=db.insert(DBConstance.TABLE_SEND_DATA, null, mContentValues);
			if(result!=-1)
			{
				return true;
			}
			
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "send_data" + "表数据插入异常");
		}
		return false;
	}
	

	/**
	 * 删除投递数据记录表
	 * @auther jiangqingqing
	 * @return
	 */
	public boolean deleteSendTime()
	{
		try {
			long result= db.delete(DBConstance.TABLE_SEND_DATA, null, null);
			if(result!=0)
			{
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "send_data" + "表数据删除异常");
		}
		return false;
	}
	
	
	/**
	 * 获取
	 * @return
	 */
	public SendTime getSendTime()
	{
		try {
			List<SendTime> mList=new ArrayList<SendTime>();
			SendTime mSendTime;
			Cursor mCursor= db.query(DBConstance.TABLE_SEND_DATA, new String[]{"send_time"}, null, null, null, null, null);
		    if(mCursor!=null&&mCursor.getCount()>=0)
		    {
		    	while(mCursor.moveToNext())
		    	{
		    		mSendTime=new SendTime();
		    		mSendTime.setSendtime(Long.valueOf(mCursor.getString(mCursor.getColumnIndex("send_time"))));
		    		mList.add(mSendTime);
		    	}
		    	return mList.get(mList.size()-1);
		    }
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "send_data" + "表数据查询异常");
		}
		
		return null;
	}
	
	/**
	 * 插入数据
	 * 
	 * @param context
	 * @param table
	 * @param cv
	 * @return
	 */
	public boolean insertIntoTable(Context context, String table,
			ContentValues cv) {// 插入数据表
		db.beginTransaction();
		try {
			long rowid = db.insert(table, null, cv);
			if (rowid != -1) {
				db.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "表数据插入异常");
		} finally {
			db.endTransaction();
		}
		return false;
	}

	/**
	 * 删除数据表
	 * 
	 * @param context
	 * @param table
	 * @return
	 */
	public boolean deleteTable(Context context, String table) {
		db.beginTransaction();
		try {
			int id = db.delete(table, null, null);
			if (id != -1) {
				db.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "表删除异常");
		} finally {
			db.endTransaction();
		}
		return false;
	}

	/**
	 * 更新数据表
	 * 
	 * @param context
	 * @param table
	 * @param cv
	 * @return
	 */
	public boolean updateTable(Context context, String table, ContentValues cv) {
		db.beginTransaction();
		try {
			int id = db.update(table, cv, null, null);
			if (id != -1) {
				db.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "表更新异常");
		} finally {
			db.endTransaction();
		}
		return false;
	}


	/**
	 * 查询数据表
	 * 
	 * @param context
	 * @param table
	 * @return
	 */
	public Cursor queryTable(Context context, String table) {

		return db.query(table, null, null, null, null, null, " timestamp desc");

	}

	/**
	 * 查询表中的时间戳字段
	 * 
	 * @param context
	 * @param table
	 * @return
	 */
	public long queryTimestamp(Context context, String table) {
		Cursor c = null;
		try {
			c = db.query(table, null, null, null, null, null, " timestamp desc");
			if (c.getCount() == 0) {
				try {
					if (c != null)
						c.close();
				} catch (Exception e) {
					LogUtils.i(TagConstance.TAG_DATABASE, table + "表游标关闭异常");
				}
				return 0L;
			} else {
				c.moveToNext();
				long result = c.getLong(c.getColumnIndex("timestamp"));
				try {
					if (c != null)
						c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		} catch (Exception e1) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "表查询时间戳异常");
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

	/**
	 * 查询control table的timestamp字段
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public long queryTimestampInControlTable(Context context, String type) {

		Cursor c = null;
		try {
			c = db.query(DBConstance.TABLE_INFOMATION_CONTROL,
					new String[] { "timestamp" }, " type = ? ",
					new String[] { type }, null, null, " timestamp asc");

			if (null != c && c.getCount() > 0) {

				c.moveToNext();
				long result = c.getLong(c.getColumnIndex("timestamp"));
				try {
					if (c != null)
						c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;

			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "control表查询时间戳异常");
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

	/**
	 * 更新控制表的timestamp字段
	 * 
	 * @param context
	 * @param type
	 * @param timestamp
	 * @return
	 */
	public boolean updateTimestampInControlTable(Context context, String type,
			long timestamp) {
		String table = DBConstance.TABLE_INFOMATION_CONTROL;
		ContentValues cv = new ContentValues();
		cv.put("timestamp", timestamp);
		int id = db.update(table, cv, " type = ? ", new String[] { type });
		if (id != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 初始化控制表
	 * 
	 * @param context
	 */
	public void initializeTableControl(Context context) {
		String table = DBConstance.TABLE_INFOMATION_CONTROL;
		String[] type = { "gps", "url", "boot", "shut", "phone", "sms" };
		long timestamp = 0L;
		ContentValues cv = new ContentValues();
		for (int i = 0; i < type.length; i++) {
			cv.put("type", type[i]);
			cv.put("timestamp", timestamp);
			db.insert(table, null, cv);
		}
		
		//初始化投递时间
	    insertSendTime(new SendTime(System.currentTimeMillis()));	
	   
		
		
		LogUtils.i(TagConstance.TAG_DATABASE, DBConstance.TABLE_INFOMATION_CONTROL + "数据表初始化成功");
	}

	/**
	 * 更新控制表的时间戳
	 * 
	 * @param table
	 * @param type
	 * @param value
	 */
	public void updateTableControl(String table, String type, String value) {

		ContentValues cv = new ContentValues();
		cv.put(type, value);
		updateTable(context, table, cv);
	}

	/**
	 * 删除数据表中的所有数据
	 * 
	 * @param table
	 */
	public void deleteRecordsInTable(String table) {

		deleteTable(context, table);
	}

	/**
	 * 插入数据到url数据表
	 */
	public void insertUrlIntoTable() {
		URLService info = new URLService();
		long timestamp = queryTimestampInControlTable(context, "url");
		ArrayList<URLInfo> list = info.getSystemBrowserUrl(context, timestamp);
		int size = 0;
		if (list != null)
			size = list.size();
		LogUtils.v(TagConstance.TAG_DATABASE, "上次插入的最后一条url时间--" + timestamp + "--记录条数 = " + size);
		if (list != null) {
			for (URLInfo url : list) {
				String table = DBConstance.TABLE_URL;
				ContentValues cv = new ContentValues();
				cv.put("url", url.getUrl());
				cv.put("keywords", url.getKeywords());
				cv.put("timestamp", url.getTimestamp());
				insertIntoTable(context, table, cv);
			}
		}
	}

	/**
	 * 查询url数据表
	 * 
	 * @return
	 */

	public List<URLInfo> queryUrlInTable() {
		String table = DBConstance.TABLE_URL;
		URLInfo info = null;
		ArrayList<URLInfo> obj = new ArrayList<URLInfo>();
		Cursor c = null;
		try {
			c = queryTable(context, table);
			if (c != null) {
				while (c.moveToNext()) {
					info = new URLInfo();
					info.setUrl(c.getString(c.getColumnIndex("url")));
					info.setKeywords(c.getString(c.getColumnIndex("keywords")));
					info.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 插入一个shutdown 对象到数据表TABLE_SHUT_TIME
	 */
	public void insertShutTimeIntoTable() {

		ShutdownInfoService bootInfo = new ShutdownInfoService();
		long timestamp = queryTimestampInControlTable(context, "shut");
		Shutdown shut = bootInfo.getShutdownTime(context, timestamp);
		String str = " ！=null";
		if (shut == null)
			str = " ==null";
		LogUtils.v(TagConstance.TAG_DATABASE, "上次插入的最后一条关机时间--" + timestamp + "--关机时间  " + str);
		if (shut != null) {
			String table = DBConstance.TABLE_SHUT_TIME;
			ContentValues cv = new ContentValues();
			cv.put("shutdowntime", shut.getShutdowntime());
			cv.put("timestamp", shut.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	/**
	 * 在数据表TABLE_SHUT_TIME中查询多个shutdown对象
	 * 
	 * @return
	 */
	public List<Shutdown> queryShutTimeInTable() {

		String table = DBConstance.TABLE_SHUT_TIME;
		Shutdown shut = null;
		ArrayList<Shutdown> obj = new ArrayList<Shutdown>();
		Cursor c = null;
		try {
			c = db.query(table, null, null, null, null, null, " timestamp desc");
			if (c != null) {
				while (c.moveToNext()) {
					shut = new Shutdown();
					shut.setShutdowntime(c.getString(c
							.getColumnIndex("shutdowntime")));
					shut.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(shut);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 插入一个boot 对象到数据表TABLE_BOOT_TIME
	 */
	public void insertBootTimeIntoTable() {

		ShutdownInfoService bootInfo = new ShutdownInfoService();
		long timestamp = queryTimestampInControlTable(context, "boot");
		Bootup boot = bootInfo.getBootUpTime(timestamp);
		String str = "";
		if (boot == null)
			str = "";
		LogUtils.v(TagConstance.TAG_DATABASE, "上次插入的最后一条开机时间--" + timestamp + "--开机时间 " + str);
		if (boot != null) {
			String table = DBConstance.TABLE_BOOT_TIME;
			ContentValues cv = new ContentValues();
			cv.put("boottime", boot.getBoottime());
			cv.put("timestamp", boot.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	/**
	 * 在数据表TABLE_BOOT_TIME中查询多个boot对象
	 * 
	 * @return
	 */
	public List<Bootup> queryTableBootTime() {

		String table = DBConstance.TABLE_BOOT_TIME;
		Bootup boot = null;
		ArrayList<Bootup> obj = new ArrayList<Bootup>();
		Cursor c = null;

		try {
			c = queryTable(context, table);

			if (c != null) {
				while (c.moveToNext()) {
					boot = new Bootup();
					boot.setBoottime(c.getString(c.getColumnIndex("boottime")));
					boot.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(boot);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 在数据表TABLE_SMS 中插入多个SMS对象
	 */
	public void insertTableSMS() {

		MessageInfoService message = new MessageInfoService();
		long timestamp = queryTimestampInControlTable(context, "sms");
		ArrayList<SMS> smsList = message.getMessageInfo(context, timestamp);
		LogUtils.v(TagConstance.TAG_DATABASE, "上次插入的最后一条短信时间--" + timestamp + "--短信数量 = " + smsList.size());
		if (smsList != null) {
			for (SMS sms : smsList) {
				String table = DBConstance.TABLE_SMS;
				ContentValues cv = new ContentValues();
				cv.put("smstime", sms.getSmstime());
				cv.put("timestamp", sms.getTimestamp());
				insertIntoTable(context, table, cv);
			}
		}
	}

	/**
	 * 在数据表TABLE_SMS查询多个SMS对象
	 * 
	 * @return
	 */
	public List<SMS> queryTableSMS() {

		String table = DBConstance.TABLE_SMS;
		SMS sms = null;
		ArrayList<SMS> obj = new ArrayList<SMS>();
		Cursor c = null;
		try {
			c = queryTable(context, table);
			if (c != null) {
				while (c.moveToNext()) {
					sms = new SMS();
					sms.setSmstime(c.getString(c.getColumnIndex("smstime")));
					sms.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(sms);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 插入数据到电话表
	 */
	public void insertTablePhone() {
		CallLogService calllog = new CallLogService();
		long timestamp = queryTimestampInControlTable(context, "phone");
		ArrayList<PhoneActivity> phones = calllog.getCallLogInfo(context,
				timestamp);
		LogUtils.v(TagConstance.TAG_DATABASE,
				"上次插入的最后一条电话时间--" + timestamp + "-- 电话记录数量 = " + phones.size());
		if (phones != null) {
			for (PhoneActivity phone : phones) {
				String table = DBConstance.TABLE_PHONE;
				ContentValues cv = new ContentValues();
				cv.put("teletime", phone.getStart_timestamp());
				cv.put("duration", phone.getDuration());
				cv.put("timestamp", phone.getTimestamp());
				insertIntoTable(context, table, cv);
			}
		}
	}

	/**
	 * 查询电话记录数据表
	 * 
	 * @return
	 */
	public List<PhoneActivity> queryTablePhone() {

		String table = DBConstance.TABLE_PHONE;
		PhoneActivity phone = null;
		ArrayList<PhoneActivity> obj = new ArrayList<PhoneActivity>();
		Cursor c = null;
		try {
			c = queryTable(context, table);
			if (c != null) {
				while (c.moveToNext()) {
					phone = new PhoneActivity();
					phone.setStart_timestamp(c.getString(c
							.getColumnIndex("teletime")));
					phone.setDuration(c.getInt(c.getColumnIndex("duration")));
					phone.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(phone);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 插入一个GPS对象到GPS表
	 */
	public void insertTableGPS() {
		GPSInfoService gps = new GPSInfoService(context);
		GPS obj = gps.getLocations(context);
		if (obj != null) {
			String table = DBConstance.TABLE_GPS;
			ContentValues cv = new ContentValues();
			cv.put("latitude", obj.getLatitude());
			cv.put("longtitude", obj.getLongitude());
			cv.put("altitude", obj.getAltitude());
			cv.put("timestamp", obj.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	/**
	 * GPS表中查询多个GPS对象
	 * 
	 * @return
	 */
	public List<GPS> queryTableGPS() {

		String table = DBConstance.TABLE_GPS;
		GPS gps = null;
		ArrayList<GPS> obj = new ArrayList<GPS>();
		Cursor cursor = null;
		try {
			cursor = queryTable(context, table);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					gps = new GPS();
					gps.setLatitude(cursor.getDouble(cursor
							.getColumnIndex("latitude")));
					gps.setLongitude(cursor.getDouble(cursor
							.getColumnIndex("longtitude")));
					gps.setAltitude(cursor.getDouble(cursor
							.getColumnIndex("altitude")));
					gps.setTimestamp(cursor.getLong(cursor
							.getColumnIndex("timestamp")));
					obj.add(gps);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}

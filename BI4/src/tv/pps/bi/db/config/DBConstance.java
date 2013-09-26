package tv.pps.bi.db.config;

public class DBConstance {
	public final static String DB_NAME = "userbehavior.db";
	public final static String TABLE_IDENTIFICATION = "identification";// 存放用户身份信息
	public final static String TABLE_GPS = "gps";// 存放gps信息
	public final static String TABLE_POI = "poi";// 存放poi信息
	public final static String TABLE_APP = "app";// 存放app信息
	public final static String TABLE_SEARCH_WORD = "search_word";// 存放网页搜索词
	public final static String TABLE_URL = "url";// 存放访问过的网址
	public final static String TABLE_BOOT_TIME = "boot";// 存放系统启动时间
	public final static String TABLE_SHUT_TIME = "shut";// 存放系统关机时间
	public final static String TABLE_PHONE = "phone";// 存放打电话时间
	public final static String TABLE_SMS = "sms";// 存放发短信时间
	public final static String TABLE_VIDEO = "video";// 存放第三方播放器数据库信息
	public final static String TABLE_PROCESS = "process";// 存放进程信息
	public final static String TABLE_DEVICE_INFO = "deviceinfo";// 存放设备信息
	public final static String TABLE_INFOMATION_CONTROL = "infomation_control";// 增量数据发送控制表

	// 创建TABLE_INFOMATION_CONTROL表
	public final static String CREATE_TABLE_INFOMATION_CONTROL = "create table if not exists "
			+ TABLE_INFOMATION_CONTROL
			+ " ( _id integer primary key autoincrement , type text UNIQUE , timestamp long);";
	// 创建TABLE_IDENTIFICATION表
	public final static String CREATE_TABLE_IDENTIFICATION = "create table if not exists "
			+ TABLE_IDENTIFICATION
			+ " ( _id integer primary key autoincrement  , uid text , login text, platform text,mac text,model text UNIQUE );";
	// 创建TABLE_GPS表
	public final static String CREATE_TABLE_GPS = "create table if not exists "
			+ TABLE_GPS
			+ "( _id integer primary key autoincrement ,  latitude double , longtitude double, altitude double,timestamp long);";
	//创建TABLE_POI 表
	public final static String CREATE_TABLE_POI = "create table if not exists "
			+ TABLE_POI
			+ "( _id integer primary key autoincrement ,  latitude integer , longtitude integer,timestamp long);";
	//创建TABLE_URL 
	public final static String CREATE_TABLE_URL = "create table if not exists "
			+ TABLE_URL
			+ "( _id integer primary key autoincrement , url text, keywords text, timestamp long  );";
	//创建TABLE_BOOT_TIME 
	public final static String CREATE_TABLE_BOOT_TIME = "create table if not exists "
			+ TABLE_BOOT_TIME
			+ "( _id integer primary key autoincrement , boottime text, timestamp long);";
	//创建TABLE_SHUT_TIME
	public final static String CREATE_TABLE_SHUT_TIME = "create table if not exists "
			+ TABLE_SHUT_TIME
			+ "( _id integer primary key autoincrement , shutdowntime text, timestamp  long);";
	//创建TABLE_PHONE
	public final static String CREATE_TABLE_PHONE = "create table if not exists "
			+ TABLE_PHONE
			+ "( _id integer primary key autoincrement , teletime text, duration integer,timestamp long);";
	//创建TABLE_SMS
	public final static String CREATE_TABLE_SMS = "create table if not exists "
			+ TABLE_SMS
			+ "(_id integer primary key autoincrement ,  smstime text,timestamp  long)";
	//创建TABLE_VIDEO
	public final static String CREATE_TABLE_VIDEO = "create table if not exists "
			+ TABLE_VIDEO
			+ "(_id integer primary key autoincrement , provider text,watchtimestamp text, video_name text,timestamp)";
	//创建TABLE_DEVICE_INFO
	public final static String CREATE_TABLE_DEVICE_INFO = "create table if not exists "
			+ TABLE_DEVICE_INFO
			+ "(_id integer primary key autoincrement,imei text, imsi text, manufacturer text, model text,screen_resolution text,os_version text,os_custermize text)";

}

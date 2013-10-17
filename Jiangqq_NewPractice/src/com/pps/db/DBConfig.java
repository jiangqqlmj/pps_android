package com.pps.db;

/**
 * 数据库进行业务操作等信息的配置
 * @author jiangqingqing
 * @time 2013/08/22 14:49
 *
 */
public class DBConfig {
	public static final String DB_NAME = "channel.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "channel_table";
	// 表的属性
	public static final String CHANNLE_ID = "channel_id";
	public static final String CHANNEL_IMG = "channel_img";
	public static final String CHANNEL_NAME = "channel_name";
	public static final String CHANNEL_TP = "channel_tp";
	public static final String CHANNEL_ON = "channel_on";
	public static final String CHANNEL_VM = "channel_vm";
	public static final String CHANNEL_SUBID = "channel_subId";
  
	public static final  String[] COLUMNS = new String[] { DBConfig.CHANNEL_IMG,
			DBConfig.CHANNEL_NAME, DBConfig.CHANNEL_TP,
			DBConfig.CHANNEL_ON, DBConfig.CHANNEL_VM,
			DBConfig.CHANNEL_SUBID };
	
	//创建表的SQL脚本语句
	public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
			+ CHANNLE_ID + " integer primary key autoincrement," + CHANNEL_IMG
			+ " text," + CHANNEL_NAME + " text," + CHANNEL_TP + " text,"
			+ CHANNEL_ON + " integer," + CHANNEL_VM + " text," + CHANNEL_SUBID
			+ " integer)";

	//删除表
	public static final String UPDATE_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
}

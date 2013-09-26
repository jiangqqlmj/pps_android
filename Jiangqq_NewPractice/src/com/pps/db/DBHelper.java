package com.pps.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 进行分页设计数据库设计
 * @author jiangqingqing
 *
 */
public class DBHelper extends SQLiteOpenHelper{
    
	public DBHelper(Context context) {
	  super(context, DBConfig.DB_NAME, null, DBConfig.VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	   db.execSQL(DBConfig.CREATE_TABLE);
	  }
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DBConfig.UPDATE_TABLE);
		onCreate(db);
	}
}

package com.pps.sharpturn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 脑筋转转转创建数据库的帮助类
 * @author jiangqingqing
 * @time 2013/09/30 12:47
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, DBConfig.DATEBASE_NAME, null, DBConfig.DATEBASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
       //db.execSQL(DBConfig.CREATE_TABLE_SQL);
       db.execSQL(DBConfig.CREATE_BOOK_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

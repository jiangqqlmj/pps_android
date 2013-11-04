package tv.pps.bi.db;

import tv.pps.bi.config.DBConstance;
import tv.pps.bi.utils.LogUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public final static String TAG = "DBHelper";
	public DBHelper(Context context) {
		super(context, DBConstance.DB_NAME, null, 2);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConstance.CREATE_TABLE_INFOMATION_CONTROL);		
		db.execSQL(DBConstance.CREATE_TABLE_GPS);
		db.execSQL(DBConstance.CREATE_TABLE_URL);
		db.execSQL(DBConstance.CREATE_TABLE_BOOT_TIME);
		db.execSQL(DBConstance.CREATE_TABLE_SHUT_TIME);
		db.execSQL(DBConstance.CREATE_TABLE_PHONE);
		db.execSQL(DBConstance.CREATE_TABLE_SMS);
		db.execSQL(DBConstance.CREATE_TABLE_SEND); //发送记录表
		db.execSQL(DBConstance.CREATE_TABLE_NET); //开启网络 状态
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_INFOMATION_CONTROL);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_GPS);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_URL);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_BOOT_TIME);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_SHUT_TIME);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_PHONE);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_SMS);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_SEND_DATA);  
		db.execSQL("DROP TABLE IF EXISTS "+DBConstance.TABLE_NET_INFO);
		LogUtils.i(TAG, "数据库1成功删除");
        onCreate(db);  
        LogUtils.i(TAG, "数据库2成功创建");
	}

}

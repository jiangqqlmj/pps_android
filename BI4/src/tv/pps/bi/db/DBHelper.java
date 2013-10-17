package tv.pps.bi.db;

import tv.pps.bi.db.config.DBConstance;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public final static String TAG = "DBHelper";
	public DBHelper(Context context) {
		super(context, DBConstance.DB_NAME, null, 1);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConstance.CREATE_TABLE_INFOMATION_CONTROL);		
		db.execSQL(DBConstance.CREATE_TABLE_GPS);
		db.execSQL(DBConstance.CREATE_TABLE_URL);
		db.execSQL(DBConstance.CREATE_TABLE_BOOT_TIME);
		db.execSQL(DBConstance.CREATE_TABLE_SHUT_TIME);
		db.execSQL(DBConstance.CREATE_TABLE_PHONE);
		db.execSQL(DBConstance.CREATE_TABLE_SMS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

package tv.pps.bi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAPPHelper extends SQLiteOpenHelper {

	private final static String NAME = "appusage.db";
	private final static int VERSION = 1;

	public DBAPPHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE IF NOT EXISTS appdata (" +
				"appid integer primary key autoincrement," +
				" name varchar(20), " +
				"start varchar(20)," +
				"duration integer)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists appdata");
		onCreate(db);

	}

}

package tv.pps.bi.db;

import java.util.ArrayList;
import java.util.List;

import tv.pps.bi.proto.model.AppActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAPPManager  {
	private DBAPPHelper dbHelper;
	private SQLiteDatabase db ;
	public static  DBAPPManager dm ;
	private DBAPPManager(Context context) {
		this.dbHelper = new DBAPPHelper(context);
		this.db = dbHelper.getWritableDatabase();
	}
	public static DBAPPManager getDBManager(Context context){
		synchronized (DBAPPManager.class) {
			
			if(dm ==null){
				dm = new DBAPPManager(context);
			}
		}
		return dm;
	}
	/**
	 * 保存数据
	 */
	public void save(AppActivity app) {

	
		String sql = "insert into appdata(name,start,duration) values(?,?,?)";
		Object[] binargs = new Object[] { app.getPackageName(), app.getStart_timestamp(),
				app.getDuration() };
		db.execSQL(sql, binargs);

	}

    /**
     * 删除数据
     */
	public void delete()
	{
	 	 db.delete("appdata", null, null);
	}
	

	// 显示 返回的是List集合
	public List<AppActivity> getData(String appname) {
		List<AppActivity> appdatas = new ArrayList<AppActivity>();
		Cursor cursor = db
				.rawQuery("select * from appdata where name=?", new String[] {appname });
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String start_timestamp = cursor.getString(cursor.getColumnIndex("start"));
			int duration = cursor.getInt(cursor.getColumnIndex("duration"));
			AppActivity app = new AppActivity();
			app.setPackageName(name);
			app.setStart_timestamp(start_timestamp);
			app.setDuration(duration);
			appdatas.add(app);

		}
		cursor.close();
		return appdatas;
	}
}

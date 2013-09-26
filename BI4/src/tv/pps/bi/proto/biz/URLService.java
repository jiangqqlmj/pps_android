package tv.pps.bi.proto.biz;

import java.util.ArrayList;

import tv.pps.bi.proto.model.URLInfo;
import tv.pps.bi.utils.Utils;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Browser;
import android.provider.Browser.BookmarkColumns;


public class URLService {

	public ArrayList<URLInfo> getSystemBrowserUrl(Context context,long timestamp) {//获取系统浏览器的URL
		ContentResolver resolver = context.getContentResolver();
		ArrayList<URLInfo> list = new ArrayList<URLInfo>();
		URLInfo urlinfo  = null;
		Cursor cursor = null;
		try {
			//cursor = Browser.getAllVisitedUrls(resolver);
			cursor = resolver.query(Browser.BOOKMARKS_URI,  Browser.HISTORY_PROJECTION,
					 BookmarkColumns.DATE+" > "+timestamp, null, "date DESC");
			if(cursor ==null){
				return null;
			}else{
			while (cursor.moveToNext()) {
				urlinfo = new URLInfo();
				String myUrl = cursor.getString(cursor
						.getColumnIndex(Browser.BookmarkColumns.URL));
				long date = cursor.getLong(cursor.getColumnIndex(BookmarkColumns.DATE));
				urlinfo.setUrl(myUrl);
		        urlinfo.setKeywords(Utils.getSearchWord(myUrl));
		        urlinfo.setTimestamp(date);
		        list.add(urlinfo);
//		        Log.i("billsong", urlinfo.getUrl()
//		        		+urlinfo.getKeywords()
//		        		+urlinfo.getTimestamp());
			}
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

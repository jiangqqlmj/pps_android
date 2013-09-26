package tv.pps.bi.proto.biz;

import java.util.ArrayList;

import tv.pps.bi.proto.model.SMS;
import tv.pps.bi.utils.Utils;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class MessageInfoService {

	public ArrayList<SMS> getMessageInfo(Context context,long timestamp) {
		ArrayList<SMS> list = new ArrayList<SMS>();
		Uri uri = Uri.parse("content://sms/sent");
		ContentResolver content = context.getContentResolver();
		SMS sms = null;
		Cursor c = null;
		try {
			c = content.query(uri, new String[]{"date"}, " date > "+timestamp, null , "date DESC");
			while (c.moveToNext()) {
				sms = new SMS();
				long time = Long
						.parseLong(c.getString(c.getColumnIndex("date")));
				sms.setSmstime(Utils.formatTimeStamp(time, "yyyyMMddhhmmss"));
				sms.setTimestamp(time);
				list.add(sms);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null)
				c.close();
		}
		return list;
	}

}

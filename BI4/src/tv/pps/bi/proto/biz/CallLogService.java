package tv.pps.bi.proto.biz;

import java.util.ArrayList;

import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.utils.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
public class CallLogService {

	public ArrayList<PhoneActivity> getCallLogInfo(Context context,long timestamp) {//ok
        ArrayList<PhoneActivity> obj = new ArrayList<PhoneActivity>();
		ContentResolver resolver = context.getContentResolver();
		PhoneActivity phone =null;
		Cursor c = null;
		try {
			c = resolver.query(CallLog.Calls.CONTENT_URI,
					new String[]{"date","duration"}, CallLog.Calls.TYPE
						+ " = " + CallLog.Calls.OUTGOING_TYPE+" and "+ CallLog.Calls.DATE+" > "+ timestamp, null,
					CallLog.Calls.DEFAULT_SORT_ORDER);
			while (c.moveToNext()) {
				phone = new PhoneActivity();
				long time =Long.parseLong( c.getString(c.getColumnIndex("date")));
				String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
				 phone.setStart_timestamp(timeStr);
				 phone.setDuration(c.getInt(c.getColumnIndex("duration")));
                 phone.setTimestamp(time);
                 obj.add(phone);
			}
		} finally {
			if (c != null)
				c.close();
		}
		return obj;
	}
}

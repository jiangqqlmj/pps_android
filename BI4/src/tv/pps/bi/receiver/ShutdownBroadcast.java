package tv.pps.bi.receiver;

import tv.pps.bi.utils.LogUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShutdownBroadcast extends BroadcastReceiver{
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("time", Context.MODE_PRIVATE);
		long time = System.currentTimeMillis();
		Editor edit = sp.edit();
		 if(intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")){
			edit.putLong("shutdown", time);
			edit.commit();
			LogUtils.v("bi", "i am shut down at "+time);
		}
	}
}

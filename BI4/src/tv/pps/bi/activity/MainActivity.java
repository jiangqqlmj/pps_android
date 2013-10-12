package tv.pps.bi.activity;
import java.util.UUID;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.utils.LogUtils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		LogUtils.setShowLog(true);

		DeviceInfoStatistic.setUid(UUID.randomUUID().toString(),this);
		DeviceInfoStatistic.setLoginId("login00001",this);
		DeviceInfoStatistic.setPlatform("pps_android",this);
		
		IntervalTimeConstance.setStartDeliverServiceTime(2*60*1000);
		IntervalTimeConstance.setStartUserInfoSearchTime(60*1000);
	}
}

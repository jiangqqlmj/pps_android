package tv.pps.bi.activity;
import java.util.UUID;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.utils.LogUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class MainActivity extends Activity {

	Button open,close;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
	
		getWindow().setContentView(R.layout.main);
		
		LogUtils.setShowLog(true);
        // 设置UUId
		DeviceInfoStatistic.setUid(UUID.randomUUID().toString(),this);
		// 设置登录ID
		DeviceInfoStatistic.setLoginId("1922333",this);
		// 设置平台信息：pps_android，qiyi_android等
		DeviceInfoStatistic.setPlatform("pps_android",this);
		// 设置数据投递时间周期，以毫秒为单位,默认:2*60*60*1000
		IntervalTimeConstance.setStartDeliverServiceTime(2*60*1000);
		// 设置用户行为数据搜集时间间隔,以毫秒为单位,默认：30*60*1000
		IntervalTimeConstance.setStartUserInfoSearchTime(60*1000);
        // 设置是否进行用户行为搜集
		IntervalTimeConstance.setStartServiceSwitch(this, true);

		
		initView();
	}
	public void initView(){
		open = (Button)findViewById(R.id.button1);
		close=(Button)findViewById(R.id.button2);
		open.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				IntervalTimeConstance.setStartServiceSwitch(MainActivity.this,true);
				LogUtils.i("billsong", "打开服务");
			}
			
		});
		close.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				LogUtils.i("billsong", "关闭服务");
				IntervalTimeConstance.setStartServiceSwitch(MainActivity.this,false);
			}
			
		});
	}
}

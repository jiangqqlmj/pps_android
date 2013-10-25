package tv.pps.bi.activity;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.service.ManagerService;
import tv.pps.bi.utils.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button open, close;
	Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;

		// 开启log,默认为关闭，true表示开启，false表示关闭
		LogUtils.setShowLog(true);
		// 设置开启用户行为搜集服务
		IntervalTimeConstance.setStartServiceSwitch(this, true);
		// 设置uuid（设备唯一标识）和平台信息
		DeviceInfoStatistic.setUuidAndPlatform(
				"11111111111111111111111111111111", "pps_android", this);
		// 设置loginid，在用户登录时调用
		DeviceInfoStatistic.setLoginId("123456", this);
		// 设置数据投递时间周期，以毫秒为单位,默认1小时:1*60*60*1000
		IntervalTimeConstance.setStartDeliverServiceTime(2 * 60 * 1000);
		// 设置用户行为数据搜集时间间隔,以毫秒为单位,默认30分钟：30*60*1000
		IntervalTimeConstance.setStartUserInfoSearchTime(60 * 1000);
		// 启动用户行为监听服务
		ManagerService.startService(this);

		initView();

	}

	public void initView() {
		open = (Button) findViewById(R.id.button1);
		close = (Button) findViewById(R.id.button2);
		open.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				IntervalTimeConstance.setStartServiceSwitch(MainActivity.this,
						true);

				LogUtils.i("billsong", "打开服务");
			}
		});
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LogUtils.i("billsong", "关闭服务");

				IntervalTimeConstance.setStartServiceSwitch(MainActivity.this,
						false);

			}

		});
	}
}

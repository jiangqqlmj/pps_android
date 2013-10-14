package com.pps.carsign;

import android.app.Activity;
import android.os.Bundle;

/**
 * 基类Activity 所有的activity都继承这个基类 来进行管理activity
 * @author jiangqingqing
 *
 */
public class BaseActivity extends Activity {
	ExitAppUtil exitAppUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		exitAppUtil=ExitAppUtil.getInstance();
		exitAppUtil.addActivity(this);
	}

	public void onBackPressed() {
		exitAppUtil.removeActivity(this);
	};
}

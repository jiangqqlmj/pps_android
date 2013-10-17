package com.pps;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	private ExitAppUtil mExitAppUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mExitAppUtil=ExitAppUtil.getInstance();
		mExitAppUtil.addActivity(this);
	}
	
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	mExitAppUtil.remove(this);
    }
}

package com.pps.customcrash.app;

import com.pps.customcrash.CustomCrash;

import android.app.Application;

public class MyCustomApp extends Application {

	@Override
	public void onCreate() {
		CustomCrash mCustomCrash=CustomCrash.getInstance();
		mCustomCrash.setCustomCrashInfo(getApplicationContext());
		super.onCreate();
	}
   
	
	
}

package com.pps.customcrash.base;

import com.pps.customcrash.ExitApp;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	ExitApp.getInstance().addActivity(this);
    }
     
    public void onBackPressed() {
    	super.onBackPressed();
    	ExitApp.getInstance().removeActivity(this);
    }
}

package com.pps;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ExitAppUtil {
   
	private List<Activity> activities;
	private static ExitAppUtil instance=new ExitAppUtil();
	private ExitAppUtil(){}
	
	public static ExitAppUtil getInstance()
	{
		return instance;
	}
	
	/**
	 * 添加一个Activity到管理集合中
	 * @param pActivity
	 */
	public void addActivity(Activity pActivity)
	{
		if(null==activities)
		{
			activities=new ArrayList<Activity>();
		}
		activities.add(pActivity);
	}
	
	/**
	 * 从管理集合中移除当前的一个Activity
	 * @param pActivity
	 */
	public void remove(Activity pActivity)
	{
		activities.remove(pActivity);
	}
	
	/**
	 * 退出所有activity
	 */
	public void exitApp()
	{
		for (Activity pActivity : activities) {
			pActivity.finish();
		}
	}
}

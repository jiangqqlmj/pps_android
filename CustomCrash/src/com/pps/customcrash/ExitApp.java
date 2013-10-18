package com.pps.customcrash;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 完整推出应用程序工具类
 * @author jiangqingqing
 * @time 2013/10/14 10:38
 */
public class ExitApp {

	
	private static ExitApp instance=new ExitApp();
	private ExitApp(){}
	private List<Activity> activities;
	public static ExitApp getInstance()
	{
		return instance;
	}
	
	
	/**
	 * 添加一个Activity对象到管理集合中
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
	 * 从Activity管理集合中异常一个Activity
	 * @param pActivity
	 */
	public void removeActivity(Activity pActivity)
	{
			activities.remove(pActivity);
	}
	
	/**
	 * 完全退出应用
	 */
	public void exitAPP()
	{
		for (Activity pActivity : activities) {
			pActivity.finish();
		}
		System.exit(0);
	}
}



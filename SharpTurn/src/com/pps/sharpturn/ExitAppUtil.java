package com.pps.sharpturn;

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
	 * 添加Activity到管理器中
	 * @param pActivity
	 */
	public void addActivity(Activity pActivity)
	{
		if(activities==null){
            activities=new ArrayList<Activity>();			
		}
		activities.add(pActivity);
	}

    /**
     * 从管理器集合中删除一个指定的Activity	 
     * @param pActivity
     */
	public void removeActivity(Activity pActivity)
	{
		 activities.remove(pActivity);
	}
	
	/**
	 * 完整退出应用
	 */
	public void exit()
	{
		for (Activity pActivity : activities) {
			pActivity.finish();
		}
	}
}
	 

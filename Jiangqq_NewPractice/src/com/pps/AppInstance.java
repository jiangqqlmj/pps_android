package com.pps;

/**
 * 静态工具类,获取PPS列表数据，为了加快加载速度，放入到内存当中
 * @author jiangqingqing
 * @version 1.0.0.0
 * @time 2013/08/21 10:37
 */
public class AppInstance {
    public static  AppInstance mInstance;
	private AppInstance()
    {}
     
	public static AppInstance getInstance()
	{
		return mInstance;
	} 
 }

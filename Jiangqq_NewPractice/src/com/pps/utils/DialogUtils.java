package com.pps.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 创建各种Dialog的帮助类
 * @author jiangqingqing
 * @time 2013/09/13 9:43
 */
public class DialogUtils {
   

  	public static ProgressDialog mProgressDialog;
  	
  	/**
  	 * 根据传进来的Message进行显示进度框
  	 * @param pContext
  	 * @param msg
  	 */
  	public static void createProgressDialog(Context pContext, String msg)
  	{
  		mProgressDialog=new ProgressDialog(pContext);
  		mProgressDialog.setMessage(msg);
  		mProgressDialog.setCancelable(false);
  		mProgressDialog.show();
  	}
  	
  	/**
  	 * 关闭ProgressDialog
  	 */
  	public static void close()
  	{
  		if(null!=mProgressDialog&&mProgressDialog.isShowing())
  		{
  			mProgressDialog.dismiss();
  		}
  	}
  	
  	
	   
}

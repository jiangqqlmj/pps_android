package com.pps.customcrash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

/**
 * 自定义异常捕捉工具类
 * @author jiangqingqing
 * @time 2013/10/14 13:14
 *
 */
public class CustomCrash implements UncaughtExceptionHandler {
     
	private static CustomCrash instance=new CustomCrash();
	private Context mContext;	
	
	private CustomCrash(){}
	
	/**
	 * 
	 * @return
	 */
	public static CustomCrash getInstance()
	{
		return instance;
	}
	
	/*
	 * (non-Javadoc) 进行重写捕捉异常 
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
        //1,保存信息到sdcard中
		saveToSdcard(mContext,ex);
	    //2,应用准备退出
		showToast(mContext, "很抱歉,程序发生异常,即将推出.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
            e.printStackTrace();
		}
		ExitApp.getInstance().exitAPP();
	}
	
	/**
	 * 设置自定异常处理类
	 * @param pContext
	 */
	public void setCustomCrashInfo(Context pContext){
       this.mContext=pContext;
       Thread.setDefaultUncaughtExceptionHandler(this);
	}

	
	/**
	 * 保存异常信息到sdcard中
	 * @param pContext  
	 * @param ex  异常信息对象
	 */
	private void saveToSdcard(Context pContext,Throwable ex) {
		StringBuffer sBuffer=new StringBuffer(); 
		//添加异常信息
		sBuffer.append(getExceptionInfo(ex));
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
	    	File file1=new File("sdcard/carsh/");
	    	if(!file1.exists()){
	    		file1.mkdir();
	    	}
	    	File file2=new File("sdcard/carsh/carsh.txt");
	    	FileOutputStream fos;
	    	try {
	    		fos=new FileOutputStream(file2);
	    		fos.write(sBuffer.toString().getBytes());
	    		fos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * 获取并且转化异常信息
	 * @param ex 
	 * @return 异常信息的字符串形式
	 */
	private String getExceptionInfo(Throwable ex)
	{
		StringWriter sw=new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();	
	}
	
	/**
	 * 进行弹出框提示
	 * @param pContext
	 * @param msg
	 */
	private void showToast(final Context pContext, final String msg)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
			  Looper.prepare();
              Toast.makeText(pContext, msg, Toast.LENGTH_SHORT).show();		
              Looper.loop();
			}
		}).start();
	}
}

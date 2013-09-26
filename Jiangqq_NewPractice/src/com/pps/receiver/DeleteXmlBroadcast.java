package com.pps.receiver;

import java.io.File;

import com.pps.commom.MemoryStatus;
import com.pps.utils.FileUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 广播接受者，使用定时闹钟，进行删除已经下载好的节目列表信息
 * @author jiangqingqing
 *
 */
public class DeleteXmlBroadcast extends BroadcastReceiver {
	public static String DELETE_ACTION="com.pps.tv.delete.xml";
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(DELETE_ACTION))
		{
			Log.v("jiangqq", "收到广播,进行删除缓存....");
			//处理删除xml文件,释放用户手机空间
            if(MemoryStatus.externalMemoryAvailable()) //外设已经挂载
            {
            	File list_File=new File("/sdcard/141.xml");
            	if(list_File.exists())
            	{
            	   	//文件存在进行删除
            		FileUtils.deleteAll(list_File);
            	}
            	File details_File=new File("/sdcard/pps_download/details");
            	if(details_File.exists())
            	{
            		FileUtils.deleteAll(details_File);
            	}
            }			
		}
	}
}

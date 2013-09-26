package com.pps.service;

import com.pps.fragment.MyListFragment;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 判断网络变化的服务类
 * @author jiangqingqing
 * @time 2013/09/13 18"13
 */
public class ListenNetStateService extends Service {
	 private ConnectivityManager connectivityManager;
	 private NetworkInfo info;
     	
	 private BroadcastReceiver mReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
	                //Log.d("jiangqq", "网络状态已经改变");
	                connectivityManager = (ConnectivityManager)      
	                                         getSystemService(Context.CONNECTIVITY_SERVICE);
	                info = connectivityManager.getActiveNetworkInfo();  
	                if(info != null && info.isAvailable()) {
	                    String name = info.getTypeName();
	                    //Toast.makeText(context, "当前网络为："+name, Toast.LENGTH_SHORT).show();
	                    Log.d("jiangqq", "当前网络名称：" + name);
	                } else {
	                    Log.d("jiangqq", "没有可用网络");
	                    if(MyListFragment.mProgressDialog!=null&&MyListFragment.mProgressDialog.isShowing())
	                    {
	                    	MyListFragment.mProgressDialog.dismiss();
	                    	Toast.makeText(context, "没有可用网络", Toast.LENGTH_SHORT).show();
	                    }
	                }
	            }
	        }
	    };

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		if(JudgeNetwork.isWifiConnected(this))
//		{
//			Toast.makeText(this, "当前为Wifi网络", Toast.LENGTH_SHORT).show();
//		}else if(JudgeNetwork.isMobileConnected(this))
//		{
//			Toast.makeText(this, "当前为Mobile网络", Toast.LENGTH_SHORT).show();
//		}else if(!JudgeNetwork.isNetworkConnected(this))
//		{
//			Toast.makeText(this, "当前为没有网络连接", Toast.LENGTH_SHORT).show();
//		}
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
	
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
    
}

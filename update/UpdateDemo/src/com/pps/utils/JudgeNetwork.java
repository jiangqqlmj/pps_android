package com.pps.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接检测类
 * @author jiangqingqing
 * @time 2013/08/06  11:34
 */
public class JudgeNetwork {
	
	/**
	 * 判断是否有网络连接
	 * @param pContext
	 * @return true->有， false->无
	 */
	public boolean isNetworkConnected(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * 判断wifi是否可用
	 * @param pContext
	 * @return
	 */
	public boolean isWifiConnected(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        if (mWiFiNetworkInfo != null) {  
	            return mWiFiNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	/**
	 * 判断MOBILE网络是否可用
	 * @param pContext
	 * @return 
	 */
	public boolean isMobileConnected(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
    /**
     * 获取网络连接的类型 
     * @param pContext
     * @return  返回连接的网络类型
     */
	public static int getConnectedType(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;  
	}
}

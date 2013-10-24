package tv.pps.bi.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import tv.pps.bi.proto.model.UserActivity;


/**
 * 网络操作工具类 使用post请求发送数据
 * 
 * @author jiangqingqing
 * @time 2013/09/04 10:45
 */
public class ProtoNetWorkManager {

	/**
	 * 通过Post请求发送序列化后的实体类对象
	 * 
	 * @param pUserActivity
	 * @return
	 */
	public static String postUserActivityByEntity(UserActivity pUserActivity) {
		return "";
	}

	/**
	 * 通过Post请求请发送加密过后的字节数组数据
	 * @param pByte  需要发送的字节数组
	 * @param pUrl   请求的链接地址
	 * @return  发送成功返回true，失败返回false
	 */
	public static boolean postUserActivityByByte(byte[] pByte,String pUrl)
	{
		URL mUrl=null;
		HttpURLConnection mHttpURLConnection=null;
		try {
           mUrl=new URL(pUrl);			
           mHttpURLConnection=(HttpURLConnection)mUrl.openConnection();
           mHttpURLConnection.setRequestProperty("content-type","text/html");   
           mHttpURLConnection.setRequestMethod("POST");  
           mHttpURLConnection.setDoOutput(true);  
           mHttpURLConnection.setDoInput(true);  
           mHttpURLConnection.setUseCaches(false);
           // 设置超时时间
           mHttpURLConnection.setConnectTimeout(20*1000);
           mHttpURLConnection.getOutputStream().write(pByte);
           mHttpURLConnection.getOutputStream().flush();
           int result_code=mHttpURLConnection.getResponseCode();
           mHttpURLConnection.disconnect();
           if(result_code==200)
           {
        	   return true;
           }
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 通过Post请求发送加密过后的字符串数据
	 * 
	 * @param pStr
	 * @return 返回请求成功与否的结果
	 */
	public static String postUserActivityByMsg(String pStr, String pUrl) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(pUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("protobuff", pStr));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response;
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

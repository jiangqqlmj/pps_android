package tv.pps.bi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tv.pps.bi.config.URL4BIConfig;
import android.content.Context;

/**
 * 获取服务器数据网络操作类
 * @author jiangqingqing
 * @time 2013/10/16
 * 
 */ 
public class BI4NetUtils {
	
	/**
	 * 获取网络数据
	 * @param pContext
	 * @return
	 */
    public boolean  getTagService(Context pContext)
    {
    	if(NetworkUtils.isNetworkConnected(pContext))
    	{
    		return false;
    	}
    	
    	String  urlStr=URL4BIConfig.getBI4_SIGN_URL();
        URL url;
        HttpURLConnection conn = null;
		//InputStream inStream = null;
		try {
			url=new URL(urlStr);
			conn=(HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200)
			{
				//inStream=conn.getInputStream();
				//String resultStr= readData(inStream);
				// 进行解析，判断，然后返回boolean值
				
			}
		}catch (Exception e) {
		}    	
    	return false;
    } 
    
    /**
     * 读取字节流
     * @param pInStream
     * @return
     * @throws IOException 
     */
    @SuppressWarnings("unused")
	private String readData(InputStream pInStream) throws IOException
    {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int length=0;
        while((length=pInStream.read(buffer))!=-1)
        {
        	bos.write(buffer, 0, length);
        }
        byte[] data=bos.toByteArray();
        bos.close();
        pInStream.close();
        return new String(data, "UTF-8");
    }
    
}

package tv.pps.bi.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("SimpleDateFormat")
public class Utils {
	
	
	 /* 使应用程序 获取root的工具类
	 * @author jiangqingqing
	 * @time 2013/08/29 9:42
	 */
		
		/**
		 * 获取root权限
		 * @return 返回获取root权限成功与否
		 */
		public static boolean getRoot(){  
	        Process process = null;  
	        try{  
	            process = Runtime.getRuntime().exec("su");  
	            process.waitFor();
	            return true;
	        } catch (Exception e) {  
	          
	        } finally {  
	                process.destroy();  
	        }  
	       return false;
	    }  
		
		/**
		 * 执行shell命令函数
		 * @param cmd  
		 * @return 返回执行成功与否
		 */
		public static boolean executeShell(String[] cmd)
		{
			Process process = null;  
	        DataOutputStream os = null;  
	        try{  
	            process = Runtime.getRuntime().exec("su");  
	            os = new DataOutputStream(process.getOutputStream());  
	            //os.writeBytes(cmd+ "\n");  
	            //os.writeBytes("chmod 777 /data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db\n");
	            //os.writeBytes("cat /data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db > /data/data/sogou.mobile.explorer/databases/pps_sogou_mobile_browser.db\n");
	            //os.writeBytes("mv /data/data/sogou.mobile.explorer/databases/pps_sogou_mobile_browser.db /data/data/com.pps.bi.activity/databases/pps_sosou_mobile_browser.db\n");
	            for(int i=0;i<cmd.length;i++)
	            {
	            	os.writeBytes(cmd[i]+"\n");
	            }
	            os.writeBytes("exit\n");  
	            os.flush();  
	            process.waitFor();  
	        } catch (Exception e) {  
	        	return false;
	        } finally {  
	            try {  
	            	if(os!=null)
	            	{
	            		os.close();
	            	}
	                process.destroy();  
	            } catch (Exception e) {  
	            }  
	        }
	        return true;
		}

	 
	 
	public static String printArrayList(ArrayList<String> list){
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ;i<list.size();i++){
			sb.append(i+"--"+list.get(i)+"\n");
		}
		return sb.toString();
	}
	
	public static  String getSearchWord(String uri){
		String myStr = "";
		if (uri.startsWith("http://www.baidu.com")
				|| uri.startsWith("www.baidu.com")) {
			int m_index = uri.indexOf("s?word=");
			int n1_index = uri.indexOf("&st=");
			int n2_index = uri.indexOf("&ref");
			if (m_index != -1) {
				if (n1_index != -1) {
					myStr = uri.substring(m_index + 7, n1_index);
				}
				if (n2_index != -1) {
					myStr = uri.substring(m_index + 7, n2_index);
				}
				return getUTFFormat(myStr);
			}
	}
	
			if(uri.startsWith("http://www.google.com.hk/")||uri.startsWith("www.google.com.hk")){
				int m_index = uri.indexOf("&q=");
				int n_index = uri.indexOf("&oq=");
				
				if(m_index!=-1){
					if(n_index!=-1){
					myStr = uri.substring(m_index+3,n_index);
					return getUTFFormat(myStr);
					}
				}
				
			}
			if(uri.startsWith("http://m.so.com")){
				int m_index = uri.indexOf("s?q=");
				int n_index = uri.indexOf("&src=");
				if(m_index!=-1){
					if(n_index!=-1){
					myStr = uri.substring(m_index+3,n_index);
					return getUTFFormat(myStr);
					}
				}
			}
			return "";
				
	}
	
	public  static ArrayList<String> getSearchWord(ArrayList<String> list) {
	
		ArrayList<String> resultList = new ArrayList<String>();
		String myStr="";
		for(int i=0;i<list.size();i++){
			String uri = list.get(i);

			if (uri.startsWith("http://www.baidu.com")
					|| uri.startsWith("www.baidu.com")) {
				int m_index = uri.indexOf("s?word=");
				int n1_index = uri.indexOf("&st=");
				int n2_index = uri.indexOf("&ref");
				if (m_index != -1) {
					if (n1_index != -1) {
						myStr = uri.substring(m_index + 7, n1_index);
					}
					if (n2_index != -1) {
						myStr = uri.substring(m_index + 7, n2_index);
					}
					resultList.add( getUTFFormat(myStr)
							+ "--from baidu");
				}
		}
		
				if(uri.startsWith("http://www.google.com.hk/")||uri.startsWith("www.google.com.hk")){
					int m_index = uri.indexOf("&q=");
					int n_index = uri.indexOf("&oq=");
					
					if(m_index!=-1){
						if(n_index!=-1){
						myStr = uri.substring(m_index+3,n_index);
						resultList.add(getUTFFormat(myStr)
								+ "--from google");
						}
					}
					
				}
				if(uri.startsWith("http://m.so.com")){
					int m_index = uri.indexOf("s?q");
					int n_index = uri.indexOf("&src=");
					if(m_index!=-1){
						if(n_index!=-1){
						myStr = uri.substring(m_index+3,n_index);
						resultList.add( getUTFFormat(myStr)
								+ "--from 360");
						}
					}
				}
		}
		return resultList;
	}
	
	public static String getUTFFormat(String str) {//将网址中的乱码转换成中文
		try {
			return java.net.URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//初始化数据库
	public static SQLiteDatabase openDatabase(Context context,String databaseFilename) {
	  try {
		  SQLiteDatabase  db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
	      return db;
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
	  return null;
	}
	public static boolean execRootCmd(String cmd){  
		//运行root命令
	    Process process = null;  
	    DataOutputStream os = null;  
	    try{  
	        process = Runtime.getRuntime().exec("su");  
	        os = new DataOutputStream(process.getOutputStream());  
	        os.writeBytes(cmd+ "\n");  
	        os.writeBytes("exit\n");  
	        os.flush();  
	        process.waitFor();  
	    } catch (Exception e) {  
	        return false;  
	    } finally {  
	        try {  
	            if (os != null)   {  
	                os.close();  
	            }  
	            process.destroy();  
	        } catch (Exception e) {  
	        }  
	    }  
	    return true;  
	}  
	
	public static String runCommand(String command) {
		// 运行普通linux命令
		Process process = null;
		ByteArrayOutputStream out = null;
		String returnValue = "0";
		try {
			process = Runtime.getRuntime().exec(command);
			InputStream in = process.getInputStream();
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for(int offset=0;(offset = in.read(buffer))!=-1;){
				out.write(buffer, 0, offset);
			}
			returnValue = new String(out.toByteArray());
			process.waitFor();
		} catch (Exception e) {
			return returnValue;
		} finally {
			try {
				if(out!=null) out.close();
				if(process!=null)process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public static String formatTimeStamp(long time,String formatStr){
		//long类型时间格式化成特定形式的日期格式
		  java.util.Date date = new java.util.Date(time);
		  SimpleDateFormat format = new SimpleDateFormat(formatStr);
		  return format.format(date) ;
	}
}

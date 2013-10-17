package com.pps.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 进行从网站中获取XML文件
 * 
 * @author jiangqingqing
 * 
 */
public class DownXML {
	/**
	 * 从网络中进行获取XML文本信息
	 * 
	 * @param str
	 *            请求的url地址
	 * @return XML的字符串
	 */
	public static String getXmlStr(String str) {
		URL url;
		HttpURLConnection conn = null;
		InputStream inStream = null;
		try {
			url = new URL(str);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(8 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				inStream = conn.getInputStream();
			} else {

			}
			return readData(inStream, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的输入流于字符编码，进行构造成相应的文本字符串
	 * 
	 * @param inSream
	 * @param charsetName
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String readData(InputStream inSream, String charsetName)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, "utf-8");
	}

	/**
	 * 根据网络地址进行获取文件
	 * 
	 * @param str
	 * @throws Exception
	 */
	public static boolean getFile(String str) {
		URL url; //文件地址
		HttpURLConnection conn;
		try {
			url=new URL(str);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(8 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				String[] strs = str.split("/");
				readAsFile(is, new File("/sdcard/" + strs[strs.length - 1]));
				return true;
			}
		} catch(Exception e)
		{
			return false;
		}
		return false;
	}

	 
	
	/**
	 * 根据网络地址进行获取文件
	 * @param str
	 * @return 
	 * @throws Exception
	 */
	public static boolean getFileWithDetails(String str) 
	{
		URL url ; //文件地址
		HttpURLConnection conn;
		try {
			url = new URL(str);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				String[] strs = str.split("/");
				File file=new File("/sdcard/pps_download");
				if(!file.exists())
				{
					file.mkdir();
				}
				readAsFile(is, new File("/sdcard/pps_download/" + strs[strs.length - 1]));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		
		return false;
	}
	
	
	/**
	 * 通过输入流进行保存到文件
	 * 
	 * @param inSream
	 * @param file
	 * @throws Exception
	 */
	public static void readAsFile(InputStream inSream, File file)
			throws Exception {
		FileOutputStream outStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inSream.close();
	}

	/**
	 * 根据提供的文本文件进行文件的输入流
	 * 
	 * @param file
	 *            需要进行读取的文本文件
	 * @return 文本的字符串文本
	 * @throws Exception
	 */
	public static InputStream getXMLFromFile(File file) throws Exception {
		// InputStream inStream=null;
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		return bis;
	}

	/**
	 * 根据输入的字节流进行去读转换成字符串
	 * 
	 * @param inputStream
	 * @return 转换成功的字符串
	 */
	public static String getStrFromStream(InputStream inputStream) {
		String str = "";
		return str;
	}
}

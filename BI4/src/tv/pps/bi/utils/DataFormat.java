package tv.pps.bi.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormat {

	@SuppressLint("SimpleDateFormat")
	public static String formatData(Date curDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//yyyyMMddHHmmss
		String str = format.format(curDate);
		return str;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static long fromDateStringToLong(String inVal) { //此方法计算时间毫秒 
		Date date = null; //定义时间类型 
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss"); //YYYYmmddhhMMss
		try { 
		date = inputFormat.parse(inVal); //将字符型转换成日期型 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		return date.getTime(); //返回毫秒数 
		}
}

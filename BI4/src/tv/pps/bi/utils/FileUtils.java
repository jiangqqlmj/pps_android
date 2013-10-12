package tv.pps.bi.utils;

import java.io.FileOutputStream;

/**
 * 
 * 文件操作工作具体类
 * @author jiangqingqing
 * @time 2013/09/06
 */
public class FileUtils {
  
	public static boolean  stringToFile(String str)
   { 
	  FileOutputStream fos;
	  try {
		  fos= new FileOutputStream("/sdcard/bi.txt",true);
		  fos.write(str.getBytes());
		  fos.flush();
		  fos.close();
		  return true;
	} catch (Exception e) {
		  e.printStackTrace();
	}
	  
	  return false; 
   }
	
	
	
}

package com.pps.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.pps.commom.MemoryStatus;

/**
 * 进行删除一个目录工具类方法
 * 
 * @author jiangqingqing
 * 
 */
public class FileUtils {
	/**
	 * 根据传入的文件夹路径，删除该目录的所有文件包括该文件夹本身
	 * 
	 * @param file
	 */
	public static void deleteAll(File file) {
		if (file.isFile() || file.list().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAll(files[i]);
				files[i].delete();
			}
			if (file.exists()) // 如果文件本身就是目录 ，就要删除目录
				file.delete();
		}
	}

	/**
	 * 根据对应的文件名，写入文件
	 * 
	 * @param mStr
	 *            输入字符串
	 * @param pathName
	 *            文件的名字
	 */
	public static boolean writeToFile(String mStr, String pathName) {

		BufferedWriter writer = null;
		if (MemoryStatus.externalMemoryAvailable()) {
			try {
				writer = new BufferedWriter(new FileWriter("/sdcard/jiangqq_pps"
						+ pathName));
				writer.write(mStr);
				writer.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null)
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}

		}
		return false;
	}

}

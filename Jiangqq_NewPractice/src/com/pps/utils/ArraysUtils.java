package com.pps.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组操作帮助类
 * @author jiangqq
 *
 */
public class ArraysUtils {

	/**
	 * 根据传入的数据进行倒置操作
	 * @param mLists
	 * @return 经过倒置的数组
	 */
	public static <T> List<T> convertArrays(List<T> mLists)
	{
		List<T> convert=new ArrayList<T>(mLists.size());
		for(int i = mLists.size()-1; i>=0; i--)
		{
			convert.add(mLists.get(i));
		}
		return convert;
		
	}
}

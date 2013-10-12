package com.pps.carsign.async;

import android.content.Context;
import android.widget.ImageView;

/**
 * 进行异步加载图片
 * @author jiangqingqing
 * @time 2013/10/12 18:01
 */
public class AsyncImage {

	/**
	 * 进行记载图片到ImageView中
	 * @param pContext
	 * @param pImageView
	 * @param pResId
	 */
	public static void loadImage(Context pContext,ImageView pImageView,Integer pResId)
	{
		pImageView.setImageResource(pResId);
	}
	
}


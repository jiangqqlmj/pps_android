package com.pps.async;


import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 进行内存的快速缓存--(硬引用于软引用)
 * <1:首先把图片加入到硬缓存中，/>
 * <2:当硬缓存中的容量达到上限的时候，把最近命中率最低的那缓存的图片加入到软缓存中，当系统的资源紧张的时候，系统进行回收/>
 * @author jiangqingqing
 * 
 */
public class ImageLruCache {
	
	// 开辟8M的硬缓存空间;
	private final int hardCachedSize = 8 * 1024 * 1024;
	// 软引用
	private static final int SOFT_CACHE_CAPACITY = 40;
	// 开软引用缓存
	private LinkedHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
			SOFT_CACHE_CAPACITY, 0.75f, true);

	// 开硬引用缓存
	private final LruCache<String, Bitmap> sHardBitmapCache = new LruCache<String, Bitmap>(
			hardCachedSize) {
		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			super.entryRemoved(evicted, key, oldValue, newValue);
			// 当硬缓存区的的容量达到极限的时候，把最近最少使用的Bitmap对象存放入到软引用缓存中
			sSoftBitmapCache.put(key, new SoftReference<Bitmap>(oldValue));
		}
		@Override
		protected int sizeOf(String key, Bitmap value) {
			if(null!=value)
			{
				return value.getRowBytes()*value.getHeight();
			}else {
				return 0;
			}
		}
	};

	/**
	 * 进行把图片放入到缓存当中去
	 * 
	 * @param key
	 * @param bitmap
	 */
	public boolean putBitmap(String key, Bitmap bitmap) {
		if (null!=bitmap) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(key, bitmap);
				return true;
			}
		}
		return false;

	}

	/**
	 * 根据传入的Key值，去缓存中去获取图片 1:<先从硬缓存中获取图片,/> 2：<如果硬缓存中没有该对应的图片，那就从软缓存中去寻找,/>
	 * 3:<如果软缓存中也也没有，那就去本地文件缓存中进行寻找,/>
	 * 
	 * @param key
	 * @return 获取到的图片
	 */
	public Bitmap getBitmap(String key) {
		Bitmap bitmap = null;
		synchronized (sHardBitmapCache) {
		    //该方法，默认把取到的缓存，重新加载到该linked的头部
			bitmap = sHardBitmapCache.get(key);
			if (null!=bitmap) {
				return bitmap;
			}
		}
		// 硬缓存中没有，现在去软缓存中进行查找该图片
		synchronized (sSoftBitmapCache) {
			SoftReference<Bitmap> softReference = sSoftBitmapCache.get(key);
			if (null!=softReference) {
				bitmap = softReference.get();
				if (null!=bitmap) {
					return bitmap;
				} else {
					// 表示此刻的图片的软引用已经被回收了，那就进行移除
					sSoftBitmapCache.remove(key);
					
				}
			}
		}
		return null;
	}
	
	/**
	 * 清空缓存
	 */
	public void clear()
	{
		sHardBitmapCache.evictAll();
	}
}

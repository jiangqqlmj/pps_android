package com.pps.async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.pps.activity.R;
import com.pps.utils.Utils;

public class ImageLoader {
	
	
	private ImageLruCache mImageLruCache=new ImageLruCache();//进行实例化缓存
	
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());

	public ImageLoader(Context context) {
		// Make the background thead low priority. This way it will not affect
		// the UI performance
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);

		fileCache = new FileCache(context);
	}

	final int stub_id = R.drawable.family;

	// 进行网络图片的加载显示
	@SuppressWarnings("unused")
	public void DisplayImage(String url, Activity activity, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = null;
		try {
			//bitmap = memoryCache.get(url);
	
			bitmap=mImageLruCache.getBitmap(url);
		} catch (Exception e) {
			System.gc();
			
			bitmap=mImageLruCache.getBitmap(url);
		}

		
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			
		} else {
			queuePhoto(url, activity, imageView);
			imageView.setImageResource(stub_id);
			// 进行释放图片资源
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		
		}
	}

	private void queuePhoto(String url, Activity activity, ImageView imageView) {
		// This ImageView may be used for other images before. So there may be
		// some old tasks in the queue. We need to discard them.
		photosQueue.Clean(imageView);
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		synchronized (photosQueue.photosToLoad) {
			photosQueue.photosToLoad.push(p);
			photosQueue.photosToLoad.notifyAll();
		}
		// start thread if it's not started yet
		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);
		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null) {
			return b;
		}
		Bitmap bitmap = null;
		// from web
		try {

			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			// 进行图片释放
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		Bitmap bitmap = null;
		try {
			// decode image size
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			BitmapFactory.decodeStream(new FileInputStream(f), null, options);
			// Find the correct scale value. It should be the power of 2.
			options.inSampleSize = calculateInSampleSize(options, 57, 79)*3/2;
			// options.inSampleSize=2;
			// System.out.println("缩小的倍数为:"+calculateInSampleSize(options, 57,
			// 79));
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null,
					options);
			return bitmap;
		} catch (Exception e) {
			
		}
		return null;
	}

	/**
	 * 进行计算缩小倍数
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		int inSampleSize = 1;
		final int height = options.outHeight;
		final int width = options.outWidth;
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	PhotosQueue photosQueue = new PhotosQueue();

	public void stopThread() {
		photoLoaderThread.interrupt();
	}

	// stores list of photos to download
	class PhotosQueue {
		private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

		// removes all instances of this ImageView
		public void Clean(ImageView image) {
			for (int j = 0; j < photosToLoad.size();) {
				if (photosToLoad.get(j).imageView == image)
					photosToLoad.remove(j);
				else
					++j;
			}
		}
	}

	class PhotosLoader extends Thread {
		public void run() {
			try {
				while (true) {
					// thread waits until there are any images to load in the
					// queue
					if (photosQueue.photosToLoad.size() == 0)
						synchronized (photosQueue.photosToLoad) {
							photosQueue.photosToLoad.wait();
						}
					if (photosQueue.photosToLoad.size() != 0) {
						PhotoToLoad photoToLoad;
						synchronized (photosQueue.photosToLoad) {
							photoToLoad = photosQueue.photosToLoad.pop();
						}
						Bitmap bmp = getBitmap(photoToLoad.url);
						//memoryCache.put(photoToLoad.url, bmp);
						mImageLruCache.putBitmap(photoToLoad.url, bmp);
                        
						String tag = imageViews.get(photoToLoad.imageView);
						if (tag != null && tag.equals(photoToLoad.url)) {
							BitmapDisplayer bd = new BitmapDisplayer(bmp,
									photoToLoad.imageView);
							Activity a = (Activity) photoToLoad.imageView
									.getContext();
							a.runOnUiThread(bd);
						}
						
						//进行图片资源的释放
						if(bmp!=null&&bmp.isRecycled())
						{
							bmp.recycle();
							bmp=null;
						}
						}
					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
				// allow thread to exit
			}
		}
	}

	PhotosLoader photoLoaderThread = new PhotosLoader();

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;

		public BitmapDisplayer(Bitmap b, ImageView i) {
			bitmap = b;
			imageView = i;
		}

		public void run() {
			if (bitmap != null) {
				imageView.setImageDrawable(new BitmapDrawable(bitmap));
			
			}
			else {
				imageView.setImageResource(stub_id);
			}
		}
	}
    
	/**
	 * 清除缓存
	 */
	public void clearCache() {
		mImageLruCache.clear();
		fileCache.clear();
	}

}

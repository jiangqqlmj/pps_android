package com.pps.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.bean.UpdateInformation;
import com.pps.common.MemoryStatus;

/**
 * 进行下載更新
 * 
 * @author jiangqingqing
 * @time 2013/7/11 15:59
 */
public class UpdateAppManager {

	private Context mContext;
	private ProgressBar mProgressBar; // 显示现在进度Bar
	private TextView update_tv;
	
	private Dialog mDialog;

	private final static int DOWNLOAD_COMPLETE = 1;// 完成
	private final static int DOWNLOAD_NOMEMORY = -1;// 内存异常
	private final static int DOWNLOAD_FAIL = -2;// 失败
	private final static int DOWNLOAD_PROGRESS = 0; // 正在下载-显示进度->update

	private int appName = 0;// 应用名字
	private String appUrl = null;// 应用升级地址
	private File updateDir = null;// 文件目录
	private File updateFile = null;// 升级文件

	private Handler updateHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			// 下载成功
			case DOWNLOAD_COMPLETE:
				//下载成功，关闭进度框
				if(null!=mDialog&&mDialog.isShowing())
				{
				      mDialog.dismiss();
				}
				
				Log.d("update", "下载成功，通知栏显示下载成功点击安装");
				// 确保读写权限
				String cmd = "chmod 777 " + updateFile.getPath();
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 点击安装PendingIntent
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				mContext.startActivity(installIntent);
				break;

			// 下载空间不足
			case DOWNLOAD_NOMEMORY:
				Log.d("update", "下载失败，内存不足重新下载");
				//进行重新下载
				if(null!=mDialog&&mDialog.isShowing())
				{
				      mDialog.dismiss();
				}
				//进行提示需要不需要重新下载
			    showTipDown(mContext);
				break;

			// 下载失败
			case DOWNLOAD_FAIL:
				Log.d("update", "下载失败，下载出错重新下载");
				// 下载失败，重新进行下载程序
				if(null!=mDialog&&mDialog.isShowing())
				{
				      mDialog.dismiss();
				}
				//进行提示需要不需要重新下载
			    showTipDown(mContext);
				break;
			case DOWNLOAD_PROGRESS: // 更新进度
				//下载成功，
				List<String> array=(ArrayList<String>)msg.obj;
				mProgressBar.setProgress(Integer.parseInt(array.get(0)));
				update_tv.setText(array.get(1));
				break;
			}
		}

	};

	/**
	 * 初始化 构造函数
	 * 
	 * @param pContext
	 */
	public UpdateAppManager(Context pContext,int pAppName,String pAppUrl) {
		this.mContext = pContext;
		this.appName=pAppName;
		this.appUrl=pAppUrl;
	}

	/**
	 * 进行下载APP
	 */
	public void updateApp() {
		// 弹出下载进度框
		
		mDialog=new Dialog(mContext);
		mDialog.setTitle("正在下载...");		
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.update_progress_tv, null);
		mProgressBar = (ProgressBar) view.findViewById(R.id.update_progress);
		update_tv = (TextView) view.findViewById(R.id.update_tv);
		mDialog.setContentView(view);
		mDialog.setCancelable(false);
		mDialog.show();
		// 下载数据
		new UpdateThread().execute();
	}

	// 下载数据的线程
	class UpdateThread extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			Log.d("update", "升级地址:" + appUrl);
			// 调用下载函数
			int downloadStatus = downloadUpdateFile(appUrl);
			// 下载成功
			if (downloadStatus == DOWNLOAD_COMPLETE) {
				
				Message message=updateHandler.obtainMessage();
				message.what = DOWNLOAD_COMPLETE;
				updateHandler.sendMessage(message);
			}
			// 内存问题
			if (downloadStatus == DOWNLOAD_NOMEMORY) {
				Message message=updateHandler.obtainMessage();
				message.what = DOWNLOAD_NOMEMORY;
				updateHandler.sendMessage(message);
			}
			// 网络问题
			if (downloadStatus == DOWNLOAD_FAIL) {
				Message message=updateHandler.obtainMessage();
				message.what = DOWNLOAD_FAIL;
				updateHandler.sendMessage(message);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (true) {
				Log.d("update", "下载升级文件程序执行完");
			}
		}

		// 这边在重写一个后台进度变化的函数

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

	}

	// 下载文件
	private int downloadUpdateFile(String downloadUrl) {
			int downloadCount = 0;// 下载大小
			int currentSize = 0;// 当前大小
			long totalSize = 0;// 总大小->当前已经下载的大小
			long updateTotalSize = 0;// 升级包大小

			HttpURLConnection httpConnection = null;
			InputStream is = null;
			FileOutputStream fos = null;

			try {
				URL url = new URL(downloadUrl);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestProperty("User-Agent", "PPStvHttpClient");
				if (currentSize > 0) {
					httpConnection.setRequestProperty("RANGE", "bytes="
							+ currentSize + "-");
				}
				httpConnection.setConnectTimeout(10000);
				httpConnection.setReadTimeout(20000);
				updateTotalSize = httpConnection.getContentLength();// 得到升级包的总大小
				// 内存异常
				if (!MemoryAvailable(updateTotalSize)) {
					if (httpConnection != null) {
						httpConnection.disconnect();
					}
					return DOWNLOAD_NOMEMORY;
				}
				// 网络异常
				if (httpConnection.getResponseCode() == 404) {
					if (httpConnection != null) {
						httpConnection.disconnect();
					}
					return DOWNLOAD_FAIL;
				}
				// 获取数据
				is = httpConnection.getInputStream();
				fos = new FileOutputStream(updateFile, false);
				byte buffer[] = new byte[4096];
				int readsize = 0;
				// 循环获取数据
				while ((readsize = is.read(buffer)) > 0) {
					fos.write(buffer, 0, readsize);
					totalSize += readsize;
					// 为了防止频繁的通知导致应用吃紧，百分比增加5才通知一次
					if ((downloadCount == 0)
							|| (int) (totalSize * 100 / updateTotalSize) >= downloadCount) {
						downloadCount += 5;
						
						//把下载实时的信息 发送出去，在handler中进行处理
						int position=(int) (totalSize * 100 / updateTotalSize);
						String result=String.format("%.2f",(totalSize / 1024.0 / 1024.0))+ "M"+ "/"+ String.format("%.2f",(updateTotalSize / 1024.0 / 1024.0))+ "M";
						List<String> array=new ArrayList<String>();
						array.add(String.valueOf(position));
						array.add(result);
						Message msg=updateHandler.obtainMessage();
						msg.what=DOWNLOAD_PROGRESS;
						msg.obj=array;
						updateHandler.sendMessage(msg);								  
					}
				}
				// 如果总大小大于或者等于升级包的大小
				if (totalSize >= updateTotalSize) {
					Log.d("update", "获取服务端的总大小:" + totalSize);
					Log.d("update", "升级文件的大小:" + updateTotalSize);
					return DOWNLOAD_COMPLETE;
				} else {
					return DOWNLOAD_FAIL;
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// 默认返回错误
			return DOWNLOAD_FAIL;
		}

	// 判断存储空间是否充足的函数，空间大小大于“源文件大小加1M“时返回true
	private boolean MemoryAvailable(long fileSize) {
		Log.d("update", "文件大小:" + fileSize);
		// 大于安装包1M空间,在原来基础上增加了1M
		fileSize += (1024 << 10);
		// SDcard已经装载
		if (MemoryStatus.externalMemoryAvailable()) {
			// 外部存储空间不足
			if ((MemoryStatus.getAvailableExternalMemorySize() <= fileSize)) {
				// 但内部空间充足
				if ((MemoryStatus.getAvailableInternalMemorySize() > fileSize)) {
					// 创建文件
					createFile(false);
					return true;
				} else {
					return false;
				}
			}
			// 外部空间充足
			else {
				// 创建文件
				createFile(true);
				return true;
			}
		}
		// SDcard未装载
		else {
			// 内部存储空间不足
			if (MemoryStatus.getAvailableInternalMemorySize() <= fileSize) {
				return false;
			} else {
				// 创建目录
				createFile(false);
				return true;
			}
		}
	}

	// 根据内部空间状况创建文件路径
	private void createFile(boolean sd_available) {
		if (sd_available) {
			// 创建文件
			// 创建目录和文件
			// sdcard/PPStv_update目录
			updateDir = new File(Environment.getExternalStorageDirectory(),
					UpdateInformation.downloadDir);
		} else {
			// 创建文件
			// 创建目录和文件
			// 无SD卡时，临时文件写在内部存储器中
			// data/data/files目录
			updateDir = mContext.getFilesDir();

		}
		// 文件名
		updateFile = new File(updateDir.getPath(), mContext.getResources()
				.getString(appName) + ".apk");
		// 如果升级目录创建！
		if (!updateDir.exists()) {
			updateDir.mkdirs();
		}
		// 如果升级文件不存在，创建！
		if (!updateFile.exists()) {
			try {
				updateFile.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			// 存在之前的升级文件，删掉！
			updateFile.delete();
			try {
				updateFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    /**
     * 进行提示是否要进行重新下载
     * @param pContext
     */
	private void showTipDown(Context pContext)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(pContext);
		builder.setTitle("需要进行重新下载吗?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
                  		updateApp();//进行下载APK，更新		
			}
		}).setNegativeButton("取消", null);
		builder.create().show();
	}
}

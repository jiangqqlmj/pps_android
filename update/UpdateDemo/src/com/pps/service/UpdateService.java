package com.pps.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.bean.UpdateInformation;
import com.pps.common.MemoryStatus;

/**
 * 进行后台检测升级的工具类
 * @author jiangqingqing
 * @time 2013/08/05 15:35
 */
public class UpdateService extends Service {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
    //下载进度框
    private View mDialogView;
    private TextView tv_update_progress_schedule;
    private ProgressBar progressbar_update_progress;
    private TextView tv_update_progress_contenxt;
    private Dialog mDialog;
    //下载失败 提醒框
    private View mTipDialogView;
    private Button btn_update_dialog_import_commit;
    private Button btn_update_dialog_import_cancel;
    private Button btn_update_dialog_import_force;
    private Dialog mTipDialog;
    
	private final static int DOWNLOAD_COMPLETE = 1;// 完成
	private final static int DOWNLOAD_NOMEMORY = -1;// 内存异常
	private final static int DOWNLOAD_FAIL = -2;// 失败
	private final static int DOWNLOAD_PROGRESS = 0; // 正在下载-显示进度->update
	private final static int DOWNLOAD_CANCEL = 3; // 撤销下载

	private int appName = 0;// 应用名字
	private String appUrl = null;// 应用升级地址
	private int appUpdateMsg = 0;// 升级标识
	private File updateDir = null;// 文件目录
	private File updateFile = null;// 升级文件
	private boolean isCancel = false; // 设置标志位，判断异步任务是否被取消
	private Handler updateHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:// 下载成功
				// 下载成功，关闭进度框
				if (null != mDialog && mDialog.isShowing()) {
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
				// 进行安装
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(installIntent);
				// 关闭服务
				stopSelf();
				break;
			case DOWNLOAD_NOMEMORY:// 下载空间不足
				Log.d("update", "下载失败，内存不足重新下载");
				// 进行重新下载
				if (null != mDialog && mDialog.isShowing()) {
					mDialog.dismiss();
				}
				// 进行提示需要不需要重新下载
				initTipDialog();
				break;
			case DOWNLOAD_FAIL: // 下载失败
				Log.d("update", "下载失败，下载出错重新下载");
				// 下载失败，重新进行下载程序
				if (null != mDialog && mDialog.isShowing()) {
					mDialog.dismiss();
				}
				// 进行提示需要不需要重新下载
				initTipDialog();
				break;
			case DOWNLOAD_PROGRESS: // 更新进度
				List<String> array = (ArrayList<String>) msg.obj;
				tv_update_progress_schedule.setText(array.get(0)+"%");
				progressbar_update_progress.setProgress(Integer.valueOf(array.get(0)));
				tv_update_progress_contenxt.setText("文件大小 ("+array.get(1)+")");
				break;
//			case DOWNLOAD_CANCEL: // 更新取消
//				if (null != mDialog && mDialog.isShowing()) {
//					mDialog.dismiss();
//					isCancel = false;// 重新把撤销标识改成false;
//					stopSelf();
//					if (appUpdateMsg == 1) {
//						// 强制更新过程中，如果撤销下载更新，那就关闭整个应用
//						android.os.Process.killProcess(android.os.Process
//								.myPid());
//					}
//				}				
//				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		mContext = this;
		
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mLayoutInflater = LayoutInflater.from(mContext);
		// 获取应用名称与下载地址
		appName = intent.getIntExtra("appname", 0);
		appUrl = intent.getStringExtra("appurl");
		// 获取表示是强制升级还是正常升级
		appUpdateMsg = intent.getIntExtra("update_msg", 0);
		// 进行更新
		updateApp();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 进行下载APP
	 */
	public void updateApp() {
		
		mDialog = new Dialog(mContext, R.style.updateDialogTheme);
		mDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mDialogView = mLayoutInflater.inflate(R.layout.update_progress_dialog_tv, null);
		tv_update_progress_schedule=(TextView)mDialogView.findViewById(R.id.tv_update_progress_schedule);
		progressbar_update_progress=(ProgressBar)mDialogView.findViewById(R.id.progressbar_update_progress);
		tv_update_progress_contenxt=(TextView)mDialogView.findViewById(R.id.tv_update_progress_contenxt);
		mDialog.setContentView(mDialogView);
		mDialog.setCancelable(true);
		mDialog.show();
		// 下载数据
		new UpdateThread().execute();
	}

	// 下载数据的异步任务
	class UpdateThread extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			Log.d("update", "升级地址:" + appUrl);
			// 调用下载函数
			int downloadStatus = downloadUpdateFile(appUrl);
			// 下载成功
			if (downloadStatus == DOWNLOAD_COMPLETE) {
				Message message = updateHandler.obtainMessage();
				message.what = DOWNLOAD_COMPLETE;
				updateHandler.sendMessage(message);
			}
			// 内存问题
			if (downloadStatus == DOWNLOAD_NOMEMORY) {
				Message message = updateHandler.obtainMessage();
				message.what = DOWNLOAD_NOMEMORY;
				updateHandler.sendMessage(message);
			}
			// 网络问题
			if (downloadStatus == DOWNLOAD_FAIL) {
				Message message = updateHandler.obtainMessage();
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

//				// 判断是否已经被取消下载
//				if (isCancel) {
//					// 发送撤销下载的消息到handler中进行处理
//					Message msg = updateHandler.obtainMessage();
//					msg.what = DOWNLOAD_CANCEL;
//					updateHandler.sendMessage(msg);
//					return DOWNLOAD_CANCEL; // 直接返回中断下载
//				}

				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 每次还是重新获取得到升级包的总大小
				// 进行防止，网络中断之后，突然连接上，要下载的文件数据大小获取错误
				updateTotalSize = httpConnection.getContentLength();
				// 进行禁毒通知
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) >= downloadCount) {
					downloadCount += 1;

					// 把下载实时的信息 发送出去，在handler中进行处理
					int position = (int) (totalSize * 100 / updateTotalSize); 
					String result = String.format("%.2f",
							(totalSize / 1024.0 / 1024.0))
							+ "M"
							+ "/"
							+ String.format("%.2f",
									(updateTotalSize / 1024.0 / 1024.0)) + "M";
					// 构造一个集合对象，第一个存下载进度，第二个存下载的进度信息
					List<String> array = new ArrayList<String>(2);
					array.add(String.valueOf(position));
					array.add(result);
					Message msg = updateHandler.obtainMessage();
					msg.what = DOWNLOAD_PROGRESS;
					msg.obj = array;
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
			
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
		// 默认返回错误
		return DOWNLOAD_FAIL;
	}

	/**
	 * 判断存储空间是否充足的函数，空间大小大于“源文件大小加1M“时返回true,并且进行创建文件
	 * 
	 * @param fileSize
	 *            要现在的源文件的大小
	 * @return
	 */
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
				// 创建文件
				createFile(false);
				return true;
			}
		}
	}

	/**
	 * 根据内部空间状况创建文件
	 * 
	 * @param sd_availabled
	 *            true：在sdcard中创建, false：在内部存储中创建
	 */
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
	 * 下载失败,进行是否重新下载提醒
	 */
	private void initTipDialog()
	{
		mTipDialog=new Dialog(mContext, R.style.updateDialogTheme);
		mTipDialogView=mLayoutInflater.inflate(R.layout.update_dialog_important_tv, null);
		mTipDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		btn_update_dialog_import_commit=(Button)mTipDialogView.findViewById(R.id.btn_update_dialog_import_commit);
		btn_update_dialog_import_cancel=(Button)mTipDialogView.findViewById(R.id.btn_update_dialog_import_cancel);
		btn_update_dialog_import_force=(Button)mTipDialogView.findViewById(R.id.btn_update_dialog_import_force);	    
        mTipDialog.setContentView(mTipDialogView);
		mTipDialog.setCancelable(false);
	    mTipDialog.show();
		//判断是是强制升级还是正常升级.来显示或者隐藏跳过与退出按钮
		if(appUpdateMsg==1)  //强制
		{
			btn_update_dialog_import_cancel.setVisibility(View.GONE);
			btn_update_dialog_import_force.setVisibility(View.VISIBLE);
		}else if(appUpdateMsg==2) //正常
		{
			btn_update_dialog_import_cancel.setVisibility(View.VISIBLE);
			btn_update_dialog_import_force.setVisibility(View.GONE);
		}
		
		btn_update_dialog_import_commit.setOnClickListener(new SetOnClickListener());
		btn_update_dialog_import_cancel.setOnClickListener(new SetOnClickListener());
		btn_update_dialog_import_force.setOnClickListener(new SetOnClickListener());
	}
	
	/**
	 * 下载失败提醒框，按钮点击事情监听处理
	 * @author jiangqingqing
	 *
	 */
	private class SetOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_update_dialog_import_commit:	
				if(mTipDialog!=null&&mTipDialog.isShowing())
				{
					mTipDialog.dismiss();
				}
				updateApp();				
				break;
			case R.id.btn_update_dialog_import_cancel:
				if(mTipDialog!=null&&mTipDialog.isShowing())
				{
					mTipDialog.dismiss();
					stopSelf();
				}
				break;			
			case R.id.btn_update_dialog_import_force:
				if(mTipDialog!=null&&mTipDialog.isShowing())
				{
					mTipDialog.dismiss();
				}
				//退出应用
				//isCancel = true;
				stopSelf();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
				break;
		}
		}
		
	}

}

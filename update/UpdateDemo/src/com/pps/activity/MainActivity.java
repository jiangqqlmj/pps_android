package com.pps.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.bean.UpdateInformation;
import com.pps.update.UpdateAppManager;

/**
 * 进行更新应用 1：启动后台线程.进行检测设置版本信息 2:如果需要版本升级，进行弹框显示， 3：后台下载最新的apk包， 4：自动进行安装
 * 
 * @author jiangqingqing
 * 
 */
public class MainActivity extends Activity {
	private Context mContext;
	private Button update_btn;
	
	
	//测试下载进度对话框变量
    private LayoutInflater mLayoutInflater;
    private View mDialogView;
    private TextView tv_update_progress_schedule;
    private ProgressBar progressbar_update_progress;
    private TextView tv_update_progress_contenxt;
    private Dialog mDialog;
    
	//广播信息
	private static final String UPDATE_ACTION = "com.pps.receiver.update";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = MainActivity.this;
		update_btn = (Button) this.findViewById(R.id.update_btn);
		update_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1:启动后台线程.进行检测设置版本信息
				//new Thread(new MyCheckUpThread()).start();
				//checkVersion(mContext);
				
				//2:发送广播进行软件更新
				Intent _Intent=new Intent(UPDATE_ACTION);
				mContext.sendBroadcast(_Intent);
				//initView();
				//initDialog();
			 }
		});

	}
	
	/**
	 *进行测试对话框--初始化界面元素 
	 */
	private void initView()
	{
		mLayoutInflater=LayoutInflater.from(mContext);
		mDialogView=mLayoutInflater.inflate(R.layout.update_progress_dialog_tv, null);
		tv_update_progress_schedule=(TextView)mDialogView.findViewById(R.id.tv_update_progress_schedule);	
		progressbar_update_progress=(ProgressBar)mDialogView.findViewById(R.id.progressbar_update_progress);
		tv_update_progress_contenxt=(TextView)mDialogView.findViewById(R.id.tv_update_progress_contenxt);
		
	}
	
	/**
	 * 进行测试下载进度对话框--初始化对话框
	 */
	private void initDialog()
	{
		mDialog=new Dialog(mContext, R.style.updateDialogTheme);
		mDialog.setContentView(mDialogView);
		mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mDialog.show();
	}
	
	
	/**
	 * 检测版本号->自动升级
	 * 
	 * @param context
	 */
	public void checkVersion(Context pContext) {
		Log.d("update", "本地版本:" + UpdateInformation.localVersion);
		Log.d("update", "服务器版本:" + UpdateInformation.serverVersion);
		Log.d("update", "服务器标志:" + UpdateInformation.serverFlag);
		Log.d("update", "之前强制升级版本号-:" + UpdateInformation.lastForce);
		Log.d("update", "升级信息:" + UpdateInformation.upgradeinfo);

		// Flag==1,提示升级
		if (UpdateInformation.localVersion < UpdateInformation.serverVersion
				&& (UpdateInformation.serverFlag == 1)) {
			if (UpdateInformation.localVersion < UpdateInformation.lastForce) {
				Log.d("update", "本地版本小于之前强制升级版本号,强制升级");
				forceUpdate(pContext);
			} else {
				Log.d("update", "服务器端标志为1-正常升级");
				normalUpdate(pContext);
			}

		}
		// Flag==2,强制升级
		else if (UpdateInformation.localVersion < UpdateInformation.serverVersion
				&& (UpdateInformation.serverFlag == 2)) {
			Log.d("update", "服务器端标志位2-强制升级");
			forceUpdate(pContext);

		}
		// 不用升级
		else {
			Log.d("update", "不用升级，清理升级目录");
			cleanUpdateFile(pContext);
		}
	}

	/**
	 * 进行强制升级，只有确定进行升级的按钮
	 * 
	 * @param pContext
	 */
	private void forceUpdate(final Context pContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(pContext);
		builder.setTitle("更新升级");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UpdateAppManager update = new UpdateAppManager(pContext,
						R.string.app_name, UpdateInformation.Durl);
				update.updateApp();
			}
		}).setCancelable(false);
		builder.create();
		builder.show();

	}

	// 正常升级
	private void normalUpdate(final Context pContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(pContext);
		builder.setTitle("更新升级");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new UpdateAppManager(pContext, R.string.app_name,
						UpdateInformation.Durl).updateApp();

			}
		}).setNegativeButton("取消", null);
		builder.create();
		builder.show();
	}

	// 清理升级目录
	// 清除之前的下载文件，避免浪费用户空间
	private void cleanUpdateFile(Context pContext) {
		File updateDir;
		File updateFile;
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			// 有SD卡，文件写到SD卡
			updateDir = new File(Environment.getExternalStorageDirectory(),
					UpdateInformation.downloadDir);
		}
		// 无SD卡时，临时文件写在内部存储器中
		else {
			// files目录
			updateDir = pContext.getFilesDir();
		}
		updateFile = new File(updateDir.getPath(), pContext.getResources()
				.getString(R.string.app_name) + ".apk");
		if (updateFile.exists()) {
			Log.d("update", "升级包存在，删除升级包");
			updateFile.delete();
		} else {
			Log.d("update", "升级包不存在，不用删除升级包");
		}

	}

	/**
	 * 后台检测并且设置版本新的线程类
	 * 
	 * @author jiangqingqing 　
	 * @time 2013/7/11 15:44
	 */
	class MyCheckUpThread implements Runnable {
		@Override
		public void run() {
			// ......
			// 1: 进行后台检测 设置版本的各项信息—UpdateInformation.java
			// 上面那一步的检测方法暂时不设置
			// 2:开始进行判断版本，强制升级或者正常升级
			checkVersion(mContext);
		}
	}
}

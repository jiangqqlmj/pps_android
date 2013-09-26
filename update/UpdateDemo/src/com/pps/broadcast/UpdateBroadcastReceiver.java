package com.pps.broadcast;

import java.io.File;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.bean.UpdateInformation;
import com.pps.service.UpdateService;

/**
 * 广播接受者，检测升级请求
 * 
 * @author jiangqingqing
 * @time 2013/08/05 16:02
 */
public class UpdateBroadcastReceiver extends BroadcastReceiver {

	private static final String UPDATE_ACTION = "com.pps.receiver.update";
	private Context mContext;
	private Dialog mDialog;
	private LayoutInflater mLayoutInflater;
	private View mDialogView;
	private Button btn_update_dialog_commit; //确定
	private Button btn_update_dialog_cancel; //跳过
 	private Button btn_update_dialog_force;  //强制更新升级确定
 	//private TextView tv_update_dialog_info_version;
 	private TextView tv_update_dialog_info_content;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent.getAction().equals(UPDATE_ACTION)) {
			Log.d("update", "收到软件更新广播...");
			mContext=context;
			mLayoutInflater=LayoutInflater.from(mContext);
			mDialogView=mLayoutInflater.inflate(R.layout.update_dialog_tv, null);
			initView();
			// 收到升级的广播
			checkVersion(context);
		}
	}

	/**
	 * 检测版本号->自动升级
	 * 
	 * @param pContext
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
//		AlertDialog builder = new AlertDialog.Builder(pContext).setTitle("更新升级").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// new UpdateAppManager(pContext, R.string.app_name,
//				// UpdateInformation.Durl).updateApp();
//				Intent intent = new Intent(pContext, UpdateService.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra("appname", R.string.app_name);
//				intent.putExtra("appurl", UpdateInformation.Durl);
//				intent.putExtra("update_msg", 1); //强制升级，传入1
//				// 启动升级服务
//				pContext.startService(intent);
//			}
//		}).setCancelable(false).create();
//		builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		builder.show();
		//
		initDialog();
		btn_update_dialog_commit.setVisibility(View.GONE);
		btn_update_dialog_cancel.setVisibility(View.GONE);
		btn_update_dialog_force.setVisibility(View.VISIBLE);
		btn_update_dialog_force.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(pContext, UpdateService.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("appname", R.string.app_name);
				intent.putExtra("appurl", UpdateInformation.Durl);
				intent.putExtra("update_msg", 1); //强制升级，传入1
				// 启动升级服务
				pContext.startService(intent);
				if(mDialog!=null&&mDialog.isShowing())
				{
					mDialog.dismiss();
				}
			}
		});
	}
	// 正常升级
	private void normalUpdate(final Context pContext) {
//		AlertDialog builder = new AlertDialog.Builder(pContext).setTitle("更新升级").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// new UpdateAppManager(pContext, R.string.app_name,
//				// UpdateInformation.Durl).updateApp();
//				Intent intent = new Intent(pContext, UpdateService.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra("appname", R.string.app_name);
//				intent.putExtra("appurl", UpdateInformation.Durl);
//				intent.putExtra("update_msg", 2); //正常升级，传入2
//				// 启动升级服务
//				pContext.startService(intent);
//			}
//		}).setNegativeButton("取消", null).create();
//		builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		builder.show();
		initDialog();
		btn_update_dialog_commit.setVisibility(View.VISIBLE);
		btn_update_dialog_cancel.setVisibility(View.VISIBLE);
		btn_update_dialog_force.setVisibility(View.GONE);
		btn_update_dialog_commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(pContext, UpdateService.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("appname", R.string.app_name);
				intent.putExtra("appurl", UpdateInformation.Durl);
				intent.putExtra("update_msg", 2); //正常升级，传入2
				// 启动升级服务
				pContext.startService(intent);	
				if(mDialog!=null&&mDialog.isShowing())
				{
					mDialog.dismiss();
				}
			}
		});
		btn_update_dialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mDialog!=null&&mDialog.isShowing())
				{
					mDialog.dismiss();
				}
			}
		});
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
	 * 初始化界面元素
	 */
	private void initView()
	{
		//tv_update_dialog_info_version=(TextView)mDialogView.findViewById(R.id.tv_update_dialog_info_version);
		tv_update_dialog_info_content=(TextView)mDialogView.findViewById(R.id.tv_update_dialog_info_content);
		btn_update_dialog_commit=(Button)mDialogView.findViewById(R.id.btn_update_dialog_commit);
		btn_update_dialog_cancel=(Button)mDialogView.findViewById(R.id.btn_update_dialog_cancel);
		btn_update_dialog_force=(Button)mDialogView.findViewById(R.id.btn_update_dialog_force);		
	}
	/**
	 * 初始化创建显示Dialog
	 */
	private void initDialog()
	{
		mDialog=new Dialog(mContext, R.style.updateDialogTheme);
		//设置升级信息
		//新版本(v1.5.1)更新提示:
		//tv_update_dialog_info_version.setText("新版本(v"+UpdateInformation.serverVersion+")更新提示:");
		tv_update_dialog_info_content.setText(UpdateInformation.upgradeinfo);
		mDialog.setContentView(mDialogView);
		mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mDialog.setCancelable(false);
		mDialog.show();
	}
}

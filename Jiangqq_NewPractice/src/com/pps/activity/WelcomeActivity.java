package com.pps.activity;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.pps.BaseActivity;
import com.pps.utils.DownXML;
import com.pps.utils.ZipToFile;

/**
 * 欢迎界面
 * @author jiangqingqing
 * @time 2013/09/12
 */

public class WelcomeActivity extends BaseActivity {
    public static final String TAG="jiangqq";
	
    private static final int DOWNLOAD_SUCCESS=1;
    private static final int DOWNLOAD_FAILED=-1;
    
    //private RelativeLayout welcome_ic_relative;
	private Handler mHandler=new Handler()
	{
		/* (non-Javadoc) 进行消息处理
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case DOWNLOAD_FAILED:
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 Intent _Intent1=new Intent();
				 _Intent1.setClass(WelcomeActivity.this, MainFragmentActivity.class);
				 WelcomeActivity.this.startActivity(_Intent1);
				 WelcomeActivity.this.finish();
				break;

			case DOWNLOAD_SUCCESS:
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 Intent _Intent2=new Intent();
				 _Intent2.setClass(WelcomeActivity.this, MainFragmentActivity.class);
				 WelcomeActivity.this.startActivity(_Intent2);
				 WelcomeActivity.this.finish();
				break;
			}
			
			
			super.handleMessage(msg);
		}
	   	
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		Thread thread=new Thread(runnable_refresh);
		thread.start();
		
//		welcome_ic_relative=(RelativeLayout)this.findViewById(R.id.welcome_ic_relative);
//		Animation mAnimation=new AlphaAnimation(0.5f, 1);
//		mAnimation.setDuration(3000);
//		welcome_ic_relative.setAnimation(mAnimation );
//		mAnimation.setAnimationListener(new AnimationListener() {
//			
//			@Override
//			public void onAnimationStart(Animation animation) {
//				
//			}
//			
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				
//			}
//			
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				 Intent _Intent=new Intent();
//				 _Intent.setClass(WelcomeActivity.this, MainFragmentActivity.class);
//				 WelcomeActivity.this.startActivity(_Intent);
//				 WelcomeActivity.this.finish();
//			}
//		});
	}
	
	//从后台进行下载数据
	Runnable runnable_refresh = new Runnable() {
		@Override
		public void run() {
			try {
				Log.v(TAG, "在首页进行获取列表的zip包...");
				boolean reault = DownXML
						.getFile("http://list1.ppstream.com/mobile/newipadc/sub/141.xml.zip");
				if (!reault) {
					Message msg=mHandler.obtainMessage();
					msg.what=DOWNLOAD_FAILED;
					mHandler.sendMessage(msg);
					return;
				}
				// 2,对压缩包进行解压缩
				boolean flag = ZipToFile.unzip("/sdcard", "141.xml.zip",
						"/sdcard/141.xml");
				if (flag) {
					Log.v(TAG, "解压成功.....");
					// 解压成功，并且删除该压缩包,
					File file_zip = new File("/sdcard/141.xml.zip");
					if (file_zip.exists()) {
						file_zip.delete();
					}
					Message msg=mHandler.obtainMessage();
					msg.what=DOWNLOAD_SUCCESS;
					mHandler.sendMessage(msg);
				} else {
					Log.v(TAG, "解压失败.....");
					Message msg=mHandler.obtainMessage();
					msg.what=DOWNLOAD_FAILED;
					mHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}

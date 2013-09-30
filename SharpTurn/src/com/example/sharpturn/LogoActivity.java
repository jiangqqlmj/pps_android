package com.example.sharpturn;

import java.util.List;

import com.example.sharpturn.db.DBManager;
import com.example.sharpturn.model.SharpModel;
import com.example.sharpturn.utils.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 *
 * @author jiangqingqing
 * @time 2013/09/30 10:44
 */
public class LogoActivity extends Activity {
    private RelativeLayout logo_relativelayout;
    private List<SharpModel> mLists; 
    private DBManager mDbManager;
    private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Intent _Intent=new Intent(LogoActivity.this, MainActivity.class);
				LogoActivity.this.startActivity(_Intent);
				LogoActivity.this.finish();
				break;

			case -1:
				break;
			}
		}
    	
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.logo);
		logo_relativelayout=(RelativeLayout)this.findViewById(R.id.logo_relativelayout);
		Animation mAlphaAnimation=new AlphaAnimation(0.2f,1.0f);
		mAlphaAnimation.setDuration(4500);
		logo_relativelayout.setAnimation(mAlphaAnimation);
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				Log.d("jiangqq", ">>>>>>>>>>>>开始读取数据");
				mLists = FileUtils.getSharpModels(LogoActivity.this);
				Log.d("jiangqq", ">>>>>>>>>>>>读取数据成功");
				Log.d("jiangqq", ">>>>>>>>>>>>准备开始保存数据到数据库");
				mDbManager=new DBManager(LogoActivity.this);
				boolean result= mDbManager.insertSharpByList(mLists);
				Message msg=mHandler.obtainMessage();
				if(result)
				{
					Log.d("jiangqq", ">>>>>>>>>>>>保存数据到数据库成功");
					msg.what=1;
					mHandler.sendMessage(msg);
				}else {
					Log.d("jiangqq", ">>>>>>>>>>>>保存数据到数据库失败");
					msg.what=-1;
					mHandler.sendMessage(msg);
				}
				
			}
		}).start();
		
	}
}

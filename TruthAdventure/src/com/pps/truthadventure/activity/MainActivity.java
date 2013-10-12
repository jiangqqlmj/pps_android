package com.pps.truthadventure.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * 真心话大冒险 主界面
 * 
 * @author jiangqingqing
 * 
 */
public class MainActivity extends Activity {
	private Button main_btn_number;
	private Button main_btn_name;
	private Button main_btn_function;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		initView();
		initValidata();
		bindData();
		initListener();
	}

	private void initView() {
		main_btn_number = (Button) findViewById(R.id.main_btn_number);
		main_btn_name = (Button) findViewById(R.id.main_btn_name);
		main_btn_function = (Button) findViewById(R.id.main_btn_function);
	}

	private void initValidata() {
		mIntent=new Intent();
	}

	private void initListener() {
		main_btn_number.setOnClickListener(new MySetOnClickListener());
		main_btn_name.setOnClickListener(new MySetOnClickListener());
		main_btn_function.setOnClickListener(new MySetOnClickListener());
	}

	private void bindData() {

	}

	class MySetOnClickListener implements  android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_btn_number:  //代号玩法
				mIntent.setClass(MainActivity.this, SymbolActivity.class);
				MainActivity.this.startActivity(mIntent);
				break;
			case R.id.main_btn_name:    //姓名玩法
				mIntent.setClass(MainActivity.this, SurnameActivity.class);
				MainActivity.this.startActivity(mIntent);
				break;
			case R.id.main_btn_function:  //功能设置
				mIntent.setClass(MainActivity.this, SettingActivity.class);
				MainActivity.this.startActivity(mIntent);
				break;
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			// 监听返回键 来退出应用
	      AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
	      builder.setTitle("温馨提示");
	      builder.setMessage("你确定要退出应用?");
	      builder.setPositiveButton("确定",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
				dialog.dismiss();
			}
		}).setNegativeButton("取消", null).create();
	      builder.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}

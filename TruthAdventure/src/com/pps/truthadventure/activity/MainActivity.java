package com.pps.truthadventure.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

	}

	private void initListener() {
		main_btn_number.setOnClickListener(new MySetOnClickListener());
		main_btn_name.setOnClickListener(new MySetOnClickListener());
		main_btn_function.setOnClickListener(new MySetOnClickListener());
	}

	private void bindData() {

	}

	class MySetOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_btn_number:  //代号玩法

				break;
			case R.id.main_btn_name:    //姓名玩法

				break;
			case R.id.main_btn_function:  //功能设置

				break;
			}
		}

	}
}

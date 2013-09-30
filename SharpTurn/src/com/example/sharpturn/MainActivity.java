package com.example.sharpturn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

/**
 * 脑经急转弯的主界面
 * @author jiangqingqing
 * @time 2013/09/30
 */
public class MainActivity extends Activity {
	private Button main_btn_random;
	private Button main_btn_smooth;
	private Button main_btn_book;
	private Intent mIntent;
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.main);
	initView();
	initValidata();
	initListener();
  }
  
  /**
   * 初始化界面元素
   */
  private void initView()
  {
	  main_btn_random=(Button)findViewById(R.id.main_btn_random);
	  main_btn_smooth=(Button)findViewById(R.id.main_btn_smooth);
	  main_btn_book=(Button)findViewById(R.id.main_btn_book);
  }
  
  /**
   * 初始化变量
   */
  private void initValidata()
  {
	 mIntent=new Intent();
  }
  /**
   * 初始化监听器
   */
  private void initListener()
  {
	  main_btn_random.setOnClickListener(new MySetOnClickListener()); 
	  main_btn_smooth.setOnClickListener(new MySetOnClickListener());
	  main_btn_book.setOnClickListener(new MySetOnClickListener());
  }
  
  class MySetOnClickListener implements OnClickListener
  {

	@Override
	public void onClick(View v) {
    switch (v.getId()) {
	case R.id.main_btn_random:
		mIntent.setClass(MainActivity.this, DetailsRandomActivity.class);
		MainActivity.this.startActivity(mIntent);
		MainActivity.this.finish();
		break;

	case R.id.main_btn_smooth:
		mIntent.setClass(MainActivity.this,SmoothActivity.class);
		MainActivity.this.startActivity(mIntent);
		MainActivity.this.finish();
		break;
	case R.id.main_btn_book:
		mIntent.setClass(MainActivity.this,BookActivity.class);
		MainActivity.this.startActivity(mIntent);
		MainActivity.this.finish();
		break;
	}		
	}	  
  }
}

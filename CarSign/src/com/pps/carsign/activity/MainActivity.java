package com.pps.carsign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.pps.carsign.adapter.TypeAdapter;

public class MainActivity extends Activity {
	private GridView gv_main;
	private Integer[] mImageView;;
	private TypeAdapter mTypeAdapter;

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
		gv_main = (GridView) findViewById(R.id.gv_main);
	}

	private void initValidata() {
		mImageView = new Integer[] { R.drawable.china, R.drawable.germany,
				R.drawable.usa, R.drawable.uk, R.drawable.france,
				R.drawable.japan, R.drawable.korea, R.drawable.italy };
		mTypeAdapter=new TypeAdapter(this, mImageView);
	}

	private void bindData() {
		gv_main.setAdapter(mTypeAdapter);
	}

	private void initListener() {
		gv_main.setOnItemClickListener(new MySetOnItemClickListener());
	}
	
	/*
	 * GridView的item的点击事件
	 */
	class MySetOnItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
	       		Log.d("jiangqq", ">>>>点击了GirdView的第"+position+"Item");
	       		Intent mIntent=new Intent();
	       		mIntent.putExtra("list_item", position);
	       		mIntent.setClass(MainActivity.this, ListSignActivity.class);
	       		MainActivity.this.startActivity(mIntent);
	       		MainActivity.this.finish();
	  }	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
		   showDialog();	
		}
		return super.onKeyDown(keyCode, event);
	}
	
    /**
     * 显示退出按钮
     */
	private void showDialog()
	{
		
	}
}

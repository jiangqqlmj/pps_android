package com.pps.carsign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pps.carsign.BaseActivity;

/**
 * 显示汽车标志的详情页
 * @author jiangqingqing
 * @time 2013/10/13 9:33
 */
public class DetailsSignActivity extends BaseActivity {
	private Button btn_details_head_back;
	private TextView tv_details_title_head;
	private ImageView img_details;
	private TextView tv_details_content;
	
	private String car_name;
	private Integer car_img;
	private String car_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details);
		initView();
		initValidata();
		bindData();
		initListener();
	}

	/**
	 * 初始化界面元素
	 */
	private void initView() {
		btn_details_head_back=(Button)findViewById(R.id.btn_details_head_back);
		tv_details_title_head=(TextView)findViewById(R.id.tv_details_title_head);
		img_details=(ImageView)findViewById(R.id.img_details);
		tv_details_content=(TextView)findViewById(R.id.tv_details_content);
	}

	/**
	 * 初始化变量
	 */
	private void initValidata() {
		car_name=getIntent().getStringExtra("car_name");
		car_img=getIntent().getIntExtra("img_id", R.drawable.logo_icon);
		car_content=getIntent().getStringExtra("car_content");
	}

	/**
	 * 绑定数据
	 */
	private void bindData() {
		tv_details_title_head.setText(car_name);
		img_details.setImageResource(car_img);
		tv_details_content.setText(car_content);
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		btn_details_head_back.setOnClickListener(new MySetOnClickListener());
	}
	
	class MySetOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent _Intent=new Intent(DetailsSignActivity.this,ListSignActivity.class);
			DetailsSignActivity.this.startActivity(_Intent);
			DetailsSignActivity.this.finish();
		}
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			Intent _Intent=new Intent(DetailsSignActivity.this,ListSignActivity.class);
			DetailsSignActivity.this.startActivity(_Intent);
			DetailsSignActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

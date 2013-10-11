package com.pps.carsign.activity;

import com.pps.carsign.adapter.ListSignAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 根据汽车的产地 进行显示相应的汽车品牌列表
 * 
 * @author jiangqingqing
 * @time 2013/10/11 15:21
 */

public class ListSignActivity extends Activity {
	private Button btn_head_back;
	private TextView tv_title_head;
	private ListView lv_list_sign;
	
	private Integer[] mImageView_Icons;
	private String[] mCar_Names;
	private String[] mCar_Contents;
	
	private ListSignAdapter mListSignAdapter;
	private Intent mIntent;
	
	private int list_item; //标记点击的汽车产地类型

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_sign);
		initView();
		initValidata();
		bindData();
		initListener();
	}
	
	/**
	 * 初始化界面元素
	 */
	private void initView()
	{	
		btn_head_back=(Button)findViewById(R.id.btn_head_back);
		tv_title_head=(TextView)findViewById(R.id.tv_title_head);
		lv_list_sign=(ListView)findViewById(R.id.lv_list_sign);
		
	}
	
	/**
	 * 初始化变量
	 */
	private void initValidata()
	{
		list_item=getIntent().getIntExtra("list_item", 0);
		switch (list_item) {
		case 0:   //国产
			mImageView_Icons=new Integer[]{R.drawable.china_1,R.drawable.china_2,R.drawable.china_3,
					R.drawable.china_4,R.drawable.china_5,R.drawable.china_6,R.drawable.china_7,
					R.drawable.china_8,R.drawable.china_9,R.drawable.china_10,R.drawable.china_11,
					R.drawable.china_12,R.drawable.china_13,R.drawable.china_14,R.drawable.china_15,
					R.drawable.china_16,R.drawable.china_17,R.drawable.china_18,R.drawable.china_19,
					R.drawable.china_20,R.drawable.china_21,R.drawable.china_22};
			mCar_Names=getResources().getStringArray(R.array.china_car_names);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			
			break;
        case 1:   //德国
			break;
        case 2:   //美国
			break;
        case 3:   //英国 
			break;
        case 4:   //法国
			break;
        case 5:   //日本
			break;
        case 6:   //韩国 
			break;
        case 7:   //意大利
			break;
		}
		
		
	}
	
	/**
	 * 绑定数据
	 */
	private void bindData()
	{
		lv_list_sign.setAdapter(mListSignAdapter);
	}
	
	/**
	 * 初始化监听器
	 */
	private void initListener()
	{
		btn_head_back.setOnClickListener(new MySetOnClickListener());
		lv_list_sign.setOnItemClickListener(new MySetOnItemClickListener());
	}
	
	// 按钮自定义监听器
	class MySetOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_head_back:  // 返回键
				 mIntent=new Intent(ListSignActivity.this,MainActivity.class);
				 ListSignActivity.this.startActivity(mIntent);
				 ListSignActivity.this.finish();
				break;

			default:
				break;
			}
		}
		
	}
    
	// 列表点击自定义监听器
	class MySetOnItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
		}
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			 mIntent=new Intent(ListSignActivity.this,MainActivity.class);
			 ListSignActivity.this.startActivity(mIntent);
			 ListSignActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

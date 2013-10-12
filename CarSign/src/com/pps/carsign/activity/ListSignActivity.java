package com.pps.carsign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pps.carsign.adapter.ListSignAdapter;

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
	private String[] mCar_Country;
	
	private ListSignAdapter mListSignAdapter;
	private Intent mIntent;
	
	public static int list_item; //标记点击的汽车产地类型

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
		//list_item=getIntent().getIntExtra("list_item", 0);
		mCar_Country=getResources().getStringArray(R.array.country);
		switch (list_item) {
		case 0:   //国产
			mImageView_Icons=new Integer[]{R.drawable.china_1,R.drawable.china_2,R.drawable.china_3,
					R.drawable.china_4,R.drawable.china_5,R.drawable.china_6,R.drawable.china_7,
					R.drawable.china_8,R.drawable.china_9,R.drawable.china_10,R.drawable.china_11,
					R.drawable.china_12,R.drawable.china_13,R.drawable.china_14,R.drawable.china_15,
					R.drawable.china_16,R.drawable.china_17,R.drawable.china_18,R.drawable.china_19,
					R.drawable.china_20,R.drawable.china_21,R.drawable.china_22,R.drawable.china_23,
					R.drawable.china_24,R.drawable.china_25,R.drawable.china_26,R.drawable.china_27,
					R.drawable.china_28,R.drawable.china_29,R.drawable.china_30,R.drawable.china_31,
					R.drawable.china_32,R.drawable.china_33,R.drawable.china_34,R.drawable.china_35,
					R.drawable.china_36,R.drawable.china_37,R.drawable.china_38,R.drawable.china_39,
					R.drawable.china_40};
			mCar_Names=getResources().getStringArray(R.array.china_car_names);
			mCar_Contents=getResources().getStringArray(R.array.china_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			
			break;
        case 1:   //德国
        	mImageView_Icons=new Integer[]{R.drawable.germany_1,R.drawable.germary_2,
        			R.drawable.germary_3,R.drawable.germary_4,R.drawable.germary_5,
        			R.drawable.germary_6,R.drawable.germary_7,R.drawable.germary_8,
        			R.drawable.germary_9,R.drawable.germary_10
        			};
        	mCar_Names=getResources().getStringArray(R.array.germary_car_names);
			mCar_Contents=getResources().getStringArray(R.array.germary_car_content);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			break;
        case 2:   //美国
        	mImageView_Icons=new Integer[]{R.drawable.usa_1,R.drawable.usa_2,
        			R.drawable.usa_3,R.drawable.usa_4,R.drawable.usa_5,
        			R.drawable.usa_6,R.drawable.usa_7,R.drawable.usa_8,
        			R.drawable.usa_9,R.drawable.usa_10,R.drawable.usa_11};
        	mCar_Names=getResources().getStringArray(R.array.usa_car_names);
			mCar_Contents=getResources().getStringArray(R.array.usa_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			break;
        case 3:   //英国 
        	mImageView_Icons=new Integer[]{R.drawable.uk_1,R.drawable.uk_2,
        			R.drawable.uk_3,R.drawable.uk_4,R.drawable.uk_5,R.drawable.uk_6};
        	mCar_Names=getResources().getStringArray(R.array.uk_car_names);
			mCar_Contents=getResources().getStringArray(R.array.uk_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			break;
        case 4:   //法国
        	mImageView_Icons=new Integer[]{R.drawable.france_1,R.drawable.france_2,R.drawable.france_3,R.drawable.france_4};
        	mCar_Names=getResources().getStringArray(R.array.france_car_names);
			mCar_Contents=getResources().getStringArray(R.array.france_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
        	break;
        case 5:   //日本
        	mImageView_Icons=new Integer[]{R.drawable.japan_1,R.drawable.japan_2,
        			R.drawable.japan_3,R.drawable.japan_4,R.drawable.japan_5,
        			R.drawable.japan_6,R.drawable.japan_7,R.drawable.japan_8,
        			R.drawable.japan_9,R.drawable.japan_10,R.drawable.japan_11};
        	mCar_Names=getResources().getStringArray(R.array.japan_car_names);
			mCar_Contents=getResources().getStringArray(R.array.japan_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			break;
        case 6:   //韩国 
        	mImageView_Icons=new Integer[]{R.drawable.korea_1,R.drawable.korea_2,R.drawable.korea_3};
        	mCar_Names=getResources().getStringArray(R.array.korea_car_names);
			mCar_Contents=getResources().getStringArray(R.array.korea_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			break;
        case 7:   //意大利
        	mImageView_Icons=new Integer[]{R.drawable.italy_1,R.drawable.italy_2,
        			R.drawable.italy_3,R.drawable.italy_4,R.drawable.italy_5,
        			R.drawable.italy_6};
        	mCar_Names=getResources().getStringArray(R.array.italy_car_names);
			mCar_Contents=getResources().getStringArray(R.array.italy_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
			break;
		
     	case 8:  //其他
     		mImageView_Icons=new Integer[]{R.drawable.other_1,R.drawable.other_2,
     				R.drawable.other_3,R.drawable.other_4,R.drawable.other_5,
     				R.drawable.other_6};
     		mCar_Names=getResources().getStringArray(R.array.other_car_names);
			mCar_Contents=getResources().getStringArray(R.array.other_car_contents);
			mListSignAdapter=new ListSignAdapter(this, mImageView_Icons, mCar_Names);
     		break;
		}
		
		
	}
	
	/**
	 * 绑定数据
	 */
	private void bindData()
	{
		tv_title_head.setText(mCar_Country[list_item]);
		lv_list_sign.setAdapter(mListSignAdapter);
	}
	
	/**
	 * 初始化监听器
	 */
	private void initListener()
	{
		btn_head_back.setOnClickListener(new MySetOnClickListener());
		lv_list_sign.setOnItemClickListener(new MySetOnItemClickListener());
		
	    // 进行滚动的时候进行实时记载显示图片,防止出现卡顿现象
		lv_list_sign.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE)
				{
					//停止滚动
					
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
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
			Intent _Intent=new Intent(ListSignActivity.this, DetailsSignActivity.class);
			_Intent.putExtra("img_id", mImageView_Icons[position]);
			_Intent.putExtra("car_name", mCar_Names[position]);
			_Intent.putExtra("car_content", mCar_Contents[position]);
			ListSignActivity.this.startActivity(_Intent);
			ListSignActivity.this.finish();
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

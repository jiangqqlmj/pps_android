package com.pps.sharpturn.activity;

import java.util.List;

import com.pps.sharpturn.R;
import com.pps.sharpturn.R.id;
import com.pps.sharpturn.R.layout;
import com.pps.sharpturn.adapter.SharpBookAdapter;
import com.pps.sharpturn.db.DBManager;
import com.pps.sharpturn.model.SharpModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 脑筋转转转  收藏盒子
 * @author jiangqingqing
 * @time 2013/09/30
 */
public class BookActivity extends Activity {
	private Button book_head_leftbtn;
	private ListView book_listview;
	private Intent mIntent;
	private List<SharpModel> sharpModels;
	private DBManager mDBManager;
	private SharpBookAdapter mBookAdapter;
	
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.book);
	initView();
	initValidata();
	bindData();
	initListener();
  }
   /**
    * 初始化界面元素
    */
   private void initView(){
	   book_head_leftbtn=(Button)this.findViewById(R.id.book_head_leftbtn);
	   book_listview=(ListView)this.findViewById(R.id.book_listview);
   }
   
   /**
    * 初始化变量
    */
   private void initValidata(){
	   mIntent=new Intent();
	   mDBManager=new DBManager(BookActivity.this);
	   sharpModels=mDBManager.querySharps();
	   mBookAdapter=new SharpBookAdapter(BookActivity.this, sharpModels);
   }
   
   /**
    * 绑定数据
    */
   private void bindData(){
	   book_listview.setAdapter(mBookAdapter);
   }
   /**
    * 初始化监听器
    */
   private void initListener()
   {
	   book_head_leftbtn.setOnClickListener(new MySetOnClickListener());
	   book_listview.setOnItemClickListener(new MySetOnItemClickListener());
   }
   
   // 按钮监听器
   class MySetOnClickListener implements OnClickListener{
	@Override
	public void onClick(View v) {
	   switch (v.getId()) {
	case R.id.book_head_leftbtn:
	mIntent.setClass(BookActivity.this, MainActivity.class);
 	   BookActivity.this.startActivity(mIntent);
 	   BookActivity.this.finish();
		break;
	}	
	}   
   }
   
   // listview列表的item响应点击事件
   class MySetOnItemClickListener implements OnItemClickListener{
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	  SharpModel model=sharpModels.get(arg2);
	  Intent _Intent=new Intent();
	  _Intent.setClass(BookActivity.this, DetailsBookActivity.class);
	  _Intent.putExtra("model", model);
	  BookActivity.this.startActivity(_Intent);
	  BookActivity.this.finish();
	}
   }
   @Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode==KeyEvent.KEYCODE_BACK){
    	   mIntent.setClass(BookActivity.this, MainActivity.class);
    	   BookActivity.this.startActivity(mIntent);
    	   BookActivity.this.finish();
       }
	   return super.onKeyDown(keyCode, event);
}
}

package com.pps.sharpturn.activity;

import com.pps.sharpturn.R;
import com.pps.sharpturn.R.id;
import com.pps.sharpturn.R.layout;
import com.pps.sharpturn.db.DBManager;
import com.pps.sharpturn.model.SharpModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsBookActivity extends Activity {
	private Button details_book_head_leftbtn;
	private TextView details_book_head_textview;
	private TextView details_book_tv_name;
	private TextView details_book_tv_answer;
	private Button detalis_book_btn_book;
	private SharpModel model;
	private Intent mIntent;
	private String index="";
	private DBManager mDbManager;
	
 @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
    setContentView(R.layout.details_book);
    initView();
    initValidata();
    bindData();
    initListener();
   }
   
  private void initView(){	  
	  details_book_head_leftbtn=(Button)this.findViewById(R.id.details_book_head_leftbtn);
	  details_book_head_textview=(TextView)this.findViewById(R.id.details_book_head_textview);
	  details_book_tv_name=(TextView)this.findViewById(R.id.details_book_tv_name);
	  details_book_tv_answer=(TextView)this.findViewById(R.id.details_book_tv_answer);
	  detalis_book_btn_book=(Button)this.findViewById(R.id.detalis_book_btn_book);
	  
  }
  
  private void initValidata(){
	  model=(SharpModel) getIntent().getSerializableExtra("model");
	  
	  mIntent=new Intent();
	  mDbManager=new DBManager(DetailsBookActivity.this);
	  index=model.getName().substring(0, 3);
	  
  }
  
  private void bindData(){
	  details_book_head_textview.setText("第"+index+"道题目");
	  details_book_tv_name.setText(model.getName());
	  details_book_tv_answer.setText(model.getAnswer());
  }
  
  private void initListener(){
	  details_book_head_leftbtn.setOnClickListener(new MySetOnClickListener());
	  detalis_book_btn_book.setOnClickListener(new MySetOnClickListener());
  }
  
  //  按钮点击监听器
  class MySetOnClickListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_book_head_leftbtn:
			 // back
			mIntent.setClass(DetailsBookActivity.this, BookActivity.class);
			DetailsBookActivity.this.startActivity(mIntent);
			DetailsBookActivity.this.finish();
			break;

		case R.id.detalis_book_btn_book:
			// delete the book
			boolean result=mDbManager.deleteSharpById(model.getId());
			if(result){
			     Toast.makeText(DetailsBookActivity.this, "删除该收藏成功!", Toast.LENGTH_SHORT).show();	
				 mIntent.setClass(DetailsBookActivity.this, BookActivity.class);
				 DetailsBookActivity.this.startActivity(mIntent);
				 DetailsBookActivity.this.finish();	
			}else {
				 Toast.makeText(DetailsBookActivity.this, "删除该收藏失败!", Toast.LENGTH_SHORT).show();	
			}
			break;
		}
	}
  }
  
  
  @Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
      if(keyCode==KeyEvent.KEYCODE_BACK){
    	  mIntent.setClass(DetailsBookActivity.this, BookActivity.class);
		  DetailsBookActivity.this.startActivity(mIntent);
		  DetailsBookActivity.this.finish();
      }
	  return super.onKeyDown(keyCode, event);
}
}

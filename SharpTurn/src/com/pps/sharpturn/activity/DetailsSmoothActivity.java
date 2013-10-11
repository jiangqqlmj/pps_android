package com.pps.sharpturn.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.sharpturn.R;
import com.pps.sharpturn.R.id;
import com.pps.sharpturn.R.layout;
import com.pps.sharpturn.db.DBManager;
import com.pps.sharpturn.model.SharpModel;
import com.pps.sharpturn.utils.FileUtils;

/**
 * 脑筋急转弯展示
 * @author jiangqingqing
 *
 */
public class DetailsSmoothActivity extends Activity {
	private int mPosition;
	private int mCurrent=0;
	private int index=15;
	private List<SharpModel> mSharpModels;
	private SharpModel model;
	private Button details_smooth_head_leftbtn; //顶部返回按钮
	private TextView details_smooth_head_textview;//顶部标题
	private ImageView details_smooth_prev; //上一题
	private ImageView details_smooth_next; //下一题
	private TextView details_smooth_tv_name;  //问题
	private TextView details_smooth_tv_answer; //答案
	private Button detalis_smooth_btn_book; //收藏
	private Button detalis_smooth_btn_answer; //显示答案
	private Intent mIntent;
	private DBManager mDbManager;
	private static final int SUCCESS=1;	
	private Timer mTimer;
	private TimerTask mTimerTask;
    private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
		  switch (msg.what) {
		case SUCCESS:
			if(index!=0){
				details_smooth_tv_answer.setText("思考中:"+index);
			}else {
				details_smooth_tv_answer.setText("点击答案按钮,显示答案!");
				if(mTimerTask!=null){
				// 取消定时器
				mTimerTask.cancel();
				}
			}
			break;
		default:
			break;
		}
			super.handleMessage(msg);
			
		
		}   	
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details_smooth);
		initView();
		initValidata();
		bindData();
		initListener();
        
	}

	// 初始化界面元素
	private void initView() {
		details_smooth_head_leftbtn=(Button)this.findViewById(R.id.details_smooth_head_leftbtn);
		details_smooth_head_textview=(TextView)this.findViewById(R.id.details_smooth_head_textview);
		details_smooth_prev=(ImageView)this.findViewById(R.id.details_smooth_prev);
		details_smooth_next=(ImageView)this.findViewById(R.id.details_smooth_next);
		details_smooth_tv_name=(TextView)this.findViewById(R.id.details_smooth_tv_name);
		details_smooth_tv_answer=(TextView)this.findViewById(R.id.details_smooth_tv_answer);
		detalis_smooth_btn_book=(Button)this.findViewById(R.id.detalis_smooth_btn_book);
		detalis_smooth_btn_answer=(Button)this.findViewById(R.id.detalis_smooth_btn_answer);
		
	}

	// 初始化变量
	private void initValidata() {
		mTimer=new Timer();
		mPosition=DetailsSmoothActivity.this.getIntent().getIntExtra("position", 0);
		//Log.d("jiangqq", ">>>>>>>传进来的position为:"+mPosition);
		// 初始化要显示的数据
		if(LogoActivity.mLists==null){
			mSharpModels=FileUtils.getSharpModels(DetailsSmoothActivity.this);
		}else {
			mSharpModels=LogoActivity.mLists;
		}
		
		mIntent=new Intent();		
		mDbManager=new DBManager(DetailsSmoothActivity.this);
		// 当前显示的题目的序号
		mCurrent=200*mPosition;
		//Log.d("jiangqq", "当前显示的题目的序号:"+(mCurrent+1));
		details_smooth_tv_answer.setText("思考中:"+index);
		// 启动定时器，进行思考倒计时
		mTimerTask=new TimerTask() {
			@Override
 			public void run() {
             //发送消息到UI线程，进行更新UI
		     sendMessageUpdate();
		     index--;
			}
		};
		mTimer.schedule(mTimerTask, 1000, 1000);
		
		model=mSharpModels.get(mCurrent);
		details_smooth_head_textview.setText("第"+String.valueOf(mCurrent+1)+"道题目");
        if(mCurrent%200==0)
        {
        	details_smooth_prev.setVisibility(View.INVISIBLE);
        }else {
        	details_smooth_prev.setVisibility(View.VISIBLE);
		}	
        if((mCurrent+1)%200==0){
        	details_smooth_next.setVisibility(View.INVISIBLE);
        }else {
        	details_smooth_next.setVisibility(View.VISIBLE);
		}
        details_smooth_tv_name.setText(model.getName());
	}
	// 绑定数据
	private void bindData() {
   
	}

	// 初始化监听器
	private void initListener() {
		
		details_smooth_head_leftbtn.setOnClickListener(new MySetOnClickListener());
		details_smooth_prev.setOnClickListener(new MySetOnClickListener());
		details_smooth_next.setOnClickListener(new MySetOnClickListener());
		detalis_smooth_btn_book.setOnClickListener(new MySetOnClickListener());
		detalis_smooth_btn_answer.setOnClickListener(new MySetOnClickListener());
		
	}
	
	private void sendMessageUpdate(){
		Message msg=mHandler.obtainMessage();
		msg.what=SUCCESS;
		mHandler.sendMessage(msg);
	}
	
	// 点击事件监听器
	class MySetOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.details_smooth_head_leftbtn:	
				mIntent.setClass(DetailsSmoothActivity.this, SmoothActivity.class);
				DetailsSmoothActivity.this.startActivity(mIntent);
				DetailsSmoothActivity.this.finish();
				break;
			case R.id.details_smooth_prev:
				mCurrent--;
				model=mSharpModels.get(mCurrent);
				details_smooth_head_textview.setText("第"+String.valueOf(mCurrent+1)+"道题目");
		        if(mCurrent%200==0)
		        {
		        	details_smooth_prev.setVisibility(View.INVISIBLE);
		        }else {
		        	details_smooth_prev.setVisibility(View.VISIBLE);
				}	
		        if((mCurrent+1)%200==0){
		        	details_smooth_next.setVisibility(View.INVISIBLE);
		        }else {
		        	details_smooth_next.setVisibility(View.VISIBLE);
				}
		        details_smooth_tv_name.setText(model.getName());
		        
		        if(mTimerTask!=null){
		        	mTimerTask.cancel();
		        }
		        
		        index=15;
		        details_smooth_tv_answer.setText("思考中:"+index);
		        
		       
				// 启动定时器，进行思考倒计时
				mTimerTask=new TimerTask() {
					
					@Override
		 			public void run() {
		             //发送消息到UI线程，进行更新UI
				     sendMessageUpdate();
				     index--;
					}
				};
		        
				mTimer.schedule(mTimerTask, 1000, 1000);		        
		        
		       // Log.d("jiangqq", ">>>上一题:当前显示的题目的序号:"+(mCurrent+1));
				break;
				
			case R.id.details_smooth_next:
				mCurrent++;
				model=mSharpModels.get(mCurrent);
				details_smooth_head_textview.setText("第"+String.valueOf(mCurrent+1)+"道题目");
		        if(mCurrent%200==0)
		        {
		        	details_smooth_prev.setVisibility(View.INVISIBLE);
		        }else {
		        	details_smooth_prev.setVisibility(View.VISIBLE);
				}	
		        if((mCurrent+1)%200==0){
		        	details_smooth_next.setVisibility(View.INVISIBLE);
		        }else {
		        	details_smooth_next.setVisibility(View.VISIBLE);
				}
		        details_smooth_tv_name.setText(model.getName());
		       
		        if(mTimerTask!=null){
		        	mTimerTask.cancel();
		        }
		        
		        index=15;
		        details_smooth_tv_answer.setText("思考中:"+index);
		       
				// 启动定时器，进行思考倒计时
				mTimerTask=new TimerTask() {
					@Override
		 			public void run() {
		             //发送消息到UI线程，进行更新UI
				     sendMessageUpdate();
				     index--;
					}
				};
		    	
				mTimer.schedule(mTimerTask, 1000, 1000);		 
		        
		       // Log.d("jiangqq", ">>>下一题:当前显示的题目的序号:"+(mCurrent+1));
				break;
				
			case R.id.detalis_smooth_btn_book:
				boolean result=mDbManager.insertSharp(model);
				if (result) {
					Toast.makeText(DetailsSmoothActivity.this, "收藏成功!", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(DetailsSmoothActivity.this, "收藏失败!", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.detalis_smooth_btn_answer:
				details_smooth_tv_answer.setText(model.getAnswer());
				// 取消定时器
				if(mTimerTask!=null){
					mTimerTask.cancel();
				}
				break;
			}			
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			mIntent.setClass(DetailsSmoothActivity.this, SmoothActivity.class);
			DetailsSmoothActivity.this.startActivity(mIntent);
			DetailsSmoothActivity.this.finish();
			
		}
		return super.onKeyDown(keyCode, event);
	}
}

package com.pps.sharpturn;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.pps.sharpturn.R;
import com.pps.sharpturn.db.DBManager;
import com.pps.sharpturn.model.SharpModel;
import com.pps.sharpturn.utils.FileUtils;

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

public class DetailsRandomActivity extends Activity {
	private Button details_random_head_leftbtn;
	private TextView details_random_head_textview;
	private ImageView details_random_prev;
	private ImageView details_random_next;
	private TextView details_random_tv_name;
	private TextView details_random_tv_answer;
	private Button detalis_random_btn_book;
	private Button detalis_random_btn_answer;
	private List<SharpModel> sharpModels;
	private SharpModel model;
	private int index = 0;
	private Intent mIntent;
	private DBManager mDBManager;
	private Timer mTimer;
	private TimerTask mTimerTask;
	private int mCurrent = 15;
	private static final int SUCCESS = 1;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				if (mCurrent != 0) {
					details_random_tv_answer.setText("思考中:" + mCurrent); 
				} else {
					details_random_tv_answer.setText("点击答案显示");
					if (mTimerTask != null) {
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
		setContentView(R.layout.details_random);
		initView();
		initValidata();
		bindData();
		initListener();

	}

	/**
	 * 初始化数据
	 */
	private void initView() {
		details_random_head_leftbtn = (Button) this
				.findViewById(R.id.details_random_head_leftbtn);
		details_random_head_textview = (TextView) this
				.findViewById(R.id.details_random_head_textview);
		details_random_prev = (ImageView) this
				.findViewById(R.id.details_random_prev);
		details_random_next = (ImageView) this
				.findViewById(R.id.details_random_next);
		details_random_tv_name = (TextView) this
				.findViewById(R.id.details_random_tv_name);
		details_random_tv_answer = (TextView) this
				.findViewById(R.id.details_random_tv_answer);
		detalis_random_btn_book = (Button) this
				.findViewById(R.id.detalis_random_btn_book);
		detalis_random_btn_answer = (Button) this
				.findViewById(R.id.detalis_random_btn_answer);
	}

	/**
	 * 初始化变量
	 */
	private void initValidata() {

		if (LogoActivity.mLists == null) {
			sharpModels = FileUtils.getSharpModels(DetailsRandomActivity.this);
		} else {
			sharpModels = LogoActivity.mLists;
		}
		index = (int) (Math.random() * 3000) + 1;
		model = sharpModels.get(index);
		mIntent = new Intent();
		mDBManager = new DBManager(DetailsRandomActivity.this);
		details_random_tv_answer.setText("思考中:" + mCurrent);
		mTimer = new Timer();
		mTimerTask = new TimerTask() {

			@Override
			public void run() {
				sendMessageUpdate();
				mCurrent--;
			}
		};
		mTimer.schedule(mTimerTask, 1000, 1000);
		

	}

	/**
	 * 绑定数据
	 */
	private void bindData() {
		details_random_head_textview.setText("第" + index + "道题目");
		details_random_tv_name.setText(model.getName());
		

	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		details_random_head_leftbtn
				.setOnClickListener(new MySetOnClickListener());
		details_random_prev.setOnClickListener(new MySetOnClickListener());
		details_random_next.setOnClickListener(new MySetOnClickListener());
		detalis_random_btn_book.setOnClickListener(new MySetOnClickListener());
		detalis_random_btn_answer
				.setOnClickListener(new MySetOnClickListener());
	}

	private void sendMessageUpdate() {
		Message msg = mHandler.obtainMessage();
		msg.what = SUCCESS;
		mHandler.sendMessage(msg);
	}

	class MySetOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.details_random_head_leftbtn:
				mIntent.setClass(DetailsRandomActivity.this, MainActivity.class);
				DetailsRandomActivity.this.startActivity(mIntent);
				DetailsRandomActivity.this.finish();
				break;

			case R.id.details_random_prev:
				index = (int) (Math.random() * 3000) + 1;
				model = sharpModels.get(index);

				if (mTimerTask != null) {
					mTimerTask.cancel();
				}
				mCurrent = 15;
				mTimerTask = new TimerTask() {

					@Override
					public void run() {
						sendMessageUpdate();
						mCurrent--;
					}
				};
				mTimer.schedule(mTimerTask, 1000, 1000);
				details_random_head_textview.setText("第" + index + "道题目");
				details_random_tv_name.setText(model.getName());
				details_random_tv_answer.setText("思考中:" + mCurrent);

				break;
			case R.id.details_random_next:
				index = (int) (Math.random() * 3000) + 1;
				model = sharpModels.get(index);

				if (mTimerTask != null) {
					mTimerTask.cancel();
				}
				mCurrent = 15;
				mTimerTask = new TimerTask() {

					@Override
					public void run() {
						sendMessageUpdate();
						mCurrent--;
					}
				};
				mTimer.schedule(mTimerTask, 1000, 1000);
				details_random_head_textview.setText("第" + index + "道题目");
				details_random_tv_name.setText(model.getName());
				details_random_tv_answer.setText("思考中:" + mCurrent);

				break;
			case R.id.detalis_random_btn_book:
				boolean result = mDBManager.insertSharp(model);
				if (result) {
					Toast.makeText(DetailsRandomActivity.this, "收藏成功!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(DetailsRandomActivity.this, "收藏失败!",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.detalis_random_btn_answer:
				details_random_tv_answer.setText(model.getAnswer());
				if (mTimerTask != null) {
					mTimerTask.cancel();
				}
				break;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mIntent.setClass(DetailsRandomActivity.this, MainActivity.class);
			DetailsRandomActivity.this.startActivity(mIntent);
			DetailsRandomActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

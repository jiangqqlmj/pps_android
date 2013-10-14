package com.pps.sharpturn.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.baidu.mobads.appoffers.OffersManager;
import com.pps.sharpturn.BaseActivity;
import com.pps.sharpturn.ExitAppUtil;
import com.pps.sharpturn.R;

/**
 * 脑经急转弯的主界面
 * 
 * @author jiangqingqing
 * @time 2013/09/30
 */
public class MainActivity extends BaseActivity {
	private Button main_btn_random;
	private Button main_btn_smooth;
	private Button main_btn_book;
	private Button main_btn_recommand;
	private Intent mIntent;
	private Dialog mDialog;
	private LayoutInflater mLayoutInflater;
	private Button dialog_btn_commit;
	private Button dialog_btn_cancel;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	private void initView() {
		main_btn_random = (Button) findViewById(R.id.main_btn_random);
		main_btn_smooth = (Button) findViewById(R.id.main_btn_smooth);
		main_btn_book = (Button) findViewById(R.id.main_btn_book);
		main_btn_recommand=(Button)findViewById(R.id.main_btn_recommand);
	}

	/**
	 * 初始化变量
	 */
	private void initValidata() {
		mIntent = new Intent();
		mLayoutInflater=LayoutInflater.from(this);
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		main_btn_random.setOnClickListener(new MySetOnClickListener());
		main_btn_smooth.setOnClickListener(new MySetOnClickListener());
		main_btn_book.setOnClickListener(new MySetOnClickListener());
		main_btn_recommand.setOnClickListener(new MySetOnClickListener());
	}

	class MySetOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_btn_random:
				mIntent.setClass(MainActivity.this, DetailsRandomActivity.class);
				MainActivity.this.startActivity(mIntent);
				MainActivity.this.finish();
				break;

			case R.id.main_btn_smooth:
				mIntent.setClass(MainActivity.this, SmoothActivity.class);
				MainActivity.this.startActivity(mIntent);
				MainActivity.this.finish();
				break;
			case R.id.main_btn_book:
				mIntent.setClass(MainActivity.this, BookActivity.class);
				MainActivity.this.startActivity(mIntent);
				MainActivity.this.finish();
				break;
			case R.id.main_btn_recommand:
				String jifen=OffersManager.getCurrencyName(MainActivity.this);
				Log.d("jiangqq","积分单位："+jifen);
				int points=OffersManager.getPoints(MainActivity.this);
				Log.d("jiangqq", "当前积分："+points);
				OffersManager.showOffers(MainActivity.this);
				break;
			case R.id.dialog_btn_commit:
				// close this activity
				if(mDialog!=null&&mDialog.isShowing()){
					mDialog.dismiss();
				}
				ExitAppUtil.getInstance().exit();
				System.exit(0);
				break;
				
			case R.id.dialog_btn_cancel:
				if(mDialog!=null&&mDialog.isShowing()){
					mDialog.dismiss();
				}
				break;
			}
		}
	}
	
	private void showDialog(){
		mDialog=new Dialog(MainActivity.this, R.style.updateDialogTheme);
		View view = mLayoutInflater.inflate(R.layout.dialog_layout, null);
		dialog_btn_commit=(Button)view.findViewById(R.id.dialog_btn_commit);
		dialog_btn_cancel=(Button)view.findViewById(R.id.dialog_btn_cancel);
		dialog_btn_commit.setOnClickListener(new MySetOnClickListener());
		dialog_btn_cancel.setOnClickListener(new MySetOnClickListener());
		mDialog.setContentView(view);
		mDialog.setCancelable(false);
		mDialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        	// 弹出退出提示框        	
          showDialog();
        }
		return super.onKeyDown(keyCode, event);
	}
}

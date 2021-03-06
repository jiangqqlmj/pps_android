package com.pps.sharpturn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.pps.sharpturn.BaseActivity;
import com.pps.sharpturn.R;
import com.pps.sharpturn.adapter.SharpTurnAdapter;
/**
 * 脑筋转转转 按照标签进行选择问题列表
 * @author jiangqingqing
 * @time 2013/09/30 12:50
 */
public class SmoothActivity extends BaseActivity {
	private Button smooth_btn_return;
	private String[] mSharp;
	private ListView smooth_lv;
	private Intent mIntent;
	private SharpTurnAdapter mSharpTurnAdapter;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.smooth);
        initView();
        initValidata();
        bindData();
        initListener();
    }
    
    private void initView()
    {
    	smooth_btn_return=(Button)findViewById(R.id.smooth_btn_return);
    	smooth_lv=(ListView)findViewById(R.id.smooth_lv);
    }
    private void initValidata()
    {
    	mIntent=new Intent();
    	mSharp=getResources().getStringArray(R.array.sharp_list);
    	mSharpTurnAdapter=new SharpTurnAdapter(this, mSharp);
    }
    private void bindData()
    {
    	smooth_lv.setAdapter(mSharpTurnAdapter);
    }
    private void initListener()
    {
    	smooth_btn_return.setOnClickListener(new MySetOnClickListener());
    	smooth_lv.setOnItemClickListener(new MySetOnItemCliclListener());
    	
    }

    // 监听返回键
    class MySetOnClickListener implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			mIntent.setClass(SmoothActivity.this, MainActivity.class);
			SmoothActivity.this.startActivity(mIntent);
			SmoothActivity.this.finish();
		}
    	
    }
    
    class MySetOnItemCliclListener implements OnItemClickListener
    {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 跳转到题目页
			Log.d("jiangqq", ">>>>>>点击了第"+(position)+"个Item");
			Intent _Intent=new Intent(SmoothActivity.this, DetailsSmoothActivity.class);
			_Intent.putExtra("position", position);
			SmoothActivity.this.startActivity(_Intent);
			SmoothActivity.this.finish();
		}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		mIntent.setClass(SmoothActivity.this, MainActivity.class);
			SmoothActivity.this.startActivity(mIntent);
			SmoothActivity.this.finish();
    	}
    	return super.onKeyDown(keyCode, event);
    }
}

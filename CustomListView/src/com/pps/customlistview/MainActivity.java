package com.pps.customlistview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    
	private CustomListView lv_custom;
	private CustomAdapter mCustomAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv_custom=(CustomListView)findViewById(R.id.lv_custom);
		String[] mStrs=new String[]{"张三","李四","王五","赵六"};
		mCustomAdapter=new CustomAdapter(this, mStrs);
		lv_custom.setAdapter(mCustomAdapter);
		
		
	}



}

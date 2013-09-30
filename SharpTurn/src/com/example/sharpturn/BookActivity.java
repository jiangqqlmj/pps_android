package com.example.sharpturn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * 脑筋转转转  收藏盒子
 * @author jiangqingqing
 * @time 2013/09/30
 */
public class BookActivity extends Activity {
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.book);
  }
}

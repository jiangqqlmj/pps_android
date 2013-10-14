package com.pps.carsign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.pps.carsign.BaseActivity;

public class LogoActivity extends BaseActivity {
    private RelativeLayout relative_log;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.logo);
		relative_log=(RelativeLayout)findViewById(R.id.relative_log);
		Animation mAnimation=new AlphaAnimation(0.7f, 1.0f);
		mAnimation.setDuration(3500);
		relative_log.setAnimation(mAnimation);
		mAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent mIntent=new Intent(LogoActivity.this, MainActivity.class);
				LogoActivity.this.startActivity(mIntent);
				LogoActivity.this.finish();
			}
		});
		
		
	}
}

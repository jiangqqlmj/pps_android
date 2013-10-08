package com.android.xiaomolong.example.shake;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 
 * @author Ð¡Œ­Áú
 * 
 */
public class ShakeDemoActivity extends Activity {
	/** Called when the activity is first created. */
	RelativeLayout mLayout;
	Button shake_x;
	Button shake;
	Button shake_y;
	Context mContext;
	EditText passWd;
	private ImageView image;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		findView();

		shake.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation shake = AnimationUtils.loadAnimation(
						ShakeDemoActivity.this, R.anim.anim);
				shake.reset();
				shake.setFillAfter(true);
				image.startAnimation(shake);
			}
		});
		shake_x.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation shakeAnim = AnimationUtils.loadAnimation(mContext,
						R.anim.shake_x);
				passWd.startAnimation(shakeAnim);
			}
		});

		shake_y.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation shakeAnim = AnimationUtils.loadAnimation(mContext,
						R.anim.shake_y);
				passWd.startAnimation(shakeAnim);
			}
		});
	}

	public void findView() {
		mLayout = (RelativeLayout) findViewById(R.id.bg);
		passWd = (EditText) findViewById(R.id.passWd);
		shake_x = (Button) findViewById(R.id.shake_x);
		shake = (Button) findViewById(R.id.shake);
		shake_y = (Button) findViewById(R.id.shake_y);
		image = (ImageView) findViewById(R.id.image);
	}
}
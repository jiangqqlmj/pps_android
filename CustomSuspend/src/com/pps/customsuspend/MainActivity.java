package com.pps.customsuspend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Ðü¸¡¿òµÄdmeo
 * @author jiangqingqing
 * @time 2013¡¢10¡¢22
 */
public class MainActivity extends Activity {

	private Button btn1;
	private Button btn2;
	private FootView mFootView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn1=(Button)findViewById(R.id.btn1);
		btn2=(Button)findViewById(R.id.btn2);
		mFootView=new FootView(this);
		mFootView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Toast.makeText(MainActivity.this, "µã»÷ÁËÐü¸¡¿ò...", Toast.LENGTH_SHORT).show();
			}
		});
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFootView.show();	
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFootView.hide();
			}
		});
	}


}

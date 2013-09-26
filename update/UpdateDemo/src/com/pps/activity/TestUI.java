package com.pps.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.adapter.MyGridAdapter;
import com.pps.utils.ScreenShotUtils;
public class TestUI extends Activity  implements OnClickListener{
	private GridView gv;
	private String[] mStr;
	private MyGridAdapter mAdapter;
	private Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.demo_gridview);
        gv=(GridView)this.findViewById(R.id.gv); 
        btn=(Button)this.findViewById(R.id.btn);
        btn.setOnClickListener(this);
        mStr=getResources().getStringArray(R.array.gv_str);
        mAdapter=new MyGridAdapter(this, mStr);
        gv.setAdapter(mAdapter);
        gv.setSelection(0);
        gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv=(TextView)view.findViewById(R.id.tv_gv_item);
				Toast.makeText(TestUI.this,tv.getText() , Toast.LENGTH_SHORT).show();
			    mAdapter.setPosition(position);
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		 if(ScreenShotUtils.shotBitmap(TestUI.this))
		 {
			 Toast.makeText(TestUI.this,"½ØÍ¼³É¹¦!", Toast.LENGTH_SHORT).show();
		 }else {
			 Toast.makeText(TestUI.this,"½ØÍ¼Ê§°Ü!", Toast.LENGTH_SHORT).show();
		}
	}
}

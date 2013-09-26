package com.pps.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.activity.R.color;

public class MyGridAdapter extends BaseAdapter {

	private Context mContext;
	private String[] mStrs;
	private LayoutInflater mLayoutInflater;
	private int tmpPostion = -1;
	private int flag=-1;

	public MyGridAdapter(Context pContext, String[] pStrs) {
		this.mContext = pContext;
		this.mStrs = pStrs;
		mLayoutInflater = LayoutInflater.from(mContext);
		tmpPostion = 0;
	}

	@Override
	public int getCount() {
		return mStrs.length;
	}

	@Override
	public Object getItem(int position) {
		return mStrs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setPosition(int position) {
		
		this.tmpPostion = position;
		flag=position;
				
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  Holder _Holder = null;
		if (null == convertView) {
			_Holder = new Holder();
			convertView = mLayoutInflater.inflate(R.layout.demo_girdview_item,
					null);
			_Holder.tv_gv_item = (TextView) convertView
					.findViewById(R.id.tv_gv_item);
			_Holder.linear=(LinearLayout)convertView.findViewById(R.id.linear);
			 
			convertView.setTag(_Holder);
		} else {
			_Holder = (Holder) convertView.getTag();
		}
		_Holder.tv_gv_item.setText(mStrs[position]);
       
		if (tmpPostion == position) {
			_Holder.tv_gv_item.setBackgroundResource(R.drawable.sift_item_selector_select);
	 	}else {
	 		_Holder.tv_gv_item.setBackgroundResource(R.drawable.sift_item_selector);
		}
        if(flag==-1&&position==0)
        {
        	_Holder.tv_gv_item.requestFocus();
        	_Holder.tv_gv_item.setBackgroundResource(R.drawable.sift_item_selector);        	
        	
        }
		return convertView;
	}

	 final  class Holder {
		TextView tv_gv_item;
		LinearLayout linear;
	}
}

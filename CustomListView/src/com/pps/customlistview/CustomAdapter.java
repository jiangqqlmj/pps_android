package com.pps.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	private Context mContext;
	private String[] mStrs;
	private LayoutInflater mLayoutInflater;
	
	public CustomAdapter(Context pContext, String[] pStrs)
	{
		this.mContext=pContext;
		this.mStrs=pStrs;
		mLayoutInflater=LayoutInflater.from(mContext);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hondler _Hondler;
        if(null==convertView)
        {
        	_Hondler=new Hondler();
        	convertView=mLayoutInflater.inflate(R.layout.lv_item,null);
        	_Hondler.textView=(TextView)convertView.findViewById(R.id.textView);
        	convertView.setTag(_Hondler);
        }else {
			_Hondler=(Hondler)convertView.getTag();
		}
        _Hondler.textView.setText(mStrs[position]);
		return convertView;
	}
	
	
	final class  Hondler
	{
	     TextView textView;	
	}		
}

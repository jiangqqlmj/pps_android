package com.pps.sharpturn.adapter;

import com.pps.sharpturn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SharpTurnAdapter extends BaseAdapter {

	private Context mContext;
	private String[] mSharp;
	private LayoutInflater mLayoutInflater;
	public SharpTurnAdapter(Context pContext,String[] pSharp)
	{
		this.mContext=pContext;
		this.mSharp=pSharp;
		this.mLayoutInflater=LayoutInflater.from(pContext);
	}
	
	@Override
	public int getCount() {
		return mSharp!=null?mSharp.length:0;
	}

	@Override
	public Object getItem(int position) {
		return mSharp[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        Hondler _Hondler;
        if(convertView==null)
        {
        	_Hondler=new Hondler();
        	convertView=mLayoutInflater.inflate(R.layout.smooth_item, null);
        	_Hondler.smooth_list_name=(TextView)convertView.findViewById(R.id.smooth_list_name);
        	convertView.setTag(_Hondler);
        	
        }else {
		    _Hondler=(Hondler)convertView.getTag();	
		}
        _Hondler.smooth_list_name.setText(mSharp[position]);
		return convertView;
	}

	
	final class Hondler
	{
		TextView smooth_list_name;
	}
}

package com.pps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pps.activity.R;

/**
 * 
 * @author jiangqingqing
 *
 */
public class MyPopupWinAdapter extends BaseAdapter {

	private class Holder
	{
		TextView tv_popupwindow_item;
	}
	private Context mContext;
	private String[] mStrs;
	public MyPopupWinAdapter(Context pContext,String[] pStrs)
	{
		this.mContext=pContext;
		this.mStrs=pStrs;
	}
	@Override
	public int getCount() {
		
		return mStrs!=null?mStrs.length:0;
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
		Holder _Holder;
		if(convertView==null)
		{
			_Holder=new Holder();
			LayoutInflater inflater=LayoutInflater.from(mContext);
			convertView=inflater.inflate(R.layout.fragment_list_popupwindow_item, null);
			_Holder.tv_popupwindow_item=(TextView)convertView.findViewById(R.id.tv_popupwindow_item);
			convertView.setTag(_Holder);
		}else {
			_Holder=(Holder)convertView.getTag();
		}
		_Holder.tv_popupwindow_item.setText(mStrs[position]);
		return convertView;
	}
}
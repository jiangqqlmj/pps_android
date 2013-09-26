package com.pps.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.pps.activity.R;
import com.pps.model.Channel;

public class MyDetailGridAdapter extends BaseAdapter {

	private class Holder
	{
		Button gv_btn_tv;
	}
	private List<Channel> mLists;
	/**
	 * @return the mLists
	 */
	public List<Channel> getmLists() {
		return mLists;
	}
	/**
	 * @param mLists the mLists to set
	 */
	public void setmLists(List<Channel> mLists) {
		this.mLists = mLists;
	}
	private Context mContext;
	private boolean mIsFeature;
	
	public MyDetailGridAdapter(Context pContext,List<Channel> pLists,boolean pIsFeature)
	{
		this.mContext=pContext;
		this.mLists=pLists;
		this.mIsFeature=pIsFeature;
	}
	@Override
	public int getCount() {
		
		return mLists!=null?mLists.size():0;
	}
	@Override
	public Object getItem(int position) {	
		return mLists.get(position);
	}
	@Override
	public long getItemId(int position) {	
		return position;
	}	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		if(convertView==null)
		{
			LayoutInflater layoutInflater=LayoutInflater.from(mContext);
			convertView=layoutInflater.inflate(R.layout.fragment_detail_item, null);
			_Holder=new Holder();
			_Holder.gv_btn_tv=(Button)convertView.findViewById(R.id.gv_btn_tv);
			convertView.setTag(_Holder);
		}else {
			_Holder=(Holder)convertView.getTag();
		}
         
		_Holder.gv_btn_tv.setText(mLists.get(position).getStroy());
		
		
		if(mIsFeature)
		{
			//表示是花絮
			_Holder.gv_btn_tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
			_Holder.gv_btn_tv.setPadding(5, 0, 0, 0);
		}else {
			_Holder.gv_btn_tv.setGravity(Gravity.CENTER);
		}
		
		
		_Holder.gv_btn_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "准备播放第"+mLists.get(position).getStroy()+"集", Toast.LENGTH_SHORT).show();				
			}
		});
		return convertView;
	}
}

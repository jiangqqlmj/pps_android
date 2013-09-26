package com.pps.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.async.ImageLoader;
import com.pps.model.CacheChannelModel;

public class CacheChannelAdapter extends BaseAdapter {

	private class Holder
	{
		ImageView list_img;
		TextView list_name;
		TextView list_tp;
		TextView list_on;
		TextView list_vm;
	}
	
	private Context mContext;
	private List<CacheChannelModel> mChannelModels;
	private LayoutInflater mLayoutInflater;
	private ImageLoader imageLoader; 
	private Activity mActivity;
	private float vm_index=0;
	public CacheChannelAdapter(Context pContext,List<CacheChannelModel> pChannelModels,Activity pActivity )
	{
		this.mContext=pContext;
		this.mChannelModels=pChannelModels;
		this.mActivity=pActivity;
		imageLoader=new ImageLoader(mContext);
		mLayoutInflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		if(mChannelModels!=null)
		{
			return mChannelModels.size();
		}else {
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		
		return mChannelModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder=null;
		if(convertView==null)
		{
			_Holder=new Holder();
			convertView = mLayoutInflater.inflate(R.layout.fragment_list_item, null);
			_Holder.list_img=(ImageView)convertView.findViewById(R.id.list_img);
			_Holder.list_name=(TextView)convertView.findViewById(R.id.list_name);
			_Holder.list_tp=(TextView)convertView.findViewById(R.id.list_tp);
			_Holder.list_on=(TextView)convertView.findViewById(R.id.list_on);
			_Holder.list_vm=(TextView)convertView.findViewById(R.id.list_vm);
			convertView.setTag(_Holder);
		}else {
			_Holder=(Holder)convertView.getTag();
		}
		imageLoader.DisplayImage(mChannelModels.get(position).getImg_url(), mActivity, _Holder.list_img);
		_Holder.list_name.setText(mChannelModels.get(position).getList_name());
		_Holder.list_tp.setText(mChannelModels.get(position).getList_tp());
		_Holder.list_on.setText(mChannelModels.get(position).getList_on());
		vm_index=Float.valueOf(mChannelModels.get(position).getList_vm().trim());
		if(vm_index>=9.0)
		{
			_Holder.list_vm.setTextColor(mContext.getResources().getColor(R.color.orange_one));
		}else {
			_Holder.list_vm.setTextColor(mContext.getResources().getColor(R.color.orange_two));
		}
		_Holder.list_vm.setText(String.valueOf(vm_index));
		return convertView;
	}

}

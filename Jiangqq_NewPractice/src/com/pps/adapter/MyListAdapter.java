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
import com.pps.model.SubModel;

public class MyListAdapter extends BaseAdapter {
	private ImageLoader imageLoader; 
	private class Holder
	{
		ImageView list_img;
		TextView list_name;
		TextView list_tp;
		TextView list_on;
		TextView list_vm;
	}
	private Activity activity;
	private List<SubModel> mLists;
	public void setmLists(List<SubModel> mLists) {
		this.mLists = mLists;
	}
	private Context mContext;
	private float vm_index=0;
	public MyListAdapter(List<SubModel> pLists,Context pContext,Activity a )
	{
		this.mLists=pLists;
		this.mContext=pContext;
		this.activity=a;
		imageLoader=new ImageLoader(mContext);
	}
	
	@Override
	public int getCount() {
		
		return mLists!=null?mLists.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		
		return mLists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		if(convertView==null)
		{
			LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
			convertView = _LayoutInflater.inflate(R.layout.fragment_list_item, null);
			_Holder = new Holder();
			_Holder.list_img=(ImageView)convertView.findViewById(R.id.list_img);
			_Holder.list_name=(TextView)convertView.findViewById(R.id.list_name);
			_Holder.list_tp=(TextView)convertView.findViewById(R.id.list_tp);
			_Holder.list_on=(TextView)convertView.findViewById(R.id.list_on);
			_Holder.list_vm=(TextView)convertView.findViewById(R.id.list_vm);
			convertView.setTag(_Holder);
		}else {
			_Holder=(Holder)convertView.getTag();
		}

		
		//异步缓存进行加载显示图片
		imageLoader.DisplayImage(mLists.get(position).getImg(), activity, _Holder.list_img);
		//myImageLoader.displayImageView(mLists.get(position).getImg(), _Holder.list_img);
		
		_Holder.list_name.setText(mLists.get(position).getName());
		_Holder.list_tp.setText(mLists.get(position).getTp());
		_Holder.list_on.setText(String.valueOf(mLists.get(position).getOn()));
		vm_index=mLists.get(position).getVm();
		if(vm_index>=9.0)
		{
			_Holder.list_vm.setTextColor(mContext.getResources().getColor(R.color.orange_one));
			//_Holder.list_vm.setBackgroundColor(R.color.orange_one);
		}else {
			_Holder.list_vm.setTextColor(mContext.getResources().getColor(R.color.orange_two));
		}
		_Holder.list_vm.setText(String.valueOf(vm_index));
		return convertView;
	}

}

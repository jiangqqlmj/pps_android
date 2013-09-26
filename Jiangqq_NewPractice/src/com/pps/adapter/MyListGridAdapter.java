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

/**
 * 进行表格布局的自定义适配器
 * @author jiangqingqing
 *
 */
public class MyListGridAdapter extends BaseAdapter{
	private class Holder
	{
		ImageView  list_gird_image;
		TextView list_gird_name;
	}
	private List<SubModel> mLists;
	private Context mContext;
	private Activity activity;
	private ImageLoader imageLoader;
	public MyListGridAdapter(List<SubModel> pLists,Context pContext,Activity a)
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder _Holder;
		if(convertView==null)
		 {
			_Holder=new Holder();
			LayoutInflater inflater=LayoutInflater.from(mContext);
			convertView=inflater.inflate(R.layout.fragment_list_grid_item, null);
			_Holder.list_gird_image=(ImageView)convertView.findViewById(R.id.list_gird_image);
			_Holder.list_gird_name=(TextView)convertView.findViewById(R.id.list_gird_name);
			convertView.setTag(_Holder);
		  }	
		 else {
			_Holder=(Holder)convertView.getTag();
		}
		//这里的图片设置需要进行一步加载处理
		//使用异步缓存进行加载图片
		imageLoader.DisplayImage(mLists.get(position).getImg(), activity, _Holder.list_gird_image);
		_Holder.list_gird_name.setText(mLists.get(position).getName());
		return convertView;
	}

}

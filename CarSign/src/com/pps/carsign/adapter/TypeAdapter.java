
package com.pps.carsign.adapter;

import com.pps.carsign.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 主页汽车标志 分类Item的自定义适配器
 * @author jiangqingqing
 * @time 2013/10/11  14:32
 */
public class TypeAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] mImageViews;
    private LayoutInflater mLayoutInflater;
	public TypeAdapter(Context pContext,Integer[] pImageViews)
	{
		this.mContext=pContext;
		this.mImageViews=pImageViews;
		mLayoutInflater=LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return mImageViews!=null?mImageViews.length:0;
	}

	@Override
	public Object getItem(int position) {
		return mImageViews[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addItem()
	{
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hondler _Hondler;
		if(null==convertView){
			_Hondler=new Hondler();
			convertView=mLayoutInflater.inflate(R.layout.gridview_item, null);
			_Hondler.img_gv_item=(ImageView)convertView.findViewById(R.id.img_gv_item);
			convertView.setTag(_Hondler);
		}else {
			_Hondler=(Hondler)convertView.getTag();
		}
		
		_Hondler.img_gv_item.setImageResource(mImageViews[position]);
		return convertView;
	}

	static class Hondler{
		ImageView img_gv_item;
	}
	
}

package com.pps.carsign.adapter;

import com.pps.carsign.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 显示当前类型的汽车标志的类表的自定义适配器
 * @author jiangqingqing
 * @time 2013/10/11 17:11
 */
public class ListSignAdapter extends BaseAdapter {

	private Context mContext;
	private Integer[] mImageView_Icons;
	private String[] mCar_Names;
	private LayoutInflater mLayoutInflater;
	
	
	public ListSignAdapter(Context pContext,Integer[] pImageView_Icons,String[] pCar_Names)
	{
		this.mContext=pContext;
		this.mImageView_Icons=pImageView_Icons;
		this.mCar_Names=pCar_Names;
		mLayoutInflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return mImageView_Icons!=null?mImageView_Icons.length:0;
	}

	@Override
	public Object getItem(int position) {
		return mImageView_Icons[position];
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
			convertView=mLayoutInflater.inflate(R.layout.list_sign_item,null);
			_Hondler.img_list_sign=(ImageView)convertView.findViewById(R.id.img_list_sign);
			_Hondler.tv_list_title=(TextView)convertView.findViewById(R.id.tv_list_title);
			convertView.setTag(_Hondler);
		}else {
			_Hondler=(Hondler)convertView.getTag();
		}
		_Hondler.img_list_sign.setImageResource(mImageView_Icons[position]);
		_Hondler.tv_list_title.setText(mCar_Names[position]);
		return convertView;
	}

	
	private class Hondler
	{
      ImageView img_list_sign;
      TextView tv_list_title;
	}
}

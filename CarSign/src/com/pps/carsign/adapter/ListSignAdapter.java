package com.pps.carsign.adapter;

import java.util.List;

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
	private List<Integer> mLists;
	private String[] mCar_Names;
	private LayoutInflater mLayoutInflater;
	
	
	public ListSignAdapter(Context pContext,List<Integer> pLists,String[] pCar_Names)
	{
		this.mContext=pContext;
		this.mLists=pLists;
		this.mCar_Names=pCar_Names;
		mLayoutInflater=LayoutInflater.from(mContext);
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

	/**
	 * 添加数据到集合中，使用adapter进行刷新数据
	 * @param pRes
	 */
	public void addItem(Integer pRes)
	{
		mLists.add(pRes);
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
		_Hondler.img_list_sign.setImageResource(mLists.get(position));
		_Hondler.tv_list_title.setText(mCar_Names[position]);
		return convertView;
	}

	
	private class Hondler
	{
      ImageView img_list_sign;
      TextView tv_list_title;
	}
}

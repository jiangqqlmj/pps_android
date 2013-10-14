package com.pps.sharpturn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pps.sharpturn.R;
import com.pps.sharpturn.model.SharpModel;

public class SharpBookAdapter extends BaseAdapter {

	private Context mContext;
	private List<SharpModel> mLists;
	private LayoutInflater mLayoutInflater;
	public SharpBookAdapter(Context pContext,List<SharpModel> pLists){
		this.mContext=pContext;
		this.mLists=pLists;
		this.mLayoutInflater=LayoutInflater.from(mContext);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		Hondler _Hondler;
		if(convertView==null){
			_Hondler=new Hondler();
			convertView=mLayoutInflater.inflate(R.layout.book_item, null);
			_Hondler.book_item_name=(TextView)convertView.findViewById(R.id.book_item_name);
			_Hondler.book_item_answer=(TextView)convertView.findViewById(R.id.book_item_answer);
			convertView.setTag(_Hondler);
		}else {
			_Hondler=(Hondler)convertView.getTag();
		}
		_Hondler.book_item_name.setText(mLists.get(position).getName());
		_Hondler.book_item_answer.setText(mLists.get(position).getAnswer());
		return convertView;
	}
	final class Hondler{
		TextView book_item_name;
		TextView book_item_answer;
	}
}

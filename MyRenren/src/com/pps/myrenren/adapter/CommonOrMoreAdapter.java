package com.pps.myrenren.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pps.myrenren.activity.R;
import com.pps.myrenren.model.ItemComOrMoreModel;
/**
 * 常用与更多的Item列表的自定义适配器
 * @author jiangqingqing
 * @time 2013/07/24 17:41
 */
public class CommonOrMoreAdapter extends BaseAdapter {

	private Context mContext;
	private List<ItemComOrMoreModel> mLists;	
	private LayoutInflater mLayoutInflater;
	public CommonOrMoreAdapter(Context pContext,List<ItemComOrMoreModel> pLists)
	{	
		this.mContext=pContext;
		this.mLists=pLists;
		mLayoutInflater=LayoutInflater.from(mContext);
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
	public View getView(int arg0, View view, ViewGroup arg2) {
		Holder _Holder=null;;
		if(null==view)
		{
			_Holder=new Holder();
			view=mLayoutInflater.inflate(R.layout.listview_common_more_item, null);
			_Holder.common_or_more_image_icon=(ImageView)view.findViewById(R.id.common_or_more_image_icon);
			_Holder.commom_or_more_text_item=(TextView)view.findViewById(R.id.commom_or_more_text_item);
			_Holder.common_or_more_text_unchat_num=(TextView)view.findViewById(R.id.common_or_more_text_unchat_num);
			view.setTag(_Holder);
		}else {
			_Holder=(Holder)view.getTag();
		}
		_Holder.common_or_more_image_icon.setImageResource(mLists.get(arg0).getId());
		_Holder.commom_or_more_text_item.setText(mLists.get(arg0).getName());
		//判断未读的数量，如果为0的时候，那就不显示该view
		int number=mLists.get(arg0).getNum();
		if(number!=0)
		{
			_Holder.common_or_more_text_unchat_num.setVisibility(View.VISIBLE);
			_Holder.common_or_more_text_unchat_num.setText(String.valueOf(number));
		}else {
			_Holder.common_or_more_text_unchat_num.setVisibility(View.GONE);
		}
		return view;
	}

	private static class Holder
	{
		ImageView common_or_more_image_icon;
		TextView commom_or_more_text_item;
		TextView common_or_more_text_unchat_num;
	}
}

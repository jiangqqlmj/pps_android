package com.pps.fragment;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.activity.R;
import com.pps.adapter.MyDetailGridAdapter;
import com.pps.async.ImageLoader;
import com.pps.custom.MyCustomGridView;
import com.pps.model.Channel;
import com.pps.model.DetailModel;
import com.pps.utils.ArraysUtils;
import com.pps.utils.DownXML;
import com.pps.utils.ZipToFile;
import com.pps.xmlparser.DetailXmLSax;

/**
 * 视频详情Fragment显示
 * 
 * @author jiangqingqing
 * 
 */
public class MyDetailFragment extends Fragment {
	private ImageButton btn_ic_title_menu;
	private TextView detail_name;
	private ImageView detail_img;
	private TextView tv_detail_region;
	private TextView tv_detail_type;
	private Button btn_detail_vm;
	private TextView tv_detail_dirt;
	private TextView tv_detail_actor;
	private Button btn_detail_play;
	private Button btn_detail_down;
	private TextView tv_detail_story;

	private LinearLayout commom_linear; // 普通
	private LinearLayout high_linear; // 高清
	private LinearLayout advance_linear; // 花絮
	private LinearLayout smooth_linear; //流畅

	private ImageButton btn_details_left;
	private MyCustomGridView gv_detail;
	private Button btn_detail_favour;
	private Button btn_detail_share;
	private Button btn_detail_comment;
	private ImageButton btn_details_right;

	private Button btn_detail_arrow_commom;
	private boolean commom_down = true; // 普通视频默认是正序排列
	private Button btn_detail_arrow_high;
	private boolean high_down = true; // 高清视频默认是正序排列
	private Button btn_detail_arrow_advance;
	private boolean advance_down = true; // 花絮视频默认是正序排列
	private Button btn_detail_arrow_smooth; 
	private boolean smooth_down = true;  //流畅视频默认为正序排序
	
	private TextView common_tv_up;
	private TextView common_tv_down;
	private TextView high_tv_up;
	private TextView high_tv_down;
	private TextView advance_tv_up;
	private TextView advance_tv_down;
	private TextView smooth_tv_up;
	private TextView smooth_tv_down;

	private List<DetailModel> mLists;
	private List<Channel> channels;
	private List<Channel> convert_Channels;
	private List<Channel> commom_Channels=new ArrayList<Channel>(); // 普通视频
	private List<Channel> high_Channels=new ArrayList<Channel>(); // 高清视频
	private List<Channel> feature_Channels=new ArrayList<Channel>(); // 花絮视频
	private List<Channel> smooth_Channels=new ArrayList<Channel>(); //流畅视频
	private DetailModel detailModel;
	private MyDetailGridAdapter mAdapter;
	// private SubModel subModel;
	private int sub_id; // 用于去请求该节目的详细数据,组合请求的URL
	private ImageLoader imageLoader;

	private View mView;
	private Context mContext;
	private ProgressDialog mProgressDialog; // 进度框
	public static final int FAIL_DOWN = -1; // 下载列表错误
	public static final int FIIL_UNZIP = -2; // 解压错误

	public boolean isUseCache = false;// 标志是否使用缓存数据

	private static final String TAG = "jiangqq";

	private Handler mHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				break;
			case 1:
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}

				mLists = (List<DetailModel>) msg.obj;
				if (mLists != null) {
					detailModel = mLists.get(0);

				} else {
				}
				detail_name.setText(detailModel.getName());
				// myImageLoader.displayImageView(detailModel.getImg(),
				// detail_img);
				imageLoader.DisplayImage(detailModel.getImg(), getActivity(),
						detail_img);
				tv_detail_region.setText(detailModel.getRegion());
				tv_detail_type.setText(detailModel.getType());
				btn_detail_vm.setText(String.valueOf(detailModel.getVote()));
				tv_detail_dirt.setText(detailModel.getDirt());
				tv_detail_actor.setText(detailModel.getActor());
				tv_detail_story.setText(detailModel.getInton());

				channels = detailModel.getChannels();
				// 对视频按照普通，高清，花絮进行分类
				for (int i = 0; i < channels.size(); i++) {
					if ("预告花絮".equals(channels.get(i).getType())) {
						// 预告花絮
						feature_Channels.add(channels.get(i));

					} else if ("普通".equals(channels.get(i).getFotm())) {
						//|| "流畅".equals(channels.get(i).getFotm())
						// 普通视频
						commom_Channels.add(channels.get(i));

					} else if ("高清".equals(channels.get(i).getFotm())) {
						// 高清视频
						high_Channels.add(channels.get(i));
			           
					}else if ("流畅".equals(channels.get(i).getFotm())) {
						// 流畅视频 
						smooth_Channels.add(channels.get(i));
					}
				}
				Log.v(TAG,"花絮视频集数:" + feature_Channels.size());
				Log.v(TAG,"普通视频集数:" + commom_Channels.size());
				Log.v(TAG,"高清视频集数:" + high_Channels.size());
				Log.v(TAG,"流畅视频集数:" + smooth_Channels.size());

				// 默认刚开始进入的是普通视频列表
				// 如果普通视频和高清视频都为空，只有花絮的时候，那就直接显示花絮
				if (feature_Channels.size() != 0 && commom_Channels.size() == 0&&smooth_Channels.size()==0&&high_Channels.size()==0) {
					// 此刻直接先进入预告花絮栏
					mAdapter = new MyDetailGridAdapter(mContext,
							feature_Channels, true);
					gv_detail.setNumColumns(1);
					gv_detail.setAdapter(mAdapter);
					advance_tv_up.setTextColor(R.color.detail_tv_color);
					advance_tv_down.setTextColor(R.color.detail_tv_color);

				} else if(commom_Channels.size()!=0){
					mAdapter = new MyDetailGridAdapter(mContext,
							commom_Channels, false);
					gv_detail.setAdapter(mAdapter);
					common_tv_up.setTextColor(R.color.detail_tv_color);
					common_tv_down.setTextColor(R.color.detail_tv_color);

				}else if (high_Channels.size()!=0) {
					mAdapter = new MyDetailGridAdapter(mContext,
							high_Channels, false);
					gv_detail.setAdapter(mAdapter);
					high_tv_up.setTextColor(R.color.detail_tv_color);
					high_tv_down.setTextColor(R.color.detail_tv_color);
				} else {
					mAdapter = new MyDetailGridAdapter(mContext,
							smooth_Channels, false);
					gv_detail.setAdapter(mAdapter);
					smooth_tv_up.setTextColor(R.color.detail_tv_color);
					smooth_tv_down.setTextColor(R.color.detail_tv_color);
				}
				// 进行隐藏相应没有视频的部分
				if (commom_Channels.size() == 0) {
					commom_linear.setVisibility(View.GONE);
				}
				if (high_Channels.size() == 0) {
					high_linear.setVisibility(View.GONE);
				}
				if (feature_Channels.size() == 0) {
					advance_linear.setVisibility(View.GONE);
				}
				if (smooth_Channels.size()==0) {
					smooth_linear.setVisibility(View.GONE);
				}

				if (isUseCache) {
					// 使用缓存数据，那么进行刷新
					refreshData();
					Log.v(TAG, "刷新数据...");
				} else {

				}

				break;

			case FAIL_DOWN: // 下载列表失败
				if (null != mProgressDialog && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
				Toast.makeText(mContext, "下载剧情详情失败,请稍后再尝试...",
						Toast.LENGTH_SHORT).show();
				break;

			case FIIL_UNZIP: // 解压失败
				if (null != mProgressDialog && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}

				Toast.makeText(mContext, "解压失败,请稍后再尝试...", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {

			mView = inflater
					.inflate(R.layout.fragment_detail, container, false);
			initView();
			initVariable();

			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setMessage("正在加载请稍后...");
			mProgressDialog.show();

			Thread thread = new Thread(runnable_id);
			thread.start();
			bindData();
			initListener();
		}
		return mView;
	}

	private void initView() {
		btn_ic_title_menu = (ImageButton) mView
				.findViewById(R.id.btn_ic_title_menu);
		btn_ic_title_menu.setBackgroundResource(0);// 去除按钮背景
		detail_name = (TextView) mView.findViewById(R.id.detail_name);
		detail_img = (ImageView) mView.findViewById(R.id.detail_img);
		tv_detail_region = (TextView) mView.findViewById(R.id.tv_detail_region);
		tv_detail_type = (TextView) mView.findViewById(R.id.tv_detail_type);
		btn_detail_vm = (Button) mView.findViewById(R.id.btn_detail_vm);
		tv_detail_dirt = (TextView) mView.findViewById(R.id.tv_detail_dirt);
		tv_detail_actor = (TextView) mView.findViewById(R.id.tv_detail_actor);
		btn_detail_play = (Button) mView.findViewById(R.id.btn_detail_play);
		btn_detail_down = (Button) mView.findViewById(R.id.btn_detail_down);
		tv_detail_story = (TextView) mView.findViewById(R.id.tv_detail_story);
		btn_details_left = (ImageButton) mView
				.findViewById(R.id.btn_details_left);

		btn_details_right = (ImageButton) mView
				.findViewById(R.id.btn_details_right);

		gv_detail = (MyCustomGridView) mView.findViewById(R.id.gv_detail);
		btn_detail_favour = (Button) mView.findViewById(R.id.btn_detail_favour);
		btn_detail_share = (Button) mView.findViewById(R.id.btn_detail_share);
		btn_detail_comment = (Button) mView
				.findViewById(R.id.btn_detail_comment);

		commom_linear = (LinearLayout) mView.findViewById(R.id.commom_linear);
		high_linear = (LinearLayout) mView.findViewById(R.id.high_linear);
		advance_linear = (LinearLayout) mView.findViewById(R.id.advance_linear);
		smooth_linear=(LinearLayout)mView.findViewById(R.id.smooth_linear);

		btn_detail_arrow_commom = (Button) mView
				.findViewById(R.id.btn_detail_arrow_commom);
		btn_detail_arrow_high = (Button) mView
				.findViewById(R.id.btn_detail_arrow_high);
		btn_detail_arrow_advance = (Button) mView
				.findViewById(R.id.btn_detail_arrow_advance);
		btn_detail_arrow_smooth=(Button)mView.findViewById(R.id.btn_detail_arrow_smooth);

		common_tv_up = (TextView) mView.findViewById(R.id.common_tv_up);
		common_tv_down = (TextView) mView.findViewById(R.id.common_tv_down);
		high_tv_up = (TextView) mView.findViewById(R.id.high_tv_up);
		high_tv_down = (TextView) mView.findViewById(R.id.high_tv_down);
		advance_tv_up = (TextView) mView.findViewById(R.id.advance_tv_up);
		advance_tv_down = (TextView) mView.findViewById(R.id.advance_tv_down);
		smooth_tv_up=(TextView)mView.findViewById(R.id.smooth_tv_up);
		smooth_tv_down=(TextView)mView.findViewById(R.id.smooth_tv_down);

	}

	private void initVariable() {
		mContext = mView.getContext();
		commom_Channels = new ArrayList<Channel>();
		high_Channels = new ArrayList<Channel>();
		feature_Channels = new ArrayList<Channel>();
		imageLoader = new ImageLoader(mContext);
		btn_details_left.setBackgroundResource(0);
		btn_details_right.setBackgroundResource(0);
		Bundle bundle = getArguments();
		if (bundle != null) {
			sub_id = bundle.getInt("sub_id");
		}
	}

	private void bindData() {
	}

	private void initListener() {
		btn_ic_title_menu.setOnClickListener(new MyOnClickListener());
		btn_detail_vm.setOnClickListener(new MyOnClickListener());
		btn_detail_play.setOnClickListener(new MyOnClickListener());
		btn_detail_down.setOnClickListener(new MyOnClickListener());
		btn_details_left.setOnClickListener(new MyOnClickListener());
		// btn_detail_arrow_up_high.setOnClickListener(new MyOnClickListener());
		// btn_detail_arrow_down_high.setOnClickListener(new
		// MyOnClickListener());
		// btn_detail_arrow_up_advance.setOnClickListener(new
		// MyOnClickListener());
		// btn_detail_arrow_down_advance.setOnClickListener(new
		// MyOnClickListener());

		// high_linear_up.setOnClickListener(new MyOnClickListener());
		// advance_linear_up.setOnClickListener(new MyOnClickListener());
		// high_linear_down.setOnClickListener(new MyOnClickListener());
		// advance_linear_down.setOnClickListener(new MyOnClickListener());

		btn_details_right.setOnClickListener(new MyOnClickListener());
		btn_detail_favour.setOnClickListener(new MyOnClickListener());
		btn_detail_share.setOnClickListener(new MyOnClickListener());
		btn_detail_comment.setOnClickListener(new MyOnClickListener());
		gv_detail.setOnItemClickListener(new MyOnItemClickListener());

		btn_detail_arrow_commom.setOnClickListener(new MyOnClickListener());
		btn_detail_arrow_high.setOnClickListener(new MyOnClickListener());
		btn_detail_arrow_advance.setOnClickListener(new MyOnClickListener());
		btn_detail_arrow_smooth.setOnClickListener(new MyOnClickListener());
	}

	/*
	 * 表格布局的Item点击监听器
	 */
	class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int agr2,
				long arg3) {
			// 进行播放视频
			int index = agr2 + 1;
			Toast.makeText(mContext, "准备播放第" + index + "集", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/*
	 * 自定义按钮监听器
	 */
	class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			switch (v.getId()) {
			case R.id.btn_ic_title_menu:
				// 返回列表页
				MyListFragment listFragment = new MyListFragment();
				transaction.remove(fragmentManager
						.findFragmentById(R.id.container_details));
				transaction.replace(R.id.container, listFragment);
				transaction.commit();
				break;
			case R.id.btn_detail_vm:
				// 进行评分
				break;
			case R.id.btn_detail_play:
				break;
			case R.id.btn_detail_down:
				break;
			case R.id.btn_details_left:

				break;
			// case R.id.btn_detail_arrow_up_commom:
			// //节目按照上升进行布局
			// mAdapter = new MyDetailGridAdapter(mContext, commom_Channels,
			// false);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.btn_detail_arrow_down_commom:
			// //节目按照下降进行布局
			// convert_Channels=ArraysUtils.convertArrays(commom_Channels);
			// mAdapter = new MyDetailGridAdapter(mContext,
			// convert_Channels,false);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// // break;
			// case R.id.btn_detail_arrow_up_high:
			// mAdapter = new MyDetailGridAdapter(mContext,
			// high_Channels,false);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.btn_detail_arrow_down_high:
			// convert_Channels=ArraysUtils.convertArrays(high_Channels);
			// mAdapter = new MyDetailGridAdapter(mContext,
			// convert_Channels,false);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.btn_detail_arrow_up_advance:
			// //预告花絮视频上升排列
			// mAdapter = new MyDetailGridAdapter(mContext,
			// feature_Channels,true);
			// gv_detail.setNumColumns(1);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.btn_detail_arrow_down_advance:
			// //预告花絮视频下降排列
			// convert_Channels=ArraysUtils.convertArrays(feature_Channels);
			// mAdapter = new MyDetailGridAdapter(mContext,
			// convert_Channels,true);
			// gv_detail.setNumColumns(1);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.commom_linear_up:
			// mAdapter = new MyDetailGridAdapter(mContext,
			// commom_Channels,true);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.high_linear_up:
			// mAdapter = new MyDetailGridAdapter(mContext, high_Channels,true);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.advance_linear_up:
			// mAdapter = new MyDetailGridAdapter(mContext,
			// feature_Channels,false);
			// gv_detail.setNumColumns(1);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.commom_linear_down:
			// mAdapter = new MyDetailGridAdapter(mContext,
			// commom_Channels,false);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.high_linear_down:
			// mAdapter = new MyDetailGridAdapter(mContext,
			// high_Channels,false);
			// gv_detail.setNumColumns(5);
			// gv_detail.setAdapter(mAdapter);
			// break;
			// case R.id.advance_linear_down:
			// mAdapter = new MyDetailGridAdapter(mContext,
			// feature_Channels,false);
			// gv_detail.setNumColumns(1);
			// gv_detail.setAdapter(mAdapter);
			// break;
			case R.id.btn_details_right:
				break;
			case R.id.commom_linear:

				break;
			case R.id.btn_detail_favour:
				// 收藏
				break;
			case R.id.btn_detail_share:
				// 分享
				break;
			case R.id.btn_detail_comment:
				// 评论
				break;

			case R.id.btn_detail_arrow_commom: // 普通视频
				if (commom_down) {
					// 节目按照下降进行布局
					convert_Channels = ArraysUtils
							.convertArrays(commom_Channels);
					mAdapter = new MyDetailGridAdapter(mContext,
							convert_Channels, false);
					gv_detail.setNumColumns(5);
					gv_detail.setAdapter(mAdapter);
					commom_down = false;
					btn_detail_arrow_commom
							.setBackgroundResource(R.drawable.ic_details_arrow_up);
					common_tv_up.setTextColor(R.color.detail_tv_color);
					common_tv_down.setTextColor(R.color.detail_tv_color);
					high_tv_up.setTextColor(R.color.black);
					high_tv_down.setTextColor(R.color.black);
					advance_tv_up.setTextColor(R.color.black);
					advance_tv_down.setTextColor(R.color.black);
					smooth_tv_up.setTextColor(R.color.black);
					smooth_tv_down.setTextColor(R.color.black);

				} else {
					mAdapter = new MyDetailGridAdapter(mContext,
							commom_Channels, false);
					gv_detail.setNumColumns(5);
					gv_detail.setAdapter(mAdapter);
					commom_down = true;
					btn_detail_arrow_commom
							.setBackgroundResource(R.drawable.ic_details_arrow_down);
					common_tv_up.setTextColor(R.color.detail_tv_color);
					common_tv_down.setTextColor(R.color.detail_tv_color);
					high_tv_up.setTextColor(R.color.black);
					high_tv_down.setTextColor(R.color.black);
					advance_tv_up.setTextColor(R.color.black);
					advance_tv_down.setTextColor(R.color.black);
					smooth_tv_up.setTextColor(R.color.black);
					smooth_tv_down.setTextColor(R.color.black);
				}
				break;

			case R.id.btn_detail_arrow_high: // 高清视频
				if (high_down) {
					// 节目按照下降进行布局
					convert_Channels = ArraysUtils.convertArrays(high_Channels);
					mAdapter = new MyDetailGridAdapter(mContext,
							convert_Channels, false);
					gv_detail.setNumColumns(5);
					gv_detail.setAdapter(mAdapter);
					high_down = false;
					btn_detail_arrow_high
							.setBackgroundResource(R.drawable.ic_details_arrow_up);
					common_tv_up.setTextColor(R.color.black);
					common_tv_down.setTextColor(R.color.black);
					high_tv_up.setTextColor(R.color.detail_tv_color);
					high_tv_down.setTextColor(R.color.detail_tv_color);
					advance_tv_up.setTextColor(R.color.black);
					advance_tv_down.setTextColor(R.color.black);
					smooth_tv_up.setTextColor(R.color.black);
					smooth_tv_down.setTextColor(R.color.black);
				} else {
					mAdapter = new MyDetailGridAdapter(mContext, high_Channels,
							false);
					gv_detail.setNumColumns(5);
					gv_detail.setAdapter(mAdapter);
					high_down = true;
					btn_detail_arrow_high
							.setBackgroundResource(R.drawable.ic_details_arrow_down);
					common_tv_up.setTextColor(R.color.black);
					common_tv_down.setTextColor(R.color.black);
					high_tv_up.setTextColor(R.color.detail_tv_color);
					high_tv_down.setTextColor(R.color.detail_tv_color);
					advance_tv_up.setTextColor(R.color.black);
					advance_tv_down.setTextColor(R.color.black);
					smooth_tv_up.setTextColor(R.color.black);
					smooth_tv_down.setTextColor(R.color.black);
				}
				break;

			case R.id.btn_detail_arrow_advance: // 花絮视频
				if (advance_down) {
					// 节目按照下降进行布局
					convert_Channels = ArraysUtils
							.convertArrays(feature_Channels);
					mAdapter = new MyDetailGridAdapter(mContext,
							convert_Channels, true);
					gv_detail.setNumColumns(1);
					gv_detail.setAdapter(mAdapter);
					advance_down = false;
					btn_detail_arrow_advance
							.setBackgroundResource(R.drawable.ic_details_arrow_up);
					common_tv_up.setTextColor(R.color.black);
					common_tv_down.setTextColor(R.color.black);
					high_tv_up.setTextColor(R.color.black);
					high_tv_down.setTextColor(R.color.black);
					advance_tv_up.setTextColor(R.color.detail_tv_color);
					advance_tv_down.setTextColor(R.color.detail_tv_color);
					smooth_tv_up.setTextColor(R.color.black);
					smooth_tv_down.setTextColor(R.color.black);
				} else {
					mAdapter = new MyDetailGridAdapter(mContext,
							feature_Channels, true);
					gv_detail.setNumColumns(1);
					gv_detail.setAdapter(mAdapter);
					advance_down = true;
					btn_detail_arrow_advance
							.setBackgroundResource(R.drawable.ic_details_arrow_down);
					common_tv_up.setTextColor(R.color.black);
					common_tv_down.setTextColor(R.color.black);
					high_tv_up.setTextColor(R.color.black);
					high_tv_down.setTextColor(R.color.black);
					advance_tv_up.setTextColor(R.color.detail_tv_color);
					advance_tv_down.setTextColor(R.color.detail_tv_color);
					smooth_tv_up.setTextColor(R.color.black);
					smooth_tv_down.setTextColor(R.color.black);
				}
				break;
				case R.id.btn_detail_arrow_smooth: //流畅视频
					
					if(smooth_down)
					{
						//按照下降排序
						convert_Channels = ArraysUtils
								.convertArrays(smooth_Channels);
						mAdapter = new MyDetailGridAdapter(mContext,
								convert_Channels, false);
						gv_detail.setNumColumns(5);
						gv_detail.setAdapter(mAdapter);
						smooth_down = false;
						btn_detail_arrow_smooth
								.setBackgroundResource(R.drawable.ic_details_arrow_up);
						common_tv_up.setTextColor(R.color.black);
						common_tv_down.setTextColor(R.color.black);
						high_tv_up.setTextColor(R.color.black);
						high_tv_down.setTextColor(R.color.black);
						advance_tv_up.setTextColor(R.color.black);
						advance_tv_down.setTextColor(R.color.black);
						smooth_tv_up.setTextColor(R.color.detail_tv_color);
						smooth_tv_down.setTextColor(R.color.detail_tv_color);
						
					}else {
						
						mAdapter = new MyDetailGridAdapter(mContext,
								smooth_Channels, false);
						gv_detail.setNumColumns(5);
						gv_detail.setAdapter(mAdapter);
						smooth_down = true;
						btn_detail_arrow_smooth
								.setBackgroundResource(R.drawable.ic_details_arrow_down);
						common_tv_up.setTextColor(R.color.black);
						common_tv_down.setTextColor(R.color.black);
						high_tv_up.setTextColor(R.color.black);
						high_tv_down.setTextColor(R.color.black);
						advance_tv_up.setTextColor(R.color.black);
						advance_tv_down.setTextColor(R.color.black);
						smooth_tv_up.setTextColor(R.color.detail_tv_color);
						smooth_tv_down.setTextColor(R.color.detail_tv_color);
						
					}
					break;
			}
		}
	}

	/**
	 * 刷新数据
	 */
	private void refreshData() {
		Thread thread = new Thread(runnable_refresh);
		thread.start();
	}

	/**
	 * 根据传来的节目id去后台获取节目的详情
	 */
	Runnable runnable_id = new Runnable() {
		@Override
		public void run() {
			try {
				// 1:如果本地有缓存,直接解析缓存文件显示,然后再去网上上面进行获取数据，进行刷新页面
				File cacheFile = new File("/sdcard/pps_download/details/"
						+ sub_id + ".xml");
				if (cacheFile.exists()) {
					Log.v(TAG, "直接解析本地缓存文件...");
					isUseCache=true;
					// 直接解析
					InputStream inputStream = DownXML.getXMLFromFile(new File(
							"/sdcard/pps_download/details/" + sub_id + ".xml"));
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser parser = spf.newSAXParser();
					DetailXmLSax handler = new DetailXmLSax();
					parser.parse(inputStream, handler);
					inputStream.close();
					mLists = handler.getDetailLists();
					mHandler.obtainMessage(1, mLists).sendToTarget();
				} else {
					Log.v(TAG, "从网络上面下载文件...");
					isUseCache=false;
					// 2，去网络上面请求数据(根据传来的节目的id)
					
					String url_str = "http://list1.ppstream.com/mobile/newipadc/detail/"
							+ sub_id + ".xml.zip";
					Log.i(TAG, "请求的URL地址为：" + url_str);
					boolean result = false;
					result = DownXML.getFileWithDetails(url_str);
					if (!result) {
						Message msg = mHandler.obtainMessage();
						msg.what = FAIL_DOWN;
						mHandler.sendMessage(msg);
						return;
					}
					String[] strs = url_str.split("/");
					
					// boolean flag = ZipToFile.unzip("/sdcard/pps_download",
					// strs[strs.length-1],
					// "/sdcard/pps_download/"+sub_id+".xml");
					File pathFile = new File("/sdcard/pps_download/details");
					if (!pathFile.exists()) {
						pathFile.mkdir();
					}
					boolean flag = ZipToFile.unzip("/sdcard/pps_download",
							strs[strs.length - 1],
							"/sdcard/pps_download/details");
					if (flag) {
						Log.i(TAG, ">>>>>>>解压成功<<<<<<<<");
						// 解压成功，并且删除该压缩包,
						File file = new File("/sdcard/pps_download/" + sub_id
								+ ".xml.zip");
						if (file.exists()) {
							file.delete();
						}
					} else {
						Message msg = mHandler.obtainMessage();
						msg.what = FIIL_UNZIP;
						mHandler.sendMessage(msg);
						Log.i(TAG, ">>>>>>>解压失败<<<<<<<<");
						return;
					}

					InputStream inputStream = DownXML.getXMLFromFile(new File(
							"/sdcard/pps_download/details/" + sub_id + ".xml"));
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser parser = spf.newSAXParser();
					DetailXmLSax handler = new DetailXmLSax();
					parser.parse(inputStream, handler);
					inputStream.close();
					mLists = handler.getDetailLists();
					mHandler.obtainMessage(1, mLists).sendToTarget();
					// 删除解压后的XML文件夹
					// FileUtils.deleteAll(new
					// File("/sdcard/pps_download/"+sub_id+".xml"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 后台自动下载新数据进行刷新
	 */
	Runnable runnable_refresh = new Runnable() {

		@Override
		public void run() {
			try {
				isUseCache=false;
				String url_str = "http://list1.ppstream.com/mobile/newipadc/detail/"
						+ sub_id + ".xml.zip";
				boolean result = false;
				result = DownXML.getFileWithDetails(url_str);
				if (!result) {
					Message msg = mHandler.obtainMessage();
					msg.what = FAIL_DOWN;
					mHandler.sendMessage(msg);
					return;
				}
				String[] strs = url_str.split("/");
				Log.i(TAG, "请求的URL地址为：" + url_str);
				// boolean flag = ZipToFile.unzip("/sdcard/pps_download",
				// strs[strs.length-1], "/sdcard/pps_download/"+sub_id+".xml");
				File pathFile = new File("/sdcard/pps_download/details");
				if (!pathFile.exists()) {
					pathFile.mkdir();
				}
				boolean flag = ZipToFile.unzip("/sdcard/pps_download",
						strs[strs.length - 1], "/sdcard/pps_download/details");
				if (flag) {
					Log.i(TAG, ">>>>>>>解压成功<<<<<<<<");
					// 解压成功，并且删除该压缩包,
					File file = new File("/sdcard/pps_download/" + sub_id
							+ ".xml.zip");
					if (file.exists()) {
						file.delete();
					}
				} else {
					Message msg = mHandler.obtainMessage();
					msg.what = FIIL_UNZIP;
					mHandler.sendMessage(msg);
					Log.i(TAG, ">>>>>>>解压失败<<<<<<<<");
					return;
				}

//				InputStream inputStream = DownXML.getXMLFromFile(new File(
//						"/sdcard/pps_download/details/" + sub_id + ".xml"));
//				SAXParserFactory spf = SAXParserFactory.newInstance();
//				SAXParser parser = spf.newSAXParser();
//				DetailXmLSax handler = new DetailXmLSax();
//				parser.parse(inputStream, handler);
//				inputStream.close();
//				mLists = handler.getDetailLists();
//				mHandler.obtainMessage(1, mLists).sendToTarget();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}

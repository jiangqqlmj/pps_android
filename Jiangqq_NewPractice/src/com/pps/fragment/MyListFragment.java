package com.pps.fragment;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.activity.R;
import com.pps.adapter.MyListAdapter;
import com.pps.adapter.MyListGridAdapter;
import com.pps.adapter.MyPopupWinAdapter;
import com.pps.commom.MemoryStatus;
import com.pps.custom.anim.MyAnimation;
import com.pps.db.service.ChannelListServiceInterface;
import com.pps.db.service.imp.ChannelListServiceImp;
import com.pps.model.SubModel;
import com.pps.utils.ArraysUtils;
import com.pps.utils.DownXML;
import com.pps.utils.JudgeNetwork;
import com.pps.utils.ZipToFile;
import com.pps.xmlparser.ListXMLContentHandler;

/**
 * 影视列表的界面
 * 
 * @author jiangqingqing
 * @time 2013/09
 * 
 */
public class MyListFragment extends Fragment {
	private ImageButton btn_ic_title_menu;
	private Button btn_top_grid_list;
	private Button btn_bkg_poster_cover;
	private Button btn_list_hot;
	private Button btn_list_new;
	private Button btn_list_score;
	private Button btn_list_letter;
	private EditText et_search;
	private LinearLayout list_grid_linear;
	private ListView lv_list;
	private GridView gv_list;
	private static List<SubModel> mLists; // 静态节目默认列表数据

	private static List<SubModel> mListsByHot; // 最热的列表
	private static List<SubModel> mListsByNew; // 最新列表
	private static List<SubModel> mListsByVm;// 按照评分列表
	private static List<SubModel> mListsByLetter;// 按照字母排序列表
	private static boolean isHot = false;
	private static boolean isNew = false;
	private static boolean isVm = false;
	private static boolean isLetter = false;

	private boolean isShift = false; // 是否为筛选 默认是正常
	//private boolean isSearch = false; // 是否为搜索 默认是正常
	private List<SubModel> mSearchModels;

	private List<SubModel> mShiftLists; // 经过筛选过的列表对象

	private MyListAdapter listAdapter;
	private MyListGridAdapter gridAdapter;
	public static ProgressDialog mProgressDialog;
	private ProgressDialog mSortProgressDialog;
	
	private View mView;
	private Context mContext;
	private boolean isClick = false;// 控制表格与列表切换按钮
									// 默认isClick=false(列表)，isClick=true(表格)

	// PopupWindow弹出框进行显示所需要的各种参数
	private PopupWindow mPopupWindow;
	private MyPopupWinAdapter myPopupWinAdapter;
	private ListView lv_popwindow;
	private TextView tv_popupwindow_type;
	private TextView tv_popupwindow_year;
	private TextView tv_popupwindow_letter;
	private View mPopupWindowView;
	private LayoutInflater mLayoutInflater;
	private String[] mStrs;// 显示类型，年份，字母的数组

	private View loadView; // 点击加载更多
	private ProgressBar load_progress;
	private Button load_btn;

	private int maxSum = 0;

	private static final int LIST_FAIL = -1; // handler处理Message的标识

	private static final int SHIF_TYPE = 3;
	private static final int SHIF_YEAR = 4;
	private static final int SHIF_LETTER = 5;

	private static final int SEARCH_ALL = 6;
	
	private static final int SORT_HOT=7;
	private static final int SORT_NEW=8;
	private static final int SORT_VOTE=9;
	private static final int SORT_LETTER=10;

	private boolean isUseCache = false;
	private InputMethodManager imm;

	private static final String TAG = "jiangqq";
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LIST_FAIL:
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				Toast.makeText(mContext, "无网络连接,请设置网络或者稍后访问.",
						Toast.LENGTH_SHORT).show();
				break;
			case 1: // 获取到全部数据
				mLists = (List<SubModel>) msg.obj;
				// 关闭进度框
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				// 设置点击标签位置
				isHot = true;
				isNew = false;
				isVm = false;
				isLetter = false;

				if (isUseCache) {
					// 刷新数据
					refreshData();
					Log.v(TAG, "刷新列表数据文件...");
				} else {

				}

				// 默认是在最新标签
				listAdapter = new MyListAdapter(mListsByHot, mContext,
						getActivity());
				lv_list.setAdapter(listAdapter);
				break;
			case 2:
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				if (mListsByHot == null) {
					mListsByHot = sortByHot(mLists);
				}
				// 返回回来也是同样直接显示最热的
				// 设置点击标签位置
				isHot = true;
				isNew = false;
				isVm = false;
				isLetter = false;
				listAdapter = new MyListAdapter(mListsByHot, mContext,
						getActivity());
				lv_list.setAdapter(listAdapter);
				break;

			case SHIF_TYPE: // 筛选类型

				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				mShiftLists = (List<SubModel>) msg.obj;

				// 进行重新加载视图
				if (isClick) {
					// 表格
					if (isHot) {
						gridAdapter = new MyListGridAdapter(
								sortByHot(mShiftLists), mContext, getActivity());
					} else if (isNew) {
						gridAdapter = new MyListGridAdapter(
								sortByNew(mShiftLists), mContext, getActivity());
					} else if (isVm) {
						gridAdapter = new MyListGridAdapter(
								sortByVm(mShiftLists), mContext, getActivity());
					} else if (isLetter) {
						gridAdapter = new MyListGridAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
					}

					gv_list.setAdapter(gridAdapter);

				} else {
					if (isHot) {
						listAdapter = new MyListAdapter(sortByHot(mShiftLists),
								mContext, getActivity());
					} else if (isNew) {
						listAdapter = new MyListAdapter(sortByNew(mShiftLists),
								mContext, getActivity());
					} else if (isVm) {
						listAdapter = new MyListAdapter(sortByNew(mShiftLists),
								mContext, getActivity());
					} else if (isLetter) {
						listAdapter = new MyListAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
					}

					lv_list.setAdapter(listAdapter);
				}
				isShift = true;// 筛选成功,设置标志位

				break;
			case SHIF_YEAR: // 筛选年份
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				mShiftLists = (List<SubModel>) msg.obj;
				// 进行重新加载视图
				if (isClick) {
					// 表格
					if (isHot) {
						gridAdapter = new MyListGridAdapter(
								sortByHot(mShiftLists), mContext, getActivity());
					} else if (isNew) {
						gridAdapter = new MyListGridAdapter(
								sortByNew(mShiftLists), mContext, getActivity());
					} else if (isVm) {
						gridAdapter = new MyListGridAdapter(
								sortByVm(mShiftLists), mContext, getActivity());
					} else if (isLetter) {
						gridAdapter = new MyListGridAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
					}
					gv_list.setAdapter(gridAdapter);

				} else {
					if (isHot) {
						listAdapter = new MyListAdapter(sortByHot(mShiftLists),
								mContext, getActivity());
					} else if (isNew) {
						listAdapter = new MyListAdapter(sortByNew(mShiftLists),
								mContext, getActivity());
					} else if (isVm) {
						listAdapter = new MyListAdapter(sortByNew(mShiftLists),
								mContext, getActivity());
					} else if (isLetter) {
						listAdapter = new MyListAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
					}

					lv_list.setAdapter(listAdapter);
				}

				isShift = true;// 筛选成功,设置标志位
				break;
			case SHIF_LETTER: // 筛选字母
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				mShiftLists = (List<SubModel>) msg.obj;
				// 进行重新加载视图
				if (isClick) {
					// 表格
					if (isHot) {
						gridAdapter = new MyListGridAdapter(
								sortByHot(mShiftLists), mContext, getActivity());
					} else if (isNew) {
						gridAdapter = new MyListGridAdapter(
								sortByNew(mShiftLists), mContext, getActivity());
					} else if (isVm) {
						gridAdapter = new MyListGridAdapter(
								sortByVm(mShiftLists), mContext, getActivity());
					} else if (isLetter) {
						gridAdapter = new MyListGridAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
					}
					gv_list.setAdapter(gridAdapter);

				} else {
					if (isHot) {
						listAdapter = new MyListAdapter(sortByHot(mShiftLists),
								mContext, getActivity());
					} else if (isNew) {
						listAdapter = new MyListAdapter(sortByNew(mShiftLists),
								mContext, getActivity());
					} else if (isVm) {
						listAdapter = new MyListAdapter(sortByNew(mShiftLists),
								mContext, getActivity());
					} else if (isLetter) {
						listAdapter = new MyListAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
					}
					lv_list.setAdapter(listAdapter);
				}
				isShift = true;// 筛选成功,设置标志位
				break;

			case SEARCH_ALL: // 搜索数据
				mSearchModels = (List<SubModel>) msg.obj;
				listAdapter.setmLists(mSearchModels);
				lv_list.setAdapter(listAdapter);
				break;
			
			case SORT_HOT:
				if(mSortProgressDialog!=null&&mSortProgressDialog.isShowing())
				{
					mSortProgressDialog.dismiss();
				}
				Log.v(TAG, "最热排序成功.");
				mListsByHot=(List<SubModel>)msg.obj;
				if (isShift) {
					// 筛选过
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(
								sortByHot(mShiftLists), mContext,
								getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(
								sortByHot(mShiftLists), mContext,
								getActivity());
						lv_list.setAdapter(listAdapter);
					}
				} else {
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(mListsByHot,
								mContext, getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(mListsByHot,
								mContext, getActivity());
						lv_list.setAdapter(listAdapter);
					}
				}
				
				break;
				
			case SORT_NEW:
				if(mSortProgressDialog!=null&&mSortProgressDialog.isShowing())
				{
					mSortProgressDialog.dismiss();
				}
				Log.v(TAG, "最新排序成功.");
				mListsByNew=(List<SubModel>)msg.obj;
				if (isShift) {
					// 筛选过
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(
								sortByNew(mShiftLists), mContext,
								getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(
								sortByNew(mShiftLists), mContext,
								getActivity());
						lv_list.setAdapter(listAdapter);
					}

				} else {
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(mListsByNew,
								mContext, getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(mListsByNew,
								mContext, getActivity());
						lv_list.setAdapter(listAdapter);
					}
				}
				
				break;
			case SORT_VOTE:
				if(mSortProgressDialog!=null&&mSortProgressDialog.isShowing())
				{
					mSortProgressDialog.dismiss();
				}
				Log.v(TAG, "评分排序成功.");
				mListsByVm=(List<SubModel>)msg.obj;
				if (isShift) {
					// 筛选过
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(
								sortByVm(mShiftLists), mContext,
								getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(
								sortByVm(mShiftLists), mContext,
								getActivity());
						lv_list.setAdapter(listAdapter);
					}

				} else {
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(mListsByVm,
								mContext, getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(mListsByVm,
								mContext, getActivity());
						lv_list.setAdapter(listAdapter);
					}
				}
				break;
			case SORT_LETTER:
				if(mSortProgressDialog!=null&&mSortProgressDialog.isShowing())
				{
					mSortProgressDialog.dismiss();
				}
				Log.v(TAG, "字母排序成功.");
				mListsByLetter=(List<SubModel>)msg.obj;
				if (isShift) { // 筛选
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(
								sortByLetter(mShiftLists), mContext,
								getActivity());
						lv_list.setAdapter(listAdapter);
					}
				} else {
					if (isClick) {
						// 在表格视图下
						gridAdapter = new MyListGridAdapter(mListsByLetter,
								mContext, getActivity());
						gv_list.setAdapter(gridAdapter);
					} else {
						// 列表情况
						listAdapter = new MyListAdapter(mListsByLetter,
								mContext, getActivity());
						lv_list.setAdapter(listAdapter);
					}
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	/*
	 * (non-Javadoc) 进行加载视图等工作
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_list, container, false);

			initView();
			initVariable();

			// 直接弹出进度框，去后台下载数据
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setMessage("正在加载....");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			Thread thread = new Thread(runnable_id);
			thread.start();
			bindData();
			initListeners();
		}
		return mView;
	}

	// 初始化变量
	private void initVariable() {
		mContext = mView.getContext();

		// 进行筛选，弹出PopupWindow
		mLayoutInflater = LayoutInflater.from(mContext);
		mPopupWindowView = mLayoutInflater.inflate(
				R.layout.fragment_list_popupwindow, null);
		mPopupWindowView.setFocusable(true);
		mPopupWindowView.setFocusableInTouchMode(true);
		// 监听返回键,进行dismiss()掉popupwindow
		mPopupWindowView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_BACK) {
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
				}
				return false;
			}
		});
		// 获取该加载出来view中的控件;
		lv_popwindow = (ListView) mPopupWindowView
				.findViewById(R.id.lv_popwindow);
		tv_popupwindow_type = (TextView) mPopupWindowView
				.findViewById(R.id.tv_popupwindow_type);
		tv_popupwindow_year = (TextView) mPopupWindowView
				.findViewById(R.id.tv_popupwindow_year);
		tv_popupwindow_letter = (TextView) mPopupWindowView
				.findViewById(R.id.tv_popupwindow_letter);
		mStrs = getResources().getStringArray(R.array.array_type_item);

		loadView = mLayoutInflater.inflate(R.layout.loadmore, null);// 进行inflate出更多的视图

		load_progress = (ProgressBar) loadView.findViewById(R.id.load_progress);
		load_btn = (Button) loadView.findViewById(R.id.load_btn);
		load_btn.setOnClickListener(new MySetOnClickListener());

		// lv_list.addFooterView(loadView); //把加载更多的视图，加入到listViw的底部

		
        
	}

	private void initView() {
		btn_ic_title_menu = (ImageButton) mView
				.findViewById(R.id.btn_ic_title_menu);
		//默认让title获取到焦点
		btn_ic_title_menu.requestFocus();
		btn_ic_title_menu.setBackgroundResource(0);// 去掉背景
		btn_top_grid_list = (Button) mView.findViewById(R.id.btn_top_grid_list);
		// btn_top_grid_list.setBackgroundResource(0);//去掉背景
		btn_bkg_poster_cover = (Button) mView
				.findViewById(R.id.btn_bkg_poster_cover);
		btn_list_hot = (Button) mView.findViewById(R.id.btn_list_hot);
		btn_list_new = (Button) mView.findViewById(R.id.btn_list_new);
		btn_list_score = (Button) mView.findViewById(R.id.btn_list_score);
		btn_list_letter = (Button) mView.findViewById(R.id.btn_list_letter);
		lv_list = (ListView) mView.findViewById(R.id.lv_list);
		gv_list = (GridView) mView.findViewById(R.id.gv_list);
		list_grid_linear = (LinearLayout) mView
				.findViewById(R.id.list_grid_linear);
		et_search = (EditText) mView.findViewById(R.id.et_search);
		et_search.clearFocus();
		imm = (InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_search.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void bindData() {
	}

	/**
	 * 初始化，进行监听
	 */
	private void initListeners() {
		// btn_ic_title_menu.setOnClickListener(new MySetOnClickListener());
		btn_ic_title_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onKeyDown(KeyEvent.KEYCODE_BACK, null);
			}
		});

		btn_top_grid_list.setOnClickListener(new MySetOnClickListener());
		btn_bkg_poster_cover.setOnClickListener(new MySetOnClickListener());
		btn_list_hot.setOnClickListener(new MySetOnClickListener());
		btn_list_new.setOnClickListener(new MySetOnClickListener());
		btn_list_score.setOnClickListener(new MySetOnClickListener());
		btn_list_letter.setOnClickListener(new MySetOnClickListener());
		lv_list.setOnItemClickListener(new MyOnItemClickListener());
		gv_list.setOnItemClickListener(new MyOnItemClickListener());

		tv_popupwindow_type.setOnClickListener(new MySetOnClickListener());
		tv_popupwindow_year.setOnClickListener(new MySetOnClickListener());
		tv_popupwindow_letter.setOnClickListener(new MySetOnClickListener());
		et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
				} else {
					imm.hideSoftInputFromWindow(et_search.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}

			}
		});
		et_search.addTextChangedListener(new TextWatcher() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.text.TextWatcher#onTextChanged(java.lang.CharSequence,
			 * int, int, int)
			 */
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.v(TAG, "搜索的内容是:" + s);
				if (s.toString().equals("")) {
					mHandler.obtainMessage(SEARCH_ALL, mLists).sendToTarget();
				} else {
					new MySearcheListTask(mLists).execute(String.valueOf(s));
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence
			 * , int, int, int)
			 */
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.text.TextWatcher#afterTextChanged(android.text.Editable)
			 */
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}

	/*
	 * 自定义按钮点击事件
	 */
	class MySetOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.btn_ic_title_menu:
				
				break;
				
			case R.id.btn_top_grid_list:
				// 进行节目的表格与列表的切换
				if (!isClick) {
					// 切换成表格,并且设置标志为true
					lv_list.setVisibility(View.GONE);
					if (isShift) {
						// 筛选过
						if (isHot) {
							gridAdapter = new MyListGridAdapter(
									sortByHot(mShiftLists), mContext,
									getActivity());
						} else if (isNew) {
							gridAdapter = new MyListGridAdapter(
									sortByNew(mShiftLists), mContext,
									getActivity());
						} else if (isVm) {
							gridAdapter = new MyListGridAdapter(
									sortByVm(mShiftLists), mContext,
									getActivity());
						} else if (isLetter) {
							gridAdapter = new MyListGridAdapter(
									sortByLetter(mShiftLists), mContext,
									getActivity());
						}
					} else {
						// 没有经过筛选
						if (isHot) {
							if (mListsByHot == null) {
								mListsByHot = sortByHot(mLists);
							}
							gridAdapter = new MyListGridAdapter(mListsByHot,
									mContext, getActivity());
						} else if (isNew) {
							if (mListsByNew == null) {
								mListsByNew = sortByNew(mLists);
							}
							gridAdapter = new MyListGridAdapter(mListsByNew,
									mContext, getActivity());
						} else if (isVm) {
							if (mListsByVm == null) {
								mListsByVm = sortByVm(mLists);
							}
							gridAdapter = new MyListGridAdapter(mListsByVm,
									mContext, getActivity());
						} else if (isLetter) {
							if (mListsByLetter == null) {
								mListsByLetter = sortByLetter(mLists);
							}
							gridAdapter = new MyListGridAdapter(mListsByLetter,
									mContext, getActivity());
						}
					}

					gv_list.setAdapter(gridAdapter);
					btn_top_grid_list
							.setBackgroundResource(R.drawable.icon_top_list);
					gv_list.setVisibility(View.VISIBLE);
					list_grid_linear.setAnimation(new MyAnimation());
					isClick = true;
				} else {
					// 切换成列表,并且设置标志位false
					lv_list.setVisibility(View.VISIBLE);
					if (isShift) { // 筛选过
						if (isHot) {
							listAdapter = new MyListAdapter(
									sortByHot(mShiftLists), mContext,
									getActivity());
						} else if (isNew) {
							listAdapter = new MyListAdapter(
									sortByNew(mShiftLists), mContext,
									getActivity());
						} else if (isVm) {
							listAdapter = new MyListAdapter(
									sortByVm(mShiftLists), mContext,
									getActivity());
						} else if (isShift) {
							listAdapter = new MyListAdapter(
									sortByLetter(mShiftLists), mContext,
									getActivity());
						}
					} else {
						// 没有筛选
						if (isHot) {
							if (mListsByHot == null) {
								mListsByHot = sortByHot(mLists);
							}
							listAdapter = new MyListAdapter(mListsByHot,
									mContext, getActivity());
						} else if (isNew) {
							if (mListsByNew == null) {
								mListsByNew = sortByNew(mLists);
							}
							listAdapter = new MyListAdapter(mListsByNew,
									mContext, getActivity());
						} else if (isVm) {
							if (mListsByVm == null) {
								mListsByVm = sortByVm(mLists);
							}
							listAdapter = new MyListAdapter(mListsByVm,
									mContext, getActivity());
						} else if (isLetter) {
							if (mListsByLetter == null) {
								mListsByLetter = sortByLetter(mLists);
							}
							listAdapter = new MyListAdapter(mListsByLetter,
									mContext, getActivity());
						}

					}

					lv_list.setAdapter(listAdapter);
					btn_top_grid_list
							.setBackgroundResource(R.drawable.icon_top_grid);
					gv_list.setVisibility(View.GONE);
					list_grid_linear.setAnimation(new MyAnimation());
					isClick = false;
				}

				break;
				
			case R.id.btn_bkg_poster_cover:
				// listview进行绑定数据
				myPopupWinAdapter = new MyPopupWinAdapter(mContext, mStrs);
				lv_popwindow.setAdapter(myPopupWinAdapter);
				lv_popwindow
						.setOnItemClickListener(new MyPopWindowTypeItemClickListener(
								mStrs));
				mPopupWindow = new PopupWindow(mPopupWindowView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						true);
				mPopupWindow.setFocusable(true);
				mPopupWindow.setTouchable(true);
				mPopupWindow.setOutsideTouchable(true);
				// 点击让Popupwindowd点击外面可以消失掉
				mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));

				if (!mPopupWindow.isShowing()) {
					// 显示进行PopupWindow
					mPopupWindow.showAsDropDown(btn_bkg_poster_cover, 0, 0);
				} else {
					mPopupWindow.dismiss();
				}
				break;
				
			case R.id.btn_list_hot: // 最热
				if (null != mLists) {
					
					// 设置标志位
					isHot = true;
					isNew = false;
					isVm = false;
					isLetter = false;
					btn_list_hot.setBackgroundResource(R.drawable.tab_bar_sel);
					btn_list_new.setBackgroundResource(R.drawable.tab_bar);
					btn_list_score.setBackgroundResource(R.drawable.tab_bar);
					btn_list_letter.setBackgroundResource(R.drawable.tab_bar);
					
					if (mListsByHot == null) {
						mSortProgressDialog=new ProgressDialog(mContext);
						mSortProgressDialog.setMessage("正在加载...");
						mSortProgressDialog.show();
						new Thread(new Runnable() {
							@Override
							public void run() {
								List<SubModel> tempSubModels=sortByHot(mLists);
								mHandler.obtainMessage(SORT_HOT, tempSubModels).sendToTarget();
							}
						}).start();
						return ;
					}
					if (isShift) {
						// 筛选过
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(
									sortByHot(mShiftLists), mContext,
									getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(
									sortByHot(mShiftLists), mContext,
									getActivity());
							lv_list.setAdapter(listAdapter);
						}
					} else {
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(mListsByHot,
									mContext, getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(mListsByHot,
									mContext, getActivity());
							lv_list.setAdapter(listAdapter);
						}
					}
				}
				
				break;
				
			case R.id.btn_list_new: // 最新
				if (null != mLists) {
					// 设置标志位
					isHot = false;
					isNew = true;
					isVm = false;
					isLetter = false;
					btn_list_hot.setBackgroundResource(R.drawable.tab_bar);
					btn_list_new.setBackgroundResource(R.drawable.tab_bar_sel);
					btn_list_score.setBackgroundResource(R.drawable.tab_bar);
					btn_list_letter.setBackgroundResource(R.drawable.tab_bar);
					if (mListsByNew == null) {
						mSortProgressDialog=new ProgressDialog(mContext);
						mSortProgressDialog.setMessage("正在加载...");
						mSortProgressDialog.show();
						new Thread(new Runnable() {
							@Override
							public void run() {
								 List<SubModel> tempSubModels=sortByNew(mLists);
                                 mHandler.obtainMessage(SORT_NEW, tempSubModels).sendToTarget();    
							}
						}).start();
						return ;
					}
					if (isShift) {
						// 筛选过
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(
									sortByNew(mShiftLists), mContext,
									getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(
									sortByNew(mShiftLists), mContext,
									getActivity());
							lv_list.setAdapter(listAdapter);
						}

					} else {
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(mListsByNew,
									mContext, getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(mListsByNew,
									mContext, getActivity());
							lv_list.setAdapter(listAdapter);
						}
					}
				}
				
				break;
				
			case R.id.btn_list_score: // 评分
				if (null != mLists) {
					// 设置标志位
					isHot = false;
					isNew = false;
					isVm = true;
					isLetter = false;

					btn_list_hot.setBackgroundResource(R.drawable.tab_bar);
					btn_list_new.setBackgroundResource(R.drawable.tab_bar);
					btn_list_score.setBackgroundResource(R.drawable.tab_bar_sel);
					btn_list_letter.setBackgroundResource(R.drawable.tab_bar);
					
					if (mListsByVm == null) {
						mSortProgressDialog=new ProgressDialog(mContext);
						mSortProgressDialog.setMessage("正在加载...");
						mSortProgressDialog.show();
						new Thread(new Runnable() {
							@Override
							public void run() {
								List<SubModel> tempSubModels=sortByVm(mLists);
								mHandler.obtainMessage(SORT_VOTE, tempSubModels).sendToTarget();
							}
						}).start();
						return ;
					}
					if (isShift) {
						// 筛选过
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(
									sortByVm(mShiftLists), mContext,
									getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(
									sortByVm(mShiftLists), mContext,
									getActivity());
							lv_list.setAdapter(listAdapter);
						}

					} else {
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(mListsByVm,
									mContext, getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(mListsByVm,
									mContext, getActivity());
							lv_list.setAdapter(listAdapter);
						}
					}
				}

				break;
				
			case R.id.btn_list_letter: // 字母
				if (null != mLists) {
					
					// 设置标志位
					isHot = false;
					isNew = false;
					isVm = false;
					isLetter = true;
					btn_list_hot.setBackgroundResource(R.drawable.tab_bar);
					btn_list_new.setBackgroundResource(R.drawable.tab_bar);
					btn_list_score.setBackgroundResource(R.drawable.tab_bar);
					btn_list_letter.setBackgroundResource(R.drawable.tab_bar_sel);
					
					if (mListsByLetter == null) {
						mSortProgressDialog=new ProgressDialog(mContext);
						mSortProgressDialog.setMessage("正在加载...");
						mSortProgressDialog.show();
						new Thread(new Runnable() {
							@Override
							public void run() {
								List<SubModel> tempSubModels=sortByLetter(mLists);
								mHandler.obtainMessage(SORT_LETTER, tempSubModels).sendToTarget();
							}
						}).start();
						return;
					}
					if (isShift) { // 筛选
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(
									sortByLetter(mShiftLists), mContext,
									getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(
									sortByLetter(mShiftLists), mContext,
									getActivity());
							lv_list.setAdapter(listAdapter);
						}
					} else {
						if (isClick) {
							// 在表格视图下
							gridAdapter = new MyListGridAdapter(mListsByLetter,
									mContext, getActivity());
							gv_list.setAdapter(gridAdapter);
						} else {
							// 列表情况
							listAdapter = new MyListAdapter(mListsByLetter,
									mContext, getActivity());
							lv_list.setAdapter(listAdapter);
						}
					}
				}
			
				break;

			case R.id.tv_popupwindow_type:
				// 根据类型显示筛选视频
				mStrs = getResources().getStringArray(R.array.array_type_item);
				myPopupWinAdapter = new MyPopupWinAdapter(mContext, mStrs);
				lv_popwindow.setAdapter(myPopupWinAdapter);
				lv_popwindow
						.setOnItemClickListener(new MyPopWindowTypeItemClickListener(
								mStrs));
				break;
				
			case R.id.tv_popupwindow_year:
				// 根据年份显示筛选视频
				mStrs = getResources().getStringArray(R.array.array_year_item);
				myPopupWinAdapter = new MyPopupWinAdapter(mContext, mStrs);
				lv_popwindow.setAdapter(myPopupWinAdapter);
				lv_popwindow
						.setOnItemClickListener(new MyPopWindowYearItemClickListener(
								mStrs));
				break;
				
			case R.id.tv_popupwindow_letter:
				// 根据字母显示筛选视频
				mStrs = getResources()
						.getStringArray(R.array.array_letter_item);
				myPopupWinAdapter = new MyPopupWinAdapter(mContext, mStrs);
				lv_popwindow.setAdapter(myPopupWinAdapter);
				lv_popwindow
						.setOnItemClickListener(new MyPopWindowLetterItemClickListener(
								mStrs));
				break;

			case R.id.load_btn: // 进行加载更多
				load_btn.setVisibility(View.GONE);
				load_progress.setVisibility(View.VISIBLE);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						loadMore(); // 记载更多数据
						load_btn.setVisibility(View.VISIBLE);
						load_progress.setVisibility(View.GONE);
						listAdapter.notifyDataSetChanged(); // 通知ListView进行刷新数据
					}
				}, 1000);
				break;
			}
		}
	}

	/*
	 * 自定义列表点击事件
	 */
	class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id) {
			// 点击列表Item进入详情页面
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			MyDetailFragment detailFragment = new MyDetailFragment();
			Bundle bundle = new Bundle();
			int index = 0;

			if (isShift) { // 筛选
				if (isHot) {
					List<SubModel> tempModels = sortByHot(mShiftLists);
					index = tempModels.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + tempModels.get(position).getName()
							+ ",ID:" + tempModels.get(position).getId());
				} else if (isNew) {
					List<SubModel> tempModels = sortByNew(mShiftLists);
					index = tempModels.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + tempModels.get(position).getName()
							+ ",ID:" + tempModels.get(position).getId());
				} else if (isVm) {
					List<SubModel> tempModels = sortByVm(mShiftLists);
					index = tempModels.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + tempModels.get(position).getName()
							+ ",ID:" + tempModels.get(position).getId());
				} else if (isLetter) {
					List<SubModel> tempModels = sortByVm(mShiftLists);
					index = tempModels.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + tempModels.get(position).getName()
							+ ",ID:" + tempModels.get(position).getId());
				}

			} else {
				// 无筛选
				if (isHot) {
					index = mListsByHot.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + mListsByHot.get(position).getName()
							+ ",ID:" + mListsByHot.get(position).getId());
				} else if (isNew) {
					index = mListsByNew.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + mListsByNew.get(position).getName()
							+ ",ID:" + mListsByNew.get(position).getId());
				} else if (isVm) {
					index = mListsByVm.get(position).getId();
					Log.v(TAG, "点击了剧情为:" + mListsByVm.get(position).getName()
							+ ",ID:" + mListsByVm.get(position).getId());
				} else if (isLetter) {
					index = mListsByLetter.get(position).getId();
					Log.v(TAG, "点击了剧情为:"
							+ mListsByLetter.get(position).getName() + ",ID:"
							+ mListsByLetter.get(position).getId());
				}
			}

			bundle.putInt("sub_id", index);
			detailFragment.setArguments(bundle);
			transaction.replace(R.id.container_details, detailFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}

	/*
	 * 根据类型进行筛选列表
	 */
	class MyPopWindowTypeItemClickListener implements OnItemClickListener {

		private String[] mTypeCompare;

		public MyPopWindowTypeItemClickListener(String[] pTypeCompare) {
			this.mTypeCompare = pTypeCompare;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			new MyTypeListTask(mLists).execute(mTypeCompare[position]);
			Log.v(TAG, "点击了:" + mTypeCompare[position]);
		}

	}

	/*
	 * 根据年份进行筛选列表
	 */
	class MyPopWindowYearItemClickListener implements OnItemClickListener {

		private String[] mYearCompare;

		public MyPopWindowYearItemClickListener(String[] pYearCompare) {
			this.mYearCompare = pYearCompare;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			new MyYearListTask(mLists).execute(mYearCompare[position]); // 刷新列表
		}
	}

	/*
	 * 根据字母进行筛选列表
	 */
	class MyPopWindowLetterItemClickListener implements OnItemClickListener {

		private String[] mLetterCompare;

		public MyPopWindowLetterItemClickListener(String[] pLetterCompare) {
			this.mLetterCompare = pLetterCompare;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			new MyLetterListTask(mLists).execute(mLetterCompare[position]);

		}

	}

	/*
	 * 根据类型进行筛选
	 */
	class MyTypeListTask extends AsyncTask<String, Integer, List<SubModel>> {

		private List<SubModel> mSubModels;

		public MyTypeListTask(List<SubModel> pSubModels) {
			this.mSubModels = pSubModels;
		}

		@Override
		protected List<SubModel> doInBackground(String... params) {

			String compareStr = params[0];
			List<SubModel> tempSubModels = new ArrayList<SubModel>();
			for (SubModel subModel : mSubModels) {
				// String[] compare=subModel.getTp().trim().split(",");
				// int length=compare.length;
				if (subModel.getTp().trim().indexOf(compareStr.trim()) > 0) {
					tempSubModels.add(subModel);
				}
			}
			return tempSubModels;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<SubModel> result) {

			mHandler.obtainMessage(SHIF_TYPE, result).sendToTarget();
			super.onPostExecute(result);
		}

	}

	/*
	 * 异步任务进行获取相应的年份列表
	 */
	class MyYearListTask extends AsyncTask<String, Integer, List<SubModel>> {

		private List<SubModel> mSubModels;

		public MyYearListTask(List<SubModel> pSubModels) {
			this.mSubModels = pSubModels;

		}

		/*
		 * (non-Javadoc) 异步后台执行任务
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<SubModel> doInBackground(String... params) {

			String compareStr = params[0];
			List<SubModel> tempSubModels = new ArrayList<SubModel>();
			for (SubModel subModel : mSubModels) {
				if (null != subModel.getSearch()) {
					if (subModel.getSearch().getPt().equals(compareStr.trim())) {
						tempSubModels.add(subModel);
					}
				}
			}
			return tempSubModels;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<SubModel> result) {
			// 发送消息，使用handler进行统一处理
			mHandler.obtainMessage(SHIF_TYPE, result).sendToTarget();
			super.onPostExecute(result);
		}

	}

	/*
	 * 根据字母进行筛选
	 */
	class MyLetterListTask extends AsyncTask<String, Integer, List<SubModel>> {
		private List<SubModel> mSubModels;

		public MyLetterListTask(List<SubModel> pSubModels) {
			this.mSubModels = pSubModels;
		}

		@Override
		protected List<SubModel> doInBackground(String... params) {
			String compareStr = params[0];
			List<SubModel> tempSubModels = new ArrayList<SubModel>();
			for (SubModel subModel : mSubModels) {
				if (subModel.getLt().equals(compareStr.trim())) {
					tempSubModels.add(subModel);
				}
			}
			return tempSubModels;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<SubModel> result) {
			mHandler.obtainMessage(SHIF_LETTER, result).sendToTarget();
			super.onPostExecute(result);
		}

	}

	/*
	 * 异步进行搜索剧情
	 */
	class MySearcheListTask extends AsyncTask<String, Integer, List<SubModel>> {

		private List<SubModel> mSubModels;

		public MySearcheListTask(List<SubModel> pSubModels) {
			this.mSubModels = pSubModels;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<SubModel> doInBackground(String... params) {
			String mStr = params[0];
			return searchModels(mStr, mSubModels);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<SubModel> result) {
			mHandler.obtainMessage(SHIF_TYPE, result).sendToTarget();
			super.onPostExecute(result);
		}
	}

	/**
	 * 后台线程请求数据，并且进行数据的XML解析
	 */
	Runnable runnable_id = new Runnable() {
		@Override
		public void run() {
			try {
				if (JudgeNetwork.isNetworkConnected(mContext)) {
					// 如果内存中有数据，那就直接进行显示
					if (mLists != null && mLists.size() >= 0) {
						mHandler.obtainMessage(2, mLists).sendToTarget();
					} else {

						// 内存中没有数据，进行下载解析
						// a:首先进行判断是否sdcard中存在节目列表的xml文件
						// b:如果存在直接进行解析，然后把数据放入到内存当中<->如果不存在那么去网络中进行下载节目列表zip包，进行解压缩，然后在放入到内存当中
						if (MemoryStatus.externalMemoryAvailable()) {
							File file = new File("/sdcard/141.xml/141.xml");
							if (file.exists()) {
								// 文件存在进行XML解析
								Log.v(TAG, "使用本地缓存文件进行解析...");
								isUseCache = true;
								InputStream inputStream = DownXML
										.getXMLFromFile(file);
								SAXParserFactory spf = SAXParserFactory
										.newInstance();
								SAXParser parser = spf.newSAXParser();
								ListXMLContentHandler handler = new ListXMLContentHandler();
								parser.parse(inputStream, handler);
								inputStream.close();
								mLists = handler.getmLists();

								if (mListsByHot == null) {
									mListsByHot = sortByHot(mLists);
									Log.i(TAG, "最热列表排序完成...");
								}
//								if (mListsByNew == null) {
//									mListsByNew = sortByNew(mLists);
//									Log.i(TAG, "最新列表排序完成...");
//								}
//								if (mListsByVm == null) {
//									mListsByVm = sortByVm(mLists);
//									Log.i(TAG, "按照评分列表排序完成...");
//								}
//								if (mListsByLetter == null) {
//									mListsByLetter = sortByLetter(mLists);
//									Log.i(TAG, "按照字母列表排序完成...");
//								}
								mHandler.obtainMessage(1, mLists)
										.sendToTarget();
							} else {
								// 文件不存在-去网上上面进行下载zip包，然后解压缩，解析数据
								// 1，去网络上面请求数据
								Log.v(TAG, "使用网络文件进行解析...");
								isUseCache = false;
								boolean reault = DownXML
										.getFile("http://list1.ppstream.com/mobile/newipadc/sub/141.xml.zip");

								if (!reault) {
									mHandler.obtainMessage(LIST_FAIL, mLists)
											.sendToTarget();
									Toast.makeText(mContext, "获取列表失败...",
											Toast.LENGTH_SHORT).show();
									return;
								}
								Log.v(TAG, "下载列表压缩包成功...");
								// 2,对压缩包进行解压缩
								boolean flag = ZipToFile.unzip("/sdcard",
										"141.xml.zip", "/sdcard/141.xml");
								if (flag) {
									Log.v(TAG, "解压成功.....");
									// 解压成功，并且删除该压缩包,
									File file_zip = new File(
											"/sdcard/141.xml.zip");
									if (file_zip.exists()) {
										file_zip.delete();
									}
								} else {
									Log.v(TAG, "解压失败.....");
									mHandler.obtainMessage(LIST_FAIL, mLists)
											.sendToTarget();
									Toast.makeText(mContext, "获取列表失败...",
											Toast.LENGTH_SHORT).show();
									return;
								}
								// 3，对获取到的XML数据进行解析
								// InputStream
								// inputStream=MyListActivity.class.getClassLoader().getResourceAsStream("141.xml");
								Log.v(TAG, "进行xml解析.....");
								InputStream inputStream = DownXML
										.getXMLFromFile(new File(
												"/sdcard/141.xml/141.xml"));
								SAXParserFactory spf = SAXParserFactory
										.newInstance();
								SAXParser parser = spf.newSAXParser();
								ListXMLContentHandler handler = new ListXMLContentHandler();
								parser.parse(inputStream, handler);
								inputStream.close();
								mLists = handler.getmLists();
								if (mListsByHot == null) {
									mListsByHot = sortByHot(mLists);
									Log.i(TAG, "最热列表排序完成...");
								}
//								if (mListsByNew == null) {
//									mListsByNew = sortByNew(mLists);
//									Log.i(TAG, "最新列表排序完成...");
//								}
//								if (mListsByVm == null) {
//									mListsByVm = sortByVm(mLists);
//									Log.i(TAG, "按照评分列表排序完成...");
//								}
//								if (mListsByLetter == null) {
//									mListsByLetter = sortByLetter(mLists);
//									Log.i(TAG, "按照字母列表排序完成...");
//								}
								mHandler.obtainMessage(1, mLists)
										.sendToTarget();
							}
						}
					}
					// // 1，去网络上面请求数据
					// DownXML.getFile("http://list1.ppstream.com/mobile/newipadc/sub/141.xml.zip");
					// // 2,对压缩包进行解压缩
					// boolean flag = ZipToFile.unzip("/sdcard", "141.xml.zip",
					// "/sdcard/141.xml");
					// if (flag) {
					// Log.i(TAG, "解压成功.....");
					// // 解压成功，并且删除该压缩包,
					// File file = new File("/sdcard/141.xml.zip");
					// if (file.exists()) {
					// file.delete();
					// }
					// } else {
					// Log.i(TAG, "解压失败.....");
					// }
					// // 3，对获取到的XML数据进行解析
					// // InputStream
					// //
					// inputStream=MyListActivity.class.getClassLoader().getResourceAsStream("141.xml");
					// InputStream inputStream = DownXML.getXMLFromFile(new
					// File(
					// "/sdcard/141.xml/141.xml"));
					// SAXParserFactory spf = SAXParserFactory.newInstance();
					// SAXParser parser = spf.newSAXParser();
					// ListXMLContentHandler handler = new
					// ListXMLContentHandler();
					// parser.parse(inputStream, handler);
					// inputStream.close();
					// mLists = handler.getmLists();
					// mHandler.obtainMessage(1, mLists).sendToTarget();
					// // XML文件解析完成，删除该xml文件以及上层空文件夹
					// //FileUtils.deleteAll(new
					// File("/sdcard/141.xml"));-->已经转移到使用广播进行删除

				} else {
					// 没有网络连接
					// Toast.makeText(mContext, "当前没有网络连接，使用缓存资源。",
					// Toast.LENGTH_SHORT).show();
					// File file = new File("/sdcard/141.xml/141.xml");
					// if (file.exists()) {
					// // 文件存在进行XML解析
					// InputStream inputStream = DownXML.getXMLFromFile(file);
					// SAXParserFactory spf = SAXParserFactory.newInstance();
					// SAXParser parser = spf.newSAXParser();
					// ListXMLContentHandler handler = new
					// ListXMLContentHandler();
					// parser.parse(inputStream, handler);
					// inputStream.close();
					// mLists = handler.getmLists();
					// mHandler.obtainMessage(1, mLists).sendToTarget();
					// } else {
					// mHandler.obtainMessage(LIST_FAIL, mLists)
					// .sendToTarget();
					// Toast.makeText(mContext, "无网络连接,请设置网络或者稍后访问.",
					// Toast.LENGTH_SHORT).show();
					// return;
					// }
					Message msg = mHandler.obtainMessage();
					msg.what = LIST_FAIL;
					mHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 刷新数据
	 */
	private void refreshData() {
		// 把最新的xml文件保存在sdcard中，下次加载的时候进行显示
		Thread thread = new Thread(runnable_refresh);
		thread.start();
	}

	Runnable runnable_refresh = new Runnable() {

		@Override
		public void run() {
			try {
				boolean reault = DownXML
						.getFile("http://list1.ppstream.com/mobile/newipadc/sub/141.xml.zip");
				if (!reault) {
					mHandler.obtainMessage(LIST_FAIL, mLists).sendToTarget();
					Toast.makeText(mContext, "获取列表失败...", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Log.v(TAG, "下载列表压缩包成功...");
				// 2,对压缩包进行解压缩
				boolean flag = ZipToFile.unzip("/sdcard", "141.xml.zip",
						"/sdcard/141.xml");
				if (flag) {
					Log.v(TAG, "解压成功.....");
					// 解压成功，并且删除该压缩包,
					File file_zip = new File("/sdcard/141.xml.zip");
					if (file_zip.exists()) {
						file_zip.delete();
					}
				} else {
					Log.v(TAG, "解压失败.....");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 记载更多数据
	 */
	private void loadMore() {
		int count = listAdapter.getCount();
		if (count + 100 < maxSum) {
			// 数据还够100条,在取出相应的五十条数据
			ChannelListServiceInterface mServiceInterface = new ChannelListServiceImp(
					mContext);
			List<SubModel> models = mServiceInterface.queryChanenlByLimited(
					100, count);
			for (int i = 0; i < 100; i++) {
				SubModel model = new SubModel();
				model.setImg(models.get(i).getImg());
				model.setName(models.get(i).getName());
				model.setOn(models.get(i).getOn());
				model.setTp(models.get(i).getTp());
				model.setVm(models.get(i).getVm());
				model.setId(models.get(i).getId());
				model.setTm(models.get(i).getTm());
				model.setLt(models.get(i).getLt());
				model.setTp(models.get(i).getTp());
				model.setSearch(models.get(i).getSearch());
				mLists.add(model);
			}

		} else {
			// 数据不足100条
			ChannelListServiceInterface mServiceInterface = new ChannelListServiceImp(
					mContext);
			List<SubModel> models = mServiceInterface.queryChanenlByLimited(
					maxSum - count, count);
			for (int i = 0; i < maxSum - count; i++) {
				SubModel model = new SubModel();
				model.setImg(models.get(i).getImg());
				model.setName(models.get(i).getName());
				model.setOn(models.get(i).getOn());
				model.setTp(models.get(i).getTp());
				model.setVm(models.get(i).getVm());
				model.setId(models.get(i).getId());
				model.setTm(models.get(i).getTm());
				model.setLt(models.get(i).getLt());
				model.setTp(models.get(i).getTp());
				model.setSearch(models.get(i).getSearch());
				mLists.add(model);
			}
		}
	}

	/**
	 * 根据字母进行排序
	 * 
	 * @param pSubModels
	 * @return
	 */
	private List<SubModel> sortByLetter(List<SubModel> pSubModels) {
		List<SubModel> tempSubModels = copeList(pSubModels); // 首先拷贝一份
		int size = tempSubModels.size();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - i - 1; j++) {
				int index = j + 1;
				if (tempSubModels.get(j).getLt()
						.compareTo(tempSubModels.get(index).getLt()) > 0) {
					SubModel tempSubModel = tempSubModels.get(j);
					tempSubModels.set(j, tempSubModels.get(index));
					tempSubModels.set(index, tempSubModel);
				}
			}
		}
		return tempSubModels;
	}

	/**
	 * 根据时间进行排序 倒置
	 * 
	 * @param pSubModels
	 * @return
	 */
	private List<SubModel> sortByNew(List<SubModel> pSubModels) {

		return ArraysUtils.convertArrays(pSubModels);
	}

	/**
	 * 根据剧情的评分进行排序
	 * 
	 * @param pSubModels
	 * @return
	 */
	private List<SubModel> sortByVm(List<SubModel> pSubModels) {
		List<SubModel> tempSubModels = copeList(pSubModels); // 首先拷贝一份
		int size = tempSubModels.size();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - i - 1; j++) {
				int index = j + 1;
				if (tempSubModels.get(j).getVm() < tempSubModels.get(index)
						.getVm()) {
					SubModel tempSubModel = tempSubModels.get(j);
					tempSubModels.set(j, tempSubModels.get(index));
					tempSubModels.set(index, tempSubModel);
				}
			}
		}
		return tempSubModels;
	}

	/**
	 * 根据剧情的看的人数进行排序
	 * 
	 * @param pSubModels
	 * @return
	 */
	private List<SubModel> sortByHot(List<SubModel> pSubModels) {
		List<SubModel> tempSubModels = copeList(pSubModels); // 首先拷贝一份
		int size = tempSubModels.size();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - i - 1; j++) {
				int index = j + 1;
				if (tempSubModels.get(j).getOn() < tempSubModels.get(index)
						.getOn()) {
					SubModel tempSubModel = tempSubModels.get(j);
					tempSubModels.set(j, tempSubModels.get(index));
					tempSubModels.set(index, tempSubModel);
				}
			}
		}
		return tempSubModels;
	}

	/**
	 * 进行复制一个集合
	 * 
	 * @param pSource
	 * @param pDest
	 * @param pSize
	 */
	private List<SubModel> copeList(List<SubModel> pSource) {
		int size = pSource.size();
		List<SubModel> pDest = new ArrayList<SubModel>(size);
		for (int i = 0; i < size; i++) {
			SubModel model = new SubModel();
			model.setImg(pSource.get(i).getImg());
			model.setName(pSource.get(i).getName());
			model.setOn(pSource.get(i).getOn());
			model.setTp(pSource.get(i).getTp());
			model.setVm(pSource.get(i).getVm());
			model.setId(pSource.get(i).getId());
			model.setTm(pSource.get(i).getTm());
			model.setLt(pSource.get(i).getLt());
			model.setTp(pSource.get(i).getTp());
			model.setSearch(pSource.get(i).getSearch());
			pDest.add(model);
		}
		return pDest;
	}

	/**
	 * 根据输入的字符型进行搜索剧情列表
	 * 
	 * @param pStr
	 * @return 返回搜索成功的剧情对象集合
	 */
	private List<SubModel> searchModels(String pStr, List<SubModel> pSource) {
		List<SubModel> pDest = new ArrayList<SubModel>();
		char[] search_char = pStr.toCharArray();
		int length = search_char.length;
		int size = pSource.size();
		for (int i = 0; i < size; i++) {
			String name = pSource.get(i).getName();
			for (int index = 0; index < length; index++) {
				if (name.indexOf(search_char[index]) != -1) {
					pDest.add(pSource.get(i));
					break;
				}
			}
		}
		return pDest;
	}
}

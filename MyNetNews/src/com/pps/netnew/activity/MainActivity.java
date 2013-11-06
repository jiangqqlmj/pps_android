package com.pps.netnew.activity;

import tv.pps.bi.config.IntervalTimeConstance;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.service.ManagerService;
import tv.pps.bi.utils.LogUtils;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pps.netnew.adapter.MainPagerAdapter;
import com.pps.netnew.fragment.LeftCategoryFragment;
import com.pps.netnew.fragment.RightPerMsgCenterFragment;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends SlidingFragmentActivity {

	private ImageButton main_left_imgbtn;
	private ImageButton main_right_imgbtn;
	private ViewPager myViewPager;
	private PagerTitleStrip pagertitle;
	private TabPageIndicator mTabPageIndicator;
	private PagerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 开启log,默认为关闭，true表示开启，false表示关闭
		LogUtils.setShowLog(true);
		// 设置开启用户行为搜集服务
		IntervalTimeConstance.setStartServiceSwitch(this, true);
		// 设置uuid（设备唯一标识）和平台信息
		DeviceInfoStatistic.setUuidAndPlatform(
				"UUID_0001_MyNetNews", "pps_android", this);
		// 设置loginid，在用户登录时调用
		DeviceInfoStatistic.setLoginId("123456", this);
		// 设置数据投递时间周期，以毫秒为单位,默认1小时:1*60*60*1000
		IntervalTimeConstance.setStartDeliverServiceTime(2 * 60 * 1000);
		// 设置用户行为数据搜集时间间隔,以毫秒为单位,默认30分钟：30*60*1000
		IntervalTimeConstance.setStartUserInfoSearchTime(60 * 1000);
		// 启动用户行为监听服务
		ManagerService.startService(this);

		IntervalTimeConstance.setStartServiceSwitch(MainActivity.this, true);

		initSlidingMenu();
		initView();
		initValidata();
		bindData();
		initListener();
	}

	/**
	 * 初始化SlidingMenu视图
	 */
	private void initSlidingMenu() {
		// 设置滑动菜单的属性值
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);  // 设置左右的滑动菜单
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);  // 设置触摸到屏幕的边缘侧面的滑动界面打开
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width); //设置阴影的宽度
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);   //设置阴影的效果
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);  
		getSlidingMenu().setFadeDegree(0.35f);
		// 设置主界面的视图 -中间的视图
		setContentView(R.layout.main);
		// 设置左边菜单打开后的视图界面  
		setBehindContentView(R.layout.left_content);
		getSupportFragmentManager()
		        .beginTransaction()
				.replace(R.id.left_content_id, new LeftCategoryFragment())
				.commit();  
		// 设置右边菜单打开后的视图界面
		getSlidingMenu().setSecondaryMenu(R.layout.right_content);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.right_content_id, new RightPerMsgCenterFragment())
				.commit();
	}

	private void initView() {
		main_left_imgbtn = (ImageButton) this
				.findViewById(R.id.main_left_imgbtn);
		main_right_imgbtn = (ImageButton) this
				.findViewById(R.id.main_right_imgbtn);
		myViewPager = (ViewPager) this.findViewById(R.id.myviewpager);
		//pagertitle = (PagerTitleStrip) this.findViewById(R.id.pagertitle);
		//
		mTabPageIndicator=(TabPageIndicator)this.findViewById(R.id.indicator);

	}

	/**
	 * 初始化变量
	 */
	private void initValidata() {
		//pagertitle.setTextSize(0, 25);
		mAdapter = new MainPagerAdapter(getSupportFragmentManager());

	}

	/**
	 * 绑定数据
	 */
	private void bindData() {
		myViewPager.setAdapter(mAdapter);
		myViewPager.setCurrentItem(0);
		// 
		mTabPageIndicator.setViewPager(myViewPager);
	}

	private void initListener() {
		main_left_imgbtn.setOnClickListener(new MySetOnClickListener());
		main_right_imgbtn.setOnClickListener(new MySetOnClickListener());
		myViewPager.setOnPageChangeListener(new MySetOnPageChangeListener());
	}

	/**
	 * ViewPager页面选项卡切换监听器
	 */
	class MySetOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
		}
	}

	/**
	 * 进行侧滑界面打开与关闭
	 * 
	 * @author jiangqq
	 * 
	 */
	class MySetOnClickListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_left_imgbtn:
				showMenu();
				break;

			case R.id.main_right_imgbtn:
				showSecondaryMenu();
				break;
			}
		}
	}
}

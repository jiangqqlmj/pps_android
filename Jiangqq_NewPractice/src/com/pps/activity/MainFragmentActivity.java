package com.pps.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pps.fragment.MyListFragment;
import com.pps.receiver.AlarmBroadcast;
import com.pps.service.ListenNetStateService;

public class MainFragmentActivity extends FragmentActivity implements
		OnTouchListener {
	/**
	 * 滚动显示和隐藏menu时，手指滑动需要达到的速度。
	 */
	public static final int SNAP_VELOCITY = 200;

	/**
	 * 屏幕宽度值。
	 */
	private int screenWidth;

	/**
	 * menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
	 */
	private int leftEdge;

	/**
	 * menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。
	 */
	private int rightEdge = 0;

	/**
	 * menu完全显示时，留给content的宽度值。
	 */
	private int menuPadding = 250;

	/**
	 * 主内容的布局。
	 */
	private View content;

	/**
	 * menu的布局。
	 */
	private View menu;

	/**
	 * menu布局的参数，通过此参数来更改leftMargin的值。
	 */
	private LinearLayout.LayoutParams menuParams;

	/**
	 * 记录手指按下时的横坐标。
	 */
	private float xDown;

	/**
	 * 记录手指移动时的横坐标。
	 */
	private float xMove;

	/**
	 * 记录手机抬起时的横坐标。
	 */
	private float xUp;

	/**
	 * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
	 */
	private boolean isMenuVisible;

	/**
	 * 用于计算手指滑动的速度。
	 */
	private VelocityTracker mVelocityTracker;

	private Button btn_left_home;
	private Button btn_left_search;
	private Button btn_left_ipd;
	private Button btn_left_vip;
	private Button btn_left_fav_his;
	private Button btn_left_download;
	private Button btn_left_setting;
	private boolean isMenu = true; //标识界面是否已经侧滑到菜单栏

	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private long exitTime = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_main);
	    //发送注册删除节目列表xml的广播定时器
		this.sendBroadcast(new Intent(AlarmBroadcast.ALARM_ACTION));
		//启动检测网络的服务
		Intent _Intent=new Intent(this, ListenNetStateService.class);
		this.startService(_Intent);
		initValues();
		initListener();
		scrollToMenu();//默认显示出菜单
		fragmentManager = getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
		if (this.findViewById(R.id.container) != null) {
			MyListFragment listFragment = new MyListFragment();
			transaction.add(R.id.container, listFragment);
			// transaction.addToBackStack(null);
			transaction.commit();
		}
	}

	/**
	 * 初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。
	 */
	private void initValues() {
		WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = window.getDefaultDisplay().getWidth();
		content = findViewById(R.id.content);
		menu = findViewById(R.id.menu);
		menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
		
		// 将menu的宽度设置为屏幕宽度减去menuPadding
		//menuParams.width = screenWidth - menuPadding;
		menuParams.width=75;
		// 左边缘的值赋值为menu宽度的负数
		leftEdge = -menuParams.width;
		// menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
		menuParams.leftMargin = leftEdge;
		// 将content的宽度设置为屏幕宽度
		content.getLayoutParams().width = screenWidth;

		btn_left_home = (Button) this.findViewById(R.id.btn_left_home);
		btn_left_search = (Button) this.findViewById(R.id.btn_left_search);
		btn_left_ipd = (Button) this.findViewById(R.id.btn_left_ipd);
		btn_left_vip = (Button) this.findViewById(R.id.btn_left_vip);
		btn_left_fav_his = (Button) this.findViewById(R.id.btn_left_fav_his);
		btn_left_download = (Button) this.findViewById(R.id.btn_left_download);
		btn_left_setting = (Button) this.findViewById(R.id.btn_left_setting);
        
		
	}

	private void initListener() {
		content.setOnTouchListener(this);

		btn_left_home.setOnClickListener(new MyOnClickListener());
		btn_left_search.setOnClickListener(new MyOnClickListener());
		btn_left_ipd.setOnClickListener(new MyOnClickListener());
		btn_left_vip.setOnClickListener(new MyOnClickListener());
		btn_left_fav_his.setOnClickListener(new MyOnClickListener());
		btn_left_download.setOnClickListener(new MyOnClickListener());
		btn_left_setting.setOnClickListener(new MyOnClickListener());

	}
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时，记录按下时的横坐标
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown);
			if (isMenuVisible) {
				menuParams.leftMargin = distanceX;
			} else {
				menuParams.leftMargin = leftEdge + distanceX;
			}
			if (menuParams.leftMargin < leftEdge) {
				menuParams.leftMargin = leftEdge;
			} else if (menuParams.leftMargin > rightEdge) {
				menuParams.leftMargin = rightEdge;
			}
			menu.setLayoutParams(menuParams);
			break;
		case MotionEvent.ACTION_UP:
			// 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
			xUp = event.getRawX();
			if (wantToShowMenu()) {
				if (shouldScrollToMenu()) {
					scrollToMenu();
					isMenu=true;
				} else {
					scrollToContent();
					isMenu=false;
				}
			} else if (wantToShowContent()) {
				if (shouldScrollToContent()) {
					scrollToContent();
				} else {
					scrollToMenu();
				}
			}
			recycleVelocityTracker();
			break;
		}
		return true;
	}

	/**
	 * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
	 * 
	 * @return 当前手势想显示content返回true，否则返回false。
	 */
	private boolean wantToShowContent() {
		return xUp - xDown < 0 && isMenuVisible;
	}

	/**
	 * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
	 * 
	 * @return 当前手势想显示menu返回true，否则返回false。
	 */
	private boolean wantToShowMenu() {
		return xUp - xDown > 0 && !isMenuVisible;
	}

	/**
	 * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
	 * 就认为应该滚动将menu展示出来。
	 * 
	 * @return 如果应该滚动将menu展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToMenu() {
		return xUp - xDown > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
	 * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
	 * 
	 * @return 如果应该滚动将content展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToContent() {
		return xDown - xUp + menuPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 将屏幕滚动到menu界面，滚动速度设定为30.
	 */
	private void scrollToMenu() {
		new ScrollTask().execute(30);
	}

	/**
	 * 将屏幕滚动到content界面，滚动速度设定为-30.
	 */
	private void scrollToContent() {
		new ScrollTask().execute(-30);
	}

	/**
	 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
	 * 
	 * @param event
	 *            content界面的滑动事件
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 获取手指在content界面滑动的速度。
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = menuParams.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > rightEdge) {
					leftMargin = rightEdge;
					break;
				}
				if (leftMargin < leftEdge) {
					leftMargin = leftEdge;
					break;
				}
				publishProgress(leftMargin);
				//为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
				sleep(20);
			}
			if (speed[0] > 0) {
				isMenuVisible = true;
			} else {
				isMenuVisible = false;
			}
			return leftMargin;
		}
		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			menuParams.leftMargin = leftMargin[0];
			menu.setLayoutParams(menuParams);
		}
		@Override
		protected void onPostExecute(Integer leftMargin) {
			menuParams.leftMargin = leftMargin;
			menu.setLayoutParams(menuParams);
		}
	}
	/**
	 * 使当前线程睡眠指定的毫秒数。
	 * 
	 * @param millis
	 *            指定当前线程睡眠多久，以毫秒为单位
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对左边的菜单Item项监听事件,默认点击全部先跳转到首页
	 * @author jiangqingqing
	 *
	 */
	class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_left_home:
				scrollToContent();
				isMenu = false;
				break;
			case R.id.btn_left_search:
				scrollToContent();
				isMenu = false;
				break;
			case R.id.btn_left_ipd:
				scrollToContent();
				isMenu = false;
				break;
			case R.id.btn_left_vip:
				scrollToContent();
				isMenu = false;
				break;
			case R.id.btn_left_fav_his:
				scrollToContent();
				isMenu = false;
				break;
			case R.id.btn_left_download:
				scrollToContent();
				isMenu = false;
				break;
			case R.id.btn_left_setting:
				scrollToContent();
				isMenu = false;
				break;
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
         //event.getAction() == KeyEvent.ACTION_DOWN
		if(event!=null){
		 if (event.getAction() == KeyEvent.ACTION_DOWN &&keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isMenu) {
				// 进行侧滑到菜单栏
				scrollToMenu();
				isMenu = true;
			} else {
				// 连续按两次退出系统
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(this, "再按一次退出程序!", Toast.LENGTH_SHORT)
							.show();
					exitTime = System.currentTimeMillis();
				} else {
//					ActivityManager activityMgr= (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//					activityMgr.restartPackage(this.getPackageName());
//					System.exit(0);
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(0);
				}
			}
		}}else {
			if(keyCode == KeyEvent.KEYCODE_BACK)
			{
				if (!isMenu) {
					// 进行侧滑到菜单栏
					scrollToMenu();
					isMenu = true;
				}else {
					scrollToContent();
					isMenu=false;
				}
			}
		}
		return false;

	}
}

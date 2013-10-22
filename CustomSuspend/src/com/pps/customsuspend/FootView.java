package com.pps.customsuspend;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class FootView extends ImageView {

	private float mTouchX; //触摸点的x坐标
	private float mTouchY; //触摸点的y坐标
	private float x;       //x坐标
	private float y;       //y坐标
	private int   startX;     
	private int   startY;
	
	boolean isShow = false;  
	private OnClickListener mOnClickListener;
	private int controlledSpace = 20;   
	
	private int imgId=R.drawable.ic_launcher;
	private int screenWidth;  // WindowManager视图默认宽度
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams windowManagerParams=new WindowManager.LayoutParams();
	public FootView(Context context) {
		super(context);
		initView(context);
	}

	public FootView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	/**
	 * 初始化界面
	 * @param pContext
	 */
	public void initView(Context pContext)
	{
		mWindowManager=(WindowManager)pContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	 	this.setImageResource(imgId);
		screenWidth=mWindowManager.getDefaultDisplay().getWidth();
	 	windowManagerParams.type=LayoutParams.TYPE_PHONE;
	 	windowManagerParams.format=PixelFormat.RGBA_8888; // 背景色透明
	 	windowManagerParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE;
	 	//悬浮框在屏幕的左上方
	 	windowManagerParams.gravity=Gravity.LEFT|Gravity.TOP;
	 	//以屏幕左上方为起点
	 	windowManagerParams.x=0;
	 	windowManagerParams.y=20;
	 	windowManagerParams.width=LayoutParams.WRAP_CONTENT;
	 	windowManagerParams.height=LayoutParams.WRAP_CONTENT;
	 	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x=event.getRawX();
		y=event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchX=event.getX();
			mTouchY=event.getY();
			startX=(int)event.getRawX();
			startY=(int)event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			updataView();
			break;
			
		case MotionEvent.ACTION_UP:
		    if(Math.abs(x-startX)>controlledSpace&&Math.abs(y-startY)>controlledSpace)
		    {
		    	if(mOnClickListener!=null)
		    	{
		    		mOnClickListener.onClick(this);
		    	}
		    }
		    Log.i("jiangqq", "x="+x+" startX+"+startX+" y="+y+" startY="+startY);  
		    if(x<screenWidth/2)
		    {
		    	x=0;
		    }else if (x>screenWidth/2) {
				x=screenWidth;
			}
		    updataView();
		    
		    break;
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * 设置图片资源
	 * @param resId
	 */
	public void setImageId(int resId)
	{
		this.imgId=resId;
	}
	/**
	 * 添加监听器
	 */
	public void setOnClickListener(OnClickListener pOnClickListener)
	{
		this.mOnClickListener=pOnClickListener;
	}
	/**
	 * 隐藏该窗体
	 */
	public void hide()
	{
		if(isShow)
		{
			mWindowManager.removeView(this);
			isShow=false;
		}
	}
	/**
	 * 显示该窗体
	 */
	public void show()
	{
		if(!isShow)
		{
			mWindowManager.addView(this, windowManagerParams);
			isShow=true;
		}
	}
	
	/**
	 * 更新视图位置
	 */
	private void updataView()
	{
		windowManagerParams.x=(int)(x-mTouchX);
		windowManagerParams.y=(int)(y-mTouchY);
		mWindowManager.updateViewLayout(this, windowManagerParams);
	}
}

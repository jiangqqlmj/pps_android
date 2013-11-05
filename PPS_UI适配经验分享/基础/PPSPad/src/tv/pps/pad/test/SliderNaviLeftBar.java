package tv.pps.pad.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class SliderNaviLeftBar extends LinearLayout {
	
	private static final int LINE_WIDTH=3;//左边选中效果的线的宽度
	private static final int STEP_VALUE=30;//计算step的一个固定常量
	private LinearLayout imageView_homepage;
	private Paint paint;
	private Rect curRectF;
	private Rect tarRectF;
	private Bitmap bmp;
	
	public SliderNaviLeftBar(Context context) {
		this(context, null);
	}
	
	public SliderNaviLeftBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		LayoutInflater.from(context).inflate(R.layout.leftbar, this, true);
		bmp=BitmapFactory.decodeResource(getResources(), R.drawable.pps_line);
		paint = new Paint();
		paint.setAntiAlias(true);
		imageView_homepage = (LinearLayout)findViewById(R.id.left_imageview_homepage);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int step = getHeight()/STEP_VALUE;
		if (curRectF == null)
		{
			curRectF = new Rect(imageView_homepage.getLeft(), imageView_homepage.getTop(),LINE_WIDTH, imageView_homepage.getBottom());
		}
		if (tarRectF == null)
		{
			tarRectF = new Rect(imageView_homepage.getLeft(), imageView_homepage.getTop(),LINE_WIDTH, imageView_homepage.getBottom());
		}
		if (Math.abs(curRectF.top - tarRectF.top) < step) 
		{
			curRectF.top = tarRectF.top;
			curRectF.bottom = tarRectF.bottom;
		}
		if (curRectF.top > tarRectF.top) 
		{ 
			curRectF.top -= step;
			curRectF.bottom -= step;
			invalidate();
		}
		else if (curRectF.top < tarRectF.top) 
		{
			curRectF.top += step;
			curRectF.bottom += step;
			invalidate();
		}
		canvas.drawBitmap(bmp, null, curRectF, paint);
	}
	
	/**
	 * 设置并启动动画
	 * @param view
	 */
	public void setAnimation(View view)
	{
		tarRectF.left = view.getLeft();
		tarRectF.right = LINE_WIDTH;
		tarRectF.top = view.getTop();
		tarRectF.bottom = view.getBottom();
		invalidate();
	}

}

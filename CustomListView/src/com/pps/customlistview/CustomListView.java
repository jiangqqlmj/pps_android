package com.pps.customlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义ListView-带有边框
 * @author jiangqingqing
 *
 */
public class CustomListView extends ListView {

	public CustomListView(Context context) {
		super(context);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * (non-Javadoc) 给ListView加入边框
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		float width=getWidth();
		float height=getHeight();
		int lineWidth=10;
		int redColor=Color.RED;
		Paint mPaint=new Paint();
		mPaint.setColor(redColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(lineWidth); //画的线的粗细
		canvas.drawLine(0+lineWidth/2, 0+lineWidth/2, width-lineWidth/2, 0+lineWidth/2, mPaint);  //top
		canvas.drawLine(width-lineWidth/2, 0, width-lineWidth/2, height, mPaint); //right
		canvas.drawLine(width-lineWidth/2, height-lineWidth/2, 0+lineWidth/2, height-lineWidth/2, mPaint); //bottom
		canvas.drawLine(0+lineWidth/2,  0,  0+lineWidth/2, height, mPaint); //left
		//DashPathEffect effect=new DashPathEffect(intervals, phase);
		
		super.onDraw(canvas);
	}
}

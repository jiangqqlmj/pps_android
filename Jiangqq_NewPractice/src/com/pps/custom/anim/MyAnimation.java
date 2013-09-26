package com.pps.custom.anim;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;


/**
 * 自定义View动画</a>
 * 对布局进行3D旋转</a>
 * @author jiangqingqing
 * @time 2013/06/18 
 */
public class MyAnimation extends Animation {
    private int halfWidth;
    private int halfHeight; 
    private Camera camera = new Camera();   
    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(800);
        setFillAfter(true);        
        halfWidth = width / 2;
        halfHeight = height / 2;
        setInterpolator(new LinearInterpolator());      
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();    
        camera.save();
        camera.translate(0.5f , 0.0f, (3000 - 3000.0f * interpolatedTime));
        camera.rotateY(360 * interpolatedTime);
        camera.getMatrix(matrix);
        matrix.preTranslate(-halfWidth, -halfHeight);
        matrix.postTranslate(halfWidth, halfHeight);
        camera.restore();
    }
}

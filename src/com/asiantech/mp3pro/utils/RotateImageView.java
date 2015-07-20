package com.asiantech.mp3pro.utils;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.mp3pro.R;




public class RotateImageView extends ImageView
{
	private int mAngle;
	
	
	public RotateImageView(Context context)
	{
		super(context);
	}


	public RotateImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		loadAttributes(context, attrs);
	}


	public RotateImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		loadAttributes(context, attrs);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) // if you want to rotate the entire view (along with its background), you should oveerride draw() instead of onDraw()
	{
		canvas.save();
		canvas.rotate(mAngle%360, canvas.getWidth()/2, canvas.getHeight()/2);	
		super.onDraw(canvas);
		canvas.restore();
		
//		Drawable drawable = getDrawable();
//		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//		
//		Matrix transform = new Matrix();
//		transform.preRotate(mAngle%360, canvas.getWidth()/2, canvas.getHeight()/2);
//		
//		canvas.save();
//		canvas.drawBitmap(bitmap, transform, null);
//		canvas.restore();
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int w = getDrawable().getIntrinsicWidth();
		int h = getDrawable().getIntrinsicHeight();
		double a = Math.toRadians(mAngle);

		int width = (int) (Math.abs(w * Math.cos(a)) + Math.abs(h * Math.sin(a)));
		int height = (int) (Math.abs(w * Math.sin(a)) + Math.abs(h * Math.cos(a)));

		setMeasuredDimension(width, height);
	}


	public int getAngle()
	{
		return mAngle;
	}


	public void setAngle(int angle)
	{
		mAngle = angle;
	}
	
	
	private void loadAttributes(Context context, AttributeSet attrs)
	{
		TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RotateImageView);
		mAngle = arr.getInt(R.styleable.RotateImageView_angle, 0);
		arr.recycle();
	}
}

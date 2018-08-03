package com.demo.resetmysoul.study_demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.resetmysoul.study_demo.Ls;
import com.demo.resetmysoul.study_demo.R;

/**
 * Created by Mr. Huang on 2018/4/21.
 * Type:
 * des:
 */

public class CustomRatingBar extends View {

    public interface OnStarTouchListener {
        void onStarTouch(int touchCount);
    }

    private static final int DEFAULT_STAR_SIZE = 20;
    private static final int DEFAULT_STAR_COUNT = 1;
    private static final int DEFAULT_STAR_SPACE = 0;
    private static final boolean DEFAULT_STAR_CAN_TOUCH = true;


    private Bitmap mBitmap;
    /**
     * 一个星星的宽度(宽高一样)
     */
    private int mStarSize;
    /**
     * 星星的个数
     */
    private int mStarCount;
    /**
     * 星星的间距
     */
    private int mStarSpace;

    /**
     * 是否有触摸事件
     */
    private boolean mCanTouch;

    private int mWidth;

    private OnStarTouchListener onStarTouchListener;



    public CustomRatingBar(Context context) {
        this(context, null);
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_rating_star0);

    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }



    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomRatingBar);

        mStarSize = array.getDimensionPixelOffset(R.styleable.CustomRatingBar_starSize, DEFAULT_STAR_SIZE);
        mStarCount = array.getInteger(R.styleable.CustomRatingBar_starCount, DEFAULT_STAR_COUNT);
        if (mStarCount > 5) {
            mStarCount = 5;
        }
        mStarSpace = array.getDimensionPixelOffset(R.styleable.CustomRatingBar_starSpace, DEFAULT_STAR_SPACE);
        mCanTouch = array.getBoolean(R.styleable.CustomRatingBar_starCanTouch, DEFAULT_STAR_CAN_TOUCH);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mStarSize * mStarCount + (mStarCount - 1) * mStarSpace;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        mWidth = (int) result;
        return (int) result;
    }

    private int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mStarSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return (int) result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawStar(canvas);
//        canvas.drawBitmap(mBitmap , 0 , 0 ,new Paint());
//        Rect rect = new Rect(0,0,mBitmap.getWidth() ,mBitmap.getHeight());
       /* RectF rect1 = new RectF(0,0,getWidth()/4f,getWidth()/4f);
        int width = getWidth();
        int height = getHeight();
        canvas.drawBitmap(mBitmap, null , rect1,null);
        rect1.set(getWidth()/4f * 1 , 0 ,2 * getWidth()/4f , getWidth()/4f );
        canvas.drawBitmap(mBitmap, null , rect1,null);
        rect1.set(getWidth()/4f * 2 , 0 ,3 * getWidth()/4f , getWidth()/4f );
        canvas.drawBitmap(mBitmap, null , rect1,null);
        rect1.set(getWidth()/4f * 3 , 0 ,4 * getWidth()/4f , getWidth()/4f );
        canvas.drawBitmap(mBitmap, null , rect1,null);*/
    }

    private void drawStar(Canvas canvas){
        RectF rect1 = new RectF(0,0,0,0);
        Ls.d("mStarSize:"+mStarSize +" mStarSpace:"+mStarSpace);

        for (int i = 0; i < mStarCount; i++) {
            int left = (mStarSize + mStarSpace) * i;
            int right = mStarSize * (i+1) + mStarSpace * i;
            Ls.d("left:"+left +" right:"+right);
            rect1.set(left,0, right,mStarSize);
            if (mCanTouch) {
                if (i < mTouchCount) {
                    canvas.drawBitmap(BitmapManager.getBitmap(i), null, rect1, null);
                } else {
                    canvas.drawBitmap(BitmapManager.getBitmap(5) , null ,rect1 , null);
                }
            } else {
                canvas.drawBitmap(BitmapManager.getBitmap(i), null, rect1, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCanTouch) {
            checkTouch(event);
        }
        return super.onTouchEvent(event) || mCanTouch;
    }



    private int mTouchCount = 5;

    private void checkTouch(MotionEvent event) {
        if (event.getX() > (mStarSize * mStarCount + (mStarCount - 1) * mStarSpace) || event.getY() > mStarSize) {
            return;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int nowTouchCount = (int) (event.getX() / (mStarSize + mStarSpace)) + 1;
                if (nowTouchCount != mTouchCount) {
                    mTouchCount = nowTouchCount;
                    Ls.d("这里走了"+mTouchCount);
                    reDraw(false);
                    if (onStarTouchListener != null) {
                        onStarTouchListener.onStarTouch(mTouchCount);
                    }
                }
                break;

        }
    }

    public void setOnStarTouchListener(OnStarTouchListener onStarTouchListener) {
        this.onStarTouchListener = onStarTouchListener;
    }

    /**
     * 刷新
     *
     * @param requestLayout 是否需要重新计算宽高
     */
    private void reDraw(boolean requestLayout) {
        if (requestLayout) {
            requestLayout();
        }
        invalidate();
    }

    public int getTouchCount() {
        return mTouchCount;
    }
}

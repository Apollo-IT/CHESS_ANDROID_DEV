package com.app.hrms.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.app.hrms.utils.PixelUtil;


public class GalleryNavigator extends View {
    
    private final int RADIUS_DP = 3;
    private int m_nRadius ;
    
    private final int SPACING_DP = 4;
    private int m_nSpacing = 6;
    
    private int mSize = 5;
    private int mPosition = 0;
    private static final Paint mOnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);;
    private static final Paint mOffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);;

    
    public GalleryNavigator(Context context) {
        super(context);
        mOnPaint.setColor(0xFFFFFFFF);
        mOffPaint.setColor(0xFF9E9C94);
        m_nRadius = PixelUtil.dpToPx(context, RADIUS_DP);
        m_nSpacing = PixelUtil.dpToPx(context, SPACING_DP);
        
    }

    public GalleryNavigator(Context c, int size) {
        this(c);
        mSize = size;
        m_nRadius = PixelUtil.dpToPx(c, RADIUS_DP);
        m_nSpacing = PixelUtil.dpToPx(c, SPACING_DP);
    }

    public GalleryNavigator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOnPaint.setColor(0xFFFFFFFF);
        mOffPaint.setColor(0xFF9E9C94);
        m_nRadius = PixelUtil.dpToPx(context, RADIUS_DP);
        m_nSpacing = PixelUtil.dpToPx(context, SPACING_DP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mSize; ++i) {
            if (i == mPosition) {
                canvas.drawCircle(i * (2 * m_nRadius + m_nSpacing) + m_nRadius, m_nRadius, m_nRadius, mOnPaint);
            } else {
                canvas.drawCircle(i * (2 * m_nRadius + m_nSpacing) + m_nRadius, m_nRadius, m_nRadius, mOffPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize * (2 * m_nRadius + m_nSpacing) - m_nSpacing, 2 * m_nRadius);
    }

	public void setPosition(int id) {
        mPosition = id;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public void setPaints(int onColor, int offColor) {
        mOnPaint.setColor(onColor);
        mOffPaint.setColor(offColor);
    }

    public void setBlack() {
        setPaints(0xE6000000, 0x66000000);
    }

}

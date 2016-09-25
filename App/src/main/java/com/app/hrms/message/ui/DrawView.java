package com.app.hrms.message.ui;

/**
 * Created by Administrator on 7/27/2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private List<Path> paths = new ArrayList<Path>();
    private List<Path> undoPaths = new ArrayList<Path>();
    private Map<Path, Integer> colorMap = new HashMap<Path, Integer>();

    private Paint currentPaint = new Paint();
    private Path currentPath;
    private Canvas mCanvas;
    private int screen_width;
    private int screen_height;

    //Bitmap to be used by the canvas
    public Bitmap canvasBitmap;

    private int currentColor;


    public DrawView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        //do stuff that was in your original constructor...
        setFocusable(true);
        setFocusableInTouchMode(true);

        currentPaint.setColor(Color.BLACK);
        currentPaint.setAntiAlias(true);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(5);
        currentColor = Color.BLACK;

        currentPath = new Path();
        mCanvas = new Canvas();
    }

    public void setColor(int color) {
        currentPaint = new Paint();
        currentPaint.setColor(color);
        currentPaint.setAntiAlias(true);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(5);
        currentColor = color;
    }

    public int getColor() {
        return currentPaint.getColor();
    }

    public void clearScreen() {
        paths = new ArrayList<Path>();
        undoPaths = new ArrayList<Path>();
        colorMap = new HashMap<Path, Integer>();
        currentPath = new Path();
        canvasBitmap = Bitmap.createBitmap(screen_width, screen_height, Bitmap.Config.ARGB_8888);
        invalidate();
    }

    public void undo() {
        if (paths.size() > 0) {
            undoPaths.add(paths.remove(paths.size() - 1));
            invalidate();
        }
    }

    public void redo() {
        if (undoPaths.size() > 0) {
            paths.add(undoPaths.remove(undoPaths.size() - 1));
            invalidate();
        }
    }

    public void drawBitmap(Bitmap bitmap)
    {
        canvasBitmap = bitmap;
        invalidate();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        //call the super class version of the method
        super.onSizeChanged(w, h, oldw, oldh);
        screen_width = w;
        screen_height = h;
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(canvasBitmap, 0, 0, currentPaint);

        for (Path p : paths) {
            currentPaint.setColor(colorMap.get(p));
            canvas.drawPath(p, currentPaint);
        }

        currentPaint.setColor(currentColor);
        canvas.drawPath(currentPath, currentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                undoPaths = new ArrayList<Path>();
                currentPath.moveTo(x, y);
                currentPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(currentPath, currentPaint);
                paths.add(currentPath);
                currentPath.lineTo(x, y);
                paths.add(currentPath);
                colorMap.put(currentPath, currentColor);
                currentPath = new Path();
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }
}


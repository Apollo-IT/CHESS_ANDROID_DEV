package com.app.hrms.ui.home.subordinate;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import com.github.mikephil.charting.charts.PieChart;

public class CircleChart extends PieChart {
    public CircleChart(Context context) {
        super(context);
        initUI();
    }

    public CircleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public CircleChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI();
    }
    private void initUI(){
        setUsePercentValues(true);
        setDescription("");
        setExtraOffsets(5, 10, 5, 5);

        setDragDecelerationFrictionCoef(0.95f);
        setDrawHoleEnabled(false);
        setHoleColor(Color.WHITE);

        setTransparentCircleColor(Color.WHITE);
        setTransparentCircleAlpha(110);

        setHoleRadius(5f);
        setTransparentCircleRadius(10f);

        setDrawCenterText(true);

        setRotationAngle(0);
        setRotationEnabled(true);
        setHighlightPerTapEnabled(true);

        setDrawEntryLabels(false);
        setEntryLabelColor(Color.WHITE);
        setEntryLabelTextSize(10f);
    }
}

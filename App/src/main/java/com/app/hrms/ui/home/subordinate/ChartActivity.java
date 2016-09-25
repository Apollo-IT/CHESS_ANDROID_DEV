package com.app.hrms.ui.home.subordinate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.subordinate.adapter.SubordinateInfoAdapter;
import com.app.hrms.utils.PixelUtil;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity implements OnChartValueSelectedListener, ListItemClickHandler{
    protected String[]  mParties = new String[0];
    protected int[]     mColors  = new int[0];
    protected PieEntry[]mEntries = new PieEntry[0];


    protected CircleChart mChart;
    protected WebView webView;
    protected ListView listView;
    protected SubordinateInfoAdapter adapter;
    protected List<SubordinateInfo> list;

    /***********************************************************************************************
     *                                  On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_subordinate);

        //init subordinate list
        listView = (ListView)findViewById(R.id.listview);
        list = MySubordinateActivity.subordinateList;
        adapter = new SubordinateInfoAdapter(this, R.layout.list_subordinate_item, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onListItemClick(list.get(i).pernr);
            }
        });
        //back button
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mChart = (CircleChart) findViewById(R.id.chart1);

        webView = (WebView)findViewById(R.id.webview);
        webView.setVisibility(View.GONE);
    }
    protected void setPageTitle(String title){
        ((TextView)findViewById(R.id.txtTitle)).setText(title);
    }
    protected void setChartTitle(String title){
        ((TextView)findViewById(R.id.chart_title_txt)).setText(title);
    }
    protected void showWebView(String urlStr){
        mChart.setVisibility(View.GONE);
        findViewById(R.id.chat_descript_list).setVisibility(View.GONE);

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlStr);
    }
    /***********************************************************************************************
     *                                      Draw Chart
     */
    protected void drawChart(){
        mChart.getLegend().setEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
        //mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for(int i=0; i<mEntries.length; i++){
            entries.add(mEntries[i]);
            colors.add(mColors[i]);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);

        mChart.highlightValues(null);
        mChart.invalidate();

        drawChartPartText();
    }
    private void drawChartPartText(){
        LinearLayout descView = (LinearLayout)findViewById(R.id.chat_descript_list);
        descView.removeAllViews();
        for(int i=0; i<mParties.length; i++){
            LinearLayout LL = new LinearLayout(this);
            LL.setOrientation(LinearLayout.VERTICAL);
            int w = PixelUtil.dpToPx(this, 5);
            int h = PixelUtil.dpToPx(this, 5);
            int m = PixelUtil.dpToPx(this, 3);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(w,h);
            LLParams.setMargins(m,m,m,m);
            LL.setLayoutParams(LLParams);
            LL.setGravity(Gravity.CENTER);
            LL.setBackgroundColor(mColors[i]);
            descView.addView(LL);

            TextView TV = new TextView(this);
            TV.setText(mParties[i]);
            TV.setTextSize(8f);
            LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LLParams.setMargins(0,0,PixelUtil.dpToPx(this,5),0);
            TV.setLayoutParams(LLParams);
            descView.addView(TV);
        }

    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }
    @Override
    public void onNothingSelected() {
    }

    @Override
    public void onListItemClick(String memberID) {

    }
}

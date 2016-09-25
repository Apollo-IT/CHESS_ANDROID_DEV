package com.app.hrms.ui.home.subordinate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItem;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItemsAdapter;
import com.app.hrms.ui.home.subordinate.adapter.SubordinateInfoAdapter;

import java.util.ArrayList;

public class ChartDetailListActivity extends AppCompatActivity {
    protected ListView listView;
    protected ArrayList<ChartDetailItem> list;
    protected ChartDetailItemsAdapter adapter;

    /***********************************************************************************************
     *                                      On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_detail_list);

        listView = (ListView)findViewById(R.id.listview);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /***********************************************************************************************
     *                                      Set Title
     * @param title
     */
    protected void setTitleText(String title){
        ((TextView)findViewById(R.id.txtTitle)).setText(title);
    }
    /***********************************************************************************************
     *                                      Set Table Headers
     */
    protected void setTableHeaders(String title1, String title2, String title3, String title4){
        ((TextView)findViewById(R.id.title1_txt)).setText(title1);
        ((TextView)findViewById(R.id.title2_txt)).setText(title2);
        ((TextView)findViewById(R.id.title3_txt)).setText(title3);
        ((TextView)findViewById(R.id.title4_txt)).setText(title4);
    }
}

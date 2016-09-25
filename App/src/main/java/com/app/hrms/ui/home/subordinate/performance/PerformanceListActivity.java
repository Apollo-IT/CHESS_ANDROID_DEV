package com.app.hrms.ui.home.subordinate.performance;

import android.os.Bundle;

import com.app.hrms.R;
import com.app.hrms.model.PA1012;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.subordinate.ChartDetailListActivity;
import com.app.hrms.ui.home.subordinate.MySubordinateActivity;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItem;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItemsAdapter;

import java.sql.Date;
import java.util.ArrayList;

public class PerformanceListActivity extends ChartDetailListActivity
{
    public static String title;
    public static ArrayList<PA1012>data = new ArrayList<>();

    /***********************************************************************************************
     *                                      On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText(title);
        setTableHeaders("姓名","部门","期间","分数");
        loadData();
    }
    /***********************************************************************************************
     *                                      Load Data
     */
    private void loadData(){
        list = new ArrayList<>();
        for(int i=0; i<data.size(); i++){
            PA1012 info = data.get(i);
            ChartDetailItem item = new ChartDetailItem();
            SubordinateInfo subordinateInfo = MySubordinateActivity.getSubordinateByID(info.PERNR);
            if(subordinateInfo!=null){
                item.item1 = subordinateInfo.name;
                item.item2 = subordinateInfo.orgname;
            }else{
                item.item1 = info.NACHN;
                item.item2 = "";
            }
            item.item3 = (new Date(System.currentTimeMillis())).toString().substring(0,7);
            item.item4 = String.valueOf(info.PEFSC);
            list.add(item);
        }
        adapter = new ChartDetailItemsAdapter(this, R.layout.list_chart_detail_item, list);
        listView.setAdapter(adapter);
    }
}


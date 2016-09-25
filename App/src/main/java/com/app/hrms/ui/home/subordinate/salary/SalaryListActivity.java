package com.app.hrms.ui.home.subordinate.salary;

import android.os.Bundle;

import com.app.hrms.R;
import com.app.hrms.model.PT100x;
import com.app.hrms.model.SalaryInfo;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.subordinate.ChartDetailListActivity;
import com.app.hrms.ui.home.subordinate.MySubordinateActivity;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItem;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItemsAdapter;

import java.util.ArrayList;

public class SalaryListActivity extends ChartDetailListActivity
{
    public static String title;
    public static ArrayList<SalaryInfo>data = new ArrayList<>();

    /***********************************************************************************************
     *                                      On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText(title);
        setTableHeaders("姓名","月份","应发工资","实发工资");
        loadData();
    }
    /***********************************************************************************************
     *                                      Load Data
     */
    private void loadData(){
        list = new ArrayList<>();
        for(int i=0; i<data.size(); i++){
            SalaryInfo info = data.get(i);
            ChartDetailItem item = new ChartDetailItem();
            item.item1 = MySubordinateActivity.getSubordinateNameByID(info.getPernr());
            item.item2 = info.getPaydate();
            item.item3 = String.valueOf(info.getTotal_salary());
            item.item4 = String.valueOf(info.getTotal_rsalary());
            list.add(item);
        }
        adapter = new ChartDetailItemsAdapter(this, R.layout.list_chart_detail_item, list);
        listView.setAdapter(adapter);
    }
}


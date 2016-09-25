package com.app.hrms.ui.home.subordinate.attendance;

import android.os.Bundle;

import com.app.hrms.R;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.PT100x;
import com.app.hrms.model.SubordinateInfo;
import com.app.hrms.ui.home.subordinate.ChartDetailListActivity;
import com.app.hrms.ui.home.subordinate.MySubordinateActivity;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItem;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItemsAdapter;

import java.util.ArrayList;

public class AttendanceListActivity extends ChartDetailListActivity
{
    public static String title;
    public static ArrayList<PT100x>data = new ArrayList<>();

    /***********************************************************************************************
     *                                      On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText(title);
        setTableHeaders("姓名","部门","时间","时数");
        loadData();
    }
    /***********************************************************************************************
     *                                      Load Data
     */
    private void loadData(){
        list = new ArrayList<>();
        for(int i=0; i<data.size(); i++){
            PT100x info = data.get(i);
            if(info.getTime()>0.001){
                ChartDetailItem item = new ChartDetailItem();
                SubordinateInfo subordinateInfo = MySubordinateActivity.getSubordinateByID(info.PERNR);
                if(subordinateInfo!=null){
                    item.item1 = subordinateInfo.name;
                    item.item2 = subordinateInfo.orgname;
                }else{
                    item.item1 = info.PERNR;
                    item.item2 = "";
                }
                item.item3 = info.getDate();
                item.item4 = String.valueOf(info.getTime());
                list.add(item);
            }
        }
        adapter = new ChartDetailItemsAdapter(this, R.layout.list_chart_detail_item, list);
        listView.setAdapter(adapter);
    }
}


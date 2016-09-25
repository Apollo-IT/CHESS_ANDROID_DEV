package com.app.hrms.ui.home.subordinate.academic;

import android.content.Intent;
import android.os.Bundle;

import com.app.hrms.R;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.ui.home.subordinate.ChartDetailListActivity;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItem;
import com.app.hrms.ui.home.subordinate.adapter.ChartDetailItemsAdapter;

import java.util.ArrayList;

public class AcademicListActivity extends ChartDetailListActivity
{
    public static String title;
    public static ArrayList<EducationInfo>data = new ArrayList<>();

    /***********************************************************************************************
     *                                      On Create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText(title);
        setTableHeaders("姓名","毕业时间","毕业学校","学历");
        loadData();
    }
    /***********************************************************************************************
     *                                      Load Data
     */
    private void loadData(){
        list = new ArrayList<>();
        for(int i=0; i<data.size(); i++){
            EducationInfo info = data.get(i);
            ChartDetailItem item = new ChartDetailItem();
            item.item1 = info.getPernr();
            item.item2 = info.getEndda();
            item.item3 = info.getInsti();
            item.item4 = info.getEtypename();
            list.add(item);
        }
        adapter = new ChartDetailItemsAdapter(this, R.layout.list_chart_detail_item, list);
        listView.setAdapter(adapter);
    }
}

package com.app.hrms.ui.home.application;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.model.WorkFlow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AppealDetailView extends LinearLayout {
    private Context context;
    public AppealDetailView(Context context) {
        super(context);
        this.context = context;
    }

    public AppealDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public AppealDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    public void init(JSONObject jsonObject, int tab){
        try{

            JSONObject workFlow = jsonObject.getJSONObject("workFlow");
            int type = workFlow.getInt("type");
            View view = null;
            switch (type){
                case 1:view = show_01(jsonObject); break;
                case 2:view = show_02(jsonObject); break;
                case 3:view = show_03(jsonObject); break;
                case 4:view = show_04(jsonObject); break;
                case 5:view = show_05(jsonObject); break;
            }
            if(view != null){
//                if(tab==1){
//                    TextView textView = (TextView)view.findViewById(R.id.approver_label);
//                    textView.setText("申请人");
//                }else{
//                    TextView textView = (TextView)view.findViewById(R.id.approver_label);
//                    textView.setText("审批人");
//                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    //--------------------------------------------------------------------- 01
    private View show_01(JSONObject jsonObject) throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.view_appeal_01, this, true);

        JSONObject workFlow = jsonObject.getJSONObject("workFlow");
        TextView textView = (TextView)view.findViewById(R.id.editName);
        textView.setText(workFlow.getString("name"));
        textView = (TextView)view.findViewById(R.id.editDescription);
        textView.setText(workFlow.getString("description"));

        textView = (TextView)view.findViewById(R.id.approver_txt);
        textView.setText(workFlow.getString("nachn"));

        return view;
    }
    //--------------------------------------------------------------------- 02
    private View show_02(JSONObject jsonObject)throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.view_appeal_02, this, true);

        JSONObject workFlow = jsonObject.getJSONObject("workFlow");
        JSONObject pt1001 = jsonObject.getJSONObject("pt1001");
        HashMap<String, String>typeMap = new HashMap<>();
        JSONArray jsonArray = jsonObject.getJSONObject("paramMap").getJSONArray("par035");
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject item = jsonArray.getJSONObject(i);
            typeMap.put(item.getString("paraValue"), item.getString("paraName"));
        }
        TextView textView = (TextView)view.findViewById(R.id.name_txt);
        textView.setText(workFlow.getString("name"));

        textView = (TextView)view.findViewById(R.id.type_txt);
        textView.setText(typeMap.get(pt1001.getString("abtyp")));

        textView = (TextView)view.findViewById(R.id.begda_txt);
        textView.setText(pt1001.getString("begda"));

        textView = (TextView)view.findViewById(R.id.endda_txt);
        textView.setText(pt1001.getString("endda"));

        textView = (TextView)view.findViewById(R.id.abday_txt);
        textView.setText(pt1001.getString("abday"));

        textView = (TextView)view.findViewById(R.id.abtim_txt);
        textView.setText(pt1001.getString("abtim"));

        textView = (TextView)view.findViewById(R.id.abren_txt);
        textView.setText(pt1001.getString("abren"));

        textView = (TextView)view.findViewById(R.id.approver_txt);
        textView.setText(workFlow.getString("nachn"));

        return view;
    }
    //--------------------------------------------------------------------- 03
    private View show_03(JSONObject jsonObject)throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.view_appeal_03, this, true);

        JSONObject workFlow = jsonObject.getJSONObject("workFlow");
        JSONObject pt1002 = jsonObject.getJSONObject("pt1002");

        TextView textView = (TextView)view.findViewById(R.id.name_txt);
        textView.setText(workFlow.getString("name"));

        textView = (TextView)view.findViewById(R.id.begda_txt);
        textView.setText(pt1002.getString("begda"));

        textView = (TextView)view.findViewById(R.id.endda_txt);
        textView.setText(pt1002.getString("endda"));

        textView = (TextView)view.findViewById(R.id.trday_txt);
        textView.setText(pt1002.getString("trday"));

        textView = (TextView)view.findViewById(R.id.trtim_txt);
        textView.setText(pt1002.getString("trtim"));

        textView = (TextView)view.findViewById(R.id.trsta_txt);
        textView.setText(pt1002.getString("trsta"));

        textView = (TextView)view.findViewById(R.id.trdes_txt);
        textView.setText(pt1002.getString("trdes"));

        textView = (TextView)view.findViewById(R.id.trbud_txt);
        textView.setText(pt1002.getString("trbud"));

        textView = (TextView)view.findViewById(R.id.trren_txt);
        textView.setText(pt1002.getString("trren"));

        textView = (TextView)view.findViewById(R.id.approver_txt);
        textView.setText(workFlow.getString("nachn"));

        return view;
    }
    //--------------------------------------------------------------------- 04
    private View show_04(JSONObject jsonObject)throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.view_appeal_04, this, true);

        JSONObject workFlow = jsonObject.getJSONObject("workFlow");
        JSONObject pt1003 = jsonObject.getJSONObject("pt1003");
        HashMap<String, String>typeMap = new HashMap<>();
        JSONArray jsonArray = jsonObject.getJSONObject("paramMap").getJSONArray("par036");
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject item = jsonArray.getJSONObject(i);
            typeMap.put(item.getString("paraValue"), item.getString("paraName"));
        }
        TextView textView = (TextView)view.findViewById(R.id.name_txt);
        textView.setText(workFlow.getString("name"));

        textView = (TextView)view.findViewById(R.id.type_txt);
        textView.setText(typeMap.get(pt1003.getString("ottyp")));

        textView = (TextView)view.findViewById(R.id.begda_txt);
        textView.setText(pt1003.getString("begda"));

        textView = (TextView)view.findViewById(R.id.endda_txt);
        textView.setText(pt1003.getString("endda"));

        textView = (TextView)view.findViewById(R.id.otday_txt);
        textView.setText(pt1003.getString("otday"));

        textView = (TextView)view.findViewById(R.id.ottim_txt);
        textView.setText(pt1003.getString("ottim"));

        textView = (TextView)view.findViewById(R.id.otren_txt);
        textView.setText(pt1003.getString("otren"));

        textView = (TextView)view.findViewById(R.id.approver_txt);
        textView.setText(workFlow.getString("nachn"));

        return view;
    }
    //--------------------------------------------------------------------- 05
    private View show_05(JSONObject jsonObject)throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.view_appeal_05, this, true);

        JSONObject workFlow = jsonObject.getJSONObject("workFlow");
        JSONObject pt1005 = jsonObject.getJSONObject("pt1005");

        TextView textView = (TextView)view.findViewById(R.id.name_txt);
        textView.setText(workFlow.getString("name"));

        textView = (TextView)view.findViewById(R.id.cloda_txt);
        textView.setText(pt1005.getString("cloda"));

        textView = (TextView)view.findViewById(R.id.oloin_txt);
        textView.setText(pt1005.getString("oloin"));

        textView = (TextView)view.findViewById(R.id.mloin_txt);
        textView.setText(pt1005.getString("mloin"));

        textView = (TextView)view.findViewById(R.id.oinad_txt);
        textView.setText(pt1005.getString("oinad"));

        textView = (TextView)view.findViewById(R.id.minad_txt);
        textView.setText(pt1005.getString("minad"));

        textView = (TextView)view.findViewById(R.id.oloou_txt);
        textView.setText(pt1005.getString("oloou"));

        textView = (TextView)view.findViewById(R.id.mloou_txt);
        textView.setText(pt1005.getString("mloou"));

        textView = (TextView)view.findViewById(R.id.oouad_txt);
        textView.setText(pt1005.getString("oouad"));

        textView = (TextView)view.findViewById(R.id.mouad_txt);
        textView.setText(pt1005.getString("mouad"));

        textView = (TextView)view.findViewById(R.id.description_txt);
        textView.setText(workFlow.getString("description"));

        textView = (TextView)view.findViewById(R.id.approver_txt);
        textView.setText(workFlow.getString("nachn"));

        return view;
    }

}

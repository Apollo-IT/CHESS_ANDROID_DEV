package com.app.hrms.ui.home.resume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.AppCookie;
import com.app.hrms.model.EventInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.ui.home.resume.adapter.EventAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBegda;
    private TextView txtMassn;
    private EditText editMassg;
    private EditText editEstua;

    private Map<String, List<ParamModel>> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personevent);

        paramMap = (HashMap)getIntent().getExtras().getSerializable("paramMap");

        txtBegda = (TextView)findViewById(R.id.txtBegda);
        txtMassn = (TextView)findViewById(R.id.txtMassn);
        editMassg = (EditText)findViewById(R.id.editMassg);
        editEstua = (EditText)findViewById(R.id.editEstua);

        txtBegda.setOnClickListener(this);
        txtMassn.setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.txtSave).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(EventAddActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtBegda: {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtBegda.setText(y + "-" + (m + 1) + "-" + d);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtMassn: {
                final String[] massnArray = new String[paramMap.get("par002").size()];
                final String[] massnValArray = new String[paramMap.get("par002").size()];
                int i = 0;
                for (ParamModel param : paramMap.get("par002")) {
                    massnArray[i] = param.getParamName();
                    massnValArray[i] = param.getParamValue();
                    i++;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(massnArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtMassn.setText(massnArray[which]);
                        txtMassn.setTag(massnValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.txtSave:
                saveEventInfo();
        }
    }

    public void saveEventInfo() {
        EventInfo event = new EventInfo();
        event.setPernr(AppCookie.getInstance().getCurrentUser().getPernr());
        event.setBegda(txtBegda.getText().toString());
        event.setEstua(editEstua.getText().toString());
        event.setMassg(editMassg.getText().toString());
        event.setMassn(txtMassn.getTag() == null ? "" : (String) txtMassn.getTag());

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().saveEventInfo(this, event, new ResumeHelper.SaveCallback() {
            @Override
            public void onSuccess() {
                hud.showSuccessWithStatus("Success!");
                finish();
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Fail!");
            }
        });

    }
}



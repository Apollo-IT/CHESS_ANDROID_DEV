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
import com.app.hrms.model.ContractInfo;
import com.app.hrms.model.ParamModel;
import com.app.hrms.ui.home.resume.adapter.ContractAdapter;
import com.app.hrms.utils.Utils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.garymansell.SweetAlert.SweetAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContractAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBegda;
    private TextView txtCttyp;
    private EditText editPrbzt;
    private TextView txtPrbeh;
    private TextView txtCtedt;
    private EditText editCtnum;
    private TextView txtSidat;
    private TextView txtCtsel;

    private Map<String, List<ParamModel>> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contract);

        paramMap = (HashMap)getIntent().getExtras().getSerializable("paramMap");

        txtBegda = (TextView)findViewById(R.id.txtBegda);
        txtCttyp = (TextView)findViewById(R.id.txtCttyp);
        editPrbzt = (EditText)findViewById(R.id.editPrbzt);
        txtPrbeh = (TextView)findViewById(R.id.txtPrbeh);
        txtCtedt = (TextView)findViewById(R.id.txtCtedt);
        editCtnum = (EditText)findViewById(R.id.editCtnum);
        txtSidat = (TextView)findViewById(R.id.txtSidat);
        txtCtsel = (TextView)findViewById(R.id.txtCtsel);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.txtSave).setOnClickListener(this);
        txtBegda.setOnClickListener(this);
        txtCttyp.setOnClickListener(this);
        txtPrbeh.setOnClickListener(this);
        txtCtedt.setOnClickListener(this);
        txtSidat.setOnClickListener(this);
        txtCtsel.setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("新增合同信息");
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(ContractAddActivity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.txtBegda:
            {
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
            case R.id.txtCttyp: {
                final String[] cttypArray = new String[paramMap.get("par026").size()];
                final String[] cttypValArray = new String[paramMap.get("par026").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par026")) {
                    cttypArray[i] = param.getParamName();
                    cttypValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(cttypArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtCttyp.setText(cttypArray[which]);
                        txtCttyp.setTag(cttypValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtPrbeh: {
                final String[] prbehArray = new String[paramMap.get("par049").size()];
                final String[] prebhValArray = new String[paramMap.get("par049").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par049")) {
                    prbehArray[i] = param.getParamName();
                    prebhValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(prbehArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtPrbeh.setText(prbehArray[which]);
                        txtPrbeh.setTag(prebhValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtCtedt:
            {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtCtedt.setText(y + "-" + (m + 1) + "-" + d);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtSidat:
            {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int y, int m, int d) {
                                txtSidat.setText(y + "-" + (m + 1) + "-" + d);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
                break;
            case R.id.txtCtsel: {
                final String[] ctselArray = new String[paramMap.get("par027").size()];
                final String[] ctselValArray = new String[paramMap.get("par027").size()];
                int i = 0;
                for (ParamModel param: paramMap.get("par027")) {
                    ctselArray[i] = param.getParamName();
                    ctselValArray[i] = param.getParamValue();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(ctselArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtCtsel.setText(ctselArray[which]);
                        txtCtsel.setTag(ctselValArray[which]);
                    }
                });
                builder.show();
            }
                break;
            case R.id.txtSave:
                saveContract();
                break;
        }
    }

    public void saveContract() {

        String begda = txtBegda.getText().toString();
        if ("".equals(begda)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("开始日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String cttyp = txtCttyp.getTag() == null ? "" : (String) txtCttyp.getTag();
        if ("".equals(cttyp)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("合同类型不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String prbzt = editPrbzt.getText().toString().trim();
        if ("".equals(prbzt)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("试用期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String prbeh = txtPrbeh.getTag() == null ? "" : (String) txtPrbeh.getTag();
        if ("".equals(prbeh)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("试用期单位不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String ctedt = txtCtedt.getText().toString();
        if ("".equals(ctedt)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("试用期截止日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String ctnum = editCtnum.getText().toString().trim();
        if ("".equals(ctnum)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("合同编号不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String sidat = txtSidat.getText().toString();
        if ("".equals(sidat)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("合同签订日期不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        String ctsel = txtCtsel.getTag() == null ? "" : (String) txtCtsel.getTag();
        if ("".equals(ctsel)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误")
                    .setContentText("合同密级不能为空!")
                    .setConfirmButtonColor(SweetAlertDialog.BLACK)
                    .setConfirmText("确认")
                    .show();
            return;
        }

        ContractInfo contract = new ContractInfo();
        contract.setBegda(txtBegda.getText().toString());
        contract.setCttyp(txtCttyp.getTag() == null ? "" : (String) txtCttyp.getTag());
        contract.setPrbzt(editPrbzt.getText().toString());
        contract.setPrbeh(txtPrbeh.getTag() == null ? "" : (String) txtPrbeh.getTag());
        contract.setCtedt(txtCtedt.getText().toString());
        contract.setCtnum(editCtnum.getText().toString());
        contract.setSidat(txtSidat.getText().toString());
        contract.setCtsel(txtCtsel.getTag() == null ? "" : (String) txtCtsel.getTag());

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().saveContractInfo(this, contract, new ResumeHelper.SaveCallback() {
            @Override
            public void onSuccess() {
                hud.showSuccessWithStatus("Success!");
                onBackPressed();
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Failed");
            }
        });
    }
}

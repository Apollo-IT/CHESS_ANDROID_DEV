package com.app.hrms.ui.home.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;
import com.app.hrms.UserSetBaseActivity;
import com.app.hrms.helper.ImageHelper;
import com.app.hrms.helper.ResumeHelper;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ParamModel;
import com.app.hrms.utils.Urls;
import com.app.hrms.widget.RoundedImageView;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonBaseActivity extends UserSetBaseActivity implements View.OnClickListener {

    public static final int PICK_IMAGE_REQUEST = 1000;

    private RoundedImageView imgAvatar;
    private TextView txtPernr;
    private TextView editNachn;
    private TextView txtPlans;
    private TextView txtOrgeh;
    private TextView txtBukrs;
    private TextView txtKostl;
    private TextView editWerks;
    private TextView editBtrtl;
    private TextView txtPersg;
    private TextView txtPersk;
    private TextView txtEndat;
    private TextView txtJwdat;
    private TextView txtGesch;
    private TextView editVorna;
    private TextView editPerid;
    private TextView txtGbdat;
    private TextView txtNatio;
    private TextView txtRacky;
    private TextView editGbdep;
    private TextView editGbort;
    private TextView txtFatxt;
    private TextView txtPcode;

    private MemberModel member;
    private Map<String, List<ParamModel>> paramMap;
    private List<ParamModel> countryList;
    private List<ParamModel> nationalList;

    private boolean isEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_baseinfo);

        imgAvatar = (RoundedImageView)findViewById(R.id.imgAvatar);
        txtPernr = (TextView)findViewById(R.id.txtPernr);
        editNachn = (TextView)findViewById(R.id.editNachn);
        txtPlans = (TextView)findViewById(R.id.txtPlans);
        txtOrgeh = (TextView)findViewById(R.id.txtOrgeh);
        txtBukrs = (TextView)findViewById(R.id.txtBukrs);
        txtKostl = (TextView)findViewById(R.id.txtKostl);
        editWerks = (TextView)findViewById(R.id.editWerks);
        editBtrtl = (TextView)findViewById(R.id.editBtrtl);
        txtPersg = (TextView)findViewById(R.id.txtPersg);
        txtPersk = (TextView)findViewById(R.id.txtPersk);
        txtEndat = (TextView)findViewById(R.id.txtEndat);
        txtJwdat = (TextView)findViewById(R.id.txtJwdat);
        txtGesch = (TextView)findViewById(R.id.txtGesch);
        editVorna = (TextView)findViewById(R.id.editVorna);
        editPerid = (TextView)findViewById(R.id.editPerid);
        txtGbdat = (TextView)findViewById(R.id.txtGbdat);
        txtNatio = (TextView)findViewById(R.id.txtNatio);
        txtRacky = (TextView)findViewById(R.id.txtRacky);
        editGbdep = (TextView)findViewById(R.id.editGbdep);
        editGbort = (TextView)findViewById(R.id.editGbort);
        txtFatxt = (TextView)findViewById(R.id.txtFatxt);
        txtPcode = (TextView)findViewById(R.id.txtPcode);

        txtPersg.setOnClickListener(this);
        txtPersk.setOnClickListener(this);
        txtGesch.setOnClickListener(this);
        txtFatxt.setOnClickListener(this);
        txtPcode.setOnClickListener(this);
        txtNatio.setOnClickListener(this);
        txtRacky.setOnClickListener(this);
        txtEndat.setOnClickListener(this);
        txtJwdat.setOnClickListener(this);
        txtGbdat.setOnClickListener(this);

        findViewById(R.id.btnBack).setOnClickListener(this);

        imgAvatar.setCornerRadius(10.0f);
        imgAvatar.setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText("基体信息");

        loadPersonBaseInfo();
    }

    public void loadPersonBaseInfo() {

        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
        ResumeHelper.getInstance().getPersonBaseInfo(this, currentUser.getPernr(), new ResumeHelper.PersonBaseInfoCallback() {
            @Override
            public void onSuccess(MemberModel member, Map<String, List<ParamModel>> paramMap, List<ParamModel> countryList, List<ParamModel> nationalList) {
                hud.dismiss();

                PersonBaseActivity.this.member = member;
                PersonBaseActivity.this.paramMap = paramMap;
                PersonBaseActivity.this.countryList = countryList;
                PersonBaseActivity.this.nationalList = nationalList;

                txtPernr.setText(member.getPernr());
                txtPlans.setText(member.getPlansname());
                txtOrgeh.setText(member.getOrgehname());
                editNachn.setText(member.getNachn());
                txtBukrs.setText(member.getConame());
                txtKostl.setText(member.getKostl());
                editWerks.setText(member.getWerks());
                editBtrtl.setText(member.getBtrtl());

                for (ParamModel param: paramMap.get("par037")) {
                    if (param.getParamValue().equals(member.getPersg())) {
                        txtPersg.setText(param.getParamName());
                        txtPersg.setTag(member.getPersg());
                        break;
                    }
                }

                List<ParamModel> _list = new ArrayList<ParamModel>();
                if ("01".equals(member.getPersg())) {
                    _list = paramMap.get("par038");
                } else if ("02".equals(member.getPersg())) {
                    _list = paramMap.get("par039");
                } else if("03".equals(member.getPersg())) {
                    _list = paramMap.get("par040");
                }

                for (ParamModel param: _list) {
                    if (param.getParamValue().equals(member.getPersk())) {
                        txtPersk.setText(param.getParamName());
                        txtPersk.setTag(param.getParamValue());
                        break;
                    }
                }
                txtEndat.setText(member.getEndat());
                txtJwdat.setText(member.getJwdat());

                for (ParamModel param: paramMap.get("par004")) {
                    if (param.getParamValue().equals(member.getGesch())) {
                        txtGesch.setText(param.getParamName());
                        txtGesch.setTag(param.getParamValue());
                        break;
                    }
                }

                editVorna.setText(member.getVorna());
                editPerid.setText(member.getPerid());
                txtGbdat.setText(member.getGbdat());

                for (ParamModel country: countryList) {
                    if (country.getParamValue().equals(member.getNatio())) {
                        txtNatio.setText(country.getParamName());
                        txtNatio.setTag(country.getParamValue());
                    }
                }

                for (ParamModel nation: nationalList) {
                    if (nation.getParamValue().equals(member.getRacky())) {
                        txtRacky.setText(nation.getParamName());
                        txtRacky.setTag(nation.getParamValue());
                    }
                }

                editGbdep.setText(member.getGbdep());
                editGbort.setText(member.getGbort());

                for (ParamModel param: paramMap.get("par006")) {
                    if (param.getParamValue().equals(member.getFatxt())) {
                        txtFatxt.setText(param.getParamName());
                        txtFatxt.setTag(param.getParamValue());
                        break;
                    }
                }

                for (ParamModel param: paramMap.get("par005")) {
                    if (param.getParamValue().equals(member.getPcode())) {
                        txtPcode.setText(param.getParamName());
                        txtPcode.setTag(param.getParamValue());
                        break;
                    }
                }
                ImageLoader.getInstance().displayImage(Urls.BASE_URL + Urls.PHOTO + member.getPernr(), imgAvatar);
            }

            @Override
            public void onFailed(int retcode) {
                hud.showErrorWithStatus("Fail!");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }

}

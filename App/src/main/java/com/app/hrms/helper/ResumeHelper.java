package com.app.hrms.helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hrms.model.AppCookie;
import com.app.hrms.model.ContractInfo;
import com.app.hrms.model.CourseInfo;
import com.app.hrms.model.EducationInfo;
import com.app.hrms.model.EventInfo;
import com.app.hrms.model.MemberModel;
import com.app.hrms.model.ParamModel;
import com.app.hrms.model.SkillInfo;
import com.app.hrms.model.TitleInfo;
import com.app.hrms.model.TrainInfo;
import com.app.hrms.model.WorkExpInfo;
import com.app.hrms.utils.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResumeHelper {

    private static ResumeHelper instance = null;

    public static ResumeHelper getInstance() {
        if (instance == null) {
            instance = new ResumeHelper();
        }
        return instance;
    }

    public void getContactInfo(Context context, String memberID, final ContractInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.CONTACT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<ContractInfo> contactList = new ArrayList<>();
                        JSONArray contacts = resultJson.getJSONArray("contacts");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject contactJson = contacts.getJSONObject(i);
                            ContractInfo contact = new ContractInfo();
                            contact.setRowId(contactJson.getInt("id"));
                            contact.setPernr(contactJson.getString("pernr"));
                            contact.setBegda(contactJson.getString("begda"));
                            contact.setCttyp(contactJson.getString("cttyp"));
                            contact.setPrbeh(contactJson.getString("prbeh"));
                            contact.setPrbzt(contactJson.getString("prbzt"));
                            contact.setCtedt(contactJson.getString("ctedt"));
                            contact.setCtnum(contactJson.getString("ctnum"));
                            contact.setSidat(contactJson.getString("sidat"));
                            contact.setCtsel(contactJson.getString("ctsel"));
                            contactList.add(contact);
                        }
                        callback.onSuccess(contactList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getEventInfo(Context context, String memberID, final EventInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.EVENT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<EventInfo> eventList = new ArrayList<>();
                        JSONArray events = resultJson.getJSONArray("events");
                        for (int i = 0; i < events.length(); i++) {
                            JSONObject eventJson = events.getJSONObject(i);
                            EventInfo event = new EventInfo();
                            event.setRowId(eventJson.getInt("id"));
                            event.setPernr(eventJson.getString("pernr"));
                            event.setBegda(eventJson.getString("begda"));
                            event.setMassn(eventJson.getString("massn"));
                            event.setMassg(eventJson.getString("massg"));
                            event.setEstua(eventJson.getString("estua"));
                            eventList.add(event);
                        }
                        callback.onSuccess(eventList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getEducationInfo(Context context, String memberID, final EducationInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.EDUCATION_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<EducationInfo> educationList = new ArrayList<>();
                        JSONArray educations = resultJson.getJSONArray("educations");
                        for (int i = 0; i < educations.length(); i++) {
                            JSONObject educationJson = educations.getJSONObject(i);
                            EducationInfo education = new EducationInfo();
                            education.setRowId(educationJson.getInt("id"));
                            education.setPernr(educationJson.getString("pernr"));
                            education.setBegda(educationJson.getString("begda"));
                            education.setEndda(educationJson.getString("endda"));
                            education.setEtype(educationJson.getString("etype"));
                            education.setAcdeg(educationJson.getString("acdeg"));
                            education.setInsti(educationJson.getString("insti"));
                            education.setEtypename(educationJson.getString("etypename"));
                            education.setHetyp(educationJson.getString("hetyp"));
                            education.setHacde(educationJson.getString("hacde"));
                            education.setDacde(educationJson.getString("dacde"));
                            education.setActur(educationJson.getString("actur"));
                            education.setSpec1(educationJson.getString("spec1"));
                            education.setSpec2(educationJson.getString("spec2"));
                            educationList.add(education);
                        }
                        callback.onSuccess(educationList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getWorkExpInfo(Context context, String memberID, final WorkInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.WORKEXP_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<WorkExpInfo> workList = new ArrayList<>();
                        JSONArray works = resultJson.getJSONArray("works");
                        for (int i = 0; i < works.length(); i++) {
                            JSONObject workJson = works.getJSONObject(i);
                            WorkExpInfo work = new WorkExpInfo();
                            work.setRowId(workJson.getInt("id"));
                            work.setPernr(workJson.getString("pernr"));
                            work.setBegda(workJson.getString("begda"));
                            work.setEndda(workJson.getString("endda"));
                            work.setUntur(workJson.getString("untur"));
                            work.setUnnam(workJson.getString("unnam"));
                            work.setUnpos(workJson.getString("unpos"));

                            workList.add(work);
                        }
                        callback.onSuccess(workList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getTrainInfo(Context context, String memberID, final TrainInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.TRAIN_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<TrainInfo> trainList = new ArrayList<>();
                        JSONArray trains = resultJson.getJSONArray("trains");
                        for (int i = 0; i < trains.length(); i++) {
                            JSONObject trainJson = trains.getJSONObject(i);
                            TrainInfo train = new TrainInfo();
                            train.setRowId(trainJson.getInt("id"));
                            train.setPernr(trainJson.getString("pernr"));
                            train.setBegda(trainJson.getString("begda"));
                            train.setEndda(trainJson.getString("endda"));
                            train.setTrype(trainJson.getString("trype"));
                            train.setTrrst(trainJson.getString("trrst"));
                            train.setCouna(trainJson.getString("couna"));
                            trainList.add(train);
                        }
                        callback.onSuccess(trainList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getTitleInfo(Context context, String memberID, final TitleInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.TITLE_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<TitleInfo> titleList = new ArrayList<>();
                        JSONArray titles = resultJson.getJSONArray("titles");
                        for (int i = 0; i < titles.length(); i++) {
                            JSONObject titleJson = titles.getJSONObject(i);
                            TitleInfo title = new TitleInfo();
                            title.setRowId(titleJson.getInt("id"));
                            title.setPernr(titleJson.getString("pernr"));
                            title.setCtdat(titleJson.getString("ctdat"));
                            title.setCtnum(titleJson.getString("ctnum"));
                            title.setCtunt(titleJson.getString("ctunt"));
                            title.setHqflv(titleJson.getString("hqflv"));
                            title.setQflvl(titleJson.getString("qflvl"));
                            title.setQftyp(titleJson.getString("qftyp"));
                            titleList.add(title);
                        }
                        callback.onSuccess(titleList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getSkillList(Context context,String memberID, final SkillInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.SKILL_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<SkillInfo> skillList = new ArrayList<>();
                        JSONArray skills = resultJson.getJSONArray("skills");
                        for (int i = 0; i < skills.length(); i++) {
                            JSONObject skillJson = skills.getJSONObject(i);
                            SkillInfo skill = new SkillInfo();
                            skill.setRowId(skillJson.getInt("id"));
                            skill.setPernr(skillJson.getString("pernr"));
                            skill.setHsklv(skillJson.getString("hsklv"));
                            skill.setSklvl(skillJson.getString("sklvl"));
                            skill.setSktyp(skillJson.getString("sktyp"));
                            skill.setStdat(skillJson.getString("skdat"));
                            skill.setStnum(skillJson.getString("stnum"));
                            skill.setStunt(skillJson.getString("stunt"));
                            skillList.add(skill);
                        }
                        callback.onSuccess(skillList, paramMap);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void getPersonBaseInfo(Context context,String memberID, final PersonBaseInfoCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", memberID);

        client.post(context, Urls.BASE_URL + Urls.PERSON_BASE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        Map<String, List<ParamModel>> paramMap = new HashMap<>();
                        JSONObject paramsJson = resultJson.getJSONObject("params");
                        Iterator<String> iterator = paramsJson.keys();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            JSONArray paramArray = paramsJson.getJSONArray(name);
                            List<ParamModel> paramList = new ArrayList<>();
                            for (int i = 0; i < paramArray.length(); i++) {
                                JSONObject paramJson = paramArray.getJSONObject(i);
                                ParamModel param = new ParamModel();
                                param.setParamName(paramJson.getString("paraName"));
                                param.setParamValue(paramJson.getString("paraValue"));
                                paramList.add(param);
                            }
                            paramMap.put(name, paramList);
                        }

                        List<ParamModel> countryList = new ArrayList<>();
                        JSONArray countryJsonArray = resultJson.getJSONArray("countryList");
                        for(int i = 0; i < countryJsonArray.length(); i++) {
                            JSONObject countryJson = countryJsonArray.getJSONObject(i);
                            ParamModel countryMd = new ParamModel();
                            countryMd.setParamName(countryJson.getString("paraName"));
                            countryMd.setParamValue(countryJson.getString("paraValue"));
                            countryList.add(countryMd);
                        }

                        List<ParamModel> nationalList = new ArrayList<>();
                        JSONArray nationalJsonArray = resultJson.getJSONArray("nationalList");
                        for(int i = 0; i < nationalJsonArray.length(); i++) {
                            JSONObject nationalJson = nationalJsonArray.getJSONObject(i);
                            ParamModel nationalMd = new ParamModel();
                            nationalMd.setParamName(nationalJson.getString("paraName"));
                            nationalMd.setParamValue(nationalJson.getString("paraValue"));
                            nationalList.add(nationalMd);
                        }

                        MemberModel member = new MemberModel();
                        member.setConame(resultJson.getString("coname"));
                        JSONObject pa1001 = resultJson.getJSONObject("pa1001");
                        member.setPernr(pa1001.getString("pernr"));
                        member.setPlans(pa1001.getString("plans"));
                        member.setPlansname(pa1001.getString("plansname"));
                        member.setOrgeh(pa1001.getString("orgeh"));
                        member.setOrgehname(pa1001.getString("orgehname"));
                        member.setBukrs(pa1001.getString("bukrs"));
                        member.setKostl(pa1001.getString("kostl"));
                        member.setWerks(pa1001.getString("werks"));
                        member.setBtrtl(pa1001.getString("btrtl"));
                        member.setPersg(pa1001.getString("persg"));
                        member.setPersk(pa1001.getString("persk"));
                        member.setBegda(pa1001.getString("begda"));

                        JSONObject pa1002 = resultJson.getJSONObject("pa1002");
                        member.setNachn(pa1002.getString("nachn"));
                        member.setEndat(pa1002.getString("endat"));
                        member.setJwdat(pa1002.getString("jwdat"));
                        member.setGesch(pa1002.getString("gesch"));
                        member.setVorna(pa1002.getString("vorna"));
                        member.setPerid(pa1002.getString("perid"));
                        member.setGbdat(pa1002.getString("gbdat"));
                        member.setNatio(pa1002.getString("natio"));
                        member.setRacky(pa1002.getString("racky"));
                        member.setGbdep(pa1002.getString("gbdep"));
                        member.setGbort(pa1002.getString("gbort"));
                        member.setFatxt(pa1002.getString("fatxt"));
                        member.setPcode(pa1002.getString("pcode"));

                        callback.onSuccess(member, paramMap, countryList, nationalList);
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void savePersonBaseInfo(Context context, MemberModel member, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", member.getPernr());
        params.put("BEGDA", member.getBegda());
        params.put("NACHN", member.getNachn());
        params.put("BUKRS", member.getBukrs());
        params.put("KOSTL", member.getKostl());
        params.put("WERKS", member.getWerks());
        params.put("BTRTL", member.getBtrtl());
        params.put("PERSG", member.getPersg());
        params.put("PERSK", member.getPersk());
        params.put("ENDAT", member.getEndat());
        params.put("JWDAT", member.getJwdat());
        params.put("GESCH", member.getGesch());
        params.put("VORNA", member.getVorna());
        params.put("PERID", member.getPerid());
        params.put("GBDAT", member.getGbdat());
        params.put("NATIO", member.getNatio());
        params.put("RACKY", member.getRacky());
        params.put("GBDEP", member.getGbdep());
        params.put("GBORT", member.getGbort());
        params.put("FATXT", member.getFatxt());
        params.put("PCODE", member.getPcode());

        client.post(context, Urls.BASE_URL + Urls.SAVE_PERSON_BASE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void saveContractInfo(Context context, ContractInfo contract, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", AppCookie.getInstance().getCurrentUser().getPernr());
        params.put("BEGDA", contract.getBegda());
        params.put("CTTYP", contract.getCttyp());
        params.put("PRBZT", contract.getPrbzt());
        params.put("PRBEH", contract.getPrbeh());
        params.put("CTEDT", contract.getCtedt());
        params.put("CTNUM", contract.getCtnum());
        params.put("SIDAT", contract.getSidat());
        params.put("CTSEL", contract.getCtsel());

        client.post(context, Urls.BASE_URL + Urls.SAVE_CONTACT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeContractInfo(Context context, ContractInfo contract, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", contract.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_CONTACT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void saveEventInfo(Context context, EventInfo event, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", event.getPernr());
        params.put("BEGDA", event.getBegda());
        params.put("ESTUA", event.getEstua());
        params.put("MASSG", event.getMassg());
        params.put("MASSN", event.getMassn());

        client.post(context, Urls.BASE_URL + Urls.SAVE_EVENT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeEventInfo(Context context, EventInfo event, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", event.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_EVENT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void saveEducationInfo(Context context, EducationInfo education, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", education.getPernr());
        params.put("BEGDA", education.getBegda());
        params.put("ENDDA", education.getEndda());
        params.put("ETYPE", education.getEtype());
        params.put("ACDEG", education.getAcdeg());
        params.put("SPEC1", education.getSpec1());
        params.put("SPEC2", education.getSpec2());
        params.put("ACTUR", education.getActur());
        params.put("DACDE", education.getDacde());
        params.put("HETYP", education.getHetyp());
        params.put("HACDE", education.getHacde());
        params.put("INSTI", education.getInsti());

        client.post(context, Urls.BASE_URL + Urls.SAVE_EDUCATION_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void saveTrainingInfo(Context context, TrainInfo train, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", train.getPernr());
        params.put("BEGDA", train.getBegda());
        params.put("ENDDA", train.getEndda());
        params.put("TRYPE", train.getTrype());
        params.put("COUNA", train.getCouna());
        params.put("TRRST", train.getTrrst());

        client.post(context, Urls.BASE_URL + Urls.SAVE_TRAIN_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeEducationInfo(Context context, EducationInfo education, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", education.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_EDUCATION_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void saveWorkExpInfo(Context context, WorkExpInfo work, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", work.getPernr());
        params.put("BEGDA", work.getBegda());
        params.put("ENDDA", work.getEndda());
        params.put("UNTUR", work.getUntur());
        params.put("UNNAM", work.getUnnam());
        params.put("UNPOS", work.getUnpos());

        client.post(context, Urls.BASE_URL + Urls.SAVE_WORKEXP_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeWorkExpInfo(Context context, WorkExpInfo work, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", work.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_WORKEXP_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeTrainInfo(Context context, TrainInfo train, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", train.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_TRAIN_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void saveTitleInfo(Context context, TitleInfo title, final SaveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("PERNR", title.getPernr());
        params.put("QFTYP", title.getQftyp());
        params.put("QFLVL", title.getQflvl());
        params.put("CTUNT", title.getCtunt());
        params.put("CTNUM", title.getCtnum());
        params.put("CTDAT", title.getCtdat());
        params.put("HQFLV", title.getHqflv());
        client.post(context, Urls.BASE_URL + Urls.SAVE_TITLE_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeTitleInfo(Context context, TitleInfo title, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", title.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_TITLE_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void removeSkillInfo(Context context, TitleInfo title, final RemoveCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ROW_ID", title.getRowId());
        client.post(context, Urls.BASE_URL + Urls.REMOVE_TITLE_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {
                        callback.onSuccess();
                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public void searchCourse(Context context, String begda, String endda, final SearchCourseCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("BEGDA", begda);
        params.put("ENDDA", endda);
        client.post(context, Urls.BASE_URL + Urls.SEARCH_COURSE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] headers, byte[] bytes) {
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));
                    int retcode = resultJson.getInt("success");

                    if (retcode == 1) {

                        List<CourseInfo> courseList = new ArrayList<>();
                        JSONArray courseArray = resultJson.getJSONArray("courseList");
                        for (int i = 0; i < courseArray.length(); i++) {
                            JSONObject courseJson = courseArray.getJSONObject(i);
                            CourseInfo course = new CourseInfo();
                            course.setCouno(courseJson.getString("COUNO"));
                            course.setCouna(courseJson.getString("COUNA"));
                            course.setBegda(courseJson.getString("BEGDA"));
                            course.setEndda(courseJson.getString("ENDDA"));
                            course.setTrype(courseJson.getString("TRYPE"));
                            course.setCoute(courseJson.getString("COUTE"));
                            course.setCouad(courseJson.getString("COUAD"));
                            course.setCoudt(courseJson.getString("COUDT"));
                            courseList.add(course);
                        }
                        callback.onSuccess(courseList);

                    } else {
                        callback.onFailed(retcode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1);
                }
            }

            @Override
            public void onFailure(int status, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onFailed(-1);
            }
        });
    }

    public interface ContractInfoCallback {
        void onSuccess(List<ContractInfo> contractList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface EventInfoCallback {
        void onSuccess(List<EventInfo> eventList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface EducationInfoCallback {
        void onSuccess(List<EducationInfo> educationList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface WorkInfoCallback {
        void onSuccess(List<WorkExpInfo> workExpList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface TrainInfoCallback {
        void onSuccess(List<TrainInfo> trainList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface TitleInfoCallback {
        void onSuccess(List<TitleInfo> titleList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface SkillInfoCallback {
        void onSuccess(List<SkillInfo> skillList, Map<String, List<ParamModel>> paramMap);
        void onFailed(int retcode);
    }

    public interface PersonBaseInfoCallback {
        void onSuccess(MemberModel member, Map<String, List<ParamModel>> paramMap, List<ParamModel> countryList, List<ParamModel> nationalList);
        void onFailed(int retcode);
    }

    public interface  SearchCourseCallback {
        void onSuccess(List<CourseInfo> courseList);
        void onFailed(int retcode);
    }

    public interface SaveCallback {
        void onSuccess();
        void onFailed(int retcode);
    }

    public interface RemoveCallback {
        void onSuccess();
        void onFailed(int retcode);
    }
}

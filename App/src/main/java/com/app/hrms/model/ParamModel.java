package com.app.hrms.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ParamModel implements Serializable {
    private String paramName;
    private String paramValue;

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    public String getParamName() {
        return paramName;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    public String getParamValue() {
        return paramValue;
    }
}

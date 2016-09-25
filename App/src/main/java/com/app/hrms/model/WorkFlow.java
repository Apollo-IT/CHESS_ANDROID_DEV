package com.app.hrms.model;

import java.io.Serializable;

public class WorkFlow implements Serializable {

    private int rowId;
    private String pernr               = "";
    private String createdAt           = "";
    private String type                = "";
    private String name                = "";
    private String description         = "";
    private String status              = "";

    private String nachn               = "";
    private String orgehname           = "";
    private String approver            = "";
    private String read                = "";
    private String state               = "";

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
    public int getRowId() {
        return rowId;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }
    public String getPernr() {
        return pernr;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setStatus(String unpos) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setNachn(String unpos) {
        this.nachn = nachn;
    }
    public String getNachn() {
        return nachn;
    }

    public void setOrgehname(String unpos) {
        this.orgehname = orgehname;
    }
    public String getOrgehname() {
        return orgehname;
    }

    public void setApprover(String unpos) {
        this.approver = approver;
    }
    public String getApprover() {
        return approver;
    }

    public void setRead(String unpos) {
        this.read = read;
    }
    public String getRead() {
        return read;
    }

    public void setState(String unpos) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
}

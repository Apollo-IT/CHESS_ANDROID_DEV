package com.app.hrms.model;

public class SalaryInfo {

    private String pernr            = "";
    private String nachn            = "";
    private String paydate          = "";
    private double total_salary     = 0;
    private double total_rsalary    = 0;
    private double total_tax        = 0;
    private double total_deduction  = 0;
    private double total_insurance  = 0;

    public String getPernr() {
        return pernr;
    }
    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getNachn() {
        return nachn;
    }
    public void setNachn(String nachn) {
        this.nachn = nachn;
    }

    public String getPaydate() {
        return paydate;
    }
    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public double getTotal_salary() {
        return total_salary;
    }
    public void setTotal_salary(double total_salary) {
        this.total_salary = total_salary;
    }

    public double getTotal_rsalary() {
        return total_rsalary;
    }
    public void setTotal_rsalary(double total_rsalary) {
        this.total_rsalary = total_rsalary;
    }

    public double getTotal_tax() {
        return total_tax;
    }
    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public double getTotal_deduction() {
        return total_deduction;
    }
    public void setTotal_deduction(double total_deduction) {
        this.total_deduction = total_deduction;
    }

    public double getTotal_insurance() {
        return total_insurance;
    }
    public void setTotal_insurance(double total_insurance) {
        this.total_insurance = total_insurance;
    }
}

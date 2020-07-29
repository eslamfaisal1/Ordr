package com.android.order.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lead {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("shortage")
    @Expose
    private String shortage;

    @SerializedName("empcode")
    @Expose
    private String empcode = null;

    public Lead() {
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortage() {
        return shortage;
    }

    public void setShortage(String shortage) {
        this.shortage = shortage;
    }
}

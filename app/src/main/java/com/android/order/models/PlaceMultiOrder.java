package com.android.order.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceMultiOrder {

    @SerializedName("Lead")
    @Expose
    private Lead lead;

    @SerializedName("Orders")
    @Expose
    private List<Order> Orders;

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }
}
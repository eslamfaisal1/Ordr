package com.android.order.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceSingleOrder {

    @SerializedName("Lead")
    @Expose
    private Lead lead;
    @SerializedName("Order")
    @Expose
    private Order order;

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
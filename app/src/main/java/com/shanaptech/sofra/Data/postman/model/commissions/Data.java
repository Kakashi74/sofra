
package com.shanaptech.sofra.Data.postman.model.commissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("commissions")
    @Expose
    private double commissions;
    @SerializedName("payments")
    @Expose
    private double payments;
    @SerializedName("net_commissions")
    @Expose
    private double netCommissions;
    @SerializedName("commission")
    @Expose
    private String commission;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double  total) {
        this.total = total;
    }

    public double getCommissions() {
        return commissions;
    }

    public void setCommissions(double commissions) {
        this.commissions = commissions;
    }

    public double getPayments() {
        return payments;
    }

    public void setPayments(double payments) {
        this.payments = payments;
    }

    public double getNetCommissions() {
        return netCommissions;
    }

    public void setNetCommissions(double netCommissions) {
        this.netCommissions = netCommissions;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

}

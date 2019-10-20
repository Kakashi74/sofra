
package com.shanaptech.sofra.Data.postman.model.newOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedClient;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedItem;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedRestaurant;

import java.util.List;

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("payment_method_id")
    @Expose
    private String paymentMethodId;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("delivery_cost")
    @Expose
    private String deliveryCost;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("commission")
    @Expose
    private String commission;
    @SerializedName("net")
    @Expose
    private String net;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("delivered_at")
    @Expose
    private Object deliveredAt;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("items")
    @Expose
    private List<GeneratedItem> items = null;
    @SerializedName("restaurant")
    @Expose
    private GeneratedRestaurant restaurant;
    @SerializedName("client")
    @Expose
    private GeneratedClient client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Object getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Object deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<GeneratedItem> getItems() {
        return items;
    }

    public void setItems(List<GeneratedItem> items) {
        this.items = items;
    }

    public GeneratedRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(GeneratedRestaurant restaurant) {
        this.restaurant = restaurant;
    }

    public GeneratedClient getClient() {
        return client;
    }

    public void setClient(GeneratedClient client) {
        this.client = client;
    }

}

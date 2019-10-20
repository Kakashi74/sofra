
package com.shanaptech.sofra.Data.postman.model.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataNotify {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_en")
    @Expose
    private Object titleEn;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("content_en")
    @Expose
    private Object contentEn;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("notifiable_id")
    @Expose
    private String notifiableId;
    @SerializedName("notifiable_type")
    @Expose
    private String notifiableType;
    @SerializedName("order_id")
    @Expose
    private String orderId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(Object titleEn) {
        this.titleEn = titleEn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getContentEn() {
        return contentEn;
    }

    public void setContentEn(Object contentEn) {
        this.contentEn = contentEn;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNotifiableId() {
        return notifiableId;
    }

    public void setNotifiableId(String notifiableId) {
        this.notifiableId = notifiableId;
    }

    public String getNotifiableType() {
        return notifiableType;
    }

    public void setNotifiableType(String notifiableType) {
        this.notifiableType = notifiableType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}

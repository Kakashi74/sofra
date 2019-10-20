
package com.shanaptech.sofra.Data.postman.model.allRestaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedPivot;

public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pivot")
    @Expose
    private GeneratedPivot pivot;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneratedPivot getPivot() {
        return pivot;
    }

    public void setPivot(GeneratedPivot pivot) {
        this.pivot = pivot;
    }

}

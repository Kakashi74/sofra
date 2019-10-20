package com.shanaptech.sofra.Data.postman.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "itemsOrder")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "idOrder")
    private Integer idOrder;

    @ColumnInfo(name = "idRestaurant")
    private Integer idRestaurant;

    @ColumnInfo(name = "idItems")
    private Integer idItems;

    @ColumnInfo(name = "nameItem")
     private String nameItem;


    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "quantity")
    private Integer quantity;

    @ColumnInfo(name = "ImageUrl")
    private String ImageUrl;


    @ColumnInfo(name = "state")
    private int state;

    @ColumnInfo(name = "price")
    private String price;

    public Item() {
    }

    public Item(Integer idRestaurant, Integer idItems, String nameItem, String description, Integer quantity, String imageUrl, String price , int state) {
        this.idRestaurant = idRestaurant;
        this.idItems = idItems;
        this.nameItem = nameItem;
        this.description = description;
        this.quantity = quantity;
        ImageUrl = imageUrl;
        this.price = price;
        this.state = state;
    }

    public Item(int id, Integer idRestaurant, Integer idItems, String nameItem, String description, Integer quantity, String imageUrl, String price, int state) {
        this.id = id;
         this.idRestaurant = idRestaurant;
        this.idItems = idItems;
        this.nameItem = nameItem;
        this.description = description;
        this.quantity = quantity;
        ImageUrl = imageUrl;
        this.price = price;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nameItem;
    }

    public void setName(String name) {
        this.nameItem = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Integer getIdItems() {
        return idItems;
    }

    public void setIdItems(Integer idItems) {
        this.idItems = idItems;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

package com.shanaptech.sofra.Data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.shanaptech.sofra.Data.postman.model.Item;

import java.util.List;

@Dao
public interface ItemDAO {




    @Query("DELETE FROM itemsOrder ")
    void deleteAll();

    @Query("DELETE FROM itemsOrder where idItems = :idItems")
    void delete(int idItems);


    @Query("SELECT * FROM itemsOrder")
    List<Item> getItems();

    @Query("SELECT * FROM itemsOrder WHERE state = '0'")
    List<Item> getItemByIdRestaurant();


    @Query("SELECT * FROM itemsOrder WHERE idItems = :idItems " )
    Item getItemByIdItems(int idItems);


}

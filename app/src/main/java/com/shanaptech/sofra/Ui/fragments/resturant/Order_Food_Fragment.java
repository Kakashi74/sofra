package com.shanaptech.sofra.Ui.fragments.resturant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;

public class Order_Food_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_food_layout , container , false);

        BottomNavigationViewEx bottomNavigationViewEx = v.findViewById(R.id.bottom_Nview);
        Helper.SetButtomNavigationViewEX(bottomNavigationViewEx);
        v.setBackgroundColor(Color.BLUE);
        return  v;

    }
}

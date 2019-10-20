package com.shanaptech.sofra.Ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Ui.fragments.resturant.Login_Resturant_Fragment;


public class MainActivity extends AppCompatActivity  {

    private Button sellbtn , orderbtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Helper.replace(new Login_Resturant_Fragment(), getSupportFragmentManager(), R.id.fragment_container, null, null);


    }
}


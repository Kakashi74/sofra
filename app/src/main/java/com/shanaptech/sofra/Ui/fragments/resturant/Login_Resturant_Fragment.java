package com.shanaptech.sofra.Ui.fragments.resturant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.shanaptech.sofra.Data.postman.model.loginRestaurant.LoginRestaurant;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Resturant_Fragment extends Fragment {

    TextView textView;
    Button login ;
    EditText Password , Email ;
    CheckBox checkBox ;

    private boolean Checked;
    ApiPostman apiServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment_layout, container , false);

        apiServices = RetrofitClient.getClient().create(ApiPostman.class);

        Password = v.findViewById(R.id.password);
        Email = v.findViewById(R.id.mail);
        checkBox = v.findViewById(R.id.check_box);


        // not registerd
 textView = v.findViewById(R.id.forgettxt);
 textView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Helper.replace(new Register_Resturant_Fragment() ,getActivity().getSupportFragmentManager() ,R.id.fragment_container, null , null);
     }
 });

 // login button
 login= v.findViewById(R.id.login);
 login.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Login();
         Helper.replace(new ListFood_Fragment() ,getActivity().getSupportFragmentManager() ,R.id.fragment_container, null , null);

checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 Checked = isChecked;
             }
         });
     }
 });


        return v ;
    }


    private void Login()
    {
        if (!Password.getText().toString().isEmpty()
                && !Email.getText().toString().isEmpty()) {




           final  String Pass = Password.getText().toString().trim();
           String email = Email.getText().toString().trim();

          apiServices.Login(email , Pass) .enqueue(new Callback<LoginRestaurant>() {
            @Override
            public void onResponse(Call<LoginRestaurant> call, Response<LoginRestaurant> response) {
                if (response.body().getStatus() == 1) {
                    if (checkBox.isChecked())
                    {
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity() , "USER_ID",response.body().getData().getDataUser().getId().toString());
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity() , "USER_NAME",response.body().getData().getDataUser().getName());
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity() , "USER_EMAIL",response.body().getData().getDataUser().getEmail());
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity() , "USER_API_TOKEN",response.body().getData().getApiToken());
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity() , "USER_PHONE",response.body().getData().getDataUser().getPhone());
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity() , "Key_password",Pass);
                        SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "KEY_IS_CHECK_BOX", Checked);

                    }
                    else {

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRestaurant> call, Throwable t) {

            }
        });


        } else {

            if (Email.getText().toString().isEmpty()) {
                Email.setError("Error Occured");
            }
            if (!Password.getText().toString().isEmpty()) {
                Password.setError("Error Occured");
            }
        }

    }



        }


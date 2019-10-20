package com.shanaptech.sofra.Ui.fragments.resturant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.getcategories.Categories;
import com.shanaptech.sofra.Data.postman.model.restaurantRegister.RestauranttRegister;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Utils.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Continue_KFC_Fragment extends Fragment {
    @Nullable
    private View view ;
    private EditText price , priceinSale , phone , whatsNum ;
    private Button Edit ;
    Switch status;
    private int idRegions;
    ApiPostman apiServices;

    private String filePath;
    List<String> items = new ArrayList<>();
    List<Integer> itemsId = new ArrayList<>();
    private String KeyName, KeyEmail, KeyPhone, KeyPassword, KeyPasswordEmphasise;
    private String getAvailability, getDeliveryCost, getMinimumCharger, getWhatsapp;

    MultiSelectionSpinner MealsSpinners;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.continue_kfc_layout , container , false);

        price = view.findViewById(R.id.Oprice);
        priceinSale=view.findViewById(R.id.SPrice);
        phone = view.findViewById(R.id.phoneNum);
        whatsNum = view.findViewById(R.id.what_Num);
        status = view.findViewById(R.id.status);
        Edit = view.findViewById(R.id.edit_btn2);
        MealsSpinners = view.findViewById(R.id.food_spinner);

        Bundle bundle = getArguments();
        if (bundle != null) {
            KeyName = bundle.getString("Name");
            KeyEmail = bundle.getString("Email");
            KeyPhone = bundle.getString("Phone");
            idRegions = bundle.getInt("idRegions");
            KeyPassword = bundle.getString("Password");
            KeyPasswordEmphasise = bundle.getString("PasswordEmphasis");
            filePath = bundle.getString("filePath");
            getAvailability = bundle.getString("getAvailability");
            getMinimumCharger = bundle.getString("getMinimumCharger");
            getDeliveryCost = bundle.getString("getDeliveryCost");
            getWhatsapp = bundle.getString("getWhatsapp");

            price.setText(getMinimumCharger);
            priceinSale.setText(getDeliveryCost);
            whatsNum.setText(getWhatsapp);
            if (getAvailability.equals("open")) {

                status.setChecked(true);
            } else {
                status.setChecked(true);

            }
        }

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getAvailability = "open";
                } else {
                    getAvailability = "Close";
                }
            }
        });


        CategoriesData();
        return  view ;
    }

    private void CategoriesData() {


        apiServices.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                if (response.body().getStatus() == 1) {

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        itemsId.add(response.body().getData().get(i).getId());
                        items.add(response.body().getData().get(i).getName());
                        MealsSpinners.setItems(items, itemsId);
                    }


                } else {
                    Log.d("response", response.body().getMsg());

                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }



    @OnClick(R.id.edit_btn2)
    public void onViewClicked() {
        for (int i = 0; i < MealsSpinners.getSelectedId().size(); i++) {
            Log.d("AsString", String.valueOf(MealsSpinners.getSelectedId().get(i)));
        }
        // save data
        apiServices.getEditRestaurantRegister(
                convertToRequestBody(KeyName)
                , convertToRequestBody(KeyEmail)
                , convertToRequestBody(KeyPassword)
                , convertToRequestBody(KeyPasswordEmphasise)
                , convertToRequestBody(KeyPhone)
                , convertToRequestBody(whatsNum.getText().toString())
                , convertToRequestBody(String.valueOf(idRegions)), MealsSpinners.getSelectedId()
                , convertToRequestBody(price.getText().toString())
                , convertToRequestBody(priceinSale.getText().toString())
                , convertFileToMultipart(filePath, "photo")
                , convertToRequestBody(LoadData((AppCompatActivity)getActivity(), USER_API_TOKEN))
                , convertToRequestBody(getAvailability))
                .enqueue(new Callback<RestauranttRegister>() {
                    @Override
                    public void onResponse(Call<RestauranttRegister> call, Response<RestauranttRegister> response) {


                        Log.d("response", response.body().getMsg());

                        if (response.body().getStatus() == 1) {



                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestauranttRegister> call, Throwable t) {
                        Log.d("onFailure", t.getMessage());

                    }
                });
    }
}

package com.shanaptech.sofra.Ui.fragments.resturant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.commissions.Commissions;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Payment_Fragment extends Fragment {

    ApiPostman ApiServices ;
    private TextView  Sells , Deals , Pay , Charge ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.payment_fragment_layout , container , false);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

        Sells = v.findViewById(R.id.money1);
        Deals = v.findViewById(R.id.money2);
        Pay = v.findViewById(R.id.money3);
        Charge = v.findViewById(R.id.money4);


        PaymentData();
        return v;
    }


    private void PaymentData() {

        ApiServices.myShowOrder(LoadData((AppCompatActivity)getActivity(), USER_API_TOKEN)
        ).enqueue(new Callback<Commissions>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Commissions> call, Response<Commissions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                    Sells.setText("مبيعات المطعم"+response.body().getData().getTotal());
                    Deals.setText("عمولة التطبيق"+response.body().getData().getCommissions());
                    Pay.setText("ماتم سددادة"+response.body().getData().getPayments());

                    double Remaining =   response.body().getData().getCommissions() - response.body().getData().getPayments() ;

                    Charge.setText("المتبقي"+ Remaining);

                } else {
                    Log.d("response", response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<Commissions> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }




}



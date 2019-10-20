package com.shanaptech.sofra.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.shanaptech.sofra.Data.postman.model.notifyToken.NotifyToken;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.clean;

public class CustomDialogCloseClass extends Dialog  {

    public Activity activity;

     @BindView(R.id.closeDialogRestaurantClientBtnNo)
    ImageView closeDialogRestaurantClientBtnNo;
    @BindView(R.id.closeDialogRestaurantClientBtnYes)
    ImageView closeDialogRestaurantClientBtnYes;

    ApiPostman ApiServices;

    public CustomDialogCloseClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.close_layout);
        ButterKnife.bind(this);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

    }



    @OnClick({R.id.closeDialogRestaurantClientBtnNo, R.id.closeDialogRestaurantClientBtnYes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.closeDialogRestaurantClientBtnNo:
                activity.onBackPressed();
                break;
            case R.id.closeDialogRestaurantClientBtnYes:
                RemoveToken();
                clean((AppCompatActivity) activity);
                activity.startActivity(new Intent(getContext(), MainActivity.class));
                break;
            default:
                break;
        }
        dismiss();
    }



    //send Remove Token
    public void RemoveToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Remove Token
        ApiServices.RemoveToken(refreshedToken, LoadData((AppCompatActivity)activity, USER_API_TOKEN)).enqueue(new Callback<NotifyToken>() {
            @Override
            public void onResponse(Call<NotifyToken> call, Response<NotifyToken> response) {

                try {
                    if (response.body().getStatus() == 1) {
//                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<NotifyToken> call, Throwable t) {

            }
        });

    }
}
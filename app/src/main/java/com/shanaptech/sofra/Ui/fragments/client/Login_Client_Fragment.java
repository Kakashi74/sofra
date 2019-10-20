package com.shanaptech.sofra.Ui.fragments.client;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.shanaptech.sofra.Data.postman.model.loginClient.ClientLogin;
import com.shanaptech.sofra.Data.postman.model.notifyToken.NotifyToken;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadBooleanClient;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Login_Client_Fragment extends Fragment {

    private EditText Email, Password;
    private Button Login;
    private CheckBox mcheck;
    private boolean Checked;

    ApiPostman ApiServices;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment_layout, container, false);

        Email = v.findViewById(R.id.mail);
        Password = v.findViewById(R.id.password);
        mcheck = v.findViewById(R.id.check_box);
        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);


        return v;
    }

    private void ClassLogin() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Email.getText().toString().isEmpty()
                        && !Password.getText().toString().isEmpty()) {


                    ApiServices.onLogin(Email.getText().toString()
                            , Password.getText().toString())
                            .enqueue(new Callback<ClientLogin>() {
                                @Override
                                public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {
                                    try {

                                        Toast.makeText(getActivity(), response.body().getMsg() + "dss", Toast.LENGTH_SHORT).show();

                                        if (response.body().getStatus() == 1) {


                                            if (mcheck.isChecked()) {


                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "USER_ID", response.body().getData().getUser().getId().toString());
                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "USER_NAME", response.body().getData().getUser().getName());
                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "USER_EMAIL", response.body().getData().getUser().getEmail());
                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "USER_API_TOKEN", response.body().getData().getApiToken());
                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "USER_PHONE", response.body().getData().getUser().getPhone());
                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "Key_password", Password.getText().toString());
                                                SharedPreferenceManager.SaveData((AppCompatActivity) getActivity(), "KEY_IS_CHECK_BOX", Checked);


                                            } else {

                                            }

                                            Helper.replace(new Client_Home_Fragment(), getActivity().getSupportFragmentManager(), R.id.fragment_container, null, null);


                                           RegisterToken();

                                        }
                                    } catch (Exception e) {

                                        Log.d("error", e.getMessage());

                                    }

                                }

                                @Override
                                public void onFailure(Call<ClientLogin> call, Throwable t) {

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
        });
    }

    private void getDataUserShrPreferences() {

        Email.setText(LoadData((AppCompatActivity) getActivity(), "USER_EMAIL"));
        Password.setText(LoadData((AppCompatActivity) getActivity(), "Key_password"));

        if (LoadBooleanClient((AppCompatActivity) getActivity(), "KEY_IS_CHECK_BOX")) {
            Log.d("response", "true");
            mcheck.setChecked(true);
        } else {
            mcheck.setChecked(false);
            Log.d("response", "false");

        }
    }

    public void RegisterToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("USER_API_TOKEN", refreshedToken);

        ApiServices.RegisterToken(refreshedToken, LoadData((AppCompatActivity) getActivity(), USER_API_TOKEN), "android").enqueue(new Callback<NotifyToken>() {
            @Override
            public void onResponse(Call<NotifyToken> call, Response<NotifyToken> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<NotifyToken> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.addContact.AddContact;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactMeFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.Name)
    EditText Name;
    @BindView(R.id.Email)
    EditText Email;
    @BindView(R.id.Phone)
    EditText Phone;
    @BindView(R.id.Massage)
    EditText Msg;
    @BindView(R.id.BtnSend)
    Button SendBtn;
    private ApiPostman ApiServices;

    private View view;

    private String filePath;
    private String  type = "suggestion";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.contact_me_lauout, container, false);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

        unbinder = ButterKnife.bind(this, view);


        return view;
    }




     private void AddContact() {

        ApiServices.addContact(Name.getText().toString(),
                Email.getText().toString(), Phone.getText().toString()
                , type, Msg.getText().toString()).enqueue(new Callback<AddContact>() {
            @Override
            public void onResponse(Call<AddContact> call, Response<AddContact> response) {
                try {
                    Log.d("response", response.body().getMsg());
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                        clearText();
                    } else {
                        Log.d("response", response.body().getMsg());
                         }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<AddContact> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    private void clearText() {
        Name.getText().clear();
        Email.getText().clear();
        Phone.getText().clear();
        Msg.getText().clear();
     }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.Complaint, R.id.Suggestion, R.id.Enquiry, R.id.BtnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Complaint:
                type = "complaint";
                Toast.makeText(getActivity(),type, Toast.LENGTH_LONG).show();

                break;
            case R.id.Suggestion:
                type = "suggestion";
                Toast.makeText(getActivity(),type, Toast.LENGTH_LONG).show();

                break;
            case R.id.Enquiry:
                type = "inquiry";
                Toast.makeText(getActivity(),type, Toast.LENGTH_LONG).show();

                break;
            case R.id.BtnSend:
                AddContact();
                break;
        }
    }
}



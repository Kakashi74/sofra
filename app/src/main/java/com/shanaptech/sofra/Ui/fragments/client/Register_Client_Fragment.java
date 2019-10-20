package com.shanaptech.sofra.Ui.fragments.client;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedModelSpinner;
import com.shanaptech.sofra.Data.postman.model.cities.GetCities;
import com.shanaptech.sofra.Data.postman.model.clientRegister.ClientRegister;
import com.shanaptech.sofra.Data.postman.model.regions.Regions;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.SpinnerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.shanaptech.sofra.Helper.Helper.checkCorrespondPassword;
import static com.shanaptech.sofra.Helper.Helper.checkLengthPassword;
import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;

public class Register_Client_Fragment extends Fragment {
    @BindView(R.id.profile)
    ImageView profile;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.mail)
    EditText mail;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.regionSpinner)
    Spinner citySpinner;
    @BindView(R.id.regionspinner2)
    Spinner regionspinner;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.ensure_password)
    EditText ensurePassword;
    @BindView(R.id.continues)
    Button continues;
    Unbinder unbinder;

    SpinnerAdapter spinnerAdapter;
    ArrayList<GeneratedModelSpinner> CityList;
    GeneratedModelSpinner cityGeneratedModelSpinner;

    private int idCity;
    private int idRegions;
    private String filePath;

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;
    SpinnerAdapter RegoinAdapter;
    ArrayList<GeneratedModelSpinner> RegionsList;
    GeneratedModelSpinner RegionSpinner;

    ApiPostman ApiServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class)  ;
        RegionsList = new ArrayList<>();
        CityList = new ArrayList<>();
        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getDataCity() {

        ApiServices.getCities().enqueue(new Callback<GetCities>() {
            @Override
            public void onResponse(Call<GetCities> call, Response<GetCities> response) {

                if (response.body().getStatus() == 1) {


                    CityList.clear();
                     CityList.add(new GeneratedModelSpinner(0
                            , "المدينه"));

                    for (int i = 0; i < response.body().getData().getData().size(); i++) {

                        cityGeneratedModelSpinner = new GeneratedModelSpinner(response.body().getData().getData().get(i).getId(),
                                response.body().getData().getData().get(i).getName());

                        CityList.add(cityGeneratedModelSpinner);
                    }

                    spinnerAdapter = new SpinnerAdapter(getActivity(), CityList);
                    citySpinner.setAdapter(spinnerAdapter);
                    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                idCity = CityList.get(position).getId();
                                getDataGetRegions(idCity);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }


            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {

            }
        });
    }

    public void getDataGetRegions(int idCity) {

       ApiServices.getRegions(idCity).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {

                    // clear list
                    RegionsList.clear();
                    // add new data
                    RegionsList.add(new GeneratedModelSpinner(0, "الحي"));

                    for (int i = 0; i < response.body().getDataPagination().getData().size(); i++) {
                        RegionSpinner =
                                new GeneratedModelSpinner(response.body().getDataPagination().getData().get(i).getId(),
                                        response.body().getDataPagination().getData().get(i).getName());
                        RegionsList.add(RegionSpinner);
                    }

                    RegoinAdapter = new SpinnerAdapter(getActivity(), RegionsList);
                    regionspinner.setAdapter(RegoinAdapter);
                    regionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {

                                idRegions = RegionsList.get(position).getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Log.d("response", response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

            //data.getDataCode return the content URI for the selected Image
            Uri uri = data.getData();

            filePath = getRealPathFromURIPath(uri, getActivity());

            // Set the Image in ImageView after decoding the String
            profile.setImageBitmap(BitmapFactory.decodeFile(filePath)
            );

            Toast.makeText(getActivity(), "Something went wrong" + filePath, Toast.LENGTH_LONG).show();

        }

    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public boolean checkPermissionForReadExtertalStorage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result =  ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @OnClick({R.id.profile, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile:
                if (checkPermissionForReadExtertalStorage()) {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);

                } else {
                    try {
                        requestPermissionForReadExtertalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.save:

                if (checkLengthPassword(password.getText().toString())
                        && checkCorrespondPassword(password.getText().toString()
                        ,password.getText().toString())) {

                   ApiServices.addClientRegister(
                            convertToRequestBody(name.getText().toString())
                            , convertToRequestBody(mail.getText().toString())
                            , convertToRequestBody(password.getText().toString())
                            , convertToRequestBody(ensurePassword.getText().toString())
                            , convertToRequestBody(phone.getText().toString())
                            , convertToRequestBody(".")
                            , convertToRequestBody(String.valueOf(idRegions))
                            , convertFileToMultipart(filePath, "profile_image"))
                            .enqueue(new Callback<ClientRegister>() {
                                @Override
                                public void onResponse(Call<ClientRegister> call, Response<ClientRegister> response) {

                                    Log.d("response", response.body().getMsg());

                                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    if (response.body().getStatus() == 1) {


                                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();



                                    } else {


                                    }

                                }

                                @Override
                                public void onFailure(Call<ClientRegister> call, Throwable t) {

                                }
                            });
                } else {


                    if (!checkLengthPassword(password.getText().toString())) {
                       password.setError("No Longer than 6 letters");
                    }
                    if (!checkCorrespondPassword(password.getText().toString(), ensurePassword.getText().toString())) {
                        ensurePassword.setError("no matching");
                    }
                }
                break;
        }
    }
}

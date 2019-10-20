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
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedModelSpinner;
import com.shanaptech.sofra.Data.postman.model.cities.GetCities;
import com.shanaptech.sofra.Data.postman.model.regions.Regions;
import com.shanaptech.sofra.Data.postman.model.updateProfileClient.ProfileClient;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.R;
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
import static com.shanaptech.sofra.Helper.Helper.LodeImageCircle;
import static com.shanaptech.sofra.Helper.Helper.checkCorrespondPassword;
import static com.shanaptech.sofra.Helper.Helper.checkLengthPassword;
import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Update_Profile_Fragment extends Fragment {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;

    Unbinder unbinder;

    ApiPostman ApiServices ;

    SpinnerAdapter spinnerAdapter;
    ArrayList<GeneratedModelSpinner> ArrayListSpinnerCity = new ArrayList<>();
    GeneratedModelSpinner cityGeneratedModelSpinner;

    SpinnerAdapter regionsSpinnerAdapter;
    ArrayList<GeneratedModelSpinner> ArrayListSpinnerRegions = new ArrayList<>();
    GeneratedModelSpinner regionsGeneratedModelSpinner;
    @BindView(R.id.profile_ic)
    ImageView Profile_img;
    @BindView(R.id.EditName)
    TextInputEditText name;
    @BindView(R.id.EditEmail)
    TextInputEditText mail;
    @BindView(R.id.updateProfileFragmentEditPhoneClient)
    TextInputEditText phone;
    @BindView(R.id.CitySpinner)
    Spinner CitySpinner;
    @BindView(R.id.RegionSpinner)
    Spinner RegionSpinner;
    @BindView(R.id.EditPassword)
    TextInputEditText Password;
    @BindView(R.id.EnsurePassowrd)
    TextInputEditText EnsurePassword;
    @BindView(R.id.SaveBtn)
    Button updateProfileFragmentSaveBtnClient;



    private int idCity;
    private int idRegions;
    private String filePath;
    private View view;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_layout_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);
        getProfileClient();

        getDataCity();
        return view;
    }



    private void getProfileClient() {


        ApiServices.getProfileClient(LoadData((AppCompatActivity) getActivity(),USER_API_TOKEN)).enqueue(new Callback<ProfileClient>() {
            @Override
            public void onResponse(Call<ProfileClient> call, Response<ProfileClient> response) {
                try {
                    Log.d("response", response.body().getMsg());

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                        LodeImageCircle(getContext(), response.body().getData().getUser().getPhotoUrl(), Profile_img);

                        name.setText(response.body().getData().getUser().getName());
                        mail.setText(response.body().getData().getUser().getEmail());
                        phone.setText(response.body().getData().getUser().getPhone());
                        Password.setText(LoadData((AppCompatActivity) getActivity(),"Key_password"));
                        EnsurePassword.setText(LoadData((AppCompatActivity) getActivity(),"Key_password"));


                        for (int i = 0; i < ArrayListSpinnerCity.size(); i++) {
                            if (ArrayListSpinnerCity.get(i).getId()== Integer.parseInt(response.body().getData().getUser().getRegion().getCityId())){
                                CitySpinner.setSelection(i);
                            }
                        }

                        for (int i = 0; i < ArrayListSpinnerRegions.size(); i++) {
                            if (ArrayListSpinnerRegions.get(i).getId()== Integer.parseInt(response.body().getData().getUser().getRegionId())){
                                RegionSpinner.setSelection(i);
                            }
                        }



                    } else {
                        Log.d("response", response.body().getMsg());
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ProfileClient> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }


    // get   data cities
    private void getDataCity() {

       ApiServices.getCities().enqueue(new Callback<GetCities>() {
            @Override
            public void onResponse(Call<GetCities> call, Response<GetCities> response) {

                if (response.body().getStatus() == 1) {
                    ArrayListSpinnerCity.clear();
                    ArrayListSpinnerCity.add(new GeneratedModelSpinner(0
                            , "المدينه"));

                    for (int i = 0; i < response.body().getData().getData().size(); i++) {

                        cityGeneratedModelSpinner = new GeneratedModelSpinner(response.body().getData().getData().get(i).getId(),
                                response.body().getData().getData().get(i).getName());

                        ArrayListSpinnerCity.add(cityGeneratedModelSpinner);
                    }

                    spinnerAdapter = new SpinnerAdapter(getContext(), ArrayListSpinnerCity);
                    CitySpinner.setAdapter(spinnerAdapter);
                    CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                idCity = ArrayListSpinnerCity.get(position).getId();
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
                    ArrayListSpinnerRegions.clear();
                    // add new data
                    ArrayListSpinnerRegions.add(new GeneratedModelSpinner(0, "الحي"));

                    for (int i = 0; i < response.body().getDataPagination().getData().size(); i++) {
                        regionsGeneratedModelSpinner =
                                new GeneratedModelSpinner(response.body().getDataPagination().getData().get(i).getId(),
                                        response.body().getDataPagination().getData().get(i).getName());
                        ArrayListSpinnerRegions.add(regionsGeneratedModelSpinner);
                    }

                    regionsSpinnerAdapter = new SpinnerAdapter(getContext(), ArrayListSpinnerRegions);
                    RegionSpinner.setAdapter(regionsSpinnerAdapter);
                    RegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {

                                idRegions = ArrayListSpinnerRegions.get(position).getId();
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
            Profile_img.setImageBitmap(BitmapFactory.decodeFile(filePath)
            );

            Toast.makeText(getContext(), "Something went wrong" + filePath, Toast.LENGTH_LONG).show();

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
            int result = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
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


    @OnClick({R.id.profile_ic, R.id.SaveBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_ic:
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
            case R.id.SaveBtn:

                if (checkLengthPassword(Password.getText().toString())
                        && checkCorrespondPassword(Password.getText().toString()
                        , EnsurePassword.getText().toString())) {


                    ApiServices.editProfileClient( convertToRequestBody(LoadData((AppCompatActivity) getActivity(), USER_API_TOKEN)),
                            convertToRequestBody(name.getText().toString())
                            , convertToRequestBody(mail.getText().toString())
                            , convertToRequestBody(Password.getText().toString())
                            , convertToRequestBody(EnsurePassword.getText().toString())
                            , convertToRequestBody(phone.getText().toString())
                            , convertToRequestBody(".")
                            , convertToRequestBody(String.valueOf(idRegions))
                            , convertFileToMultipart(filePath, "profile_image"))
                            .enqueue(new Callback<ProfileClient>() {
                                @Override
                                public void onResponse(Call<ProfileClient> call, Response<ProfileClient> response) {

                                    try {
                                        Log.d("response", response.body().getMsg());

                                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        if (response.body().getStatus() == 1) {


                                            getActivity().onBackPressed();
                                        } else {
                                        }
                                    }catch (Exception e){
                                        e.getMessage();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ProfileClient> call, Throwable t) {

                                }
                            });
                } else {


                    if (!checkLengthPassword(Password.getText().toString())) {
                        EnsurePassword.setError("No Longet Than 6 Charchterise");
                    }
                    if (!checkCorrespondPassword(Password.getText().toString(), EnsurePassword.getText().toString())) {
                        EnsurePassword.setError("Don't Match");
                    }
                }
                break;
        }
    }
}

package com.shanaptech.sofra.Ui.fragments.resturant;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedModelSpinner;
import com.shanaptech.sofra.Data.postman.model.cities.GetCities;
import com.shanaptech.sofra.Data.postman.model.profile.Profile;
import com.shanaptech.sofra.Data.postman.model.regions.Regions;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.MultiSelectionSpinner;
import com.shanaptech.sofra.Utils.SpinnerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.shanaptech.sofra.Helper.Helper.LodeImageCircle;
import static com.shanaptech.sofra.Helper.Helper.checkCorrespondPassword;
import static com.shanaptech.sofra.Helper.Helper.checkLengthPassword;
import static com.shanaptech.sofra.Helper.Helper.getStartFragments;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Kfc_Fragment extends Fragment {
    private int idCity;
    private int idRegions;

    private String filePath;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;

    SpinnerAdapter spinnerAdapter;
    ArrayList<GeneratedModelSpinner> generatedModelSpinnerArrayListCity;
    GeneratedModelSpinner cityGeneratedModelSpinner;


    SpinnerAdapter regionsSpinnerAdapter;
    ArrayList<GeneratedModelSpinner> generatedModelSpinnerArrayListRegions;
    GeneratedModelSpinner regionsGeneratedModelSpinner;

    ApiPostman ApiServices ;

    MultiSelectionSpinner rSpinner , cSpinner ;

    Button continue_btn ;
    Spinner RegionSpinner , CitySpinner ;

    de.hdodenhof.circleimageview.CircleImageView imageView ;

    private EditText resturantName , mail , phone , password , ensurePassword ;
    Button edit_btn ;
    View view ;
    private String getAvailability,getDeliveryCost,getMinimumCharger,getWhatsapp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kfc_layout , container , false);
        inti();

        getDataCity();

        onClickBtn();

        getDataProfile();
        return  view ;
    }

    private void inti() {


        edit_btn = view.findViewById(R.id.edit_btn2);
        imageView = view.findViewById(R.id.kfc);

        resturantName = view.findViewById(R.id.resturant_name);
        mail = view.findViewById(R.id.resturant_name);
        phone = view.findViewById(R.id.resturant_name);
        password = view.findViewById(R.id.resturant_name);
        ensurePassword = view.findViewById(R.id.resturant_name);



        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

        generatedModelSpinnerArrayListRegions = new ArrayList<>();
        generatedModelSpinnerArrayListCity = new ArrayList<>();
    }


    public void getDataProfile() {

        ApiServices.getProfile(LoadData( (AppCompatActivity) getActivity(), USER_API_TOKEN)).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.d("response", response.body().getMsg());
                Toast.makeText(getContext(),  response.body().getMsg(), Toast.LENGTH_LONG).show();

                if (response.body().getStatus() == 1) {
                    resturantName.setText(response.body().getData().getUser().getName());
                    mail.setText(response.body().getData().getUser().getEmail());
                    phone.setText(response.body().getData().getUser().getPhone());
                    password.setText(LoadData((AppCompatActivity) getActivity(), "Key_password"));
                    ensurePassword.setText(LoadData((AppCompatActivity) getActivity(), "Key_password"));
                    idRegions = Integer.parseInt(response.body().getData().getUser().getRegionId());
                    idCity = Integer.parseInt(response.body().getData().getUser().getRegion().getCityId());
                    getAvailability = response.body().getData().getUser().getAvailability();
                    getDeliveryCost = response.body().getData().getUser().getDeliveryCost();
                    getMinimumCharger = response.body().getData().getUser().getMinimumCharger();
                    getWhatsapp = response.body().getData().getUser().getWhatsapp();

                    LodeImageCircle(getActivity(), response.body().getData().getUser().getPhotoUrl(), imageView );

                    for (int i = 0; i < generatedModelSpinnerArrayListCity.size(); i++) {
                        if (generatedModelSpinnerArrayListCity.get(i).getId() == idCity) {
                            CitySpinner.setSelection(i);
                        }
                    }



                } else {
                    Log.d("response", response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }


    public void getDataGetRegions(int idCity) {

        ApiServices.getRegions(idCity).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {

                    generatedModelSpinnerArrayListRegions.clear();

                    generatedModelSpinnerArrayListRegions.add(new GeneratedModelSpinner(0,
                           "المنطقه"));

                    for (int i = 0; i < response.body().getDataPagination().getData().size(); i++) {
                        regionsGeneratedModelSpinner =
                                new GeneratedModelSpinner(response.body().getDataPagination().getData().get(i).getId(),
                                        response.body().getDataPagination().getData().get(i).getName());
                        generatedModelSpinnerArrayListRegions.add(regionsGeneratedModelSpinner);
                    }

                    regionsSpinnerAdapter = new SpinnerAdapter(getContext(), generatedModelSpinnerArrayListRegions);
                    RegionSpinner.setAdapter(regionsSpinnerAdapter);
                    RegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                idRegions = generatedModelSpinnerArrayListRegions.get(position).getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    for (int i = 0; i < generatedModelSpinnerArrayListRegions.size(); i++) {
                        if (generatedModelSpinnerArrayListRegions.get(i).getId() == idRegions) {
                            rSpinner.setSelection(i);
                        }
                    }

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


    private void getDataCity() {

        ApiServices.getCities().enqueue(new Callback<GetCities>() {
            @Override
            public void onResponse(Call<GetCities> call, Response<GetCities> response) {

                try {
                    if (response.body().getStatus() == 1) {


                        // clear list
                        generatedModelSpinnerArrayListCity.clear();
                        // add new data from list
                        generatedModelSpinnerArrayListCity.add(new GeneratedModelSpinner(0, "المدينه"));

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {

                            cityGeneratedModelSpinner = new GeneratedModelSpinner(response.body().getData().getData().get(i).getId(),
                                    response.body().getData().getData().get(i).getName());

                            generatedModelSpinnerArrayListCity.add(cityGeneratedModelSpinner);
                        }

                        spinnerAdapter = new SpinnerAdapter(getContext(), generatedModelSpinnerArrayListCity);
                        cSpinner.setAdapter(spinnerAdapter);
                        cSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    idCity = generatedModelSpinnerArrayListCity.get(position).getId();
                                    getDataGetRegions(idCity);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });


                    } else {
                        Log.d("response", response.body().getMsg());


                    }
                }catch (Exception e){
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }


    public void onClickBtn() {

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkLengthPassword(password.getText().toString())
                        && checkCorrespondPassword(password.getText().toString()
                        , ensurePassword.getText().toString())) {

                    Bundle bundle = new Bundle();
                    bundle.putString("Name", resturantName.getText().toString());
                    bundle.putString("Email", mail.getText().toString());
                    bundle.putString("Phone", phone.getText().toString());
                    bundle.putInt("idRegions", idRegions);
                    bundle.putString("Password", password.getText().toString());
                    bundle.putString("PasswordEmphasis", ensurePassword.getText().toString());
                    bundle.putString("filePath", filePath);
                    bundle.putString("getAvailability", getAvailability);
                    bundle.putString("getDeliveryCost", getDeliveryCost);
                    bundle.putString("getMinimumCharger", getMinimumCharger);
                    bundle.putString("getWhatsapp", getWhatsapp);


                    // open
                    Fragment fragment = new Continue_KFC_Fragment();
                    fragment.setArguments(bundle);

                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.fragment_container, fragment);
                    }

                } else {
                    if (!checkLengthPassword(password.getText().toString())) {
                        password.setError("لا يجب ان يكون اكبر من ٦ احرف ");
                    }
                    if (!checkCorrespondPassword(password.getText().toString(), ensurePassword.getText().toString())) {
                        ensurePassword.setError("خطا");
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath)
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
}

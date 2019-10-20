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


import com.shanaptech.sofra.Data.postman.model.cities.GetCities;
import com.shanaptech.sofra.Data.postman.model.getcategories.Categories;
import com.shanaptech.sofra.Data.postman.model.restaurantRegister.RestauranttRegister;
import com.shanaptech.sofra.Utils.MultiSelectionSpinner;
import com.shanaptech.sofra.Utils.SpinnerAdapter;

import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedModelSpinner;
import com.shanaptech.sofra.Data.postman.model.regions.Regions;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.shanaptech.sofra.Helper.Helper.checkCorrespondPassword;
import static com.shanaptech.sofra.Helper.Helper.checkLengthPassword;
import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;

public class Register_Resturant_Fragment extends Fragment {


    private Button cont , save;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;



    private Spinner RegionSpinner1 , RegionSpinner2 ;
    SpinnerAdapter spinnerAdapter;
    ArrayList<GeneratedModelSpinner> CitiesList;
    GeneratedModelSpinner cityGeneratedModelSpinner;


    SpinnerAdapter regionsSpinnerAdapter;
    ArrayList<GeneratedModelSpinner> RegionsList;

    GeneratedModelSpinner regionsGeneratedModelSpinner;

    private int idCity;
    private int idRegions;

    private String filePath;
    List<String> items = new ArrayList<>();
    List<Integer> itemsId = new ArrayList<>();


    private ImageView imageView;

    private EditText name , mail , phone , password , ensurepassword , limitPrice , deliveryCoast , whatsNum ;
    private ApiPostman ApiServices ;

    MultiSelectionSpinner MSpinner , CategorySpinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

        RegionSpinner1 = v.findViewById(R.id.regionSpinner);
        RegionSpinner2 =v.findViewById(R.id.regionspinner2);
        CategorySpinner = v.findViewById(R.id.catSpinner);

        name = v.findViewById(R.id.name);
        mail = v.findViewById(R.id.mail);
        phone = v.findViewById(R.id.phone);
        limitPrice = v.findViewById(R.id.limit_time_order);
        deliveryCoast = v.findViewById(R.id.delivery);
        whatsNum = v.findViewById(R.id.whatnum);
        password = v.findViewById(R.id.password);
        ensurepassword = v.findViewById(R.id.ensure_password);


        save = v.findViewById(R.id.save);
        cont = v.findViewById(R.id.continues);


         imageView = v.findViewById(R.id.profile);


        RegionsList = new ArrayList<>();
        CitiesList = new ArrayList<>();

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);



        getCities();

        return v;

    }



    private void getCities() {


        ApiServices.getCities().enqueue(new Callback<GetCities>() {
            @Override
            public void onResponse(Call<GetCities> call, Response<GetCities> response) {

                if (response.body().getStatus() == 1) {


                    CitiesList.clear();

                    CitiesList.add(new GeneratedModelSpinner(0, "المدن"));

                    for (int i = 0; i < response.body().getData().getData().size(); i++) {

                        cityGeneratedModelSpinner = new GeneratedModelSpinner(response.body().getData().getData().get(i).getId(),
                                response.body().getData().getData().get(i).getName());

                        CitiesList.add(cityGeneratedModelSpinner);
                    }

                    spinnerAdapter = new SpinnerAdapter(getActivity(), CitiesList);
                    RegionSpinner1.setAdapter(spinnerAdapter);
                    RegionSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                idCity = CitiesList.get(position).getId();
                                getRegions(idCity);
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
            public void onFailure(Call<GetCities> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }



    public void getRegions(int idCity) {
        ApiServices.getRegions(idCity).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {
                    // clear list regions
                    RegionsList.clear();

                    RegionsList.add(new GeneratedModelSpinner(
                            0, "المناطق"));

                    for (int i = 0; i < response.body().getDataPagination().getData().size(); i++) {
                        regionsGeneratedModelSpinner =
                                new GeneratedModelSpinner(response.body().getDataPagination().getData().get(i).getId(),
                                        response.body().getDataPagination().getData().get(i).getName());
                        RegionsList.add(regionsGeneratedModelSpinner);
                    }

                    regionsSpinnerAdapter = new SpinnerAdapter( getActivity(), RegionsList);
                    RegionSpinner2.setAdapter(regionsSpinnerAdapter);
                    RegionSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void getCategories() {

        ApiServices.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                if (response.body().getStatus() == 1) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        itemsId.add(response.body().getData().get(i).getId());
                        items.add(response.body().getData().get(i).getName());
                        MSpinner.setItems(items, itemsId);
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


    public void onClickBtn() {


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkLengthPassword(password.getText().toString())
                        && checkCorrespondPassword(password.getText().toString()
                        , ensurepassword.getText().toString())) {

                   getActivity().setContentView(R.layout.continue_register_fragment);


                    InitContinues();


                    getCategories();

                } else {
                    if (!checkLengthPassword(password.getText().toString())) {
                        password.setError("يجب ان يكون اطول من ٦ حروف");
                    }
                    if (!checkCorrespondPassword(password.getText().toString(), ensurepassword.getText().toString())) {
                        ensurepassword.setError("غير متطابقان");
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionForReadExtertalStorage()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {


            Uri uri = data.getData();

            filePath = getRealPathFromURIPath(uri, getActivity());


            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath)
            );

            Toast.makeText(getActivity(), "Something went wrong" + filePath, Toast.LENGTH_LONG).show();

        }

    }


    private void InitContinues() {



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < MSpinner.getSelectedId().size(); i++) {
                    Log.d("AsString", String.valueOf(MSpinner.getSelectedId().get(i)));
                }

                ApiServices.getRestaurantRegister(
                        convertToRequestBody(name.getText().toString())
                        , convertToRequestBody(mail.getText().toString())
                        , convertToRequestBody(password.getText().toString()),
                        convertToRequestBody(ensurepassword.getText().toString())
                        , convertToRequestBody(phone.getText().toString())
                        , convertToRequestBody(whatsNum.getText().toString())
                        , convertToRequestBody(String.valueOf(idRegions)), CategorySpinner.getSelectedId()
                        , convertToRequestBody(limitPrice.getText().toString())
                        , convertToRequestBody(deliveryCoast.getText().toString()),
                        convertFileToMultipart(filePath, "photo"))
                        .enqueue(new Callback<RestauranttRegister>() {
                            @Override
                            public void onResponse(Call<RestauranttRegister> call, Response<RestauranttRegister> response) {



                                Log.d("response", response.body().getMsg());

                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                if (response.body().getStatus() == 1) {

                                    Helper.replace(new ListFood_Fragment(), getActivity().getSupportFragmentManager(), R.id.fragment_container, null, null);


                                } else {

                                }
                            }

                            @Override
                            public void onFailure(Call<RestauranttRegister> call, Throwable t) {
                                Log.d("onFailure", t.getMessage());

                            }
                        });

            }
        });

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

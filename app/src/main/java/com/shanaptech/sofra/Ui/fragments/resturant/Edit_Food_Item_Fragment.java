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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.myItems.Datum;
import com.shanaptech.sofra.Data.postman.model.updateItem.UpdateItem;
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

import static android.app.Activity.RESULT_OK;
import static com.shanaptech.sofra.Helper.Helper.LodeImageCircle;
import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;
import static com.shanaptech.sofra.Helper.Helper.getStartFragments;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Edit_Food_Item_Fragment extends Fragment {

    public Datum mydata;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.desc)
    EditText desc;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.price_in_sale)
    EditText priceInSale;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.edit_btn)
    Button editBtn;
    Unbinder unbinder;
    private EditText food_name, food_Desc, Price, price2, Time;
    private Button Edit;


    private String getName, getDescription, getPhotoUrl, getPrice, getPreparingTime;
    private int getId;
    private String filePath;
    private String getOfferPrice;

    private ApiPostman ApiServices;

    private ImageView imageView;

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.food_item_edit_layout, container, false);


        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);
        food_name = v.findViewById(R.id.name);
        food_Desc = v.findViewById(R.id.desc);
        Price = v.findViewById(R.id.price);
        price2 = v.findViewById(R.id.price_in_sale);
        Time = v.findViewById(R.id.time);

        imageView = v.findViewById(R.id.img);
        Edit = v.findViewById(R.id.edit_btn);
        Bundle bundle = getArguments();
        getId = bundle.getInt("getId");
        getName = bundle.getString("getName");
        getDescription = bundle.getString("getDescription");
        getPhotoUrl = bundle.getString("getPhotoUrl");
        getPrice = bundle.getString("getPrice");
        getOfferPrice = bundle.getString("getOfferPrice");
        getPreparingTime = bundle.getString("getPreparingTime");

        food_name.setText(getName);
        food_Desc.setText(getDescription);
        Price.setText(getPrice);
        Time.setText(getPreparingTime);
        price2.setText(getOfferPrice);
        LodeImageCircle(getActivity(), getPhotoUrl, imageView);
        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    private void UpdateItemRestaurant() {
        LoadData((AppCompatActivity) getActivity(), USER_API_TOKEN);
        ApiServices.UpdateItems(
                convertToRequestBody(food_Desc.getText().toString()),
                convertToRequestBody(Price.getText().toString()),
                convertToRequestBody(Time.getText().toString()),
                convertToRequestBody(food_name.getText().toString()),
                convertToRequestBody(String.valueOf(getId)),
                convertToRequestBody(price2.getText().toString()),
                convertToRequestBody(LoadData((AppCompatActivity) getActivity(), USER_API_TOKEN))
                , convertFileToMultipart(filePath, "photo")).enqueue(new Callback<UpdateItem>() {
            @Override
            public void onResponse(Call<UpdateItem> call, Response<UpdateItem> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();


                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.fragment_container, new ListFood_Fragment());
                    }
                } else {
                    Log.d("response", response.body().getMsg());

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UpdateItem> call, Throwable t) {
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
            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath)
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

    @OnClick({R.id.img, R.id.edit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img:
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
            case R.id.edit_btn:
                UpdateItemRestaurant();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.updateItem.UpdateItem;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class Add_Item_Fragment extends Fragment {


    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;
    @BindView(R.id.calculator)
    ImageView calculator;
    @BindView(R.id.notification)
    ImageView notification;
    @BindView(R.id.top_toolbar)
    Toolbar topToolbar;
    @BindView(R.id.normal_tetx)
    TextView normalTetx;
    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.desc)
    EditText desc;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.price_in_sale)
    EditText priceInSale;
    @BindView(R.id.duration_getready)
    EditText durationGetready;
    @BindView(R.id.add_btn)
    Button addBtn;
    Unbinder unbinder;

    private ApiPostman ApiServices;

    private ImageView imageView;

    private EditText Name, Desc, Price, PriceInSale, duration;

    private String filePath;
    private Button Add;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_item_pic_layout, container, false);


        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);
        Name = v.findViewById(R.id.name);
        Desc = v.findViewById(R.id.desc);
        Price = v.findViewById(R.id.price);
        PriceInSale = v.findViewById(R.id.price_in_sale);
        duration = v.findViewById(R.id.duration_getready);

        imageView = v.findViewById(R.id.shop_img);
        imageView.setClickable(true);
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

        Add = v.findViewById(R.id.add_btn);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemRestaurant();
            }
        });

        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    private void AddItemRestaurant() {

        ApiServices.NewItem(
                convertToRequestBody(Desc.getText().toString()),
                convertToRequestBody(Price.getText().toString()),
                convertToRequestBody(PriceInSale.getText().toString()),
                convertToRequestBody(duration.getText().toString()),
                convertToRequestBody(Name.getText().toString()),
                convertToRequestBody(LoadData((AppCompatActivity) getActivity(), USER_API_TOKEN))
                , convertFileToMultipart(filePath, "photo")).enqueue(new Callback<UpdateItem>() {
            @Override
            public void onResponse(Call<UpdateItem> call, Response<UpdateItem> response) {
                try {
                    Log.d("response", response.body().getMsg());

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();


                    } else {
                        Log.d("response", response.body().getMsg());

                    }
                } catch (Exception e) {
                    e.getMessage();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

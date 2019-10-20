package com.shanaptech.sofra.Ui.fragments.resturant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.UpdateOffer.UpdateOffer;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;


import java.text.DecimalFormat;
import java.util.Calendar;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.shanaptech.sofra.Helper.Helper.convertFileToMultipart;
import static com.shanaptech.sofra.Helper.Helper.convertToRequestBody;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class UpdateOffersRestaurantFragment extends Fragment {


    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;
    private int YEAR;
    private int MONTH;
    private int DAY_OF_MONTH;
    private Calendar calendar;
    private String DataOfBirth, DataLastDonation;
    private ApiPostman ApiServices;
    private String getName, getDescription, getPhotoUrl, getPrice;

    private View view;

    private String filePath;
    private int getId;
    private String getStartingAt;
    private String getEndingAt;

    private ImageView imageView;

    private EditText Name, Desc , From , To ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_offers_restaurnt, container, false);


        inti();

        decimalFormatCalendar();

        return view;
    }


    private void inti() {

        Name= view.findViewById(R.id.offer_name);
        Desc=view.findViewById(R.id.offer_desc);
        From=view.findViewById(R.id.from);
        To=view.findViewById(R.id.to);
        imageView=view.findViewById(R.id.normalimage);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

        /// get date MyItemRestaurantFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            getId = bundle.getInt("getId");
            getName = bundle.getString("getName");
            getDescription = bundle.getString("getDescription");
            getPhotoUrl = bundle.getString("getPhotoUrl");
            getStartingAt = bundle.getString("getStartingAt");
            getEndingAt = bundle.getString("getEndingAt");
            getPrice = bundle.getString("getPrice");
        }


        Name.setText(getName);
        Desc.setText(getDescription);
        From.setText(getStartingAt);
        To.setText(getEndingAt);

        Helper.LodeImageCircle(getActivity(), getPhotoUrl, imageView);

    }


    private void UpdateOffersRestaurant() {


        ApiServices.updateOffer(
                convertToRequestBody(Desc.getText().toString()),
                convertToRequestBody("0"),
                convertToRequestBody(From.getText().toString()),
                convertToRequestBody(Name.getText().toString()),
                convertFileToMultipart(filePath, "photo"),
                convertToRequestBody(To.getText().toString()),
                convertToRequestBody(LoadData( (AppCompatActivity) getActivity(), USER_API_TOKEN)),
                convertToRequestBody(String.valueOf(getId))
               ).enqueue(new Callback<UpdateOffer>() {
            @Override
            public void onResponse(Call<UpdateOffer> call, Response<UpdateOffer> response) {
                try {
                    Log.d("response", response.body().getMsg());
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<UpdateOffer> call, Throwable t) {
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

    @OnClick({R.id.normalimage, R.id.Add_btn})
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
            case R.id.Add_btn:
                UpdateOffersRestaurant();
                break;
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void decimalFormatCalendar() {

        DecimalFormat decimalFormat = new DecimalFormat("00");
        calendar = Calendar.getInstance();
        YEAR = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.YEAR)));
        MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.MONTH)))+1;
        DAY_OF_MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.DAY_OF_MONTH)));

//        DataOfBirth = "1972" + "-" + "01" + "-" + "01";
//        addOffersRestaurantFragmentEditToItem.setText(DataOfBirth);
        To.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            DataOfBirth = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                            To.setText(DataOfBirth);
                        }
                    }, YEAR, MONTH, DAY_OF_MONTH);

                    datePickerDialog.show();
                    return true;
                }
                return false;
            }
        });

        From.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            DataLastDonation = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                            From.setText(DataLastDonation);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-1);

                    datePickerDialog.show();

                    return true;
                }
                return false;
            }
        });

    }
}

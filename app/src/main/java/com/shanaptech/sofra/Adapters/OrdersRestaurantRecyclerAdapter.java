package com.shanaptech.sofra.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanaptech.sofra.Data.postman.model.acceptOrder.AcceptOrder;
import com.shanaptech.sofra.Data.postman.model.myOrders.Datum;
import com.shanaptech.sofra.Data.postman.model.rejectOrder.RejectOrder;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Ui.fragments.resturant.Bervious_Orders_Fragment;
import com.shanaptech.sofra.Ui.fragments.resturant.Orders_Tab_Fragment;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Helper.Helper.dismissProgressDialog;
import static com.shanaptech.sofra.Helper.Helper.getStartFragments;
import static com.shanaptech.sofra.Helper.Helper.showProgressDialog;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;


public class OrdersRestaurantRecyclerAdapter extends RecyclerView.Adapter<OrdersRestaurantRecyclerAdapter.ViewHolder> {

    ArrayList<Datum> ordersArrayList;

    Activity context;
    private static final Integer CALL = 0x2;
    private Orders_Tab_Fragment requestsRestaurantComponentFragment;

    private ApiPostman apiServerRestaurant;
    String keyRequest;
    private View itemLayoutView;
    private ViewHolder viewHolder;

    public OrdersRestaurantRecyclerAdapter(ArrayList<Datum> ordersArrayList, Activity context
            , Orders_Tab_Fragment requestsRestaurantComponentFragment, String keyRequest) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
        this.requestsRestaurantComponentFragment = requestsRestaurantComponentFragment;
        this.keyRequest = keyRequest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.orders_restaurant_recycler, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);

        /// get date MyItemRestaurantFragment
        apiServerRestaurant = RetrofitClient.getClient().create(ApiPostman.class);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterNewOrdersNameClientTxv.setText(ordersArrayList.get(i).getClient().getName());
        viewHolder.setAdapterNewOrdersTotalClientTxv.setText(ordersArrayList.get(i).getTotal());
        viewHolder.setAdapterNewOrdersAddressClientTxv.setText(ordersArrayList.get(i).getClient().getAddress());
        viewHolder.setAdapterNumberOrdersTxv.setText(String.valueOf(ordersArrayList.get(i).getId()));

        if (keyRequest.equals("pending")) {

            viewHolder.setAdapterNewOrdersBtnClientCall.setText(context.getResources().getString(R.string.phone));
            viewHolder.setAdapterNewOrdersBtnClientCall.setVisibility(View.VISIBLE);
            viewHolder.setAdapterNewOrdersBtnClientReject.setVisibility(View.VISIBLE);
            viewHolder.setAdapterNewOrdersBtnClientAccept.setVisibility(View.VISIBLE);

        } else if (keyRequest.equals("current")) {

            viewHolder.setAdapterNewOrdersBtnClientCall.setText(context.getResources().getString(R.string.phone));
            viewHolder.setAdapterNewOrdersBtnClientAccept.setText("تأكـيد الطلب");
            viewHolder.setAdapterNewOrdersBtnClientCall.setVisibility(View.VISIBLE);
            viewHolder.setAdapterNewOrdersBtnClientReject.setVisibility(View.GONE);
            viewHolder.setAdapterNewOrdersBtnClientAccept.setVisibility(View.VISIBLE);

        } else if (keyRequest.equals("completed")) {

            viewHolder.setAdapterNewOrdersBtnClientCall.setVisibility(View.GONE);
            viewHolder.setAdapterNewOrdersBtnClientReject.setVisibility(View.GONE);
            viewHolder.setAdapterNewOrdersBtnClientAccept.setVisibility(View.VISIBLE);
            viewHolder.setAdapterNewOrdersBtnClientCall.setText(ordersArrayList.get(i).getState());

            if (ordersArrayList.get(i).getState().equals("delivered")) {
                viewHolder.setAdapterNewOrdersBtnClientAccept.setBackground(context.getDrawable(R.drawable.shape_btn_accept));
                viewHolder.setAdapterNewOrdersBtnClientAccept.setText(ordersArrayList.get(i).getState());

            } else if (ordersArrayList.get(i).getState().equals("rejected"))  {
                viewHolder.setAdapterNewOrdersBtnClientAccept.setBackground(context.getDrawable(R.drawable.shape_btn_call));
                viewHolder.setAdapterNewOrdersBtnClientAccept.setText(ordersArrayList.get(i).getState());
            }
        }


//         method lode image view post
//        LodeImageCircle(context, dataCommentList.get(i).getDataItem().getLastPageUrl(), viewHolder.setAdapterNewOrdersImgClientPhoto);


        if (keyRequest.equals("pending")) {

            viewHolder.setAdapterNewOrdersBtnClientAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Integer id = ordersArrayList.get(i).getId();
                        GetNewOrder(id, i);
                        showProgressDialog(context, "please wait");
                        apiServerRestaurant.acceptOrder(LoadData( (AppCompatActivity)context, USER_API_TOKEN), id).enqueue(new Callback<AcceptOrder>() {
                            @Override
                            public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                                try {
                                    dismissProgressDialog();
                                    if (response.body().getStatus() == 1) {

                                        Log.d("response", response.body().getMsg());

                                        requestsRestaurantComponentFragment.bervious_order_fragment.ordersArrayList.add(0, ordersArrayList.get(i));
                                        ordersArrayList.remove(i);

                                        requestsRestaurantComponentFragment.bervious_order_fragment.restaurantRecyclerAdapter.notifyDataSetChanged();
                                        notifyDataSetChanged();

                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }

                            @Override
                            public void onFailure(Call<AcceptOrder> call, Throwable t) {
                                dismissProgressDialog();
                                Log.d("onFailure", t.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Demo Exception", e.getMessage());
                    }
                }
            });


            viewHolder.setAdapterNewOrdersBtnClientReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Integer id = ordersArrayList.get(i).getId();
                        GetNewOrder(id, i);
                        showProgressDialog(context,"please wait");
                        apiServerRestaurant.rejectOrder(LoadData((AppCompatActivity)context, USER_API_TOKEN), id).enqueue(new Callback<RejectOrder>() {
                            @Override
                            public void onResponse(Call<RejectOrder> call, Response<RejectOrder> response) {
                                try {
                                    dismissProgressDialog();
                                    if (response.body().getStatus() == 1) {

                                        Log.d("response", response.body().getMsg());
                                        requestsRestaurantComponentFragment.bervious_order_fragment.ordersArrayList.add(0, ordersArrayList.get(i));
                                         ordersArrayList.remove(i);
                                         notifyDataSetChanged();
                                        requestsRestaurantComponentFragment.bervious_order_fragment.restaurantRecyclerAdapter.notifyDataSetChanged();
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }

                            @Override
                            public void onFailure(Call<RejectOrder> call, Throwable t) {
                                Log.d("onFailure", t.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Demo Exception", e.getMessage());
                    }
                }
            });

        } else if (keyRequest.equals("current")) {

            viewHolder.setAdapterNewOrdersBtnClientAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Integer id = ordersArrayList.get(i).getId();
                        GetNewOrder(id, i);
                        showProgressDialog(context,"please wait");
                        apiServerRestaurant.confirmOrder(LoadData((AppCompatActivity) context, USER_API_TOKEN), id).enqueue(new Callback<RejectOrder>() {
                            @Override
                            public void onResponse(Call<RejectOrder> call, Response<RejectOrder> response) {
                                dismissProgressDialog();
                                try {
                                    if (response.body().getStatus() == 1) {
                                        Log.d("response", response.body().getMsg());
                                        requestsRestaurantComponentFragment.new_order_fragment.ordersArrayList.add(0, ordersArrayList.get(i));
                                        ordersArrayList.remove(i);

                                        requestsRestaurantComponentFragment.new_order_fragment.restaurantRecyclerAdapter.notifyDataSetChanged();
                                         notifyDataSetChanged();
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }

                            @Override
                            public void onFailure(Call<RejectOrder> call, Throwable t) {
                                Log.d("onFailure", t.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Demo Exception", e.getMessage());
                    }
                }
            });


        }
        viewHolder.setAdapterNewOrdersBtnClientCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ordersArrayList.get(i).getClient().getPhone()));
                        context.startActivity(callIntent);

                    } else {
                        AslForPermission(CALL);
                    }

                } catch (Exception e) {
                    Log.e("Demo application", "Failed to invoke call", e);
                }
            }
        });
        viewHolder.setAdapterNewOrdersItemsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("getId", ordersArrayList.get(i).getId());
                bundle.putString("keyRequest", keyRequest);
                bundle.putInt("posation", i);
                bundle.putString("getPhone", ordersArrayList.get(i).getClient().getPhone());
                Fragment fragment = new Bervious_Orders_Fragment();
                fragment.setArguments(bundle);
                getStartFragments(((FragmentActivity) context).getSupportFragmentManager(), R.id.fragment_container, fragment);

            }
        });
    }


    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.setAdapterNewOrdersImgClientPhoto)
        ImageView setAdapterNewOrdersImgClientPhoto;
        @BindView(R.id.name)
        TextView setAdapterNewOrdersNameClientTxv;
        @BindView(R.id.total)
        TextView setAdapterNewOrdersTotalClientTxv;
        @BindView(R.id.address)
        TextView setAdapterNewOrdersAddressClientTxv;
        @BindView(R.id.orders)
        TextView setAdapterNumberOrdersTxv;

        @BindView(R.id.Reject)
        Button setAdapterNewOrdersBtnClientReject;

        @BindView(R.id.setAdapterNewOrdersBtnClientAccept)
        Button setAdapterNewOrdersBtnClientAccept;

        @BindView(R.id.setAdapterNewOrdersBtnClientCall)
        Button setAdapterNewOrdersBtnClientCall;
        View view;
        @BindView(R.id.setAdapterNewOrdersItemsCardView)
        CardView setAdapterNewOrdersItemsCardView;


        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }


    private void AslForPermission(Integer requestCode) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {

                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, requestCode);

            } else {

                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, requestCode);

            }
        } else {

            Toast.makeText(context, "" + Manifest.permission.CALL_PHONE + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    // ge New Order
    private void GetNewOrder(int getId, final int position) {
//        LoadData(getActivity(), USER_API_TOKEN)


    }
}

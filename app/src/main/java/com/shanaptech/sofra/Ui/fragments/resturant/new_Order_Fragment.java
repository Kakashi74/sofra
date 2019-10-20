package com.shanaptech.sofra.Ui.fragments.resturant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shanaptech.sofra.Adapters.OrdersRestaurantRecyclerAdapter;
import com.shanaptech.sofra.Data.postman.model.myOrders.Datum;
import com.shanaptech.sofra.Data.postman.model.myOrders.MyOrders;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.OnEndless;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;

public class new_Order_Fragment extends Fragment {

    Orders_Tab_Fragment new_tab_fragmnet;
    RecyclerView recyclerView ;
    SwipeRefreshLayout swipeRefreshLayout ;

    public OrdersRestaurantRecyclerAdapter restaurantRecyclerAdapter;
    private OnEndless onEndless;

    public ArrayList<Datum> ordersArrayList = new ArrayList<>();
    private int max;
    private ApiPostman ApiServices;
    private String KeyRequest;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_order_fragment_layout , container , false);

     ApiServices = RetrofitClient.getClient().create(ApiPostman.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            KeyRequest = bundle.getString("KeyRequest");
        }

        onEndless();

        SwipeRefresh();


        return v ;
    }

    @Override
    public void onStop() {
        super.onStop();
        ordersArrayList.clear();
    }


    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 10) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    geNewOrder(current_page);
                }
            }
        };

        recyclerView.addOnScrollListener(onEndless);

        restaurantRecyclerAdapter =
                new OrdersRestaurantRecyclerAdapter(ordersArrayList, getActivity(), new_tab_fragmnet,KeyRequest);
        recyclerView.setAdapter(restaurantRecyclerAdapter);


        geNewOrder(1);

    }



    private void geNewOrder(int current_page) {

        swipeRefreshLayout.setRefreshing(true);

        ApiServices.getMyOrders(LoadData((AppCompatActivity) getActivity(), USER_API_TOKEN), KeyRequest, current_page)
                .enqueue(new Callback<MyOrders>() {
                    @Override
                    public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                        try {
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            if (response.body().getStatus() == 1) {

                                max = response.body().getData().getLastPage();

                                // add All
                                ordersArrayList.addAll(response.body().getData().getData());

                                restaurantRecyclerAdapter.notifyDataSetChanged();

                                //  set Visibility INVISIBLE
                                swipeRefreshLayout.setRefreshing(false);

                            } else {

                                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        } catch (Exception e) {

                            e.getMessage();
                        }


                    }

                    @Override
                    public void onFailure(Call<MyOrders> call, Throwable t) {


                    }
                });

    }




    private void SwipeRefresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ordersArrayList.clear();
                geNewOrder(1);


            }
        });
    }
}

package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.shanaptech.sofra.Adapters.Client_Home_Adapter;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedDataUser;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedModelSpinner;
import com.shanaptech.sofra.Data.postman.model.allRestaurants.AllRestaurants;
import com.shanaptech.sofra.Data.postman.model.cities.GetCities;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.OnEndless;
import com.shanaptech.sofra.Utils.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Client_Home_Fragment extends Fragment {

    Client_Home_Adapter mAdapter;
    public static List<GeneratedDataUser> userArrayList  ;

    Unbinder unbinder;
    @BindView(R.id.search_food)
    ImageButton imgBtn;
    @BindView(R.id.search_favourite_food)
    TextInputEditText TextSearch;
    @BindView(R.id.choose_city_spinner)
    Spinner CitySpinner;
    @BindView(R.id.food_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.fragmentAllClientSprRefresh)
    SwipeRefreshLayout mSwipe;
    private boolean checkFilterPost = true;


    private ApiPostman ApiServices;
    private int max = 0;

    public int current_page = 1;

    private OnEndless onEndless;

    // value adapter city
    SpinnerAdapter spinnerAdapter;
    ArrayList<GeneratedModelSpinner> generatedModelSpinnerArrayListCity;
    GeneratedModelSpinner cityGeneratedModelSpinner;
    private int idCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clinet_home_layout, container , false);
        mRecycler = v.findViewById(R.id.food_recycler);
        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);
        userArrayList = new ArrayList<>();


        return v;
    }

    // get  All Restaurant
    private void getAllRestaurant(int Page) {

        mSwipe.setRefreshing(true);

        ApiServices.getAllRestaurant(Page).enqueue(new Callback<AllRestaurants>() {
            @Override
            public void onResponse(Call<AllRestaurants> call, Response<AllRestaurants> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        max = response.body().getData().getLastPage();

                        userArrayList.addAll(response.body().getData().getData());

                        mAdapter.notifyDataSetChanged();

                        mSwipe.setRefreshing(false);

                    } else {
                        Log.d("response", response.body().getMsg());

                        mSwipe.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            @Override
            public void onFailure(Call<AllRestaurants> call, Throwable t) {
                Log.d("response", t.getMessage());

            }
        });

    }

    // get   Restaurant Filter
    private void getRestaurantFilter(int Page) {
        mSwipe.setRefreshing(true);
        ApiServices.getAllRestaurantFilter(TextSearch.getText().toString(),idCity,Page).enqueue(new Callback<AllRestaurants>() {
            @Override
            public void onResponse(Call<AllRestaurants> call, Response<AllRestaurants> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        max = response.body().getData().getLastPage();

                        userArrayList.clear();

                        userArrayList.addAll(response.body().getData().getData());

                        mAdapter.notifyDataSetChanged();
                        mSwipe.setRefreshing(false);

                    } else {
                        Log.d("response", response.body().getMsg());
                        mSwipe.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.getMessage();
                    mSwipe.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<AllRestaurants> call, Throwable t) {
                Log.d("response", t.getMessage());
                mSwipe.setRefreshing(false);
            }
        });

    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    if (checkFilterPost) {

                        getAllRestaurant(current_page);
                    } else {
                        getRestaurantFilter(current_page);
                    }
                }
            }
        };

        mRecycler.addOnScrollListener(onEndless);
        mAdapter = new Client_Home_Adapter(userArrayList, getActivity());
        mRecycler.setAdapter(mAdapter);

        getAllRestaurant(1);

    }

    // get getDataCity
    private void getDataCity() {

        ApiServices.getCities().enqueue(new Callback<GetCities>() {
            @Override
            public void onResponse(Call<GetCities> call, Response<GetCities> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        // clear list
                        generatedModelSpinnerArrayListCity.clear();
                        // add new data from list
                        generatedModelSpinnerArrayListCity.add(new GeneratedModelSpinner(0, "المدينــة"));

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {

                            cityGeneratedModelSpinner = new GeneratedModelSpinner(response.body().getData().getData().get(i).getId(),
                                    response.body().getData().getData().get(i).getName());

                            generatedModelSpinnerArrayListCity.add(cityGeneratedModelSpinner);
                        }

                        spinnerAdapter = new SpinnerAdapter(getContext(), generatedModelSpinnerArrayListCity);
                        CitySpinner.setAdapter(spinnerAdapter);
                        CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    idCity = generatedModelSpinnerArrayListCity.get(position).getId();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_food)
    public void onViewClicked() {
        if (CitySpinner.getSelectedItemPosition() ==
                0 && TextSearch.getText().toString().isEmpty()&& !checkFilterPost) {

            mAdapter = new Client_Home_Adapter(userArrayList, getActivity());
            mRecycler.setAdapter(mAdapter);

            checkFilterPost = true;

        } else {

            mAdapter = new Client_Home_Adapter(userArrayList, getActivity());
            mRecycler.setAdapter(mAdapter);


            checkFilterPost = false;

            getRestaurantFilter(1);

        }

    }

    //  swipeRefresh All list
    private void SwipeRefresh() {

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllRestaurant(1);
                userArrayList.clear();
            }
        });
    }
}



package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanaptech.sofra.Adapters.GetOffer_Adapter;
import com.shanaptech.sofra.Data.postman.model.getOffersClient.DataItemOffers;
import com.shanaptech.sofra.Data.postman.model.getOffersClient.GetOffersClient;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.OnEndless;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetOffersFragment extends Fragment {

    GetOffer_Adapter OfferAdapter;
    public static List<DataItemOffers> itemOffers = new ArrayList<>();

    Unbinder unbinder;


    @BindView(R.id.offer_recycler)
    RecyclerView OfferREcyclerView;



    private View view;
    private ApiPostman ApiServices;

    private int previousTotal = 0;
    private boolean loading = true;
    public int current_page = 1;
    private Integer max = 0;
    private OnEndless onEndless;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_getoffer_layout, container, false);

        unbinder = ButterKnife.bind(this, view);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

        itemOffers.clear();


        onEndless();

        return view;
    }

    private void getOffersRestaurant(int Page) {
        ApiServices.getOffers(Page)
                .enqueue(new Callback<GetOffersClient>() {
                    @Override
                    public void onResponse(Call<GetOffersClient> call, Response<GetOffersClient> response) {

                        Log.d("response", response.body().getMsg());

                        if (response.body().getStatus() == 1) {

                            max = response.body().getData().getLastPage();

                            itemOffers.addAll(response.body().getData().getData());

                            OfferAdapter.notifyDataSetChanged();


                        } else {
                            Log.d("response", response.body().getMsg());
                           }
                    }

                    @Override
                    public void onFailure(Call<GetOffersClient> call, Throwable t) {
                        Log.d("response", t.getMessage());
                        }
                });
    }



    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        OfferREcyclerView.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {

                        getOffersRestaurant(current_page);

                }
            }
        };

        OfferREcyclerView.addOnScrollListener(onEndless);
        OfferAdapter = new GetOffer_Adapter(itemOffers, getActivity());
        OfferREcyclerView.setAdapter(OfferAdapter);

        getOffersRestaurant(1);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

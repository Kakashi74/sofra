package com.shanaptech.sofra.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedDataUser;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Ui.fragments.client.Food_List_Client_Fragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shanaptech.sofra.Helper.Helper.LodeImageCircle;
import static com.shanaptech.sofra.Helper.Helper.getStartFragments;


public class Client_Home_Adapter extends RecyclerView.Adapter<Client_Home_Adapter.ViewHolder> {

    List<GeneratedDataUser> ordersArrayList;

    Activity context;

    private ApiPostman ApiServices;
    private View itemLayoutView;
    private ViewHolder viewHolder;
    public static Integer idRestaurant;
    public static String PhotoUrl;
    public static  String Name;

    public Client_Home_Adapter(List<GeneratedDataUser> ordersArrayList, Activity context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.client_home_recycler, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);


        /// get date MyItemRestaurantFragment
        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterShowRestaurantClientTVNameRestaurant.setText(ordersArrayList.get(i).getName());
        viewHolder.setAdapterShowRestaurantClientTvMinimumRequest.setText(ordersArrayList.get(i).getMinimumCharger());
        viewHolder.setAdapterShowRestaurantClientTvDeliveryCost.setText(ordersArrayList.get(i).getDeliveryCost());
        viewHolder.setAdapterShowRestaurantClientRatingBarRestaurant.setRating(ordersArrayList.get(i).getRate());

        if (ordersArrayList.get(i).getAvailability().equals("open")) {

            viewHolder.setAdapterShowRestaurantClientImgPhotoOnline.setImageResource(R.drawable.ic_online);
            viewHolder.setAdapterShowRestaurantClientTvOnline.setText("Open");
        } else {
            viewHolder.setAdapterShowRestaurantClientImgPhotoOnline.setImageResource(R.drawable.ic_offline);
            viewHolder.setAdapterShowRestaurantClientTvOnline.setText("Close");

        }
        LodeImageCircle(context, ordersArrayList.get(i).getPhotoUrl(), viewHolder.setAdapterShowRestaurantClientImgPhotoRestaurant);

        viewHolder.setAdapterItemsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idRestaurant = ordersArrayList.get(i).getId();
                PhotoUrl = ordersArrayList.get(i).getPhotoUrl();
                Name = ordersArrayList.get(i).getName();

                Bundle bundle = new Bundle();
                bundle.putInt("IdRestaurantFromCartOrShowAdapter", ordersArrayList.get(i).getId());


                Fragment fragment = new Food_List_Client_Fragment();
                Log.d("idRestaurantAdap", String.valueOf(ordersArrayList.get(i).getId()));

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

        View view;
        @BindView(R.id.setAdapterShowRestaurantClientTVNameRestaurant)
        TextView setAdapterShowRestaurantClientTVNameRestaurant;
        @BindView(R.id.setAdapterShowRestaurantClientRatingBarRestaurant)
        RatingBar setAdapterShowRestaurantClientRatingBarRestaurant;
        @BindView(R.id.setAdapterShowRestaurantClientTvMinimumRequest)
        TextView setAdapterShowRestaurantClientTvMinimumRequest;
        @BindView(R.id.setAdapterShowRestaurantClientTvDeliveryCost)
        TextView setAdapterShowRestaurantClientTvDeliveryCost;

        @BindView(R.id.setAdapterShowRestaurantClientImgPhotoRestaurant)
        ImageView setAdapterShowRestaurantClientImgPhotoRestaurant;
        @BindView(R.id.setAdapterShowRestaurantClientImgPhotoOnline)
        ImageView setAdapterShowRestaurantClientImgPhotoOnline;
        @BindView(R.id.setAdapterShowRestaurantClientTvOnline)
        TextView setAdapterShowRestaurantClientTvOnline;
        @BindView(R.id.setAdapterItemsCardView)
        CardView setAdapterItemsCardView;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}

package com.shanaptech.sofra.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanaptech.sofra.Data.postman.model.getOffersClient.DataItemOffers;
import com.shanaptech.sofra.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shanaptech.sofra.Helper.Helper.LodeImage;


public class GetOffer_Adapter extends RecyclerView.Adapter<GetOffer_Adapter.ViewHolder> {

    List<DataItemOffers> ordersArrayList;

    Activity context;


    private View itemLayoutView;
    private ViewHolder viewHolder;

    public GetOffer_Adapter(List<DataItemOffers> ordersArrayList, Activity context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.
                getoffers_recycler_layout, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterNewOfferTxv.setText(ordersArrayList.get(i).getName());


        LodeImage(context, ordersArrayList.get(i).getPhotoUrl(), viewHolder.setAdapterNewOfferImgPhoto);

    }


    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        @BindView(R.id.setAdapterNewOfferTxv)
        TextView setAdapterNewOfferTxv;
        @BindView(R.id.setAdapterNewOfferImgPhoto)
        ImageView setAdapterNewOfferImgPhoto;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}

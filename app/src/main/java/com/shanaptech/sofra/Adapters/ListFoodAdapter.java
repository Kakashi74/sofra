package com.shanaptech.sofra.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shanaptech.sofra.Data.postman.model.myItems.Datum;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.Ui.activity.MainActivity;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Ui.fragments.resturant.Edit_Food_Item_Fragment;

import java.util.ArrayList;
import java.util.List;

public class ListFoodAdapter extends android.support.v7.widget.RecyclerView.Adapter<ListFoodAdapter.Holder> {
    private AppCompatActivity activity;
    private Context mContext;
    private List<Datum> itemList = new ArrayList<>();

    public ListFoodAdapter(Context mContext, List<Datum> itemList , AppCompatActivity activity) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listfood_layout_recycler, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
notifyItemRemoved(i);
            }
        });


        holder.food_name.setText(itemList.get(i).getName());
        holder.food_desc.setText(itemList.get(i).getDescription());
        holder.price_txt.setText(itemList.get(i).getPrice());
        Glide.with(mContext)
                .load(itemList.get(i).getPhotoUrl())
                .into(holder.food_img);

                holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity homeActivity = (MainActivity) activity;
               Edit_Food_Item_Fragment item = new Edit_Food_Item_Fragment();
                item.mydata = itemList.get(i);

               Helper.replace(item ,homeActivity.getSupportFragmentManager() , R.id.fragment_container, null , null );
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class Holder extends RecyclerView.ViewHolder{
        ImageView food_img ;
        TextView food_name , food_desc  , price_txt ;
        Button edit , delete ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.food_name);
            food_desc = itemView.findViewById(R.id.food_desc);
            price_txt = itemView.findViewById(R.id.food_price);
            food_img= itemView.findViewById(R.id.food_img);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
 }

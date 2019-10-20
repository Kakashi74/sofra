package com.shanaptech.sofra.Ui.fragments.resturant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.shanaptech.sofra.Adapters.ListFoodAdapter;
import com.shanaptech.sofra.Data.postman.model.myItems.Datum;
import com.shanaptech.sofra.Data.postman.model.myItems.ITems;
import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.OnEndless;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFood_Fragment extends Fragment {

    FloatingActionButton FButton ;
    RecyclerView mrecycler ;
    RecyclerView.LayoutManager mManger ;
    ApiPostman apiServices ;
    ListFoodAdapter mAdapter ;
    private List<Datum> itemList = new ArrayList<>();
    int maxPage = 0;
    OnEndless onEndless ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.food_list_layout , container , false);

        BottomNavigationViewEx bottomNavigationViewEx = v.findViewById(R.id.bottom_Nview);
        Helper.SetButtomNavigationViewEX(bottomNavigationViewEx);

        mManger = new LinearLayoutManager(getActivity());

        apiServices = RetrofitClient.getClient().create(ApiPostman.class);

        mrecycler = v.findViewById(R.id.recyclerview);
        mAdapter = new ListFoodAdapter(getActivity() ,itemList , (AppCompatActivity) getActivity());
        onEndless = new OnEndless( new LinearLayoutManager(getActivity()), 10) {

            @Override

            public void onLoadMore(int current_page) {



                if (maxPage != 0 || current_page == 1) {

                    getOrders(current_page);
                }
            }
        };

        mrecycler.setLayoutManager(mManger);
        mrecycler.setAdapter(mAdapter);

        FButton = v.findViewById(R.id.flaot_button);
        FButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.replace(new Add_Item_Fragment() ,getActivity().getSupportFragmentManager() , R.id.fragment_container , null , null );
            }
        });

        getOrders(1);

        return  v;
    }

private void getOrders(int page)
{
    apiServices.getOrders("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx" , 1).enqueue(new Callback<ITems>() {
        @Override
        public void onResponse(Call<ITems> call, Response<ITems> response) {
            if (response.body().getStatus() == 1 ) {

                itemList.addAll(response.body().getData().getData());
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<ITems> call, Throwable t) {

        }
    });
}

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.person:
                Helper.replace(new Orders_Tab_Fragment() , getActivity().getSupportFragmentManager() , R.id.fragment_container , null , null );

                return true;
            case R.id.home:
                return true;

            case R.id.details:
                Helper.replace(new Orders_Tab_Fragment() , getActivity().getSupportFragmentManager() , R.id.fragment_container , null , null );

                return true;

            case R.id.points:
                Helper.replace(new More_Item_Fragment() , getActivity().getSupportFragmentManager() , R.id.fragment_container , null , null );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shanaptech.sofra.Adapters.NotificationRecyclerAdapter;
import com.shanaptech.sofra.Data.postman.model.notifications.DataNotify;
import com.shanaptech.sofra.Data.postman.model.notifications.Notifications;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;
import com.shanaptech.sofra.Utils.OnEndless;;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shanaptech.sofra.Utils.SharedPreferenceManager.LoadData;
import static com.shanaptech.sofra.Utils.SharedPreferenceManager.USER_API_TOKEN;


public class NotificationsFragment extends Fragment {

    @BindView(R.id.notificationsFragmentShowPostRecyclerView)
    RecyclerView NotificationRecycler;
    Unbinder unbinder;


    private NotificationRecyclerAdapter NotificationAdapter;

    private ApiPostman ApiServices;
    private View view;
    private ArrayList<DataNotify> notificationsArrayList;
    private OnEndless onEndless;
    private Integer max;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        unbinder = ButterKnife.bind(this, view);

        notificationsArrayList = new ArrayList<>();
        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);


        onEndless();

        getNotifications(1);

        return view;

    }




    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        NotificationRecycler.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {

                    getNotifications(current_page);

                }
            }
        };

        NotificationRecycler.addOnScrollListener(onEndless);
        NotificationAdapter = new NotificationRecyclerAdapter(notificationsArrayList, getActivity());
        NotificationRecycler.setAdapter(NotificationAdapter);

        getNotifications(1);

    }

    private void getNotifications(int i) {
        try {

            ApiServices.getNotifications(LoadData((AppCompatActivity)getActivity(), USER_API_TOKEN),i).enqueue(new Callback<Notifications>() {
                @Override
                public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                    try {


                        if (response.body().getStatus() == 1) {

                            max = response.body().getData().getLastPage();

                            notificationsArrayList.addAll(response.body().getData().getData());

                            NotificationAdapter.notifyDataSetChanged();


                        } else {


                            Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<Notifications> call, Throwable t) {
                    Log.d("Throwable", t.getMessage());

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

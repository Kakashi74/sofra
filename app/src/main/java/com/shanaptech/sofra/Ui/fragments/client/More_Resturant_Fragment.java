package com.shanaptech.sofra.Ui.fragments.client;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shanaptech.sofra.Adapters.More_Adapter;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedModelMore;
import com.shanaptech.sofra.Helper.CustomDialogCloseClass;
import com.shanaptech.sofra.R;
import com.shanaptech.sofra.Retrofit.ApiPostman;
import com.shanaptech.sofra.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.shanaptech.sofra.Helper.Helper.getStartFragments;


public class More_Resturant_Fragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.MoreResturantListView)
    ListView listView;

    ApiPostman ApiServices;

    List<GeneratedModelMore> generatedModelMores = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.more_resturant_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        ApiServices = RetrofitClient.getClient().create(ApiPostman.class);

        Initialize();
        return view;
    }

    private void Initialize() {

        generatedModelMores.clear();

        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_offer, "Offer"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_connect, "Connect with us"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_about, "About App"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_logout, "logout"));

        More_Adapter moreAdapter = new More_Adapter(getContext(), generatedModelMores);
        listView.setAdapter(moreAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.fragment_container, new GetOffersFragment());
                    }
                }else  if (position == 1) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.fragment_container, new ContactMeFragment());
                    }
                }
                else  if (position == 2) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.fragment_container, new AboutRestaurantFragment());
                    }
                }
                else if (position == 3) {
                    CustomDialogCloseClass cdd=new CustomDialogCloseClass(getActivity());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    cdd.show();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

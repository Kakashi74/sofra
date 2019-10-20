package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanaptech.sofra.Adapters.ViewPagerComponentAdapter;
import com.shanaptech.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.shanaptech.sofra.Adapters.Client_Home_Adapter.PhotoUrl;
import static com.shanaptech.sofra.Adapters.Client_Home_Adapter.idRestaurant;
import static com.shanaptech.sofra.Helper.Helper.LodeImageCircle;

public class Food_List_Client_Fragment extends Fragment {

    @BindView(R.id.simple_image)
    ImageView simpleImage;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;

    ViewPagerComponentAdapter adapter;

    public Menu_Fragment menu_fragment;
    public Comments_Fragment comments_fragment;
    public Shop_Info_Fragment shop_info_fragment;


    private int idRestaurant;

    String Name ;
    View v;
    @BindView(R.id.justText)
    TextView justText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_food_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    private void inti() {
        // Setting ViewPager for each Tabs
        pager = v.findViewById(R.id.pager);
        tabLayout = v.findViewById(R.id.tab_layout);

        LodeImageCircle(getContext(), PhotoUrl, simpleImage);
        justText.setText(Name);
        adapter = new ViewPagerComponentAdapter(getChildFragmentManager());

        // get id restaurant from return show adapter or cart item

        Bundle arguments = getArguments();
        if (arguments != null) {
            idRestaurant = arguments.getInt("IdRestaurantFromCartOrShowAdapter");
            Log.d("idRestaurantContent", String.valueOf(idRestaurant));

        }
        // send id restaurant from return show adapter or cart item
        Bundle bundle = new Bundle();
        bundle.putInt("idRestaurantContentComponent", idRestaurant);

        menu_fragment = new Menu_Fragment();
        menu_fragment.setArguments(bundle);

        comments_fragment = new Comments_Fragment();
        comments_fragment.setArguments(bundle);

        shop_info_fragment = new Shop_Info_Fragment();
        shop_info_fragment.setArguments(bundle);


        adapter.addFragment(menu_fragment, "menu");
        adapter.addFragment(comments_fragment, "Comments");
        adapter.addFragment(shop_info_fragment, "Shop Inforamtoin");

        pager.setAdapter(adapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(getResources().getColor(R.color.red), getResources().getColor(R.color.white));
        tabLayout.setupWithViewPager(pager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

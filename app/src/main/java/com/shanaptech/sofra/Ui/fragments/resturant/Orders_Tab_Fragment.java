package com.shanaptech.sofra.Ui.fragments.resturant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanaptech.sofra.Adapters.ViewPagerComponentAdapter;
import com.shanaptech.sofra.R;

public class Orders_Tab_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager ;

    Orders_Tab_Fragment new_tab_fragmnet ;

    public new_Order_Fragment  new_order_fragment ;
    public new_Order_Fragment recent_order_fragment ;
    public new_Order_Fragment  bervious_order_fragment ;


    ViewPagerComponentAdapter adapter ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  v = inflater.inflate(R.layout.tabe_layout , container , false);

        tabLayout = v.findViewById(R.id.tab_layout);
        viewPager = v.findViewById(R.id.pager);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        adapter = new ViewPagerComponentAdapter(getChildFragmentManager());

        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        }

     InitFragments();

        return v;

    }

    private void InitFragments()
    {
        bervious_order_fragment = new new_Order_Fragment();
        Bundle bundlePending = new Bundle();
        bundlePending.putString("KeyRequest", "pending");
        bervious_order_fragment.setArguments(bundlePending);



        recent_order_fragment = new new_Order_Fragment();
        Bundle bundleDelivered = new Bundle();
        bundleDelivered.putString("KeyRequest", "current");
        recent_order_fragment.setArguments(bundleDelivered);

        new_order_fragment = new new_Order_Fragment();
        Bundle bundleRejected = new Bundle();
        bundleRejected.putString("KeyRequest", "completed");
        new_order_fragment.setArguments(bundleRejected);


        bervious_order_fragment.new_tab_fragmnet = this;
        recent_order_fragment.new_tab_fragmnet = this;
        new_order_fragment.new_tab_fragmnet = this;



        adapter.addFragment(bervious_order_fragment, "طلبات سابقه");
        adapter.addFragment(recent_order_fragment, "طلبات  حاليه");
        adapter.addFragment(new_order_fragment, "طلبات جديده");

        viewPager.setAdapter(adapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setupWithViewPager(viewPager);
    }
}

package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shanaptech.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Shop_Info_Fragment extends Fragment {
    @BindView(R.id.Online)
    TextView Online;
    @BindView(R.id.cityT)
    TextView cityT;
    @BindView(R.id.street)
    TextView street;
    @BindView(R.id.limitdollars)
    TextView limitdollars;
    @BindView(R.id.delivery)
    TextView delivery;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shop_info_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.shanaptech.sofra.Ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shanaptech.sofra.Helper.Helper;
import com.shanaptech.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Comments_Fragment extends Fragment {
    @BindView(R.id.add_comment_btn)
    Button addCommentBtn;
    @BindView(R.id.comments_recycler)
    RecyclerView commentsRecycler;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comments_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_comment_btn)
    public void onViewClicked() {
      //  Helper.replace(new Choose_Evaluate_Fragment() , getActivity().getSupportFragmentManager() , R.id.fragment_container , null ,null);View

                 getActivity().setContentView(R.layout.choose_evaluate_layout);

    }
}

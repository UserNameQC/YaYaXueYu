package com.runto.yayaxueyu.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.runto.controler.utils.CommonUtils;
import com.runto.controler.utils.Entity;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.databinding.FragmentMineLayoutBinding;

public class MineFragment extends BaseFragment {

    public FragmentMineLayoutBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine_layout, container, false);
        CommonUtils.setViewHeight(getActivity(), binding.mineHeadView);
        initMineLayoutEvent();
        return binding.getRoot();
    }

    public void initMineLayoutEvent(){
        binding.percentEditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentEditActivity").navigation();
            }
        });

        binding.percentAboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentNameActivity").withString(Entity.PERCENT_LAYOUT_TYPE, Entity.PERCENT_ABOUT)
                        .navigation();
            }
        });

        binding.percentCollectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentNameActivity").withString(Entity.PERCENT_LAYOUT_TYPE, Entity.PERCENT_COLLECTION)
                        .navigation();
            }
        });

        binding.percentHelpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentNameActivity").withString(Entity.PERCENT_LAYOUT_TYPE, Entity.PERCENT_HELP)
                        .navigation();
            }
        });

        binding.percentSetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentNameActivity").
                        withString(Entity.PERCENT_LAYOUT_TYPE, Entity.PERCENT_SETTING)
                        .navigation();
            }
        });
    }
}

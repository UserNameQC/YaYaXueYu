package com.runto.yayaxueyu.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.runto.controler.bean.HomeSchoolBean;
import com.runto.controler.utils.Entity;
import com.runto.controler.utils.RecyclerViewUnDecoration;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.adapter.HomeRecyclerAdapter;
import com.runto.yayaxueyu.databinding.FragmentHomeLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    public FragmentHomeLayoutBinding binding;
    public HomeRecyclerAdapter recyclerAdapter;
    public List<HomeSchoolBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_layout, container, false);
        //StatusBarUtil.setColor(getActivity(), );
        initView();
        initEvent();
        return binding.getRoot();
    }

    public void initView(){

        HomeSchoolBean schoolBean = new HomeSchoolBean();
        schoolBean.setAddress("玉兴路2号");
        schoolBean.setTitle("瑞思学科英语（玉兴路）");
        dataList.add(schoolBean);

        HomeSchoolBean schoolBean1 = new HomeSchoolBean();
        schoolBean1.setTitle("阿斯顿英语(高新万达校区)");
        schoolBean1.setAddress("康虹路与颖秀路交叉口北100米路西三楼");
        dataList.add(schoolBean1);

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new HomeRecyclerAdapter(getActivity(), dataList);
        binding.homeRecyclerView.addItemDecoration(new RecyclerViewUnDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
        binding.homeRecyclerView.setAdapter(recyclerAdapter);
    }

    public void initEvent(){
        binding.homeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/main/mapActivity")
                        .withString(Entity.MAP_TYPE, Entity.MAP_HOME_LOCATION).navigation();
            }
        });

        binding.searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ARouter.getInstance().build("/main/searchActivity").navigation();
                return false;
            }
        });
    }
}

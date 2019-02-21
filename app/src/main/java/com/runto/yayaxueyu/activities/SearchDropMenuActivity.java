package com.runto.yayaxueyu.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baiiu.dropdownmenu.DropMenuAdapter;
import com.baiiu.dropdownmenu.entity.FilterUrl;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.jaeger.library.StatusBarUtil;
import com.runto.controler.adapter.UniversalRecyclerAdapter;
import com.runto.controler.bean.HomeSchoolBean;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.adapter.HomeRecyclerAdapter;
import com.runto.yayaxueyu.databinding.ActivitySearchDropMenuBinding;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/main/searchDropMenu")
public class SearchDropMenuActivity extends BaseActivity {

    public ActivitySearchDropMenuBinding binding;
    public HomeRecyclerAdapter recyclerAdapter;
    public List<HomeSchoolBean> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_drop_menu);
        StatusBarUtil.setLightMode(this);
        initFlowLayoutView();
        initRecyclerView();
        initEvent();
    }

    public void initRecyclerView(){

        HomeSchoolBean schoolBean = new HomeSchoolBean();
        schoolBean.setAddress("玉兴路2号");
        schoolBean.setTitle("瑞思学科英语（玉兴路）");
        dataList.add(schoolBean);

        HomeSchoolBean schoolBean1 = new HomeSchoolBean();
        schoolBean1.setTitle("阿斯顿英语(高新万达校区)");
        schoolBean1.setAddress("康虹路与颖秀路交叉口北100米路西三楼");
        dataList.add(schoolBean1);

        binding.searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new HomeRecyclerAdapter(this, dataList);
        binding.searchRecycler.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnClickListener(new HomeRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                ARouter.getInstance().build("/main/schoolDetailsActivity").navigation();
            }
        });

        recyclerAdapter.setOnItemClickListener(new UniversalRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance().build("/main/schoolDetailsActivity").navigation();
            }
        });
    }

    public void initEvent(){
        binding.homeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/main/mapActivity").navigation();
            }
        });
    }


    public void initFlowLayoutView(){
        String[] titls = {"分类", "附近", "年龄"};
        binding.searchDropDown.setMenuAdapter(new DropMenuAdapter(this, titls, new OnFilterDoneListener() {
            @Override
            public void onFilterDone(int position, String positionTitle, String urlValue) {
                binding.searchDropDown.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
                binding.searchDropDown.close();
            }
        }));
    }
}

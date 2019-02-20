package com.runto.yayaxueyu.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.adapter.HomeRecyclerAdapter;
import com.runto.yayaxueyu.databinding.ActivitySearchForBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;


import java.util.ArrayList;
import java.util.List;

@Route(path = "/main/searchActivity")
public class SearchForActivity extends BaseActivity {

    public ActivitySearchForBinding binding;
    public List<String> searchTagList = new ArrayList<>();
    public LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_for);
        inflater = LayoutInflater.from(this);
        initView();
    }

    public void initView() {
        searchTagList.add("沃尔德英语");
        searchTagList.add("瑞思英语");
        searchTagList.add("英语");
        binding.searchFlowLayout.setAdapter(new TagAdapter<String>(searchTagList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) inflater.inflate(R.layout.search_item_flowlayout, binding.searchFlowLayout, false);
                textView.setText(s);
                return textView;
            }
        });

        binding.homeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/main/searchDropMenu").navigation();
            }
        });

        binding.searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

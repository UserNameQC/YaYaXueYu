package com.runto.percent.activites;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.runto.controler.adapter.CollectionAdapter;
import com.runto.controler.bean.HomeSchoolBean;
import com.runto.controler.utils.CommonUtils;
import com.runto.percent.R;
import com.runto.percent.databinding.PercentAboutLayoutBinding;
import com.runto.percent.databinding.PercentHelpLayoutBinding;
import com.runto.percent.databinding.PercentNickNameLayoutBinding;
import com.runto.percent.databinding.PercentNameLayoutBinding;
import com.runto.percent.databinding.PercentCollectionLayoutBinding;
import com.runto.controler.utils.Entity;
import com.runto.percent.databinding.PercentSettingLayoutBinding;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/percent/percentNameActivity")
public class PercentMainActivity extends BaseActivity {

    public String layoutType;
    public PercentNickNameLayoutBinding nickBinding;
    public PercentNameLayoutBinding nameBinding;
    public PercentCollectionLayoutBinding collectionBinding;
    public CollectionAdapter collectionAdapter;
    public ArrayList<HomeSchoolBean> collectionList = new ArrayList<>();
    public PercentAboutLayoutBinding aboutBinding;
    public PercentHelpLayoutBinding helpBinding;
    public PercentSettingLayoutBinding setBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutType = getIntent().getStringExtra(Entity.PERCENT_LAYOUT_TYPE);
        switch (layoutType){
            case Entity.PERCENT_NICK_NAME://昵称
                nickBinding = DataBindingUtil.setContentView(PercentMainActivity.this, R.layout.percent_nick_name_layout);
                initNickLayoutData();
                break;
            case Entity.PERCENT_NAME://姓名
                nameBinding = DataBindingUtil.setContentView(PercentMainActivity.this, R.layout.percent_name_layout);
                initNameLayoutData();
                break;
            case Entity.PERCENT_COLLECTION://收藏
                collectionBinding = DataBindingUtil.setContentView(PercentMainActivity.this, R.layout.percent_collection_layout);
                initCollectionLayout();
                break;
            case Entity.PERCENT_ABOUT://关于
                aboutBinding = DataBindingUtil.setContentView(this, R.layout.percent_about_layout);
                initAboutLayoutData();
                break;
            case Entity.PERCENT_HELP://帮助与反馈
                helpBinding = DataBindingUtil.setContentView(this, R.layout.percent_help_layout);
                initHelpLayoutData();
                break;
            case Entity.PERCENT_SETTING://设置
                setBinding = DataBindingUtil.setContentView(this, R.layout.percent_setting_layout);
                initSettingLayoutData();
                break;
        }
    }

    private void initAboutLayoutData() {
        aboutBinding.aboutVersionExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aboutBinding.aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        aboutBinding.aboutHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initHelpLayoutData() {
        helpBinding.helpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        helpBinding.helpLimitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helpBinding.helpNotice.length() > 0){

                } else {
                    CommonUtils.showToast(PercentMainActivity.this, "请输入反馈内容");
                }
            }
        });

        helpBinding.helpHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initSettingLayoutData() {
        //退出登录
        setBinding.setLimitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setBinding.setBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setBinding.setPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/settingPassword").navigation();
            }
        });
    }

    //修改昵称
    public void initNickLayoutData(){
        nickBinding.nickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nickBinding.nickClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickBinding.nickEdit.setText("");
            }
        });

        nickBinding.nickSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nickBinding.nickEdit.length() > 0){

                } else {
                    CommonUtils.showToast(PercentMainActivity.this, "请输入昵称");
                }
            }
        });
    }
    //修改姓名
    public void initNameLayoutData(){
        nameBinding.nameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nameBinding.nameClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameBinding.nameEdit.setText("");
            }
        });

        nameBinding.nameSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameBinding.nameEdit.length() > 0){

                } else {
                    CommonUtils.showToast(PercentMainActivity.this, "请输入姓名");
                }
            }
        });
    }

    //我的收藏
    public void initCollectionLayout(){
        HomeSchoolBean homeSchoolBean = new HomeSchoolBean();
        homeSchoolBean.setAddress("玉兴路2号");
        homeSchoolBean.setTitle("瑞思学科英语");
        collectionList.add(homeSchoolBean);
        collectionBinding.percentCollectionRecycler.setLayoutManager(new LinearLayoutManager(this));
        collectionAdapter = new CollectionAdapter(this, collectionList);
        collectionBinding.percentCollectionRecycler.setAdapter(collectionAdapter);

        collectionAdapter.setOnClickListen(new CollectionAdapter.onClickListen() {
            @Override
            public void setOnClickListen(View v, int position, HomeSchoolBean homeSchoolBean) {
                //查看详情的监听事件
            }
        });
    }
}

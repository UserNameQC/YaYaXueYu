package com.runto.yayaxueyu.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.runto.controler.adapter.FragmentAdapter;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.databinding.ActivityHomeBinding;
import com.runto.yayaxueyu.fragment.HomeFragment;
import com.runto.yayaxueyu.fragment.MapFragment;
import com.runto.yayaxueyu.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/home/homeActivity")
public class HomeActivity extends BaseActivity {

    public ActivityHomeBinding homeBinding;
    public List<TextView> textViews = new ArrayList<>();
    public List<ImageView> imageViews = new ArrayList<>();

    public ArrayList<Fragment> fragments = new ArrayList<>();
    public FragmentManager fragmentManager;
    public FragmentAdapter fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        StatusBarUtil.setTranslucent(this);
        fragmentManager = getSupportFragmentManager();
        initView();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

    public void initView(){
        //初始化底部菜单按钮
        textViews.add(homeBinding.homeText);
        textViews.add(homeBinding.homeMapText);
        textViews.add(homeBinding.homeMineText);

        imageViews.add(homeBinding.homeImage);
        imageViews.add(homeBinding.homeMapImage);
        imageViews.add(homeBinding.homeMineImage);

        HomeFragment homeFragment = new HomeFragment();
        MapFragment mapFragment = new MapFragment();
        MineFragment mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(mapFragment);
        fragments.add(mineFragment);

        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        setMenuSelector(0);
        homeBinding.homeMainViewpager.setAdapter(fragmentAdapter);
        initEvent();
    }

    public void initEvent(){
        homeBinding.homeHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMenuSelector(0);
            }
        });

        homeBinding.homeMapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMenuSelector(1);
            }
        });

        homeBinding.homeMineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMenuSelector(2);
            }
        });

        homeBinding.homeMainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setMenuSelector(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }

    /**
     * 选中指定的菜单项并显示对应的Fragment
     *
     * @param index
     */
    private void setMenuSelector(int index) {
        reSetSelected();
        textViews.get(index).setSelected(true);
        imageViews.get(index).setSelected(true);
        homeBinding.homeMainViewpager.setCurrentItem(index, false);
    }

    /**
     * 重置底部菜单所有ImageView和TextView为未选中状态
     */
    private void reSetSelected() {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setSelected(false);
            imageViews.get(i).setSelected(false);
        }
    }
}

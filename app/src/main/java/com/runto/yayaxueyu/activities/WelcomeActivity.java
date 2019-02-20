package com.runto.yayaxueyu.activities;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends BaseActivity {

    public ActivityWelcomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        StatusBarUtil.setTranslucent(this);
        initWelcomeImage();
    }
    public void initWelcomeImage(){

        binding.welcomeImage.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build("/main/loginActivity").navigation();
            }
        }, 3000);
    }

}

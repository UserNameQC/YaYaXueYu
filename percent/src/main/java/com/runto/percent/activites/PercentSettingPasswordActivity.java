package com.runto.percent.activites;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.runto.percent.R;
import com.runto.percent.databinding.PecentSettingPasswordBinding;
import com.runto.percent.databinding.PercentEditPasswordBinding;

@Route(path = "percent/settingPassword")
public class PercentSettingPasswordActivity extends BaseActivity{

    public PecentSettingPasswordBinding passwordBinding;
    public PercentEditPasswordBinding editPasswordBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordBinding = DataBindingUtil.setContentView(this, R.layout.pecent_setting_password);
    }

    public void initEvent(){
        passwordBinding.passwordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //获取验证码
        passwordBinding.passwordGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        passwordBinding.passwordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPasswordBinding = DataBindingUtil.setContentView(PercentSettingPasswordActivity.this, R.layout.percent_edit_password);
            }
        });
    }
}

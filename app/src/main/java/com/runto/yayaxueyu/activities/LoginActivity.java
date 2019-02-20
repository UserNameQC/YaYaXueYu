package com.runto.yayaxueyu.activities;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.runto.controler.utils.CommonUtils;
import com.runto.controler.utils.DialogUtils;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.databinding.ActivityLoginBinding;

import java.util.Timer;
import java.util.TimerTask;


@SuppressLint("ResourceAsColor")
@Route(path = "/main/loginActivity")
public class LoginActivity extends BaseActivity {

    public ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initTypeEdit();
        initLoginLayoutData();
    }



    public void initTypeEdit(){
        String tag = loginBinding.loginTypeText.getTag().toString();
        switch (tag){
            case "PASSWORD":
                loginBinding.loginTypeText.setHint("输入密码");
                loginBinding.loginTypeText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                loginBinding.loginGetCode.setVisibility(View.GONE);
                break;
            case "CODE":
                loginBinding.loginTypeText.setHint("输入验证码");
                loginBinding.loginTypeText.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                loginBinding.loginGetCode.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void initLoginLayoutData(){
        //账号密码登录与验证码登录切换按钮
        loginBinding.loginTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = loginBinding.loginTypeText.getTag().toString();
                switch (tag){
                    case "PASSWORD":
                        loginBinding.loginTypeText.setTag("CODE");
                        loginBinding.loginTypeText.setText("账号密码登陆");
                        loginBinding.loginTitle.setText("验证码登陆");
                        loginBinding.loginGetCode.setVisibility(View.VISIBLE);
                        loginBinding.loginCode.setHint("输入验证码");
                        loginBinding.loginCode.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case "CODE":
                        loginBinding.loginTypeText.setTag("PASSWORD");
                        loginBinding.loginTypeText.setText("验证码登陆");
                        loginBinding.loginTitle.setText("账号密码登陆");
                        loginBinding.loginGetCode.setVisibility(View.GONE);
                        loginBinding.loginCode.setHint("输入密码");
                        loginBinding.loginCode.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                        break;
                }
            }
        });

        loginBinding.loginPhoneNum.addTextChangedListener(new TextWatcher() {
            String type = loginBinding.loginTypeText.getTag().toString();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 11) {
                    if (type.equals("CODE")) {
                        loginBinding.loginGetCode.setTextColor(R.color.text_color_3);
                    }
                } else {
                    if (type.equals("CODE")) {
                        loginBinding.loginGetCode.setTextColor(R.color.text_color_2);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 11) {
                    if (type.equals("CODE")) {
                        loginBinding.loginGetCode.setTextColor(R.color.text_color_3);
                    }
                } else {
                    if (type.equals("CODE")) {
                        loginBinding.loginGetCode.setTextColor(R.color.text_color_2);
                    }
                }

                if (loginBinding.loginCode.length() > 0 && s.length() > 0){
                    loginBinding.loginNext.setBackgroundResource(R.drawable.drawable_help_limit_back);
                } else{
                    loginBinding.loginNext.setBackgroundResource(R.drawable.drawable_login_btn_back);
                }
            }
        });

        loginBinding.loginCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginBinding.loginPhoneNum.length() > 0 && loginBinding.loginCode.length() > 0)
                    loginBinding.loginNext.setBackgroundResource(R.drawable.drawable_help_limit_back);
                else
                    loginBinding.loginNext.setBackgroundResource(R.drawable.drawable_login_btn_back);
            }
        });
        //登陆
        loginBinding.loginNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = loginBinding.loginTypeText.getTag().toString();
                if (loginBinding.loginPhoneNum.length() == 0){
                    CommonUtils.showToast(LoginActivity.this, "请输入手机号码");
                    return;
                }
                if (loginBinding.loginPhoneNum.length() != 11){
                    CommonUtils.showToast(LoginActivity.this, "请输入正确的手机号码");
                    return;
                }
                if (loginBinding.loginCode.length() == 0){
                    if (type.equals("PASSWORD"))
                        CommonUtils.showToast(LoginActivity.this, "请输入密码");
                    else
                        CommonUtils.showToast(LoginActivity.this, "请输入验证码");
                    return;
                }
                ARouter.getInstance().build("/home/homeActivity").navigation();
            }
        });
        //获取验证码
        loginBinding.loginGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBinding.loginPhoneNum.length() != 11){
                    CommonUtils.showToast(LoginActivity.this, "请输入正确的手机号");
                } else {
                    loginBinding.loginGetCode.setTextColor(R.color.text_color_2);
                    timers();
                }
            }
        });
        //微信登陆
        loginBinding.loginTypeWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBinding.loginPhoneNum.setText("");
                loginBinding.loginCode.setText("");
                loginBinding.loginTypeWeixin.setVisibility(View.GONE);
                loginBinding.loginTypeText.setVisibility(View.GONE);
                loginBinding.loginBottomType.setVisibility(View.GONE);
                loginBinding.loginBindingPhone.setVisibility(View.VISIBLE);
                loginBinding.loginTitle.setText("绑定关联手机号");
                if (loginBinding.loginGetCode.getVisibility() == View.GONE){
                    loginBinding.loginGetCode.setVisibility(View.VISIBLE);
                }
            }
        });
        //返回键
        loginBinding.loginBindingPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBinding.loginBindingPhone.setVisibility(View.GONE);
                String type = loginBinding.loginTypeText.getTag().toString();
                if (type.equals("PASSWORD"))
                    loginBinding.loginTitle.setText("账号密码登陆");
                else
                    loginBinding.loginTitle.setText("验证码登陆");
                loginBinding.loginTypeText.setVisibility(View.VISIBLE);
                loginBinding.loginTypeWeixin.setVisibility(View.VISIBLE);
                loginBinding.loginBottomType.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 计时器
     */
    public int time;
    private Timer timer = null;
    private TimerTask task = null;

    public void timers() {
        time = 60;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                time--;
                if (time == 0) {
                    handler.sendEmptyMessage(0);
                } else {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = time;
                    handler.sendMessage(message);
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    public Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    timer.cancel();
                    task.cancel();
                    loginBinding.loginGetCode.setTextColor(R.color.text_color_3);
                    loginBinding.loginGetCode.setText("重新获取");
                    break;
                case 1:
                    String time = msg.obj.toString();
                    loginBinding.loginGetCode.setText("重新获取("+time+")");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (timer != null){
            timer.cancel();
            timer = null;
        }

        if (task != null){
            task.cancel();
            task = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timer != null && task != null){
            timer.cancel();
            task.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer != null && task != null){
            timer.cancel();
            task.cancel();
        }
    }
}

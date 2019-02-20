package com.runto.percent.activites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

@SuppressLint("Registered")
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setStatusBar() {
        //StatusBarUtil.setColor(this, 0xffffff, 80);
    }
}

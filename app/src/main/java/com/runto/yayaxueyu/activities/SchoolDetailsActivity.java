package com.runto.yayaxueyu.activities;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.runto.controler.utils.DialogUtils;
import com.runto.controler.utils.Entity;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.databinding.ActivitySchoolDotailsBinding;

@Route(path = "/main/schoolDetailsActivity")
public class SchoolDetailsActivity extends BaseActivity {

    public ActivitySchoolDotailsBinding binding;
    public Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_school_dotails);
        initView();
    }

    public void initView(){
        binding.schoolPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = DialogUtils.showDialogWithLayout(SchoolDetailsActivity.this, R.layout.show_school_phone_number_layout);
                    dialog.show();
                }

                dialog.findViewById(R.id.call_number_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ((TextView) dialog.findViewById(R.id.school_phoneNumber)).setText("15569633256");

                dialog.findViewById(R.id.call_number).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调起电话
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + "15569633256");
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
            }
        });

        binding.schoolItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/main/mapActivity")
                        .withString(Entity.MAP_TYPE, Entity.MAP_SCHOOL_DETAILS).navigation();
            }
        });
    }
}

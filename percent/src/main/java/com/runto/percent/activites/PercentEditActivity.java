package com.runto.percent.activites;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.runto.controler.utils.DialogUtils;
import com.runto.controler.utils.Entity;
import com.runto.percent.R;
import com.runto.percent.databinding.ActivityPercentEditBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

@Route(path = "/percent/percentEditActivity")
public class PercentEditActivity extends BaseActivity {

    public ActivityPercentEditBinding binding;
    public TimePickerView timePickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_percent_edit);
        initData();
    }

    public void initData(){
        binding.editBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //头像设置
        binding.editAvatarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAvatarDialog();
            }
        });
        //昵称
        binding.editNickNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentNameActivity").withString(Entity.PERCENT_LAYOUT_TYPE, Entity.PERCENT_NICK_NAME)
                        .navigation();
            }
        });
        //姓名
        binding.editNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/percent/percentNameActivity").withString(Entity.PERCENT_LAYOUT_TYPE, Entity.PERCENT_NAME)
                        .navigation();
            }
        });
        //性别
        binding.editGenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGenderDialog();
            }
        });
        //生日
        binding.editBirthdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBirthdayDialog();
            }
        });
    }

    //头像选择设置
    public void initAvatarDialog(){
        final Dialog dialog = DialogUtils.createBottomDialog(PercentEditActivity.this, R.layout.percent_avatar_selector_layout);
        dialog.show();

        dialog.findViewById(R.id.select_type_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.findViewById(R.id.select_type_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.findViewById(R.id.select_type_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
    }

    //性别选择
    public void initGenderDialog(){
        final Dialog dialog = DialogUtils.createBottomDialog(PercentEditActivity.this, R.layout.percent_gender_selector_layout);
        dialog.findViewById(R.id.select_type_man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.findViewById(R.id.select_type_women).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.findViewById(R.id.select_type_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

    public void initBirthdayDialog(){
        timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String birth = format.format(date);
                binding.editBirthdayText.setText(birth);
            }
        })
                .isCyclic(true)
                .isDialog(true)
                .build();

        Dialog mDialog = timePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }
}

package com.baiiu.dropdownmenu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.baiiu.dropdownmenu.databinding.ActivityDropMainBinding;
import com.baiiu.dropdownmenu.entity.FilterUrl;
import com.baiiu.dropdownmenu.fragmentSample.FilterActivity;
import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;

public class DropMainActivity extends AppCompatActivity implements OnFilterDoneListener, View.OnClickListener {

    ActivityDropMainBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drop_main);
        ButterKnife.bind(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        initFilterDropDownView();

        binding.mFilterContentView.setOnClickListener(this);
    }

    private void initFilterDropDownView() {
        String[] titleList = new String[] { "第一个", "第二个", "第三个", "第四个" };
        binding.dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, this));
    }

    @Override public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            binding.dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        binding.dropDownMenu.close();
        binding.mFilterContentView.setText(FilterUrl.instance()
                                           .toString());
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        FilterUrl.instance()
                .clear();
    }

    @Override public void onClick(View view) {
        startActivity(new Intent(this, FilterActivity.class));
    }

}

package com.baiiu.dropdownmenu.fragmentSample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import com.baiiu.dropdownmenu.DropMenuAdapter;
import com.baiiu.dropdownmenu.R;
import com.baiiu.dropdownmenu.databinding.ActivityDropMainBinding;
import com.baiiu.dropdownmenu.entity.FilterUrl;
import com.baiiu.filter.interfaces.OnFilterDoneListener;

/**
 * author: baiiu
 * date: on 18/1/8 11:52
 * description:
 */
public class FilterFragment extends Fragment implements OnFilterDoneListener {

    ActivityDropMainBinding binding;
    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_drop_main, container, false);

        ButterKnife.bind(this, binding.getRoot());

        binding.toolbar.setTitle(R.string.app_name);

        return binding.getRoot();
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFilterDropDownView();
    }

    private void initFilterDropDownView() {
        String[] titleList = new String[] { "第一个", "第二个", "第三个", "第四个" };
        binding.dropDownMenu.setMenuAdapter(new DropMenuAdapter(getContext(), titleList, this));
    }

    @Override public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            binding.dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        binding.dropDownMenu.close();
        binding.mFilterContentView.setText(FilterUrl.instance()
                                           .toString());
    }

    @Override public void onDestroy() {
        super.onDestroy();
        FilterUrl.instance()
                .clear();
    }
}

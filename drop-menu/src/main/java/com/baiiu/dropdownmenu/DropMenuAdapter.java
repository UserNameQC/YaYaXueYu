package com.baiiu.dropdownmenu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baiiu.dropdownmenu.view.betterDoubleGrid.BetterDoubleGridView;
import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.baiiu.dropdownmenu.entity.FilterType;
import com.baiiu.dropdownmenu.entity.FilterUrl;

import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createSingleListView();
                break;
            case 1:
                view = createDoubleListView();
                break;
            case 2:
                //view = createSingleGridView();
                view = createGenDerListView();
                break;
//            case 3:
//                // view = createDoubleGrid();
//                view = createBetterDoubleGrid();
//                break;
        }

        return view;
    }

    private View createGenDerListView() {
        SingleListView<String> genderListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext, 0) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView, ImageView imageView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                }).onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });
        List<String> age = new ArrayList<>();
        age.add("三岁");
        age.add("四岁");
        age.add("五岁");
        age.add("六岁");
        age.add("七岁");
        age.add("八岁");
        age.add("九岁");
        genderListView.setList(age, -1);
        return genderListView;
    }

    private View createSingleListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext, 0) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView, ImageView imageView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                        //checkedTextView.setTextColor(mContext.getResources().getColor(R.color.text_color_1));
                    }

                    @Override
                    protected void initCheckedLayout(RelativeLayout layout) {
                        layout.setBackgroundResource(R.color.home_address_text_color);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("英语");
        list.add("日语");
        list.add("法语");
        singleListView.setList(list, -1);
        singleListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        singleListView.setDivider(mContext.getResources().getDrawable(R.drawable.drawable_listview_divider));

        return singleListView;
    }


    private View createDoubleListView() {
        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext, 1) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView, ImageView imageView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, mContext, 0) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView, ImageView imageView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }

                    @Override
                    protected void initCheckedLayout(RelativeLayout layout) {
                        layout.setBackgroundResource(R.color.home_address_text_color);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = 1;
                            FilterUrl.instance().positionTitle = item.desc;

                            onFilterDone();
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String string) {
                        FilterUrl.instance().doubleListLeft = item.desc;
                        FilterUrl.instance().doubleListRight = string;

                        FilterUrl.instance().position = 1;
                        FilterUrl.instance().positionTitle = string;

                        onFilterDone();
                    }
                });


        List<FilterType> list = new ArrayList<>();
        List<List<String>> allList = new ArrayList<>();
        String[] address = {"附件", "历下区", "历城区", "市中区", "槐荫区", "长清区", "高新区"};
        //附件菜单
        List<String> desList = new ArrayList<>();
        desList.add("1km");
        desList.add("2km");
        desList.add("3km");
        desList.add("3km");
        desList.add("10km");
        allList.add(desList);

        //历下区菜单
        List<String> LxList = new ArrayList<>();
        LxList.add("解放路街道");
        LxList.add("万科金域");
        LxList.add("奥体中心");
        allList.add(LxList);

        List<String> lcList = new ArrayList<>();
        lcList.add("汉峪金谷");
        lcList.add("顺泰广场");
        allList.add(lcList);

        List<String> szList = new ArrayList<>();
        szList.add("趵突泉");
        szList.add("泉城广场");
        szList.add("大明湖");
        allList.add(szList);

        List<String> hyList = new ArrayList<>();
        hyList.add("济南西站");
        allList.add(hyList);

        List<String> cqList = new ArrayList<>();
        cqList.add("恒大新城");
        cqList.add("大学城");
        allList.add(cqList);

        List<String> gxList = new ArrayList<>();
        gxList.add("万达广场");
        gxList.add("汉峪金谷");
        allList.add(gxList);

        //第一项
        FilterType filterType;
//        filterType.desc = "";
//        list.add(filterType);
        for (int i = 0; i < address.length; i++) {
            filterType = new FilterType();
            filterType.desc = address[i];
            filterType.child = allList.get(i);
            list.add(filterType);
        }

        //初始化选中.
        comTypeDoubleListView.setLeftList(list, 1);
        comTypeDoubleListView.setRightList(list.get(1).child, -1);
        //comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


    private View createSingleGridView() {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext, 1) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView, ImageView imageView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleGridPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();

                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 20; i < 39; ++i) {
            list.add(String.valueOf(i));
        }
        singleGridView.setList(list, -1);


        return singleGridView;
    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


//    private View createDoubleGrid() {
//        DoubleGridView doubleGridView = new DoubleGridView(mContext);
//        doubleGridView.setOnFilterDoneListener(onFilterDoneListener);
//
//
//        List<String> phases = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            phases.add("3top" + i);
//        }
//        doubleGridView.setTopGridData(phases);
//
//        List<String> areas = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            areas.add("3bottom" + i);
//        }
//        doubleGridView.setBottomGridList(areas);
//
//        return doubleGridView;
//    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(0, "", "");
        }
    }

}

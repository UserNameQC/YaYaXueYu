package com.baiiu.filter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiiu.filter.R;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;

import java.util.List;

/**
 * Created by baiiu on 15/12/23.
 * 菜单条目适配器
 */
public abstract class SimpleTextAdapter<T> extends BaseBaseAdapter<T> {

    private final LayoutInflater inflater;
    public static int type;

    public SimpleTextAdapter(List<T> list, Context context, int type) {
        super(list, context);
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    public static class FilterItemHolder implements Checkable {
        FilterCheckedTextView checkedTextView;
        ImageView imageView;
        boolean checked;

        @Override
        public void setChecked(boolean checked) {
            if (this.checked != checked){
                this.checked = checked;
            }
            if (type != 1) {
                if (checked)
                    imageView.setVisibility(View.VISIBLE);
                else
                    imageView.setVisibility(View.GONE);
            }
        }

        @Override
        public boolean isChecked() {
            return checked;
        }

        @Override
        public void toggle() {
            setChecked(!checked);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FilterItemHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_item_filter, parent, false);

            holder = new FilterItemHolder();
            holder.checkedTextView = convertView.findViewById(R.id.tv_item_filter);
            holder.imageView = convertView.findViewById(R.id.selected_image);
            //holder.checkedTextView.setPadding(0, UIUtil.dp(context, 15), 0, UIUtil.dp(context, 15));
            initCheckedTextView(holder.checkedTextView, holder.imageView);
            initCheckedLayout((RelativeLayout) convertView.findViewById(R.id.drop_menu_layout));

            convertView.setTag(holder);
        } else {
            holder = (FilterItemHolder) convertView.getTag();
        }

        T t = list.get(position);
        holder.checkedTextView.setText(provideText(t));
//        holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean checked = holder.checkedTextView.isChecked();
//                if (checked)
//                    holder.imageView.setVisibility(View.VISIBLE);
//                else
//                    holder.imageView.setVisibility(View.GONE);
//            }
//        });


        return convertView;
    }

    public abstract String provideText(T t);

    protected void initCheckedTextView(FilterCheckedTextView checkedTextView, ImageView imageView) {
    }

    protected void initCheckedLayout(RelativeLayout layout){}


}

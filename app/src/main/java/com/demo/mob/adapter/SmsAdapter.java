package com.demo.mob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.bean.MenuItem;
import com.demo.mob.bean.SmsItem;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by chenjt on 2017/1/9.
 */

public class SmsAdapter extends BaseAdapter{

    private List<SmsItem> list;
    private Context context;
    private LayoutInflater inflater;

    public SmsAdapter(Context context, List list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item;
        if (view == null){
            item = new Item();
            view = inflater.inflate(R.layout.sms_list_menu_item,null);
            item.country = (TextView) view.findViewById(R.id.sms_right_menu_item_country);
            item.number = (TextView) view.findViewById(R.id.sms_right_menu_item_number);
            view.setTag(item);
            AutoUtils.autoSize(view);
        }else{
            item = (Item) view.getTag();
        }
        item.country.setText(list.get(i).getCountry());
        item.number.setText(list.get(i).getNumber());
        return view;
    }
    class Item{
        TextView country;
        TextView number;
    }
}

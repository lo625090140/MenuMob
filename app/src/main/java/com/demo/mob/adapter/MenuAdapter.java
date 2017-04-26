package com.demo.mob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;




import java.util.List;

import com.demo.mob.activity.R;
import com.demo.mob.bean.MenuItem;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by chenjt on 2017/1/9.
 */

public class MenuAdapter extends BaseAdapter{

    private List<MenuItem> list;
    private Context context;
    private LayoutInflater inflater;

    public MenuAdapter(Context context,List list){
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
        return list == null ? -1 : list.get(position).getSequence();
    }
    
    
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item;
        if (view == null){
            item = new Item();
            view = inflater.inflate(R.layout.main_list_menu_item,null);
            item.name = (TextView) view.findViewById(R.id.main_left_navigation_menu_item_text);
            view.setTag(item);
            AutoUtils.autoSize(view);
        }else{
            item = (Item) view.getTag();
        }
        item.name.setText(list.get(i).getName());
        return view;
    }
    class Item{
        TextView name;
    }
}

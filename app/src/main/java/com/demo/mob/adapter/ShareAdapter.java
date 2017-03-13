package com.demo.mob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.bean.AuthorizeItem;
import com.demo.mob.bean.ShareItem;

import java.util.List;

/**
 * Created by chenjt on 2017/1/9.
 */

public class ShareAdapter extends BaseAdapter{

    private List<ShareItem> list;
    private Context context;
    private LayoutInflater inflater;

    public ShareAdapter(Context context, List list){
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
        return list == null ? -1 : list.get(position).getSequence();}
    
    
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item;
        if (view == null){
            item = new Item();
            view = inflater.inflate(R.layout.share_list_menu_item,null);
            item.name = (TextView) view.findViewById(R.id.fragment_share_item_platform_name);
            item.icon = (ImageView) view.findViewById(R.id.fragment_share_item_platform_icon);
            view.setTag(item);
        }else{
            item = (Item) view.getTag();
        }

        item.icon.setImageResource(list.get(i).getIcon());
        item.name.setText(list.get(i).getName());
        return view;
    }



    class Item{
        TextView name;
        ImageView icon;
    }
}

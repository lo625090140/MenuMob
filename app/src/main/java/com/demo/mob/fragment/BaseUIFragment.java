package com.demo.mob.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.mob.activity.R;
import com.demo.mob.adapter.mSimpleAdapter;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.ToastUtil;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenjt on 2017/4/11.
 */

public class BaseUIFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private TextView add,remove;
    private List<String> list;
    private mSimpleAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_baseui;
    }

    @Override
    protected View getViews() {
        LinearLayout lv = new LinearLayout(context);
        LinearLayout.LayoutParams lvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setOrientation(LinearLayout.VERTICAL);
        lv.setLayoutParams(lvParams);

        Button btn = new Button(context);
        LinearLayout.LayoutParams btnParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(50,100,0,0);
        btn.setText("你好啊");
        btn.setLayoutParams(btnParams);
        btn.setOnClickListener((v) -> {
            Observable.just("Hello","setHello","Hello RxJava")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((String s) -> {ToastUtil.show(context,s);});
        });
        lv.addView(btn);


        return null;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 'A'; i <= 'z';i++){
            list.add((char)i + "");
        }
    }

    private void initView() {
        (add = (TextView) view.findViewById(R.id.id_recyclerview_add_item)).setOnClickListener(this);
        (remove = (TextView) view.findViewById(R.id.id_recyclerview_remove_item)).setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        adapter = new mSimpleAdapter(context,list);
        adapter.setOnItemClickListener((view, position) -> {
            Toast.makeText(context, position + "|||" + view.toString(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
//        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));


        DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        decoration.setDrawable(context.getResources().getDrawable(R.drawable.shape_recyclerview));
//        recyclerView.addItemDecoration(decoration);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_recyclerview_add_item :
                adapter.addItem(1,"Hello");
                break;
            case R.id.id_recyclerview_remove_item :
                adapter.removeItem(1);
                break;
        }
    }
}

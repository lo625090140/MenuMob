package com.demo.mob.fragment;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.ToastUtil;

/**
 * Created by chenjt on 2017/4/11.
 */

public class BaseUIFragment extends BaseFragment {
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

        lv.addView(btn);

        return lv;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View view) {

    }
}
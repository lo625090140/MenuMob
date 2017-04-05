package com.demo.mob.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.fragment.gameutils.Calculate;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.ToastUtil;

import java.util.Arrays;

/**
 * Created by chenjt on 2017/4/5.
 */

public class GameFragment extends BaseFragment {
    private Calculate game;
    private EditText time;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        time = (EditText) view.findViewById(R.id.fragment_game_time);
        view.findViewById(getResID("fragment_game_button_auto", "id")).setOnClickListener(this);
        game = new Calculate();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_game_button_auto){
            for (String str : getResources().getStringArray(R.array.game)) {
                String[] item = str.split(",");
                ((TextView) view.findViewById(getResID("fragment_game_ball_" + item[0], "id"))).setText("num");
            }
            Message msg = new Message();
            msg.what = 100;
            msg.arg1 = 1;
            handler.sendMessageDelayed(msg, getTime() * 1000, this);
            ToastUtil.show(context, getTime() + "秒后开启第1次摇号", getTime());
        }
    }

    private int getTime() {
        return !time.getText().toString().equals("") ? Integer.valueOf(time.getText().toString()) : 5;
    }

    private void setTextView(String item, int num) {
        game.startGeme(num);
        ((TextView) view.findViewById(getResID("fragment_game_ball_" + item, "id"))).setText(game.getNum());
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 100:
                if (msg.arg1 < 8) {
                    for (String str : getResources().getStringArray(R.array.game)) {
                        String[] item = str.split(",");
                        if (!(msg.arg1 == Integer.valueOf(item[1]))) {
                            continue;
                        }
                        setTextView(item[0], msg.arg1);
                    }
                    Message mg = new Message();
                    mg.what = 100;
                    mg.arg1 = ++msg.arg1;
                    if (mg.arg1 < 8)
                        ToastUtil.show(context, getTime() + "秒后第" + mg.arg1 + "次摇号", getTime());
                    handler.sendMessageDelayed(msg, getTime() * 1000, this);
                } else {
                    ((TextView) view.findViewById(getResID("fragment_game_text_result", "id"))).setText(game.getResult());
                    game.restart();
                }
                break;
        }
        return false;
    }
}

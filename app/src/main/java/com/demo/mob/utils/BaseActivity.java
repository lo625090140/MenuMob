package com.demo.mob.utils;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements Callback, OnClickListener {
    protected String Tag = this.getClass().getName();
    protected UIHandler handler = new UIHandler();

    public abstract boolean handleMessage(Message msg);

    protected abstract void initContentView(Bundle savedInstanceState);

    public abstract void onClick(View view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView(savedInstanceState);
    }

    protected void sendHandler(int what, Handler.Callback callback, Object... obj) {
        Message msg = new Message();
        msg.what = what;
        if (obj.length == 1) {
            msg.obj = obj[0];
        } else {
            Object[] objects = new Object[obj.length];
            for (int i = 0; i < obj.length; i++) {
                objects[i] = obj[i];
            }
            msg.obj = objects;
        }
        handler.sendMessage(msg, callback);
    }
    protected void exit(int i){
        finish();
        System.exit(i);
    }
}

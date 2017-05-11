package com.demo.mob.utils;


import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.demo.mob.activity.MainActivity;

public abstract class BaseActivity extends AppCompatActivity implements Callback, OnClickListener {
    protected String Tag = App.TAG = this.getClass().getSimpleName();
    protected UIHandler handler = new UIHandler();

    public boolean handleMessage(Message msg) {
        return false;
    }

    protected abstract void initContentView(Bundle savedInstanceState);

    public abstract void onClick(View view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.exercise(Tag,"onCreate");
        initContentView(savedInstanceState);
    }
    public int getResID(String name, String type) {
        return getResources().getIdentifier(name, type,getPackageName());
    }

    protected void sendHandler(int what, Callback callback, Object... obj) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.launcherMainIfNecessary(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logs.exercise(Tag,"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logs.exercise(Tag,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logs.exercise(Tag,"onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logs.exercise(Tag,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logs.exercise(Tag,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logs.exercise(Tag,"onDestroy");
    }
}

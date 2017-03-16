package com.demo.mob.utils;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler.Callback;
import android.view.View.OnClickListener;

/**
 * Created by chenjt on 2017/1/11.
 */

public abstract class BaseFragment extends Fragment implements Callback,OnClickListener {
    protected UIHandler handler = new UIHandler();
    protected String Tag = this.getClass().getName();
    protected Context context;
    protected View view;
    //获取布局文件ID
    protected abstract int getLayoutId();

    protected View getViews() {
        return null;
    }

    protected abstract void initContentView(Bundle savedInstanceState);
    public abstract boolean handleMessage(Message msg);
    public abstract void onClick(View view);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(Tag,"onCreateView");
        view = getViews();
        context = getActivity();
        if (view == null){
            view = inflater.inflate(getLayoutId(),container,false);
        }
        initContentView(savedInstanceState);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(Tag,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(Tag,"onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(Tag,"onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(Tag,"onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Tag,"onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(Tag,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(Tag,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(Tag,"onDetach");
    }

    protected void sendHandler(int what, Handler.Callback callback, Object... obj){
        Message msg = new Message();
        msg.what = what;
        if (obj.length == 1){
            msg.obj = obj[0];
        }else{
            Object[] objects = new Object[obj.length];
            for (int i = 0; i < obj.length;i++){
                objects[i] = obj[i];
            }
            msg.obj = objects;
        }
        handler.sendMessage(msg, callback);
    }
}

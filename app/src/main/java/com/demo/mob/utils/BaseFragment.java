package com.demo.mob.utils;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;



/**
 * Created by chenjt on 2017/1/11.
 */

public abstract class BaseFragment extends Fragment implements Callback,OnClickListener {
    protected UIHandler handler = new UIHandler();
    protected String Tag = this.getClass().getSimpleName();
    protected Context context;
    protected View view;


    /**
     * 获取布局文件ID
     */
    protected int getLayoutId() {
        return getArguments().getInt("layout");
    }

    protected View getViews() {
        return view;
    }

    protected abstract void initContentView(Bundle savedInstanceState);

    public boolean handleMessage(Message msg) {
        return false;
    }

    public abstract void onClick(View view);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logs.exercise(Tag,"onCreateView");
        context = getActivity();
        view = getViews();
        if (view == null){
            view = inflater.inflate(getLayoutId(),container,false);
        }
        initContentView(savedInstanceState);
        return view;

    }

    public int getResID(String name, String type) {
        return getResources().getIdentifier(name, type, context.getPackageName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logs.exercise(Tag,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logs.exercise(Tag,"onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logs.exercise(Tag,"onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logs.exercise(Tag,"onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logs.exercise(Tag,"onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.exercise(Tag,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs.exercise(Tag,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logs.exercise(Tag,"onDetach");
    }

    protected void sendHandler(int what, Callback callback, Object... obj){
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

package com.demo.mob.utils;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/4/2.
 */

public class FragmentUtil {
    public static BaseFragment newInstance(Bundle bundle)
    {
        return newInstance(bundle,BaseFragment.class);
    }
    public static BaseFragment newInstance(Bundle bundle,Class<? extends BaseFragment> clazz)
    {
        BaseFragment pane = null;
        try {
            pane = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putAll(bundle);
        pane.setArguments(args);
        return pane;
    }


    /**
     * 设置碎片实例化的Bundle（布局）
     */
    public static Bundle setBundle(int layout){
        return setBundle(layout,null);
    }
    /**
     * 设置碎片实例化的Bundle（传递消息）
     */
    public static Bundle setBundle(String msg){
        return setBundle(0,msg);
    }
    /**
     * 设置碎片实例化的Bundle（传递消息 + 布局）
     */
    public static Bundle setBundle(int layout,String msg){
        Bundle bundle = new Bundle();
        if(layout != 0)bundle.putInt("layout",layout);
        if(msg != null)bundle.putString("msg",msg);
        return bundle;
    }
}

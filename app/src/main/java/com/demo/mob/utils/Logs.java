package com.demo.mob.utils;

import android.util.Log;

/**
 * Created by chenjt on 2017/3/9.
 */

public class Logs {
    /**
     * 是否开启log模式
     */
    private static boolean debug = true;
    /**
     * 是否开启监听生命周期的log
     */
    private static boolean life_debug = true;

    public static void e(String tag,String msg){
        if (debug) Log.e(tag,msg);
    }
    public static void exercise(String tag,String msg){
        if (life_debug) Log.e(tag,msg);
    }
}

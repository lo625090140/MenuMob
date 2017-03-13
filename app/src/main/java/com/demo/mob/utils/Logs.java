package com.demo.mob.utils;

import android.util.Log;

/**
 * Created by chenjt on 2017/3/9.
 */

public class Logs {
    private static boolean debug = true;

    public static void e(String tag,String msg){
        if (debug) Log.e(tag,msg);
    }
}

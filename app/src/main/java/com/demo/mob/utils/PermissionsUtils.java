package com.demo.mob.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;

/**
 * Created by chenjt on 2017/1/21.
 */

public class PermissionsUtils {
    public static final int MULTIPLE = 0x00000011;
    public static final int READ_PHONE_STATE = 0x00000001;
    public static final int ACCESS_FINE_LOCATION = 0x00000002;
    public static final int WRITE_EXTERNAL_STORAGE = 0x00000003;
    public static final int RECEIVE_SMS = 0x00000004;

    public static boolean hasPermission(Activity context, String Permission, int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Permission) != PackageManager.PERMISSION_GRANTED) {
                context.requestPermissions(new String[]{Permission}, type);
                return false;
            }
        }
        return true;
    }

    public static boolean morePermission(Activity context, String[] Permissions, int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> list = new ArrayList<String>();
            for (String Permission : Permissions) {
                if (context.checkSelfPermission(Permission) != PackageManager.PERMISSION_GRANTED) {
                    list.add(Permission);
                }
            }
            if (!list.isEmpty()){
                context.requestPermissions(list.toArray(new String[list.size()]), type);
            }
            return true;
        }else{
            return false;
        }

    }



}

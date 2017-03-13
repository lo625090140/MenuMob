package com.demo.bmob;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenjt on 2017/1/20.
 */

public class SavePx {
    private Context c;
    public void uploading(Activity activity){
        c = activity;
        Bmob.initialize(c, "de2009dacf34cf355e03d9cc1ae006e1");
        Display display = activity.getWindowManager().getDefaultDisplay();
        review_internet_SQLite(display.getWidth() + "",display.getHeight() + "");
    }
    private void review_internet_SQLite(final String width,final String height){
        final String device = ((TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        String bql = "select * from screen_resolution where DeviceId='" + device + "'";
        new BmobQuery<screen_resolution>().doSQLQuery(bql,new SQLQueryListener<screen_resolution>(){
            public void done(BmobQueryResult<screen_resolution> result, BmobException e) {
                if(e ==null){
                    List<screen_resolution> list = (List<screen_resolution>) result.getResults();
                    if(list!=null && list.size()>0){
                        for (screen_resolution screen_resolution : list) {
                            if (!screen_resolution.getDeviceId().equals(device)) {
                                add_internet_SQLite(width, height, device);
                            }else {
                                continue;
                            }
                        }
                    }else{
                        add_internet_SQLite(width, height, device);
                    }
                }else{
                    Log.e("review", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });

    }



    private void add_internet_SQLite(String width,String height,String deviceId){
        Build bd = new Build();
        String model = bd.MODEL;
        screen_resolution p2 = new screen_resolution();
        p2.setWidth(width);
        p2.setHeight(height);
        p2.setDeviceId(deviceId);
        p2.setModel(!model.equals("") ? model : "型号为空");
        p2.save(new SaveListener<String>(){
            @Override
            public void done(String arg0, BmobException e) {
                if (e == null)  {
                }else{
                    Log.e("add", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }

            }

        });
    }
}

package com.demo.mob.link_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.demo.mob.activity.R;
import com.demo.mob.utils.BaseActivity;
import com.mob.moblink.ActionListener;
import com.mob.moblink.MobLink;

import java.util.HashMap;
import java.util.Map;

public class Activity_B extends BaseActivity {
    private String path = "/demo/b";
    public static final String APPKEY = "1c12352b1abd0";//正式部署到测试环境 //测试 d9b58a07cbf4
    private String source;
    private String paramStr = "";
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activityb);
        init();
    }

    private void init() {

    }

    @Override
    public void onClick(View view) {

    }
    protected void onResume() {
        super.onResume();
        //初始化moblink SDK
        MobLink.initSDK(this, APPKEY);
        //设置场景还原监听
        MobLink.setIntentHandler(getIntent(), new ActionListener() {
            public void onResult(final HashMap<String, Object> res) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (res != null) {
                            if (res.get("source") != null) {
                                source = String.valueOf(res.get("source"));
                            }
                            if (res.get("params") != null) {
                                HashMap<String, Object> params = (HashMap<String, Object>) res.get("params");
                                for (Map.Entry<String, Object> entry : params.entrySet()) {
                                    paramStr += (entry.getKey() + " : " + entry.getValue() + "\r\n");
                                }
                            }
                        }


                    }
                });
            }
            public void onError(Throwable t) {
                if (t != null) {
                    Log.e(Tag, t.getMessage());
                }
            }
        });

    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}

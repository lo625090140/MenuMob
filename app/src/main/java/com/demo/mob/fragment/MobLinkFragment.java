package com.demo.mob.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.utils.App;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.ToastUtil;
import com.mob.moblink.ActionListener;
import com.mob.moblink.MobLink;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by chenjt on 2017/3/15.
 */

public class  MobLinkFragment extends BaseFragment{
    public static final String[] MAIN_PATH_ARR = {"/a", "/b", "/c","/input"};
    public static final String SHARE_URL = "http://www.mobshare.top";//"http://f.moblink.mob.com";

    private String mobID;
    private TextView tv;
    private int selectedID;
    private String shareUrl;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_moblink;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        ShareSDK.initSDK(context);
        MobLink.initSDK(context, App.LINK_APPKEY);
        view.findViewById(R.id.mobid).setOnClickListener(this);
        view.findViewById(R.id.share).setOnClickListener(this);
        (tv = (TextView) view.findViewById(R.id.text)).setOnClickListener(this);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedID = 3;
                ToastUtil.show(context,"OK");
                return true;
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mobid:
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("AppClient","我可以用来还原场景啊");
                String source = "mob";
                MobLink.getMobID(params, MAIN_PATH_ARR[selectedID], source, new ActionListener() {
                    public void onResult(HashMap<String, Object> params) {
                        if (params != null && params.containsKey("mobID")) {
                            mobID = String.valueOf(params.get("mobID"));
                            Log.e("Tag_onResult", mobID);
                            ToastUtil.show(context,mobID);
                        } else {
                            ToastUtil.show(context,"Get MobID Failed!");
                        }
                    }

                    public void onError(Throwable t) {
                        if (t != null) {
                            ToastUtil.show(context,t.getMessage());
                        }
                    }
                });
                break;
            case R.id.share:
                shareUrl = SHARE_URL + MAIN_PATH_ARR[selectedID] + ".html";
                if (!TextUtils.isEmpty(mobID)) {
                    shareUrl += "?mobid=" + mobID;
                    // "http://f.moblink.mob.com/demo/a/?mobid="+mobID
                }
                String title = "我是测试的Title";
                final String text = "我是测试的Text";
                String imgPath = null;
                try {
                    imgPath = com.mob.tools.utils.BitmapHelper.saveBitmap(context, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
//                        copyImgToSD(context, R.mipmap.ic_launcher , "moblink");
                OnekeyShare oks = new OnekeyShare();
                oks.setTitle(title);;
                oks.setText(text);
                Log.e("Tag",imgPath);
                oks.setImagePath(imgPath);
                oks.setUrl(shareUrl);
                oks.setTitleUrl(shareUrl);
                oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                    @Override
                    public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                        if (platform.getName().equals("SinaWeibo")){
                            paramsToShare.setText(text + "\n" + shareUrl);
                            paramsToShare.setUrl(null);
                        }
                    }
                });
                Platform[] plats = ShareSDK.getPlatformList();
                for (Platform p : plats) {
                    if (!(p.getName().equals("SinaWeibo") ||
                            p.getName().equals("QZone") ||
                            p.getName().equals("QQ") ||
                            p.getName().equals("Wechat") ||
                            p.getName().equals("WechatMoments"))){
                        oks.addHiddenPlatform(p.getName());
                    }
                }
                oks.show(context);
                break;
            case R.id.text:
                if (tv.getText().toString().trim().equals("A")){
                    tv.setText("B");
                    selectedID = 1;
                }else if (tv.getText().toString().trim().equals("B")){
                    tv.setText("C");
                    selectedID = 2;
                }else if (tv.getText().toString().trim().equals("C")){
                    tv.setText("A");
                    selectedID = 0;
                }
                break;
        }
    }

    public static String copyImgToSD(Context context, int imgID, String imgName) {
        String imgPaht = "";
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgID);
        if (bitmap != null && !bitmap.isRecycled()) {
            String path = ResHelper.getImageCachePath(context);
            if (TextUtils.isEmpty(imgName)) {
                imgName = String.valueOf(System.currentTimeMillis());
            }
            File file = new File(path, imgName + ".jpg");
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                fos.flush();
                fos.close();
                imgPaht = file.getAbsolutePath();
            } catch (Throwable t) {
            }
        }
        return imgPaht;
    }
}

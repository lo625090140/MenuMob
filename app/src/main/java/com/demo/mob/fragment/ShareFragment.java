package com.demo.mob.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.adapter.ShareAdapter;
import com.demo.mob.bean.ShareItem;
import com.demo.mob.utils.App;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.BitmapUtil;
import com.demo.mob.utils.InitShareSDK;
import com.demo.mob.utils.LoginAnim;
import com.demo.mob.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by chenjt on 2017/1/10.
 */

public class ShareFragment extends BaseFragment implements AdapterView.OnItemClickListener, PlatformActionListener {
    private ListView platform_item;
    private List<ShareItem> list;
    private HashMap<String, Platform> map;
    private EditText title, titleurl, text, url, imageurl, imagepath, sharetype,musicurl,filepath;
    private CheckBox ShareClient,BypassApproval;
    private boolean isEmpty;
    private String path;
    private Dialog loadanim;// 加载动画
    // 分享加载动画
    private void ShareAnim(boolean isStart) {
        if (isStart){
            loadanim = new LoginAnim().login(context, "分享中...");
        }else{
            if (loadanim != null) loadanim.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ShareAnim(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        ShareSDK.initSDK(context);
        init();
    }

    private void init() {

        for (String str : getResources().getStringArray(R.array.params)) {
            if (str.equals("imagepath")){
                view.findViewById(getResID("fragment_share_tv_" + str, "id")).setOnLongClickListener(new View.OnLongClickListener() {
                    @SuppressWarnings({"deprecation", "WrongConstant"})
                    @Override
                    public boolean onLongClick(View v) {
                        ToastUtil.show(context,"复制完成");
                        ((ClipboardManager)context.getSystemService("clipboard")).setText(App.PATH);
                        return true;
                    }
                });
            }
            view.findViewById(getResID("fragment_share_tv_" + str, "id")).setOnClickListener(this);
        }
        title = (EditText) view.findViewById(R.id.fragment_share_Title);
        text = (EditText) view.findViewById(R.id.fragment_share_Text);
        url = (EditText) view.findViewById(R.id.fragment_share_Url);
        titleurl = (EditText) view.findViewById(R.id.fragment_share_TitleUrl);
        imageurl = (EditText) view.findViewById(R.id.fragment_share_ImageUrl);
        imagepath = (EditText) view.findViewById(R.id.fragment_share_ImagePath);
        musicurl = (EditText) view.findViewById(R.id.fragment_share_MusicUrl);
        sharetype = (EditText) view.findViewById(R.id.fragment_share_ShareType);
        filepath = (EditText) view.findViewById(R.id.fragment_share_FilePath);
        BypassApproval = (CheckBox) view.findViewById(R.id.fragment_share_item_platform_BypassApproval);
        ShareClient = (CheckBox) view.findViewById(R.id.fragment_share_item_platform_judge);
        (platform_item = (ListView) view.findViewById(R.id.fragment_share_menu)).setOnItemClickListener(this);
        list = new ArrayList<ShareItem>();
        map = new HashMap<String, Platform>();
        Platform[] plats = ShareSDK.getPlatformList();
        for (Platform plat : plats) {
            String name = plat.getName().toLowerCase();
            list.add(new ShareItem(getString(getResID("ssdk_" + name, "string")), getResID("ssdk_oks_classic_" + name, "drawable"), plat.getId()));
//            Log.e(Tag,name + "_" + plat.getName() + "_" + plat.getId());
            map.put(String.valueOf(plat.getId()), plat);
        }
        ShareAdapter adapter = new ShareAdapter(context, list);
        platform_item.setAdapter(adapter);
    }

    private int getResID(String name, String type) {
        return getResources().getIdentifier(name, type, context.getPackageName());
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ShareItem.NameConstant.MSG_SHARE_CANCEL:
            case ShareItem.NameConstant.MSG_SHARE_COMPLETE:
                ToastUtil.show(context, msg.obj.toString());
                setMessage(View.GONE,"");
                if(path != null ? !path.equals("") : false){
                    handler.sendEmptyMessageDelayed(200,5000,this);
                }
                ShareAnim(false);
                break;
            case ShareItem.NameConstant.MSG_SHARE_ERROR:
                Platform plat_Error = (Platform) ((Object[]) msg.obj)[0];
                Throwable throwable = (Throwable) ((Object[]) msg.obj)[1];
                throwable.printStackTrace();
                setMessage(View.VISIBLE,"错误信息 : \n" + throwable.getMessage());
                ToastUtil.show(context, "分享失败");
                ShareAnim(false);
                break;
            case 100:
                isEmpty = false;
                break;
            case 200:
                BitmapUtil.deleteDir(new File(path));
                path = "";
                break;
        }

        return false;
    }

    private void setMessage(int visibility , String message){
        ((TextView)view.findViewById(R.id.fragment_share_message_error)).setVisibility(visibility);
        ((TextView)view.findViewById(R.id.fragment_share_message_error)).setText(message);
    }


    @Override
    public void onClick(View view) {
        if (!isEmpty) {
            isEmpty = true;
            handler.sendEmptyMessageDelayed(100, 1000, this);
        } else {
            ((EditText) this.view.findViewById(getResID("fragment_share_" + ((TextView) view).getText(), "id"))).setText("");
            handler.sendEmptyMessage(100, this);
        }
    }

    private Platform.ShareParams params() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title.getText().toString().trim().equals("") ? "我是测试的Title" : title.getText().toString().trim().equals("1") ? "" : title.getText().toString().trim());
        sp.setText(text.getText().toString().trim().equals("") ? "我是测试的Text" : text.getText().toString().trim().equals("1") ? "" : text.getText().toString().trim());
        sp.setUrl(url.getText().toString().trim().equals("") ? "https://www.baidu.com" : url.getText().toString().trim().equals("1") ? "" : url.getText().toString().trim());
        sp.setTitleUrl(titleurl.getText().toString().trim().equals("") ? "https://www.baidu.com" : titleurl.getText().toString().trim().equals("1") ? "" : titleurl.getText().toString().trim());
        if (!imageurl.getText().toString().trim().equals("") && !imageurl.getText().toString().trim().equals("1")) {
            sp.setImageUrl(imageurl.getText().toString().trim());
        }
        if (!imagepath.getText().toString().trim().equals("") && !imagepath.getText().toString().trim().equals("1")) {
            BitmapUtil.BitmapSaveSD(imagepath.getText().toString().trim(), BitmapUtil.cutActivity(getActivity()));
            sp.setImagePath(imagepath.getText().toString().trim());
        }
        if ((imageurl.getText().toString().trim().equals("") && imagepath.getText().toString().trim().equals("")) && (!imageurl.getText().toString().trim().equals("1") && !imagepath.getText().toString().trim().equals("1"))) {
            sp.setImageUrl("https://dn-fdd8geyb.qbox.me/eb1cb2932dd8f3f1c42a.png");
        }
        sp.setMusicUrl(musicurl.getText().toString().trim().equals("") ? "" : musicurl.getText().toString().trim());
        sp.setShareType(type(sharetype.getText().toString().trim().toLowerCase()));
        sp.setFilePath(filepath.getText().toString().trim().equals("") ? "" : filepath.getText().toString().trim());
        return sp;
    }

    private int type(String type_tx) {
        if (type_tx.equals("apps")) {
            return Platform.SHARE_APPS;
        } else if (type_tx.equals("text")) {
            return Platform.SHARE_TEXT;
        } else if (type_tx.equals("webpage")) {
            return Platform.SHARE_WEBPAGE;
        } else if (type_tx.equals("image")) {
            return Platform.SHARE_IMAGE;
        } else if (type_tx.equals("emoji")) {
            return Platform.SHARE_EMOJI;
        } else if (type_tx.equals("music")) {
            return Platform.SHARE_MUSIC;
        } else if (type_tx.equals("video")) {
            return Platform.SHARE_VIDEO;
        } else if (type_tx.equals("file")) {
            return Platform.SHARE_FILE;
        } else {
            return Platform.SHARE_WEBPAGE;
        }

    }


    //判断是否绕过审核或者客户端分享的封装方法
    private void platformDevinfo(Platform plat,String devinfo,CheckBox name){
        if (plat.getDevinfo(devinfo) != null) {

            HashMap<String, String> Client = new HashMap<String, String>();
            if (name.isChecked()) {
                Client.put(devinfo, "true");
            } else {
                Client.put(devinfo, "false");
            }
            InitShareSDK.Init(Client, plat.getName());
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Platform plat = map.get(String.valueOf(id));
//        ToastUtil.show(context,plat.getName());
        //是否绕过审核
        platformDevinfo(plat,"BypassApproval",BypassApproval);
        //是否调用客户端分享
        platformDevinfo(plat,"ShareByAppClient", ShareClient);

        plat.setPlatformActionListener(this);
        if (params().getImagePath() != null ? !params().getImagePath().equals("") : false){
            path = params().getImagePath();
        }
        if (isUseClientToShare(plat.getName())) {
            if (plat.isClientValid()) {
                plat.share(params());
            } else {
                ToastUtil.show(context, "客户端不存在");
            }
        } else {
            if (plat.getName().equals(SinaWeibo.NAME)) {
                Platform.ShareParams sp = params();
                sp.setText(sp.getText() + "\n" + sp.getUrl());
                sp.setUrl("");
                plat.share(sp);
            } else {
                plat.share(params());
            }
        }
        ShareAnim(true);

    }

    private boolean isUseClientToShare(String name) {
        if ("Wechat".equals(name) || "WechatMoments".equals(name)
                || "WechatFavorite".equals(name)
                || "Pinterest".equals(name)
                || "Instagram".equals(name) || "Yixin".equals(name)
                || "YixinMoments".equals(name) || "QZone".equals(name)
                || "Mingdao".equals(name)
                || "KakaoStory".equals(name) || "KakaoTalk".equals(name)
                || "WhatsApp".equals(name)
                || "BaiduTieba".equals(name) || "Laiwang".equals(name)
                || "LaiwangMoments".equals(name) || "Alipay".equals(name)
                || "FacebookMessenger".equals(name) || "AlipayMoments".equals(name)
                ) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        sendHandler(ShareItem.NameConstant.MSG_SHARE_COMPLETE,this,"分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        sendHandler(ShareItem.NameConstant.MSG_SHARE_ERROR,this,platform,throwable);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        sendHandler(ShareItem.NameConstant.MSG_SHARE_CANCEL,this,"分享取消");
    }
}

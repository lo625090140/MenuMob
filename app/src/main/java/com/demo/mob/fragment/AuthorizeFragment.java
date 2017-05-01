package com.demo.mob.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.adapter.AuthorizeAdapter;
import com.demo.mob.bean.AuthorizeItem;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.BitmapUtil;
import com.demo.mob.utils.InitShareSDK;
import com.demo.mob.utils.LoginAnim;
import com.demo.mob.utils.ToastUtil;
import com.mauiie.aech.AECrashHelper;
import com.mob.tools.utils.Hashon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by chenjt on 2017/1/10.
 */

public class AuthorizeFragment extends BaseFragment implements AdapterView.OnItemClickListener,PlatformActionListener {
    private ListView platform_item;
    private List<AuthorizeItem> list;
    private HashMap<String, Platform> map;
    private ImageView icon;
    private TextView nickname,message;
    private Bitmap bitms;
    private String nicknames,messages;
    private Dialog loadanim;// 加载动画
    // 分享加载动画
    private void LoginAnim(boolean isStart) {
        if (isStart){
            loadanim = new LoginAnim().login(context, "授权中...");
        }else{
            if (loadanim != null) loadanim.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LoginAnim(false);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_authorize;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        ShareSDK.initSDK(context);
        init();
    }

    private void init() {
        (platform_item = (ListView) view.findViewById(R.id.fragment_authorize_menu)).setOnItemClickListener(this);
        icon = (ImageView) view.findViewById(R.id.fragment_authorize_icon);
        nickname = (TextView) view.findViewById(R.id.fragment_authorize_name);
        message = (TextView) view.findViewById(R.id.fragment_authorize_message);
        list = new ArrayList<AuthorizeItem>();
        map = new HashMap<String, Platform>();
        Platform[] plats = ShareSDK.getPlatformList();
        for(Platform plat : plats){
            if (isSupportPlat(plat.getName())){continue;}
            String name = plat.getName().toLowerCase();
            list.add(new AuthorizeItem(getString(getResID("ssdk_" + name,"string")),getResID("ssdk_oks_classic_" + name,"drawable"),plat.getId()));
            map.put(String.valueOf(plat.getId()),plat);
        }
        AuthorizeAdapter adapter = new AuthorizeAdapter(context,list);
        platform_item.setAdapter(adapter);
    }


    //不支持授权的平台
    private boolean isSupportPlat(String plat){
        if ("WechatMoments".equals(plat)||
                "WechatFavorite".equals(plat)||
                "ShortMessage".equals(plat)||
                "Email".equals(plat)||
                "Pinterest".equals(plat)||
                "Yixin".equals(plat)||
                "YixinMoments".equals(plat)||
                "Bluetooth".equals(plat)||
                "WhatsApp".equals(plat)||
                "Pocket".equals(plat)||
                "BaiduTieba".equals(plat)||
                "Laiwang".equals(plat)||
                "LaiwangMoments".equals(plat)||
                "AlipayMoments".equals(plat)||
                "Alipay".equals(plat)||
                "FacebookMessenger".equals(plat)||
                "Dingding".equals(plat)||
                "Meipai".equals(plat)||
                "Youtube".equals(plat)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(nicknames)){
            nickname.setText(nicknames);
        }
        if (bitms != null){
            icon.setImageBitmap(bitms);
        }
        if (!TextUtils.isEmpty(messages)){
            message.setText(messages);
        }
    }

    @Override
    public void onClick(View view) {

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Platform plat =  map.get(String.valueOf(id));
        if (!plat.isClientValid() && plat.getName().equals("Wechat")){
            ToastUtil.show(context,"微信客户端不存在");
            return;
        }
        plat.SSOSetting(((CheckBox)view.findViewById(R.id.fragment_authorize_item_platform_judge)).isChecked() ? false : true);
        plat.setPlatformActionListener(this);
        plat.authorize();
        LoginAnim(true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case AuthorizeItem.NameConstant.MSG_AUTH_COMPLETE :
                ToastUtil.show(context,"授权成功");
                Platform plat_Complete = (Platform) ((Object[])msg.obj)[0];
                HashMap<String, Object> hashMap = (HashMap<String, Object>) ((Object[])msg.obj)[1];
                if (!TextUtils.isEmpty(plat_Complete.getDb().getUserId())){
                    setIcon(plat_Complete);
                }else{
                    ToastUtil.show(context,"未获取到头像");
                    icon.setImageResource(getResID("ssdk_oks_classic_" + plat_Complete.getName().toLowerCase(),"drawable"));
                }
                nicknames = plat_Complete.getDb().getUserName();
                nickname.setText(nicknames);
                StringBuffer str = new StringBuffer();
                Iterator itea = new Hashon().fromJson(plat_Complete.getDb().exportData().toString()).entrySet().iterator();
                while (itea.hasNext()) {
                    @SuppressWarnings("rawtypes")
                    Map.Entry entry = (Map.Entry) itea.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    str.append(key + " : " + value.toString().trim() + "\n\n");
                }
                messages = getString(getResID("ssdk_" + plat_Complete.getName().toLowerCase(),"string")) + "信息:"
                        + "\n"
                        + str;
                message.setText(messages);
                LoginAnim(false);
                break;
            case AuthorizeItem.NameConstant.MSG_AUTH_ERROR :
                Platform plat_Error = (Platform) ((Object[]) msg.obj)[0];
                Throwable throwable = (Throwable) ((Object[])msg.obj)[1];
                ToastUtil.show(context,"授权失败");
                messages = "错误平台:"+ getString(getResID("ssdk_" + plat_Error.getName().toLowerCase(),"string")) +"\n" + "错误信息:\n" + throwable.getMessage();
                message.setText(messages);
                LoginAnim(false);
                AECrashHelper.goActivity(context,Tag,Log.WARN,throwable,false);
                break;
            case AuthorizeItem.NameConstant.MSG_AUTH_CANCEL :
                ToastUtil.show(context,msg.obj.toString());
                LoginAnim(false);
                break;
            case 10 :
                bitms = (Bitmap) msg.obj;
                icon.setImageBitmap(bitms);
                break;
        }
        return false;
    }

    private void setIcon(final Platform plat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e(Tag,plat.getDb().getUserIcon());
                    Bitmap bimp = BitmapUtil.getBitmapFormUri(plat.getDb().getUserIcon(),false);
                    sendHandler(10,AuthorizeFragment.this,bimp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        sendHandler(AuthorizeItem.NameConstant.MSG_AUTH_COMPLETE,this,platform,hashMap);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        sendHandler(AuthorizeItem.NameConstant.MSG_AUTH_ERROR,this,platform,throwable);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        sendHandler(AuthorizeItem.NameConstant.MSG_AUTH_CANCEL,this,"授权取消");
    }
}

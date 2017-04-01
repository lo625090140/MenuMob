package com.demo.mob.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.demo.mob.adapter.MenuAdapter;
import com.demo.mob.bean.MenuItem;
import com.demo.mob.utils.BaseActivity;
import com.demo.mob.utils.PermissionsUtils;
import com.demo.mob.utils.PreferActivity;
import com.demo.mob.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends BaseActivity implements OnItemClickListener {
    private ListView menu;
    private List<MenuItem> list;
    private DrawerLayout drawer;
    private ImageView menuimg;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private HashMap<String, Object> map;
    private Fragment fragments;
    private boolean isBrak;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 10:
//                new SavePx().uploading(this);
                break;
            case 0:
                isBrak = false;
                break;
        }
        return false;
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        Log.e(Tag, "onCreate");
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
        setListView();
//        if (PermissionsUtils.hasPermission(this, Manifest.permission.READ_PHONE_STATE, PermissionsUtils.READ_PHONE_STATE)){
//            handler.sendEmptyMessageDelayed(10, 2 * 1000, this);
//        }
        boolean isVersion= PermissionsUtils.morePermission(this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE},PermissionsUtils.MULTIPLE);
        if(!isVersion){
            handler.sendEmptyMessageDelayed(10, 2 * 1000, this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtils.READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handler.sendEmptyMessageDelayed(10, 2 * 1000, this);
                } else {
                    exit(0);
                }
                break;
            case PermissionsUtils.WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getFragment("com.demo.mob.fragment.ShareFragment",1, false);
                } else {
                    ToastUtil.show(this, "分享可能需要存储权限请先打开");
                }
                break;
            case PermissionsUtils.MULTIPLE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handler.sendEmptyMessageDelayed(10, 2 * 1000, this);
                } else {
                    exit(0);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }



    private void setListView() {
        map = new HashMap<String, Object>();
        (menu = (ListView) findViewById(R.id.main_left_navigation_menu)).setOnItemClickListener(this);
        (menuimg = (ImageView) findViewById(R.id.main_menu)).setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.main_drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//打开滑动LOCK_MODE_UNLOCKED
        list = new ArrayList<MenuItem>();
        String[] items = getResources().getStringArray(R.array.menu_item);
        for (int i = 0; i < items.length; i++) {
            String[] item = items[i].split(",");
            list.add(new MenuItem(item[0], Integer.valueOf(item[1])));
        }
        MenuAdapter adapter = new MenuAdapter(this, list);
        menu.setAdapter(adapter);
        TraverseFragment(4, map.isEmpty());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_menu:
                if (drawer.isDrawerOpen(findViewById(R.id.main_left_navigation))) {
                    drawer.closeDrawer(findViewById(R.id.main_left_navigation));
                } else {
                    drawer.openDrawer(findViewById(R.id.main_left_navigation));
                }
                break;
        }
    }

    private void getFragment(String pck, long l, boolean flag) {
        try {
            CommitFragment((Fragment) (Class.forName(pck).newInstance()), l, flag);
        } catch (Exception e) {
            ToastUtil.show(this, "暂无此页面数据");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //判断碎片是否存在
        Boolean isEmpty = map.get(String.valueOf(l)) == null ? TraverseFragment(l, map.isEmpty() ? true : false) : (Fragment) map.get(String.valueOf(l)) == fragments ? false : true;
        if (isEmpty) {
            fragments = (Fragment) map.get(String.valueOf(l));
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_content_fragment, (Fragment) map.get(String.valueOf(l)));
            transaction.commit();
        }
        drawer.closeDrawer(findViewById(R.id.main_left_navigation));
    }


    //遍历碎片
    private boolean TraverseFragment(long l, boolean flag) {
        for (String str : getResources().getStringArray(R.array.menu_item)) {
            if (Integer.valueOf(str.split(",")[1]) == l) {
                if (!(str.split(",")[2].equals("ShareFragment") && !PermissionsUtils.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionsUtils.WRITE_EXTERNAL_STORAGE))) {
                    getFragment("com.demo.mob.fragment." + str.split(",")[2].toString(), l, flag);
                }
            }
        }
        return false;
    }

    //替换和缓存_碎片
    private void CommitFragment(Fragment fragment, long l, Boolean flag) {
        map.put(String.valueOf(l), fragment);
        fragments = fragment;
        transaction = manager.beginTransaction();
        if (flag) {
            transaction.add(R.id.main_content_fragment, fragments);
        } else {
            transaction.replace(R.id.main_content_fragment, fragments);
        }
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            if (!isBrak){
                ToastUtil.show(this,"再次点击退出程序");
                isBrak = true;
                handler.sendEmptyMessageDelayed(0,2000,this);
            }else {
                exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(Tag, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(Tag, "onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(Tag, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(Tag, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(Tag, "onStop");
    }

    @Override
    protected void onDestroy() {
        Log.e(Tag, "onDestroy");
        super.onDestroy();

    }
}

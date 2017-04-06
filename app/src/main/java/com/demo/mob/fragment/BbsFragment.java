package com.demo.mob.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.mob.activity.BbsActivity;
import com.demo.mob.activity.R;
import com.demo.mob.bbs.viewer.PageOfficeViewer;
import com.demo.mob.bbs.viewer.PagePDFViewer;
import com.demo.mob.bbs.viewer.PageVideoViewer;
import com.demo.mob.utils.BaseFragment;
import com.mob.bbssdk.gui.pages.PageAttachmentViewer;
import com.mob.bbssdk.gui.pages.PageForumThreadDetail;
import com.mob.bbssdk.gui.views.ForumMenuView;
import com.mob.bbssdk.gui.views.ForumThreadListView;
import com.mob.bbssdk.gui.views.TitleBar;
import com.mob.bbssdk.gui.webview.JsViewClient;
import com.mob.bbssdk.model.ForumThread;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;

/**
 * Created by chenjt on 2017/3/16.
 */

public class BbsFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.activitya;
    }



    @Override
    protected void initContentView(Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(100,1*1000,this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 100:
                startActivity(new Intent(getActivity(), BbsActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }
}

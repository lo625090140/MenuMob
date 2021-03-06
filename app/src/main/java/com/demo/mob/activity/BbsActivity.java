package com.demo.mob.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.mob.bbs.viewer.PageOfficeViewer;
import com.demo.mob.bbs.viewer.PagePDFViewer;
import com.demo.mob.bbs.viewer.PageVideoViewer;
import com.demo.mob.utils.BaseActivity;
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
 * Created by chenjt on 2017/4/5.
 */

public class BbsActivity extends BaseActivity {
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(getViews());
    }
    private View getViews() {
        LinearLayout flContent = new LinearLayout(BbsActivity.this);
        flContent.setBackgroundResource(ResHelper.getColorRes(BbsActivity.this, "bbs_title_bg"));
        flContent.setOrientation(LinearLayout.VERTICAL);
        flContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TitleBar titleBar = new TitleBar(BbsActivity.this);
        titleBar.setTitle("哈哈哈");
        titleBar.setTvLeft("返回");
        titleBar.getLeftTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flContent.addView(titleBar, llp);


        ForumMenuView forumMenuView = new ForumMenuView(BbsActivity.this);
        forumMenuView.setBackgroundResource(ResHelper.getColorRes(BbsActivity.this, "bbs_bg"));
        llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        llp.weight = 1;
        flContent.addView(forumMenuView, llp);

        if (Build.VERSION.SDK_INT >= 14) {
            flContent.setFitsSystemWindows(true);
        }

        //检查使用权限
        checkPermissions();

        //设置主题帖子列表的点击事件
        forumMenuView.setThreadListItemClickListener(new ForumThreadListView.OnItemClickListener() {
            public void onItemClick(int position, ForumThread item) {
                if (item != null) {
                    showDetailsView(item);
                }
            }
        });

        //加载数据
        forumMenuView.loadData();
        return flContent;
    }

    /* 检查使用权限 */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                PackageManager pm = BbsActivity.this.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(BbsActivity.this.getPackageName(), PackageManager.GET_PERMISSIONS);
                ArrayList<String> list = new ArrayList<String>();
                for (String p : pi.requestedPermissions) {
                    if (!DeviceHelper.getInstance(BbsActivity.this).checkPermission(p)) {
                        list.add(p);
                    }
                }
                if (list.size() > 0) {
                    String[] permissions = list.toArray(new String[list.size()]);
                    if (permissions != null) {
                        requestPermissions(permissions, 1);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }


    private void showDetailsView(ForumThread forumThread) {
        PageForumThreadDetail pageForumThreadDetail = new PageForumThreadDetail();
        pageForumThreadDetail.setForumThread(forumThread);
        //设置JS交互事件，默认事件不包含打开PDF、word等格式的附件功能
        pageForumThreadDetail.setOnJsViewClient(new JsViewClient(BbsActivity.this) {
            public void onItemAttachmentClick(ForumThreadAttachment attachment) {
                String extension = attachment.extension;
                if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension)
                        || "bmp".equals(extension) || "gif".equals(extension)) {
                    //如果是图片，则直接打开图片
                    onItemImageClick(new String[]{attachment.url}, 0);
                    return;
                }
                //其它格式的文件，采用不同的方式打开
                PageAttachmentViewer page;
                if ("pdf".equals(extension)) {
                    page = new PagePDFViewer();
                } else if ("doc".equals(extension) || "docx".equals(extension) || "xlsx".equals(extension)
                        || "xls".equals(extension) || "txt".equals(extension)) {
                    page = new PageOfficeViewer();
                } else if ("3gp".equals(extension) || "mp4".equals(extension)) {
                    page = new PageVideoViewer();
                } else {
                    page = new PageAttachmentViewer();
                }
                page.setAttachment(attachment);
                page.show(BbsActivity.this);
            }

            public void onItemImageClick(String[] imageUrls, int index) {
                //可重载打开图片的方式
                super.onItemImageClick(imageUrls, index);
            }
        });
        pageForumThreadDetail.show(BbsActivity.this);
    }

    @Override
    public void onClick(View view) {

    }
}

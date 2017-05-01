package com.mauiie.aech;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.mauiie.aech.utils.LogUtil;

import io.explod.android.sqllog.data.LogEntryProvider;
import io.explod.android.sqllog.timber.SqlLoggingTree;
import io.explod.android.sqllog.ui.activity.LogViewerActivity;
import timber.log.Timber;

/**
 *
 */
public class AECrashHelper {

    public static void initCrashHandler(Application app) {
        initCrashHandler(app,new AECHConfiguration.Builder().build());
    }

    public static void initCrashHandler(Application app,AECHConfiguration config) {
        LogEntryProvider.initialize(app);
        Timber.plant(new Timber.DebugTree(), new SqlLoggingTree(app, "Error"));

        LogUtil.d("initCrashHandler");
        Context appContext = app.getApplicationContext();
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                if (appProcess.processName.equalsIgnoreCase(appContext.getPackageName())) {
                    Thread.setDefaultUncaughtExceptionHandler(AECrashHandler.getInstance(appContext,config));
                    LogUtil.d("setDefaultUncaughtExceptionHandler");
                }
            }
        }
    }
    public static void goActivity(Context context,String tag ,int priority,Throwable throwable,boolean isEnable) {
        if (!isEnable || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Exception sample = new Exception(throwable);
            Timber.tag(tag).log(priority, sample, context.getPackageName());

            Intent intent = new Intent(context, LogViewerActivity.class);
            context.startActivity(intent);
        }
    }
}

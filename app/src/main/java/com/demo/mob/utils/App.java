package com.demo.mob.utils;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.demo.mob.activity.R;
import com.mob.MobSDK;
import com.mob.bbssdk.BBSSDK;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


public class App extends MultiDexApplication{
	public final static String SMS_KEY = "15121e7272aa8";
	public final static String SMS_SECRET = "e47a6dbc24f8fecff5479ad7f4ede7ac";
	public static final String LINK_APPKEY = "1c25eec125a1e";//"1b8898cb51ccb";//正式部署到测试环境 //测试 d9b58a07cbf4
//	public final static String SMS_KEY = "19b598fa6bc60";
//	public final static String SMS_SECRET = "2875b213e184bb0473decc1bd4496271";
	public static final String PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/tupian.jpg";
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//DEMO中由于添加了打开PDF、word等附件的方式，添加了很多第三方库，导致dex方法数超过了64K，所以需要使用多dex的方式
		MultiDex.install(this);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		MobSDK.init(this);
		BBSSDK.registerSDK();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		BasicPushNotificationBuilder builder =  new BasicPushNotificationBuilder(this);
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
		builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}
}

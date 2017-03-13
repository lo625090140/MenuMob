package com.demo.mob.utils;

import android.app.Application;
import android.app.Notification;
import android.os.Environment;

import com.demo.mob.activity.R;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


public class App extends Application{
	public final static String SMS_KEY = "15121e7272aa8";
	public final static String SMS_SECRET = "e47a6dbc24f8fecff5479ad7f4ede7ac";
//	public final static String SMS_KEY = "19b598fa6bc60";
//	public final static String SMS_SECRET = "2875b213e184bb0473decc1bd4496271";
	public static final String PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/tupian.jpg";
	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		BasicPushNotificationBuilder builder =  new BasicPushNotificationBuilder(this);
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
		builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}
}

package com.demo.mob.utils;


import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void show(Context context, String message){
		show(context,message,2);
	}

	public static void show(Context context, String message,int time){
		Toast.makeText(context, message, time*1000).show();
	}
	
	public static void show(Context context, int resId){
		show(context,resId,2);
	}

	public static void show(Context context, int resId,int time){
		Toast.makeText(context, resId, time*1000).show();
	}

}

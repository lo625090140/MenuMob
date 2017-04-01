package utils;


import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void show(Context context, String message){
		Toast.makeText(context, message, 2*2000).show();
	}
	
	public static void show(Context context, int resId){
		Toast.makeText(context, resId, 2*2000).show();
	}

}

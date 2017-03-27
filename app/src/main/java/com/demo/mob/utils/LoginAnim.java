package com.demo.mob.utils;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.demo.mob.activity.R;

public class LoginAnim {
	public AlertDialog login(Context context){
		return login(context,2500l);
	}
	public AlertDialog login(Context context,Long time){
		return login(context,time,true);
	}
	@SuppressWarnings("WrongConstant")
	public AlertDialog login(Context context, Long time, boolean repeat){
		AlertDialog myDialog = new AlertDialog.Builder(context).create();
		myDialog.show();
		myDialog.setCancelable(false);
        myDialog.getWindow().setContentView(R.layout.dialog_load);
        PropertyValuesHolder holder3 = 
				PropertyValuesHolder.ofFloat("rotation", 361f);
		ObjectAnimator anim1 = ObjectAnimator .ofPropertyValuesHolder(myDialog.getWindow()  
	            .findViewById(R.id.img_dialog), holder3);
		if (repeat) {
			anim1.setRepeatCount(1000);  
			anim1.setRepeatMode(ValueAnimator.INFINITE); 
		}
		AnimatorSet set = new AnimatorSet();
		set.setDuration(2500);
		set.play(anim1);
		set.start();
		return myDialog;
	}
	public AlertDialog login(Context context,String text){
		AlertDialog myDialog = new AlertDialog.Builder(context).create();

		myDialog.show();
		myDialog.setCancelable(true);
        myDialog.getWindow().setContentView(R.layout.dialog_load);
		myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ((TextView)myDialog.getWindow().findViewById(R.id.text_dialog)).setText(text);
        PropertyValuesHolder holder3 = 
				PropertyValuesHolder.ofFloat("rotation", 361f);
		ObjectAnimator anim1 = ObjectAnimator .ofPropertyValuesHolder(myDialog.getWindow()  
	            .findViewById(R.id.img_dialog), holder3);
		anim1.setRepeatCount(1000);  
		anim1.setRepeatMode(ValueAnimator.INFINITE); 
		AnimatorSet set = new AnimatorSet();
		set.setDuration(2500);
		set.play(anim1);
		set.start();
		return myDialog;
	}
}

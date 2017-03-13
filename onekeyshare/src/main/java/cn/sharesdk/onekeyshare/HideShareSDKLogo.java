package cn.sharesdk.onekeyshare;

import android.view.View;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.authorize.AuthorizeAdapter;

public class HideShareSDKLogo extends AuthorizeAdapter{
	@Override
	public void onCreate() {
		super.onCreate();
		hideShareSDKLogo();
	}
}

package com.demo.bmob;

import cn.bmob.v3.BmobObject;

public class screen_resolution extends BmobObject{
	private String Height;
	private String Width;
	private String DeviceId;
	private String Model;
	
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public String getHeight() {
		return Height;
	}
	public void setHeight(String height) {
		Height = height;
	}
	public String getWidth() {
		return Width;
	}
	public void setWidth(String width) {
		Width = width;
	}

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}
}

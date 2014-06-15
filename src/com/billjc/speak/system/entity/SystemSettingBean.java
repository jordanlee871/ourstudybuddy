package com.billjc.speak.system.entity;

import java.io.Serializable;

public class SystemSettingBean implements Serializable{

	private static final long serialVersionUID = -291210113447249284L ;
		
	private String settingName;
	private String settingType;
	private String settingValue;
	public String getSettingName() {
		return settingName;
	}
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	public String getSettingType() {
		return settingType;
	}
	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}
	public String getSettingValue() {
		return settingValue;
	}
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	
	
	
}


package com.billjc.speak.system.entity;

import java.io.Serializable;

public class SysParam implements Serializable {

	private static final long serialVersionUID = -6441209088974240341L;

	private long paramId;

	private String paramCode;

	private String paramKey;

	private String paramValue;

	private int paramState;

	private int paramOrder;

	private String paramDesc;

	private String paramRemark;


	public long getParamId() {
		return paramId;
	}

	public void setParamId(long paramId) {
		this.paramId = paramId;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getParamRemark() {
		return paramRemark;
	}

	public void setParamRemark(String paramRemark) {
		this.paramRemark = paramRemark;
	}

	public int getParamState() {
		return paramState;
	}

	public void setParamState(int paramState) {
		this.paramState = paramState;
	}

	public int getParamOrder() {
		return paramOrder;
	}

	public void setParamOrder(int paramOrder) {
		this.paramOrder = paramOrder;
	}

}

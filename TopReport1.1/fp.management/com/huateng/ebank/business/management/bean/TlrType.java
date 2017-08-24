package com.huateng.ebank.business.management.bean;

public class TlrType {
	private String tlrType;
	private String tlrTypeName;

	public TlrType(String tlrType, String tlrTypeName) {
		this.tlrType = tlrType;
		this.tlrTypeName = tlrTypeName;
	}

	public String getTlrType() {
		return tlrType;
	}

	public void setTlrType(String tlrType) {
		this.tlrType = tlrType;
	}

	public String getTlrTypeName() {
		return tlrTypeName;
	}

	public void setTlrTypeName(String tlrTypeName) {
		this.tlrTypeName = tlrTypeName;
	}
}

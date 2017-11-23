package com.cibfintech.cloud.domain;

import java.io.Serializable;

public class ClientPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//请求系统标识号
	private String xtbs;
	//证件号码
	private String zjhm;
	//客户名称
	private String khmc;
	//账号
	private String zhdh;
	//卡号/折号
	private String yhkh;
	
	public String getXtbs() {
		return xtbs;
	}
	public void setXtbs(String xtbs) {
		this.xtbs = xtbs;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getZhdh() {
		return zhdh;
	}
	public void setZhdh(String zhdh) {
		this.zhdh = zhdh;
	}
	public String getYhkh() {
		return yhkh;
	}
	public void setYhkh(String yhkh) {
		this.yhkh = yhkh;
	}
	@Override
	public String toString() {
		return "ClientPackage [xtbs=" + xtbs + ", zjhm=" + zjhm + ", khmc=" + khmc + ", zhdh=" + zhdh + ", yhkh=" + yhkh
				+ "]";
	}
	
}

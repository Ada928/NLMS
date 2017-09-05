/*
 * Created on 2005-7-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.huateng.ebank.business.parammng.bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class TlrBrcodeCondition implements Serializable {
	private String brcode;
	private String tlrno;

	public String getBrcode() {
		return brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

	public String getTlrno() {
		return tlrno;
	}

	public void setTlrno(String tlrno) {
		this.tlrno = tlrno;
	}

}

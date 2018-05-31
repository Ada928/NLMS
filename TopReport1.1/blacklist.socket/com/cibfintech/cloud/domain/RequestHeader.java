package com.cibfintech.cloud.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "sysNo", "tranCode", "tranDate", "tranTime" })
public class RequestHeader {
	@XmlElement
	private String sysNo;
	@XmlElement
	private String tranCode;
	@XmlElement
	private String tranDate;
	@XmlElement
	private String tranTime;

	public String getSysNo() {
		return sysNo;
	}

	public void setSysNo(String sysNO) {
		this.sysNo = sysNO;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public RequestHeader() {
	}

	public RequestHeader(String sysNO, String tranCode, String tranDate, String tranTime) {
		this.sysNo = sysNO;
		this.tranCode = tranCode;
		this.tranDate = tranDate;
		this.tranTime = tranTime;
	}

	@Override
	public String toString() {
		return "MsgHeader [sysNo=" + sysNo + ", tranCode=" + tranCode + ", tranDate=" + tranDate + ", tranTime=" + tranTime + "]";
	}

}
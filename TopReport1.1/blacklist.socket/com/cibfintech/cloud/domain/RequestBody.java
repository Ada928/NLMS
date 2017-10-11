package com.cibfintech.cloud.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "seqNo", "certificateType", "certificateNumber", "accountCode" })
public class RequestBody {
	@XmlElement(defaultValue = " ")
	private String seqNo;
	@XmlElement(defaultValue = " ")
	private String certificateType;
	@XmlElement(defaultValue = " ")
	private String certificateNumber;
	@XmlElement(defaultValue = " ")
	private String accountCode;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public RequestBody() {
	}

	public RequestBody(String seqNo, String certificateType, String certificateNumber, String accountCode) {
		this.seqNo = seqNo;
		this.certificateType = certificateType;
		this.certificateNumber = certificateNumber;
		this.accountCode = accountCode;
	}

	@Override
	public String toString() {
		return "ReqBody [seqNo=" + seqNo + ", certificateType=" + certificateType + ", certificateNumber=" + certificateNumber + ", accountCode=" + accountCode
				+ "]";
	}

}
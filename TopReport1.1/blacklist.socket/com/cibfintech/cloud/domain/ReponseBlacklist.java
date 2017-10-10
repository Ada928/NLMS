package com.cibfintech.cloud.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "seqNo", "certificateType", "certificateNumber", "accountCode", "clientName", "clientEnglishName", "accountType", "blacklistType",
		"isValid", "validDate", "remarks", "rspCode", "rspMsg" })
public class ReponseBlacklist {
	@XmlElement(defaultValue = " ")
	private String seqNo;
	@XmlElement(defaultValue = " ")
	private String certificateType;
	@XmlElement(defaultValue = " ")
	private String certificateNumber;
	@XmlElement(defaultValue = " ")
	private String accountCode;
	@XmlElement(defaultValue = " ")
	private String clientName;
	@XmlElement(defaultValue = " ")
	private String clientEnglishName;
	@XmlElement(defaultValue = " ")
	private String accountType;
	@XmlElement(defaultValue = " ")
	private String blacklistType;
	@XmlElement(defaultValue = " ")
	private String isValid;
	@XmlElement(defaultValue = " ")
	private String validDate;
	@XmlElement(defaultValue = " ")
	private String remarks;
	@XmlElement(defaultValue = " ")
	private String rspCode;
	@XmlElement(defaultValue = " ")
	private String rspMsg;

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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEnglishName() {
		return clientEnglishName;
	}

	public void setClientEnglishName(String clientEnglishName) {
		this.clientEnglishName = clientEnglishName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBlacklistType() {
		return blacklistType;
	}

	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public ReponseBlacklist() {
	}

	@Override
	public String toString() {
		return "ReqBody [seqNo=" + seqNo + ", certificateType=" + certificateType + ", certificateNumber=" + certificateNumber + ", accountCode=" + accountCode
				+ ", clientName=" + clientName + ", clientEnglishName=" + clientEnglishName + ", blacklistType=" + blacklistType + ", accountType="
				+ accountType + ", isValid=" + isValid + ", validDate=" + validDate + ", remarks=" + remarks + ", rspCode=" + rspCode + ", rspMsg=" + rspMsg
				+ "]";

	}
}
package com.cibfintech.view.pub;

public class BankBlackListAuditStateView {

	private String id;
	private String blacklistID;
	private java.lang.String brcode;
	private java.lang.String auditType;
	private java.lang.String auditState;
	private String certificateType;
	private String certificateNumber;
	private String clientName;
	private String clientEnglishName;
	private String blacklistType;
	private java.lang.String editUserID;
	private java.lang.String verifyUserID;
	private java.lang.String approveUserID;
	private java.util.Date editDate;
	private java.util.Date verifyDate;
	private java.util.Date approveDate;
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBlacklistID() {
		return blacklistID;
	}

	public void setBlacklistID(String blacklistID) {
		this.blacklistID = blacklistID;
	}

	public java.lang.String getEditUserID() {
		return editUserID;
	}

	public void setEditUserID(java.lang.String editUserID) {
		this.editUserID = editUserID;
	}

	public java.lang.String getVerifyUserID() {
		return verifyUserID;
	}

	public void setVerifyUserID(java.lang.String verifyUserID) {
		this.verifyUserID = verifyUserID;
	}

	public java.lang.String getApproveUserID() {
		return approveUserID;
	}

	public java.util.Date getEditDate() {
		return editDate;
	}

	public void setEditDate(java.util.Date editDate) {
		this.editDate = editDate;
	}

	public java.util.Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(java.util.Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public java.util.Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(java.util.Date approveDate) {
		this.approveDate = approveDate;
	}

	public void setApproveUserID(java.lang.String approveUserID) {
		this.approveUserID = approveUserID;
	}

	public java.lang.String getBrcode() {
		return brcode;
	}

	public void setBrcode(java.lang.String brcode) {
		this.brcode = brcode;
	}

	public java.lang.String getAuditType() {
		return auditType;
	}

	public void setAuditType(java.lang.String auditType) {
		this.auditType = auditType;
	}

	public java.lang.String getAuditState() {
		return auditState;
	}

	public void setAuditState(java.lang.String auditState) {
		this.auditState = auditState;
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

	public String getBlacklistType() {
		return blacklistType;
	}

	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}
}

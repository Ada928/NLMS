package resource.bean.blacklist.base;

import java.io.Serializable;

import resource.bean.blacklist.NsBankBlackListAuditState;

/**
 * This is an object that contains data related to the TLR_LOGIN_LOG table. Do
 * not modify this class because it will be overwritten if the configuration
 * file related to this class is modified.
 *
 * @hibernate.class table="TLR_LOGIN_LOG"
 */

public abstract class BaseNsBankBlackListAuditState implements Serializable {

	public static String REF = "BaseInternationBLOperateLog";
	public static String PROP_MESSAFE = "message";
	public static String PROP_TLRNO = "tlrno";
	public static String PROP_ID = "id";
	public static String PROP_CREATE_DATE = "create_date";
	public static String PROP_BRCODE = "brcode";
	public static String PROP_AUDIT_TYPE = "auditType";
	public static String PROP_TLR_IP = "tlr_ip";
	public static String PROP_FILTER1 = "filter1";
	public static String PROP_FILTER2 = "filter2";
	public static String PROP_FILTER3 = "filter3";

	// constructors
	public BaseNsBankBlackListAuditState() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseNsBankBlackListAuditState(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields

	private java.lang.String editUserID;
	private java.lang.String verifyUserID;
	private java.lang.String approveUserID;
	private java.lang.String blacklistID;
	private java.lang.String brcode;
	private java.lang.String auditType;
	private java.lang.String auditState;
	private java.util.Date editDate;
	private java.util.Date verifyDate;
	private java.util.Date approveDate;

	private java.lang.String filter1;
	private java.lang.String filter2;
	private java.lang.String filter3;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id column="LOG_ID"
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: TLR_NO
	 */
	public java.lang.String getEditUserID() {
		return editUserID;
	}

	/**
	 * Set the value related to the column: TLR_NO
	 * 
	 * @param tlrNo
	 *            the TLR_NO value
	 */
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

	public void setApproveUserID(java.lang.String approveUserID) {
		this.approveUserID = approveUserID;
	}

	/**
	 * Return the value associated with the column: BR_NO
	 */
	public java.lang.String getBrcode() {
		return brcode;
	}

	/**
	 * Set the value related to the column: BR_NO
	 * 
	 * @param brNo
	 *            the BR_NO value
	 */
	public void setBrcode(java.lang.String brNo) {
		this.brcode = brNo;
	}

	public java.lang.String getBlacklistID() {
		return blacklistID;
	}

	public void setBlacklistID(java.lang.String blacklistID) {
		this.blacklistID = blacklistID;
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

	public java.lang.String getFilter1() {
		return filter1;
	}

	public void setFilter1(java.lang.String filter1) {
		this.filter1 = filter1;
	}

	public java.lang.String getFilter2() {
		return filter2;
	}

	public void setFilter2(java.lang.String filter2) {
		this.filter2 = filter2;
	}

	public java.lang.String getFilter3() {
		return filter3;
	}

	public void setFilter3(java.lang.String filter3) {
		this.filter3 = filter3;
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

	public java.util.Date getEditDate() {
		return editDate;
	}

	public void setEditDate(java.util.Date editDate) {
		this.editDate = editDate;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof NsBankBlackListAuditState))
			return false;
		else {
			NsBankBlackListAuditState intenaBLOperateLog = (NsBankBlackListAuditState) obj;
			if (null == this.getId() || null == intenaBLOperateLog.getId())
				return false;
			else
				return (this.getId().equals(intenaBLOperateLog.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

}
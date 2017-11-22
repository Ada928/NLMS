package resource.bean.blacklist.base;

import java.io.Serializable;

import resource.bean.blacklist.NsBlackListSocketQueryLog;

/**
 * This is an object that contains data related to the TLR_LOGIN_LOG table. Do
 * not modify this class because it will be overwritten if the configuration
 * file related to this class is modified.
 *
 * @hibernate.class table="NLMS_BLACKLIST_SOCK_QUERY_LOG"
 */

public abstract class BaseNsBlackListSocketQueryLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4323699609160768345L;
	// constructors
	public BaseNsBlackListSocketQueryLog() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseNsBlackListSocketQueryLog(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String sysno;
	private java.lang.String tranCode;
	private java.lang.String seqno;
	private java.lang.String certificateType;
	private java.lang.String certificateNumber;
	private java.lang.String accountCode;
	private java.util.Date createDate;
	private java.lang.String filter1;
	private java.lang.String filter2;
	private java.lang.String filter3;
	private java.lang.String cardBkBookNo;
	private java.lang.String clientName;
	
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

	public java.lang.String getSysno() {
		return sysno;
	}

	public void setSysno(java.lang.String sysno) {
		this.sysno = sysno;
	}

	public java.lang.String getTranCode() {
		return tranCode;
	}

	public void setTranCode(java.lang.String tranCode) {
		this.tranCode = tranCode;
	}

	public java.lang.String getSeqno() {
		return seqno;
	}

	public void setSeqno(java.lang.String seqno) {
		this.seqno = seqno;
	}

	public java.lang.String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(java.lang.String certificateType) {
		this.certificateType = certificateType;
	}

	public java.lang.String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(java.lang.String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public java.lang.String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(java.lang.String accountCode) {
		this.accountCode = accountCode;
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

	public java.lang.String getOperateType() {
		return certificateNumber;
	}

	public void setOperateType(java.lang.String operateType) {
		this.certificateNumber = operateType;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	
	public java.lang.String getCardBkBookNo() {
		return cardBkBookNo;
	}

	public void setCardBkBookNo(java.lang.String cardBkBookNo) {
		this.cardBkBookNo = cardBkBookNo;
	}
	
	
	public java.lang.String getClientName() {
		return clientName;
	}

	public void setClientName(java.lang.String clientName) {
		this.clientName = clientName;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof NsBlackListSocketQueryLog))
			return false;
		else {
			NsBlackListSocketQueryLog bean = (NsBlackListSocketQueryLog) obj;
			if (null == this.getId() || null == bean.getId())
				return false;
			else
				return (this.getId().equals(bean.getId()));
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
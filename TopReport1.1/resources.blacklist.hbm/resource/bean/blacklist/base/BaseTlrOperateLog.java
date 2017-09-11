package resource.bean.blacklist.base;

import java.io.Serializable;

import resource.bean.blacklist.BctlOperateLog;
import resource.bean.blacklist.TlrOperateLog;

/**
 * This is an object that contains data related to the TLR_LOGIN_LOG table. Do
 * not modify this class because it will be overwritten if the configuration
 * file related to this class is modified.
 *
 * @hibernate.class table="TLR_LOGIN_LOG"
 */

public abstract class BaseTlrOperateLog implements Serializable {

	public static String REF = "BaseBctlOperateLog";
	public static String PROP_MESSAFE = "message";
	public static String PROP_TLRNO = "tlrno";
	public static String PROP_ID = "id";
	public static String PROP_CREATE_DATE = "create_date";
	public static String PROP_BRCODE = "brcode";
	public static String PROP_QUERY_TYPE = "queryType";
	public static String PROP_TLR_IP = "tlr_ip";
	public static String PROP_FILTER1 = "filter1";
	public static String PROP_FILTER2 = "filter2";
	public static String PROP_FILTER3 = "filter3";

	// constructors
	public BaseTlrOperateLog() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTlrOperateLog(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String tlrno;
	private java.lang.String tlrIP;
	private java.lang.String brcode;
	private java.lang.String operateType;
	private java.lang.String queryType;
	private java.lang.String queryRecordNumber;
	private java.lang.String message;
	private java.util.Date createDate;
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
	public java.lang.String getTlrno() {
		return tlrno;
	}

	/**
	 * Set the value related to the column: TLR_NO
	 * 
	 * @param tlrNo
	 *            the TLR_NO value
	 */
	public void setTlrno(java.lang.String tlrNo) {
		this.tlrno = tlrNo;
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

	public java.lang.String getTlrIP() {
		return tlrIP;
	}

	public void setTlrIP(java.lang.String tlrIP) {
		this.tlrIP = tlrIP;
	}

	public java.lang.String getQueryType() {
		return queryType;
	}

	public void setQueryType(java.lang.String queryType) {
		this.queryType = queryType;
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
		return operateType;
	}

	public void setOperateType(java.lang.String operateType) {
		this.operateType = operateType;
	}

	public java.lang.String getQueryRecordNumber() {
		return queryRecordNumber;
	}

	public void setQueryRecordNumber(java.lang.String queryRecordNumber) {
		this.queryRecordNumber = queryRecordNumber;
	}

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String message) {
		this.message = message;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof BctlOperateLog))
			return false;
		else {
			TlrOperateLog tlrOperateLog = (TlrOperateLog) obj;
			if (null == this.getId() || null == tlrOperateLog.getId())
				return false;
			else
				return (this.getId().equals(tlrOperateLog.getId()));
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
package resource.bean.blacklist.base;

import java.io.Serializable;
import java.util.Date;

import resource.bean.blacklist.NsPoliceBlackList;

/**
 * NlmsBankblacklist entity. @author MyEclipse Persistence Tools
 */

public class BaseNsPoliceBlackList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String REF = "PoliceBlackList";
	public static String PROP_ID = "id";
	public static String PROP_BLACKLIST_TYPE = "blacklistType";
	public static String PROP_ACCOUNT_TYPE = "accountType";
	public static String PROP_ACCOUNT_CODE = "accountCode";
	public static String PROP_CERTIFICATE_TYPE = "certificateType";
	public static String PROP_CERTIFICATE_NUMBER = "certificateNumber";
	public static String PROP_CLIENT_NAME = "clientName";
	public static String PROP_CLIENT_ENGLISH_NAME = "clientEnglishName";
	public static String PROP_BANK_CODE = "bankCode";
	public static String PROP_BLACKLISTED_ORGANIZATION = "blacklistedOrganization";
	public static String PROP_VALID = "isValid";
	public static String PROP_DEL = "del";
	public static String PROP_VALID_DATE = "validDate";
	public static String PROP_OPERATE_STATE = "operateState";
	public static String PROP_FILLER1 = "filler1";
	public static String PROP_FILLER2 = "filler2";
	public static String PROP_FILLER3 = "filler3";
	public static String PROP_FILLER4 = "filler4";
	public static String PROP_CREATE_DATE = "createDate";
	public static String PROP_LAST_MODIFY_DATE = "lastModifyDate";
	public static String PROP_LAST_MODIFY_OPERATOR = "lastModifyOperator";
	public static String PROP_REMARKS = "remarks";
	public static String PROP_CARD_BKBOOK_NO = "cardBkBookNo";

	// Fields
	private int hashCode = Integer.MIN_VALUE;
	private String id;
	private String blacklistType;
	private String accountType;
	private String accountCode;
	private String certificateType;
	private String certificateNumber;
	private String clientName;
	private String clientEnglishName;
	private String bankCode;
	private String blackListedOrganization;
	private String contact;
	private String contactPhone;
	private String valid;
	private String del;
	private Date validDate;
	private String operateState;
	private String filler1;
	private String filler2;
	private String filler3;
	private String filler4;
	private Date createDate;
	private Date lastModifyDate;
	private String lastModifyOperator;
	private String remarks;
	private String cardBkBookNo;

	// Constructors

	/** default constructor */
	public BaseNsPoliceBlackList() {
	}

	public BaseNsPoliceBlackList(String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNumber() {
		return this.certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEnglishName() {
		return this.clientEnglishName;
	}

	public void setClientEnglishName(String clientEnglishName) {
		this.clientEnglishName = clientEnglishName;
	}

	public String getBlacklistType() {
		return this.blacklistType;
	}

	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}

	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getOperateState() {
		return this.operateState;
	}

	public void setOperateState(String operateState) {
		this.operateState = operateState;
	}

	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getBlackListedOrganization() {
		return blackListedOrganization;
	}

	public void setBlackListedOrganization(String blackListedOrganization) {
		this.blackListedOrganization = blackListedOrganization;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getFiller4() {
		return filler4;
	}

	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}

	public String getFiller1() {
		return this.filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getFiller2() {
		return this.filler2;
	}

	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}

	public String getFiller3() {
		return this.filler3;
	}

	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifyDate() {
		return this.lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getLastModifyOperator() {
		return this.lastModifyOperator;
	}

	public void setLastModifyOperator(String lastModifyOperator) {
		this.lastModifyOperator = lastModifyOperator;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getCardBkBookNo() {
		return this.cardBkBookNo;
	}

	public void setCardBkBookNo(String cardBkBookNo) {
		this.cardBkBookNo = cardBkBookNo;
	}
	
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof NsPoliceBlackList))
			return false;
		else {
			NsPoliceBlackList policeBlackList = (NsPoliceBlackList) obj;
			if (null == this.getId() || null == policeBlackList.getId())
				return false;
			else
				return (this.getId().equals(policeBlackList.getId()));
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
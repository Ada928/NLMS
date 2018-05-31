package resource.bean.blacklist.base;

import java.io.Serializable;
import java.util.Date;

import resource.bean.blacklist.NsInternationalBlackList;

/**
 * NlmsBankblacklist entity. @author MyEclipse Persistence Tools
 */

public class BaseNsInternationalBlackList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String REF = "InternationalBlackList";
	public static String PROP_ID = "id";
	public static String PROP_BLACKLIST_TYPE = "blacklistType";
	public static String PROP_SAN_CODE = "sanCode";
	public static String PROP_SAN_NAME = "sanName";
	public static String PROP_ACCOUNT_TYPE = "accountType";
	public static String PROP_CERTIFICATE_TYPE = "certificateType";
	public static String PROP_CERTIFICATE_NUMBER = "certificateNumber";
	public static String PROP_CLIENT_NAME = "clientName";
	public static String PROP_CLIENT_ENGLISH_NAME = "clientEnglishName";
	public static String PROP_NATIONALITY = "nationality";
	public static String PROP_BIRTHDAY = "birthday";
	public static String PROP_BIRTH_COUNTRY = "birthCountry";
	public static String PROP_GENDER = "gender";
	public static String PROP_LAST_OCCUPATION = "lastOccupation";
	public static String PROP_RESIDENCE_COUNTRY = "residenceCountry";
	public static String PROP_POLITICIANS = "isPoliticians";
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

	// Fields
	private int hashCode = Integer.MIN_VALUE;
	private String id;
	private String blacklistType;
	private String sanCode;
	private String sanName;
	private String accountType;
	private String certificateType;
	private String certificateNumber;
	private String clientName;
	private String clientEnglishName;
	private String nationality;
	private Date birthday;
	private String birthCountry;
	private String gender;
	private String lastOccupation;
	private String residenceCountry;
	private String politicians;
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

	// Constructors

	/** default constructor */
	public BaseNsInternationalBlackList() {
	}

	public BaseNsInternationalBlackList(String id) {
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

	public String getBlacklistType() {
		return blacklistType;
	}

	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}

	public String getSanCode() {
		return sanCode;
	}

	public void setSanCode(String sanCode) {
		this.sanCode = sanCode;
	}

	public String getSanName() {
		return sanName;
	}

	public void setSanName(String sanName) {
		this.sanName = sanName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastOccupation() {
		return lastOccupation;
	}

	public void setLastOccupation(String lastOccupation) {
		this.lastOccupation = lastOccupation;
	}

	public String getResidenceCountry() {
		return residenceCountry;
	}

	public void setResidenceCountry(String residenceCountry) {
		this.residenceCountry = residenceCountry;
	}

	public String getPoliticians() {
		return politicians;
	}

	public void setPoliticians(String politicians) {
		this.politicians = politicians;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getValid() {
		return this.valid;
	}

	public void setValid(String isValid) {
		this.valid = isValid;
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

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof NsInternationalBlackList))
			return false;
		else {
			NsInternationalBlackList internationalBlackList = (NsInternationalBlackList) obj;
			if (null == this.getId() || null == internationalBlackList.getId())
				return false;
			else
				return (this.getId().equals(internationalBlackList.getId()));
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
package resource.bean.report.base;

import java.util.Date;

/**
 * TShhmd entity. @author MyEclipse Persistence Tools
 */

public class BaseBankBlacklist implements java.io.Serializable {

	public static String REF = "BankBlacklist";
	public static String PROP_ID = "id";
	public static String PROP_YHDM = "yhdm";
	public static String PROP_PARTY_CLASS_CD = "partyClassCd";
	public static String PROP_ZHDH = "zhdh";
	public static String PROP_ZJZL = "zjzl";
	public static String PROP_ZJHM = "zjhm";
	public static String PROP_KHMC = "khmc";
	public static String PROP_YWXM = "ywxm";
	public static String PROP_MDXZ = "mdxz";
	public static String PROP_SFGX = "sfgx";
	public static String PROP_YXZT = "yxzt";
	public static String PROP_YXQ = "yxq";
	public static String PROP_LHSJ = "lhsj";
	public static String PROP_LHCZY = "lhczy";
	public static String PROP_LHYY = "lhyy";
	public static String PROP_JCSJ = "jcsj";
	public static String PROP_JCCZY = "jcczy";
	public static String PROP_BYZD = "byzd";
	public static String PROP_REMARKS = "remarks";
	public static String PROP_CREATE_DATE = "createDt";
	public static String PROP_MODIFY_DATE = "modifyDt";
	public static String PROP_MODIFY_USER = "modifyUser";

	// Fields
	private String id;
	private String yhdm;
	private String partyClassCd;
	private String zhdh;
	private String zjzl;
	private String zjhm;
	private String khmc;
	private String ywxm;
	private String mdxz;
	private String sfgx;
	private String yxzt;
	private Date yxq;
	private Date lhsj;
	private String lhczy;
	private String lhyy;
	private Date jcsj;
	private String jcczy;
	private String jcyy;
	private String byzd;
	private String remarks;
	private Date createDt;
	private Date modifyDt;
	private String modifyUser;

	// constructors
	public BaseBankBlacklist() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseBankBlacklist(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public String getYhdm() {
		return this.yhdm;
	}

	public void setYhdm(String yhdm) {
		this.yhdm = yhdm;
	}

	public String getPartyClassCd() {
		return this.partyClassCd;
	}

	public void setPartyClassCd(String partyClassCd) {
		this.partyClassCd = partyClassCd;
	}

	public String getZhdh() {
		return this.zhdh;
	}

	public void setZhdh(String zhdh) {
		this.zhdh = zhdh;
	}

	public String getZjzl() {
		return this.zjzl;
	}

	public void setZjzl(String zjzl) {
		this.zjzl = zjzl;
	}

	public String getZjhm() {
		return this.zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getKhmc() {
		return this.khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

	public String getYwxm() {
		return this.ywxm;
	}

	public void setYwxm(String ywxm) {
		this.ywxm = ywxm;
	}

	public String getMdxz() {
		return this.mdxz;
	}

	public void setMdxz(String mdxz) {
		this.mdxz = mdxz;
	}

	public String getSfgx() {
		return this.sfgx;
	}

	public void setSfgx(String sfgx) {
		this.sfgx = sfgx;
	}

	public String getYxzt() {
		return this.yxzt;
	}

	public void setYxzt(String yxzt) {
		this.yxzt = yxzt;
	}

	public Date getYxq() {
		return this.yxq;
	}

	public void setYxq(Date yxq) {
		this.yxq = yxq;
	}

	public Date getLhsj() {
		return this.lhsj;
	}

	public void setLhsj(Date lhsj) {
		this.lhsj = lhsj;
	}

	public String getLhczy() {
		return this.lhczy;
	}

	public void setLhczy(String lhczy) {
		this.lhczy = lhczy;
	}

	public String getLhyy() {
		return this.lhyy;
	}

	public void setLhyy(String lhyy) {
		this.lhyy = lhyy;
	}

	public Date getJcsj() {
		return this.jcsj;
	}

	public void setJcsj(Date jcsj) {
		this.jcsj = jcsj;
	}

	public String getJcczy() {
		return this.jcczy;
	}

	public void setJcczy(String jcczy) {
		this.jcczy = jcczy;
	}

	public String getJcyy() {
		return this.jcyy;
	}

	public void setJcyy(String jcyy) {
		this.jcyy = jcyy;
	}

	public String getByzd() {
		return this.byzd;
	}

	public void setByzd(String byzd) {
		this.byzd = byzd;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof resource.bean.report.BankBlackList))
			return false;
		else {
			resource.bean.report.BankBlackList bankBlacklist = (resource.bean.report.BankBlackList) obj;
			if (null == this.getId() || null == bankBlacklist.getId())
				return false;
			else
				return (this.getId().equals(bankBlacklist.getId()));
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
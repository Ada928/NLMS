package com.cibfintech.cloud.domain;
/**
 * 定长报文返回处理类
 * @author
 *
 */
public class NewResponseBlackList {
	//黑名单标识
	private String hmdbz;
	//证件号码
	private String zjhm;
	//客户名称
	private String khmc;
	//客户类型
	private String khlx;
	//名单种类
	private String mdzl;
	//名单类型
	private String mdlx;
	
	public String getHmdbz() {
		return hmdbz;
	}
	public void setHmdbz(String hmdbz) {
		this.hmdbz = hmdbz;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getMdzl() {
		return mdzl;
	}
	public void setMdzl(String mdzl) {
		this.mdzl = mdzl;
	}
	public String getKhlx() {
		return khlx;
	}
	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
	public String getMdlx() {
		return mdlx;
	}
	public void setMdlx(String mdlx) {
		this.mdlx = mdlx;
	}
	@Override
	public String toString() {
		return "NewResponseBlackList [hmdbz=" + hmdbz + ", zjhm=" + zjhm + ", khmc=" + khmc + ", khlx=" + khlx
				+ ", mdzl=" + mdzl + ", mdlx=" + mdlx + "]";
	}
	
}

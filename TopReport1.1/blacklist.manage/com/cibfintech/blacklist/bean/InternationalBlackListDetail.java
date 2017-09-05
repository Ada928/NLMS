package com.cibfintech.blacklist.bean;

import java.io.Serializable;

import resource.bean.blacklist.NsInternationalBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class InternationalBlackListDetail implements Serializable {
	private NsInternationalBlackList old_internationalBlackList;
	private NsInternationalBlackList internationalBlackList;

	public NsInternationalBlackList getOld_internationalBlackList() {
		return old_internationalBlackList;
	}

	public void setOld_internationalBlackList(NsInternationalBlackList oldinternationalBlackList) {
		old_internationalBlackList = oldinternationalBlackList;
	}

	public NsInternationalBlackList getInternationalBlackList() {
		return internationalBlackList;
	}

	public void setInternationalBlackList(NsInternationalBlackList internationalBlackList) {
		this.internationalBlackList = internationalBlackList;
	}

}

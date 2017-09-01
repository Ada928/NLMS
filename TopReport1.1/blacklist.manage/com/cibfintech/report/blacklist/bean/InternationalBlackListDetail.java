package com.cibfintech.report.blacklist.bean;

import resource.bean.report.InternationalBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class InternationalBlackListDetail {
	private InternationalBlackList old_internationalBlackList;
	private InternationalBlackList internationalBlackList;

	public InternationalBlackList getOld_internationalBlackList() {
		return old_internationalBlackList;
	}

	public void setOld_internationalBlackList(InternationalBlackList oldinternationalBlackList) {
		old_internationalBlackList = oldinternationalBlackList;
	}

	public InternationalBlackList getInternationalBlackList() {
		return internationalBlackList;
	}

	public void setInternationalBlackList(InternationalBlackList internationalBlackList) {
		this.internationalBlackList = internationalBlackList;
	}

}

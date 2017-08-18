package com.cibfintech.report.blacklist.bean;

import resource.bean.report.PoliceBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class PoliceBlackListDetail {
	private PoliceBlackList old_policeBlackList;
	private PoliceBlackList policeBlackList;

	public PoliceBlackList getOld_policeBlackList() {
		return old_policeBlackList;
	}

	public void setOld_policeBlackList(PoliceBlackList oldbankBlackList) {
		old_policeBlackList = oldbankBlackList;
	}

	public PoliceBlackList getPoliceBlackList() {
		return policeBlackList;
	}

	public void setPoliceBlackList(PoliceBlackList policeBlackList) {
		this.policeBlackList = policeBlackList;
	}

}

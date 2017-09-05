package com.cibfintech.blacklist.bean;

import java.io.Serializable;

import resource.bean.blacklist.NsPoliceBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class PoliceBlackListDetail implements Serializable {
	private NsPoliceBlackList old_policeBlackList;
	private NsPoliceBlackList policeBlackList;

	public NsPoliceBlackList getOld_policeBlackList() {
		return old_policeBlackList;
	}

	public void setOld_policeBlackList(NsPoliceBlackList oldpoliceBlackList) {
		old_policeBlackList = oldpoliceBlackList;
	}

	public NsPoliceBlackList getPoliceBlackList() {
		return policeBlackList;
	}

	public void setPoliceBlackList(NsPoliceBlackList policeBlackList) {
		this.policeBlackList = policeBlackList;
	}

}

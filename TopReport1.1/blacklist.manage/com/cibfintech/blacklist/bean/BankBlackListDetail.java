package com.cibfintech.blacklist.bean;

import java.io.Serializable;

import resource.bean.blacklist.NsBankBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class BankBlackListDetail implements Serializable {
	private NsBankBlackList old_bankBlackList;
	private NsBankBlackList bankBlackList;

	public NsBankBlackList getOld_bankBlackList() {
		return old_bankBlackList;
	}

	public void setOld_bankBlackList(NsBankBlackList oldbankBlackList) {
		old_bankBlackList = oldbankBlackList;
	}

	public NsBankBlackList getBankBlackList() {
		return bankBlackList;
	}

	public void setBankBlackList(NsBankBlackList bankBlackList) {
		this.bankBlackList = bankBlackList;
	}

}

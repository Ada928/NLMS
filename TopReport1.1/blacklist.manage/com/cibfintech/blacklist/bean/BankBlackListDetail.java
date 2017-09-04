package com.cibfintech.blacklist.bean;

import resource.bean.blacklist.BankBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class BankBlackListDetail {
	private BankBlackList old_bankBlackList;
	private BankBlackList bankBlackList;

	public BankBlackList getOld_bankBlackList() {
		return old_bankBlackList;
	}

	public void setOld_bankBlackList(BankBlackList oldbankBlackList) {
		old_bankBlackList = oldbankBlackList;
	}

	public BankBlackList getBankBlackList() {
		return bankBlackList;
	}

	public void setBankBlackList(BankBlackList bankBlackList) {
		this.bankBlackList = bankBlackList;
	}

}

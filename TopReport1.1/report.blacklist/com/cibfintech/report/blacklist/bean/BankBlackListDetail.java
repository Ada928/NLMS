package com.cibfintech.report.blacklist.bean;

import resource.bean.report.NlmsBankBlackList;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class BankBlackListDetail {
	private NlmsBankBlackList old_bankBlackList;
	private NlmsBankBlackList bankBlackList;

	public NlmsBankBlackList getOld_bankBlackList() {
		return old_bankBlackList;
	}

	public void setOld_bankBlackList(NlmsBankBlackList oldbankBlackList) {
		old_bankBlackList = oldbankBlackList;
	}

	public NlmsBankBlackList getBankBlackList() {
		return bankBlackList;
	}

	public void setBankBlackList(NlmsBankBlackList bankBlackList) {
		this.bankBlackList = bankBlackList;
	}

}

package com.cibfintech.report.blacklist.bean;

import resource.bean.report.NlmsBankblacklist;

/**
 * 
 * 商行黑名单新旧信息的对比的bean
 */
public class BankBlackListDetail {
	private NlmsBankblacklist old_bankBlackList;
	private NlmsBankblacklist bankBlackList;

	public NlmsBankblacklist getOld_bankBlackList() {
		return old_bankBlackList;
	}

	public void setOld_bankBlackList(NlmsBankblacklist oldbankBlackList) {
		old_bankBlackList = oldbankBlackList;
	}

	public NlmsBankblacklist getBankBlackList() {
		return bankBlackList;
	}

	public void setBankBlackList(NlmsBankblacklist bankBlackList) {
		this.bankBlackList = bankBlackList;
	}

}

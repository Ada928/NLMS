package com.huateng.report.basis.bean;

import java.io.Serializable;

import resource.bean.report.SysCurrency;

/**
 * 
 * author by 计翔 2012.9.5 币种新旧信息的对比的bean
 */
public class CurrencyManEntryDetail implements Serializable {
	private SysCurrency old_currency;
	private SysCurrency currency;

	public SysCurrency getOld_currency() {
		return old_currency;
	}

	public void setOld_currency(SysCurrency oldCurrency) {
		old_currency = oldCurrency;
	}

	public SysCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(SysCurrency currency) {
		this.currency = currency;
	}

}

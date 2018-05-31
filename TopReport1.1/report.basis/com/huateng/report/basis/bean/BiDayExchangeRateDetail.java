package com.huateng.report.basis.bean;

/**
 * 
 * author  by  计翔 2012.9.5
 * 日币的新旧信息对比的bean
 */

import java.io.Serializable;

import resource.bean.report.BiDayexchangerate;

public class BiDayExchangeRateDetail implements Serializable {
	private BiDayexchangerate bidayexchangerate;
	private BiDayexchangerate old_bidayexchangerate;

	public BiDayexchangerate getBidayexchangerate() {
		return bidayexchangerate;
	}

	public BiDayexchangerate getOld_bidayexchangerate() {
		return old_bidayexchangerate;
	}

	public void setOld_bidayexchangerate(BiDayexchangerate oldBidayexchangerate) {
		old_bidayexchangerate = oldBidayexchangerate;
	}

	public void setBidayexchangerate(BiDayexchangerate bidayexchangerate) {
		this.bidayexchangerate = bidayexchangerate;
	}

}

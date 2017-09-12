package com.huateng.view.pub;

import java.io.Serializable;

public class BranchManageView implements Serializable {

	private String brhNo;

	private String brhName;

	public String getBrhNo() {
		return brhNo;
	}

	public void setBrhNo(String brhNo) {
		this.brhNo = brhNo;
	}

	public String getBrhName() {
		return brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

}

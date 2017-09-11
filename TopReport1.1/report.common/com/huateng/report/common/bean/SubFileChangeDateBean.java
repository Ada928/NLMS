package com.huateng.report.common.bean;

import java.io.Serializable;

public class SubFileChangeDateBean implements Serializable {
	private String oldFileDate;
	private String newFileDate;

	public String getOldFileDate() {
		return oldFileDate;
	}

	public void setOldFileDate(String oldFileDate) {
		this.oldFileDate = oldFileDate;
	}

	public String getNewFileDate() {
		return newFileDate;
	}

	public void setNewFileDate(String newFileDate) {
		this.newFileDate = newFileDate;
	}

	public SubFileChangeDateBean(String oldFileDate, String newFileDate) {
		super();
		this.oldFileDate = oldFileDate;
		this.newFileDate = newFileDate;
	}

	public SubFileChangeDateBean() {
		super();
		// TODO Auto-generated constructor stub
	}

}

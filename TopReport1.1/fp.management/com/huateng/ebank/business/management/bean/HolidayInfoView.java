package com.huateng.ebank.business.management.bean;

import java.io.Serializable;

public class HolidayInfoView implements Serializable {
	private String regionCode;
	private String yyyymm;
	private String holiday;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
}

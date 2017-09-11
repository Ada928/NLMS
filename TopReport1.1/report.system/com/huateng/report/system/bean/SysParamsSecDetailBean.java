package com.huateng.report.system.bean;

import java.io.Serializable;

import com.huateng.ebank.entity.data.mng.PfSysParam;

public class SysParamsSecDetailBean implements Serializable {

	private PfSysParam pfsysparam;
	private PfSysParam old_pfsysparam;

	public PfSysParam getPfsysparam() {
		return pfsysparam;
	}

	public void setPfsysparam(PfSysParam pfsysparam) {
		this.pfsysparam = pfsysparam;
	}

	public PfSysParam getOld_pfsysparam() {
		return old_pfsysparam;
	}

	public void setOld_pfsysparam(PfSysParam old_pfsysparam) {
		this.old_pfsysparam = old_pfsysparam;
	}

}

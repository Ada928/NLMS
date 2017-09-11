package com.huateng.report.imports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogInfo implements Serializable {

	private List logDtl = new ArrayList();

	private Map logMan = null;

	public List getLogDtl() {
		return logDtl;
	}

	public void setLogDtl(List logDtl) {
		this.logDtl = logDtl;
	}

	public Map getLogMan() {
		return logMan;
	}

	public void setLogMan(Map logMan) {
		this.logMan = logMan;
	}
}

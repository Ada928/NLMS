package com.cibfintech.cloud.domain;
/**
 * 请求实例
 * @author 
 *
 */
public class NewRequest {
	private String reqMsg;
	
	
	public NewRequest() {
	}
	
	
	public NewRequest(String reqMsg) {
		this.reqMsg = reqMsg;
	}


	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}


	@Override
	public String toString() {
		return "NewRequest [reqMsg=" + reqMsg + "]";
	}
	
	
}

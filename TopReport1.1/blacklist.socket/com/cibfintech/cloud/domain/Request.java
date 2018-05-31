package com.cibfintech.cloud.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MSGREQUEST")
@XmlType(propOrder = {})
public class Request {

	@XmlElement(name = "msgHeader")
	private RequestHeader msgHeader;

	@XmlElement(name = "reqBody")
	private RequestBody reqBody;

	public RequestHeader getMsgHeader() {
		return msgHeader;
	}

	public void setMsgHeader(RequestHeader msgHeader) {
		this.msgHeader = msgHeader;
	}

	public RequestBody getReqBody() {
		return reqBody;
	}

	public void setReqBody(RequestBody reqBody) {
		this.reqBody = reqBody;
	}

	public Request() {
	}

	public Request(RequestHeader msgHeader, RequestBody reqBody) {
		this.msgHeader = msgHeader;
		this.reqBody = reqBody;
	}

	@Override
	public String toString() {
		return "MsgRequest [ msgHeader=" + msgHeader + ",  reqBody=" + reqBody + "]";
	}
}
package com.cibfintech.cloud.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MSGRESPONSE")
@XmlType(propOrder = {})
public class Reponse {

	@XmlElement(name = "msgHeader")
	private ReponseHeader msgHeader;

	@XmlElementWrapper(name = "reqBody")
	@XmlElement(name = "blacklist")
	private List<ReponseBlacklist> blacklists;

	public ReponseHeader getMsgHeader() {
		return msgHeader;
	}

	public void setMsgHeader(ReponseHeader msgHeader) {
		this.msgHeader = msgHeader;
	}

	public List<ReponseBlacklist> getReqBody() {
		return blacklists;
	}

	public void setReqBody(List<ReponseBlacklist> blacklists) {
		this.blacklists = blacklists;
	}

	public Reponse() {
	}

	public Reponse(ReponseHeader msgHeader, List<ReponseBlacklist> blacklists) {
		this.msgHeader = msgHeader;
		this.blacklists = blacklists;
	}

	@Override
	public String toString() {
		return "MsgReponse [ msgHeader=" + msgHeader + ",  repBody=" + blacklists.toString() + "]";
	}
}
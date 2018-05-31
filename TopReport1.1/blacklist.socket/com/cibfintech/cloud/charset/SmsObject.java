package com.cibfintech.cloud.charset;

import java.util.List;

public class SmsObject{
	private String sender;  //发送者
	private String receiver; //接受者
	private String message;  //接收信息
	private List<FileInfo> fileInfos;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SmsObject [sender=" + sender + ", receiver=" + receiver + ", message=" + message + "]";
	}
	
	
}

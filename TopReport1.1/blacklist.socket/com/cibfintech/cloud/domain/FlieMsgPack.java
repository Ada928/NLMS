package com.cibfintech.cloud.domain;

public class FlieMsgPack {
	/*
	 * 序列化和反序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	// 消息长度
	private int msgLength;
	// 消息方法
	private int msgMethod;
	// 消息包内容
	private String msgPack;

	public FlieMsgPack() {
	}

	public int getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		this.msgLength = msgLength;
	}

	public int getMsgMethod() {
		return msgMethod;
	}

	public void setMsgMethod(int msgMethod) {
		this.msgMethod = msgMethod;
	}

	public String getMsgPack() {
		return msgPack;
	}

	public void setMsgPack(String msgPack) {
		this.msgPack = msgPack;
	}

	public FlieMsgPack(int msgLength, int msgMethod, String msgPack) {
		this.msgLength = msgLength;
		this.msgMethod = msgMethod;
		this.msgPack = msgPack;
	}

	public String toString() {
		return "[msgMethod:" + msgMethod + ", msgLength:" + msgLength + ", msgPack:" + msgPack + "]";
	}

}

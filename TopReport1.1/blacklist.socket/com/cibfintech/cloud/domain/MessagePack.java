package com.cibfintech.cloud.domain;

import java.io.Serializable;

/*
 * @see 自定义数据包
 * @author Herman.Xiong
 * @date 2014年6月11日 11:31:45
 */
public class MessagePack implements Serializable {
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

	public MessagePack() {
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

	public MessagePack(int msgLength, int msgMethod, String msgPack) {
		this.msgLength = msgLength;
		this.msgMethod = msgMethod;
		this.msgPack = msgPack;
	}

	public String toString() {
		return "MsgPack [msgLength=" + msgLength + ", msgMethod=" + msgMethod + ", msgPack=" + msgPack + "]";
	}

}
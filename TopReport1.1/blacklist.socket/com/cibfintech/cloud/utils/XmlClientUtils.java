package com.cibfintech.cloud.utils;

public class XmlClientUtils {

	public static void testXStream() {
		// XStream xstream = new XStream(new DomDriver());
		// xstream.alias("MSGREQUEST", MsgRequest.class);
		// xstream.alias("MSGHEADER", MsgReqHeader.class);
		// xstream.alias("REQBODY", MsgReqBody.class);
		// String var = XmlClientUtils.getXml();
		// MsgRequest request = (MsgRequest) xstream.fromXML(var);
		// System.out.println(request.toString());
		// MsgReqHeader msgHeader = request.getMsgHeader();
		// System.out.println(msgHeader.toString());
		// MsgReqBody reqBody = request.getReqBody();
		// System.out.println(reqBody.toString());
	}

	public static String getXml() {
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");//
		sb.append("<MSGREQUEST>");

		sb.append("<msgHeader>");
		sb.append("<sysNo>HX</sysNo>");
		sb.append("<tranCode>1000</tranCode>");
		sb.append("<tranDate>20170929</tranDate>");
		sb.append("<tranTime>132030</tranTime>");
		sb.append("</msgHeader>");

		sb.append("<reqBody>");
		sb.append("<seqNo>HX2017092900000001</seqNo>");
		sb.append("<certificateType>11</certificateType>");
		sb.append("<certificateNumber>12345678901234</certificateNumber>");
		sb.append("<accountCode></accountCode>");
		sb.append("</reqBody>");

		sb.append("</MSGREQUEST>\n");

		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(XmlClientUtils.getXml());
		XmlClientUtils.testXStream();
		// Person bean = new Person("张三", 19);
		// // XStream xstream = new XStream();// 需要XPP3库
		// // XStream xstream = new XStream(new DomDriver());// 不需要XPP3库
		// XStream xstream = new XStream(new StaxDriver());// 不需要XPP3库开始使用Java6
		// xstream.alias("人", Person.class);// 为类名节点重命名
		// // XML序列化
		// String xml = xstream.toXML(bean);
		// System.out.println(xml);
		// // XML反序列化
		// bean = (Person) xstream.fromXML(xml);
		// System.out.println(bean);
	}
}

class Person {
	private String name;
	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
}
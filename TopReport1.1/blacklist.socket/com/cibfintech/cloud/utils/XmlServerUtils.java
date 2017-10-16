package com.cibfintech.cloud.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.blacklist.NsPoliceBlackList;

import com.cibfintech.cloud.domain.Reponse;
import com.cibfintech.cloud.domain.ReponseBlacklist;
import com.cibfintech.cloud.domain.ReponseHeader;
import com.cibfintech.cloud.domain.RequestHeader;
import com.huateng.ebank.framework.util.DateUtil;

public class XmlServerUtils {

	public static String tranToXML() {
		List<ReponseBlacklist> list = new ArrayList<ReponseBlacklist>();
		ReponseBlacklist msgRepBody = new ReponseBlacklist();
		msgRepBody.setRspCode("000000");
		msgRepBody.setRspMsg("success");
		msgRepBody.setSeqNo("HX2017092900000001");
		msgRepBody.setAccountCode("3212017092900000001");
		msgRepBody.setAccountType("I");
		msgRepBody.setBlacklistType("1");
		msgRepBody.setCertificateNumber("3212017092900000001");
		msgRepBody.setCertificateType("01");
		msgRepBody.setClientName("张三");
		msgRepBody.setClientEnglishName("zhangsan");
		msgRepBody.setIsValid("T");
		msgRepBody.setValidDate("21100908");
		msgRepBody.setRemarks("remaker");
		list.add(msgRepBody);

		ReponseHeader msHeader = new ReponseHeader();
		msHeader.setSysNo("HX");
		msHeader.setTranCode("10000");
		msHeader.setTranDate("20170929");
		msHeader.setTranTime("132030");

		Reponse msgReponse = new Reponse();

		msgReponse.setMsgHeader(msHeader);
		msgReponse.setReqBody(list);

		String xml = JaxbUtil.convertToXml(msgReponse);
		System.out.println(xml);

		return xml;
	}

	public static String tranToXML(RequestHeader header, List list) {
		ReponseHeader msHeader = new ReponseHeader();
		msHeader.setSysNo(header.getSysNo());
		msHeader.setTranCode(header.getTranCode());
		msHeader.setTranDate(DateUtil.dateToNumber(new Date()));
		msHeader.setTranTime(DateUtil.timeToString(new Date()));

		Reponse msgReponse = new Reponse();

		msgReponse.setMsgHeader(msHeader);
		msgReponse.setReqBody(list);

		String xml = JaxbUtil.convertToXml(msgReponse);
		return xml;
	}

	public static ReponseBlacklist groupBlacklist(Object obj, String rspCode, String rspMsg, String seqNo) {

		ReponseBlacklist msgRepBody = new ReponseBlacklist();
		msgRepBody.setRspCode(rspCode);
		msgRepBody.setRspMsg(rspMsg);
		msgRepBody.setSeqNo(seqNo);
		if (obj instanceof NsBankBlackList) {
			NsBankBlackList blackList = (NsBankBlackList) obj;
			msgRepBody.setAccountCode(blackList.getAccountCode());
			msgRepBody.setAccountType(blackList.getAccountType());
			msgRepBody.setBlacklistType(blackList.getBlacklistType());
			msgRepBody.setCertificateNumber(blackList.getCertificateNumber());
			msgRepBody.setCertificateType(blackList.getCertificateType());
			msgRepBody.setClientName(blackList.getClientName());
			msgRepBody.setClientEnglishName(blackList.getClientEnglishName());
			msgRepBody.setIsValid(blackList.getValid());
			msgRepBody.setValidDate(DateUtil.dateToNumber(blackList.getValidDate()));
			msgRepBody.setRemarks(blackList.getRemarks());
			// list.add(msgRepBody);
		} else if (obj instanceof NsInternationalBlackList) {
			NsInternationalBlackList blackList = (NsInternationalBlackList) obj;
			msgRepBody.setAccountType(blackList.getAccountType());
			msgRepBody.setBlacklistType(blackList.getBlacklistType());
			msgRepBody.setCertificateNumber(blackList.getCertificateNumber());
			msgRepBody.setCertificateType(blackList.getCertificateType());
			msgRepBody.setClientName(blackList.getClientName());
			msgRepBody.setClientEnglishName(blackList.getClientEnglishName());
			msgRepBody.setIsValid(blackList.getValid());
			msgRepBody.setValidDate(DateUtil.dateToNumber(blackList.getValidDate()));
			msgRepBody.setRemarks(blackList.getRemarks());
		} else if (obj instanceof NsPoliceBlackList) {
			NsPoliceBlackList blackList = (NsPoliceBlackList) obj;
			msgRepBody.setAccountType(blackList.getAccountType());
			msgRepBody.setBlacklistType(blackList.getBlacklistType());
			msgRepBody.setCertificateNumber(blackList.getCertificateNumber());
			msgRepBody.setCertificateType(blackList.getCertificateType());
			msgRepBody.setClientName(blackList.getClientName());
			msgRepBody.setClientEnglishName(blackList.getClientEnglishName());
			msgRepBody.setIsValid(blackList.getValid());
			msgRepBody.setValidDate(DateUtil.dateToNumber(blackList.getValidDate()));
			msgRepBody.setRemarks(blackList.getRemarks());
		} else {
			msgRepBody.setAccountCode("");
			msgRepBody.setAccountType("");
			msgRepBody.setBlacklistType("");
			msgRepBody.setCertificateNumber("");
			msgRepBody.setCertificateType("");
			msgRepBody.setClientName("");
			msgRepBody.setClientEnglishName("");
			msgRepBody.setIsValid("");
			msgRepBody.setValidDate("");
			msgRepBody.setRemarks("");
		}

		return msgRepBody;
	}

	public static void main(String[] args) {
		XmlServerUtils.tranToXML();
	}
}
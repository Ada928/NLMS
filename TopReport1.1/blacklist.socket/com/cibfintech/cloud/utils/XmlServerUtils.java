package com.cibfintech.cloud.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.blacklist.NsPoliceBlackList;

import com.cibfintech.cloud.domain.NewResponseBlackList;
import com.cibfintech.cloud.domain.Reponse;
import com.cibfintech.cloud.domain.ReponseBlacklist;
import com.cibfintech.cloud.domain.ReponseHeader;
import com.cibfintech.cloud.domain.RequestHeader;
import com.huateng.ebank.framework.util.DateUtil;

public class XmlServerUtils {
	private static Charset charset = Charset.forName("UTF-8");
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
	//服务端发送数据包
	public static String tranMsg(List list) {
		/*ReponseHeader msHeader = new ReponseHeader();
		msHeader.setSysNo(header.getSysNo());
		msHeader.setTranCode(header.getTranCode());
		msHeader.setTranDate(DateUtil.dateToNumber(new Date()));
		msHeader.setTranTime(DateUtil.timeToString(new Date()));

		Reponse msgReponse = new Reponse();

		msgReponse.setMsgHeader(msHeader);
		msgReponse.setReqBody(list);*/
		
		//String xml = JaxbUtil.convertToXml(msgReponse);
		String s = list.toString();
		return s;
	}
	
	//该方法将查询的数据赋值给黑名单列表
	public static NewResponseBlackList packagBlackList(Object obj,String hmdbz,String mdlx){
		NewResponseBlackList repbl = new NewResponseBlackList();
		
		if(obj instanceof NsBankBlackList){
			NsBankBlackList blackList = (NsBankBlackList)obj;
			repbl.setHmdbz(hmdbz); //是否是黑名单标识
			repbl.setZjhm(blackList.getCertificateNumber()); //证件号码
			repbl.setKhmc(blackList.getClientName());        //客户名称
			repbl.setKhlx(blackList.getAccountType());      //客户类型
			repbl.setMdzl(blackList.getBlacklistType());    //名单种类（1：黑名单  2：灰名单）
			repbl.setMdlx(mdlx);   //名单类型（区别数据库类型 ,即是指B:商行、I:国际、P:公安部黑名单中的任意一个黑名单）
		}else if(obj instanceof NsInternationalBlackList){
			NsInternationalBlackList blackList = (NsInternationalBlackList)obj;
			repbl.setHmdbz(hmdbz);
			repbl.setZjhm(blackList.getCertificateNumber());
			repbl.setKhmc(blackList.getClientName());
			repbl.setKhlx(blackList.getAccountType());
			repbl.setMdzl(blackList.getBlacklistType());
			repbl.setMdlx(mdlx);
		}else if(obj instanceof NsPoliceBlackList){
			NsPoliceBlackList blackList = (NsPoliceBlackList)obj;
			repbl.setHmdbz(hmdbz); 
			repbl.setZjhm(blackList.getCertificateNumber()); 
			repbl.setKhmc(blackList.getClientName());
			repbl.setKhlx(blackList.getAccountType());
			repbl.setMdzl(blackList.getBlacklistType());
			repbl.setMdlx(mdlx);
		}
		return repbl;
	}
	
	//返回客户端信息处理方法
	public static IoBuffer responseBlackList(List<NewResponseBlackList> list) throws UnsupportedEncodingException{
		StringBuffer sbf = new StringBuffer();
		//根据bkList判断黑(灰)名单是否存在
		if(0 != list.size() && null != list){
			for(NewResponseBlackList li:list){
					//判断属于哪种类型的黑灰名单
					if("B".equals(li.getMdlx())){   			//商行黑（灰）名单
						sbf.append(fillStr(li.getHmdbz(), 1, " "));
						sbf.append(fillStr(li.getZjhm(), 64, " "));
						sbf.append(fillStr(li.getKhmc(), 100, " "));
						sbf.append(fillStr(li.getKhlx(), 1, " "));
						sbf.append(fillStr(li.getMdzl(), 1, " "));
						sbf.append(fillStr(li.getMdlx(), 1, " "));
						sbf.append(fillStr("", 30, " "));
						sbf.append("|");
					}else if("I".equals(li.getMdlx())){			//国际组织黑（灰）名单
						sbf.append(fillStr(li.getHmdbz(), 1, " "));
						sbf.append(fillStr(li.getZjhm(), 64, " "));
						sbf.append(fillStr(li.getKhmc(), 100, " "));
						sbf.append(fillStr(li.getKhlx(), 1, " "));
						sbf.append(fillStr(li.getMdzl(), 1, " "));
						sbf.append(fillStr(li.getMdlx(), 1, " "));
						sbf.append(fillStr("", 30, " "));
						sbf.append("|");
					}else if("P".equals(li.getMdlx())){			//公安部黑（灰）名单
						sbf.append(fillStr(li.getHmdbz(), 1, " "));
						sbf.append(fillStr(li.getZjhm(), 64, " "));
						sbf.append(fillStr(li.getKhmc(), 100, " "));
						sbf.append(fillStr(li.getKhlx(), 1, " "));
						sbf.append(fillStr(li.getMdzl(), 1, " "));
						sbf.append(fillStr(li.getMdlx(), 1, " "));
						sbf.append(fillStr("", 30, " "));
						sbf.append("|");
					}
			}
		}else{
			//当前用户不是黑名单
			sbf.append(fillStr("0", 1, " "));
			sbf.append(fillStr("", 64, " "));
			sbf.append(fillStr("", 100, " "));
			sbf.append(fillStr("", 1, " "));
			sbf.append(fillStr("", 1, " "));
			sbf.append(fillStr("", 1, " "));
			sbf.append(fillStr("", 30, " "));
		}
		byte[] bt = sbf.toString().getBytes(charset);  
		IoBuffer ioBuffer = IoBuffer.allocate(bt.length); 
		ioBuffer.put(bt, 0, bt.length);   
		ioBuffer.flip();  
		return ioBuffer;
	}
	
	//old
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
		//定长报文补位方法
		public static String fillStr(String curStr,int len,String addStr){
		    //第一步判断 不需要右补位的情况 直接返回原始字符串
		    if(null != curStr && curStr.length() == len){
		        return curStr;
		    }else if(curStr.length() > len){
		    	System.out.println("输入字符串超出限定长度");        //此处需要客户端 自定义(超长输入)异常。
		    	return "inputError";
		    }

		    //取得字符串的长度，注意汉字占两个字节的问题  (此处根据具体接口文档报文输入类型决定char或byte)
		    int length=0;
		    StringBuffer res=new StringBuffer();
		    StringBuffer bf=new StringBuffer(curStr);
		    for(int i=0;i<curStr.length();i++){
		        //取得ascii编码 据此判断
		        int ASCII=Character.codePointAt(curStr,i);
		        if(ASCII>=0 && ASCII<=255){ 
		            length++;
		        }else{
		            length += 2;
		        }
		    }
		    //准确的字节长度拿到后 再据此补位 ;
		    for(int j=0;j<len-curStr.length();j++){
		        res=bf.append(addStr);
		    }
		    return res.toString();
		}
	public static void main(String[] args) {
		XmlServerUtils.tranToXML();
	}
}
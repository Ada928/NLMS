package com.cibfintech.cloud.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.blacklist.NsPoliceBlackList;

import com.cibfintech.cloud.domain.Reponse;
import com.cibfintech.cloud.domain.ReponseBlacklist;
import com.cibfintech.cloud.domain.ReponseHeader;
import com.cibfintech.cloud.domain.RequestHeader;
import com.huateng.ebank.framework.util.DateUtil;

import nl.justobjects.pushlet.util.Log;

public class XmlServerUtils {
	private static Charset charset = Charset.forName("GBK");
	public static String tranToXML() {
		/*List<ReponseBlacklist> list = new ArrayList<ReponseBlacklist>();
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

		return xml;*/
		return null;
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
	
	//返回客户端信息处理方法
	public static byte[] responseBlackList(List<Map<String, String>> mplist) throws UnsupportedEncodingException{
		StringBuffer sbf = new StringBuffer();
		//根据bkList判断黑(灰)名单是否存在
		if(0 != mplist.size() && null != mplist){
			//成功应答码
			sbf.append(fillStr(BlackListConstants.RES_RESCODE, BlackListConstants.RES_RESCODE_LEN, " "));
			//返回结果数
			sbf.append(fillStr(String.valueOf(mplist.size()), BlackListConstants.RES_RESULT_LEN, " "));
			
			//根据循环体最大长度循环添加
			int index = 0;
			for(Map<String, String> li:mplist){
					index++;
					sbf.append(fillStr(li.get(BlackListConstants.RES_CLITYPE), BlackListConstants.RES_CLITYPE_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_CLINAME), BlackListConstants.RES_CLINAME_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_ENGNAME), BlackListConstants.RES_ENGNAME_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_CETYPE), BlackListConstants.RES_CETYPE_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_CERNO), BlackListConstants.RES_CERNO_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_ACCNO), BlackListConstants.RES_ACCNO_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_BLTYPE), BlackListConstants.RES_BLTYPE_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_AUTHORITY), BlackListConstants.RES_AUTHORITY_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_MODIFYDATE), BlackListConstants.RES_MODIFYDATE_LEN, " "));
					sbf.append(fillStr(li.get(BlackListConstants.RES_DISC), BlackListConstants.RES_DISC_LEN, " "));	
					
					//超出循环体最大长度退出循环
					if(BlackListConstants.LOOP_LEN == index){
						break;
					}
			}
		}else{
			//当前用户不是黑名单
			//成功应答码
			sbf.append(fillStr(BlackListConstants.RES_RESCODE, BlackListConstants.RES_RESCODE_LEN, " "));
			//返回结果数
			sbf.append(fillStr(String.valueOf(mplist.size()), BlackListConstants.RES_RESULT_LEN, " "));
			
			sbf.append(fillStr("", BlackListConstants.RES_CLITYPE_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_CLINAME_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_ENGNAME_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_CETYPE_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_CERNO_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_ACCNO_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_BLTYPE_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_AUTHORITY_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_MODIFYDATE_LEN, " "));
			sbf.append(fillStr("", BlackListConstants.RES_DISC_LEN, " "));
		
		}
		
		Log.info("返回报文截取数据长度后内容----------：："+"["+sbf+"]");
		//实际长度
		//long acl= sbf.length();
		long acl= sbf.toString().getBytes(charset).length;
		Log.info("返回报文长度----------： 实际有效长度："+ acl);
		StringBuffer buf = new StringBuffer();
		if(acl <= BlackListConstants.MAXL_ENGTH){
			buf.append(String.format("%4d", acl).replace(" ", "0"));
		}else{
			buf.append(String.valueOf(BlackListConstants.MAXL_ENGTH));
		}
		
		buf.append(sbf.toString());
		byte[] bt = new byte[BlackListConstants.MAXL_ENGTH];
		bt = buf.toString().getBytes(charset);
		
		return bt;
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
			if(curStr != null && !"".equals(curStr)){
				if(curStr.length() == len){
			        return curStr;
			    }else if(curStr.length() > len){
			    	Log.warn("输入字符串超出限定长度------------------"+curStr+"实际长度为： "+curStr.length());    //此处需要客户端 自定义(超长输入)异常。
			    	return curStr.substring(0, len);
			    }
			}else{
				curStr = "";             //将空或null统一处理为（空）
			}
		    
		    StringBuffer bf=new StringBuffer(curStr);
		    StringBuffer res=new StringBuffer();
		    //取得字符串的长度，注意汉字占两个字节的问题  (此处根据具体接口文档报文输入类型决定char或byte)
		    int length=0;
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
		    for(int j=0;j<len-length;j++){
		        res=bf.append(addStr);
		    }
		    return res.toString();
		}
		
	public static void main(String[] args) {
		XmlServerUtils.tranToXML();
	}
	
	/**
	 * 对socket通讯报文异常类型报错返回
	 * @param errMsg
	 * @return
	 */
	public static IoBuffer responseExpBlackList(String errcode, String errMsg) throws UnsupportedEncodingException {
		StringBuffer sbf = new StringBuffer();
		//当前用户上送报文长度异常
		int len = BlackListConstants.ERRCODE_LEN + BlackListConstants.ERRMSG_LEN;
		sbf.append(String.valueOf(len));
		sbf.append(fillStr(errcode, BlackListConstants.ERRCODE_LEN, " "));
		sbf.append(fillStr(errMsg, BlackListConstants.ERRMSG_LEN, " "));
		byte[] bt = sbf.toString().getBytes(charset);  
		IoBuffer ioBuffer = IoBuffer.allocate(bt.length); 
		ioBuffer.put(bt, 0, bt.length);   
		ioBuffer.flip();
		return ioBuffer;
	}
	
	/**
	 * 处理接受信息
	 */
	public static Map<String, String> getRequst(byte[] b){
		
		String trancode = new String(b,BlackListConstants.REQ_TRANCODE_SUBLEN, BlackListConstants.REQ_TRANCODE_LEN, charset);
		String brcode = new String(b,BlackListConstants.REQ_BRCODE_SUBLEN, BlackListConstants.REQ_BRCODE_LEN, charset);
		String reqtype = new String(b,BlackListConstants.REQ_REQTYPE_SUBLEN,  BlackListConstants.REQ_REQTYPE_LEN, charset);
		String req = new String(b,BlackListConstants.REQ_REQMSG_SUBLEN, BlackListConstants.REQ_REQMSG_LEN, charset);
		
		Map<String, String> clientMap = new HashMap<String, String>();
		//请求功能码:trancode
		clientMap.put(BlackListConstants.REQ_TRANCODE, trancode.trim());
		//银行代码:brcode
		clientMap.put(BlackListConstants.REQ_BRCODE, brcode.trim());
		//信息类别:reqtype
		clientMap.put(BlackListConstants.REQ_REQTYPE, reqtype.trim());
		//证件类型：certype        0:根据证件查,1:根据账户查,2:根据中文姓名查,3:根据英文姓名查,4.根据卡号、折号查询
		//上送：证件类型+证件号码
		if("0".equals(clientMap.get(BlackListConstants.REQ_REQTYPE))){
			clientMap.put(BlackListConstants.REQ_CERTYPE,
					req.substring(0, BlackListConstants.REQ_CERTYPE_LEN).trim());
			
			clientMap.put(BlackListConstants.REQ_CERNUMB, req.substring(BlackListConstants.REQ_CERTYPE_LEN).trim());
			//上送账户
		}else if("1".equals(clientMap.get(BlackListConstants.REQ_REQTYPE))){
			//根据账户
			clientMap.put(BlackListConstants.REQ_ACCNUM, req.trim());
			//根据中文名查
		}else if("2".equals(clientMap.get(BlackListConstants.REQ_REQTYPE))){
			clientMap.put(BlackListConstants.REQ_CLIENAME, req.trim());
			//根据英文
		}else if("3".equals(clientMap.get(BlackListConstants.REQ_REQTYPE))){
			clientMap.put(BlackListConstants.REQ_ENNAME, req.trim());
			//卡、折号
		}else if("4".equals(clientMap.get(BlackListConstants.REQ_REQTYPE))){
			clientMap.put(BlackListConstants.REQ_CARDNO, req.trim());
		}
		return clientMap;
		
	}
	/**
	 * 检查上送字段是否符合要求
	 * @param msg
	 */
	public static String checkRequst(Map<String, String> mc) {
		String errMsg = "";
//		String tranCode = msg.substring(0, BlackListConstants.REQ_TRANCODE_SUBLEN).trim();
//		String brCode = msg.substring(BlackListConstants.REQ_TRANCODE_SUBLEN, BlackListConstants.REQ_BRCODE_SUBLEN).trim();
//		String msType = msg.substring(BlackListConstants.REQ_BRCODE_SUBLEN, BlackListConstants.REQ_REQTYPE_SUBLEN).trim();
//		String qur = msg.substring(BlackListConstants.REQ_REQTYPE_SUBLEN, BlackListConstants.REQ_REQMSG_SUBLEN).trim();
		String tranCode = mc.get(BlackListConstants.REQ_TRANCODE);
		String brCode = mc.get(BlackListConstants.REQ_BRCODE);
		String msType = mc.get(BlackListConstants.REQ_REQTYPE);
		String qur = null;
		if("0".equals(msType)){
			qur = mc.get(BlackListConstants.REQ_CERNUMB);
		}else if("1".equals(msType)){
			qur = mc.get(BlackListConstants.REQ_ACCNUM);
		}else if("2".equals(msType)){
			qur = mc.get(BlackListConstants.REQ_CLIENAME);
		}else if("3".equals(msType)){
			qur = mc.get(BlackListConstants.REQ_ENNAME);
		}else if("4".equals(msType)){
			qur = mc.get(BlackListConstants.REQ_CARDNO);
		}
		
		
		Log.info("截取详细信息："+"tranCode:"+tranCode+"brCode:"+brCode+"msType:"+msType+"qur:"+qur);
		if(tranCode != null &&  !"".equals(tranCode) && "0001".equals(tranCode)){  //功能码不为空且为"0001"
			//银行代码
			if(brCode != null && !"".equals(brCode)){//
				
				if(msType != null && !"".equals(msType)){
					if("0".equals(msType) || "1".equals(msType) || "2".equals(msType) || "3".equals(msType)){
						
						if(null != qur && !"".equals(qur)){
							//若返回的errMsg="",则表示查询信息无误
							errMsg = "";
						}else{
							errMsg = BlackListConstants.ERR_0008;    //查询信息不能为空
						}
						
					}else{
						errMsg = BlackListConstants.ERR_0007;    //信息类别错误
					}
				}else{
					errMsg = "";
				}
				
			}else{
				errMsg = BlackListConstants.ERR_0004;
			}
			
		}else{
			errMsg = BlackListConstants.ERR_0003;
		}
		return errMsg;
		
	}
	
}
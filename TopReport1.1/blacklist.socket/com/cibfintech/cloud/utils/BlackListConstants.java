package com.cibfintech.cloud.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 黑名单系统常量
 * @author 
 *
 */
public class BlackListConstants {
	//请求报文总长度
	public static final int BLACKLIST_REQLENGTH = 255;
	//截取后长度
	public static final int SUB_LENGTH = 251;
	public static final String REQ_HEAD = "0251";
	//数据最大长度8k
	public static final int MAXL_ENGTH = 8192;
	//查询结果最大条数（循环体条数）(8192-头长度)/循环体长度
	public static final int LOOP_LEN = 14;
	//数据长度
	public static final String REQ_DATALEN = "dataLen";
	public static final int DATALEN_LEN = 4; 
	//请求功能码:trancode ,字段长度,截取长度
	public static final String REQ_TRANCODE = "tranCode";
	public static final int REQ_TRANCODE_LEN = 4;
	public static final int REQ_TRANCODE_SUBLEN = 4;
	//银行代码
	public static final String REQ_BRCODE = "brCode";
	public static final int REQ_BRCODE_LEN = 6;
	public static final int REQ_BRCODE_SUBLEN = 8;
	//请求信息类别:reqtype
	public static final String REQ_REQTYPE = "reqType";
	public static final int REQ_REQTYPE_LEN = 1;
	public static final int REQ_REQTYPE_SUBLEN = 14;
	//查询信息：reqmsg
	public static final String REQ_REQMSG = "reqMsg";
	public static final int REQ_REQMSG_LEN = 240;
	public static final int REQ_REQMSG_SUBLEN = 15;
	//证件种类 ：certype    0:根据证件查,1:根据账户查,2:根据中文姓名查,3:根据英文姓名查
	public static final String REQ_CERTYPE = "cerType";
	public static final int REQ_CERTYPE_LEN = 1;
	//证件号码：cernum
	public static final String REQ_CERNUMB = "cerNum";
	public static final int REQ_CERNUMB_LEN = 32;
	//账户：accNum
	public static final String REQ_ACCNUM = "accNum";
	public static final String REQ_ACCNUMFLAG = "1";
	//中文名：clieName
	public static final String REQ_CLIENAME = "clieName";
	//英文名：enName
	public static final String REQ_ENNAME = "enName";
	//卡、折号
	public static final String REQ_CARDNO = "cardNo";
	public static final String REQ_CARDNOFLAG = "4";


	
	//返回报文
	//应答码
	public static final String RES_RESCODE = "0000";
	public static final int RES_RESCODE_LEN = 4;
	//返回结果，返回结果的记录数
	public static final String RES_RESULT= "result";
	public static final int RES_RESULT_LEN = 4;
	//返回结果的记录数,如果是多条记录,以下部分循环
	//客户类别
	public static final String RES_CLITYPE = "cliType";
	public static final int RES_CLITYPE_LEN = 1;
	//客户名称
	public static final String RES_CLINAME = "cliName";
	public static final int RES_CLINAME_LEN = 120;
	//英文名称
	public static final String RES_ENGNAME = "engName";
	public static final int RES_ENGNAME_LEN = 240;
	//证件种类
	public static final String RES_CETYPE = "ceType";
	public static final int RES_CETYPE_LEN = 1;
	//证件号码
	public static final String RES_CERNO = "cerNo";
	public static final int RES_CERNO_LEN = 32;
	//客户账户
	public static final String RES_ACCNO = "accNo";
	public static final int RES_ACCNO_LEN = 32;
	//名单类别
	public static final String RES_BLTYPE= "blType";
	public static final int RES_BLTYPE_LEN = 2;
	//有权机关
	public static final String RES_AUTHORITY = "authority";
	public static final int RES_AUTHORITY_LEN = 16;
	public static final String RES_AUTHORITY_B = "商行";
	public static final String RES_AUTHORITY_I = "国际";
	public static final String RES_AUTHORITY_P = "公安部";

	//变更时间
	public static final String RES_MODIFYDATE = "modifyDate";
	public static final int RES_MODIFYDATE_LEN = 19;
	//涉案描述
	public static final String RES_DISC = "disc";
	public static final int RES_DISC_LEN = 120;
	
	//定义错误信息errorcode
	public static final int ERRCODE_LEN = 4;
	public static final int ERRMSG_LEN = 120;
	public static final int ERRINFO_LEN = 128;    //+4位报文长度错误信息总长度
	
	public static final String ERR_0001 = "0001";
	public static final String ERR_MSG01 = "报文长度错误";
	public static final String ERR_0002 = "0002";
	public static final String ERR_MSG02 = "上送报文为空";
	public static final String ERR_0003 = "0003";
	public static final String ERR_MSG03 = "上送功能码异常";
	public static final String ERR_0004 = "0004";
	public static final String ERR_MSG04 = "银行代码不为空";
	public static final String ERR_0005 = "0005";
	public static final String ERR_MSG05 = "必输检索条件不能为空";
	public static final String ERR_0006 = "0006";
	public static final String ERR_MSG06 = "查询异常";
	public static final String ERR_0007 = "0007";
	public static final String ERR_MSG07 = "信息类别错误";
	public static final String ERR_0008 = "0008";
	public static final String ERR_MSG08 = "查询信息不能为空";
	//错误映射
	public static String errMaping (String errcode){
		Map<String, String> errMap = new HashMap<String, String>();
		errMap.put(ERR_0001, ERR_MSG01);
		errMap.put(ERR_0002, ERR_MSG02);
		errMap.put(ERR_0003, ERR_MSG03);
		errMap.put(ERR_0004, ERR_MSG04);
		errMap.put(ERR_0005, ERR_MSG05);
		errMap.put(ERR_0006, ERR_MSG06);
		errMap.put(ERR_0007, ERR_MSG07);
		errMap.put(ERR_0008, ERR_MSG08);
		return errMap.get(errcode);
		
	}
		
}

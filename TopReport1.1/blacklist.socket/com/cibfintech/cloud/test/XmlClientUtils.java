package com.cibfintech.cloud.test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import nl.justobjects.pushlet.util.Log;

public class XmlClientUtils {
	public static Charset charset = Charset.forName("GBK");
	//定长报文
	public static final int ALLLEN = 482;
	public static final String ALLLENSTR = "482";
	
	public static String getMsg() {
		StringBuffer sb = new StringBuffer();//
		long lasting = System.currentTimeMillis();
		
		try {
			File f = new File("blacklist.socket/com/cibfintech/cloud/utils/XmlClient.xml");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element foo;
			for(Iterator i = root.elementIterator("head"); i.hasNext();){
				foo = (Element) i.next();
				
				sb.append(fillStr(foo.elementText("jytbbh"),3," "));    //jytbbh   交易头版本号 ，3
				sb.append(fillStr(foo.elementText("jydm"),4," "));    //jydm   交易代码 ，4
				sb.append(fillStr(foo.elementText("dqdh"),2," "));    //dqdh    地区代号  2
				sb.append(fillStr(foo.elementText("jgdh"),3," "));    //jgdh   机构代号  3
				sb.append(fillStr(foo.elementText("jygy"),4," "));    //jygy   交易柜员号  4
				sb.append(fillStr(foo.elementText("zddh"),8," "));    //zddh   终端代号 8
				sb.append(fillStr(foo.elementText("syxtjylsbh"),30," "));    // syxtjylsbh      上游系统系统交易流水编号30
				sb.append(fillStr(foo.elementText("syxtjybm"),8," "));     // syxtjybm    上游系统交易编码 8
				sb.append(fillStr(foo.elementText("fqxtjylsbh"),30," "));    // fqxtjylsbh 发起系统交易流水编号 30
				sb.append(fillStr(foo.elementText("fqxtyhdh"),10," "));     //fqxtyhdh  发起系统用户代号 10
				sb.append(fillStr(foo.elementText("fqxtjybm"),8," "));     //fqxtjybm  发起系统交易编码 8
				sb.append(fillStr(foo.elementText("ywth"),16," "));     //ywth    业务套号 16
				sb.append(fillStr(foo.elementText("tysqgy"),4," "));      //tysqgy   统一授权柜员 4
				sb.append(fillStr(foo.elementText("tysqmm"),16," "));       //tysqmm   统一授权密码 16
				sb.append(fillStr(foo.elementText("fjqdzl"),3," "));		//fjqdzl  附加渠道种类 3
				sb.append(fillStr(foo.elementText("fqxtfjxx"),32," "));        //fqxtfjxx  发起系统附加信息 32
				sb.append(fillStr(foo.elementText("ywsldqdh"),2," "));        //ywsldqdh  业务受理地区代号2
				sb.append(fillStr(foo.elementText("ywsljgdh"),3," "));		//ywsljgdh  业务受理机构代号 3
				sb.append(fillStr(foo.elementText("cpdh"),9," "));		//cpdh 产品代号 9
				sb.append(fillStr(foo.elementText("sccfcs"),4," "));		//sccfcs 上传重复次数  4
				sb.append(fillStr(foo.elementText("scwjbz"),1," "));		//scwjbz 上传文件标志 1
				sb.append(fillStr(foo.elementText("scwjm"),128," "));		//scwjm  上传文件名 128
				sb.append(fillStr(foo.elementText("wjid"),8," "));		//wjid 文件ID  8
				sb.append(fillStr(foo.elementText("jkbb"),2," "));		//jkbb   接口版本 2
				sb.append(fillStr(foo.elementText("sfxgxx"),24," "));		//sfxgxx  收费相关信息24
				sb.append(fillStr(foo.elementText("khmmjmfs"),1," "));		//khmmjmfs 客户密码加密方式 1
				sb.append(fillStr(foo.elementText("jytbl"),20," "));		//jytbl 交易头保留 20
				sb.append(fillStr(foo.elementText("tyqqghy"),60," "));		// tyqqghy 统一请求规划域 60
				
			}
			
			Element fbody;
			for(Iterator i = root.elementIterator("body"); i.hasNext();){
				fbody = (Element) i.next();
				sb.append(fillStr(fbody.elementText("czbj"),1," "));		//czbj  操作标记 1
				sb.append(fillStr(fbody.elementText("wjm"),30," "));		//wjm   文件名30
				sb.append(fillStr(fbody.elementText("wjjym"),8," "));		//wjjym 文件校验码	8
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		int len = sb.toString().getBytes(charset).length;
		if(ALLLEN == len){
			return ALLLENSTR+sb.toString();
		}else{
			return null;
		}
		
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
	
}

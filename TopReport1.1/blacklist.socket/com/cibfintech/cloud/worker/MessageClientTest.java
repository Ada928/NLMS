package com.cibfintech.cloud.worker;



import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.cibfintech.cloud.client.ClientMessageProtocol;




public class MessageClientTest {
	//方法1：（mina）
	public static void main(String[] args) {
		ClientMessageProtocol.clientStart();
		System.out.println("客户端启动成功......");
		ClientSendMsgThread csmt = new ClientSendMsgThread();
		csmt.start();

		// MsgPack mp = new MsgPack(); // 请求协议
		// mp.setMsgMethod(2000);
		// mp.setMsgPack(XmlClientUtils.getXml());
		// mp.setMsgLength(mp.getMsgPack().getBytes().length);
		// ClientMsgProtocol.getIoSession().write(mp);

		System.out.println("客户端工作线程启动成功......");
		}
	
	//方法2:(传统socket)
	/*public static void main(String[] args) throws Exception{
		String serviceAddress = "127.0.0.1:8888";
		//int port = 8888;
		String[] address = serviceAddress.split(":");
		int port =0;
		try {
		   port = Integer.valueOf(address[1]);
		 } catch (NumberFormatException e) {
		   e.printStackTrace();
		   throw new Exception("服务器地址错误！");
		 }
		
		//建立服务器
		Socket client = new Socket(address[0],port);
		//建立连接后就可以往服务端写数据了
		String xtbs = fillStr("HXXT", 4 ," ");    //请求系统标识号(注：最后一个参数为补位空格)
		String zjhm = fillStr("311371198907177113", 64, " ");    //证件号码
		String khmc = fillStr("张岳虎", 100, " ");                //客户名称
		String zhdh = fillStr("411370198907177119", 64, " ");    //账号
		String yhkh = fillStr("111222222333333333333333333333", 30, " ");    //  卡/折号
		
		//String newStr = new String(reqMsg.getBytes("UTF-8"),"ISO-8859-1");
		String reqMsg = xtbs+zjhm+khmc+zhdh+yhkh;
		byte [] message=reqMsg.getBytes();
		OutputStream  out = client.getOutputStream(); //获取一个输出流向服务端发送消息
		out.write(message);
		out.flush();
		//写完以后进行读操作
		
		InputStream in = client.getInputStream();
		byte [] cliRevice = new byte[1024];
		int len;
		StringBuilder sb = new StringBuilder();
		while((len=in.read(cliRevice))  != -1){
			sb.append(new String(cliRevice, 0, len)); 
		}
		//对接受报文进行按“|”截取操作
		StringTokenizer token=new StringTokenizer(sb.toString(), "|"); 
		System.out.println("截取数：：："+token.countTokens());
		List<String> li = new ArrayList<String>();
		while(token.hasMoreElements()){ 
			li.add(token.nextToken());
		}
		for(String st:li){
			System.out.println("截取内容：：："+"黑名单标识：（1：是  0：否）"+st.substring(0, 1).trim()+"  "
					+"证件号码："+st.substring(1, 65).trim()+"  "
					+"客户名称："+st.substring(65, 165).trim()+"  "
					+"客户类型："+st.substring(165, 166).trim()+"  "
					+"名单种类：（1:黑名单 2：灰名单 "+st.substring(166, 167).trim()+"  "
					+"黑名单类型：（B商行,I国际,P公安）"+st.substring(167, 168).trim()+"  "
					+"备用字段："+st.substring(168, 198).trim());
		}
		
		out.close();
		in.close();
		//reader.close();
		client.close();
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
	}*/

}
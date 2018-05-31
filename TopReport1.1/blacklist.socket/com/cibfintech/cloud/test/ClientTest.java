package com.cibfintech.cloud.test;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cibfintech.cloud.server.NewServerMessageHandler;
import com.sun.xml.internal.messaging.saaj.util.LogDomainConstants;

/**
 * 测试客户端  多对一通讯模拟
 * @author administrator
 *
 */
public class ClientTest {
	private final static Logger LOG = Logger.getLogger(ClientTest.class);
	
	private static Charset charset = Charset.forName("GBK");
	private final static String local = "163.1.8.61";
	private final static int port  = 3801;
	private final static int fport  = 3901;
	/**
	 * 客户端入口
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {  
		

		if(null != XmlClientUtils.getMsg()){
			int len = XmlClientUtils.getMsg().getBytes(charset).length;
			System.out.println("总长度"+len);
			
			final byte [] message=XmlClientUtils.getMsg().getBytes(charset);
			
			// 开启多个客户端，一个线程代表一个客户端
			for (int i = 0; i < 1; i++) {  
				new Thread(new Runnable() {  
					@Override  
			        public void run() {  
						try {  
							TestClient client = TestClientFactory.createClient();  
							client.send(message); 
							MinaFileClient clientFile = new MinaFileClient();  
							clientFile.createClienStream(local, fport); 
							
							client.receive();  
						} catch (Exception e) {  
							e.printStackTrace();  
						}  
					}  
				}).start();  
			}
		}else{
			System.out.println("长度错误");
			LOG.error("发送报文长度错误");
		}
		  
	} 
	
		
	static class TestClientFactory {  
			
		public static TestClient createClient() throws Exception {  
			return new TestClient(local, port); 
			}  
	} 

		
	static class TestClient{
			/**
		 	 * @param host
		 	 * @param port
		 	 * @throws Exception
		 	 */
		public TestClient(String host, int port) throws Exception {  
			    // 与服务端建立连接  
			this.client = new Socket(host, port);  
			System.out.println("Cliect[port:" + client.getLocalPort() + "] 与服务端建立连接...");  
		} 
			
		private Socket client; 
		private OutputStream out;

		public void send(byte[] msg) throws Exception {  
				 // 建立连接后就可以往服务端写数据了  
			if(out == null) {  
				out = client.getOutputStream();   //获取一个输出流向服务端发送消息
			}  
			out.write(msg);  
					//writer.write("eof\n");  
			out.flush();// 写完后要记得flush  
			System.out.println("Cliect[port:" + client.getLocalPort() + "] 消息发送成功");  
		} 
			
			
		public void receive() throws Exception {
			// 写完以后进行读操作  
			InputStream in = client.getInputStream();
			// 设置接收数据超时间为10秒 
			client.setSoTimeout(30*1000);
			byte[] cliRevice = new byte[1024]; 
			int len;
			StringBuilder s = new StringBuilder();  
			while ((len = in.read(cliRevice)) != -1) {
				s.append(new String(cliRevice, 0, len,"UTF-8"));
			}
			System.out.println("Cliect[port:" + client.getLocalPort() + "] 消息收到了:"+s.toString() ); 
			// 关闭连接  
			in.close(); 
			out.close();  
			client.close(); 
		}
	}
		
		//定长报文补位方法
		public static String fillStr(String curStr,int len,String addStr){
		    //第一步判断 不需要右补位的情况 直接返回原始字符串
		    if(null != curStr && curStr.length() == len){
		        return curStr;
		    }else if(curStr.length() > len){
		    	System.out.println("输入字符串超出限定长度");        //此处需要客户端 自定义(超长输入)异常。
		    	return curStr;
		    }
		    
		    StringBuffer res=new StringBuffer();
		    StringBuffer bf=new StringBuffer(curStr);
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

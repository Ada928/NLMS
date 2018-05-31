package com.cibfintech.cloud.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class MinaFileClient extends StreamIoHandler{
	IoSession session;  
	public void setSession(IoSession session) {  
	      this.session = session;  
	}  
	public IoSession getSession() {  
	      return session;    
	 }  
	

	@Override
	protected void processStreamIo(IoSession arg0, InputStream in, OutputStream out) {
		// TODO Auto-generated method stub
		//客户端发送文件  
        File sendFile = new File("");  
        FileInputStream fis = null;  
         try {  
              fis = new FileInputStream(sendFile);  
                
          } catch (FileNotFoundException e) {  
              e.printStackTrace();  
          }  
          //放入线程让其执行  
           //客户端一般都用一个线程实现即可 不用线程池  
         new IoStreamThreadWork(fis,out).start(); 
         	
         //return; 
	}

	public void createClienStream(String local, int port){  
			/*int port = 3901;  
		    String local = "127.0.0.1";  */
		         
		      NioSocketConnector connector = new NioSocketConnector();  
		      DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
		      ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();  
		      factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);  
		      factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);  
		      chain.addLast("logging", new LoggingFilter());//用于打印日志可以不写  
		      connector.setHandler(new MinaFileClient());  
		      //connector.setHandler(new MinaClientHanlder());  
		      
		      connector.getSessionConfig().setUseReadOperation(true);
		      
		      connector.setConnectTimeoutCheckInterval(60); 
		      ConnectFuture connectFuture = connector.connect(new InetSocketAddress(local,port));  
		      connectFuture.awaitUninterruptibly();//写上这句为了得到下面的session 意思是等待连接创建完成 让创建连接由异步变同步  
		     
		      
		     /* IoSession session = connectFuture.getSession(); 
		      Object a=session.read().awaitUninterruptibly().getMessage();
		      System.out.println("返回信息……………………………………………………"+a);*/
		  } 

	/* public static void main(String[] args) {  
		    MinaFileClient client = new MinaFileClient();  
		    client.createClienStream();  
		 } */

	 public static byte[] ObjectToByte(java.lang.Object obj) {  
		   byte[] bytes = null;  
		   try {  
		       // object to bytearray  
		     ByteArrayOutputStream bo = new ByteArrayOutputStream();  
		      ObjectOutputStream oo = new ObjectOutputStream(bo);  
		       oo.writeObject(obj);  
		 
		       bytes = bo.toByteArray();  
		 
		       bo.close();  
		      oo.close();  
		   } catch (Exception e) {  
		       System.out.println("translation" + e.getMessage());  
		      e.printStackTrace();  
		   }  
		    return bytes;  
		 } 

}

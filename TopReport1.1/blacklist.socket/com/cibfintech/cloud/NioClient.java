package com.cibfintech.cloud;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class NioClient {
	// 创建一个套接字通道，注意这里必须使用无参形式
	private Selector selector = null;
	static Charset charset = Charset.forName("UTF-8");
	private volatile boolean stop = false;
	public ArrayBlockingQueue<String> arrayQueue = new ArrayBlockingQueue<String>(8);
	private static int count = 0;
	private static long start = System.currentTimeMillis();

	public void init(byte[] msg) throws IOException {
		selector = Selector.open();
		SocketChannel channel = SocketChannel.open();
		// 设置为非阻塞模式，这个方法必须在实际连接之前调用(所以open的时候不能提供服务器地址，否则会自动连接)
		channel.configureBlocking(false);
		if (channel.connect(new InetSocketAddress("163.1.8.61", 8888))) {
			channel.register(selector, SelectionKey.OP_READ);
			// 发送消息
			doWrite(channel,msg);
				//	"0251000199999 2陈国明                                                                                                                                                                                                                                          ");
		} else {
			channel.register(selector, SelectionKey.OP_CONNECT);
		}

		// 启动一个接受服务器反馈的线程
		// new Thread(new ReceiverInfo()).start();

		while (!stop) {
			selector.select(1000);
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();
			SelectionKey key = null;
			while (it.hasNext()) {
				key = it.next();
				it.remove();
				SocketChannel sc = (SocketChannel) key.channel();
				// OP_CONNECT 两种情况，链接成功或失败这个方法都会返回true
				if (key.isConnectable()) {
					// 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
					// 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
					if (channel.finishConnect()) {
						/*
						 * System.out.println("准备发送数据"); // 链接成功了可以做一些自己的处理
						 * channel.write(charset.encode("I am Coming")); //
						 * 处理完后必须吧OP_CONNECT关注去掉，改为关注OP_READ
						 * key.interestOps(SelectionKey.OP_READ);
						 */
						sc.register(selector, SelectionKey.OP_READ);
						// new Thread(new DoWrite(channel)).start();
						doWrite(channel,msg);					} 
					else {
						// 链接失败，进程推出
						System.exit(1);
					}
				}
				if (key.isReadable()) {
					// 读取服务端的响应
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					int readBytes = sc.read(buffer);
					String content = "";
					if (readBytes > 0) {
						buffer.flip();
						byte[] bytes = new byte[buffer.remaining()];
						buffer.get(bytes);
						content += new String(bytes);
						stop = true;
					} else if (readBytes < 0) {
						// 对端链路关闭
						key.channel();
						sc.close();
					}
					System.out.println("返回信息：" + content);
					key.interestOps(SelectionKey.OP_READ);
					count++;
					long end = System.currentTimeMillis();

					System.out.println("时间差：" + (end - start));
					System.out.println("成功数量：" + count);
				}
			}
		}
	}

	private void doWrite(SocketChannel sc, byte[] data) throws IOException {
		//byte[] req = data.getBytes("GBK");
		ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);
		byteBuffer.put(data);
		byteBuffer.flip();
		sc.write(byteBuffer);
		if (!byteBuffer.hasRemaining()) {
			System.out.println("Send 2 client successed");
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

	public static void main(String[] args) throws IOException {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入4位长度的功能码");
		String xt=sc.nextLine();
		//组装定长报文
		String xtbs = fillStr(xt, 4," ");    //请求系统标识号(注：最后一个参数为补位空格)HXXT
		System.out.println("请输入银行代码如：99999");
		String yd=sc.nextLine();
		String yhdh = fillStr(yd, 6," ");    //银行代号
		System.out.println("实际银行代号长度"+yhdh.length());
	

		System.out.println("请输入信息类别：   0:根据证件查,1:根据账户查,2:根据中文姓名查,3:根据英文姓名查");
		String mc=sc.nextLine();
		String khmc = fillStr(mc, 5, " ");                //客户名称
		System.out.println("实际长度"+khmc.length());

		
		System.out.println("请输入查询信息");
		String zd=sc.nextLine();
		String zhdh = fillStr(zd, 236, " ");    //账号
		System.out.println("实际长度"+zhdh.length());


		String reqMsg = "0251"+xtbs+yhdh+khmc+zhdh;
		System.out.println("总长度"+reqMsg.length());
		
		
		/*String xtbs = fillStr("", 4 ," ");    //请求系统标识号(注：最后一个参数为补位空格)
		String zjhm = fillStr("", 64, " ");    //证件号码
		String khmc = fillStr("", 100, " ");                //客户名称
		String zhdh = fillStr("", 64, " ");    //账号
		String yhkh = fillStr("", 30, " ");    //  卡/折号
		String reqMsg = xtbs+zjhm+khmc+zhdh+yhkh;*/
		final byte [] message=reqMsg.getBytes("GBK");
		
		for (int i = 0; i < 1; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						NioClient nc = new NioClient();
						nc.init(message);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
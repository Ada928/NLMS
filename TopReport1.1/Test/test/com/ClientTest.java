package test.com;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 测试客户端  多对一通讯模拟
 * @author administrator
 *
 */
public class ClientTest {
	/**
	 * 客户端入口
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {  
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
		String khmc = fillStr(mc, 1, " ");                //客户名称
		System.out.println("实际长度"+khmc.length());

		
		System.out.println("请输入查询信息");
		String zd=sc.nextLine();
		String zhdh = fillStr(zd, 240, " ");    //账号
		System.out.println("实际长度"+zhdh.length());


		String reqMsg = "1234"+xtbs+yhdh+khmc+zhdh;
		System.out.println("总长度"+reqMsg.length());
		
		
		/*String xtbs = fillStr("", 4 ," ");    //请求系统标识号(注：最后一个参数为补位空格)
		String zjhm = fillStr("", 64, " ");    //证件号码
		String khmc = fillStr("", 100, " ");                //客户名称
		String zhdh = fillStr("", 64, " ");    //账号
		String yhkh = fillStr("", 30, " ");    //  卡/折号
		String reqMsg = xtbs+zjhm+khmc+zhdh+yhkh;*/
		final byte [] message=reqMsg.getBytes("GBK");
		
		// 开启多个客户端，一个线程代表一个客户端
		for (int i = 0; i < 1; i++) {  
			new Thread(new Runnable() {  
				@Override  
		        public void run() {  
					try {  
						TestClient client = TestClientFactory.createClient();  
						client.send(message);  
						client.receive();  
					} catch (Exception e) {  
						e.printStackTrace();  
					}  
				}  
			}).start();  
		}  
	} 
	
		
	static class TestClientFactory {  
			
		public static TestClient createClient() throws Exception {  
			return new TestClient("163.1.8.61", 8888); 
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
			StringBuffer s = new StringBuffer();  
			while ((len = in.read(cliRevice)) != -1) {
				s.append(new String(cliRevice, 0, len,"GBK"));
			}
			System.out.println("Cliect[port:" + client.getLocalPort() + "] 消息收到了:" ); 
			//对接受报文进行按“|”截取操作
			//StringTokenizer token=new StringTokenizer(sb.toString(), "|"); 
			//System.out.println("截取数：：："+token.countTokens());
			/*List<String> li = new ArrayList<String>();
			while(token.hasMoreElements()){
				li.add(token.nextToken());
			}*/
			System.out.println("原报文长度：："+s.toString().getBytes("GBK").length);			
			System.out.println("原报文：："+s);
			String sb = s.substring(4, s.length());
			int lenth = sb.getBytes().length;
				
			System.out.println("截取头长后报文：："+"["+sb+"]");
			System.out.println("返回原报文长度：："+s.length());
			System.out.println("截取后长度：："+sb.length());
			//String sb = s.substring(4, 596);
			System.out.println("截取内容：：："+"应答码：  "+sb.substring(0, 4).trim()+"  "
						+"返回结果："+sb.substring(4, 8).trim()+"  ");
			String li = sb.substring(8, lenth-4);
			System.out.println(
					"客户类别长度："+li.substring(0, 1).length()+"  "
					+"客户名称长度："+li.substring(1, 121).length()+"  "
					+"英文名称长度："+li.substring(121,361).length()+"  "
					+"证件种类长度："+li.substring(361, 363).length()+"  "
					+"证件号码长度： "+li.substring(363, 395).length()+"  "
					+"客户账户长度： "+li.substring(395, 427).length()+"  "
					+"名单类别长度："+li.substring(427, 429).length()+"  "
					+"有权机关长度："+li.substring(429, 445).length()+"  "
					+"变更时间长度："+li.substring(445, 464).length()+"  "
					+"描述长度："+li.substring(464, li.length()).length()+"  "
					);
			//String li = hh.substring(8, 588);
			System.out.println(
						"客户类别："+li.substring(0, 1).trim()+"  "
						+"客户名称："+li.substring(1, 121).trim()+"  "
						+"英文名称："+li.substring(121,361).trim()+"  "
						+"证件种类："+li.substring(361, 363).trim()+"  "
						+"证件号码： "+li.substring(363, 395).trim()+"  "
						+"客户账户： "+li.substring(395, 427).trim()+"  "
						+"名单类别："+li.substring(427, 429).trim()+"  "
						+"有权机关："+li.substring(429, 445).trim()+"  "
						+"变更时间："+li.substring(445, 464).trim()+"  "
						+"描述："+li.substring(464, li.length()).trim()+"  "
						);
			
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

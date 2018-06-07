package com.cibfintech.cloud.upFile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOExceptionWithCause;

import com.cibfintech.cloud.charset.FileUploadRequest;
import com.cibfintech.cloud.test.XmlClientUtils;

/**
 *

 * @author administrator
 *
 */
public class SendMgs {
	private static Charset charset = Charset.forName("GBK");
	private static FileInputStream fis;
    private DataOutputStream dos;
    
    private static FileUploadRequest getFileUploadRequest() throws Exception  {  
            FileUploadRequest fileUploadRequest = new FileUploadRequest();  
              
           fileUploadRequest.setFileName("1.txt");  
              
           File file = new File("C:/IMPFILE/1.txt");  
              
           InputStream is = new FileInputStream(file);  
              
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
              
            byte [] bytes = new byte[1024];  
              
            int length = 0;   
              
            while (-1 !=(length = is.read(bytes)))  
            {  
                baos.write(bytes, 0, length);  
            }  
              
            fileUploadRequest.setBytes(baos.toByteArray());  
              
            is.close();  
            baos.close();  
              
            return fileUploadRequest;  
        } 

    
    public static byte[] SendMsgFile(){
    	File file =new File("C:/IMPFILE/1.txt");
    	int msLen = XmlClientUtils.getMsg().getBytes(charset).length;
    	byte[] btMsg = new byte[msLen];
	    try {
			fis =new FileInputStream(file);
			
			btMsg = XmlClientUtils.getMsg().getBytes(charset);
			byte[] btFile = getBytesFromFile(file);
			return unitByteArray(btMsg, btFile);
			
			} catch(FileNotFoundException e){
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e){
				e.printStackTrace();
			}
		    
			return null;   	   
	    
	
	}
    
    public static byte[] getBytesFromFile(File file) {  
    	        byte[] ret = null;  
    	        try {  
    	            if (file == null) {  
    	                // log.error("helper:the file is null!");  
    	                return null;  
    	            }  
    	            FileInputStream in = new FileInputStream(file);  
    	            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);  
    	            byte[] b = new byte[4096];  
    	            int n;  
    	            while ((n = in.read(b)) != -1) {  
    	                out.write(b, 0, n);  
    	            }  
    	            in.close();  
    	            out.close();  
    	            ret = out.toByteArray();  
    	        } catch(FileNotFoundException e) {  
    	            // log.error("helper:get bytes from file process error!");  
    	           e.printStackTrace();  
    	       } catch (IOException e){
    	    	   e.printStackTrace(); 
    	       }  
    	       return ret;  
    	    } 
    
    /*
	 * 合并byte数组
	 */
	  public static byte[] unitByteArray(byte[] byte1,byte[] byte2){
	        byte[] unitByte = new byte[byte1.length + byte2.length];
	        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
	        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
	        return unitByte;
	    }

}

package com.cibfintech.cloud.charset;

import java.io.Serializable;
import java.util.Arrays;

public class FileUploadRequest implements Serializable {
		/** 
		 * 文件名称 
		 */  
	    private String fileName;  
	    /** 
	     * 文件字节 
	     */      
	    private byte [] bytes;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}

		@Override
		public String toString() {
			return "FileUploadRequest [fileName=" + fileName + ", bytes=" + Arrays.toString(bytes) + "]";
		} 
	    
}
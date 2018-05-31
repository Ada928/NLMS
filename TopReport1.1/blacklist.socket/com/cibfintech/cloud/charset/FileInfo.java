package com.cibfintech.cloud.charset;

import java.io.File;

public class FileInfo {
	private File content;
	private String fileName; 
	private int contentLength;
	private int fileNameLength;
	public File getContent() {
		return content;
	}
	public void setContent(File content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getContentLength() {
		return contentLength;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	public int getFileNameLength() {
		return fileNameLength;
	}
	public void setFileNameLength(int fileNameLength) {
		this.fileNameLength = fileNameLength;
	}
	@Override
	public String toString() {
		return "FileInfo [content=" + content + ", fileName=" + fileName + ", contentLength=" + contentLength
				+ ", fileNameLength=" + fileNameLength + "]";
	}
	
	
}

package com.cibfintech.cloud.test;

import TuxedoClient.MessageProcess.MessageProcessService;

public class Ctsp {
	//public TuxedoClient.MessageProcess.MessageProcessService.MessageProcessService();
	//public TuxedoClient.MessageProcess.MessageProcessService.MessageProcessService();
	
	public static void main(String[] args){
		testGetbw();
	}
	

	private static boolean testGetbw() {
		try {
			MessageProcessService mp = new MessageProcessService("C:\\etc");
			mp.ctsptransfer("Z030700117017779zzzzzzzz".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
}

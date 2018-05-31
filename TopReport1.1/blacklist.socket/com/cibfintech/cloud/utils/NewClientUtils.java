package com.cibfintech.cloud.utils;

public class NewClientUtils {
		public static byte[] getContent(){
			/*char xtbs;
			char zjhm;
			char khmc;
			char zhdh;
			char yhkh;*/
			
			String reqMsg = "H XT"+"311371198907177113                                              "
			+"张岳虎                                                                                                 "+"411370198907177119                                              "
			+"111222222333333333333333333333";
			
			//byte[] bt = reqMsg.getBytes();
			byte[] bt = new byte[268];
			bt = reqMsg.getBytes();
			return bt;
		}
}

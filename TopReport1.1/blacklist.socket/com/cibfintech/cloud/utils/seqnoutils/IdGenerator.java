package com.cibfintech.cloud.utils.seqnoutils;
/**
 * ID生成器接口, 用于生成全局唯一的ID流水号 
 * @author administrator
 *
 */
public interface IdGenerator {
	//生成一个不重复的流水号
	 String next();  
}

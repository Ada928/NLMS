package com.cibfintech.cloud.utils.seqnoutils;
/**
 * id生成器的配置接口
 * @author administrator
 *
 */
public interface IdGeneratorConfig {
	//获取分隔符
	String getSplitString();
	//获取初始值
	int getInitial();
	//获取ID前缀
	String getPrefix();
	//获取滚动间隔, 单位: 秒 
	int getRollingInterval(); 
}

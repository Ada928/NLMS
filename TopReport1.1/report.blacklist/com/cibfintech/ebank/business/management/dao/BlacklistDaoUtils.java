package com.cibfintech.ebank.business.management.dao;

import com.huateng.ebank.business.common.BaseDAOUtils;
import com.huateng.ebank.framework.util.ApplicationContextUtils;

public class BlacklistDaoUtils extends BaseDAOUtils {
	final public static BlacklistDao getBlacklistRootDao() {
		return (BlacklistDao) ApplicationContextUtils.getBean("BlacklistRootDao");
	}
}

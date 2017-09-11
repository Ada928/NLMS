package resource.blacklist.dao;

import com.huateng.ebank.business.common.BaseDAOUtils;
import com.huateng.ebank.framework.util.ApplicationContextUtils;

public class BlackListDAOUtils extends BaseDAOUtils {
	final public static BlackListDAO getBlackListDAO() {
		return (BlackListDAO) ApplicationContextUtils.getBean("BlackListDAO");
	}
}

package com.cibfintech.blacklist.roleinfo.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.RoleInfo;
import resource.dao.pub.RoleInfoDAO;

import com.cibfintech.blacklist.roleinfo.service.RoleInfoService;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.management.common.DAOUtils;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

/**
 * @Description:
 * @Package: com.cibfintech.blacklist.roleinfo.getter
 * @author: yyxyz
 * @date: 2010-8-3 下午07:25:28
 * @Copyright: Copyright (c) 2010
 * @Company: Shanghai Huateng Software Systems Co., Ltd.
 */
public class RoleInfoManageQueryGetter extends BaseGetter {

	@Override
	public Result call() throws AppException {

		String id = getCommQueryServletRequest().getParameter("id");
		if (id == null || id.equals("")) {
			id = "0";
		}

		Integer roleId = Integer.parseInt(id);
		RoleInfo roleInfo = RoleInfoService.getInstance().selectById(roleId); 

		ResultMng.fillResultByObject(this.commonQueryBean, getCommQueryServletRequest(), roleInfo, getResult());
		List content = new ArrayList();
		getResult().setContent(content);
		getResult().init();

		return getResult();
	}

}

package com.huateng.ebank.business.management.operation;

import com.huateng.ebank.business.management.service.HolidayService;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;

import java.util.Map;

public class HolidayDetailQueryOperation extends BaseOperation {
	public static final String ID = "Management.HolidayDetailQueryOperation";
	public static final String INPUT_YEAR = "INPUT_YEAR";
	public static final String OUTPUT_RESULT = "OUTPUT_RESULT";
	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub

	}
	@Override
	public void beforeProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub

	}
	@Override
	public void execute(OperationContext context) throws CommonException {
		String year = (String)context.getAttribute(INPUT_YEAR);
		Map map = HolidayService.getInstance().queryHolidayDetail(year);
		context.setAttribute(OUTPUT_RESULT, map);
	}

}

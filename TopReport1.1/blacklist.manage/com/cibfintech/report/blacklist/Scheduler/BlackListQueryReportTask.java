package com.cibfintech.report.blacklist.Scheduler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cibfintech.report.blacklist.service.BankBlackListOperateLogService;
import com.cibfintech.report.blacklist.service.InternationBlackListOperateLogService;
import com.cibfintech.report.blacklist.service.PoliceBlackListOperateLogService;
import com.cibfintech.report.blacklist.service.QueryDailyLogCountService;
import com.cibfintech.report.blacklist.service.QueryMonthlyLogCountService;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.DateUtil;

public class BlackListQueryReportTask {
	private static final Logger logger = Logger
			.getLogger(BlackListQueryReportTask.class);

	/*
	 * 每日查询数据统计: 1, 统计查询国际黑名单的数据 2， 统计查询公安部黑名单的数据 3，统计查询商行黑名单的数据
	 */
	public void dailyBlackListQueryCount() throws CommonException {
		String startDate = DateUtil.dateToString(DateUtil
				.getBeforeDayWithTime(new Date()));
		String endDate = DateUtil.dateToString(new Date());
		InternationBlackListOperateLogService interService = InternationBlackListOperateLogService
				.getInstance();
		List interList = interService.sumQueryInternationBlacklist(startDate,
				endDate);
		saveToDailyLogCount(interList, "0", startDate);

		PoliceBlackListOperateLogService policeService = PoliceBlackListOperateLogService
				.getInstance();
		List policeList = policeService.sumQueryPoliceBlacklist(startDate,
				endDate);
		saveToDailyLogCount(policeList, "1", startDate);

		BankBlackListOperateLogService bankService = BankBlackListOperateLogService
				.getInstance();
		List bankList = bankService.sumQueryBankBlacklist(startDate, endDate);
		saveToDailyLogCount(bankList, "2", startDate);
	}

	public void monthlyBlackListQueryCount() throws CommonException {
		String startDate = DateUtil.dateToString(DateUtil
				.getLastMonthFirstDay(new Date()));
		String endDate = DateUtil.dateToString(DateUtil
				.getLastDateL(new Date()));
		InternationBlackListOperateLogService interService = InternationBlackListOperateLogService
				.getInstance();
		List interList = interService.sumQueryInternationBlacklist(startDate,
				endDate);
		saveToMonthlyLogCount(interList, "0", startDate);

		PoliceBlackListOperateLogService policeService = PoliceBlackListOperateLogService
				.getInstance();
		List policeList = policeService.sumQueryPoliceBlacklist(startDate,
				endDate);
		saveToMonthlyLogCount(policeList, "1", startDate);

		BankBlackListOperateLogService bankService = BankBlackListOperateLogService
				.getInstance();
		List bankList = bankService.sumQueryBankBlacklist(startDate, endDate);
		saveToMonthlyLogCount(bankList, "2", startDate);
	}

	private void saveToDailyLogCount(List list, String table, String day)
			throws CommonException {
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String brno = (String) obj[0];
			String sumQueryRecord = (String) obj[1];

			QueryDailyLogCountService queryDailyService = QueryDailyLogCountService
					.getInstance();
			queryDailyService.saveQueryDailyLogCount("", brno, table,
					sumQueryRecord, DateUtil.stringToDate(day));
		}
	}

	private void saveToMonthlyLogCount(List list, String table, String day)
			throws CommonException {
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String brno = (String) obj[0];
			String sumQueryRecord = (String) obj[1];

			QueryMonthlyLogCountService queryMonthlyService = QueryMonthlyLogCountService
					.getInstance();
			queryMonthlyService.saveQueryMonthlyLogCount("", brno, table,
					sumQueryRecord, DateUtil.stringToDate(day));
		}
	}
}

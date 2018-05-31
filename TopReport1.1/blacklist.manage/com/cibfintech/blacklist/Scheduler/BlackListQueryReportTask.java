package com.cibfintech.blacklist.Scheduler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsQueryDailyLogCount;
import resource.bean.blacklist.NsQueryMonthlyLogCount;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListOperateLogService;
import com.cibfintech.blacklist.internationblacklist.service.InternationBlackListOperateLogService;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListOperateLogService;
import com.cibfintech.blacklist.service.QueryDailyLogCountService;
import com.cibfintech.blacklist.service.QueryMonthlyLogCountService;
import com.cibfintech.blacklist.util.GenerateID;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.DateUtil;

public class BlackListQueryReportTask {
	private static final Logger logger = Logger.getLogger(BlackListQueryReportTask.class);

	/*
	 * 每日查询数据统计: 1, 统计查询国际黑名单的数据 2， 统计查询公安部黑名单的数据 3，统计查询商行黑名单的数据
	 */
	public void dailyBlackListQueryCount() throws CommonException {
		Date startDate = DateUtil.getBeforeDayWithTime(new Date());
		Date endDate = new Date();
		InternationBlackListOperateLogService interService = InternationBlackListOperateLogService.getInstance();
		List interList = interService.sumQueryInternationBlacklist(startDate, endDate);
		saveToDailyLogCount(interList, "0", startDate);

		PoliceBlackListOperateLogService policeService = PoliceBlackListOperateLogService.getInstance();
		List policeList = policeService.sumQueryPoliceBlacklist(startDate, endDate);
		saveToDailyLogCount(policeList, "1", startDate);

		BankBlackListOperateLogService bankService = BankBlackListOperateLogService.getInstance();
		List bankList = bankService.sumQueryBankBlacklist(startDate, endDate);
		saveToDailyLogCount(bankList, "2", startDate);
	}

	public void monthlyBlackListQueryCount() throws CommonException {
		Date startDate = DateUtil.getLastMonthFirstDay(new Date());
		Date endDate = DateUtil.getLastDateLastMonth(new Date());
		// String endDate =new Date();
		InternationBlackListOperateLogService interService = InternationBlackListOperateLogService.getInstance();
		List interList = interService.sumQueryInternationBlacklist(startDate, endDate);
		saveToMonthlyLogCount(interList, "0", startDate);

		PoliceBlackListOperateLogService policeService = PoliceBlackListOperateLogService.getInstance();
		List policeList = policeService.sumQueryPoliceBlacklist(startDate, endDate);
		saveToMonthlyLogCount(policeList, "1", startDate);

		BankBlackListOperateLogService bankService = BankBlackListOperateLogService.getInstance();
		List bankList = bankService.sumQueryBankBlacklist(startDate, endDate);
		saveToMonthlyLogCount(bankList, "2", startDate);
	}

	private void saveToDailyLogCount(List list, String table, Date day) throws CommonException {
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String brcode = (String) obj[0];
			String sumQueryRecord = (String) obj[1];

			NsQueryDailyLogCount queryDailyLogCount = new NsQueryDailyLogCount();
			queryDailyLogCount.setId(String.valueOf(GenerateID.getId()));
			queryDailyLogCount.setBrcode(brcode);
			queryDailyLogCount.setOperateType("");
			queryDailyLogCount.setQueryTable(table);
			queryDailyLogCount.setSumQueryRecord(sumQueryRecord);
			queryDailyLogCount.setCountDay(day);
			queryDailyLogCount.setCreateDate(DateUtil.getCurrentDate());

			QueryDailyLogCountService queryDailyService = QueryDailyLogCountService.getInstance();
			queryDailyService.addEntity(queryDailyLogCount);
			// queryDailyService.saveQueryDailyLogCount("", brcode, table,
			// sumQueryRecord, DateUtil.stringToDate(day));
		}
	}

	private void saveToMonthlyLogCount(List list, String table, Date day) throws CommonException {
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String brcode = (String) obj[0];
			String sumQueryRecord = (String) obj[1];

			NsQueryMonthlyLogCount queryMonthlyLogCount = new NsQueryMonthlyLogCount();
			queryMonthlyLogCount.setId(String.valueOf(GenerateID.getId()));
			queryMonthlyLogCount.setBrcode(brcode);
			queryMonthlyLogCount.setOperateType("");
			queryMonthlyLogCount.setQueryTable(table);
			queryMonthlyLogCount.setSumQueryRecord(sumQueryRecord);
			queryMonthlyLogCount.setCountMonth(day);
			queryMonthlyLogCount.setCreateDate(DateUtil.getCurrentDate());

			QueryMonthlyLogCountService queryMonthlyService = QueryMonthlyLogCountService.getInstance();
			queryMonthlyService.addEntity(queryMonthlyLogCount);
			// queryMonthlyService.saveQueryMonthlyLogCount("", brcode, table,
			// sumQueryRecord, DateUtil.stringToDate(day));
		}
	}
}

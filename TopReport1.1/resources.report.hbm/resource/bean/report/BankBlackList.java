package resource.bean.report;

import org.apache.commons.lang.StringUtils;

import com.huateng.ebank.framework.util.DataFormat;

import resource.bean.report.base.BaseBankBlacklist;

public class BankBlackList extends BaseBankBlacklist {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public BankBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BankBlackList(java.lang.String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

	public String getCurrencyNoName() {
		String id = DataFormat.trim(super.getId());
		String currencyName = DataFormat.trim(super.getYhdm());

		if (StringUtils.isEmpty(id) && StringUtils.isEmpty(currencyName)) {
			return "";
		}
		return id + "-" + currencyName;
	}

	public void setCurrencyNoName(String name) {
	}

}
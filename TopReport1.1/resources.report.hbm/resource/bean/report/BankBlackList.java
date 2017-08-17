package resource.bean.report;

import java.io.Serializable;

import resource.bean.report.base.BaseBankBlackList;

public class BankBlackList extends BaseBankBlackList implements Serializable {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public BankBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BankBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
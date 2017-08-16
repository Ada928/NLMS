package resource.bean.report;

import java.io.Serializable;

import resource.bean.report.base.BaseNlmsBankBlackList;

public class NlmsBankBlackList extends BaseNlmsBankBlackList implements Serializable {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public NlmsBankBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NlmsBankBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
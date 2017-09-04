package resource.bean.blacklist;

import java.io.Serializable;

import resource.bean.report.base.BaseInternationalBlackList;

public class InternationalBlackList extends BaseInternationalBlackList implements Serializable {

	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public InternationalBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InternationalBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
package resource.bean.blacklist;

import java.io.Serializable;

import resource.bean.blacklist.base.BaseNsInternationalBlackList;

public class NsInternationalBlackList extends BaseNsInternationalBlackList implements Serializable {

	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public NsInternationalBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NsInternationalBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
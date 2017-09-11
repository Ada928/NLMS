package resource.bean.blacklist;

import java.io.Serializable;

import resource.bean.blacklist.base.BaseNsPoliceBlackList;

public class NsPoliceBlackList extends BaseNsPoliceBlackList implements Serializable {

	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public NsPoliceBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NsPoliceBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
package resource.bean.blacklist;

import java.io.Serializable;

import resource.bean.blacklist.base.BasePoliceBlackList;

public class PoliceBlackList extends BasePoliceBlackList implements Serializable {

	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public PoliceBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PoliceBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
package resource.bean.blacklist;

import java.io.Serializable;

import resource.bean.blacklist.base.BaseNsBankBlackList;

public class NsBankBlackList extends BaseNsBankBlackList implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public NsBankBlackList() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NsBankBlackList(String id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
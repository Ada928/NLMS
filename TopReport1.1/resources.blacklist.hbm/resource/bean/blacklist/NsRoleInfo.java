package resource.bean.blacklist;

import resource.bean.blacklist.base.BaseNsRoleInfo;

public class NsRoleInfo extends BaseNsRoleInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String roleList;
	private String statusModFlag;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public NsRoleInfo() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NsRoleInfo(java.lang.Integer id) {
		super(id);
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

	public String getStatusModFlag() {
		return statusModFlag;
	}

	public void setStatusModFlag(String statusModFlag) {
		this.statusModFlag = statusModFlag;
	}

	/* [CONSTRUCTOR MARKER END] */

}
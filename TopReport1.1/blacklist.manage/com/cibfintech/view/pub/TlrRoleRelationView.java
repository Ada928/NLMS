package com.cibfintech.view.pub;

import java.io.Serializable;

/**
 * ???????
 * 
 * @author zxj
 *
 */
public class TlrRoleRelationView implements Serializable {
	private boolean selected;
	private String roleId;
	private String roleName;
	private String tlrno;
	private String brcode;

	public String getTlrno() {
		return tlrno;
	}

	public void setTlrno(String tlrno) {
		this.tlrno = tlrno;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getBrcode() {
		return brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

}

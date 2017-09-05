package resource.bean.blacklist;

import org.apache.commons.lang.StringUtils;

import resource.bean.blacklist.base.BaseNsBank;

import com.huateng.ebank.framework.util.DataFormat;

public class NsBankInfo extends BaseNsBank implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public NsBankInfo() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NsBankInfo(java.lang.String brcode) {
		super(brcode);
	}

	/**
	 * Constructor for required fields
	 */
	public NsBankInfo(java.lang.String brcode, java.lang.String brno) {

		super(brcode, brno);
	}

	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getBrcodeTypeName() {
		String brcode = DataFormat.trim(super.getBrcode());
		String brname = DataFormat.trim(super.getBrname());

		if (StringUtils.isEmpty(brcode) && StringUtils.isEmpty(brname)) {
			return "";
		}
		return brcode + " - " + brname;
	}

	public void setBrcodeTypeName(String name) {

	}
}
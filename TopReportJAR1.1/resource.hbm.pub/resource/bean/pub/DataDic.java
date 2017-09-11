package resource.bean.pub;

import org.apache.commons.lang.StringUtils;

import resource.bean.pub.base.BaseDataDic;

import com.huateng.ebank.framework.util.DataFormat;

public class DataDic extends BaseDataDic implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public DataDic() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DataDic(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DataDic(java.lang.Integer id, java.lang.Integer dataTypeNo,
			java.lang.String dataNo) {

		super(id, dataTypeNo, dataNo);
	}

	/* [CONSTRUCTOR MARKER END] */

	public String getDataNoName() {
		String dataNo = DataFormat.trim(super.getDataNo());
		String dataName = DataFormat.trim(super.getDataName());

		if (StringUtils.isEmpty(dataNo) && StringUtils.isEmpty(dataName)) {
			return "";
		}
		return dataNo + "-" + dataName;
	}

	public void setDataNoName(String dataNoName) {

	}
}
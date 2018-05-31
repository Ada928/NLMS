package resource.bean.report;

import resource.bean.report.base.BaseTradeLog;

public class TradeLog extends BaseTradeLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public TradeLog() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TradeLog(resource.bean.report.TradeLogPK id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**OpenUp Ltda Issue #5780
 * @author SBT 13/6/2016
 *
 */
public class MRTTrxReportLog extends X_UY_RT_TrxReportLog {

	/**
	 * @param ctx
	 * @param UY_RT_TrxReportLog_ID
	 * @param trxName
	 */
	public MRTTrxReportLog(Properties ctx, int UY_RT_TrxReportLog_ID,
			String trxName) {
		super(ctx, UY_RT_TrxReportLog_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTrxReportLog(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}

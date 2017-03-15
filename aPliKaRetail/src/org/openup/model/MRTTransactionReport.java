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
public class MRTTransactionReport extends X_UY_RT_TransactionReport {

	/**
	 * @param ctx
	 * @param UY_RT_TransactionReport_ID
	 * @param trxName
	 */
	public MRTTransactionReport(Properties ctx, int UY_RT_TransactionReport_ID,
			String trxName) {
		super(ctx, UY_RT_TransactionReport_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTransactionReport(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}

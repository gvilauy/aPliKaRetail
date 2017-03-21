/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author gabriel
 *
 */
public class MRTSendPosLog extends X_UY_RT_SendPosLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3557942722143500578L;

	/**
	 * @param ctx
	 * @param UY_RT_SendPosLog_ID
	 * @param trxName
	 */
	public MRTSendPosLog(Properties ctx, int UY_RT_SendPosLog_ID, String trxName) {
		super(ctx, UY_RT_SendPosLog_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTSendPosLog(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}

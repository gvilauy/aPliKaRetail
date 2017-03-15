/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTLogFile extends X_UY_RT_LogFile {

	/**
	 * @param ctx
	 * @param UY_RT_LogFile_ID
	 * @param trxName
	 */
	public MRTLogFile(Properties ctx, int UY_RT_LogFile_ID, String trxName) {
		super(ctx, UY_RT_LogFile_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTLogFile(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}

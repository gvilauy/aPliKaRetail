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
public class MRTTicketHeader extends X_UY_RT_Ticket_Header {

	public static final String vVenta = "1";
	public static final String vVentaNormal = "0";

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_Header_ID
	 * @param trxName
	 */
	public MRTTicketHeader(Properties ctx, int UY_RT_Ticket_Header_ID,
			String trxName) {
		super(ctx, UY_RT_Ticket_Header_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketHeader(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}

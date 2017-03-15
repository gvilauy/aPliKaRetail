/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTAuditLoadTicket extends X_UY_RT_AuditLoadTicket {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1659009495410184135L;

	/**
	 * @param ctx
	 * @param UY_RT_AuditLoadTicket_ID
	 * @param trxName
	 */
	public MRTAuditLoadTicket(Properties ctx, int UY_RT_AuditLoadTicket_ID,
			String trxName) {
		super(ctx, UY_RT_AuditLoadTicket_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTAuditLoadTicket(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**Metodo que retorna el ultimo modelo de la MRTAuditLoadTicket si existe auditoria con nombre de archivo igual al ingresado como filename
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param fileName
	 * @param object
	 * @return
	 */
	public static MRTAuditLoadTicket forFileName(Properties ctx, String fileName, String trxName) {
		// TODO Auto-generated method stub
		
		String whereClause = X_UY_RT_AuditLoadTicket.COLUMNNAME_FileName + " = '" + fileName + "'" +													
				" AND " + X_UY_RT_AuditLoadTicket.COLUMNNAME_IsActive + " = 'Y'";

		MRTAuditLoadTicket model = new Query(ctx, I_UY_RT_AuditLoadTicket.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_rt_auditloadticket.created desc ")
		.first();		
		
		return model;
	}

	/**
	 * SBT obtener auditoria desde un proceso de salida pazos
	 */
	public static MRTAuditLoadTicket forLoadTicketID(Properties ctx, int loadTicketID, String trxName) {
		// TODO Auto-generated method stub
		
		String whereClause = X_UY_RT_AuditLoadTicket.COLUMNNAME_UY_RT_LoadTicket_ID + " = " + loadTicketID  +													
				" AND " + X_UY_RT_AuditLoadTicket.COLUMNNAME_IsActive + " = 'Y'";

		MRTAuditLoadTicket model = new Query(ctx, I_UY_RT_AuditLoadTicket.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}
}

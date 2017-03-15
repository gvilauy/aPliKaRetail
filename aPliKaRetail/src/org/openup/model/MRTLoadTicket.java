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
public class MRTLoadTicket extends X_UY_RT_LoadTicket {

	/**
	 * @param ctx
	 * @param UY_RT_LoadTicket_ID
	 * @param trxName
	 */
	public MRTLoadTicket(Properties ctx, int UY_RT_LoadTicket_ID, String trxName) {
		super(ctx, UY_RT_LoadTicket_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTLoadTicket(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retorna el modelo que corresponde al nombre ingresado y el nombre 2 (Nomkbre de archivo y Fecha) - 
	 * Usado principalmente Scanntech
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/2/2016
	 * @param ctx
	 * @param inName
	 * @param trxName
	 * @return
	 */
	
	
	
	/**
	 * Retorna el modelo que corresponde al nombre ingresado y el nombre 2 (Nomkbre de archivo y Fecha) - 
	 * Usado principalmente Scanntech
	 * OpenUp Ltda Issue #4933
	 * @author Sylvie Bouissa 29/2/2016
	 * @param ctx
	 * @param inName
	 * @param inFechaProceso
	 * @return
	 */
	public static MRTLoadTicket forNameAndDate(Properties ctx, String inName,String inFechaProceso, String trxName){
		String whereClause = X_UY_RT_LoadTicket.COLUMNNAME_Name + " = '" + inName + "'" +													
				" AND " + X_UY_RT_LoadTicket.COLUMNNAME_IsActive + " = 'Y'"
						+ " AND "+X_UY_RT_LoadTicket.COLUMNNAME_FileName +" ='"+inFechaProceso+"'"
								+ " AND "+X_UY_RT_LoadTicket.COLUMNNAME_Processed+ " = 'N'" ; //Se usa para setear la fecha
		
		MRTLoadTicket model = new Query(ctx, I_UY_RT_LoadTicket.Table_Name, whereClause, trxName)
			.first();		

		return model;
	}

	/**
	 * OpenUp Ltda Issue #7820
	 * @author SBT 10 nov. 2016
	 * @param mov
	 * @param getnumerodocumentopago
	 */
	public void logError(MRTMovement mov, String getNroDocPago) {
		MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
		
		log.set_ValueOfColumn("UY_RT_LoadTicket_ID", this.get_ID());
		log.setName("Pagos duplicados !!!");
		
		log.setDescription("Movimeinto "+mov.gettipocfe()+"-"+mov.getseriecfe()+"-"+mov.getnumerooperacionfiscal()
					+",de la caja "+mov.getcodigocaja()+", del local "+mov.getcodigolocal());
		
		log.setdatofila("Pago con numero de documento duplicado:"+getNroDocPago +" - Lecura: "+mov.getCreated());
		log.saveEx();
		
	}
}

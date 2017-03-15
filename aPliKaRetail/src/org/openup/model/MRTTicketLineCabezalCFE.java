/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**OpenUp Ltda Issue#
 * @author SBT 15/12/2015
 *
 */
public class MRTTicketLineCabezalCFE extends X_UY_RT_Ticket_LineCabezalCFE {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineCabezalCFE_ID
	 * @param trxName
	 */
	public MRTTicketLineCabezalCFE(Properties ctx,
			int UY_RT_Ticket_LineCabezalCFE_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineCabezalCFE_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineCabezalCFE(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 15/12/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param posicionfila
	 */
	public void parseLineCabezalCFE(String[] lineSplit,
			String fchCabezal, int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, get_TrxName());
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			if(lineSplit.length>4){
				this.settipocfe(lineSplit[4]);
			}
			if(lineSplit.length>5){
				this.setdescripcioncfe(lineSplit[5]);
			}
			if(lineSplit.length>6){
				this.setseriecfe(lineSplit[6]);
			}
			if(lineSplit.length>7){
				this.setnumerocfe((lineSplit[7]!=null && !(lineSplit[7].equals("")))?new BigDecimal (lineSplit[7]):BigDecimal.ZERO);
			}
			if(lineSplit.length>8){
				this.settipodocumentoreceptor(lineSplit[8]);
			}
			if(lineSplit.length>9){
				this.setdocumentoreceptor(lineSplit[9]);
			}
			if(lineSplit.length>10){
				this.setnombrereceptor(lineSplit[10]);
			}
			if(lineSplit.length>11){
				this.setdireccionreceptor(lineSplit[11]);
			}
			if(lineSplit.length>12){
				this.setciudadreceptor(lineSplit[12]);
			}
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("No existe tipo de lina codigo"+lineSplit[2]+")");

		}
		
	}
	
}

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
public class MRTTicketLineTktCteCFE extends
		X_UY_RT_Ticket_LineTktCteCFE {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineTktClienteCFE_ID
	 * @param trxName
	 */
	public MRTTicketLineTktCteCFE(Properties ctx,
			int UY_RT_Ticket_LineTktClienteCFE_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineTktClienteCFE_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineTktCteCFE(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}

	public void parseLineTktCteCFE(String[] lineSplit,
			String fchCabezal, int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, get_TrxName());
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.settipodocumento(lineSplit[4]);
			this.setcodigopais(lineSplit[5]);
			this.setdocumento(lineSplit[6]);
			this.setnombre(lineSplit[7]);
			this.setdireccion(lineSplit[8]);
			if(lineSplit.length>9){
				this.setciudad(lineSplit[9]);
			}
			if(lineSplit.length>10){
				this.setcodigodepartamento(lineSplit[10]);
			}
			if(lineSplit.length>11){
				this.setcodigopostal(lineSplit[11]);
			}
			if(lineSplit.length>12){
				this.setrut(lineSplit[12]);
			}
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("No existe tipo de lina codigo"+lineSplit[2]+")");

		}
		
	}
	
}

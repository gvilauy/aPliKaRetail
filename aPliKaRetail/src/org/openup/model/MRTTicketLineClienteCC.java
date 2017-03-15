/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;



import org.adempiere.exceptions.AdempiereException;
//import org.openup.util.Converter;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**Especificacion de la línea de ticket de cliente de cuenta
 *  corriente (clientes especiales).
 * OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTTicketLineClienteCC extends X_UY_RT_Ticket_LineClienteCC {
	
	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineClienteCC_ID
	 * @param trxName
	 */
	public MRTTicketLineClienteCC(Properties ctx,
			int UY_RT_Ticket_LineClienteCC_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineClienteCC_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineClienteCC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 */
	public void parseLineClienteCC(String[] lineSplit,
			String fchCabezal, int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, get_TrxName());
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setcodigocc((lineSplit[4]!=null)?Integer.valueOf(lineSplit[4]):0); //4
			this.setnombrecc(lineSplit[5]); //5
			this.setmontopagocc((lineSplit[6]!=null)?new BigDecimal (lineSplit[6]):BigDecimal.ZERO); //6
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("No existe tipo de lina codigo"+lineSplit[2]+")");

		}
		
	}

}

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
 * @author SBT 12/1/2016
 *
 */
public class MRTTicketDescAlTotal extends X_UY_RT_Ticket_DescAlTotal {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_DescAlTotal_ID
	 * @param trxName
	 */
	public MRTTicketDescAlTotal(Properties ctx,
			int UY_RT_Ticket_DescAlTotal_ID, String trxName) {
		super(ctx, UY_RT_Ticket_DescAlTotal_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketDescAlTotal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	public void parseDescTotal(String[] lineSplit,String fchCabezal,int idHeader,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setOrigen(lineSplit[4]);//4
			this.setcodigobeneficio(lineSplit[5]); //5
			this.setcodigodescuento(lineSplit[6]); //6
			this.setimportedescuento((lineSplit[7]!=null)?new BigDecimal(lineSplit[7]):BigDecimal.ZERO); //7
			this.setivadescuento((lineSplit[8]!=null)?new  BigDecimal(lineSplit[8]):BigDecimal.ZERO); //8
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(idHeader);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo ticket: "+lineSplit[2]);
		}
	
	}
}

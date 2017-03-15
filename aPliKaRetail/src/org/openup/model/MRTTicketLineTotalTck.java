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

/**Contiene el total del ticket:
 * OpenUp Ltda Issue #
 * @author SBT 16/12/2015
 *
 */
public class MRTTicketLineTotalTck extends X_UY_RT_Ticket_LineTotalTckt {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineTotalTckt_ID
	 * @param trxName
	 */
	public MRTTicketLineTotalTck(Properties ctx,
			int UY_RT_Ticket_LineTotalTckt_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineTotalTckt_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineTotalTck(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public void parseLineTotalTickt(String[] lineSplit,
			String fchCabezal, int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, get_TrxName());
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.settotalticket((lineSplit[4]!=null && !(lineSplit[4].equals("")))?new BigDecimal (lineSplit[4]):BigDecimal.ZERO);
			this.setivatotalticket((lineSplit[5]!=null && !(lineSplit[5].equals("")))?new BigDecimal (lineSplit[5]):BigDecimal.ZERO);
			this.settotaltcktsinpagoserv((lineSplit[6]!=null && !(lineSplit[6].equals("")))?new BigDecimal (lineSplit[6]):BigDecimal.ZERO);
			this.setivatotaltcktsinpagoserv((lineSplit[7]!=null && !(lineSplit[7].equals("")))?new BigDecimal (lineSplit[7]):BigDecimal.ZERO);
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("No existe tipo de lina codigo"+lineSplit[2]+")");

		}
		
	}

}

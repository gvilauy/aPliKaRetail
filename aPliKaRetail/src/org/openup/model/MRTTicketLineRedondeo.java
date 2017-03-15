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
public class MRTTicketLineRedondeo extends X_UY_RT_Ticket_LineRedondeo {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineRedondeo_ID
	 * @param trxName
	 */
	public MRTTicketLineRedondeo(Properties ctx,
			int UY_RT_Ticket_LineRedondeo_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineRedondeo_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineRedondeo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	public void parseLineRedondeo(String[] lineSplit,String fchCabezal,int idHeader,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);//2 tipolinea
		MRTCodigoMedioPago codMdoPago = MRTCodigoMedioPago.forValue(getCtx(),lineSplit[7],null);//7 codigomediopago
		if(tipoTkt!=null && codMdoPago!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setimporteticket((lineSplit[4]!=null)?new BigDecimal(lineSplit[4]):BigDecimal.ZERO); //7
			this.setimporteredondeo((lineSplit[5]!=null)?new BigDecimal(lineSplit[5]):BigDecimal.ZERO); //7
			this.setimportetotalticket((lineSplit[6]!=null)?new BigDecimal(lineSplit[6]):BigDecimal.ZERO); //7

			this.setUY_RT_CodigoMedioPago_ID(codMdoPago.get_ID()); //7
			this.setcodigomoneda(lineSplit[8]); //8
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(idHeader);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo ticket: "+lineSplit[2]);
			if(codMdoPago==null)throw new AdempiereException("Falta parametrizar medio de pago: "+lineSplit[4]);
		}
	
	}
	
}

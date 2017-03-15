/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**
 * Línea de pago con cheque cobranza (94).
   Línea de ticket cuando se paga con cheque pero para la cobranza (pago de servicios).
 * OpenUp Ltda Issue #4976 - Mejora se agrega el tipo de linea 94
 * @author SBT 2/12/2015
 *
 */
public class MTicketLinePServCheck extends X_UY_RT_Ticket_LinePServCheck {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LinePServCheck_ID
	 * @param trxName
	 */
	public MTicketLinePServCheck(Properties ctx,
			int UY_RT_Ticket_LinePServCheck_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LinePServCheck_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTicketLinePServCheck(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Se parocesa la línea para guardar cada dato en el campo correspondiete
	 * OpenUp Ltda Issue #4976
	 * @author Sylvie Bouissa 2/12/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param posicionfila
	 */
	public void parseLinePagoServCheck(String[] lineSplit, String fchCabezal,
			int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		MRTCodigoMedioPago codMdoPago = MRTCodigoMedioPago.forValue(getCtx(),lineSplit[4],null);
		if(tipoTkt!=null && codMdoPago!=null){
			
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setUY_RT_CodigoMedioPago_ID(codMdoPago.get_ID()); //4
			this.setcodigomoneda(lineSplit[5]); //5
			
			this.settotalmeidopago((lineSplit[6]!=null)?new BigDecimal(lineSplit[6]):BigDecimal.ZERO);//6
			this.settotalmediopagomonedareferencia((lineSplit[7]!=null)?new BigDecimal(lineSplit[7]):BigDecimal.ZERO);//7
			this.setcambio((lineSplit[8]!=null)?new BigDecimal(lineSplit[8]):BigDecimal.ZERO);//8
			this.settipooperacion(lineSplit[9]);//9
			this.setlineaultimopago(lineSplit[10]);//10
			this.settipocliente(lineSplit[11]);//11
			this.setidcliente(lineSplit[12]);//12
			this.setautorizasupervisora(lineSplit[13]);//13
			if(OpenUpUtilsRT.autorizaSupervisora.equals(lineSplit[13])){
				this.setcodigosupervisora(lineSplit[14]); //15
			}
			this.setlineacancelada(lineSplit[15]);//15
			
			MBPartner bp = MBPartner.forValue(getCtx(), this.getidcliente(), null);
			if(null!=bp)this.setC_BPartner_ID(bp.get_ID());
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
			if(codMdoPago==null)throw new AdempiereException("Falta parametrizar medio de pago: "+lineSplit[4]);
		}
	}
}

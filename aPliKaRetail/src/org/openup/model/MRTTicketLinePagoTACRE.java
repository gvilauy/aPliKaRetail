/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;


/**Representa la línea de pago por Tarjeta Ticket Alimentación/Canasta/Restaurant de froma electrónica
 * OpenUp Ltda Issue# 
 * @author SBT 11/12/2015
 *
 */
public class MRTTicketLinePagoTACRE extends X_UY_RT_Ticket_LinePagoTACRE {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LinePagoTACRE_ID
	 * @param trxName
	 */
	public MRTTicketLinePagoTACRE(Properties ctx,
			int UY_RT_Ticket_LinePagoTACRE_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LinePagoTACRE_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLinePagoTACRE(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param numerofila
	 */
	public void parsePagoTacre(String[] lineSplit, String fchCabezal,
			int headerId,String posicionFila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		MRTCodigoMedioPago codMdoPago = MRTCodigoMedioPago.forValue(getCtx(),lineSplit[4],null);
		if(tipoTkt!=null && codMdoPago!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setUY_RT_CodigoMedioPago_ID(codMdoPago.get_ID()); //4
			this.setcodigomoneda(lineSplit[5]); //5
			this.settotalmeidopagomoneda((lineSplit[6]!=null)?new BigDecimal(lineSplit[6]):BigDecimal.ZERO); //6
			this.settotalmediopagomonedareferencia((lineSplit[7]!=null)?new BigDecimal(lineSplit[7]):BigDecimal.ZERO); //7
			
			this.settotalpagado((lineSplit[8]!=null)?new BigDecimal(lineSplit[8]):BigDecimal.ZERO); 
			this.settotalpagadomonedareferencia((lineSplit[9]!=null)?new BigDecimal(lineSplit[9]):BigDecimal.ZERO); 
			this.setcambio((lineSplit[10]!=null)?new BigDecimal(lineSplit[10]):BigDecimal.ZERO); //10
			this.settipooperacion(lineSplit[11]); //11
			this.setlineaultimopago(lineSplit[12]); //12
			
			this.setnumerotarjeta(lineSplit[13]); //13
			this.setnumeroautorizacion(lineSplit[14]); //14
			this.setautorizasupervisora(lineSplit[15]); //15
			if(OpenUpUtilsRT.autorizaSupervisora.equals(lineSplit[15])){
				this.setcodigosupervisora(lineSplit[16]); //16
			}		
			
			this.setlineacancelada(lineSplit[17]); //17
			
			this.setsiaplicaleydesciva(lineSplit[18]);//18
			this.setmontodescuentoleyiva((lineSplit[19]!=null)?new BigDecimal(lineSplit[19]):BigDecimal.ZERO);//19
			if(lineSplit.length>20){
				this.settextoley(lineSplit[20]);//20
			}
			
			this.setpositionfile(posicionFila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
			if(codMdoPago==null)throw new AdempiereException("Falta parametrizar medio de pago: "+lineSplit[4]);
		}
	}
}

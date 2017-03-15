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

/**
 * OpenUp Ltda Issue#
 * @author SBT 12/1/2016
 *
 */
public class MRTTicketBenefAlTotal extends X_UY_RT_Ticket_BenefAlTotal {

	/**L�nea de detalle de un beneficio al total (27).
		Representa las l�neas de Ticket que contienen la informaci�n de los Beneficios al Total de la Venta.
		(P�gina 40 Doc SalidaPazos4)
	 * @param ctx
	 * @param UY_RT_Ticket_BenefAlTotal_ID
	 * @param trxName
	 */
	public MRTTicketBenefAlTotal(Properties ctx,
			int UY_RT_Ticket_BenefAlTotal_ID, String trxName) {
		super(ctx, UY_RT_Ticket_BenefAlTotal_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketBenefAlTotal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 12/1/2016
	 * @param lineSplit
	 * @param fchCabezal
	 * @param idHeader
	 * @param posicionfila
	 */
	public void parseBenefTotal(String[] lineSplit,String fchCabezal,int idHeader,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			
			this.setcodigobeneficio(lineSplit[4]);//4
			this.settipobeneficio(lineSplit[5]); //5
			
			//SBT 09/12/2016 - ISsue #8112
			if(lineSplit[5].equalsIgnoreCase("D")){
				this.setcodigodescuento(lineSplit[6]); //6
				this.setimportedescuento((lineSplit[7]!=null && (!lineSplit[7].toString().isEmpty()))?new BigDecimal(lineSplit[7]):BigDecimal.ZERO); //7
				this.setivadescuento((lineSplit[8]!=null && (!lineSplit[8].toString().isEmpty()))?new  BigDecimal(lineSplit[8]):BigDecimal.ZERO); //8
			}
						
			if(9<lineSplit.length){
				this.setpuntosextras(lineSplit[9]); //9
			}
			if(10<lineSplit.length){
				this.setvecespuntosextras(lineSplit[10]); //10
			}
			if(11<lineSplit.length){
				this.setcantidadcupones(lineSplit[11]); //11
			}
			if(12<lineSplit.length){
				this.setvecescupones(lineSplit[12]); //12
			}
			if(13<lineSplit.length){
				this.setcodigoarticuloregalo(lineSplit[13]); //13
			}
			if(14<lineSplit.length){	
				this.setcantidadarticuloregalo(lineSplit[14]);//14
			}
			if(15<lineSplit.length){	
				this.setvecesarticuloregalo(lineSplit[15]);//15
			}
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(idHeader);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo ticket: "+lineSplit[2]);
		}
	
	}
	
	
}

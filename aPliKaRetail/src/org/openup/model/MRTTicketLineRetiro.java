/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;







import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
//import org.openup.util.Converter;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;
import org.zkoss.zhtml.Big;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTTicketLineRetiro extends X_UY_RT_Ticket_LineRetiro {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineRetiro_ID
	 * @param trxName
	 */
	public MRTTicketLineRetiro(Properties ctx, int UY_RT_Ticket_LineRetiro_ID,
			String trxName) {
		super(ctx, UY_RT_Ticket_LineRetiro_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineRetiro(Properties ctx, ResultSet rs, String trxName) {
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
	public void parseLineRetiro(String[] lineSplit, String fchCabezal,
			int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		MRTCodigoMedioPago codMdoPago = MRTCodigoMedioPago.forValue(getCtx(),lineSplit[4],null);
		if(tipoTkt!=null && codMdoPago!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			this.setUY_RT_CodigoMedioPago_ID(codMdoPago.get_ID()); //4
			this.setcodigomoneda(lineSplit[5]); //5
			this.setmontoretiro((lineSplit[6]!=null)? new BigDecimal(lineSplit[6]):BigDecimal.ZERO);//6
		
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
			if(codMdoPago==null)throw new AdempiereException("Falta parametrizar medio de pago: "+lineSplit[4]);
		}
	}

	/**Se suma el monto total de las lineas dependiendo el tipo de moneda y medio de pago pasados
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 08/05/2015
	 * @param ctx
	 * @param string
	 * @param string2
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularMontoRetiro(Properties ctx, String codMoneda,
			String cdoMedioPago, int Cov_LoadTicket_ID, String trxName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_UY_RT_Ticket_LineRetiro.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_UY_RT_Ticket_LineRetiro.COLUMNNAME_UY_RT_CodigoMedioPago_ID +" = "+
				MRTCodigoMedioPago.forValue(ctx, cdoMedioPago, trxName).get_ID();
				;
		try{
			
			// Sumo el monto de retiro de las lineas correspondientes
			String sql = "SELECT SUM(montoretiro) FROM UY_RT_Ticket_LineRetiro WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +Cov_LoadTicket_ID +")";
			//" AND estadoticket = 'F' )";  AND UY_RY_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
			pstmt = DB.prepareStatement (sql, trxName);
			rs = pstmt.executeQuery ();
					
			while (rs.next()){
				retorno = rs.getBigDecimal(1);
			}
		
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}finally{
			try{
				if(rs!=null){
					rs.close();
					if(pstmt!=null){
						pstmt.close();
					}
				}
			}catch(Exception e){
				
			}	
			//OpenUpUtils.closePS(pstmt);
			//OpenUpUtils.closeRS(rs);
		}
		if(retorno!=null){
			return retorno;
		}else{
			return BigDecimal.ZERO;
		}
	}

}

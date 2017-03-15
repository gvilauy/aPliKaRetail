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

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTTicketLineEfectivo extends X_UY_RT_Ticket_LineEfectivo {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineEfectivo_ID
	 * @param trxName
	 */
	public MRTTicketLineEfectivo(Properties ctx,
			int UY_RT_Ticket_LineEfectivo_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineEfectivo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineEfectivo(Properties ctx, ResultSet rs, String trxName) {
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
	public void parseLineaEfectivo(String[] lineSplit,String fchCabezal,int idHeader,String posicionfila) {
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
			this.settotalentregado((lineSplit[8]!=null)?new BigDecimal(lineSplit[8]):BigDecimal.ZERO); //8
			this.settotalentregadomonedareferencia((lineSplit[9]!=null)?new  BigDecimal(lineSplit[9]):BigDecimal.ZERO); //9
			this.setcambio((lineSplit[10]!=null)?new BigDecimal(lineSplit[10]):BigDecimal.ZERO); //10
			this.settipooperacion(lineSplit[11]); //11
			this.setlineaultimopago(lineSplit[12]); //12
			this.setautorizasupervisora(lineSplit[13]); //13
			if(OpenUpUtilsRT.autorizaSupervisora.equals(lineSplit[13])){
				this.setcodigosupervisora(lineSplit[14]); //14
			}
			this.setlineacancelada(lineSplit[15]); //15
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(idHeader);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo ticket: "+lineSplit[2]);
			if(codMdoPago==null)throw new AdempiereException("Falta parametrizar medio de pago: "+lineSplit[4]);
		}
	
	}

	/**Calcula las ventas por moneda y por medio de pago 
	 * Si es en dolares solo se toma en cuenta el total entregado ya que el cambio se especifica en pesos
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param string
	 * @param string2
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularVentaMonedaCodigoMdioPago(Properties ctx,String codMoneda, String cdoMedioPago, int Cov_LoadTicket_ID,
			String trxName) {
		String calculo = "totalentregado - cambio";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_UY_RT_Ticket_LineEfectivo.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_UY_RT_Ticket_LineEfectivo.COLUMNNAME_UY_RT_CodigoMedioPago_ID +" = "+
				MRTCodigoMedioPago.forValue(ctx, cdoMedioPago, trxName).get_ID();
				;
		if(!codMoneda.equals("1")){//Si no es en pesos solo se contempla el total entregado
			calculo = "totalentregado";
		}
		try{
			MRTTicketType venta = MRTTicketType.forValueAndHeader(ctx, MRTTicketType.vVenta, OpenUpUtilsRT.isHeader, trxName);
			MRTTicketType dev = MRTTicketType.forValueAndHeader(ctx,  MRTTicketType.vDevolucion, OpenUpUtilsRT.isHeader, trxName);
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT SUM("+calculo+") FROM UY_RT_Ticket_LineEfectivo WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +Cov_LoadTicket_ID +
					" AND estadoticket = 'F' AND UY_RT_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
			pstmt = DB.prepareStatement (sql, trxName);
			rs = pstmt.executeQuery ();
					
			while (rs.next()){
				retorno = (BigDecimal) rs.getObject(1);
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
	
	
	/**Suma el campo cambio discriminando por moneda (Pesos,Dolares)
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param string
	 * @param string2
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularCambioMoneda(Properties ctx,
			String codMoneda, String cdoMedioPago, int Cov_LoadTicket_ID, String trxName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_UY_RT_Ticket_LineEfectivo.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_UY_RT_Ticket_LineEfectivo.COLUMNNAME_UY_RT_CodigoMedioPago_ID +" = "+
				MRTCodigoMedioPago.forValue(ctx, cdoMedioPago, trxName).get_ID();
				;
		try{
			MRTTicketType venta = MRTTicketType.forValueAndHeader(ctx, MRTTicketType.vVenta, OpenUpUtilsRT.isHeader, trxName);
			MRTTicketType dev = MRTTicketType.forValueAndHeader(ctx, MRTTicketType.vDevolucion, OpenUpUtilsRT.isHeader, trxName);
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT SUM(cambio) as cambioa FROM UY_RT_Ticket_LineEfectivo WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +Cov_LoadTicket_ID +
					" AND estadoticket = 'F' AND UY_RT_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
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

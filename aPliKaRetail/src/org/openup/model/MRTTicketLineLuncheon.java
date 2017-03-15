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
public class MRTTicketLineLuncheon extends X_UY_RT_Ticket_LineLuncheon {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineLuncheon_ID
	 * @param trxName
	 */
	public MRTTicketLineLuncheon(Properties ctx,
			int UY_RT_Ticket_LineLuncheon_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineLuncheon_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineLuncheon(Properties ctx, ResultSet rs, String trxName) {
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
	public void parseLineLuncheon(String[] lineSplit, String fchCabezal,
			int headerId,String posicionfila) {
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
			//TotalPagado pos 8 -->27/11/2015 Issue #4976
			this.settotalentregado((lineSplit[8]!=null)?new BigDecimal(lineSplit[8]):BigDecimal.ZERO); //8
			//TotalPagadoMonedaReferencia pos 9 -->27/11/2015 Issue #4976
			this.settotalentregadomonedareferencia((lineSplit[9]!=null)?new  BigDecimal(lineSplit[9]):BigDecimal.ZERO); //9
			this.setcambio((lineSplit[10]!=null)?new BigDecimal(lineSplit[10]):BigDecimal.ZERO); //10
			this.settipooperacion(lineSplit[11]); //11
			this.setlineaultimopago(lineSplit[12]); //12			
			this.setcodigobarras(lineSplit[13]); //13
			this.setmodoingreso(lineSplit[14]); //14
			this.setautorizasupervisora(lineSplit[15]); //15
			if(OpenUpUtilsRT.autorizaSupervisora.equals(lineSplit[15])){
				this.setcodigosupervisora(lineSplit[16]); //16
			}
			this.setlineacancelada(lineSplit[17]); //17 
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
			if(codMdoPago==null)throw new AdempiereException("Falta parametrizar medio de pago: "+lineSplit[4]);
		}
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 08/05/2015
	 * @param ctx
	 * @param string
	 * @param get_ID
	 * @param object
	 * @return
	 */
	public static BigDecimal calcularMontoPorMoneda(Properties ctx,
			String codMoneda, int _LoadTicket_ID, String trxName) {

		PreparedStatement pstmt = null;
		BigDecimal retorno = BigDecimal.ZERO;
		ResultSet rs = null;
		
		String whereClause = X_UY_RT_Ticket_LineLuncheon.COLUMNNAME_IsActive+ " = 'Y' AND "
				+ X_UY_RT_Ticket_LineLuncheon.COLUMNNAME_codigomoneda +" = '"+codMoneda+"' ";
				
		try{
			
			// Sumo el monto de retiro de las lineas correspondientes
			String sql = "SELECT SUM(totalentregado) FROM UY_RT_Ticket_LineLuncheon WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +_LoadTicket_ID +" )";  //AND UY_RY_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
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

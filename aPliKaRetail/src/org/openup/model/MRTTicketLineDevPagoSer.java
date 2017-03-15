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
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/** Línea de devolución de pago de servicio (81).
 * Representa la línea de devolución de un pago de servicio.
 * OpenUp Ltda Issue #5329
 * @author SBT 19/1/2016
 *
 */
public class MRTTicketLineDevPagoSer extends X_UY_RT_Ticket_LineDevPagoSer {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineDevPagoSer_ID
	 * @param trxName
	 */
	public MRTTicketLineDevPagoSer(Properties ctx,
			int UY_RT_Ticket_LineDevPagoSer_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineDevPagoSer_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineDevPagoSer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * OpenUp Ltda Issue #5329
	 * @author Sylvie Bouissa 19/1/2016
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param posicionFila
	 */
	public void parseDevPagoServicios(String[] lineSplit, String fchCabezal,
			int headerId,String posicionFila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		MRTCodigoServicio tipoServ = MRTCodigoServicio.forValue(getCtx(), lineSplit[4], null); //4
		if(tipoTkt!=null && tipoServ!= null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setUY_RT_CodigoServicio_ID(tipoServ.get_ID());//4
			this.setmonto((lineSplit[5]!=null)? new BigDecimal(lineSplit[5]):BigDecimal.ZERO); //5
			this.setcodigomoneda((lineSplit[6]!=null)? lineSplit[6]:null); //6
			this.setmodoingreso((lineSplit[7]!=null)?lineSplit[7]:null); //7
			this.setcodigosupervisora((lineSplit[8]!=null)?lineSplit[8]:null);//8
			this.setcodigovendedor((lineSplit[9]!=null)?lineSplit[9]:null); //9
			this.setreferencia((lineSplit[10]!=null)?lineSplit[10]:null); //10
			this.setlineacancelada((lineSplit[11]!=null)?lineSplit[11]:null); //11
			
			this.setpositionfile(posicionFila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("No existe tipo de lina o codigo de servicio (linea"+posicionFila+")");
		}
		
	}
	
	/**
	 * 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 19/1/2016
	 * @param ctx
	 * @param codMoneda
	 * @param _LoadTicket_ID
	 * @param trxName
	 * @return
	 */
	
	public static BigDecimal calcularMontoTotalDevolucion(Properties ctx, String codMoneda,
			int _LoadTicket_ID, String trxName) {
		PreparedStatement pstmt = null;
		BigDecimal retorno = BigDecimal.ZERO;
		ResultSet rs = null;
		
		String whereClause = X_UY_RT_Ticket_LineDevPagoSer.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_UY_RT_Ticket_LineDevPagoSer.COLUMNNAME_IsActive +" = 'Y' ";
		try{
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT case when SUM(monto) is null then 0 else SUM(monto) end FROM UY_RT_Ticket_LineDevPagoSer WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN "
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +_LoadTicket_ID +")";
		
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
		}
		if(retorno!=null){
			return retorno;
		}else{
			return BigDecimal.ZERO;
		}
	}
	
}

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
public class MRTTicketLinePagoServ extends X_UY_RT_Ticket_LinePagoServ {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LinePagoServ_ID
	 * @param trxName
	 */
	public MRTTicketLinePagoServ(Properties ctx,
			int UY_RT_Ticket_LinePagoServ_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LinePagoServ_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLinePagoServ(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param numerofila
	 */
	public void parsePagoServicios(String[] lineSplit, String fchCabezal,
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
			this.setcodigovendedor((lineSplit[8]!=null)?lineSplit[8]:null); //8
			this.setreferencia((lineSplit[9]!=null)?lineSplit[9]:null); //9
			this.setlineacancelada((lineSplit[10]!=null)?lineSplit[10]:null); //10
			this.setpositionfile(posicionFila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("No existe tipo de lina o codigo de servicio (linea"+posicionFila+")");
		}
		
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 12/05/2015
	 * @param ctx
	 * @param string
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularMontoTotal(Properties ctx, String codMoneda,
			int _LoadTicket_ID, String trxName) {
		PreparedStatement pstmt = null;
		BigDecimal retorno = BigDecimal.ZERO;
		ResultSet rs = null;
		
		String whereClause = X_UY_RT_Ticket_LinePagoServ.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_UY_RT_Ticket_LinePagoServ.COLUMNNAME_IsActive +" = 'Y' ";
		try{
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT case when SUM(monto) is null then 0 else SUM(monto) end FROM UY_RT_Ticket_LinePagoServ WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN "
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

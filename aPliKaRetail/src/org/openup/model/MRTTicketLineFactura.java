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
public class MRTTicketLineFactura extends X_UY_RT_Ticket_LineFactura {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineFactura_ID
	 * @param trxName
	 */
	public MRTTicketLineFactura(Properties ctx,
			int UY_RT_Ticket_LineFactura_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineFactura_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineFactura(Properties ctx, ResultSet rs, String trxName) {
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
	public void parseLineFactura(String[] lineSplit, String fchCabezal,
			int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			try{
				this.setnumerodelinea(lineSplit[1]); //1
				this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
				this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
				
				this.setruc(lineSplit[4]); //4
				this.setcaja(lineSplit[5]); //5
				this.setcajaticket(lineSplit[6]); //6
				this.setnumeroticket(lineSplit[7]); //7
				this.setticketreferencia(lineSplit[8]);  //8
				this.setfechafactura(Convertir.convertirddMMYYYY_YYYYMMdd(lineSplit[9])); //11-10-2008
				
				this.settotalfactura((lineSplit[10]!=null)?new BigDecimal (lineSplit[10]):BigDecimal.ZERO);
				this.setimpoteivacodigo((lineSplit[11]!=null)?new BigDecimal (lineSplit[11]):BigDecimal.ZERO);
				
				this.setimpoteivacodigo1((lineSplit[12]!=null)?new BigDecimal (lineSplit[12]):BigDecimal.ZERO);
				this.setimpoteivacodigo2((lineSplit[13]!=null)?new BigDecimal (lineSplit[13]):BigDecimal.ZERO);
				this.setimpoteivacodigo3((lineSplit[14]!=null)?new BigDecimal (lineSplit[14]):BigDecimal.ZERO);
				this.setimpoteivacodigo4((lineSplit[15]!=null)?new BigDecimal (lineSplit[15]):BigDecimal.ZERO);
				this.setimpoteivacodigo5((lineSplit[16]!=null)?new BigDecimal (lineSplit[16]):BigDecimal.ZERO);
				this.setimpoteivacodigo6((lineSplit[17]!=null)?new BigDecimal (lineSplit[17]):BigDecimal.ZERO);
				this.setimpoteivacodigo7((lineSplit[18]!=null)?new BigDecimal (lineSplit[18]):BigDecimal.ZERO);
				
				this.setserienrofactura(lineSplit[19]);
				this.setpositionfile(posicionfila);
				this.setUY_RT_Ticket_Header_ID(headerId);
			}catch(Exception e){
				throw new AdempiereException(e.getMessage());
			}
			
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
		}
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 08/05/2015
	 * @param ctx
	 * @param string
	 * @param string2
	 * @param object
	 * @return
	 */
	public static BigDecimal calcularMontoXTipoCabezal(Properties ctx,
			String tipoCabezal, int _LoadTicket_ID, String trxName) {
		System.out.println(""+tipoCabezal);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_UY_RT_Ticket_LineFactura.COLUMNNAME_IsActive+ " = 'Y' ";

		try{
			
			// Sumo el monto de retiro de las lineas correspondientes
			String sql = "SELECT SUM(TotalFactura) FROM UY_RT_Ticket_LineFactura WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +_LoadTicket_ID +
			" AND UY_RT_TicketType_ID = "+MRTTicketType.forValueAndHeader(ctx, tipoCabezal, "Y", trxName).get_ID()+" )";  //AND UY_RY_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
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

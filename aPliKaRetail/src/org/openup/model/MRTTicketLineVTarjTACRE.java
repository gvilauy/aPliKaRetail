/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**OpenUp Ltda Issue#
 * @author SBT 11/12/2015
 *
 */
public class MRTTicketLineVTarjTACRE extends X_UY_RT_Ticket_LineVTarjTACRE {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineVTarjTACRE_ID
	 * @param trxName
	 */
	public MRTTicketLineVTarjTACRE(Properties ctx,
			int UY_RT_Ticket_LineVTarjTACRE_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineVTarjTACRE_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineVTarjTACRE(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 11/12/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param posicionfila
	 */
	public void parseVaucherTjtaTacre(String[] lineSplit, String fchCabezal,
			int headerId, String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setnumerotarjeta(lineSplit[4]);//4
			this.setVencimiento(lineSplit[5]);//5
			this.setcomprobante(lineSplit[6]);//6
			this.setautorizacion(lineSplit[7]);//7
			this.setcodigoterminal(lineSplit[8]);//8
			this.setcodigocomercio(lineSplit[9]);//9
			this.settipoautorizacion(lineSplit[10]);//10
			this.setNroLote(lineSplit[11]);//11
			this.setcodigomoneda(lineSplit[12]); //12
			this.settipotransaccion(lineSplit[13]);//13
			this.settipovaucher(lineSplit[14]);//14
			this.setImportePago((lineSplit[15]!=null)?new BigDecimal(lineSplit[15]):BigDecimal.ZERO);//15
			//Parsear fecha
			String s = lineSplit[16];
			String newDate = s.substring(6,10)+'-'+s.substring(3,5)+'-'+s.substring(0,2)+s.substring(10);
			this.setfechatransaccion(Timestamp.valueOf(newDate));//16
			this.setcodigocaja(lineSplit[17]);//17
			this.setcodigocajera(lineSplit[18]);//18
			this.setnombrepropuetario(lineSplit[19]);//19
			this.settipoingreso(lineSplit[20]);//20
			this.setdescuentoiva(lineSplit[21]);//21
			this.setsiaplicaleydesciva(lineSplit[22]);//22
			this.setmontodescuentoleyiva((lineSplit[23]!=null)?new BigDecimal(lineSplit[23]):BigDecimal.ZERO);//23
			this.setmontofactura((lineSplit[24]!=null)?new BigDecimal(lineSplit[24]):BigDecimal.ZERO);;//24
			this.setmontogravado((lineSplit[25]!=null)?new BigDecimal(lineSplit[25]):BigDecimal.ZERO);;//25
			this.setnombretarjeta(lineSplit[26]);//26
			this.setflagimprimefirma(lineSplit[27]);//27
			
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
		}
		
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 11/12/2015
	 * @param ctx
	 * @param string
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularMontoEdenredSodexo(Properties ctx,
			String codigoMoneda,int m_LoadTicket_ID, String trxName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal retorno = BigDecimal.ZERO;
		
		String whereClause = X_UY_RT_Ticket_LineVTarjTACRE.COLUMNNAME_IsActive+ " = 'Y' "
				+ " AND "+ X_UY_RT_Ticket_LineVTarjTACRE.COLUMNNAME_codigomoneda+ " = '"+codigoMoneda+"'" // 00 pesos
				+ " AND "+X_UY_RT_Ticket_LineVTarjTACRE.COLUMNNAME_tipovaucher+ " = '1' "; //-->Original cliente

		try{
			
			// Sumo el monto de retiro de las lineas correspondientes
			String sql = "SELECT SUM(ImportePago) FROM UY_RT_Ticket_LineVTarjTACRE WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +m_LoadTicket_ID +" )";  //AND UY_RY_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
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

/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.IOUtils;
import org.compiere.util.DB;
//import org.openup.util.Converter;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**LINEA DE PAGO CON TARJETA (37) - SM -->27/11/2015
 * OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTTicketLineTarjeta extends X_UY_RT_Ticket_LineTarjeta {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineTarjeta_ID
	 * @param trxName
	 */
	public MRTTicketLineTarjeta(Properties ctx,
			int UY_RT_Ticket_LineTarjeta_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineTarjeta_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineTarjeta(Properties ctx, ResultSet rs, String trxName) {
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
	public void parseLineTarjeta(String[] lineSplit, String fchCabezal,
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
			
			//TotalPagado// pos 8-->27/11/2015 Issue #4976
			this.settotalentregado((lineSplit[8]!=null)?new BigDecimal(lineSplit[8]):BigDecimal.ZERO); //8
			//TotalPagadoMonedaReferencia // pos 9-->27/11/2015 Issue #4976
			this.settotalentregadomonedareferencia((lineSplit[9]!=null)?new  BigDecimal(lineSplit[9]):BigDecimal.ZERO); //9
			this.setcambio((lineSplit[10]!=null)?new BigDecimal(lineSplit[10]):BigDecimal.ZERO); //10
			this.settipooperacion(lineSplit[11]); //11
			this.setlineaultimopago(lineSplit[12]); //12
			
			this.setnumerotarjetacredito(lineSplit[13]); //13
			this.setcuotastarjetacredito(lineSplit[14]); //14
			this.setnumautorizaciontjtacred(lineSplit[15]);//15
			this.settipotarjetacredito(lineSplit[16]);//16
			
			this.setautorizasupervisora(lineSplit[17]); //17
			if(OpenUpUtilsRT.autorizaSupervisora.equals(lineSplit[17])){
				this.setcodigosupervisora(lineSplit[18]); //18
			}
			this.setlineacancelada(lineSplit[19]); //19
			
			this.setPlan(lineSplit[20]);//Plan //pos 20 -->27/11/2015 Issue #4976
			this.setNroComercio(lineSplit[21]);//NroComercio //pos 21 -->27/11/2015 Issue #4976
			this.setsiaplicaleydesciva(lineSplit[22]);//ANTES this.setsiaplicaleydesciva(lineSplit[20]); -->27/11/2015 Issue #4976
			this.setmontodescuentoleyiva((lineSplit[23]!=null)?new BigDecimal(lineSplit[23]):BigDecimal.ZERO);//ANTES this.setmontodescuentoleyiva((lineSplit[21]!=null)?new BigDecimal(lineSplit[21]):BigDecimal.ZERO);
			
			this.settextoley(lineSplit[24]);//TextoLey //pos24 -->27/11/2015 Issue #4976
			this.setSiEsDebitoCredito(lineSplit[25]);//SiEsDebitoCredito 0-No especificado 1-La tarjeta es de credito.-->27/11/2015 Issue #4976
			
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
	 * @param cuotasTarjetaCredito  cuotasTarjetaCredito
	 * @param get_ID
	 * @param object
	 * @return
	 */
	public static BigDecimal calcularMontoTarjetaXCuotas(Properties ctx,
			String codMoneda, String cuotasTarjetaCredito, boolean igual,int _LoadTicket_ID, String trxName) {
		ResultSet rs = null;
		PreparedStatement pstmt =  null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_UY_RT_Ticket_LineTarjeta.COLUMNNAME_IsActive+ " = 'Y' AND "
				+ X_UY_RT_Ticket_LineTarjeta.COLUMNNAME_codigomoneda +" = '"+codMoneda+"' AND ";
		if(igual){
			whereClause += X_UY_RT_Ticket_LineTarjeta.COLUMNNAME_cuotastarjetacredito + " = '"+cuotasTarjetaCredito+"' ";
		}else{
			whereClause += X_UY_RT_Ticket_LineTarjeta.COLUMNNAME_cuotastarjetacredito + " != '"+cuotasTarjetaCredito+"' ";

		}
				
		try{
			
			// Sumo el monto de retiro de las lineas correspondientes
			String sql = "SELECT SUM(totalentregado) FROM UY_RT_Ticket_LineTarjeta WHERE "+whereClause+" AND UY_RT_Ticket_Header_ID IN"
					+ "(SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header WHERE UY_RT_LoadTicket_ID = " +_LoadTicket_ID +" )";  //AND UY_RY_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
			pstmt = DB.prepareStatement (sql, trxName);
			rs = pstmt.executeQuery ();
			//pstmt.setBigDecimal(0,valor)
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

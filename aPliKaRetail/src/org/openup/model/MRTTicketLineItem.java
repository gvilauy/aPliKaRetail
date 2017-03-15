/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;








import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.Env;
//import org.openup.util.Converter;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTTicketLineItem extends X_UY_RT_Ticket_LineItem {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineItem_ID
	 * @param trxName
	 */
	public MRTTicketLineItem(Properties ctx, int UY_RT_Ticket_LineItem_ID,
			String trxName) {
		super(ctx, UY_RT_Ticket_LineItem_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineItem(Properties ctx, ResultSet rs, String trxName) {
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
	public void parseLineItemVta(String[] lineSplit, String fchCabezal,
			int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setcodigoarticulo((lineSplit[4]!=null)? (lineSplit[4]):null);//4
			this.setCantidad((lineSplit[5]!=null)? new BigDecimal(lineSplit[5]):BigDecimal.ZERO); //5
			//Precio de la l�nea del articulo (cantidad x precio unitario) expresado en la moneda de referencia. Es el precio �limpio� al cual no se le efectu� ning�n descuento (no incluye iva, este va separado en el siguiente campo).
			this.setpreciounitario((lineSplit[6]!=null)? new BigDecimal(lineSplit[6]):BigDecimal.ZERO); //6
			this.setiva((lineSplit[7]!=null)? new BigDecimal(lineSplit[7]):BigDecimal.ZERO); //7
			this.setpreciodescuento((lineSplit[8]!=null)? new BigDecimal(lineSplit[8]):BigDecimal.ZERO); //8
			this.setivadescuento((lineSplit[9]!=null)? new BigDecimal(lineSplit[9]):BigDecimal.ZERO);; //9
			this.setpreciodescuentocombo((lineSplit[10]!=null)? new BigDecimal(lineSplit[10]):BigDecimal.ZERO); //10
			this.setivadescuentocombo((lineSplit[11]!=null)? new BigDecimal(lineSplit[11]):BigDecimal.ZERO); //11
			this.setpreciodescuentomarca((lineSplit[12]!=null)? new BigDecimal(lineSplit[12]):BigDecimal.ZERO); //12		
			
			this.setivadescuentomarca((lineSplit[13]!=null)? new BigDecimal(lineSplit[13]):BigDecimal.ZERO); //13
			this.setpreciodescuentototal((lineSplit[14]!=null)? new BigDecimal(lineSplit[14]):BigDecimal.ZERO); //14
			this.setivadescuentototal((lineSplit[15]!=null)? new BigDecimal(lineSplit[15]):BigDecimal.ZERO);//15
			this.setcantdescmanuales((lineSplit[16]!=null)? new BigDecimal(lineSplit[16]).intValue():BigDecimal.ZERO.intValue());//16
			
			this.setlineacancelada((lineSplit[17]!=null)? Integer.valueOf(lineSplit[17]):0); //17
			this.setmodoingreso((lineSplit[18]!=null)? lineSplit[18]:null);
			
			this.setcodigovendedor((lineSplit[19]!=null)? lineSplit[19]:null); //19
			this.settalle((lineSplit[20]!=null)? lineSplit[20]:null);
			this.setcolor((lineSplit[21]!=null)? lineSplit[21]:null);
			this.setmarca((lineSplit[22]!=null)? lineSplit[22]:null);
			this.setmodelo((lineSplit[23]!=null)? lineSplit[23]:null);
			this.setsiestandem((lineSplit[24]!=null)? lineSplit[24]:null);
			this.setcodigoarticulooriginal((lineSplit[25]!=null)? lineSplit[25]:null);
			this.setcodigoiva((lineSplit[26]!=null)? lineSplit[26]:null);
			this.setsiaplicadescfidel((lineSplit[27]!=null)? lineSplit[27]:null);
			this.setmontorealdescfidel((lineSplit[28]!=null)? new BigDecimal(lineSplit[28]):BigDecimal.ZERO);
			//INI OpenUp SBT Issue #4976 Campo nuevo PrecioUnitario Precio unitario del item (precio de lista que incluye IVA).
			this.setPrecioUnitarioConIva((lineSplit[29]!=null)? new BigDecimal(lineSplit[29]):BigDecimal.ZERO);
			//Ya no se deben de tener en cuenta
//			this.setsiesconvenio((lineSplit[29]!=null)? lineSplit[29]:null);
//			this.setnrolineaconvenio((lineSplit[30]!=null)? Integer.valueOf(lineSplit[30]):0);
//			this.setpuntosoferta((lineSplit[31]!=null)? new BigDecimal(lineSplit[31]):BigDecimal.ZERO);
//			this.setsiesobsequio((lineSplit[32]!=null)? lineSplit[32]:null);
//			this.setcantdescmanuales((lineSplit[33]!=null)? new BigDecimal(lineSplit[33]).intValue():BigDecimal.ZERO.intValue());
			//FIN OpenUp SBT Issue #4976
			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
			
			//OpenUp SBT 26/11/2015 Issue #4976 Se agrega la columna pedido de GV
			MProduct prod = MProduct.forValueAllClients(getCtx(), this.getcodigoarticulo(), null);
			if(null!=prod && prod.get_ID()>0){
				this.setM_Product_ID(prod.get_ID());
			}else{
				//OpenUp SBT 22/01/2016 Issue # Se agrega el id de producto ya sea por codArticulo o por UPC
				MProductUpc pUPC = MProductUpc.forUPC(getCtx(), this.getcodigoarticulooriginal(), null);
				if(null!=pUPC && pUPC.get_ID()>0)
					this.setM_Product_ID(pUPC.getM_Product_ID());
			}
						
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
		}
	}

	/**Se crea la linea para suplantar la liena de retiro que no es de tipo 1 y tiene valor contable negativo
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 10/9/2015
	 * @param lineTktVentaDev
	 * @param fchCabezal
	 * @param headerId
	 * @param posicionfila
	 */
	public void parseLineItemVtaOpenUp(MRTTicketLineItemReturn lineTktVentaDev,
			int headerId) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), "1", OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			
			this.setnumerodelinea(lineTktVentaDev.getnumerodelinea()); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(lineTktVentaDev.gettimestamplinea()); //3
			
			this.setcodigoarticulo(lineTktVentaDev.getcodigoartsubf());//4
			
			//21-09-2015 SBouissa  Se calcula la cantidad en negativo calculos GV
			//int cant = (lineTktVentaDev.getCantidad() * (-1)); 
			this.setCantidad(lineTktVentaDev.getCantidad().multiply(new BigDecimal("-1"))); //5
			this.setpreciounitario(lineTktVentaDev.getprecio()); //6
			this.setiva(lineTktVentaDev.getiva()); //7
			
			this.setpreciodescuento(BigDecimal.ZERO); //8
			this.setivadescuento(BigDecimal.ZERO);; //9
			this.setpreciodescuentocombo(BigDecimal.ZERO);; //10
			this.setivadescuentocombo(BigDecimal.ZERO);; //11
			this.setpreciodescuentomarca(BigDecimal.ZERO);; //12		
			
			this.setivadescuentomarca(BigDecimal.ZERO); //13
			this.setpreciodescuentototal(lineTktVentaDev.getprecio()); //14
			this.setivadescuentototal(lineTktVentaDev.getiva());//15
			this.setcantdescmanuales(BigDecimal.ZERO.intValue());//16
			
			this.setlineacancelada(lineTktVentaDev.getlineacancelada()); //17
			this.setmodoingreso(lineTktVentaDev.getmodoingreso());
			
			this.setcodigovendedor(lineTktVentaDev.getcodigovendedor()); //19
			this.settalle(null);
			this.setcolor(null);
			this.setmarca(null);
			this.setmodelo(null);
			this.setsiestandem(null);
			this.setcodigoarticulooriginal(lineTktVentaDev.getcodigoarticulooriginal());
			this.setcodigoiva(null);
			this.setsiaplicadescfidel(null);
			this.setmontorealdescfidel(BigDecimal.ZERO);
			this.setsiesconvenio(null);
			this.setnrolineaconvenio(0);
			this.setpuntosoferta(BigDecimal.ZERO);
			this.setsiesobsequio("0");
			this.setcantdescmanuales(0);
			this.setpositionfile(null);
			this.setUY_RT_Ticket_Header_ID(headerId);
			
			//OpenUp SBT 22/01/2016 Issue # Se agrega el id de producto ya sea por codArticulo o por UPC
			MProduct prod = MProduct.forValueAllClients(getCtx(), this.getcodigoarticulo(), null);
			if(null!=prod){
				this.setM_Product_ID(prod.get_ID());
			}else{
				MProductUpc pUPC = MProductUpc.forUPC(getCtx(), this.getcodigoarticulooriginal(), null);
				if(null!=pUPC)this.setM_Product_ID(pUPC.getM_Product_ID());
			}
			
			this.setUY_RT_Ticket_LineItemReturn_ID(lineTktVentaDev.get_ID());
		}
	}

	/**Obtengo todas las lineas de venta del ticket actual
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 18/9/2015
	 * @param codigoartsubf
	 * @param tnrolineaventa
	 * @param headerId
	 * @return
	 */
	public static MRTTicketLineItem getLineCancelItem(Properties ctx,String codigoartsubf,
			String nrolineaventa, int headerId, String trxName ) {
		String whereClause = X_UY_RT_Ticket_LineItem.COLUMNNAME_UY_RT_Ticket_Header_ID + "="+ headerId ;
		int count = 1;
		
		List<MRTTicketLineItem> lineas = new Query(ctx, I_UY_RT_Ticket_LineItem.Table_Name, whereClause, trxName)
		.setOrderBy("UY_RT_Ticket_LineItem.positionfile").list();
		
		if(null!=lineas){
			for(MRTTicketLineItem l:lineas){
				if(String.valueOf(count).equals(nrolineaventa)){//Se van contabilizando las lineas del ticket hasta que coincida con la linea de cancelacio
					if(l.getcodigoarticulo().equals(codigoartsubf)){//Se verifica que el codigo de articulo sea igual al de la linea cancelada
						return l;
					}
				}
				count ++;		
			}
		}
		
		
		return null;
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 18/9/2015
	 * @param lineaCancel
	 * @param cancelada
	 * @param headerId
	 */
	public void parseLineItemVtaOpenUpCancel(MRTTicketCancelItem lineaCancel,
			MRTTicketLineItem noCancelada, int headerId) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), "1", OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			
			this.setnumerodelinea(lineaCancel.getnumerodelinea()); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(lineaCancel.gettimestamplinea()); //3
			
			this.setcodigoarticulo(lineaCancel.getcodigoartsubf());//4
			//21-09-2015 SBouissa  Se calcula la cantidad en negativo calculos GV
			//int cant = (noCancelada.getCantidad() * (-1));			
			this.setCantidad(noCancelada.getCantidad().multiply(new BigDecimal("-1"))); //5
			this.setpreciounitario(lineaCancel.getImporte()); //6
			this.setiva(noCancelada.getiva().multiply(new BigDecimal("-1"))); //7
			
			this.setpreciodescuento(BigDecimal.ZERO); //8
			this.setivadescuento(BigDecimal.ZERO);; //9
			this.setpreciodescuentocombo(BigDecimal.ZERO);; //10
			this.setivadescuentocombo(BigDecimal.ZERO);; //11
			this.setpreciodescuentomarca(BigDecimal.ZERO);; //12		
			
			this.setivadescuentomarca(BigDecimal.ZERO); //13
			//Sumo el precio de la linea de venta con el iva de la mismsa
			BigDecimal totalNoCancelada = noCancelada.getpreciodescuentototal().add(noCancelada.getivadescuentototal());
			//Comparo si el total de la l�nea es igual al importe que viene de la l�nea cancelada
			if(totalNoCancelada.add(lineaCancel.getImporte()).compareTo(Env.ZERO)==0){
				//Seteo el iva seg�n la l�nea de venta (expresado en negativo)
				this.setivadescuentototal(noCancelada.getivadescuentototal().multiply(new BigDecimal("-1")));//pongo en nevativo el iva 
				//Al importe le debo restar el iva que viene de la l�ena de venta ya que el importe viene con iva
				this.setpreciodescuentototal(lineaCancel.getImporte().add(noCancelada.getivadescuentototal())); //descuento el iva 
			}else{
				throw new AdempiereException("Error al crear linea que contraresta cancelaci�n de item"+ noCancelada.getpositionfile());
			}
			//this.setpreciodescuentototal(lineaCancel.getImporte()); //14
			//this.setivadescuentototal(noCancelada.getivadescuentototal().multiply(new BigDecimal("-1")));//15  ???????????????????
			this.setcantdescmanuales(BigDecimal.ZERO.intValue());//16
			
			this.setlineacancelada(0); //17
			this.setmodoingreso(noCancelada.getmodoingreso()); 
			
			this.setcodigovendedor(lineaCancel.getcodigovendedor()); //19
			this.settalle(null);
			this.setcolor(null);
			this.setmarca(null);
			this.setmodelo(null);
			this.setsiestandem(null);
			this.setcodigoarticulooriginal(noCancelada.getcodigoarticulooriginal()); 
			this.setcodigoiva(null);
			this.setsiaplicadescfidel(null);
			this.setmontorealdescfidel(BigDecimal.ZERO);
			this.setsiesconvenio(null);
			this.setnrolineaconvenio(0);
			this.setpuntosoferta(BigDecimal.ZERO);
			this.setsiesobsequio("0");
			this.setcantdescmanuales(0);
			this.setpositionfile(null);
			this.setUY_RT_Ticket_Header_ID(headerId);

			this.setUY_RT_Ticket_CancelItem_ID(lineaCancel.get_ID());
			
			//OpenUp SBT 22/01/2016 Issue # Se agrega el id de producto ya sea por codArticulo o por UPC
			MProduct prod = MProduct.forValueAllClients(getCtx(), this.getcodigoarticulo(), null);
			if(null!=prod){
				this.setM_Product_ID(prod.get_ID());
			}else{
				MProductUpc pUPC = MProductUpc.forUPC(getCtx(), this.getcodigoarticulooriginal(), null);
				if(null!=pUPC)this.setM_Product_ID(pUPC.getM_Product_ID());
			}
		}
	}	
}

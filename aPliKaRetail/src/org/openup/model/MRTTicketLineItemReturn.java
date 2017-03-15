/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**OpenUp Ltda Issue#
 * @author SBT 10/9/2015
 *
 */
public class MRTTicketLineItemReturn extends X_UY_RT_Ticket_LineItemReturn {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_LineItemReturn_ID
	 * @param trxName
	 */
	public MRTTicketLineItemReturn(Properties ctx,
			int UY_RT_Ticket_LineItemReturn_ID, String trxName) {
		super(ctx, UY_RT_Ticket_LineItemReturn_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketLineItemReturn(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public void parseLineItemReturn(String[] lineSplit, String fchCabezal,
			int headerId,String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setcodigoartsubf((lineSplit[4]!=null)? lineSplit[4]:null);//4
			this.setCantidad((lineSplit[5]!=null)? new BigDecimal(lineSplit[5]):BigDecimal.ZERO); //5
			this.setprecio((lineSplit[6]!=null)? new BigDecimal(lineSplit[6]):BigDecimal.ZERO); //6

			this.setiva((lineSplit[7]!=null)? new BigDecimal(lineSplit[7]):BigDecimal.ZERO); //7
			this.setindicadorartsubf((lineSplit[8]!=null)? lineSplit[8]:null);//8
			this.setcodigosupervisora((lineSplit[9]!=null)? lineSplit[9]:null);//9
			this.setcodigoarticulooriginal((lineSplit[10]!=null)? lineSplit[10]:null);//10
			this.setmodoingreso((lineSplit[11]!=null)? lineSplit[11]:null);//11
			this.setcodigovendedor((lineSplit[12]!=null)? lineSplit[12]:null);//12 
			this.setlineacancelada((lineSplit[13]!=null)? Integer.valueOf(lineSplit[13]):0); //13
			
			this.setUY_RT_Ticket_Header_ID(headerId);
		
			this.setpositionfile(posicionfila);
			
			//OpenUp SBT 22/01/2016 Issue # Se agrega el id de producto ya sea por codArticulo o por UPC
			MProduct prod = MProduct.forValueAllClients(getCtx(), this.getcodigoartsubf(), null);
			if(null!=prod){
				this.setM_Product_ID(prod.get_ID());
			}else{
				MProductUpc pUPC = MProductUpc.forUPC(getCtx(), this.getcodigoarticulooriginal(), null);
				if(null!=pUPC)this.setM_Product_ID(pUPC.getM_Product_ID());
			}
			
			
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
		}
	}	
	
}

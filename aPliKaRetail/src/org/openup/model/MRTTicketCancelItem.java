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
 * @author SBT 17/9/2015
 *
 */
public class MRTTicketCancelItem extends X_UY_RT_Ticket_CancelItem {

	/**
	 * @param ctx
	 * @param UY_RT_Ticket_CancelItem_ID
	 * @param trxName
	 */
	public MRTTicketCancelItem(Properties ctx, int UY_RT_Ticket_CancelItem_ID,
			String trxName) {
		super(ctx, UY_RT_Ticket_CancelItem_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketCancelItem(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 17/9/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 * @param posicionfila
	 */
	public void parseLineCancelItem(String[] lineSplit, String fchCabezal,
			int headerId, String posicionfila) {
		MRTTicketType tipoTkt = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[2], OpenUpUtilsRT.isNotHeader, null);
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setUY_RT_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Convertir.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setcodigoartsubf((lineSplit[4]!=null)? lineSplit[4]:null);//4
			this.setnrolineaventa((lineSplit[5]!=null)? lineSplit[5]:null);//5
			this.setImporte((lineSplit[6]!=null)? new BigDecimal(lineSplit[6]):BigDecimal.ZERO);//6
			this.setindicadorartsubf((lineSplit[7]!=null)? lineSplit[7]:null);//7
			this.setnombresupervisora((lineSplit[8]!=null)? lineSplit[8]:null);//8
			if(lineSplit.length >9 ){
				this.setcodigovendedor((lineSplit[9]!=null)? lineSplit[9]:null);//9			
			}

			this.setpositionfile(posicionfila);
			this.setUY_RT_Ticket_Header_ID(headerId);
			
			//OpenUp SBT 22/01/2016 Issue # Se agrega el id de producto por codArticulo no se puede por upc porque no viene este dato
			MProduct prod = MProduct.forValueAllClients(getCtx(), this.getcodigoartsubf(), null);
			if(null!=prod) this.setM_Product_ID(prod.get_ID());
//			}else{
//				MProductUpc pUPC = MProductUpc.forUPC(getCtx(), this.getcodigoarticulooriginal(), null);
//				if(null!=pUPC)this.setM_Product_ID(pUPC.getM_Product_ID());
//			}
			
		}else{
			if(tipoTkt==null)throw new AdempiereException("Falta parametrizar tipo: "+lineSplit[2]);
		}
		
	}

}

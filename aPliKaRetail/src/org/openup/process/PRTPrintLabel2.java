/**
 * 
 */
package org.openup.process;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import javax.print.PrintService;

import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MProductUpc;

/**OpenUp Ltda Issue#
 * @Andrea Odriozola 2/7/2015
 *
 */
public class PRTPrintLabel2 extends SvrProcess{

	private int mAdOrgID = 0;
	private MProduct mProduct;
	
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("M_Product_ID")){
					int id = para[i].getParameterAsInt();
					this.mProduct = new MProduct(getCtx(), id, get_TrxName());
				}
			}
		}
		//Se obliga a seleccionar organización para la impresión de etiquetas ya que se debe imprimir por sucursal Issue #5989
		mAdOrgID  = Env.getAD_Org_ID(this.getCtx());
		if(mAdOrgID<=0)	throw new AdempiereException("Debe loguearse indicando una organización específica");
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		try {
			

			String raw = "CT~~CD,~CC^~CT~\n"
						+ "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR5,5~SD15^JUS^LRN^CI0^XZ\n"
						+ "^XA\n"
						+ "^MMT\n"
						+ "^PW480\n"
						+ "^LL0240\n"
						+ "^LS0\n"
						+ "^FT471,173^A0I,50,26^FH\\^FD{{producto}}^FS\n"
						+ "^FT471,101^A0I,49,36^FH\\^FD{{precio}}^FS\n"
						+ "^BY2,3,54^FT457,28^BCI,,Y,N\n"
						+ "^FD>:{{upc}}^FS\n"
						+ "^PQ1,0,1,Y^XZ";

			
			
			String printer = MSysConfig.getValue("UY_COVADONGA_LABEL_PRINTER", "ZDesigner GK420t", this.getAD_Client_ID());// "ZDesigner GK420t"; // This should match your printer name
			// from Step 2
			PrintService ps = PrintServiceMatcher.findPrinter(printer);
			if (ps == null) {
				throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
			}
			
			
			BigDecimal precioProd = Env.ZERO;
			
			MPriceList list = MPriceList.getDefault(getCtx(), true,142);//Pesos
			MPriceList listUSD = MPriceList.getDefault(getCtx(), true,100);//Dolares
			
			Timestamp date = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			MPriceListVersion priceListVersionPesos = list.getVersionVigente(date);
			MPriceListVersion priceListVersionDolares = listUSD.getVersionVigente(date);
			
			MProductPrice prodPricePesos = MProductPrice.forVersionProduct(getCtx(), priceListVersionPesos.get_ID(), mProduct.get_ID(),
					mAdOrgID,get_TrxName());
			MProductPrice prodPriceDolares  = MProductPrice.forVersionProduct(getCtx(), priceListVersionDolares.get_ID(), mProduct.get_ID(),
					mAdOrgID,get_TrxName());
			
			if (prodPricePesos != null) {
				precioProd = prodPricePesos.getPriceList();
			} else if (prodPriceDolares != null) {
				precioProd = prodPriceDolares.getPriceList();
			}
			
			MProductUpc upc = MProductUpc.forProduct(getCtx(), mProduct.get_ID(), get_TrxName());
			
			raw = raw.replace("{{producto}}", mProduct.getName());
			raw = raw.replace("{{precio}}", precioProd.setScale(2, RoundingMode.HALF_UP).toString());
			raw = raw.replace("{{upc}}", upc.getUPC());
			
			PrintRaw p = new PrintRaw(ps, raw);			
			p.print();
			
			

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		return "OK";
	}

}

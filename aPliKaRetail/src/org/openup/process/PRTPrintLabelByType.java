/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MCurrency;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MLabelPrint;
import org.openup.model.MLabelPrintLine;
import org.openup.model.MLabelPrintScan;
import org.openup.model.MProductUpc;

/**OpenUp Ltda Issue#
 * @author Nicolas 6/5/2016
 *
 */
public class PRTPrintLabelByType extends SvrProcess {

	MLabelPrint hdr = null;
	
	/**
	 * 
	 */
	public PRTPrintLabelByType() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		hdr = new MLabelPrint(getCtx(), this.getRecord_ID(), get_TrxName());

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		try{
					
			int adProcessID = this.getPrintProcessID();
				
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de impresion de etiquetas.");
			}
			
			this.processPrint(adProcessID);		
			
			return "OK";
			
		} catch(Exception e) {
			throw new AdempiereException(e.getMessage());

		} 
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Nicolas Sarlabos. 19/7/2016. #.
	 * @param adProcessID
	 */
	private void processPrint(int adProcessID) {

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			String sql = "select uy_labelprintline_id" +
					" from uy_labelprintline" +
					" where uy_labelprint_id = " + hdr.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				DecimalFormat f = new DecimalFormat("###,###,###,###,###.##");
				f.setDecimalSeparatorAlwaysShown(true); 
				f.setMinimumFractionDigits(2);
				
				MLabelPrintLine line = new MLabelPrintLine(getCtx(), rs.getInt("uy_labelprintline_id"), null);
				MLabelPrintScan scan = (MLabelPrintScan)line.getUY_LabelPrintScan();
				
				MCurrency currency = null;
				
				if(line.get_ValueAsInt("c_currency_id") > 0){
					
					currency = new MCurrency (getCtx(),line.get_ValueAsInt("c_currency_id"),null);					
					
				} else currency = new MCurrency (getCtx(),scan.get_ValueAsInt("c_currency_id"),null);				
				
				MProduct prod = (MProduct)line.getM_Product();	
				
				if(prod != null && prod.get_ID() > 0){
					
					String priceFinal = "", priceRound = "";
					
					MProductUpc upc = MProductUpc.forProduct(getCtx(), prod.get_ID(), null);					
				
					BigDecimal price = Env.ZERO;
					
					//OpenUp. Nicolas Sarlabos. 29/07/2016. #6500.
					//si se indico tomar el precio actual, o se toma el de la linea
					if(hdr.isPriceActual()){						
						
						price = prod.getSalePrice(currency.get_ID(), hdr.getAD_Org_ID());								
						
					} else price = line.getPrice();
					//Fin #6500.
					
					//OpenUp. Nicolas Sarlabos. 03/11/2016. #7753. Se comenta codigo, ahora siempre se redondea a 2 decimales.
					/*if(currency.get_ID() == 142) {						
						
						price = price.setScale(0, RoundingMode.HALF_UP);
											
					} else price = price.setScale(2, RoundingMode.HALF_UP);*/
					
					price = price.setScale(2, RoundingMode.HALF_UP);					
					//Fin #7753.	
					
					/*
					 * OpenUp Ltda. - Raul Capecce - #8418 - 17/01/2017
					 * Si el precio a imprimir es 0 => No imprimo estas etiquetas
					 */
					if (price != null && price.compareTo(Env.ZERO) != 0) {
					
						priceRound = (f.format(price));
						
						priceRound = priceRound
								.replace(".", ":")
								.replace(",", ".")
								.replace(":", ",");
						
						priceFinal = currency.getCurSymbol() + " " + priceRound;
						
						MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
						instance.saveEx();
						
						ProcessInfo pi = new ProcessInfo ("PrintLabel", adProcessID);
						pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
						
						MPInstancePara para = new MPInstancePara(instance, 10);
						para.setParameter("Name", prod.getName());
						para.saveEx();
						
						para = new MPInstancePara(instance, 20);
						
						
						// OpenUp Ltda - #6999 - Raul Capecce - Si no se cuenta con UPC se establece el value para codigo de barras
						if (upc != null) {
							para.setParameter("UPC", upc.getUPC());
						} else {
							para.setParameter("UPC", prod.getValue());
						}
						// FIN #6999
						
						para.saveEx();
						
						para = new MPInstancePara(instance, 30);
						para.setParameter("Qty", line.getQty());
						para.saveEx();
						
						para = new MPInstancePara(instance, 40);
						para.setParameter("Price", priceFinal);//OpenUp. Nicolas Sarlabos. 29/07/2016. #6500.
						para.saveEx();
						
						ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
						worker.start();
						
						/*
						 * OpenUp Ltda. - #7525 - Raul Capecce
						 * Hasta que no termina la primera tanda de etiquetas, no manda a imprimir la siguiente
						 */
						while(worker.isAlive()) {
							Thread.sleep(1000);
						}
						// OpenUp Ltda. - #7525 - Fin
						
						java.lang.Thread.sleep(1000);
					}
					// FIN - #8418
				}		
			}
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}

	/*private void printSelfAdhesive() throws Exception {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
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
			
			PrintService ps = PrintServiceMatcher.findPrinter(printer);

			if (ps == null) {
				throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
			}

			String sql = "select uy_labelprintline_id" +
						" from uy_labelprintline" +
						" where uy_labelprint_id = " + hdr.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				String printText=raw;
				
				MLabelPrintLine line = new MLabelPrintLine(getCtx(), rs.getInt("uy_labelprintline_id"), null);
				
				MProduct prod = (MProduct)line.getM_Product();
				
				MProductUpc upc = MProductUpc.forProduct(getCtx(), prod.get_ID(), null);

				if(upc != null){
					
					printText = printText.replace("{{producto}}", prod.getName());
					printText = printText.replace("{{precio}}", line.getPrice().setScale(2, RoundingMode.HALF_UP).toString());
					printText = printText.replace("{{upc}}", upc.getUPC());
					
					for (int i=0; i<line.getQty().intValueExact(); i++){
						
						PrintRaw p = new PrintRaw(ps, printText);			
						p.print();						
						
					}							
					
				}				
			}	
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
	}*/
	
	private int getPrintProcessID() {

		String sql = "", processVal = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
				
		try{
					
			if(hdr.getlabelprinttype().equalsIgnoreCase("GONDOLA")){
				
				processVal = "prtprintlabelgondola";				
				
			} else processVal = "prtprintlabeladhesive";			
			
			sql = " select ad_process_id " +
				  " from ad_process " +
				  " where lower(name) = '" + processVal + "'";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;

	}

}

/**
 * 
 */
package org.openup.process;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import javax.print.PrintService;

import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MLabelPrint;
import org.openup.model.MLabelPrintLine;
import org.openup.model.MLabelPrintScan;
import org.openup.model.MProductUpc;
import org.openup.util.OpenUpUtils;

/**OpenUp Ltda Issue#
 * @Andrea Odriozola 2/7/2015
 *
 */
public class PRTPrintLabel extends SvrProcess{

	private MLabelPrint model = null;
	private int headerID = 0;
	private int mADOrdID = 0;
	
	//OpenUp. Nicolas Sarlabos. 09/05/2016. #5936.
	@Override
	protected void prepare() {		
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_LabelPrint_ID")){
					this.headerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		mADOrdID = Env.getAD_Org_ID(getCtx());
		if(mADOrdID == 0) throw new AdempiereException("Debe seleccionar una organizacion para realizar esta operacion.");
		if(this.headerID > 0) this.model = new MLabelPrint(getCtx(), this.headerID, get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		try {
			

			String raw = "CT~~CD,~CC^~CT~\n"
					+ "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n"
					+ "^XA\n" + "^MMT\n" + "^PW480\n" + "^LL0272\n" + "^LS0\n"
					//+ "^FT0,60^ADN,72,10^FH\\^FD{PRODUCTO}^FS\n"
					+ "^FT0,60^A0N,72,24^FH\\^FD{PRODUCTO}^FS\n"
					+ "^FT20,165^A0N,110,80^FH\\^FD{PRECIO}^FS\n"
					+ "^FT180,222^A0N,46,24^FH\\^FD{CODIGO}^FS\n"
					+ "^PQ1,0,1,Y^XZ\n";

			
			
			String printer = "zebra"; // This should match your printer name
			// from Step 2
			PrintService ps = PrintServiceMatcher.findPrinter(printer);
			if (ps == null) {
				throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
			}

			
			// traemos las lineas por el id del cabezal
			List<MLabelPrintLine> lines = model.getLineas();

			// Por cada producto
			for (MLabelPrintLine line : lines) {				

				MProduct prod = (MProduct)line.getM_Product();
				
				String upc = "";
				MLabelPrintScan printScan = (MLabelPrintScan)line.getUY_LabelPrintScan();
				if ((printScan == null) || (printScan.get_ID() <= 0)){

					// Obtengo ultimo codigo de barra para este producto
					String sqlUPC = " select max(uy_productupc_id) from uy_productupc where m_product_id =" + prod.get_ID();
					int productUpcID = DB.getSQLValueEx(null, sqlUPC);
					if (productUpcID > 0){
						MProductUpc prodUPC = new MProductUpc(getCtx(), productUpcID, null);
						upc = prodUPC.getUPC(); 
					}
				}
				else{
					if (printScan.getScanText() != null){
						upc = printScan.getScanText().trim();	
					}
				}
				
				String printText=raw;
				
				String producto = prod.getName().toUpperCase();
				int espacios = 20;
				if (producto.length() < 39) {
					espacios = espacios - (producto.length() / 2);
				}
				for (int i = 0 ; i < espacios ; i++) {
					producto = " " + producto;
				}
				
				
				printText = printText.replace("{PRODUCTO}", producto);	
			    
				//SBT 07-04-2016 Issue #5733 (Ahora existe lista de venta en dolares)
				int currID = 0;
				if(null!=line.get_Value("C_Currency_ID")){
					currID = line.get_ValueAsInt("C_Currency_ID");
				}else{
					currID = OpenUpUtils.getSchemaCurrencyID(getCtx(), this.getAD_Client_ID(), null);
				}
				MCurrency currency = new MCurrency(getCtx(),currID,null);
				if (prod.getSalePrice(currID, mADOrdID)!=null){
					//Formato de separador de miles por punto
					  DecimalFormat df = new DecimalFormat(
						      "#,##0.00", 
						      new DecimalFormatSymbols(new Locale("pt", "UY")));
					  BigDecimal value = line.getPrice().setScale(2, RoundingMode.HALF_UP);
					  
					  //String precio ="$ "+ df.format(value.floatValue()).toString();
					  String precio =currency.getCurSymbol()+ df.format(value.floatValue()).toString();
					  espacios=11;
					  if (precio.length()<11){
						  espacios=espacios -(precio.length()/2);
					  }
					  for (int i = 0 ; i < espacios/2 ; i++) {
							precio = " " + precio;
						}
					  printText = printText.replace("{PRECIO}",precio);					  		
				//sino Falta el precio escribe 0
				}else printText = printText.replace("{PRECIO}", "$ 0,00");				
				printText = printText.replace("{CODIGO}", upc);
			
				// Segun cantidad de copias
				for (int i=0; i<line.getQty().intValueExact(); i++){
				PrintRaw p = new PrintRaw(ps, printText);			
				p.print();
				}
			
			}			

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		return "OK";
	}

}

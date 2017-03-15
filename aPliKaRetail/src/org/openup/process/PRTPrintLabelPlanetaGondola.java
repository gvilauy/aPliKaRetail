/**
 * 
 */
package org.openup.process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;

import javax.print.PrintException;
import javax.print.PrintService;

import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MProductUpc;
import org.openup.util.printlabels.PrintLabelPlanetaGondola;

/**OpenUp Ltda Issue#
 * @author OpenUp 15/7/2016
 *
 */
public class PRTPrintLabelPlanetaGondola extends SvrProcess{

	private String producto;
	private String precio;
	private String upc;
	private int quantity;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("Name")) {
					this.producto = para[i].getParameter().toString();
				} else if (name.equalsIgnoreCase("Price")) {
					this.precio = para[i].getParameter().toString();
				} else if (name.equalsIgnoreCase("UPC")) {
					this.upc = para[i].getParameter().toString();
				} else if (name.equalsIgnoreCase("Qty")) {
					this.quantity = ((BigDecimal)para[i].getParameter()).intValue();
				}
			}
		}
	}

	
	@Override
	protected String doIt() throws Exception {
		
		PrintLabelPlanetaGondola pl = new PrintLabelPlanetaGondola(this.producto, this.precio, this.upc, this.quantity, this.getAD_Client_ID());
		pl.print();
		return "OK";
	}

}

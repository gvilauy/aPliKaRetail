/**
 * 
 */
package org.openup.util.printlabels;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.print.PrintException;
import javax.print.PrintService;

import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MSysConfig;

/**OpenUp Ltda Issue#
 * @author OpenUp 19/7/2016
 *
 */
public class PrintLabelCovadongaGondola {

	private final char STX ='\u0002';
	
	private String producto;
	private String precio;
	private String upc;
	private int quantity;
	private int ad_client;
	
	public PrintLabelCovadongaGondola(String producto, String precio, String upc, int quantity, int ad_client) {
		this.producto = producto;
		this.precio = precio;
		this.upc = upc;
		this.ad_client = ad_client;
		this.quantity = quantity;
	}
	
	public void print() {
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
		
		
		
		raw = raw.replace("{PRODUCTO}", getStrCentered(this.producto, 36, ad_client))
				.replace("{PRECIO}", getStrCentered(this.precio, 11, ad_client))
				.replace("{CODIGO}", this.upc);
		
		PrintRaw p;
		try {
			p = new PrintRaw(ps, raw);
			for (int i = 0 ; i < this.quantity ; i++) p.print();
		} catch (UnsupportedEncodingException e) {
			throw new AdempiereException(e);
		} catch (IOException e) {
			throw new AdempiereException(e);
		} catch (InterruptedException e) {
			throw new AdempiereException(e);
		} catch (PrintException e) {
			throw new AdempiereException(e);
		}			
		
	}
	
	public static String getStrCentered(String str, int lineLength, int ad_client_id) {
		String ret = "";
		int chrCant = (lineLength - str.length()) / 2;
		
		if (str.length() > lineLength) return str.substring(0, str.length() - 1);
		
		for (int i=0 ; i<chrCant ; i++) {
			ret += "  ";
		}
		
		ret += str;
		
		return ret;
	}
}

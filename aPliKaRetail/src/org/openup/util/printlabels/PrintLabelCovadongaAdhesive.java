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
public class PrintLabelCovadongaAdhesive {

	private final char STX ='\u0002';
	
	private String producto;
	private String precio;
	private String upc;
	private int quantity;
	private int ad_client;
	
	public PrintLabelCovadongaAdhesive(String producto, String precio, String upc, int quantity, int ad_client) {
		this.producto = producto;
		this.precio = precio;
		this.upc = upc;
		this.ad_client = ad_client;
		this.quantity = quantity;
	}
	
	public void print() {
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
		
		raw = "CT~~CD,~CC^~CT~\n"
				+ "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR5,5~SD15^JUS^LRN^CI0^XZ\n"
				+ "^XA\n"
				+ "^MMT\n"
				+ "^PW480\n"
				+ "^LL0240\n"
				+ "^LS0\n"
				+ "^FT471,184^A0I,31,31^FH\\^FD{{producto}}^FS\n"
				+ "^FT471,115^A0I,49,48^FH\\^FD{{precio}}^FS\n"
				+ "^BY2,3,54^FT348,32^BCI,,Y,N\n"
				+ "^FD>:{{upc}}^FS\n"
				+ "^PQ1,0,1,Y^XZ";
	
		String printer = MSysConfig.getValue("UY_COVADONGA_LABEL_PRINTER", "Datamax-O'Neil E-4204B Mark III", ad_client);
		// from Step 2
		PrintService ps = PrintServiceMatcher.findPrinter(printer);
		if (ps == null) {
			throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
		}
		
		
		
		raw = raw.replace("{{producto}}", getStrCentered(this.producto, 35, ad_client))
				.replace("{{precio}}", getStrCentered(this.precio, 28, ad_client))
				.replace("{{upc}}", this.upc);
		
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
		chrCant = (int) (chrCant * 0.60);
		
		if (str.length() > lineLength) return str.substring(0, str.length() - 1);
		
		for (int i=0 ; i<chrCant ; i++) {
			ret += "  ";
		}
		
		ret += str;
		
		return ret;
	}
}

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
public class PrintLabelPlanetaGondola {

	private final char STX ='\u0002';
	
	private String producto;
	private String precio;
	private String upc;
	private int quantity;
	private int ad_client;
	
	public PrintLabelPlanetaGondola(String producto, String precio, String upc, int quantity, int ad_client) {
		this.producto = producto;
		this.precio = precio;
		this.upc = upc;
		this.ad_client = ad_client;
		this.quantity = quantity;
	}
	
	public void print() {
		String raw = STX + "L\n"
				+ "D11\n"
				+ "ySPM\n"
				+ "A2\n"
				+ "1911A1001000011{{producto1}}\n"
				+ "1911A1000860011{{producto2}}\n"
				+ "1911A2400490011{{precio}}\n"
				+ "1e4202300240036B{{upc}}\n"
				+ "1911A0600130078{{upc}}\n"
				+ "Q0001\n"
				+ "E\n";
		
		String printer = MSysConfig.getValue("UY_PLANETA_LABEL_PRINTER", "Datamax-O'Neil E-4204B Mark III", ad_client);
		// from Step 2
		PrintService ps = PrintServiceMatcher.findPrinter(printer);
		if (ps == null) {
			throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
		}
		

		int caracteresPorLinea = 25;
		String producto1 = "";
		String producto2 = "";
		String producto3 = "";
		
		if (this.producto.length() > caracteresPorLinea) {
			producto1 = this.producto.substring(0, caracteresPorLinea);
			if (this.producto.length() > caracteresPorLinea * 2) {
				producto2 = this.producto.substring(caracteresPorLinea, caracteresPorLinea * 2);
				producto3 = this.producto.substring(caracteresPorLinea * 2, this.producto.length());
			} else {
				producto2 = this.producto.substring(caracteresPorLinea, this.producto.length());
			}
		} else {
			producto1 = this.producto;
		}
		
		
		raw = raw.replace("{{producto1}}", getStrCentered(producto1, 26, ad_client))
				.replace("{{producto2}}", getStrCentered(producto2, 26, ad_client))
				.replace("{{producto3}}", getStrCentered(producto3, 26, ad_client))
				.replace("{{precio}}", getStrCentered(precio, 14, ad_client))
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
		
		if (str.length() > lineLength) return str.substring(0, str.length() - 1);
		
		for (int i=0 ; i<chrCant ; i++) {
			ret += "  ";
		}
		
		ret += str;
		
		return ret;
	}
}

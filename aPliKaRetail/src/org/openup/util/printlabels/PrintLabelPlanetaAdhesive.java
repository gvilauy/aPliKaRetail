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
public class PrintLabelPlanetaAdhesive {

	private final char STX ='\u0002';
	
	private String producto;
	private String precio;
	private String upc;
	private int quantity;
	private int ad_client;
	
	public PrintLabelPlanetaAdhesive(String producto, String precio, String upc, int quantity, int ad_client) {
		this.producto = producto;
		this.precio = precio;
		this.upc = upc;
		this.ad_client = ad_client;
		this.quantity = quantity;
	}
	
	public void print() {
		String raw1 = STX + "L\n"
				+ "D11\n"
				+ "PC\n"
				+ "pC\n"
				+ "SC\n"
				+ "ySPM\n"
				+ "A2\n"
				+ "1911A0800760012{{producto1}}\n"
				+ "1911A0800650012{{producto2}}\n"
				+ "1911A0800530012{{producto3}}\n"
				+ "1911A0800370012{{precio}}\n"
				+ "1e4202100140029B{{upc}}\n"
				+ "1911A0600020071{{upc}}\n"
				+ "Q0001\n"
				+ "E\n";
		
		String raw2 = STX + "L\n"
				+ "D11\n"
				+ "PC\n"
				+ "pC\n"
				+ "SC\n"
				+ "ySPM\n"
				+ "A2\n"
				+ "1911A0800760012{{producto1}}\n"
				+ "1911A0800650012{{producto2}}\n"
				+ "1911A0800530012{{producto3}}\n"
				+ "1911A0800370012{{precio}}\n"
				+ "1e4202100140029B{{upc}}\n"
				+ "1911A0600020071{{upc}}\n"
				+ "1911A0800760175{{producto1}}\n"
				+ "1911A0800650175{{producto2}}\n"
				+ "1911A0800530175{{producto3}}\n"
				+ "1911A0800370175{{precio}}\n"
				+ "1e4202100140192B{{upc}}\n"
				+ "1911A0600020234{{upc}}\n"
				+ "Q0001\n"
				+ "E\n";
		
		String printer = MSysConfig.getValue("UY_PLANETA_LABEL_PRINTER", "Datamax-O'Neil E-4204B Mark III", ad_client);
		// from Step 2
		PrintService ps = PrintServiceMatcher.findPrinter(printer);
		if (ps == null) {
			throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
		}
		
		int caracteresPorLinea = 24;
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
		
		raw1 = raw1.replace("{{producto1}}", producto1)
				.replace("{{producto2}}", producto2)
				.replace("{{producto3}}", producto3)
				.replace("{{precio}}", this.precio)
				.replace("{{upc}}", this.upc);
		
		raw2 = raw2.replace("{{producto1}}", producto1)
				.replace("{{producto2}}", producto2)
				.replace("{{producto3}}", producto3)
				.replace("{{precio}}", this.precio)
				.replace("{{upc}}", this.upc);
		
		PrintRaw p;
		try {
			p = new PrintRaw(ps, raw2);
			for (int i = 0 ; i < this.quantity / 2 ; i++) p.print();
			if (this.quantity % 2 != 0) {
				p = new PrintRaw(ps, raw1);
				p.print();
			}
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
}

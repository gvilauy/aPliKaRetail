package org.openup.cargamasiva;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.compiere.model.MProduct;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class PRTCargaTandemMasiva extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		
		HashMap<String, String> tandemProducts = new HashMap<String, String>();
		
//		tandemProducts.put("10674", "4787");
//		tandemProducts.put("10683", "4776");
//		tandemProducts.put("10689", "4776");
//		tandemProducts.put("10690", "14130");
//		tandemProducts.put("10698", "4778");
//		tandemProducts.put("10708", "4780");
//		tandemProducts.put("10725", "4785");
//		tandemProducts.put("10730", "4782");
//		tandemProducts.put("10735", "4782");
//		tandemProducts.put("10745", "14093");
//		tandemProducts.put("13056", "14099");
//		tandemProducts.put("13058", "14099");
//		tandemProducts.put("13063", "14099");
//		tandemProducts.put("13204", "4788");
//		tandemProducts.put("13208", "4791");
//		tandemProducts.put("13210", "4791");
//		tandemProducts.put("13211", "4790");
//		tandemProducts.put("13212", "4791");
//		tandemProducts.put("13213", "4790");
//		tandemProducts.put("13214", "4791");
//		tandemProducts.put("13216", "4788");
//		tandemProducts.put("13218", "4791");
//		tandemProducts.put("13224", "4790");
//		tandemProducts.put("13226", "4791");
//		tandemProducts.put("13229", "4790");
//		tandemProducts.put("13231", "4790");
//		tandemProducts.put("13232", "4790");
//		tandemProducts.put("13238", "4790");
//		tandemProducts.put("2468", "4781");
//		tandemProducts.put("2475", "4781");
//		tandemProducts.put("2477", "4781");
//		tandemProducts.put("2479", "4781");
//		tandemProducts.put("2481", "4784");
//		tandemProducts.put("2485", "4784");
//		tandemProducts.put("2488", "14132");
//		tandemProducts.put("2492", "4793");
//		tandemProducts.put("2613", "14130");
//		tandemProducts.put("30555", "4784");
//		tandemProducts.put("31725", "4784");
//		tandemProducts.put("33569", "14130");
//		tandemProducts.put("36465", "35804");
//		tandemProducts.put("38678", "4784");
//		tandemProducts.put("50463", "4781");
//		tandemProducts.put("8092", "4784");
//		tandemProducts.put("10684", "4775");
//		tandemProducts.put("10688", "4775");
//		tandemProducts.put("10695", "14130");
//		tandemProducts.put("10696", "18173");
//		tandemProducts.put("14062", "14066");
//		tandemProducts.put("14064", "14063");
//		tandemProducts.put("14091", "4776");
//		tandemProducts.put("14183", "14182");
//		tandemProducts.put("14266", "4786");
//		tandemProducts.put("14274", "1846");
//		tandemProducts.put("14563", "4784");
//		tandemProducts.put("1848", "4773");
//		tandemProducts.put("2480", "4781");
//		tandemProducts.put("2482", "4784");
//		tandemProducts.put("25915", "18173");
//		tandemProducts.put("25968", "4781");
//		tandemProducts.put("2609", "4774");
//		tandemProducts.put("26769", "16936");
//		tandemProducts.put("26770", "17321");
//		tandemProducts.put("33875", "4785");
//		tandemProducts.put("51482", "1845");
		
		
		
		// Carga tandem 2015 08 13
		tandemProducts.put("2489", "23913");
		tandemProducts.put("2493", "4793");
		tandemProducts.put("5070", "14098");
		tandemProducts.put("9002", "23913");
		tandemProducts.put("9060", "4777");
		tandemProducts.put("10746", "4775");
		tandemProducts.put("12938", "4789");
		tandemProducts.put("12983", "14098");
		tandemProducts.put("12985", "14098");
		tandemProducts.put("12988", "14098");
		tandemProducts.put("12989", "14098");
		tandemProducts.put("12991", "14098");
		tandemProducts.put("13009", "14098");
		tandemProducts.put("13010", "14098");
		tandemProducts.put("13017", "14098");
		tandemProducts.put("13034", "14096");
		tandemProducts.put("13036", "14096");
		tandemProducts.put("13038", "14096");
		tandemProducts.put("13043", "14096");
		tandemProducts.put("13047", "14098");
		tandemProducts.put("13051", "14098");
		tandemProducts.put("13052", "14096");
		tandemProducts.put("13064", "14098");
		tandemProducts.put("13078", "14098");
		tandemProducts.put("13083", "14098");
		tandemProducts.put("13085", "14098");
		tandemProducts.put("13086", "4789");
		tandemProducts.put("13159", "14098");
		tandemProducts.put("13162", "14098");
		tandemProducts.put("13163", "14098");
		tandemProducts.put("13174", "14098");
		tandemProducts.put("13176", "14098");
		tandemProducts.put("13177", "14098");
		tandemProducts.put("13203", "4789");
		tandemProducts.put("13205", "4788");
		tandemProducts.put("13206", "4788");
		tandemProducts.put("13207", "4788");
		tandemProducts.put("13209", "14096");
		tandemProducts.put("13210", "4791");
		tandemProducts.put("13215", "4788");
		tandemProducts.put("13217", "4788");
		tandemProducts.put("13221", "4790");
		tandemProducts.put("13225", "4790");
		tandemProducts.put("13227", "4790");
		tandemProducts.put("13230", "4790");
		tandemProducts.put("14065", "14066");
		tandemProducts.put("14095", "14096");
		tandemProducts.put("14100", "14096");
		tandemProducts.put("14301", "14098");
		tandemProducts.put("14560", "23913");
		tandemProducts.put("26787", "14098");
		tandemProducts.put("27119", "4783");
		tandemProducts.put("27476", "14098");
		tandemProducts.put("27756", "14556");
		tandemProducts.put("28203", "4792");
		tandemProducts.put("29785", "14186");
		tandemProducts.put("29786", "14186");
		tandemProducts.put("29787", "14098");
		tandemProducts.put("35804", "4784");
		tandemProducts.put("36871", "14556");
		tandemProducts.put("38071", "14132");
		tandemProducts.put("50098", "4784");

		
		
		
		
		
		
		int actualizados = 0;
		
		boolean valueProdCorrecto = false;
		boolean valueTandCorrecto = false;
		String strLineaRet = "";
		
		Iterator it = tandemProducts.keySet().iterator();
		while (it.hasNext()) {
			String valueOrigen = (String) it.next(); 
			String valueTandem = tandemProducts.get(valueOrigen);
			
			valueProdCorrecto = true;
			valueTandCorrecto = true;
			strLineaRet = "";
			
			if (!existIdByValue_M_Product(valueOrigen)) {
				valueProdCorrecto = false;
				strLineaRet += "\tProducto " + valueOrigen + " no existe";
			}
			
			if (!existIdByValue_M_Product(valueTandem)) {
				valueTandCorrecto = false;
				strLineaRet += "\tTandem " + valueTandem + " no existe";
			}
			
			
			if (valueProdCorrecto && valueTandCorrecto) {
				MProduct mProduct = new MProduct(getCtx(), getIdByValue_M_Product(valueOrigen), get_TrxName());
				mProduct.set_ValueOfColumn("M_Product_Tandem_ID", getIdByValue_M_Product(valueTandem));
				mProduct.saveEx();
				actualizados++;
				strLineaRet += "Producto " + valueOrigen + " asignado con tandem " + valueTandem;
			}
			
			System.out.println(strLineaRet);
			
		}
		
		
		return "Tandem Actualizados: " + actualizados;
	}
	
	
	public boolean existIdByValue_M_Product(String value) {
		String sql = "SELECT COUNT(M_Product_ID) FROM M_Product WHERE value = '" + value + "'";	
		return (((int) DB.getSQLValueEx(get_TrxName(), sql)) > 0) ? true : false ;
	}
	
	public int getIdByValue_M_Product(String value) {	
		String sql = "SELECT M_Product_ID FROM M_Product WHERE value = '" + value + "'";	
		return DB.getSQLValueEx(get_TrxName(), sql);
	}
	
	
	

}

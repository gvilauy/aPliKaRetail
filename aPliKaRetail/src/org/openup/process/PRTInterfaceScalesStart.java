/**
 * 
 */
package org.openup.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOM;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MFamilia;
import org.openup.model.MProductGroup;
import org.openup.model.MSubFamilia;
import org.openup.util.OpenUpUtils;

/**OpenUp Ltda Issue#
 * @author sevans Jan 29, 2016
 *
 */
public class PRTInterfaceScalesStart extends SvrProcess {
	private boolean onWindows;

	private String fchToday;
	//private	List<MRTInterfaceScales> scalescodes = null; // datos de los códigos de barra de los productos a procesar
	private File fBatch = null;
	private File fBatchTarget = null;	
	
	private static final String SEPARATOR_L = ":";
	private static final String NAME_BATCH = "MantSistBalanza.txt";
	private static final String TIENDA = "0000";
	private static final String SECCION = "0000";
	private static final String FLIA = "001";
	private static final String SUBFLIA = "001";			
	private static final String TIPOIVA = "0";
	private static final String CODRAPIDO = "000";
	//private static final String CODPLU = "1";
	private static final String TAGFORMAT = "21";
	private static final String DEFAULTACTION = "M";
	
	//private static final int ATTRID = 1000010;
	private static final int DESCLENGTH = 30;
	private static final int CODLENGTH = 6;
	private static final int DUELENGTH = 3;
	private static final int PRICELENGTH = 10;
	
	/**
	 *Constructor 
	 */
	public PRTInterfaceScalesStart() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		onWindows = true;
		try{
			String a = System.getProperty("os.name" );
			if(a.contains("Linux")){
				onWindows = false;
				System.out.println(System.getProperty("os.name" ));
			} 			

		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new AdempiereException(e.getMessage());
		}

	}

	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 29/1/2016
	 * @return Directorio origen 
	 */
	private String getRutaOrigen() {
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_SCALES_HISTORY_MANT",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_SCALES_HISTORY_MANT",0); //Directorio origen prametrizado para linux
		}
		return origen;		

	}		

	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/1/2016
	 * @return Directorio origen 
	 */
	private String getRutaDestino() {
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_SCALES_HISTORY_MANT_DEST",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_SCALES_HISTORY_MANT_DEST",0); //Directorio origen prametrizado para linux
		}
		return origen;		

	}			
	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/1/2016
	 * @return
	 */
	private File createBatchFile(boolean isOrigin) {
		File fb=null;
		if(isOrigin){
			fb = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceScalesStart.NAME_BATCH);
		}else{
			fb = new File(getRutaDestino()+File.separator+PRTInterfaceScalesStart.NAME_BATCH);
		}
		return fb;
	}		
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */

	@Override
	protected String doIt() throws Exception {
		PreparedStatement pstmt = null;  
		ResultSet prods = null;		
		int count = 0; int countError = 0;
//		String sql = "select pu.m_product_id, pu.upc, p.name from m_product p, m_productattribute pa, 
//		uy_productupc pu where p.m_product_id = pa.m_product_id and p.m_product_id = pu.m_product_id 
//		and pa.uy_prodattribute_id = "+ 
//				ATTRID +" and pa.isselected = 'Y' and p.isActive = 'Y' order by pu.created;";
		
		String sql = "SELECT p.m_product_id, p.value, p.name FROM m_product p "
				+ " WHERE p.isactive = 'Y' AND UsaBalanza = 'Y' ORDER BY p.Value ";
		
		// Issue #6319 SBT 04/07/2016
//		String sql = " SELECT pp.m_product_id, pp.value, pp.name FROM m_product pp where pp.M_Product_ID IN (select distinct(a.M_Product_ID) from M_ProductPrice a"
//					+ "	 join M_PriceList_Version b on (a.M_PriceList_Version_ID = b.M_PriceList_Version_ID)"
//					+ "	 join M_PriceList c on (c.M_PriceList_ID = b.M_PriceList_ID)"
//					+ "	 join M_Product d on (a.M_Product_ID = d.M_Product_ID)"
//					+ "	 where c.isactive = 'Y' AND c.issopricelist = 'Y' and c.AD_Org_ID > 0"
//					+ "	  and c.c_currency_ID = 142 and b.isactive = 'Y'"
//					+ "	  and d.isactive = 'Y' AND d.UsaBalanza = 'Y')";
		
		pstmt = DB.prepareStatement(sql, get_TrxName());
		try {
			prods = pstmt.executeQuery();
			String[] hra = (new Timestamp (System.currentTimeMillis()).toString().split(SEPARATOR_L));
			String fecha =hra[0].replace("-", "").replace(" ", "_")+hra[1];
			fchToday = fecha; //cargo la fecha y el path para generar el archivo de historico		
			fBatch = createBatchFile(true);
			
			
				while(prods.next()){
					//Obtengo las sucursales disponibles ya que tengo que crear el prod para cada sucursal
					//OpenUp SBT 16/05/2016 Issue # (Multisucursal )
					int[] sucursales = MOrg.getAllIDs(I_AD_Org.Table_Name, " IsActive ='Y' AND AD_Org_Id!=0 ", null); 
					for(int i = 0; i<sucursales.length;i++){
						String UPC = prods.getString("value");
						int prodID = prods.getInt("m_product_id");
						String line = createLineStart(prodID, UPC, sucursales[i]);
						if(line != null){					
							writeFile(line, fBatch, true);
							DB.executeUpdate("UPDATE uy_rt_interfacescales set readingdate = now() "
									+ " where m_product_id = "+prodID+" AND AD_OrgAux_ID = "+sucursales[i],
									get_TrxName());
							count++;					
						}else{
							countError++;
						}
					}		
				}
			
			
			prods.close();
			fBatchTarget = createBatchFile(false);
			copyFile(fBatch, fBatchTarget);	
			
			String datos = "Se procesaron "+count+" productos correctamente - ";
			if(countError>0){
				datos = datos + " No se procesaron "+countError+" productos";
			}
			return datos;
			//return "Proceso ejecutado exitosamente";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR al cargar archivos";
		}			
		
	}
	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 1/2/2016
	 * Se genera una línea para escribir en el archivo de mantenimiento al inicio, o sea no ingresada a la tabla de scales
	 * @return linea a escribir
	 */	
	private String createLineStart(int prodID, String codUPC, int mAdOrgAuxID){
		MProduct prod = new MProduct(getCtx(), prodID, get_TrxName());
		MUOM uom = new MUOM(getCtx(), prod.getC_UOM_ID(), get_TrxName());
		if(uom == null || uom.get_ID() == 0)
			return null;		
		String codProdFormat = String.format("%%0%dd", CODLENGTH);
		String codProd = "";
		String result = "";

		String tienda = TIENDA; // Por ahora siempre tiene ese valor
		String seccion = SECCION; // Valor por defecto para covadonga
		String flia = FLIA; // Valor por defecto para covadonga
		String subflia = SUBFLIA; // Valor por defecto para covadonga
		String codRapido = CODRAPIDO; // Valor por defecto
		String tipoIVA = TIPOIVA;
		String venc = "";
		
		
		//OpenUp. SBT 234/05/2016 Issue #5953 Se implementa para que funcione con planeta
		//Si es planeta -->se debe setear tienda = local , seccion = rubro, familia y subfamilia

		//Obtengo el proveedor
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", this.getAD_Client_ID());
		if (provider.equalsIgnoreCase("Planeta")) {
			seccion = "0000"; flia="0000"; subflia = "0000"; // Valores por defecto para planeta

			MOrg org = new MOrg(getCtx(),mAdOrgAuxID,null); 
			if(null!=org && org.get_ID()>0){
				tienda = org.getValue();
			}else{
				System.out.println("Se tiene que indicar una sucursal v�lida");
				return null;
			}
			
			if(null!=prod.get_Value("UY_ProductGroup_ID")){
				if(prod.get_ValueAsInt("UY_ProductGroup_ID")>0){
					MProductGroup pg = new MProductGroup(getCtx(),prod.get_ValueAsInt("UY_ProductGroup_ID"),null);
					if(null!=pg && pg.get_ID()>0){
						seccion = pg.get_ValueAsString("POSCode");
					}
				}
			}
			
			if(0<prod.getUY_Familia_ID()){
				MFamilia f = new MFamilia(getCtx(),prod.getUY_Familia_ID(),null);
				if(null!=f && f.get_ID()>0){
					flia = f.get_ValueAsString("POSCode");
					
					if(0<prod.getUY_SubFamilia_ID()){
						MSubFamilia sf = new MSubFamilia(getCtx(),prod.getUY_SubFamilia_ID(),null);
						if(null!=sf && sf.get_ID()>0){
							subflia = sf.get_ValueAsString("POSCode");
						}
					}
				}
			}
			
			if(null!=prod.get_Value("CodigoBalanza")){
				//Codigo r�pido entre 001 al 138
				codRapido = prod.get_ValueAsString("CodigoBalanza");
				//Se controla si el codigo es menor a tres digitos ya que hay que completarlo
				if(codRapido.length()>0 && codRapido.length()<3){
					if(codRapido.length()==2){
						codRapido = "0"+codRapido;
					}else if(codRapido.length()==1){
						codRapido = "00"+codRapido;
					}
				}
				
			}
			
		}// Fin si es planeta Issue #5953
					
		Timestamp today_aux = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), 
				TimeUtil.TRUNC_DAY);
						
		
		//SBT 15/09/2016 Issue #7029 calculo los d�as que faltan para el vencimiento
		if(prod.get_Value("Expiration_Days")!=null){
			venc = prod.get_Value("Expiration_Days").toString();
			if(venc.length()<2){
				venc = "00"+venc;
			}else if(venc.length()<3){
				venc = "0"+venc;
			}
			/*if(venc.length()<3){
				if(venc.length()<2){
					venc = "00"+venc;
				}
				venc = "0"+venc;
			}*/	
		}else{// En caso de que no haya una fecha de caducidad se setea en "000"
			venc = "000";
		}
		
		//codProd = String.format(codProdFormat, Integer.parseInt(codUPC)); SBT 26-02-2016 Se cambia por lo hablado con RB el 25/02/2016
		codProd = String.format(codProdFormat, Integer.parseInt(prod.getValue()));
		if(codProd.length() > CODLENGTH){
			System.out.println("El producto tiene codigo tiene mas de 6 caracteres ("+codUPC+")");
			return null;
		}

		String desc = String.format("%1$-30s", prod.getName());
		if (desc.length() > DESCLENGTH){
			desc = desc.substring(0, DESCLENGTH);
		}
		
		String priceFormat = String.format("%%0%dd", PRICELENGTH);	
		
		//SBT 07-04-2016 Issue #5733 (Se agrega lista de venta en USD)
		int currID = OpenUpUtils.getSchemaCurrencyID(getCtx(), this.getAD_Client_ID(), null);
		//FIN SBT
		if(prod.getSalePrice(currID,mAdOrgAuxID).compareTo(Env.ZERO)==0){
			if(142 == currID){
				currID=100;
			}else{
				currID = 142;
			}
			if(prod.getSalePrice(currID,mAdOrgAuxID).compareTo(Env.ZERO)==0){
				System.out.println("El producto "+codUPC+" no esta en la lista de precio de venta vigente");
				return null;
			}
		}
		String price = prod.getSalePrice(currID,mAdOrgAuxID).setScale(2, BigDecimal.ROUND_UP).toString();
		price = price.replace(".", "");
		price = String.format(priceFormat, Integer.parseInt(price));
		if(price.length() > PRICELENGTH)
			return null;
		
		String action = DEFAULTACTION;//Al correr maestro de balanza la acci�n siempre es M (Insertar/Modificar)
		String codPLU = "";
		if (uom.getUOMSymbol().equalsIgnoreCase("KG")){
			codPLU = "1";
		}else{
			codPLU = "2";
		}
		
		String tagFormat = TAGFORMAT;
		//Formo la linea, le pongo salto de linea de Windows para que no haya problemas en el software que levanta el archivo
		result = tienda + seccion + codProd + flia + subflia + desc + price + tipoIVA + action + codRapido + codPLU + tagFormat + venc + "\r\n";
		return result;
	}		

	/**
	 * OpenUp Ltda Issue# 5319
	 * @author Santiago Evans 1/2/2016
	 * @param lineIn
	 * @param fBatch
	 */
	private void writeFile(String lineIn, File fBatch, Boolean continuo) {
		BufferedWriter bw=null;
		File f = fBatch;
		//Escritura
		if(null!=f){
			try{
				//FileWriter w = new FileWriter(f,continuo); 
				//SBT 19/09/2016 Issue #7051 se agrega codificacion ya que por defecto crea con ISO-8859-1
			//	MClient clte = new MClient(getCtx(),this.getAD_Client_ID(),get_TrxName());
				//if(clte.getName().equalsIgnoreCase("planeta")){
//					org.zkoss.io.FileWriter wp = new org.zkoss.io.FileWriter(f, "ISO-8859-15", continuo);
//					bw = new BufferedWriter(wp);
				//}else{
					FileWriter w = new FileWriter(f,continuo);
					bw = new BufferedWriter(w);
				//}
				bw.append(lineIn);
			}catch(IOException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null!=bw) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	}	

	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/01/2016
	 * @param source
	 * @param dest
	 */
	@SuppressWarnings("resource")
	private void copyFile(File sourceFile, File destFile)
			throws IOException {
			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			FileChannel origen = null;
			FileChannel destino = null;
			try {
				origen = new FileInputStream(sourceFile).getChannel();
				destino = new FileOutputStream(destFile).getChannel();

				long count = 0;
				long size = origen.size();
				while ((count += destino.transferFrom(origen, count, size - count)) < size)
					;
			} finally {
				if (origen != null) {
					origen.close();
				}
				if (destino != null) {
					destino.close();
				}
			}
	}	
	
}

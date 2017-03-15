/**
 * 
 */
package org.openup.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCountry;
import org.compiere.model.MPriceList;
import org.compiere.model.MSysConfig;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.jboss.util.file.Files;
import org.openup.model.MDepartamentos;
import org.openup.model.MLocalidades;
import org.openup.model.MRTInterfaceBP;
import org.openup.model.MRTInterfaceProd;

/**Proceso para crear archivo para carga maestro (solo contiene Inserts) 
 * OpenUp Ltda Issue#4090 Mejora para la puesta en prod del nuevo sistema de Sisteco (Nov 2015)
 * @author SBT 19/11/2015
 *
 */
public class PRTInterfaceBPProdCliMaster extends SvrProcess{

	/**
	 * 
	 */
	public PRTInterfaceBPProdCliMaster() {
	}

	private static final String INSERTAR = "I";
		
	//Tablas a impactar en sisteco 
	private static final String A  = "ARTICULOS";
	private static final String AE = "ARTICULOS_EQUIVALENTES";
	private static final String T  = "TANDEM";
	private static final String C  = "CLIENTES";;
	
	private static final String SEPARATOR_L = ":";//caracter separador de las lineas de los archivos a escribir

	private File fCountBatch = null;
	//Variable para identificar el sistema que esta corriendo el proceso
	private Boolean onWindows = true;
	//VAriables para los nombres de los archivos
	private String batchAll = "bo_batch.all";
	private String batchAfl = "bo_batch.afl";
	
	private File fBatchError = null; //Archivo nuevo para guardar lineas con errores que no se agregan al archivo enviado a sisteco
	private static final String NAME_BATCH_ERROR = "bo_batchAllError.txt";
	
	@Override
	protected void prepare() {
		onWindows = true;
		try{
			String a = System.getProperty("os.name" );
			if(a.contains("Linux")){
				onWindows = false;
				System.out.println(System.getProperty("os.name" ));
			} 			
			if(!Files.delete(new File(getRutaOrigen()+File.separator+batchAll))){
				System.out.println("No hay archivos para borrar");
			}else{
				Files.delete(new File(getRutaOrigen()+File.separator+batchAfl));
				System.out.println("Se borraron los archivos");
			}
			
			//crear archivo contador batch
			fCountBatch = creatCountBattAllFile();
			
			//23/11/2015
			fBatchError = createBatchErrorFile();

		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new AdempiereException(e.getMessage());
		}
		

	}	

	@Override
	protected String doIt() throws Exception {			
		generateMasterFile();
		return "OK!";
	}

	/**
	 * Generar archivo maestro con articulos, tandem, articulos externos y clientes cc
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 9/10/2015
	 */
	private void generateMasterFile(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {

			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			// Obtengo lista de precios predeterminada de ventas
			MPriceList list = MPriceList.getDefault(getCtx(), true,142);//Pesos
			//OpenUP SBT 07-04-2016 Se agrega lista de precio en dolares en la operativa
			MPriceList listUSD = MPriceList.getDefault(getCtx(), true,100);//Dolares

			if ((list == null) || (list.get_ID() <= 0)){
				throw new AdempiereException("No se pudo obtener Lista de Precios de Venta Predeterminada");
			}

			// Obtengo ultima version activa segun fecha de vigencia para lista pesos
			sql = " select max(m_pricelist_version_id) " +
			      " from m_pricelist_version" +
				  " where m_pricelist_id = " + list.get_ID() +
				  " and isactive = 'Y'" +
				  " and validfrom <= '" + today + "'";
			
			int versionID = DB.getSQLValueEx(get_TrxName(), sql);
			
			// Obtengo ultima version activa segun fecha de vigencia para lista dolares
			sql = " select max(m_pricelist_version_id) " +
			      " from m_pricelist_version" +
				  " where m_pricelist_id = " + listUSD.get_ID() +
				  " and isactive = 'Y'" +
				  " and validfrom <= '" + today + "'";
			
			int versionIDUSD= DB.getSQLValueEx(get_TrxName(), sql);
			
			if (versionID <= 0){
				throw new AdempiereException("No se pudo obtener Version Vigente de Lista de Precios de Venta Predeterminada");
			}
			//Para openUP
			File fileMaster = new File(getRutaOrigen()+File.separator+batchAll);
			
			sql = " select prod.m_product_id, prod.value, prod.name, coalesce(taxc.commoditycode, '0') as commoditycode, "
				+ " coalesce(pp.pricelist, 0)::numeric(14,2) as pricelist,"
				+ " prod.versionno as attrs, prod.UnitsPerPack, uni.Name as uniMedida,"
				+ " CASE WHEN pp.m_pricelist_version_id="+versionIDUSD+" THEN 2 ELSE 1 END AS monedaLista "
				+ " from m_product prod "
				+ " left outer join m_productprice pp "
				+ " on (prod.m_product_id = pp.m_product_id and (pp.m_pricelist_version_id IN (" + versionID +","+versionIDUSD+ ")))" 				
				//+ " on (prod.m_product_id = pp.m_product_id and pp.m_pricelist_version_id =" + versionID + ") " 				
				+ " left outer join c_taxcategory taxc on prod.c_taxcategory_id = taxc.c_taxcategory_id "
				+ " left outer join c_uom uni on (prod.C_UOM_ID = uni.C_UOM_ID)"
				+ " where prod.isactive = 'Y' "
				+ " and prod.issold = 'Y' "
				+ " and prod.value != 'NO DEFINIDO' ";
				
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery();

			rs.last();
			rs.beforeFirst();
			System.out.println("LOG-Inicio carga ARTICULOS");

			while (rs.next()) {		
				//System.out.println("Procesando linea " + rowCount++ + " de " + totalRowCount + " - " + rs.getInt("m_product_id"));
				StringBuilder lineToPrint = null; //
				String codigo = rs.getString("value").trim();//1
				String desc = rs.getString("name").trim().replace(SEPARATOR_L, "_");//2
				String codEntorno = "0";//3 por ahora 0
				String codSubFamilia = "0";//4 por ahora 0
				 
				String moneda = rs.getString("monedaLista");//OpenUp SBT 07-04-2016 Ahora existe lista de precio dolares
				
				//String moneda = "1";//5 --por defecto 1 pesos codigo sisteco --				
				String codIva=rs.getString("commoditycode");//6			
				String decimales = "00";
				String precio="0";//7					

				BigDecimal price = rs.getBigDecimal("pricelist");
				if (price == null) {
					price = Env.ZERO;
					//precio = "00.00";
				}else{
					precio = price.setScale(2, RoundingMode.HALF_UP).toString();

				}

				BigDecimal d = (price);
				BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
				
				if (result != null){
					
					if(result.compareTo(Env.ZERO) != 0){

						if(result.toString().length() >= 3){
							decimales = result.toString().substring(0, 2);
						}
						else if(result.toString().length() == 2){
							decimales = result.toString();
						}
						else if(result.toString().length() == 1){
							decimales = result.toString()+'0';
						}
					}
				}
				precio = String.valueOf(price.intValue()) + decimales;

				//INI Obtener string con articulos para cada producto - SBouissa 02-10-2015 
				//Atributos
				String atributosHexa = rs.getString("attrs");
				//Cantidad por pack
				String cantPack = rs.getString("UnitsPerPack");
				//FIN Obtener string con articulos para cada producto - SBouissa 02-10-2015 
				
				//CodigoMedida por ahora 0
				String codMedida =((rs.getString("uniMedida").equalsIgnoreCase("KG"))? "2":"1");
				
				lineToPrint = new StringBuilder();
				lineToPrint.append(INSERTAR+SEPARATOR_L+A+SEPARATOR_L+codigo+SEPARATOR_L+desc+SEPARATOR_L
						+codEntorno+SEPARATOR_L+codSubFamilia+SEPARATOR_L+moneda+SEPARATOR_L+codIva+SEPARATOR_L
						+precio+SEPARATOR_L+atributosHexa+SEPARATOR_L+cantPack+SEPARATOR_L+codMedida);
				//Escribir linea en el archivo
				writeFile(lineToPrint.toString(),fileMaster,true); 
				
			}
			//Isert de relacion tandem
			loadTandem(fileMaster);
			//Insert de relacion codigo de barras
			loadArticulosExternos(fileMaster);
			//Insert Clientes 
			loadClientsCC(fileMaster);
			//Se cuentan las lineas y se copia archivo al destino de sisteco
			int linesBatch = counLines(fileMaster);
			if(0<linesBatch){
				writeFile(String.valueOf(linesBatch),fCountBatch,false);
				//Directorio de sisteco a dejar cada archivo
				File destino = null;
				destino = new File (getRutaDestinoSisteco()+File.separator+batchAll);
				File destCount = null;
				destCount = new File (getRutaDestinoSisteco()+File.separator+batchAfl);

				try{
					//Copio los archivos de la carpeta local nuestra a la carpeta de mantenimiento (SISTECO)
					copyFile(fileMaster, destino);
					copyFile(fCountBatch,destCount);
					// Dejo los archivos con fecha y hora mas el nombre
					String[] hra = (new Timestamp (System.currentTimeMillis()).toString().split(SEPARATOR_L));
					String fecha =hra[0].replace("-", "").replace(" ", "_")+hra[1];
					File fhist = new File(getRutaOrigen()+File.separator+fecha+"_"+batchAll);
					File fhistC = new File(getRutaOrigen()+File.separator+fecha+"_"+batchAfl);
					//Copio los archivos que hago con la fecha y hora para auditoria en carpeta nuestra
					copyFile(fileMaster, fhist);
					copyFile(fCountBatch,fhistC);
					if((MSysConfig.getValue("UY_TEST_OPUP",0).equals("N"))){//Si es test no actualizo
						setReadingDateMantenimientos();
					}
				}catch(Exception e){
					throw new AdempiereException(e.getMessage());
				}
			}
			
		} 
		
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 25/11/2015
	 */
	private void setReadingDateMantenimientos() {
		//Seteo como leidos el mantenimiento de los productos
		String where = " WHERE "+MRTInterfaceProd.COLUMNNAME_ReadingDate +" IS NULL ";
		String sql = "UPDATE "+MRTInterfaceProd.Table_Name+" SET "
					+MRTInterfaceProd.COLUMNNAME_ReadingDate+ " = '"+ getDateString()+"'";
		sql = sql +", "+MRTInterfaceProd.COLUMNNAME_Updated+" = (SELECT now()) " ;
		DB.executeUpdateEx(sql+where,get_TrxName()); 

		//Seteo como leidos el mantenimiento de los clientes
		String whereBP = " WHERE "+MRTInterfaceBP.COLUMNNAME_ReadingDate +" IS NULL ";
		String sqlBP = "UPDATE "+MRTInterfaceBP.Table_Name+" SET "
						+MRTInterfaceBP.COLUMNNAME_ReadingDate+ " = '"+ getDateString()+"'";
		sqlBP = sqlBP +", "+MRTInterfaceProd.COLUMNNAME_Updated+" = (SELECT now()) " ;
		DB.executeUpdateEx(sqlBP+whereBP,get_TrxName()); 
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 2/7/2015
	 * @return
	 */

	private int counLines(File aFile) throws IOException {
	    LineNumberReader reader = null;
	    try {
	        reader = new LineNumberReader(new FileReader(aFile));
	        while ((reader.readLine()) != null);
	        return reader.getLineNumber();
	    } catch (Exception ex) {
	        return -1;
	    } finally { 
	        if(reader != null) 
	            reader.close();
	    }
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 19/6/2015
	 * @return
	 */
	private String getRutaOrigen() {
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_DIRECTORY_RT_MASTER",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_DIRECTORY_RT_MASTER",0); //Directorio origen prametrizado para linux
		}
		return origen;
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"RutaOrigen"); //C:\FilesCovadonga\DestinoSisteco

	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
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


	/**Metodo para convertir bits en Hexagesimal
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @return
	 */
	private String bitsToHexa(String[] hexIn) {
		String hexString = ""; String hex="";
		if(0!=hexIn.length){
			for(int j=0;j<hexIn.length;j++){
				hex = hexIn[j].trim();
				long num = Long.parseLong(hex);
				long rem;
				while(num > 0){
					rem = num % 10;
					num = num / 10;
					if(rem != 0 && rem != 1){//Es binario?
						return hexString;
					}
				}
				int i= Integer.parseInt(hex,2);
				hexString = hexString + Integer.toHexString(i); //Concateno los valores
			}
			
		}
		return hexString.toUpperCase();
	}

	/**
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @param lineIn
	 * @param fBatch
	 */
	private void writeFile(String lineIn, File fBatch, Boolean continuo) {
		BufferedWriter bw=null;
		File f = fBatch;
		//Escritura
		if(null!=f){
			try{
				FileWriter w = new FileWriter(f,continuo);
				bw = new BufferedWriter(w);
				bw.append(lineIn);
				bw.newLine();
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
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 12/05/2015
	 * @return
	 */
	private String getRutaDestinoSisteco() {
		String  origen = "";
		if(MSysConfig.getValue("UY_TEST_OPUP",0).equals("Y")){
			if(onWindows){
				origen = MSysConfig.getValue("UY_DEST_DIRECTORY_SISTECO_TEMP",0); //Directorio origen prametrizado para windows
			}else{
				origen = MSysConfig.getValue("DEST_DIRECTORY_SISTECO_TEMP",0); //Directorio origen prametrizado para el servidor
			}
		}else{
			if(onWindows){
				origen = MSysConfig.getValue("UY_DEST_DIRECTORY_SISTECO",0); //Directorio origen prametrizado para windows
			}else{
				origen = MSysConfig.getValue("DEST_DIRECTORY_SISTECO",0); //Directorio origen prametrizado para el servidor
			}
		}
		
 		return origen;
		//destino temporal para realizar prubas para siteco 25-11-2015 sbouissa
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"); //C:\FilesCovadonga\DestinoSisteco
	}
	
	

	
	
	
	/**Recorre los clientes (IsCustomer = Y) para generar lineas de inter para sisteco
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8/10/2015
	 * @param fileMaster
	 */
	private void loadClientsCC(File fileMaster) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			sql = "SELECT bp.c_bpartner_id as bpID, bp.value as cod, bp.name2 as name, bp.DUNS as ruc, bp.Cedula,"
					+ " bp.DocumentType, bp.Email "
					+ " FROM C_BPartner bp "
					+ " WHERE bp.IsActive = 'Y' AND bp.IsCustomer = 'Y' "
					+ " ORDER BY bp.value" ;
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			System.out.println("LOG-Inicio carga "+C);
          
			while (rs.next()) {
				int m_bpartnerID = 0;
				m_bpartnerID = rs.getInt("bpID");
				StringBuilder lineToPrint = null; 
				String inicio = INSERTAR+SEPARATOR_L+C+SEPARATOR_L;//Accion + Tabla a impactar
				String codCte = rs.getString("cod");//CodigoCliente
				if(!esNumerico(codCte)){
					writeError("Error- MBParnerID:"+ m_bpartnerID+" codigo no es numerico ("+codCte+")");
					continue;
				}
				String nameCte = rs.getString("name");//Nombre
				
				String dirCte = "";	//Direccion
				String esquina1 = "";//Esquina1
				String esquina2 = "";//Esquina2
				String telCte = "";//Telefono
				String rucCte = (rs.getString("ruc")!=null?rs.getString("ruc"):"");//RUT
				String documento = (rs.getString("Cedula")!=null)? rs.getString("Cedula"):"";//Documento

				String codigoTipoDocumentoCliente = "";//CodigoTipoDocumentoCliente -- Posibles RUT/CI/OTR/PSP/DNI
				//OpenUp SBouissa 26/11/2015 - Se cambia metodo de control 
				if(!rucCte.equals("")){// si tengo rut entonces codTipdoc -->RUT(3)
					codigoTipoDocumentoCliente = "2";
				}else if(rucCte.equals("") && !(documento.equals(""))){//si rut vac�o y cedula NO entonces codTipoDoc -->CI(3)
					codigoTipoDocumentoCliente = "3";
				}else if(rucCte.equals("") && (documento.equals(""))){
					writeError("PosibleError en Carga Sisteco - MBParnerID:"+ m_bpartnerID+" RUT y Cedula cliente null");
					codigoTipoDocumentoCliente = "4";
				}
				
//				if(rs.getString("DocumentType").equalsIgnoreCase("RUT")){
//					codigoTipoDocumentoCliente = "2";
//					if(rucCte.equals("")){
//						writeError("Error- MBParnerID:"+ m_bpartnerID+" RUT cliente null");
//						continue;
//					}
//				}else if(rs.getString("DocumentType").equalsIgnoreCase("CI")){
//					codigoTipoDocumentoCliente = "3";
//					if(documento.equals("")){
//						writeError("Error - MBParnerID:"+ m_bpartnerID+" Documento cliente null");
//						continue;
//					}
//				}else codigoTipoDocumentoCliente = "4"; //-->OTR
//				
				String codigoDepartamentoPais = "10";//CodigoDepartamentoPais - Se codigfica relacion depto codigo en tabla uy_
				String ciudadCte =  "";//Ciudad
				String codPtalCte = "";//CodigoPostal
				String email = rs.getString("Email")!=null?rs.getString("Email"):"";//Email
				//String codigoPais = "99";//CodigoPais -->Otro
				String codigoPais = "UY";//-->30/11/2015 Valor por defecto desde ahora no corre mas 99 Issue #5125
				String atributos = "";//Atributos
				MBPartnerLocation[] bpl = MBPartnerLocation.getForBPartner(getCtx(), m_bpartnerID, null);
				if(null!=bpl && bpl.length>0){
					
					dirCte = ((bpl[0].getAddress1()!=null)?bpl[0].getAddress1():"");//Direccion
					codPtalCte = ((bpl[0].getPostal()!=null)?bpl[0].getPostal():"");//Codigo Postal 
					telCte = ((bpl[0].getPhone()!=null)?bpl[0].getPhone():"");//Tel
					 MLocalidades loc = (MLocalidades) bpl[0].getUY_Localidades();
					 if(null!=loc){
						 if(0==loc.get_ID()){
								ciudadCte = "";
						 }else{
								ciudadCte = ((loc.getName()!=null)?loc.getName():"");//Ciudad
						 }
					 }
					 MDepartamentos dep = (MDepartamentos) bpl[0].getUY_Departamentos();
					 if(null!= dep){
						 if(0==dep.get_ID()){
								codigoDepartamentoPais = ("10");
						}else{
							 codigoDepartamentoPais = dep.get_ValueAsString("Value");
						}
					 }
					 MCountry pais = (MCountry) bpl[0].getC_Country();
					 codigoPais = "UY"; // Por defecto indico Uruguay 30/11/2015 valor permititdo por sisteco Issue #5125
					 if(null!=pais){
						 if( pais.getCountryCode().equalsIgnoreCase("AR") ||
								 pais.getCountryCode().equalsIgnoreCase("BR") ||
								 pais.getCountryCode().equalsIgnoreCase("PY") ||
								 pais.getCountryCode().equalsIgnoreCase("UY") ){
							 codigoPais = pais.getCountryCode().toUpperCase(); 
						 }else{
							 //OpenUp SBT 30/11/2015 Siste no les permite utilizar en le sistema de cajas Issue #5125
							 //codigoPais = "99"+SEPARATOR_L; //-->Otros 
						 } 
					 }
					
				}
				
				String attr1 = "1";String attr2 = "1";String attr3 = "1";
				String [] retorno = new String[4];
				retorno[0] = attr1+attr2+attr3+"0";
				retorno[1] = "0000";	
				retorno[2] = "0000";	
				retorno[3] = "0000";	
				atributos = bitsToHexa(retorno);
				 /*�	CodigoCliente�	Nombre�	Direccion�	Esquina1�	Esquina2�	Telefono�	RUT�	Documento
		            * �	CodigoTipoDocumentoCliente�	CodigoDepartamentoPais�	Ciudad�	CodigoPostal�	Email�	CodigoPais
		            * �	Atributos*/
				lineToPrint = new StringBuilder();
				lineToPrint.append(inicio+codCte.trim().replace(SEPARATOR_L, "_")
						+SEPARATOR_L+nameCte.trim().replace(SEPARATOR_L, "_")+SEPARATOR_L+dirCte.trim().replace(SEPARATOR_L, "_")
						+SEPARATOR_L+esquina1+SEPARATOR_L+esquina2+SEPARATOR_L+telCte+SEPARATOR_L+rucCte
						+SEPARATOR_L+documento+SEPARATOR_L+codigoTipoDocumentoCliente
						+SEPARATOR_L+codigoDepartamentoPais+SEPARATOR_L+ciudadCte+SEPARATOR_L+codPtalCte
						+SEPARATOR_L+email+SEPARATOR_L+codigoPais+SEPARATOR_L+atributos);
				if(codigoDepartamentoPais!="" || codigoTipoDocumentoCliente!="" || codigoPais!=""){
					writeFile(lineToPrint.toString(),fileMaster,true);
				}else{
					writeError("Verificar codigos obligatorios -  MBParnerID:"+ m_bpartnerID);
					continue;
				}
			}
			
		}catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 24/11/2015
	 * @param codCte
	 * @return
	 */
	private boolean esNumerico(String codCte) {
		try{
			int cod = Integer.valueOf(codCte);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

	private void loadTandem(File fileMaster){
		//Linea para obtener los inserts de tandem: (esportar sin columnas, sein comillas y sin serarar postgresql)
		//select 'I:TANDEM:',a.value,':',b.value from m_product a inner join m_product b on (a.m_product_tandem_id=b.m_product_id) order by a.value
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			sql = "SELECT a.value as art,b.value as tandem from m_product a inner join m_product b on "
					+ "(a.m_product_tandem_id=b.m_product_id) "
					+ " where a.isactive = 'Y' and b.isactive = 'Y' "
					+ " order by a.value ";
					
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				System.out.println("LOG-Inicio carga "+T);

				while (rs.next()) {
					StringBuilder lineToPrint = null; //
					String inicio = INSERTAR+SEPARATOR_L+T+SEPARATOR_L;//"I:TANDEM:";//1
					String val = rs.getString("art");//2
					String val2 = rs.getString("tandem");//3 por ahora 0
	
					lineToPrint = new StringBuilder();
					lineToPrint.append(inicio+val+":"+val2);

					writeFile(lineToPrint.toString(),fileMaster,true); //dirChannelB
				}
		}catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	private void loadArticulosExternos(File fileMaster){
		//Linea para obtener los inserts de codigoexterno
		//select 'I:ARTICULOS_EQUIVALENTES:',a.upc,':',b.value from UY_ProductUpc a inner join m_product b on (a.m_product_id=b.m_product_id) order by b.value
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			sql = "select a.upc as upc,b.value as art"
					+ " from UY_ProductUpc a "
					+ " inner join m_product b "
					+ " on (a.m_product_id=b.m_product_id) "
					+ " where b.isactive = 'Y'"
					+ " order by b.value";
					
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			System.out.println("LOG-Inicio carga "+AE);
			while (rs.next()) {
				
				StringBuilder lineToPrint = null; //
				String inicio = INSERTAR+SEPARATOR_L+AE+SEPARATOR_L;// "I:ARTICULOS_EQUIVALENTES:";//1
				String val = rs.getString("upc");//2
				String val2 = rs.getString("art");//3 por ahora 0

				lineToPrint = new StringBuilder();
				lineToPrint.append(inicio+val+SEPARATOR_L+val2);

				writeFile(lineToPrint.toString(),fileMaster,true); //dirChannelB
			}
		}catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	private File creatCountBattAllFile() {

		File fo = null;
		
		try {
			fo = new File(getRutaOrigen() + File.separator + batchAfl);
			
		} catch (Exception e) {
			throw new AdempiereException("No se pudo crear archivo historico.");
		}
		
		return fo;
	}
	
	/**
	 * Metodo para escribir errores
	 * OpenUp Ltda Issue #4097
	 * @author Sylvie Bouissa 23/11/2015
	 * @param error
	 */
	private void writeError(String error){
		Timestamp date = new Timestamp(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		String hora = date.getDate()+"-"+date.getMonth()+" "+date.getHours()+":"+date.getMinutes();
		writeFile(error +" Fecha:"+hora, fBatchError, true);
	}
	
	/**
	 * OpenUp Ltda Issue #4097 
	 * @author Sylvie Bouissa 23/11/2015
	 * @return
	 */
	private File createBatchErrorFile() {
		String[] fecha = new Timestamp (System.currentTimeMillis()).toString().split("-");
		//Formato fecha YYYYMMdd
		String fechaArch = fecha[0]+fecha[1]+fecha[2].substring(0, 2);
		File fb=null;
		fb = new File(getRutaOrigen()+File.separator+"ArchDeErrores"+
				File.separator+fechaArch+PRTInterfaceBPProdCliMaster.NAME_BATCH_ERROR);
		return fb;
	}
	
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 21/11/2015
	 * @return
	 */
	private String getDateString() {
		String[] fecha = new Timestamp (System.currentTimeMillis()).toString().split("-");
		//Formato fecha YYYYMMdd
		String fechaArch = fecha[0]+fecha[1]+fecha[2].substring(0, 2);
		return fechaArch;
	}
}

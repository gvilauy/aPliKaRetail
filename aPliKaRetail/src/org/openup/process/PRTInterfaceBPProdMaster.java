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
//import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCurrency;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTaxCategory;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.jboss.util.file.Files;
import org.openup.model.MDepartamentos;
import org.openup.model.MLocalidades;
import org.openup.model.MProdAttribute;
import org.openup.model.MProductAttribute;
//import org.omg.CORBA.PRIVATE_MEMBER;
import org.openup.model.MRTInterfaceProd;

import java.sql.PreparedStatement;

//import org.python.antlr.ast.Continue;

//import sun.jkernel.Bundle;

/**OpenUp Ltda Issue# 4408
 * Proceso para generar archivo batch y online que permite para creaciï¿½n, actualizaciï¿½n y 
 * eliminacion de clientes y productos en sistema sisteco
 * @author SBouissa 12/6/2015
 *
 */
public class PRTInterfaceBPProdMaster extends SvrProcess{
	private String esMaster ="";
	//	I - Insertar
	//	U - Actualizar 
	//	D - Borrar
	//	A - Insertar/Actualizar
	private String fchToday;
	private static final String INSERTAR = "I";
	private static final String ACTUALIZAR = "U";
	private static final String ELIMINAR = "D";
		
	//Tablas a impactar en sisteco 
	private static final String A  = "ARTICULOS";
	private static final String AE = "ARTICULOS_EQUIVALENTES";
	private static final String T  = "TANDEM";
	private static final String C  = "CLIENTESCUENTACORRIENTE";
	
	//Variables
	private static final String SEPARATOR_L = ":";//caracter separador de las lineas de los archivos a escribir
	//Testing maquina sbt
	//private static final String PATHBATCH = "C:"+File.separator+"FilesCovadonga"+File.separator;//"c:\temp\lineadecodigo\fichero.txt"
	//private static final String PATHONLINE = "C:"+File.separator+"FilesCovadonga"+File.separator;
	
	private static final String PATHBATCH = File.separator+"srv"+File.separator+"share"+File.separator+"HistoricoActualizaciones"+File.separator;//  srv/share/HistoricoActualizaciones
	private static final String PATHONLINE = File.separator+"srv"+File.separator+"share"+File.separator+"HistoricoActualizaciones"+File.separator;// srv/share/HistoricoActualizaciones
	
	private static final String NAME_BATCH = "bo_batch.txt";//"c:\temp\lineadecodigo\fichero.txt"
	private static final String NAME_ONLINE = "bo_online.txt";
	
	private static final String NAME_COUNTBATCH = "bo_batch.tfl";//"c:\temp\lineadecodigo\fichero.txt"
	private static final String NAME_COUNTONLINE = "bo_online.tfl";
	
	
	private File fCountBatch = null;
	//Variable para identificar el sistema que esta corriendo el proceso
	private Boolean onWindows = true;
	/**
	 * Constructor
	 */
	public PRTInterfaceBPProdMaster() {
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Indico que es maestro y que se corre en windows
		esMaster = "master";
		onWindows = true;
		try{
			String a = System.getProperty("os.name" );
			if(a.contains("Linux")){
				onWindows = false;
				System.out.println(System.getProperty("os.name" ));
			} 
			
			if((!Files.delete(new File(getRutaOrigen()+File.separator+"bo_batch.all")))&&!Files.delete(new File(getRutaOrigen()+File.separator+"bo_batch.afl"))){
				System.out.println("No hay archivos para borrar");
			}else{
				System.out.println("Se borraron los archivos");
			}

		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new AdempiereException(e.getMessage());
		}
		//crear archivo contador batch
		fCountBatch = creatCountBattAllFile();

	}	



	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if(!"master".equals(esMaster)){
			
			return "NO MASTER";

		}
		else {
			cargoAtributosYSeteo();//Verifico si algun producto no tiene atributos si no los tiene se los creo
			
			generateMasterFile();
			
			return "OK!";
		}
		
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 2/7/2015
	 * @return
	 */
	@SuppressWarnings("unused")
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

	/**Crea la linea segun accion, tabla, y datos correspondiente ver doc Mantenimiento Pazos Covadonga 
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @return
	 */
	@SuppressWarnings("unused")
	private StringBuilder processData(MRTInterfaceProd row) {
		StringBuilder lineIn = null;
		try{	
			String accion = ""; // I,U,D A
			//Accion que corresponde en dicha linea para los arch de sisteco
			accion = row.getUY_RT_Action().getcodigosisteco();
			//Detecto en que tabla debo insertar/actualizar/eliminar 			
			String codeTable = row.getTablaOrigen();// A Articulos, AE Articulo_externo, T Tandem
			String table = getTableNameSisteco(codeTable);
			StringBuilder datosLinea = null;
			//Cargo datos segun tabla, y segun 
			if(table.equals(PRTInterfaceBPProdMaster.A)){
				datosLinea = getDataToRowArticulo(row,accion);
				System.out.println("Proceso ARTICULO");
			}else if(table.equals(PRTInterfaceBPProdMaster.AE)){
				datosLinea = getDataToRowArticuloEquivalente(row,accion);
				System.out.println("Proceso ARTICULO_EQUIVALENTE");
			}else if(table.equals(PRTInterfaceBPProdMaster.T)){
				datosLinea = getDataToRowTandem(row,accion);
				System.out.println("Proceso TANDEM");
			}
			if(null!=datosLinea){
				lineIn = new StringBuilder();
				lineIn.append(accion); lineIn.append(SEPARATOR_L); //Accion
				lineIn.append(table); lineIn.append(SEPARATOR_L); //Tabla
				lineIn.append(datosLinea);//Datos
			}
			
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
		return lineIn;
	}

	/**Retorna los datos correspondientes a las lineas de la Tabla TANDEM
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 18/6/2015
	 * @param row
	 * @param accion
	 * @return
	 */
	private StringBuilder getDataToRowTandem(MRTInterfaceProd row, String accion) {
		StringBuilder lineIn = null;
		try{
			lineIn = new StringBuilder();
			if(PRTInterfaceBPProdMaster.INSERTAR.equals(accion) || PRTInterfaceBPProdMaster.ACTUALIZAR.equals(accion)){
				
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());lineIn.append(SEPARATOR_L); //CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());;lineIn.append(SEPARATOR_L);
				}
				//02-07-2015 sbouissa se actualiza linea 
				lineIn.append(row.getM_Product_Tandem().getValue()); //CodigoArticuloTandem (codigobarra)
				
			}else if(PRTInterfaceBPProdMaster.ELIMINAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());
				}else{
					lineIn.append(row.getM_Product().getValue());
				}
			}		
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		return lineIn;
	}

	/**Retorna los datos correspondientes a las lineas la Tabla ARTICULOS_EQUIVALENTES
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 18/6/2015
	 * @param row
	 * @param accion
	 * @return
	 */
	private StringBuilder getDataToRowArticuloEquivalente(MRTInterfaceProd row,
			String accion) {
		StringBuilder lineIn = null;
		
		//sbouissa 09/07/2015
		//Se debe controlar que el upc no sea cero (hasta que lo haga el sistema del lado de adempiere)
		if(row.getUPC().length()==1){
			if("0".equals(row.getUPC())){
				return null;
			}
		}
		try{
			lineIn = new StringBuilder();
			if(PRTInterfaceBPProdMaster.INSERTAR.equals(accion) || PRTInterfaceBPProdMaster.ACTUALIZAR.equals(accion)){
				lineIn.append(row.getUPC());lineIn.append(SEPARATOR_L); //CodigoArticuloExterno (codigobarra)
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode()); //CodigoArticuloInterno
				}else{
					lineIn.append(row.getM_Product().getValue());
				}
					
			}else if(PRTInterfaceBPProdMaster.ELIMINAR.equals(accion)){
				lineIn.append(row.getUPC());
			}		
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		return lineIn;
	}

	/**Retorna los datos correspondientes a las lineas la Tabla ARTICULOS
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 18/6/2015
	 * @param row
	 * @return
	 */
	private StringBuilder getDataToRowArticulo(MRTInterfaceProd row, String accion) {
		//inserto linea correspondiente (se debe calcular hxagesimal)
		StringBuilder lineIn = null;
		try{
			lineIn = new StringBuilder();
			if(PRTInterfaceBPProdMaster.INSERTAR.equals(accion) || PRTInterfaceBPProdMaster.ACTUALIZAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());lineIn.append(SEPARATOR_L);//CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());lineIn.append(SEPARATOR_L);
				}
				
				lineIn.append(row.getDescription());lineIn.append(SEPARATOR_L);//Descripcion
				lineIn.append("0").append(SEPARATOR_L); //CodigoEntorno por ahora siempre 0
				lineIn.append("0").append(SEPARATOR_L); //CodigoSubFamilia siempre es cero hasta que se defina gerarquias 25-06-2015
//				if(0!=row.getUY_SubFamilia_ID()){
//					String sql = "SELECT Value FROM UY_SubFamilia WHERE UY_SubFamilia_ID = ?"; //+row.getUY_SubFamilia_ID();
//					String val = DB.getSQLValueString(null, sql, row.getUY_SubFamilia_ID());
//					lineIn.append(val);lineIn.append(SEPARATOR_L);//CodigoSubFamilia
//				}else{
//					 return null;
//				}
				lineIn.append(currencyCodeAux(row.getC_Currency_ID()));lineIn.append(SEPARATOR_L);//CodigoMoneda
				if(null!=row.getC_TaxCategory() && 0!=row.getC_TaxCategory_ID()){
					lineIn.append(row.getC_TaxCategory().getCommodityCode());lineIn.append(SEPARATOR_L);		//CodigoIVA
				}else{
					return null;
				}
				
				lineIn.append(row.getPriceList().toString());lineIn.append(SEPARATOR_L);		//PrecioArticulo
				//--> Obtencion de los 48 bits 
				String[] listaAtributos = getAtrubutesBitString(row); 
				//Pasar a hexagesimal lista de atributos
				String atributosHexa = bitsToHexa(listaAtributos);
				if(("").equals(atributosHexa)){
					return null;
				}else if(16!=atributosHexa.length()) {
					return null; // falto agregar antes..10-07-2015
				}
				lineIn.append(atributosHexa).append(SEPARATOR_L);
				lineIn.append(String.valueOf(row.getUnitsPerPack()));lineIn.append(SEPARATOR_L);		//Cantidad Pack
				lineIn.append("0"); //Codigo Medida (por ahora 0)
	
			}else if(PRTInterfaceBPProdMaster.ELIMINAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());//CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new AdempiereException(e.getMessage());
		}
		return lineIn;
	}

	/**
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 18/6/2015
	 * @param row
	 * @return
	 */
	private String[] getAtrubutesBitString(MRTInterfaceProd row) {
		String [] retorno = null;
		// tener en cuenta los bits 8-10-18-23-26-28-33-34-35-37-42-43-44 van en cero
		try{
			retorno = new String[16];
			retorno[0] = String.valueOf(row.getattr_1()) + String.valueOf(row.getattr_2())
						 +String.valueOf(row.getattr_3()) + String.valueOf(row.getattr_4());
				
			retorno[1] = String.valueOf(row.getattr_5()) + String.valueOf(row.getattr_6())
				        +String.valueOf(row.getattr_7()) + String.valueOf(0);
			
			retorno[2] = String.valueOf(row.getattr_9()) + String.valueOf(0)
			        	+String.valueOf(row.getattr_11()) + String.valueOf(row.getattr_12());
			
			retorno[3] = String.valueOf(row.getattr_13()) + String.valueOf(row.getattr_14())
			        	+String.valueOf(row.getattr_15()) + String.valueOf(row.getattr_16());
			
			retorno[4] = String.valueOf(row.getattr_17()) + String.valueOf(0)
		        		+String.valueOf(row.getattr_19()) + String.valueOf(row.getattr_20());
			
			retorno[5] = String.valueOf(row.getattr_21()) + String.valueOf(row.getattr_22())
		        		+String.valueOf(0) + String.valueOf(row.getattr_24());
			
			retorno[6] = String.valueOf(row.getattr_25()) + String.valueOf(0)
			        	+String.valueOf(row.getattr_27()) + String.valueOf(0);
			
			retorno[7] = String.valueOf(row.getattr_29()) + String.valueOf(row.getattr_30())
			        	+String.valueOf(row.getattr_31()) + String.valueOf(row.getattr_32());
			
			retorno[8] = String.valueOf(0) + String.valueOf(0)
		        		+String.valueOf(0) + String.valueOf(row.getattr_36());
			
			retorno[9] = String.valueOf(0) + String.valueOf(row.getattr_38())
		        		+String.valueOf(row.getattr_39()) + String.valueOf(row.getattr_40());
			
			retorno[10] = String.valueOf(row.getattr_41()) + String.valueOf(0)
		        		+String.valueOf(0) + String.valueOf(0);
			
			retorno[11] = String.valueOf(row.getattr_45()) + String.valueOf(row.getattr_46())
		        		+String.valueOf(row.getattr_47()) + String.valueOf(row.getattr_48());
			
			retorno[12] = "0000";
			
			retorno[13] = "0000";
			
			retorno[14] = "0000";
			
			retorno[15] = "0000";

		}catch(Exception e){
			e.printStackTrace();
		}

		return retorno;
				
	}

	/**Retorno el nombre exacto de la tabla en la que debo insertar/borrar/actualizar
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 18/6/2015
	 * @param codeTable
	 * @return
	 */
	private String getTableNameSisteco(String codeTable) {
		if(null!=codeTable){
			if("A".equals(codeTable)){
				return PRTInterfaceBPProdMaster.A;
			}else if("AE".equals(codeTable)){
				return PRTInterfaceBPProdMaster.AE;
			}else if("T".equals(codeTable)){
				return PRTInterfaceBPProdMaster.T;
			}else if("C".equals(codeTable)){
				return PRTInterfaceBPProdMaster.C;
			}
		}
		return "";
	}

	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 19/6/2015
	 * @return
	 */
	private String getRutaOrigen() {
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_DIRECTORY_RT_MANT",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_DIRECTORY_RT_MANT",0); //Directorio origen prametrizado para linux
		}
		return origen;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param source
	 * @param dest
	 */
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
	/**Metodo retorna 1 o 2 dependiendo del codigo de moneda recibido, solo se contemplan estas dos monesdas
	 * 142 ~ 1 = PESOS
	 * 100 ~ 2 = DOLARES
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @param string
	 * @return
	 */
	private CharSequence currencyCodeAux(int moneda) {
		if(100 == moneda){
			return "2"; // DOLARES
		}else return "1"; // PESOS
	}

	/**Metodo para convertir bits en Hexagesimal
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @return
	 */
	private String bitsToHexa(String[] hexIn) {
		//System.out.println(hexIn);
		String hexString = ""; String hex="";
		if(0!=hexIn.length){
			int count = 0;
			for(int j=0;j<hexIn.length;j++){
				count++;//16?
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
		//System.out.println(hexString);
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
						// TODO Auto-generated catch block
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
		if(onWindows){
			origen = MSysConfig.getValue("UY_DEST_DIRECTORY_SISTECO",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DEST_DIRECTORY_SISTECO",0); //Directorio origen prametrizado para el servidor
		}
 		return origen;
		//destino temporal para realizar prubas para siteco 07-07-2015 sbouissa
		//return File.separator+"srv"+File.separator+"TempSisteco";
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"); //C:\FilesCovadonga\DestinoSisteco
	}
	
	public void cargoAtributosYSeteo(){
		//File fileMaster = new File(PRTInterfaceBPProdMaster.PATHBATCH+fchToday+"maestro.txt");
		List<MProduct> mpLst = MProduct.forProductsWithoutAttr(getCtx(),null);
		if(null!=mpLst){
			for(MProduct p : mpLst){
				List<MProductAttribute> prodatts = p.getAttributes();
				if(0>=prodatts.size()){
					System.out.println(String.valueOf(p.getValue())); 
					String sql1 = "select ad_sequence_id from ad_sequence where lower(name) like 'm_productattribute'";
					
					int seqID= DB.getSQLValueEx(null, sql1);
					 String sql2="insert into m_productattribute (m_productattribute_id,ad_client_id,ad_org_id,isactive,created,createdby,"
					 		+ "updated,updatedby,uy_prodattribute_id,m_product_id,isselected,seqno)"
					 		+ "select nextID("+seqID+",'N'),prod.ad_client_id,prod.ad_org_id,'Y',now(),"+Env.getAD_User_ID(getCtx())+",now(),"+Env.getAD_User_ID(getCtx())+",a.uy_prodattribute_id,"
					 		+ "prod.m_product_id,'N',(a.uy_prodattribute_id - 999999)*10 "
					 		+ "from uy_prodattribute a, m_product prod"
					 		+ " where m_product_id = "+ p.get_ID();
					 int cant = DB.executeUpdateEx(sql2, null);
					 if(0<cant){
//						 String sqlHexa ="SELECT Hexa FROM MoldeAtributosAux WHERE ValueP = '"+p.getValue()+"'";
//						 String m_hexa  = DB.getSQLValueString(null, sqlHexa);
//						 String sqlu="";
//						 if(null!=m_hexa){
//							 if(m_hexa.equals("400C000100000000")){
//								 sqlu= "UPDATE M_ProductAttribute SET IsSelected = 'Y' WHERE M_Product_ID IN ("
//										 + "SELECT M_Product_ID FROM M_Product WHERE Value in("+
//													"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like '400C000100000000')"+//-0100000000001100000000000000000100000000000000000000000000000000
//											          ") AND UY_ProdAttribute_ID IN (1000001,1000010,1000011,1000025)";
//							 }else if (m_hexa.equals("0020000100000000")){
//								 sqlu = "UPDATE M_ProductAttribute SET IsSelected = 'Y'  WHERE M_Product_ID IN ("
//											+ "SELECT M_Product_ID FROM M_Product WHERE Value in("+
//											"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like '0020000100000000')"+ //--0000000000100000000000000000000100000000000000000000000000000000
//									          ") AND UY_ProdAttribute_ID IN (1000008,1000025)";                          //     --0000000000100000000000000000000100000000000000000000000000000000 - 76350 rows
//
//							 }else if (m_hexa.equals("C000000100000000")){
//								//C000000100000000   --1100000000000000000000000000000100000000000000000000000000000000 (1,2,32) 1000000,1000001,1000025
//								 sqlu = "UPDATE M_ProductAttribute SET IsSelected = 'Y'  WHERE M_Product_ID IN ("
//									+ "SELECT M_Product_ID FROM M_Product WHERE Value in("+
//											"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like 'C000000100000000')"+// --1100000000000000000000000000000100000000000000000000000000000000
//									        ") AND UY_ProdAttribute_ID IN (1000000,1000001,1000025)";                   //    --1100000000000000000000000000000100000000000000000000000000000000 - 3 rows
//
//							 }else if (m_hexa.equals("0020010100000000")){
//								 sqlu="UPDATE M_ProductAttribute SET IsSelected = 'Y'   WHERE M_Product_ID IN ("
//									+ "SELECT M_Product_ID FROM M_Product WHERE Value in("+
//											"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like '0020010100000000')"+//--0000000000100000000000010000000100000000000000000000000000000000 -54rows
//									          ") AND UY_ProdAttribute_ID IN (1000008,1000019,1000025)"; //
//							 }
//							 int cant1 = DB.executeUpdateEx(sqlu, null);
						 	System.out.println("cargo atributos");
						 }else{
							 continue;
						 }
						 
					 }	
				}
				
			}
		//}
		
			
	}
	
	public void crearCargaMaestro(){
		throw new AdempiereException("Implementación obsoleta");
//		List<MProduct> mpLst = MProduct.forProductsNotR(getCtx(),null);
//		File fileMaster = new File(PRTInterfaceBPProdMaster.PATHBATCH+fchToday+"maestro.txt");
//		
//		for(MProduct p : mpLst){
//			//System.out.println(String.valueOf(p.getValue())); 
//			StringBuilder lineToPrint = null; //
//			String codigo = p.getValue();//1
//			String desc = p.getName().replace(":", "_");//2
//			String codEntorno = "0";//3 por ahora 0
//			String codSubFamilia = "0";//4 por ahora 0
//			 
//			String moneda = "1";//5 --por defecto 1 pesos codigo sisteco -- 			
//			MCurrency currency = new MCurrency(getCtx(), p.getSaleCurrency(), null);
//			if(null!=currency){
//				moneda = (String) currencyCodeAux(currency.get_ID());
//			}
//					
//			String codIva="0";//6			
//			MTaxCategory tax = new MTaxCategory(getCtx(),p.getC_TaxCategory_ID(),null);
//			if(null!=tax){		
//				codIva = tax.getCommodityCode();
//			}
//			String decimales = "00";
//			String precio="0";//7					
//			BigDecimal price = p.getSalePrice();
//			if(null!=price){
//				System.out.println(price.toString());
//				BigDecimal d = (price);
//				BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
//				
//				if(null!=result){
//					if(!result.equals(BigDecimal.ZERO)){
//						if(result.toString().length()>=3){
//							decimales = result.toString().substring(0, 2);
//						}else if(result.toString().length()==2){
//							decimales = result.toString();
//						}else if(result.toString().length()==1){
//							decimales = result.toString()+'0';
//						}
//					}
//				}
//				precio = String.valueOf(price.intValue())+decimales;
//			}else{
//				precio = precio +decimales;
//			}
//			//Articulos pasar a bit
//			
//			List<MProductAttribute> prodatts = p.getAttributes();
//			String[] retorno = new String[16] ;
//			if(0>=prodatts.size()){
//				System.out.println("NO TIENE ATRIBUTOSSSSS "+p.get_ID());
//				continue;
////				String sql1 = "select ad_sequence_id from ad_sequence where lower(name) like 'm_productattribute'";
////				
////				int seqID= DB.getSQLValueEx(null, sql1);
////				 String sql2="insert into m_productattribute (m_productattribute_id,ad_client_id,ad_org_id,isactive,created,createdby,"
////				 		+ "updated,updatedby,uy_prodattribute_id,m_product_id,isselected,seqno)"
////				 		+ "select nextID("+seqID+",'N'),prod.ad_client_id,prod.ad_org_id,'Y',now(),"+Env.getAD_User_ID(getCtx())+",now(),"+Env.getAD_User_ID(getCtx())+",a.uy_prodattribute_id,"
////				 		+ "prod.m_product_id,'N',(a.uy_prodattribute_id - 999999)*10 "
////				 		+ "from uy_prodattribute a, m_product prod"
////				 		+ " where m_product_id = "+ p.get_ID();
////				 int cant = DB.executeUpdateEx(sql2, null);
////				 if(0<cant){
////					 String sqlHexa ="SELECT Hexa FROM MoldeAtributosAux WHERE ValueP = '"+p.getValue()+"'";
////					 String m_hexa  = DB.getSQLValueString(null, sqlHexa);
////					 String sqlu="";
////					 if(m_hexa.equals("400C000100000000")){
////						 sqlu= "UPDATE M_ProductAttribute SET IsSelected = 'Y' WHERE M_Product_ID IN ("
////								 + "SELECT M_Product_ID FROM M_Product WHERE Value in("+
////											"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like '400C000100000000')"+//-0100000000001100000000000000000100000000000000000000000000000000
////									          ") AND UY_ProdAttribute_ID IN (1000001,1000010,1000011,1000025)";
////					 }else if (m_hexa.equals("0020000100000000")){
////						 sqlu = "UPDATE M_ProductAttribute SET IsSelected = 'Y'  WHERE M_Product_ID IN ("
////									+ "SELECT M_Product_ID FROM M_Product WHERE Value in("+
////									"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like '0020000100000000')"+ //--0000000000100000000000000000000100000000000000000000000000000000
////							          ") AND UY_ProdAttribute_ID IN (1000008,1000025)";                          //     --0000000000100000000000000000000100000000000000000000000000000000 - 76350 rows
////
////					 }else if (m_hexa.equals("C000000100000000")){
////						//C000000100000000   --1100000000000000000000000000000100000000000000000000000000000000 (1,2,32) 1000000,1000001,1000025
////						 sqlu = "UPDATE M_ProductAttribute SET IsSelected = 'Y'  WHERE M_Product_ID IN ("
////							+ "SELECT M_Product_ID FROM M_Product WHERE Value in("+
////									"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like 'C000000100000000')"+// --1100000000000000000000000000000100000000000000000000000000000000
////							        ") AND UY_ProdAttribute_ID IN (1000000,1000001,1000025)";                   //    --1100000000000000000000000000000100000000000000000000000000000000 - 3 rows
//// 
////					 }else if (m_hexa.equals("0020010100000000")){
////						 sqlu="UPDATE M_ProductAttribute SET IsSelected = 'Y'   WHERE M_Product_ID IN ("
////							+ "SELECT M_Product_ID FROM M_Product WHERE Value in("+
////									"SELECT Valuep FROM MoldeAtributosAux WHERE Hexa like '0020010100000000')"+//--0000000000100000000000010000000100000000000000000000000000000000 -54rows
////							          ") AND UY_ProdAttribute_ID IN (1000008,1000019,1000025)"; //
////					 }
////					 int cant1 = DB.executeUpdateEx(sqlu, null);	
////				 }
////				 prodatts = p.getAttributes(); 
//			}
//			int pos = 0;
//		//	for(int pos = 0; pos<12;){
//				String acumulo = "";
//				for(int i=0;i<prodatts.size();i++){
//					String aux = "";
////					if(i==6||i==8|| i==16||i==21||i==24||i==26||
////							i==31 || i==32 || i==33 || i==35 ||i==40 || i ==41 || i==42){
////						aux = "0";
////					}
//					MProductAttribute prod = prodatts.get(i);
//					MProdAttribute att = (MProdAttribute)prod.getUY_ProdAttribute();
//					if(att.getValue().equalsIgnoreCase("attr_1")||
//						att.getValue().equalsIgnoreCase("attr_2")||
//						att.getValue().equalsIgnoreCase("attr_3")||
//						att.getValue().equalsIgnoreCase("attr_4")){
//						if(retorno[0]==null){
//							retorno[0]="";
//						}
//						if(prod.isSelected()){
//							retorno[0] = retorno[0]+"1";
//						}else{
//							retorno[0] = retorno[0]+"0";
//						}
//						if(acumulo.length()==4){
//							pos++;
//							acumulo="";
//						}
//					}
//					else if(att.getValue().equalsIgnoreCase("attr_5")||
//							att.getValue().equalsIgnoreCase("attr_6")||
//							att.getValue().equalsIgnoreCase("attr_7")
//							){
//						if(retorno[1]==null){
//							retorno[1]="";
//						}
//							if(prod.isSelected()){
//								retorno[1]= retorno[1]+"1";
//							}else{
//								retorno[1]= retorno[1]+"0";
//							}
//							if(att.getValue().equalsIgnoreCase("attr_7")){
//								retorno[1]= retorno[1]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_9")||
//							att.getValue().equalsIgnoreCase("attr_11")||
//							att.getValue().equalsIgnoreCase("attr_12")
//							){
//						if(retorno[2]==null){
//							retorno[2]="";
//						}
//							if(prod.isSelected()){
//								retorno[2]= retorno[2]+"1";
//							}else{
//								retorno[2]= retorno[2]+"0";
//							}
//							if(att.getValue().equalsIgnoreCase("attr_9")){
//								retorno[2]= retorno[2]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//
//					else if(att.getValue().equalsIgnoreCase("attr_13")||
//							att.getValue().equalsIgnoreCase("attr_14")||
//							att.getValue().equalsIgnoreCase("attr_15")||
//							att.getValue().equalsIgnoreCase("attr_16")
//							){
//						if(retorno[3]==null){
//							retorno[3]="";
//						}
//							if(prod.isSelected()){
//								retorno[3] = retorno[3]+"1";
//							}else{
//								retorno[3] = retorno[3]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_17")||
//							att.getValue().equalsIgnoreCase("attr_19")||
//							att.getValue().equalsIgnoreCase("attr_20")
//							){
//						if(retorno[4]==null){
//							retorno[4]="";
//						}
//							if(prod.isSelected()){
//								retorno[4] = retorno[4]+"1";
//							}else{
//								retorno[4] = retorno[4]+"0";
//							}
//							if(att.getValue().equalsIgnoreCase("attr_17")){
//								retorno[4] = retorno[4]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_21")||
//							att.getValue().equalsIgnoreCase("attr_22")||
//							att.getValue().equalsIgnoreCase("attr_24")
//							){
//						if(retorno[5]==null){
//							retorno[5]="";
//						}
//							if(prod.isSelected()){
//								retorno[5] = retorno[5]+"1";
//							}else{
//								retorno[5] = retorno[5]+"0";
//							}
//							if(att.getValue().equalsIgnoreCase("attr_22")){
//								retorno[5] = retorno[5]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_25")||
//							att.getValue().equalsIgnoreCase("attr_27")		
//							){
//						if(retorno[6]==null){
//							retorno[6]="";
//						}
//							if(prod.isSelected()){
//								retorno[6] = retorno[6]+"1";
//							}else{
//								retorno[6] = retorno[6]+"0";
//							}
//							if(att.getValue().equalsIgnoreCase("attr_25")||att.getValue().equalsIgnoreCase("attr_27")){
//								retorno[6] = retorno[6]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_29")||
//							att.getValue().equalsIgnoreCase("attr_30")||
//							att.getValue().equalsIgnoreCase("attr_31")||
//							att.getValue().equalsIgnoreCase("attr_32")	
//							){
//						if(retorno[7]==null){
//							retorno[7]="";
//						}
//							if(prod.isSelected()){
//								retorno[7] = retorno[7]+"1";
//							}else{
//								retorno[7] = retorno[7]+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_36")
//							){
//						if(retorno[8]==null){
//							retorno[8]="";
//						}
//							if(prod.isSelected()){//33-34-35
//								retorno[8] = "000"+"1";
//							}else{
//								retorno[8] = "000"+"0";
//							}
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_38")||
//							att.getValue().equalsIgnoreCase("attr_39")||
//							att.getValue().equalsIgnoreCase("attr_40")
//							){
//						if(retorno[9]==null){
//							retorno[9]="";
//						}
//							if(att.getValue().equalsIgnoreCase("attr_38")){
//								retorno[9] = retorno[9]+"0";
//							}
//							if(prod.isSelected()){
//								retorno[9] = retorno[9]+"1";
//							}else{
//								retorno[9] = retorno[9]+"0";
//							}
//							
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_41")){
//						if(retorno[10]==null){
//							retorno[10]="";
//						}
//							if(prod.isSelected()){
//								retorno[10] = "1"+"000";
//							}else{
//								retorno[10] = "0"+"000";
//							}
//							
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//					else if(att.getValue().equalsIgnoreCase("attr_45")||
//							att.getValue().equalsIgnoreCase("attr_46")||
//							att.getValue().equalsIgnoreCase("attr_47")||
//							att.getValue().equalsIgnoreCase("attr_48")
//							){
//						if(retorno[11]==null){
//							retorno[11]="";
//						}
//							if(prod.isSelected()){
//								retorno[11] = retorno[11]+"1";
//							}else{
//								retorno[11] = retorno[11]+"0";
//							}
//							
//							if(acumulo.length()==4){
//								pos++;
//								acumulo="";
//							}
//						}
//
//				}
//		//	}
//			retorno[12] = "0000";retorno[13] = "0000";retorno[14] = "0000";retorno[15] = "0000";		
//
//			//Pasar a hexagesimal lista de atributos
//			String atributosHexa = bitsToHexa(retorno);
//			
//			//Cantidad por pack
//			String cantPack = String.valueOf(p.getUnitsPerPack());
//			//CodigoMedida por ahora 0
//			String codMedida = "0";
//			
//			lineToPrint = new StringBuilder();
//			lineToPrint.append("I:ARTICULOS:"+codigo+":"+desc+":"+codEntorno+":"+codSubFamilia+":"
//					+moneda+":"+codIva+":"+precio+":"+atributosHexa+":"+cantPack+":"+codMedida);
//			System.out.println(lineToPrint.toString());
//			writeFile(lineToPrint.toString(),fileMaster,true); //dirChannelB
//			
//		}
//	
	}

	/**
	 * Generar archivo maestro con articulos, tandem, articulos externos y clientes cc
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 9/10/2015
	 */
	private void generateMasterFile(){
		throw new AdempiereException("Version desactualizada");
//		String sql = "";
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//
//			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
//			
//			// Obtengo lista de precios predeterminada de ventas
//			MPriceList list = MPriceList.getDefault(getCtx(), true);
//
//			if ((list == null) || (list.get_ID() <= 0)){
//				throw new AdempiereException("No se pudo obtener Lista de Precios de Venta Predeterminada");
//			}
//
//			// Obtengo ultima version activa segun fecha de vigencia
//			sql = " select max(m_pricelist_version_id) " +
//			      " from m_pricelist_version" +
//				  " where m_pricelist_id = " + list.get_ID() +
//				  " and isactive = 'Y'" +
//				  " and validfrom <= '" + today + "'";
//			
//			int versionID = DB.getSQLValueEx(get_TrxName(), sql);
//			
//			if (versionID <= 0){
//				throw new AdempiereException("No se pudo obtener Version Vigente de Lista de Precios de Venta Predeterminada");
//			}
//			//Para openUP
//			File fileMaster = new File(getRutaOrigen()+File.separator+"bo_batch.all");
//			
//			sql = " select prod.m_product_id, prod.value, prod.name, coalesce(taxc.commoditycode, '0') as commoditycode, "
//				+ " coalesce(pp.pricelist, 0)::numeric(14,2) as pricelist,"
//				+ " prod.versionno as attrs, prod.UnitsPerPack "
//				+ " from m_product prod "
//				+ " left outer join m_productprice pp on (prod.m_product_id = pp.m_product_id and pp.m_pricelist_version_id =" + versionID + ") " 				
//				+ " left outer join c_taxcategory taxc on prod.c_taxcategory_id = taxc.c_taxcategory_id "
//				+ " where prod.isactive = 'Y' "
//				+ " and prod.issold = 'Y' "
//				+ " and prod.value != 'NO DEFINIDO' ";
//				
//			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
//			
//			rs = pstmt.executeQuery();
//
//			rs.last();
//			int totalRowCount = rs.getRow(), rowCount = 0;
//			rs.beforeFirst();
//			System.out.println("LOG-Inicio carga ARTICULOS");
//
//			while (rs.next()) {		
//				//System.out.println("Procesando linea " + rowCount++ + " de " + totalRowCount + " - " + rs.getInt("m_product_id"));
//				StringBuilder lineToPrint = null; //
//				String codigo = rs.getString("value").trim();//1
//				String desc = rs.getString("name").trim().replace(":", "_");//2
//				String codEntorno = "0";//3 por ahora 0
//				String codSubFamilia = "0";//4 por ahora 0
//				 
//				String moneda = "1";//5 --por defecto 1 pesos codigo sisteco --				
//				String codIva=rs.getString("commoditycode");//6			
//				String decimales = "00";
//				String precio="0";//7					
//
//				BigDecimal price = rs.getBigDecimal("pricelist");
//				if (price == null) price = Env.ZERO;
//
//				BigDecimal d = (price);
//				BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
//				
//				if (result != null){
//					
//					if(result.compareTo(Env.ZERO) != 0){
//
//						if(result.toString().length() >= 3){
//							decimales = result.toString().substring(0, 2);
//						}
//						else if(result.toString().length() == 2){
//							decimales = result.toString();
//						}
//						else if(result.toString().length() == 1){
//							decimales = result.toString()+'0';
//						}
//					}
//				}
//				precio = String.valueOf(price.intValue()) + decimales;
//
//				//INI Obtener string con articulos para cada producto - SBouissa 02-10-2015 
//				//Atributos
//				String atributosHexa = rs.getString("attrs");
//				//Cantidad por pack
//				String cantPack = rs.getString("UnitsPerPack");
//				//FIN Obtener string con articulos para cada producto - SBouissa 02-10-2015 
//				
//				//CodigoMedida por ahora 0
//				String codMedida = "0";
//				
//				lineToPrint = new StringBuilder();
//				lineToPrint.append("I:ARTICULOS:"+codigo+":"+desc+":"+codEntorno+":"+codSubFamilia+":"
//						+moneda+":"+codIva+":"+precio+":"+atributosHexa+":"+cantPack+":"+codMedida);
//				//System.out.println(lineToPrint.toString());
//				writeFile(lineToPrint.toString(),fileMaster,true); //dirChannelB
//				
//			}
//			//Isert de relacion tandem
//			loadTandem(fileMaster);
//			//Insert de relacion codigo de barras
//			loadArticulosExternos(fileMaster);
//			//Insert Clientes cuenta corriente OpenUp Ltda SBouissa Issue # ---- 08-10-2015 Insert clientes cta cte
//			loadClientsCC(fileMaster);
//			//Se cuentan las lineas y se copia archivo al destino de sisteco
//			int linesBatch = counLines(fileMaster);
//			if(0<linesBatch){
//				writeFile(String.valueOf(linesBatch),fCountBatch,false);
//				//Directorio de sisteco a dejar cada archivo
//				File destino = null;
//				destino = new File (getRutaDestinoSisteco()+File.separator+"bo_batch.all");
//				File destCount = null;
//				destCount = new File (getRutaDestinoSisteco()+File.separator+"bo_batch.afl");
//
//				try{				
//					copyFile(fileMaster, destino);
//					copyFile(fCountBatch,destCount);
//					// Dejo los archivos con fecha y hora mas el nombre
//					String[] hra = (new Timestamp (System.currentTimeMillis()).toString().split(":"));
//					String fecha =hra[0].replace("-", "").replace(" ", "_")+hra[1];
//					File fhist = new File(getRutaOrigen()+File.separator+fecha+"_bo_batch.all");
//					File fhistC = new File(getRutaOrigen()+File.separator+fecha+"_bo_batch.afl");
//					copyFile(fileMaster, fhist);
//					copyFile(fCountBatch,fhistC);
//				}catch(Exception e){
//					throw new AdempiereException(e.getMessage());
//				}
//			}
//			
//		} 
//		
//		catch (Exception e) {
//			throw new AdempiereException(e.getMessage());
//		}
//		finally{
//			DB.close(rs, pstmt);
//			rs = null;
//			pstmt = null;
//		}
		
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
			sql = "SELECT bp.c_bpartner_id as bpID, bp.value as cod, bp.name2 as name, bp.DUNS as ruc"
					+ " FROM C_BPartner bp WHERE bp.IsActive = 'Y' AND bp.IsCustomer = 'Y' "
					+" ORDER BY bp.value" ;
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			System.out.println("LOG-Inicio carga CLIENTESCUENTACORRIENTE");

			while (rs.next()) {
				int m_bpartnerID = 0;
				m_bpartnerID = rs.getInt("bpID");
				StringBuilder lineToPrint = null; //
				String inicio = "I:"+C+":";//
				String codCte = rs.getString("cod");//CodigoCliente
				String nameCte = rs.getString("name");//NombreCliente
				String rucCte = rs.getString("ruc");//RUC
				
				String dirCte = "";	
				String codPtalCte = "";
				String telCte = "";
				String ciudadCte =  "";
				String deptoCte =  "";
				MBPartnerLocation[] bpl = MBPartnerLocation.getForBPartner(getCtx(), m_bpartnerID, null);
				if(null!=bpl && bpl.length>0){
					
					dirCte = ((bpl[0].getAddress1()!=null)?bpl[0].getAddress1():"");
					codPtalCte = ((bpl[0].getPostal()!=null)?bpl[0].getPostal():"");
					telCte = ((bpl[0].getPhone()!=null)?bpl[0].getPhone():"");
					 MLocalidades loc = (MLocalidades) bpl[0].getUY_Localidades();
					 if(null!=loc){
						 ciudadCte = ((loc.getName()!=null)?loc.getName():"");//Ciudad
					 }
					 MDepartamentos dep = (MDepartamentos) bpl[0].getUY_Departamentos();
					 if(null!= dep){
						 deptoCte = ((dep.getName()!=null)?dep.getName():"");//Departamento
					 }
				}
				
				String codTipoCte = ""; //CodigoTipoCliente
				String attr1 = "1";String attr2 = "1";String attr3 = "1";
				String [] retorno = new String[4];
				retorno[0] = attr1+attr2+attr3+"0";
				retorno[1] = "0000";	
				retorno[2] = "0000";	
				retorno[3] = "0000";	
				String datoHexa = bitsToHexa(retorno);
				
				lineToPrint = new StringBuilder();
				lineToPrint.append(inicio+codCte.trim().replace(":", "_")+":"+nameCte.trim().replace(":", "_")
						+":"+dirCte.trim().replace(":", "_")+":"+ciudadCte.trim().replace(":", "_")+":"
						+deptoCte.trim().replace(":", "_")+":"+codPtalCte+":"+telCte+":"+rucCte+":"+codTipoCte+":"+datoHexa);
				//System.out.println(lineToPrint.toString());
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

	private void loadTandem(File fileMaster){
		//Linea para obtener los inserts de tandem: (esportar sin columnas, sein comillas y sin serarar postgresql)
		//select 'I:TANDEM:',a.value,':',b.value from m_product a inner join m_product b on (a.m_product_tandem_id=b.m_product_id) order by a.value
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			sql = "SELECT a.value as art,b.value as tandem from m_product a inner join m_product b on "
					+ "(a.m_product_tandem_id=b.m_product_id) order by a.value ";
					
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				System.out.println("LOG-Inicio carga I:TANDEM");

				while (rs.next()) {
					StringBuilder lineToPrint = null; //
					String inicio = "I:TANDEM:";//1
					String val = rs.getString("art");//2
					String val2 = rs.getString("tandem");//3 por ahora 0
	
					lineToPrint = new StringBuilder();
					lineToPrint.append(inicio+val+":"+val2);
					//System.out.println(lineToPrint.toString());
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
					+ " on (a.m_product_id=b.m_product_id) order by b.value";
					
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			System.out.println("LOG-Inicio carga I:ARTICULOS_EQUIVALENTES");
			while (rs.next()) {
				
				StringBuilder lineToPrint = null; //
				String inicio = "I:ARTICULOS_EQUIVALENTES:";//1
				String val = rs.getString("upc");//2
				String val2 = rs.getString("art");//3 por ahora 0

				lineToPrint = new StringBuilder();
				lineToPrint.append(inicio+val+":"+val2);
				//System.out.println(lineToPrint.toString());
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
			fo = new File(getRutaOrigen() + File.separator + "bo_batch.afl");
			
		} catch (Exception e) {
			throw new AdempiereException("No se pudo crear archivo historico.");
		}
		
		return fo;
	}

}

/**
 * 
 */
package org.openup.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CalloutBPartnerLocation;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCurrency;
import org.compiere.model.MLocation;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTaxCategory;
import org.compiere.process.SvrProcess;
import org.compiere.sqlj.BPartner;
import org.compiere.util.DB;
import org.openup.model.MDepartamentos;
import org.openup.model.MLocalidades;
import org.openup.model.MProdAttribute;
import org.openup.model.MProductAttribute;
import org.openup.model.MRTInterfaceBP;
//import org.omg.CORBA.PRIVATE_MEMBER;
import org.openup.model.MRTInterfaceProd;

import test.functional.MRuleTest;
//import org.python.antlr.ast.Continue;

//import sun.jkernel.Bundle;

/**OpenUp Ltda Issue# 4408
 * Proceso para generar archivo batch y online que permite para creaciï¿½n, actualizaciï¿½n y 
 * eliminacion de clientes y productos en sistema sisteco
 * @author SBouissa 12/6/2015
 *
 */
public class PRTInterfaceBPProd extends SvrProcess{
	private String esMaster ="";
	//ï¿½	I - Insertar
	//ï¿½	U - Actualizar 
	//ï¿½	D - Borrar
	//ï¿½	A ï¿½ Insertar/Actualizar
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
	
	private static final String PATH = "C:"+File.separator+"FilesCovadonga"+File.separator;
	
	private File fBatch = null;
	private File fOnline = null;
	private File fCountBatch = null;
	private File fCountOnline = null;
	
	private	List<MRTInterfaceProd> products = null; //--> Modelo para consultar tabla correspondiente
	
	//private	UY_RT_InterfaceCte clients = null;
	//variables para bloquear directorio y mantener integridad 06-07-2015
	private FileChannel dirChannelB;
	private FileChannel dirChannelO;
	private FileLock lockO;
	private FileLock lockB;
	
	//SBouissa Issue #4857
	private List<MRTInterfaceBP> clientes = null; // Consultar !!!
	//Variable para identificar el sistema que esta corriendo el proceso
	private Boolean onWindows = true;
	
	/**
	 * Constructor
	 */
	public PRTInterfaceBPProd() {
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// OJO SOLO PARA MASTER 
		//esMaster = "master";
		onWindows = true;
		try{
			String a = System.getProperty("os.name" );
			if(a.contains("Linux")){
				onWindows = false;
				System.out.println(System.getProperty("os.name" ));
			} 
		
			fchToday = getDateString();
			//crear archivo batch
			fBatch = createBatchFile();
			//crear archivo online
			fOnline = creatOnlineFile();
			//crear archivo contador batch
			fCountBatch = createCountBatchFile();
			//crear archivo contador online
			fCountOnline = creatCountOnlineFile();
//			bloquearArchivoBatch(fBatch);
//			bloquearArchivoOnline(fOnline);
		}catch(Exception e){
			throw new AdempiereException("Error prepate"+e.getMessage());
		}
			
	}	

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 6/7/2015
	 */
//	private void bloquearArchivoBatch(File in) {
//		// TODO Auto-generated method stub
//		//File dirPrincipal = new File(PATHBATCH);
//		try {
//			dirChannelB = new RandomAccessFile(in, "rw").getChannel();
//			lockB = dirChannelB.lock();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	private void bloquearArchivoOnline(File in) {
//		// TODO Auto-generated method stub
//		//File dirPrincipal = new File(PATHBATCH);
//		try {
//			dirChannelO = new RandomAccessFile(in, "rw").getChannel();
//			lockO = dirChannelO.lock();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	private void desbloquearArchivos(){
//		try {
//			lockO.release();
//			lockB.release();
//			dirChannelO.close();
//			dirChannelB.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 19/6/2015
	 * @return
	 */
	private String getDateString() {
		String[] fecha = new Timestamp (System.currentTimeMillis()).toString().split("-");
		//Formato fecha YYYYMMdd
		String fechaArch = fecha[0]+fecha[1]+fecha[2].substring(0, 2);
		return fechaArch;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {			
		
		int countLines = 0;
		if(null!=fBatch && null!= fOnline){
			//consultar tabla de insersion atualizacion
			products = MRTInterfaceProd.forProductsNotR(getCtx(),null);
			//Obtengo datos de los clientes a actualizar
			clientes = MRTInterfaceBP.lstCustomersNotR(getCtx(), null);
			if(null!=products||null!=clientes){
				if(null!=products){
					for (MRTInterfaceProd row : products){				
						//por c/registro
						StringBuilder lineToPrint=null;
						lineToPrint = processData(row); // Se obtiene una linea para ser impresa en cada archivo
						if(null!=lineToPrint){
							//desbloquearArchivos();
							writeFile(lineToPrint.toString(),fBatch,true); //dirChannelB
							writeFile(lineToPrint.toString(),fOnline,true);//dirChannelO
							String where = " WHERE "+MRTInterfaceProd.COLUMNNAME_UY_RT_InterfaceProd_ID +"="+row.get_ID();
							String sql = "UPDATE "+MRTInterfaceProd.Table_Name+" SET "
										+MRTInterfaceProd.COLUMNNAME_ReadingDate+ " = '"+ fchToday.toString()+"'";//" = (SELECT CURRENT_DATE) ";
							DB.executeUpdateEx(sql+where,get_TrxName()); 
							countLines = countLines+1;
//							bloquearArchivoBatch(fBatch);
//							bloquearArchivoOnline(fOnline);
						}else{
							continue;
						}	
					}
				}
				
				if(null!=clientes){
					for (MRTInterfaceBP rowBP : clientes){					
						StringBuilder lineToPrintBP=null;
						lineToPrintBP = processDataClient(rowBP); // Se obtiene una linea para ser impresa en cada archivo
						if(null!=lineToPrintBP){
							//desbloquearArchivos();
							writeFile(lineToPrintBP.toString(),fBatch,true); //dirChannelB
							writeFile(lineToPrintBP.toString(),fOnline,true);//dirChannelO
							String where = " WHERE "+MRTInterfaceBP.COLUMNNAME_UY_RT_InterfaceBP_ID +"="+rowBP.get_ID();
							String sql = "UPDATE "+MRTInterfaceBP.Table_Name+" SET "
											+MRTInterfaceBP.COLUMNNAME_ReadingDate+ " = '"+ fchToday.toString()+"'";//" = (SELECT CURRENT_DATE) ";
							DB.executeUpdateEx(sql+where,get_TrxName()); 
							countLines = countLines+1;
//							bloquearArchivoBatch(fBatch);
//							bloquearArchivoOnline(fOnline);
						}else{
							continue;
						}	
					}
				}
				
				//desbloquearArchivos();
				//Se cuentabilizan la cantidad de linea de cada archivo
				int linesBatch = counLines(fBatch);
				int linesOnli = counLines(fOnline);
				if(0<linesBatch && 0<linesOnli){//si se procesaron mas de una linea en ambos archivos 
					writeFile(String.valueOf(linesBatch),fCountBatch,false);
					writeFile(String.valueOf(linesOnli),fCountOnline,false);
					//Copiar archivos al directorio correspondiente del servidor de SISTECO
					if (copyFilesToSisteco()){			
						return "OK..! (Procesadas "+linesBatch+" lineas)";
					}else {
						return "ERROR AL COPIAR ARCHIVOS EN DESTINO";
					}
				}else return "NO HAY DATOS PARA PROCESAR..!!";
			}else{
				//desbloquearArchivos();
				return "NO HAY DATOS PARA PROCESAR..!!";
			}
			
			
		}else return "NO EXISTEN LOS ARCHIVOS";
		
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

	/**Crea la linea segun accion, tabla, y datos correspondiente ver doc Mantenimiento Pazos Covadonga 
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @return
	 */
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
			
			MProduct p = (MProduct) row.getM_Product();
			//Consulto si el producto es "vendible" si no es continúo OpenUp SBT 05-10-2015 !!
			if(null!=p && !p.isSold()){
				return lineIn;
			}
			//Cargo datos segun tabla, y segun 
			if(table.equals(PRTInterfaceBPProd.A)){
				datosLinea = getDataToRowArticulo(row,accion);
				System.out.println("Proceso ARTICULO");
			}else if(table.equals(PRTInterfaceBPProd.AE)){
				datosLinea = getDataToRowArticuloEquivalente(row,accion);
				System.out.println("Proceso ARTICULO_EQUIVALENTE");
			}else if(table.equals(PRTInterfaceBPProd.T)){
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
	
	private StringBuilder processDataClient(MRTInterfaceBP row) {
		StringBuilder lineIn = null;
		try{	
			String accion = ""; // I,U,D A
			//Accion que corresponde en dicha linea para los arch de sisteco
			accion = row.getUY_RT_Action().getcodigosisteco();
			//Indico la tabla CLIENTE CUENTA CORREINTE			
			String table = getTableNameSisteco("C");
			StringBuilder datosLinea = null;
			//Cargo datos segun tabla, y segun 
			
			datosLinea = getDataToRowCliente(row,accion);
			//System.out.println(datosLinea);
			if(null!=datosLinea){
				lineIn = new StringBuilder();
				//Accion
				lineIn.append(accion); 
				lineIn.append(SEPARATOR_L);
				//Tabla
				lineIn.append(table);
				lineIn.append(SEPARATOR_L); 
				//Datos
				lineIn.append(datosLinea);
			}
			
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
		return lineIn;
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 1/10/2015
	 * @param row
	 * @param accion
	 * @return
	 */
	private StringBuilder getDataToRowCliente(MRTInterfaceBP row, String accion) {
		//inserto linea correspondiente (se debe calcular hxagesimal)
		StringBuilder lineIn = null;
		try {
			lineIn = new StringBuilder();
			MBPartner bp = null;
			MBPartnerLocation bpl=null;
			if (PRTInterfaceBPProd.INSERTAR.equals(accion) || PRTInterfaceBPProd.ACTUALIZAR.equals(accion)) {
				if (0 < row.getC_BPartner_ID()) {
					bp = (MBPartner) row.getC_BPartner();
					if(0<bp.get_ID()){ 
						//CodigoCliente - 1
						lineIn.append(bp.getValue());
						lineIn.append(SEPARATOR_L);
						//NombreCliente - 2
						lineIn.append(bp.getName2().replace(":", "_"));
						lineIn.append(SEPARATOR_L);
			//Si el campo de cbpartner location no es null seteo los valores correspondientes
						if(0<row.getC_BPartner_Location_ID()){
							bpl = new MBPartnerLocation(getCtx(),row.getC_BPartner_Location_ID(),null);
							
							//DireccionCliente - 3
							lineIn.append(((bpl.getAddress1()!=null)?bpl.getAddress1().replace(":", "_"):""));
							lineIn.append(SEPARATOR_L);
							
							//Ciudad - 4
							MLocalidades loc = (MLocalidades) bpl.getUY_Localidades();
							if(null!=loc){
								lineIn.append(loc.getName().replace(":", "_"));
								lineIn.append(SEPARATOR_L);
							}
							//Departamento - 5
							MDepartamentos depto = (MDepartamentos) bpl.getUY_Departamentos();
							if(null!=depto){
								lineIn.append(depto.getName().replace(":", "_"));
								lineIn.append(SEPARATOR_L);
							}
							//Codigo Postal - 6
							lineIn.append(((bpl.getPostal()!=null)?bpl.getPostal():""));
							lineIn.append(SEPARATOR_L);

							//Telefono - 7
							lineIn.append(((bpl.getPhone()!=null)?bpl.getPhone().replace(":", "-"):""));
							lineIn.append(SEPARATOR_L);
							
						}else{// Si no tiene asociado una localidad los campos direccion,ciudad,departamento,
							//codigo postal y telefono se colocan vaios ::::::: 
							lineIn.append(SEPARATOR_L);//Fin direccion
							lineIn.append(SEPARATOR_L);//fin ciudad
							lineIn.append(SEPARATOR_L);//fin departamento
							lineIn.append(SEPARATOR_L);//fin codigo postal
							lineIn.append(SEPARATOR_L);//fin telefono
						}
						
						//RUC - 8
						lineIn.append(bp.getDUNS());
						lineIn.append(SEPARATOR_L);
						//CodigoTipoCliente - 9 
						lineIn.append(SEPARATOR_L);// por el momento este codigo es vacío tiene relacion con promociones
						//Atributos hexagesimal 0000
						String [] retorno = null;
						retorno = new String[4];
						retorno[0] = String.valueOf(row.getattr_1()) + String.valueOf(row.getattr_2())
										+String.valueOf(row.getattr_3())+"0";
						retorno[1] = "0000";	
						retorno[2] = "0000";	
						retorno[3] = "0000";	
						
						String datoHexa = bitsToHexa(retorno);
						lineIn.append(datoHexa);
						lineIn.append(SEPARATOR_L);

					}

				}//FIN
			} else if (PRTInterfaceBPProd.ELIMINAR.equals(accion)) {
				if (null != row.getbpcode()) {
					lineIn.append(row.getbpcode());// CodigoArticulo
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			if(PRTInterfaceBPProd.INSERTAR.equals(accion) || PRTInterfaceBPProd.ACTUALIZAR.equals(accion)){
				
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());lineIn.append(SEPARATOR_L); //CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());;lineIn.append(SEPARATOR_L);
				}
				//02-07-2015 sbouissa se actualiza linea 
				lineIn.append(row.getM_Product_Tandem().getValue()); //CodigoArticuloTandem (codigobarra)
				
			}else if(PRTInterfaceBPProd.ELIMINAR.equals(accion)){
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
			if(PRTInterfaceBPProd.INSERTAR.equals(accion) || PRTInterfaceBPProd.ACTUALIZAR.equals(accion)){
				lineIn.append(row.getUPC());lineIn.append(SEPARATOR_L); //CodigoArticuloExterno (codigobarra)
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode()); //CodigoArticuloInterno
				}else{
					lineIn.append(row.getM_Product().getValue());
				}
					
			}else if(PRTInterfaceBPProd.ELIMINAR.equals(accion)){
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
			if(PRTInterfaceBPProd.INSERTAR.equals(accion) || PRTInterfaceBPProd.ACTUALIZAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());lineIn.append(SEPARATOR_L);//CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());lineIn.append(SEPARATOR_L);
				}
				 String nameAux = row.getDescription().replace(":","_");//24-09-2015 Se ccontrola nombre si contiene separador de linea sisteco
				lineIn.append(nameAux);lineIn.append(SEPARATOR_L);//Descripcion
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
				String decimales = "00";
				String precio="0";//7
				
				//OpenUp SBouissa 19-10-2015 Issue#
				//Se consulta el precio desde la lista actual
				//Lista de precio de venta
				MPriceList list = MPriceList.getDefault(getCtx(), true,row.getC_Currency_ID());
				int adOrgID = DB.getSQLValue(null, "SELECT MIN(AD_Org_ID) FROM AD_Org WHERE isActive = 'Y' AND AD_Org_ID <> 0");
				MProductPrice prodPrice = MProductPrice.forVersionProduct(getCtx(), list.getVersionVigente(null).get_ID(), row.getM_Product_ID(), adOrgID, get_TrxName());
				//BigDecimal price = row.getPriceList(); --> Ya no se obtiene el precio de esta forma 19-10-2015
				BigDecimal price = null;
				if(null!=prodPrice){
					price = prodPrice.getPriceList();
				}
				if(null!=price){
					BigDecimal d = (price);
					BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
					//System.out.println(result);
					if(null!=result){
						if(!result.equals(BigDecimal.ZERO)){
							decimales = result.toString().substring(0, 2);
						}
					}
					precio = String.valueOf(price.intValue())+decimales;
				}else{
					precio = precio +decimales;
				}
				lineIn.append(precio);lineIn.append(SEPARATOR_L);		//PrecioArticulo
				//--> Obtencion de los 48 bits 
				String[] listaAtributos = getAtrubutesBitString(row); 
				//Pasar a hexagesimal lista de atributos
				String atributosHexa = bitsToHexa(listaAtributos).toUpperCase();
				if(("").equals(atributosHexa)){
					return null;
				}else if(16!=atributosHexa.length()) {
					return null; // falto agregar antes..10-07-2015
				}
				lineIn.append(atributosHexa).append(SEPARATOR_L);
				lineIn.append(String.valueOf(row.getUnitsPerPack()));lineIn.append(SEPARATOR_L);		//Cantidad Pack
				lineIn.append("0"); //Codigo Medida (por ahora 0)
	
			}else if(PRTInterfaceBPProd.ELIMINAR.equals(accion)){
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
				return PRTInterfaceBPProd.A;
			}else if("AE".equals(codeTable)){
				return PRTInterfaceBPProd.AE;
			}else if("T".equals(codeTable)){
				return PRTInterfaceBPProd.T;
			}else if("C".equals(codeTable)){
				return PRTInterfaceBPProd.C;
			}
		}
		return "";
	}

	/**Metodo que se encarga de copiar los archivos creados al directorio del servidor de sisteco
	 * /home/pazos/servidor/Gestion/Entrada --> compartido por smb con la etiquieta mantenimiento
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 19/6/2015
	 * @return
	 */
	private boolean copyFilesToSisteco() {
		 String nameOnline = "bo_online.txt";
		 String cantOnline = "bo_online.tfl";		
		 String nameBatch = "bo_batch.txt";
		 String cantBatch = "bo_batch.tfl";
		 boolean fileFound = false;
			File origen = null; File destino = null;
			try{	
				
				destino = new File (getRutaDestinoSisteco());//UY_DESTINATION_DIRECTORY_RT_ACT
				origen = new File(getRutaOrigen());  //origen donde se creo
				String start = fchToday+"bo_"; ////////////////////////////////////////// ojo tiene que tener solo el nomnde del arch
				String[] files = origen.list();
				for (int i = 0; i < files.length; i++) {
					if (files[i].startsWith(start)){
						//se obtiene separador configurado en system config (win\\, en linux /)12_05_2015
						File source = new File(origen + File.separator + files[i]);
						File dest = null;
						if(files[i].startsWith(fchToday+nameOnline)){
							dest = new File(destino + File.separator + nameOnline);				
						}else if(files[i].startsWith(fchToday+nameBatch)){
							dest = new File(destino + File.separator + nameBatch);
						}else if(files[i].startsWith(fchToday+cantOnline)){
							dest = new File(destino + File.separator + cantOnline);
						}else{
							dest = new File(destino + File.separator + cantBatch);
						}
						copyFile(source, dest);
						source = null;
						dest = null;
						fileFound = true;
					
					}
				}

			}catch(Exception e){
				throw new AdempiereException(e.getMessage());
			}
			finally{
				origen = null;
				destino = null;
			}
			return fileFound;
	}

	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 19/6/2015
	 * @return
	 */
	private String getRutaOrigen() {
		//return ("C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"); //C:\FilesCovadonga\DestinoSisteco
		// TODO Auto-generated method stub
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_DIRECTORY_RT_MANT",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_DIRECTORY_RT_MANT",0); //Directorio origen prametrizado para linux
		}
		return origen;
		//return "C:"+File.separator+"FilesCovadonga";
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
		System.out.println(hexString);
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
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 * @return
	 */
	private File createBatchFile() {
		File fb=null;
		fb = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProd.NAME_BATCH);
		return fb;
	}

	/**
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 */
	private File creatOnlineFile() {
		// TODO Auto-generated method stub
		//File fo = new File(PRTInterfaceBPProd.PATHONLINE+fchToday+PRTInterfaceBPProd.NAME_ONLINE);
		File fo = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProd.NAME_ONLINE);
		return fo;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 26/6/2015
	 * @return
	 */
	private File creatCountOnlineFile() {
		// TODO Auto-generated method stub
		File fo = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProd.NAME_COUNTONLINE);
		return fo;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 26/6/2015
	 * @return
	 */
	private File createCountBatchFile() {
		// TODO Auto-generated method stub
		File fo = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProd.NAME_COUNTBATCH);
		return fo;
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
//		return File.separator+"srv"+File.separator+"TempSisteco";
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"); //C:\FilesCovadonga\DestinoSisteco
	}
	
	
	
//	public void crearCargaMaestro(){
//		List<MProduct> mpLst = MProduct.forProductsNotR(getCtx(),null);
//		File fileMaster = new File(PRTInterfaceBPProd.PATHBATCH+fchToday+"maestro.txt");
//		
//		for(MProduct p : mpLst){
//			StringBuilder lineToPrint = null; //
//			String codigo = p.getValue();//1
//			String desc = p.getName();//2
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
//			
//			String precio="0";//7					
//			BigDecimal price = p.getSalePrice();
//			if(null!=price){
//				precio = price.toString();
//			}
//			//Articulos pasar a bit
//			
//			List<MProductAttribute> prodatts = p.getAttributes();
//			String[] retorno = new String[16] ;
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
//			lineToPrint.append("I:ARTICULOS"+codigo+":"+desc+":"+codEntorno+":"+codSubFamilia+":"
//					+moneda+":"+codIva+":"+precio+":"+atributosHexa+":"+cantPack+":"+codMedida);
//			
//			writeFile(lineToPrint.toString(),fileMaster,true); //dirChannelB
//			
//		}
//	
//	}
	
}

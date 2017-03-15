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
import java.nio.channels.FileLock;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCountry;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MSysConfig;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MDepartamentos;
import org.openup.model.MLocalidades;
import org.openup.model.MRTInterfaceBP;
import org.openup.model.MRTInterfaceProd;

/**OpenUp Ltda Issue #4976
 * @author SBT 20/11/2015
 *
 */
public class PRTInterfaceBPProdCli extends SvrProcess{
	
	private String fchToday;
	private static final String INSERTAR = "I";//	I - Insertar
	private static final String ACTUALIZAR = "U";//	U - Actualizar 
	private static final String ELIMINAR = "D";//	D - Borrar
	//	A - Insertar/Actualizar
		
	//Tablas a impactar en sisteco 
	private static final String A  = "ARTICULOS";
	private static final String AE = "ARTICULOS_EQUIVALENTES";
	private static final String T  = "TANDEM";
	private static final String C  = "CLIENTES";
	
	//Variables
	private static final String SEPARATOR_L = ":";//caracter separador de las lineas de los archivos a escribir
	//Testing maquina sbt

	private static final String NAME_BATCH = "bo_batch.txt";//"c:\temp\lineadecodigo\fichero.txt"
	private static final String NAME_ONLINE = "bo_online.txt";
	
	private static final String NAME_COUNTBATCH = "bo_batch.tfl";//"c:\temp\lineadecodigo\fichero.txt"
	private static final String NAME_COUNTONLINE = "bo_online.tfl";
	
	private File fBatch = null;
	private File fOnline = null;
	private File fCountBatch = null;
	private File fCountOnline = null;
	//OpenUp SBT 23/11/2015 Issue #4976
	//Archivo nuevo para guardar lineas con errores que no se agregan al archivo enviado a sisteco
	private File fBatchError = null; 
	private static final String NAME_BATCH_ERROR = "bo_batcherror.txt";
	
	private	List<MRTInterfaceProd> products = null; //--> Modelo para consultar tabla correspondiente
	
	//SBouissa Issue #4857
	private List<MRTInterfaceBP> clientes = null; // Consultar !!!
	//Variable para identificar el sistema que esta corriendo el proceso
	private Boolean onWindows = true;
	private String fchTodayUpdate = "";
	private int linesBatch = 0;
	private int linesOnli = 0;
	/**
	 * Constructor
	 */
	public PRTInterfaceBPProdCli() {
		
	}

	@Override
	protected void prepare() {

		linesBatch = 0;
		linesOnli = 0;
		onWindows = true;
		try{
			String a = System.getProperty("os.name" );
			if(a.contains("Linux")){
				onWindows = false;
				System.out.println(System.getProperty("os.name" ));
			} 
		
			String[] hra = (new Timestamp (System.currentTimeMillis()).toString().split(SEPARATOR_L));
			String fecha =hra[0].replace("-", "").replace(" ", "_")+hra[1];
			fchTodayUpdate = getDateString();
			fchToday = fecha;
			//crear archivo batch
			fBatch = createBatchFile();
			//crear archivo online
			fOnline = creatOnlineFile();
			//crear archivo contador batch
			fCountBatch = createCountBatchFile();
			//crear archivo contador online
			fCountOnline = creatCountOnlineFile();
			
			//23/11/2015 Issue #4976
			fBatchError = createBatchErrorFile();

		}catch(Exception e){
			throw new AdempiereException("Error prepate"+e.getMessage());
		}
			
	}	

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
					//for (int i = 0;i<10869; i++){
						//MRTInterfaceProd row = products.get(i);
						//por c/registro				
						System.out.println(countLines);
						StringBuilder lineToPrint=null;
						
						lineToPrint = processData(row); // Se obtiene una linea para ser impresa en cada archivo
						if(null!=lineToPrint){
							writeFile(lineToPrint.toString(),fBatch,true); //dirChannelB
							writeFile(lineToPrint.toString(),fOnline,true);//dirChannelO
							
							String where = " WHERE "+MRTInterfaceProd.COLUMNNAME_UY_RT_InterfaceProd_ID +"="+row.get_ID();
							String sql = "UPDATE "+MRTInterfaceProd.Table_Name+" SET "
										+MRTInterfaceProd.COLUMNNAME_ReadingDate+ " = '"+ fchTodayUpdate.toString()+"'";//" = (SELECT CURRENT_DATE) ";
							sql = sql +", "+MRTInterfaceProd.COLUMNNAME_Updated+" = (SELECT now()) " ;
							if((MSysConfig.getValue("UY_TEST_OPUP",0).equals("N"))){
								DB.executeUpdateEx(sql+where,get_TrxName());
							} 
							countLines = countLines+1;
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
							writeFile(lineToPrintBP.toString(),fBatch,true); //dirChannelB
							//writeFile(lineToPrintBP.toString(),fOnline,true);//dirChannelO
							String where = " WHERE "+MRTInterfaceBP.COLUMNNAME_UY_RT_InterfaceBP_ID +"="+rowBP.get_ID();
							String sql = "UPDATE "+MRTInterfaceBP.Table_Name+" SET "
											+MRTInterfaceBP.COLUMNNAME_ReadingDate+ " = '"+ fchTodayUpdate.toString()+"'";//" = (SELECT CURRENT_DATE) ";
							sql = sql +", "+MRTInterfaceProd.COLUMNNAME_Updated+" = (SELECT now()) " ;
							if((MSysConfig.getValue("UY_TEST_OPUP",0).equals("N"))){
								DB.executeUpdateEx(sql+where,get_TrxName()); 
							}
							countLines = countLines+1;
						}else{
							continue;
						}	
					}
				}
				
				//Se cuentabilizan la cantidad de linea de cada archivo
				linesBatch = counLines(fBatch);
				linesOnli = counLines(fOnline);
				if(0<linesBatch || 0<linesOnli){//si se procesaron mas de una linea en ambos archivos
					if(0<linesBatch)writeFile(String.valueOf(linesBatch),fCountBatch,false);
					if(0<linesOnli)writeFile(String.valueOf(linesOnli),fCountOnline,false);
					//Copiar archivos al directorio correspondiente del servidor de SISTECO
					if (copyFilesToSisteco()){			
						return "OK..! (Procesadas "+linesBatch+" lineas)";
					}else {
						return "ERROR AL COPIAR ARCHIVOS EN DESTINO";
					}
				}else return "NO HAY DATOS PARA PROCESAR..!!";
			}else{
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
			//Consulto si el producto es "vendible" si no es contin�o OpenUp SBT 05-10-2015 !!
			if(null==p){
				writeError("No existe producto: "+row.getM_Product_ID()+" - MRTInterfaceProdID ="+ row.get_ID());
				row.setIsActive(false);
				row.save();
				return null;
			}
			if(!p.isSold()){
				writeError("El producto: "+row.getM_Product_ID()+" - "+p.getName()+" no es vendible - MRTInterfaceProdID ="+ row.get_ID());
				row.setIsActive(false);
				row.save();
				return lineIn;
			}
			//Cargo datos segun tabla, y segun 
			if(table.equals(PRTInterfaceBPProdCli.A)){
				datosLinea = getDataToRowArticulo(row,accion);
				System.out.println("Proceso ARTICULO");
			}else if(table.equals(PRTInterfaceBPProdCli.AE)){
				datosLinea = getDataToRowArticuloEquivalente(row,accion);
				System.out.println("Proceso ARTICULO_EQUIVALENTE");
			}else if(table.equals(PRTInterfaceBPProdCli.T)){
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
			if(null!=datosLinea){
				lineIn = new StringBuilder();
				//Accion
				if(accion.equals("U")){//OpenUP SBT 30/11/2015  Mejora Issue #5125
					lineIn.append("A");
				}else {
					lineIn.append(accion); 
				}
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
			if (PRTInterfaceBPProdCli.INSERTAR.equals(accion) || PRTInterfaceBPProdCli.ACTUALIZAR.equals(accion)) {
				
				if (0 < row.getC_BPartner_ID()) {
					bp = (MBPartner) row.getC_BPartner();
					if(0<bp.get_ID()){ 
						if(bp.getValue()==null){
							writeError("Error MRTInterfaceBP:"+ row.get_ID()+" codigo cliente null");
							return null;
						}
						String codCte = bp.getValue().replace(SEPARATOR_L, "_")+SEPARATOR_L;//CodigoCliente
						if(bp.getName2()==null){
							writeError("Error MRTInterfaceBP:"+ row.get_ID()+" name2 cliente null");
							return null;
						}
						String nameCte = bp.getName2().replace(SEPARATOR_L, "_")+SEPARATOR_L;//Nombre						
						String dirCte = "";	//Direccion
						String esquina1 = "";//Esquina1
						String esquina2 = "";//Esquina2
						String telCte = "";//Telefono
						String rucCte = (bp.getDUNS()!=null?bp.getDUNS():"")+SEPARATOR_L;//RUT
						String documento = ((bp.getCedula()!=null)?bp.getCedula():"")+SEPARATOR_L;//Documento
						String codigoTipoDocumentoCliente = "";//CodigoTipoDocumentoCliente -- Posibles RUT/CI/OTR/PSP/DNI

						//OpenUp SBouissa 26/11/2015 - Se cambia metodo de control 
						if(!rucCte.equals(":")){// si tengo rut entonces codTipdoc -->RUT(3)
							codigoTipoDocumentoCliente = "2"+SEPARATOR_L;
						}else if(rucCte.equals(":") && !(documento.equals(":"))){//si rut vac�o y cedula NO entonces codTipoDoc -->CI(3)
							codigoTipoDocumentoCliente = "3"+SEPARATOR_L;
						}else if(rucCte.equals(":") && (documento.equals(":"))){
							//Se ecribe error pero se deja continuar
							writeError("PosibleError en Carga Sisteco - MRTInterfaceBP:"+ row.get_ID()+" RUT y Cedula cliente null");
							codigoTipoDocumentoCliente = "4"+SEPARATOR_L; //Tipo docuemto OTR (4)
						}
						
//						if(bp.getDocumentType().equalsIgnoreCase("RUT")){
//							codigoTipoDocumentoCliente = "2"+SEPARATOR_L;
//							if(bp.getDUNS()==null){
//								writeError("Error MRTInterfaceBP:"+ row.get_ID()+" RUT cliente null");
//								return null;
//							}
//						}else if(bp.getDocumentType().equalsIgnoreCase("CI")){
//							codigoTipoDocumentoCliente = "3"+SEPARATOR_L;
//							if(bp.getCedula()==null){
//								writeError("Error MRTInterfaceBP:"+ row.get_ID()+" Documento cliente null");
//								return null;
//							}
//						}else codigoTipoDocumentoCliente = "4"+SEPARATOR_L; //-->OTR
						
						String codigoDepartamentoPais = "";//CodigoDepartamentoPais - Se codigfica relacion depto codigo en tabla uy_
						String ciudadCte =  "";//Ciudad
						String codPtalCte = "";//CodigoPostal
						String email = "";//Email
						String codigoPais = "";//CodigoPais
						String atributos = "";//Atributos
						
						//Si el campo de cbpartner location no es null seteo los valores correspondientes
						if(0<row.getC_BPartner_Location_ID()){
							bpl = new MBPartnerLocation(getCtx(),row.getC_BPartner_Location_ID(),null);
							
							//DireccionCliente - 3
							dirCte = (((bpl.getAddress1()!=null)?bpl.getAddress1().replace(SEPARATOR_L, "_"):"")+SEPARATOR_L);;
							//6 Telefono
							telCte = (((bpl.getPhone()!=null)?bpl.getPhone().replace(SEPARATOR_L, "-"):"")+SEPARATOR_L);
							//Email
							email = ((bpl.getEMail()!=null)? bpl.getEMail():"")+SEPARATOR_L;
							
							//Ciudad - 
							MLocalidades loc = (MLocalidades) bpl.getUY_Localidades();
							if(null!=loc){
								if(0==loc.get_ID()){
									ciudadCte = ""+SEPARATOR_L;
								}else{
									ciudadCte = (loc.getName().replace(SEPARATOR_L, "_")+SEPARATOR_L);
								}
							}
							//Departamento - 
							MDepartamentos depto = (MDepartamentos) bpl.getUY_Departamentos();
							if(null!=depto){
								if(0==depto.get_ID()){
									codigoDepartamentoPais = ("10"+SEPARATOR_L);
								}else{
									codigoDepartamentoPais = (depto.get_ValueAsString("Value")+SEPARATOR_L);
								}
							}
							//Codigo Postal - 
							codPtalCte = ((bpl.getPostal()!=null)?bpl.getPostal():"")+SEPARATOR_L;
							 MCountry pais = (MCountry) bpl.getC_Country();
							 codigoPais = "UY"+SEPARATOR_L;//-->30/11/2015 Valor por defecto  Issue #5125
							 if(null!=pais && pais.get_ID() > 0 ){
								 
								 if( pais.getCountryCode().equalsIgnoreCase("AR") ||
										 pais.getCountryCode().equalsIgnoreCase("BR") ||
										 pais.getCountryCode().equalsIgnoreCase("PY") ||
										 pais.getCountryCode().equalsIgnoreCase("UY") ){
									 codigoPais = pais.getCountryCode().toUpperCase()+SEPARATOR_L; 
								 }else{
									 //OpenUp SBT 30/11/2015 Siste no les permite utilizar en le sistema de cajas Issue #5125
									 //codigoPais = "99"+SEPARATOR_L; //-->Otros 
								 } 
							 }
							 
							 esquina1 = (""+SEPARATOR_L);//Fin esquina 1
							 esquina2 = (""+SEPARATOR_L);//Fin esquina 2
						}else{// Si no tiene asociado una localidad los campos direccion,ciudad,departamento,
							//codigo postal y telefono se colocan vaios ::::::: 
							dirCte = (""+SEPARATOR_L);//Fin direccion
							esquina1 = (""+SEPARATOR_L);//Fin esquina 1
							esquina2 = (""+SEPARATOR_L);//Fin esquina 2
							telCte = (""+SEPARATOR_L);//fin telefon
							ciudadCte = (""+SEPARATOR_L);//fin ciudad
							codigoDepartamentoPais = ("10"+SEPARATOR_L);//fin departamento -- por defecto mdeo ?�
							codPtalCte = (""+SEPARATOR_L);//fin codigo postal
							codigoPais = ("UY"+SEPARATOR_L);//30/11/2015 ya no se utiliza el valor 99 OTR Issue #5125
							email = (""+SEPARATOR_L);

						}
						
						//Atributos hexagesimal 0000
						String [] retorno = null;
						retorno = new String[4];
						retorno[0] = String.valueOf(row.getattr_1()) + String.valueOf(row.getattr_2())
										+String.valueOf(row.getattr_3())+"0";
						retorno[1] = "0000";	
						retorno[2] = "0000";	
						retorno[3] = "0000";	
						
						String datoHexa = bitsToHexa(retorno);
						atributos = datoHexa;
						
						//Cargo los datos en el orden correspondiente
						if(codigoDepartamentoPais!="" || codigoTipoDocumentoCliente!="" || codigoPais!=""){
							lineIn.append(codCte+nameCte+dirCte+esquina1+esquina2+telCte+rucCte+documento
									+codigoTipoDocumentoCliente+codigoDepartamentoPais+ciudadCte+codPtalCte
									+email+codigoPais+atributos);
						}else{
							writeError("Verificar codigos obligatorios -  MRTInterfaceBP:"+ row.get_ID());
							return null;
						}
						
					}

				}//FIN
			} else if (PRTInterfaceBPProdCli.ELIMINAR.equals(accion)) {
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
			if(PRTInterfaceBPProdCli.INSERTAR.equals(accion) || PRTInterfaceBPProdCli.ACTUALIZAR.equals(accion)){
				
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());lineIn.append(SEPARATOR_L); //CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());;lineIn.append(SEPARATOR_L);
				}
				//02-07-2015 sbouissa se actualiza linea 
				lineIn.append(row.getM_Product_Tandem().getValue()); //CodigoArticuloTandem (value del tandem)
				
			}else if(PRTInterfaceBPProdCli.ELIMINAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());
				}else{
					return null;
					//lineIn.append(row.getM_Product().getValue());
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
				writeError("UPC vacio - MRTInterfaceProdID = "+row.get_ID());
				return null;
			}
		}
		try{
			lineIn = new StringBuilder();
			if(PRTInterfaceBPProdCli.INSERTAR.equals(accion) || PRTInterfaceBPProdCli.ACTUALIZAR.equals(accion)){
				lineIn.append(row.getUPC());lineIn.append(SEPARATOR_L); //CodigoArticuloExterno (codigobarra)
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode()); //CodigoArticuloInterno
				}else{
					lineIn.append(row.getM_Product().getValue());
				}
					
			}else if(PRTInterfaceBPProdCli.ELIMINAR.equals(accion)){
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
			if(PRTInterfaceBPProdCli.INSERTAR.equals(accion) || PRTInterfaceBPProdCli.ACTUALIZAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());lineIn.append(SEPARATOR_L);//CodigoArticulo
				}else{
					lineIn.append(row.getM_Product().getValue());lineIn.append(SEPARATOR_L);
				}
				 String nameAux = row.getDescription().replace(SEPARATOR_L,"_");//24-09-2015 Se ccontrola nombre si contiene separador de linea sisteco
				lineIn.append(nameAux);lineIn.append(SEPARATOR_L);//Descripcion
				lineIn.append("0").append(SEPARATOR_L); //CodigoEntorno por ahora siempre 0
				lineIn.append("0").append(SEPARATOR_L); //CodigoSubFamilia siempre es cero hasta que se defina gerarquias 25-06-2015

				lineIn.append(currencyCodeAux(row.getC_Currency_ID()));lineIn.append(SEPARATOR_L);//CodigoMoneda
				if(null!=row.getC_TaxCategory() && 0!=row.getC_TaxCategory_ID()){
					lineIn.append(row.getC_TaxCategory().getCommodityCode());lineIn.append(SEPARATOR_L);		//CodigoIVA
				}else{
					writeError("C_TaxCategory_ID null - MRTInterfaceProdID = "+row.get_ID());
					return null;
				}
				String decimales = "00";
				String precio="0";//7
				
				//OpenUp SBouissa 19-10-2015 Issue#
				//Se consulta el precio desde la lista actual
				//Lista de precio de venta
				MPriceList list = MPriceList.getDefault(getCtx(), true, row.getC_Currency_ID());
				int adOrgID = DB.getSQLValue(null, "SELECT MIN(AD_Org_ID) FROM AD_Org WHERE isActive = 'Y' AND AD_Org_ID <> 0");
				MProductPrice prodPrice = MProductPrice.forVersionProduct(getCtx(), list.getVersionVigente(null).get_ID(), row.getM_Product_ID(), adOrgID, get_TrxName());
				BigDecimal price = null;
				if(null!=prodPrice){
					price = prodPrice.getPriceList();
					System.out.println(price.toString());
				}
				if(null!=price){
					BigDecimal d = (price);
					BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
					if(null!=result){
						if(!result.equals(BigDecimal.ZERO)){
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
						//if(!result.equals(BigDecimal.ZERO)){
							//decimales = result.toString().substring(0, 2);
						//}
					}
					precio = String.valueOf(price.intValue())+decimales;
					//precio = price.setScale(2, RoundingMode.HALF_UP).toString();
				}else{
					precio = precio +decimales;
				}
				lineIn.append(precio);lineIn.append(SEPARATOR_L);		//PrecioArticulo
				//--> Obtencion de los 48 bits 
				String[] listaAtributos = getAtrubutesBitString(row); 
				//Pasar a hexagesimal lista de atributos
				String atributosHexa = bitsToHexa(listaAtributos).toUpperCase();
				if(("").equals(atributosHexa)){
					writeError("Atrinutos vac�o - MRTInterfaceProdID = "+row.get_ID());
					return null;
				}else if(16!=atributosHexa.length()) {
					writeError("Atrinutos no es del largo correcto - MRTInterfaceProdID = "+row.get_ID());
					return null; 
				}
				lineIn.append(atributosHexa).append(SEPARATOR_L);
				lineIn.append(String.valueOf(row.getUnitsPerPack()));lineIn.append(SEPARATOR_L);		//Cantidad Pack
				MProduct prod = (MProduct) row.getM_Product();
				//OpenUp SBT Issue #4976 (Mejora)
				String codMedida =((prod.getC_UOM().getName().equalsIgnoreCase("KG"))? "2":"1");
				lineIn.append(codMedida); //Codigo Medida (1-Unidad 2-Kg)
				
	
			}else if(PRTInterfaceBPProdCli.ELIMINAR.equals(accion)){
				if(null!=row.getProdCode()){
					lineIn.append(row.getProdCode());//CodigoArticulo
				}else{
					return null;
					//lineIn.append(row.getM_Product().getValue());
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
				return PRTInterfaceBPProdCli.A;
			}else if("AE".equals(codeTable)){
				return PRTInterfaceBPProdCli.AE;
			}else if("T".equals(codeTable)){
				return PRTInterfaceBPProdCli.T;
			}else if("C".equals(codeTable)){
				return PRTInterfaceBPProdCli.C;
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
							if(0<linesOnli){
								//dest = new File(destino + File.separator + nameOnline);				
							}
						}else if(files[i].startsWith(fchToday+nameBatch)){
							if(0<linesBatch){
								dest = new File(destino + File.separator + nameBatch);
							}
						}else if(files[i].startsWith(fchToday+cantOnline)){
							if(0<linesOnli){
								//dest = new File(destino + File.separator + cantOnline);
							}
						}else{
							if(0<linesBatch){
								dest = new File(destino + File.separator + cantBatch);
							}
						}
						if(dest!=null){
							copyFile(source, dest);
						}
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
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_DIRECTORY_RT_MANT",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_DIRECTORY_RT_MANT",0); //Directorio origen prametrizado para linux
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
		fb = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProdCli.NAME_BATCH);
		return fb;
	}

	/**
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 15/6/2015
	 */
	private File creatOnlineFile() {
		File fo = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProdCli.NAME_ONLINE);
		return fo;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 26/6/2015
	 * @return
	 */
	private File creatCountOnlineFile() {
		File fo = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProdCli.NAME_COUNTONLINE);
		return fo;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 26/6/2015
	 * @return
	 */
	private File createCountBatchFile() {
		File fo = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceBPProdCli.NAME_COUNTBATCH);
		return fo;
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
//		destino temporal para realizar prubas para siteco 25-11-2015 sbouissa	
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"); //C:\FilesCovadonga\DestinoSisteco

	}

	/**
	 * OpenUp Ltda Issue #4976
	 * @author Sylvie Bouissa 23/11/2015
	 * @return
	 */
	private File createBatchErrorFile() {
		File fb=null;
		fb = new File(getRutaOrigen()+File.separator+"ArchDeErrores"+
		File.separator+fchToday+PRTInterfaceBPProdCli.NAME_BATCH_ERROR);
		return fb;
	}
	
	
	/**
	 * Metodo para escribir errores
	 * OpenUp Ltda Issue #4976
	 * @author Sylvie Bouissa 23/11/2015
	 * @param error
	 */
	private void writeError(String error){
		Timestamp date = new Timestamp(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		String hora = date.getDate()+"-"+date.getMonth()+" "+date.getHours()+":"+date.getMinutes();
		//writeFile(error +" Fecha:"+hora, fBatchError, true);
	}
}

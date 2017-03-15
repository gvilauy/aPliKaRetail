/**
 * 
 */
package org.openup.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.model.X_AD_Org;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.openup.model.MRTAuditLoadTicket;
import org.openup.model.MRTLoadTicket;
import org.openup.model.MRTLogFile;
import org.openup.model.MRTTicketBenefAlTotal;
import org.openup.model.MRTTicketCancelItem;
import org.openup.model.MRTTicketDescAlTotal;
import org.openup.model.MRTTicketHeader;
import org.openup.model.MRTTicketLineCabezalCFE;
import org.openup.model.MRTTicketLineClienteCC;
import org.openup.model.MRTTicketLineCtaCte;
import org.openup.model.MRTTicketLineDevPagoSer;
import org.openup.model.MRTTicketLineEfectivo;
import org.openup.model.MRTTicketLineFactura;
import org.openup.model.MRTTicketLineFondeo;
import org.openup.model.MRTTicketLineItem;
import org.openup.model.MRTTicketLineItemReturn;
import org.openup.model.MRTTicketLineLuncheon;
import org.openup.model.MRTTicketLinePagoServ;
import org.openup.model.MRTTicketLinePagoTACRE;
import org.openup.model.MRTTicketLineRedondeo;
import org.openup.model.MRTTicketLineRetiro;
import org.openup.model.MRTTicketLineTarjeta;
import org.openup.model.MRTTicketLineTktCteCFE;
import org.openup.model.MRTTicketLineTotalTck;
import org.openup.model.MRTTicketLineVTarjTACRE;
import org.openup.model.MRTTicketType;
import org.openup.model.MTicketLinePServCheck;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;

/**OpenUp Ltda Issue #4976 (Mejora) Se toma como base la clase PRTLoadTicket y se aplican cambios
 * @author SBT 24/11/2015.-
 *
 */
public class PRTLoadTicketPazos extends SvrProcess {

		//INI variables
		private static final String CABEZAL = "C";
		private static final String LINEA = "L";
		
		private static final String cgoPESOS = "1";
		private static final String cgoDOLARES = "2";	
		//cgoMedioPago_Efectivo
		private static final String MDIOPAGO_EFECTIVO = "1";
		private static final String MDIOPAGO_SIDEXO = "10";
		private static final String MDIOPAGO_CHEQUE = "3";
		private static final String MDIOPAGO_TARJETA_OFLINE = "14";
		private static final String MDIOPAGO_DEVOLUCION_ENVASE = "13";
		//Tipos de lineas
		private static final String LINEA_VENTA = "1";
		private static final String LINEA_RETIRO = "97";
		private static final String LINEA_FACTURA= "92";
		private static final String LINEA_FONDEO = "95";
		private static final String LINEA_VTA_TARJETA = "37";
		private static final String LINEA_VTA_LUNCHEON = "40";
		private static final String LINEA_VTA_ALIMENTOS = "90";//ticket TAlimentos
		private static final String LINEA_VTA_TOTAL = "91";// ticket Total
		private static final String LINEA_VTA_EFECTIVO = "9";
		//INI MOpenUp SBT 27/11/15 Issue #4976 Cambios de numeros de lineas y nuevos tipos
		//private static final String LINEA_VTA_CTA_CTE = "104"; --> Ahora corresponde a la 127
		private static final String LINEA_VTA_CTA_CTE = "127";	
		private static final String LINEA_MTO_PMO_EFECT = "104"; //--> Nueva linea????
		//private static final String LINEA_CTA_CTE = "99";--> Ahora corresponde a la 126
		private static final String LINEA_CTA_CTE = "126";		
		//FIN MOpenUp SBT 27/11/15 Issue #4976 Cambios de numeros de lineas y nuevos tipos
		//OpenUp SBT 11/12/2015 Issue #5117 Nueva linea
		private static final String LINEA_PAGOTACRE = "123";
		private static final String LINEA_VAUCHER_TJETA_TACRE = "124";

		private static final String LINEA_PAGO_SERVICIO = "47";
		//OpenUp SBT 01/11/2015 Issue #4976 se agrega el tipod e linea 94
		private static final String LINEA_PAGO_SERVICIOCHECK = "94";
		//FIN OpenUp SBT 01/11/2015 Issue #4976 se agrega el tipod e linea 94
		private static final String CHAR_SEPARATOR = "\\|";//24/11/15 Issue #4976 cambia de # a |
		//10/09/2015
		private static final String LINEA_DEVITEM = "20";
		//17/09/2015
		private static final String LINEA_CANCELITEM = "12";
		//15/12/2015
		private static final String LINEA_CabezalCFE = "5";
		private static final String LINEA_TktClienteCFE = "106";
		//16/12/2015 
		private static final String LINEA_TOTAL_TICKET = "4";
		//11/01/2016
		private static final String LINEA_REDONDEO_IMPOTE_TOTAL = "63";
		private static final String LINEA_DETALLE_BENEFICIO_AL_TOTAL = "27";
		private static final String LINEA_DESC_AL_TOTAL = "28";
		private static final String LINEA_DEV_PGO_SERVICIO = "81";
		
		private int idMRTLT;
		/**	Logger							*/
		protected CLogger			log = CLogger.getCLogger (getClass());
		
		private MRTAuditLoadTicket mAudit = null;
		private MRTLoadTicket model = null;
		private String fileNameProcessed = "";

		private BigDecimal sumaLineas = new BigDecimal(0);
		private BigDecimal sumoCabezales = new BigDecimal(0);
		
		private int cantCabezal=0;
		private int cantLineas=0;
		private int cantTotal=0;
		
		private String cabezalActual="";
		private boolean lineVenta = false;
		
		private String rutaOrigen="";
		private String rutaDestino;
		private String fchCabezal;
		
		private String yesterday;
		private MClient client;
		private MOrg orgDefault;
		private boolean onWindows=false;
		// Fin variables
		
	
	/**
	 * CONSTRUCTOR
	 */
	public PRTLoadTicketPazos() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		onWindows = true;
		try{
			String a = System.getProperty("os.name" );
			if(a.contains("Linux")){
				onWindows = false;
			}
		}		
		catch(Exception e){
			e.toString();
		}
		// Creo modelo MRTloadTicket
		this.model = new MRTLoadTicket(getCtx(), this.getRecord_ID(), null);
		model.setProcessed(false);
		if (model.isManual()) {// Proceso manual seleccionando un archivo
			if (model.getFileName1() == null || model.getFileName1().equals("")) {
				throw new AdempiereException("Seleccione archivo para procesar");
			}
			log.log(Level.SEVERE,"INICO PRLOADTICKETPazos:"+ new Timestamp(System.currentTimeMillis()).toString());
			MUser u = new MUser(getCtx(), this.getAD_User_ID(), null);
			this.model.setName("Manual_"+ new Timestamp(System.currentTimeMillis()).toString()+ "_" + u.getName());
			File aux = new File(this.model.getFileName1());
			this.model.setFileName(aux.getName());
			log.log(Level.SEVERE,"Copia Archivo:"+ new Timestamp(System.currentTimeMillis()).toString());
			this.model.setisfilefound(copyFileFromSisteco(yesterday));
			log.log(Level.SEVERE,"Fin Copia Archivo:"+ new Timestamp(System.currentTimeMillis()).toString());
			rutaOrigen = getRutaOrigen();
			rutaDestino = getRutaDestino();
			this.model.saveEx();
		} else {// Proceso autom�tico levanta archivo del dia anterior y procesa
				// model.setDescription("");
			log.log(Level.SEVERE,"INICO PRLOADTICKET:"+ new Timestamp(System.currentTimeMillis()).toString());
			String[] fecha = new Timestamp(System.currentTimeMillis() - 1000L
					* 60L * 60L * 24L).toString().split("-");
			// Formato fecha YYYYMMdd
			String fechaArch = fecha[0] + fecha[1] + fecha[2].substring(0, 2);
			yesterday = fechaArch;
			// yesterday = "20151126";// para testing
			// Guardo el nombre del archivo que se debe procesar
			this.model.setFileName(getInitFile() + yesterday);

			log.log(Level.SEVERE,"Copia SISTECO:"+ new Timestamp(System.currentTimeMillis()).toString());
			// Traemos archivo de ayer desde servidor de sisteco si es true, se
			// encontro el archivo.
			this.model.setisfilefound(copyFileFromSisteco(yesterday));
			log.log(Level.SEVERE,"Fin Copia SISTECO:"+ new Timestamp(System.currentTimeMillis()).toString());
			// Se obtiene ruta de origen y ruta de destino
			rutaOrigen = getRutaOrigen();
			rutaDestino = getRutaDestino();
			this.model.setName("Automatico_"+ new Timestamp(System.currentTimeMillis()).toString());
			client = getDefaultClient();
			orgDefault = getDefaultOrg();
			this.model.setAD_Client_ID(client.get_ID());
			this.model.setAD_Org_ID(orgDefault.get_ID());
			this.model.saveEx();
		}
		this.idMRTLT = this.model.get_ID();

	}
	
	/**Copiar archivo desde servidor de sisteco a servidor local, retorna true si la accion es exitosa
	 *
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 12/05/2015
	 * @return boolean 
	 */
	private boolean copyFileFromSisteco(String fecha) {
		boolean fileFound = false;
		File origen = null; File destino = null;
		try{
			if(this.model.isManual()){//SBT 08-09-2015 cambio para poder cargar cualquier archivo
				origen = new File (String.valueOf(this.model.getFileName1()));
				destino = new File(getRutaOrigen()+File.separator+origen.getName());
				copyFile(origen, destino);
				fileFound = true;
			}else{
				origen = new File (getRutaOrigenSisteco());
				destino = new File(getRutaOrigen());
				String start = getInitFile();
				String[] files = origen.list();
				for (int i = 0; i < files.length; i++) {
					if (files[i].startsWith(start+fecha)){
						//se obtiene separador configurado en system config (win\\, en linux /)12_05_2015
						File source = new File(origen + getFileSeparator() + files[i]);
						File dest = new File(destino + getFileSeparator() + files[i]);				
						copyFile(source, dest);
						source = null;
						dest = null;
						//fileFound = true;
						return true;
					}
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

	/**Directorio donde se levantan los archivos salida pazos 4 en servidor local --\\\\10.0.0.254\\ArchivoSalidaPazos
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @return
	 */
	private String getRutaOrigen() {
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"RutaOrigenSalidaPazos"); //C:\FilesCovadonga\DestinoSisteco
//		String  origen ="C:"+File.separator+"FilesCovadonga"+File.separator+"RutaOrigen"+File.separator;
		String  origen = MSysConfig.getValue("SOURCE_DIRECTORY_RT",0); 
		if(onWindows){
			origen = origen.replace("/srv/share/", "\\\\10.0.0.254\\");
		}
		return origen;
	}
	
	/**
	 * Dirctorio del servidor de sisteco para obtener solo archivos SalidaPazos --\\\\10.0.0.131\\informes
	 * OpenUp Ltda Issue#40324032
	 * @author Sylvie Bouissa 12/05/2015
	 * @return
	 */
	private String getRutaOrigenSisteco() {
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"informes");
		String  origen = MSysConfig.getValue("SOURCE_DIRECTORY_SISTECO",0); 
		if(onWindows){
			origen = origen.replace("/srv/share/", "\\\\10.0.0.254\\");
		}
		return origen;
		
	}
	
	/**Directorio donde se guardan los archivos salida pazos 4 ya procesados --\\\\10.0.0.254\\HistoricoSalidaPazos
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @return
	 */
	private String getRutaDestino() {
//	return ("C:"+File.separator+"FilesCovadonga"+File.separator+"HistoricoSalidaPazos");
//  String destino = "C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"+File.separator;
		String  destino = MSysConfig.getValue("DESTINATION_DIRECTORY_RT",0); //Directorio dest prametrizado
		if(onWindows){
			destino = destino.replace("/srv/share/", "\\\\10.0.0.254\\");
		}
		return destino;
	}
	
	/**Retorna la parametrizacion del inicio del nombre de los archivos salida pazos (SalidaPazos-1-) //Version nueva cambia a Salidapazosnuevo-1-
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 12/05/2015
	 * @return
	 */
	private String getInitFile() {
		String  nombre = MSysConfig.getValue("SISTECO_INIT_FILE",0); //Directorio origen prametrizado
		return nombre;
	}
	
	/**Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 15/05/2015
	 * @return
	 */
	private MClient getDefaultClient() {		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0 AND IsActive= 'Y'";
			
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
			
		return value;
	}
	
	/**Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 15/05/2015
	 * @return
	 */
	private MOrg getDefaultOrg() {		
		String whereClause = X_AD_Org.COLUMNNAME_AD_Org_ID + "!=0 AND IsActive= 'Y'";
			
		MOrg value = new Query(getCtx(), I_AD_Org.Table_Name, whereClause, null)
		.first();
			
		return value;
	}
	
	/**Copia archivo "sourceFile" al archivo destino "destFile"
	 * OpenUp Ltda Issue #4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param sourceFile
	 * @param destFile
	 */
	@SuppressWarnings("resource") //Sug para los fileinputstream
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
	
	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 13/05/2015
	 * @return
	 */
	private String getFileSeparator() {
		String  nombre = MSysConfig.getValue("SEPARATOR_FILE_RT",0);
		return nombre;
	}
	
	@Override
	protected String doIt() throws Exception {
		log.log(Level.SEVERE, "INIT DOIT PRTLoadTicketPazos: "+new Timestamp (System.currentTimeMillis()).toString());
		
		String retorno = "";
		if(this.model!=null){//1 
			File origen = null; File destino = null;
			if(this.model.isfilefound()){//2-Consulto si se encontro el archivo del dia anterior en serv sisteco
				if(!rutaOrigen.equals("") && !rutaDestino.equals("")){//3-Consulto si existe ruta origen y destino
					origen = new File(rutaOrigen);
					destino = new File(rutaDestino);		
					String[] files = origen.list();// Listo los archivos que se encuentran en el directorio origen
					int posicion = obtenerPosicionAProcesar(files);	//Obtengo la posicion del archivo que tengo que procesar			
					if(0<=posicion){//4						
						if(!archivoProcesado(files[posicion])){//5-Si el archivo no esta procesado se prosigue
							fileNameProcessed = files[posicion];
							//Tener en cuenta que para servidores se debe colocar / para 
							//Windows se debe colocar \\							
							File source = new File(origen + getFileSeparator() + files[posicion]);
							File dest = new File(destino + getFileSeparator() + "Procesado_"+ files[posicion]);
								log.log(Level.SEVERE, "INI Procesar archivo:"+new Timestamp (System.currentTimeMillis()).toString());
								retorno = procesarArchivo(source);
								if(retorno.equals("OK")){//7
									//model.setProcessed(true);
									//model.setDescription("Archivo procesado exitosamente!!");
									//model.saveEx();
									try {
										log.log(Level.SEVERE, "INI Auditoria:"+new Timestamp (System.currentTimeMillis()).toString());
										//Se crea auditoria de lo procesado
										auditarResultado(dest);
										log.log(Level.SEVERE, "INI Copia historico:"+new Timestamp (System.currentTimeMillis()).toString());
										copyFile(source, dest);	//Se copia archivo a carpeta historico
										source.delete();
										dest = null;
										mAudit.setDescription("Archivo procesado exitosamente!!");
										mAudit.saveEx();
										MRTLoadTicket ahora = new MRTLoadTicket(getCtx(), model.get_ID(), get_TrxName());
										ahora.setProcessed(true);
										ahora.setDescription("Archivo procesado exitosamente!!");
										ahora.saveEx();
										//SBT Issue #8238 09/02/2017
										//Se crean salidas y entradas de stock por ventas y devoluciones  
										log.log(Level.SEVERE, "INI Mov. Stock:"+new Timestamp (System.currentTimeMillis()).toString());
										PRTLoadSalesMovements  prs = new PRTLoadSalesMovements(getCtx(),get_TrxName(),model.get_ID());
										prs.execute();
//										model.setProcessed(true);
//										model.saveEx();
										log.log(Level.SEVERE, "FIN Proceso lectura SalidaPazos!!"+new Timestamp (System.currentTimeMillis()).toString());
				
									} catch (IOException e) {
										e.printStackTrace();
										retorno = e.getMessage();
									}finally{
										origen = null;
										destino = null;
									}
									log.log(Level.SEVERE, "RETORNO OK:"+new Timestamp (System.currentTimeMillis()).toString());

									return "OK";
								}else{//7
									model.setProcessed(false);
									model.setDescription("Error al procesar lineas");
									model.saveEx();
									return "Error al procesar lineas";
								}
						}else{//5
							model.setProcessed(false);
							this.model.setDescription("Existe auditoria para el archivo:"+this.model.getFileName());
							model.saveEx();
							log.log(Level.SEVERE, "Existe auditoria para el archivo:"+new Timestamp (System.currentTimeMillis()).toString());

							return "OK";
						}
					}else{//4
						model.setProcessed(false);
						this.model.setDescription("No se encontro el archivo en carpeta de destino ");
						model.saveEx();									
						log.log(Level.SEVERE, "No se encontro el archivo en carpeta de destino:"+new Timestamp (System.currentTimeMillis()).toString());
						return "No se encontro el archivo en carpeta de destino";
					}
					
				}else{//3
					model.setProcessed(false);
					this.model.setDescription("No existe ruta de origen o destino");
					model.saveEx();
					log.log(Level.SEVERE, "No existe ruta de origen o destino"+new Timestamp (System.currentTimeMillis()).toString());

					return "No existe ruta de origen o destino";
				}
			}else{//2
				model.setProcessed(false);
				this.model.setDescription("No se encontro archivo en servidor Sisteco:("+this.model.getFileName()+")");
				model.saveEx();
				log.log(Level.SEVERE, "No se encontro archivo en servidor Sisteco:("+this.model.getFileName()+")"+new Timestamp (System.currentTimeMillis()).toString());

				return "No se encontro archivo en servidor Sisteco";
			}
			
			
		}else{//1 
			return "MRTLoadTicket null";
		}
	}
	
	/**Metodo recorre la lista de archivos, controla que sea el archivo correspondiente a procesar
	 * (archivo SalidaPazos-YYYYMMdd, fecha del dia anterior)
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 17/05/2015
	 * @param files
	 * @return
	 */
	private int obtenerPosicionAProcesar(String[] files) {
		if(files.length>0){
			for (int i = 0; i < files.length; i++) {
				if(!this.model.isManual()){			
					if (files[i].startsWith(getInitFile()+yesterday)){
						return i;
					}
				}else{//sbt 09-09-2015 si es manual el archivo tiene que ser tal cual el que indicaron
					if (files[i].startsWith(this.model.getFileName())){
						return i;
					}
				}
			}			
		}
		return -1;
	}
	
	/** Retorna true si existe una auditoria con ese nombre de archivo
	 * OpenUp Ltda Issue #4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param string
	 * @return
	 */
	private boolean archivoProcesado(String fileName) {
		return MRTAuditLoadTicket.forFileName(getCtx(),fileName,null)!=null;
	}

	
	/**Se leen cada una de las lineas y dependiendo de si es cabezal o linea se persiste en la tabla correspondiente
	 * OpenUp Ltda Issue#4032 3952
	 * @author Sylvie Bouissa 07/05/2015
	 * @param source
	 * @return
	 */
	private String procesarArchivo(File file) {		
		FileReader fr = null;
		BufferedReader br = null;
		try{
			mAudit = new MRTAuditLoadTicket(getCtx(), 0, get_TrxName()); //Instancio la auditoria
			if(client!=null){
				mAudit.setAD_Client_ID(client.get_ID());
			}
			if(orgDefault!=null){
				mAudit.setAD_Org_ID(orgDefault.get_ID());
			}
			mAudit.setStartTime(new Timestamp (System.currentTimeMillis()));//Se indica fecha y hora de comienzo
			mAudit.setName("Auditoria"+mAudit.getStartTime());//Se indica nombre de la auditoria indicando la fecha actual
			mAudit.setFileName(fileNameProcessed);//Se guarda nombre de archivo�		
			Timestamp fechArch = parseDateFromName(mAudit.getFileName());//16-09-2015 SBouissa  Se agrega campo de fecha que indica la fecha de los datos del archivo (para Arqueo NSarlabos) 
			mAudit.set_ValueOfColumn("DateValue", fechArch);
			mAudit.setDescription("Origen: "+file.getAbsolutePath());//Guardo orgen de lectura
			
			// Se comienza a leer el archivo fila por fila
			fr = new FileReader (file);
			br = new BufferedReader(fr);
			
			MRTTicketHeader mConH = null;
			int cont = 0;
			String line = br.readLine();
			
			while(line != null){
				try{
					cont ++;
					String tipo = line.substring(0,1);
					//Se consulta si el identificador de linea es C (Cabezal) o L (Linea)
					if (tipo.equalsIgnoreCase(PRTLoadTicketPazos.CABEZAL)){
						cantCabezal++;
						//System.out.println(cantCabezal);
						mConH = parseHeader(line,String.valueOf(cont));	
					} else if (tipo.equalsIgnoreCase(PRTLoadTicketPazos.LINEA)){
						cantLineas++;
						//System.out.println(cantLineas);
						parseLine(line, mConH.get_ID(),String.valueOf(cont));
					}			
				}catch(Exception ex){
					log.log(Level.SEVERE, "Error: "+ ex.getMessage());
					throw new AdempiereException(ex.getMessage());
				}
				line = br.readLine();
				cantTotal++;
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			if(br!=null){
				try {
					br.close();
					if(fr!=null){
						fr.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.log(Level.SEVERE, "ProcesarArvhico: "+new Timestamp (System.currentTimeMillis()).toString()+" catnTotalL:"+cantTotal);
		return "OK";
	}
	
	/**SE TIENE EN CUENTA QUE EL NOMBRE DE LOS ARCHIVOS ES DEL TIPO SalidaPazos-1-20150813.236
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 16/9/2015
	 * @param fileName
	 * @return
	 */
	private Timestamp parseDateFromName(String fileName) {
		if(fileName.length()>0){
			fileName = fileName.replace(getInitFile(), "");
			String res = fileName.substring(0, 8); //SalidaPazos-1-20150813.236		antes(14,22)
			return Convertir.converTimestampYYMMdd(res);
		}
		return null;
	}

	/**Proceso la l�nea tipo cabezal
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param line
	 * @param valueOf
	 * @return
	 */
	private MRTTicketHeader parseHeader(String line, String position) {
		//Insatanciamos el modelo de cabezal
		MRTTicketHeader ret = new MRTTicketHeader(getCtx(), 0, get_TrxName());
		ret.setAD_Client_ID(model.getAD_Client_ID());
		ret.setAD_Org_ID(model.getAD_Org_ID());
		String lineSplit[] = line.split(PRTLoadTicketPazos.CHAR_SEPARATOR);
		MRTTicketType tktType = MRTTicketType.forValueAndHeader(getCtx(), lineSplit[1], OpenUpUtilsRT.isHeader, null);
		try{		
			if(tktType!=null){
				cabezalActual = lineSplit[1]; //guardo el tipodlinea del cabezal que estoy procesando 
				ret.setUY_RT_LoadTicket_ID(model.get_ID());
				ret.setUY_RT_TicketType_ID(tktType.get_ID()); //pos1				
				ret.setcodigocaja(lineSplit[2]); //pos2
				ret.setnumeroticket(lineSplit[3]); //pos3
				if(!lineSplit[4].equals("")){
					ret.setcodigocajera(lineSplit[4]);//pos4
				}			
				String fecha =lineSplit[5]; //pos5
				ret.settimestampticket(Convertir.convertirYYYYMMddHHMMss(fecha));
				fchCabezal = fecha.substring(0,8);
				ret.setestadoticket(lineSplit[6]); //pos6
				ret.setcantidadarticulos(Integer.valueOf(lineSplit[7]));//pos7
				ret.settotalapagar(new BigDecimal(lineSplit[8])); //pos8
				ret.settipocliente(lineSplit[9]); //pos9
				ret.setcantidadlineas(Integer.valueOf(lineSplit[10])); //pos10
				//INI OpenUp Lda SBouissa 07-09-2015 A pedido de GV se agrega el per�odo.
				int cpID = MPeriod.findByCalendar(getCtx(), ret.gettimestampticket(), 1000010,null).get_ID();
				if(0<cpID){
					ret.set_ValueOfColumn("C_Period_ID", cpID);
				}
				//FIN OpenUp Lda SBouissa 07-09-2015 A pedido de GV se agrega el periodo.
				//Ahoar se toma en cuenta el tipo de linea 1 - SM solo contiene 10 campos no existe
				if(MRTTicketHeader.vVenta.equals(lineSplit[1])){// se trata de un tiket de venta
//					//if(!lineSplit[11].equals("0")){ //si es cero es un ticket normal sino se deben guardar los siguientes datos
//					if(!(MRTTicketHeader.vVentaNormal.equals(lineSplit[11]))){
//						if (lineSplit[11] !=null) ret.setcodigocajadevolucion(Integer.valueOf(lineSplit[10]));
//						if (lineSplit[12] !=null) ret.setnumeroticketdevolucion(Integer.valueOf(lineSplit[12]));
//					}
					lineVenta = true;
				}else{
					lineVenta = false;
				}
				ret.setpositionfile(position);
				ret.saveEx();

			}else{
				throw new AdempiereException("No esta parametrizado el cabezal del tipo: "+lineSplit[1]);//crear lineas con detalles
			}
			
			
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}

		return ret;
	}
	
	
	/**Se guardan los datos de la l�nea seg�n el tipo de lina en la tabla que corresponde
	 * Si no esta registrado el tipo de l�nea, se guarda en una tabla gen�rica
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param line
	 * @param get_ID
	 * @param valueOf
	 */
	private void parseLine(String line, int headerId, String posicionfila) {
		String lineSplit[] = line.split(PRTLoadTicketPazos.CHAR_SEPARATOR);
		
		if(lineSplit[2].equals(PRTLoadTicketPazos.LINEA_VENTA)){//Linea de venta 1
			//OpenUp SBT 26/11/2015 Issue #4976 --> Un campo nuevo y 5 menos
			MRTTicketLineItem lineTktVenta = new MRTTicketLineItem(getCtx(),0,get_TrxName());
			lineTktVenta.parseLineItemVta(lineSplit,fchCabezal,headerId,posicionfila);
			lineTktVenta.setAD_Client_ID(model.getAD_Client_ID());
			lineTktVenta.setAD_Org_ID(model.getAD_Org_ID());
			lineTktVenta.saveEx();

			//INI - OpenUp se agrega el control al tipo de linea 20 - SBouissa 10-09-2015
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_DEVITEM)){//Linea de devolucion de item 20
			//OpenUp SBT 26/11/2015 Issue #4976 --> 5 campos menos
			MRTTicketLineItemReturn lineTktVentaDev = new MRTTicketLineItemReturn(getCtx(),0,get_TrxName());
			lineTktVentaDev.parseLineItemReturn(lineSplit,fchCabezal,headerId,posicionfila);
			lineTktVentaDev.setAD_Client_ID(model.getAD_Client_ID());
			lineTktVentaDev.setAD_Org_ID(model.getAD_Org_ID());
			lineTktVentaDev.saveEx();
			//Creo linea de item para que se contabilice negativamente 
			MRTTicketLineItem lineTktVenta = new MRTTicketLineItem(getCtx(),0,get_TrxName());
			lineTktVenta.parseLineItemVtaOpenUp(lineTktVentaDev,headerId);
			lineTktVenta.setAD_Client_ID(model.getAD_Client_ID());
			lineTktVenta.setAD_Org_ID(model.getAD_Org_ID());
			lineTktVenta.saveEx();
			//FIN - OpenUp se agrega el control al tipo de linea 20 - SBouissa 10-09-2015
			//INI - OpenUp se agrega el control al tipo de linea 12 - SBouissa 17-09-2015			// I 
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_CANCELITEM)){ //Linea cancel item 12

			MRTTicketCancelItem lineaCancel = new MRTTicketCancelItem(getCtx(),0,get_TrxName());
			lineaCancel.parseLineCancelItem(lineSplit,fchCabezal,headerId,posicionfila);
			lineaCancel.setAD_Client_ID(model.getAD_Client_ID());
			lineaCancel.setAD_Org_ID(model.getAD_Org_ID());
			lineaCancel.saveEx();
			//Se deben recorrer las lineas creadas anteriormente para ver si corresponde crear una linea que contrareste
			MRTTicketLineItem noCancelada = MRTTicketLineItem.getLineCancelItem(getCtx(),
					lineaCancel.getcodigoartsubf(),lineaCancel.getnrolineaventa(),headerId,get_TrxName());
			if(noCancelada != null && noCancelada.getlineacancelada() == 0 && noCancelada.getsiesobsequio().equals("0") ){			
				//Si no esta cancelada creao linea para contrarestar totales
				MRTTicketLineItem aux = new MRTTicketLineItem(getCtx(), 0, get_TrxName());
				aux.parseLineItemVtaOpenUpCancel(lineaCancel, noCancelada,headerId);
				aux.setAD_Client_ID(model.getAD_Client_ID());
				aux.setAD_Org_ID(model.getAD_Org_ID());
				aux.saveEx();
			}
			
			//FIN - OpenUp se agrega el control al tipo de linea 12 - SBouissa 17-09-2015			// I 
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_RETIRO)){ //Linea retiro 97

			MRTTicketLineRetiro lineaRetiro = new MRTTicketLineRetiro(getCtx(),0,get_TrxName());
			lineaRetiro.parseLineRetiro(lineSplit,fchCabezal,headerId,posicionfila);
			lineaRetiro.setAD_Client_ID(model.getAD_Client_ID());
			lineaRetiro.setAD_Org_ID(model.getAD_Org_ID());
			lineaRetiro.saveEx();

		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_FACTURA)){// Liena Factura "92"

			MRTTicketLineFactura lineFactura = new MRTTicketLineFactura(getCtx(),0,get_TrxName());
			lineFactura.parseLineFactura(lineSplit,fchCabezal,headerId,posicionfila);
			lineFactura.setAD_Client_ID(model.getAD_Client_ID());
			lineFactura.setAD_Org_ID(model.getAD_Org_ID());
			lineFactura.saveEx();
			
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_FONDEO)){ //linea fondeo 95

			MRTTicketLineFondeo lineFondeo = new MRTTicketLineFondeo(getCtx(),0,get_TrxName());
			lineFondeo.parseLineFondeo(lineSplit,fchCabezal,headerId,posicionfila);
			lineFondeo.setAD_Client_ID(model.getAD_Client_ID());
			lineFondeo.setAD_Org_ID(model.getAD_Org_ID());
			lineFondeo.saveEx();

		// ------------------- Tipos de ventas -------------------
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VTA_TARJETA)){ //Venta Tarjeta 37
			
			//OpenUp SBT 27/11/2015 Issue #4976 --> Cambio cambian dos campos cambia orden y se agregan campos
			MRTTicketLineTarjeta lineTjta = new MRTTicketLineTarjeta(getCtx(), 0, get_TrxName());
			lineTjta.parseLineTarjeta(lineSplit,fchCabezal,headerId,posicionfila);
			lineTjta.setAD_Client_ID(model.getAD_Client_ID());
			lineTjta.setAD_Org_ID(model.getAD_Org_ID());
			lineTjta.saveEx();

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VTA_LUNCHEON)){ //Venta Lunchon 40
			
			//OpenUp SBT 27/11/2015 Issue #4976 --> Cambio cambian dos campos
			MRTTicketLineLuncheon lineLuncheon = new MRTTicketLineLuncheon(getCtx(),0,get_TrxName());
			lineLuncheon.parseLineLuncheon(lineSplit,fchCabezal,headerId,posicionfila);
			lineLuncheon.setAD_Client_ID(model.getAD_Client_ID());
			lineLuncheon.setAD_Org_ID(model.getAD_Org_ID());
			lineLuncheon.saveEx();
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VTA_ALIMENTOS)){ //Venta ticket Alimentos 90
			
			//OpenUp SBT 27/11/2015 Issue #4976 --> Tipo 90 corresponde a la linea vta luncheon
			MRTTicketLineLuncheon lineLuncheon = new MRTTicketLineLuncheon(getCtx(),0,get_TrxName());
			lineLuncheon.parseLineLuncheon(lineSplit,fchCabezal,headerId,posicionfila);
			lineLuncheon.setAD_Client_ID(model.getAD_Client_ID());
			lineLuncheon.setAD_Org_ID(model.getAD_Org_ID());
			lineLuncheon.saveEx();
				
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VTA_TOTAL)){ //Venta Ticket Total 91
			
			//OpenUp SBT 27/11/2015 Issue #4976 --> CTipo 90 corresponde a la linea vta luncheon
			MRTTicketLineLuncheon lineLuncheon = new MRTTicketLineLuncheon(getCtx(),0,get_TrxName());
			lineLuncheon.parseLineLuncheon(lineSplit,fchCabezal,headerId,posicionfila);
			lineLuncheon.setAD_Client_ID(model.getAD_Client_ID());
			lineLuncheon.setAD_Org_ID(model.getAD_Org_ID());
			lineLuncheon.saveEx();					
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VTA_EFECTIVO)){  //Venta venta efectivo 9

			MRTTicketLineEfectivo lineEfectvio = new MRTTicketLineEfectivo(getCtx(),0,get_TrxName());
			lineEfectvio.parseLineaEfectivo(lineSplit,fchCabezal,headerId,posicionfila);
			lineEfectvio.setAD_Client_ID(model.getAD_Client_ID());
			lineEfectvio.setAD_Org_ID(model.getAD_Org_ID());
			lineEfectvio.saveEx();

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VTA_CTA_CTE)){  //Venta cuenta corriente 104 ahora 127 (27/11/2015)
			
			//OpenUp SBT 27/11/2015 Issue #4976 --> Cambio ya no corresonde a linea de cuenta corriente!!!!!!!!!!!!!
			MRTTicketLineCtaCte lcc = new MRTTicketLineCtaCte(getCtx(),0,get_TrxName());
			lcc.parseLineCtaCte(lineSplit,fchCabezal,headerId,posicionfila);
			lcc.setAD_Client_ID(model.getAD_Client_ID());
			lcc.setAD_Org_ID(model.getAD_Org_ID());
			lcc.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_CTA_CTE)){  //Venta cuenta corriente 99 cambio a 126

			MRTTicketLineClienteCC lClienteCC = new MRTTicketLineClienteCC(getCtx(),0,get_TrxName());
			lClienteCC.parseLineClienteCC(lineSplit,fchCabezal,headerId,posicionfila);
			lClienteCC.setAD_Client_ID(model.getAD_Client_ID());
			lClienteCC.setAD_Org_ID(model.getAD_Org_ID());
			lClienteCC.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_PAGO_SERVICIO)){ //LINEA_PAGO_SERVICIO 47

			MRTTicketLinePagoServ lPgoServ = new MRTTicketLinePagoServ(getCtx(),0,get_TrxName());
			lPgoServ.parsePagoServicios(lineSplit,fchCabezal,headerId,posicionfila);
			lPgoServ.setAD_Client_ID(model.getAD_Client_ID());
			lPgoServ.setAD_Org_ID(model.getAD_Org_ID());
			lPgoServ.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_PAGO_SERVICIOCHECK)){
			
			//OpenUp SBT 01/11/2015 Issue  #4976 ->Se contempla el c�digo 94 pago de servicios con cheque
			MTicketLinePServCheck lServCheck= new MTicketLinePServCheck(getCtx(),0,get_TrxName());
			lServCheck.parseLinePagoServCheck(lineSplit, fchCabezal, headerId, posicionfila);
			lServCheck.setAD_Client_ID(model.getAD_Client_ID());
			lServCheck.setAD_Org_ID(model.getAD_Org_ID());
			lServCheck.saveEx();
			
		//OpenUp SBT 11/12/2015 Isse# 5117 --> lineas nuevas a contemplar 123 y 124
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_PAGOTACRE)){	//linea 123
			
			MRTTicketLinePagoTACRE lPagoTacre = new MRTTicketLinePagoTACRE(getCtx(),0,get_TrxName());
			lPagoTacre.parsePagoTacre(lineSplit, fchCabezal, headerId, posicionfila);
			lPagoTacre.setAD_Client_ID(model.getAD_Client_ID());
			lPagoTacre.setAD_Org_ID(model.getAD_Org_ID());
			lPagoTacre.saveEx();

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_VAUCHER_TJETA_TACRE)){	//linea 124
						
			MRTTicketLineVTarjTACRE lVcherTacre = new MRTTicketLineVTarjTACRE(getCtx(),0,get_TrxName());
			lVcherTacre.parseVaucherTjtaTacre(lineSplit, fchCabezal, headerId, posicionfila);
			lVcherTacre.setAD_Client_ID(model.getAD_Client_ID());
			lVcherTacre.setAD_Org_ID(model.getAD_Org_ID());
			lVcherTacre.saveEx();
			
		//OpenUp SBT 15/12/2015 Isse# 5117 --> lineas nuevas a contemplar 5 y 106
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_CabezalCFE)){	//linea 5
						
			MRTTicketLineCabezalCFE lCabezalCFE = new MRTTicketLineCabezalCFE(getCtx(),0,get_TrxName());
			lCabezalCFE.parseLineCabezalCFE(lineSplit, fchCabezal, headerId, posicionfila);
			lCabezalCFE.setAD_Client_ID(model.getAD_Client_ID());
			lCabezalCFE.setAD_Org_ID(model.getAD_Org_ID());
			lCabezalCFE.saveEx();

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_TktClienteCFE)){//linea 106

			MRTTicketLineTktCteCFE lTktCteCFE = new MRTTicketLineTktCteCFE(getCtx(),0,get_TrxName());
			lTktCteCFE.parseLineTktCteCFE(lineSplit, fchCabezal, headerId, posicionfila);
			lTktCteCFE.setAD_Client_ID(model.getAD_Client_ID());
			lTktCteCFE.setAD_Org_ID(model.getAD_Org_ID());
			lTktCteCFE.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_TOTAL_TICKET)){//linea 4
			//OpenUp SBT 16/12/2015 Issue# 5117	--> Linea nueva a contemplar 4					
			MRTTicketLineTotalTck lTotalTktCFE = new MRTTicketLineTotalTck(getCtx(),0,get_TrxName());
			lTotalTktCFE.parseLineTotalTickt(lineSplit, fchCabezal, headerId, posicionfila);
			lTotalTktCFE.setAD_Client_ID(model.getAD_Client_ID());
			lTotalTktCFE.setAD_Org_ID(model.getAD_Org_ID());
			lTotalTktCFE.saveEx();

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_REDONDEO_IMPOTE_TOTAL)){//linea 63
			//OpenUp SBT 11/01/2015 Issue #5293 	--> Linea nueva a contemplar 63 a tener en cuenta para reporte vta desg por iva				
			MRTTicketLineRedondeo lTotalTktCFE = new MRTTicketLineRedondeo(getCtx(),0,get_TrxName());
			lTotalTktCFE.parseLineRedondeo(lineSplit, fchCabezal, headerId, posicionfila);
			lTotalTktCFE.setAD_Client_ID(model.getAD_Client_ID());
			lTotalTktCFE.setAD_Org_ID(model.getAD_Org_ID());
			lTotalTktCFE.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_DETALLE_BENEFICIO_AL_TOTAL)){//linea 27
			//OpenUp SBT 12/01/2015 Issue #5293 	--> Linea nueva a contemplar 27 a tener en cuenta para reporte vta desg por iva				
			MRTTicketBenefAlTotal lTotalTktCFE = new MRTTicketBenefAlTotal(getCtx(),0,get_TrxName());
			lTotalTktCFE.parseBenefTotal(lineSplit, fchCabezal, headerId, posicionfila);
			lTotalTktCFE.setAD_Client_ID(model.getAD_Client_ID());
			lTotalTktCFE.setAD_Org_ID(model.getAD_Org_ID());
			lTotalTktCFE.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_DESC_AL_TOTAL)){//linea 28
			//OpenUp SBT 12/01/2015 Issue #5293 	--> Linea nueva a contemplar 28 a tener en cuenta para reporte vta desg por iva				
			MRTTicketDescAlTotal lTotalTktCFE = new MRTTicketDescAlTotal(getCtx(),0,get_TrxName());
			lTotalTktCFE.parseDescTotal(lineSplit, fchCabezal, headerId, posicionfila);
			lTotalTktCFE.setAD_Client_ID(model.getAD_Client_ID());
			lTotalTktCFE.setAD_Org_ID(model.getAD_Org_ID());
			lTotalTktCFE.saveEx();
			
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_DEV_PGO_SERVICIO)){//linea 81
				//OpenUp SBT 19/01/2015 Issue #5329	--> Linea nueva a contemplar 81 devolucion de pago de servicio		
				MRTTicketLineDevPagoSer lineDevPgoServ = new MRTTicketLineDevPagoSer(getCtx(),0,get_TrxName());
				lineDevPgoServ.parseDevPagoServicios(lineSplit, fchCabezal, headerId, posicionfila);
				lineDevPgoServ.setAD_Client_ID(model.getAD_Client_ID());
				lineDevPgoServ.setAD_Org_ID(model.getAD_Org_ID());
				lineDevPgoServ.saveEx();	
		}else {//Se guardan las restantes lineas en el log de lineas UY_LogFile
			
			MRTLogFile logF = new MRTLogFile(getCtx(),0,get_TrxName());
			logF.setAD_Client_ID(model.getAD_Client_ID());
			logF.setAD_Org_ID(model.getAD_Org_ID());
			logF.setUY_RT_Ticket_Header_ID(headerId);
			logF.setdatofila(line);
			logF.setDescription("TipoLinea: "+lineSplit[2]);
			//Se agrega linea tipo 19-09-2015 SBouissa (control de lineas no contempladas)
			logF.settipo(lineSplit[2]);
			logF.setpositionfile(posicionfila);
			logF.setName(mAudit.getFileName());
			logF.saveEx();

		}
		// VER SI CORRESPONDE AGREGAR TABLA Y DEMAS PARA L�nea de Cupones Numeros (99)-SM. ---VERRRRRRRRRRRRRRRRRRRRRRRR	
		//}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicketPazos.LINEA_MTO_PMO_EFECT)){//--> SE Consulto y no se debe considerar por el momento
		//Verificar si es necesario parametrizar L�nea de seteo de monto de prestamo en efectivo (104)
	
	}
	
	
	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param dest
	 */
	private void auditarResultado(File destino) {

		//BigDecimal consistencia = new BigDecimal(0);
		//consistencia = this.sumoCabezales.subtract(this.sumaLineas);
		mAudit.setpath(destino.getAbsolutePath());
		mAudit.settotallinesfile(String.valueOf(cantTotal));
		mAudit.settotalheaders(String.valueOf(cantCabezal));
		mAudit.setTotalLines(String.valueOf(cantLineas));
		
		mAudit.setUY_RT_LoadTicket_ID(model.get_ID());
		//mAudit.setamtconsistenciavtas(consistencia);
		mAudit.saveEx();
		
		//INI Calculos lineas ventaEfectivo Pesos----------
		BigDecimal a = MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","1",model.get_ID(),get_TrxName());
		BigDecimal b = MRTTicketLineEfectivo.calcularCambioMoneda(getCtx(),"2","1",model.get_ID(),get_TrxName());

		mAudit.setamtvtaefectivo(a.subtract(b));
		
			// Sodexo
		mAudit.setamtvtaefectivosodexo(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","10",model.get_ID(),get_TrxName()));
	
			// Cheques
		mAudit.setamtvtacheque(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","3",model.get_ID(),get_TrxName()));
		
		// Cheques dolares OpenUp SBT 15/12/2015
		mAudit.setamtvtachequedlrs(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"2","3",model.get_ID(),get_TrxName()));
		
		// Tajeta manual
		mAudit.setamtvtatarjetaofline(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","14",model.get_ID(),get_TrxName()));
		
		// Devolucion de envases
		mAudit.setamtvtadevenvases(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","13",model.get_ID(),get_TrxName()));
				
		//Calculos lineas ventaEfectivo Dolares no se le sustrae el cambio porque ya esta contemplado en el calculo en pesos porque el cambio esta dado en pesos
		mAudit.setamtvtaefectivodolares(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"2","1",model.get_ID(),get_TrxName()));

		// Calculo monto total retiro -- Moneda pesos = 1, MedioPago = 1 efectivo
		mAudit.setamtvtaretiro(MRTTicketLineRetiro.calcularMontoRetiro(getCtx(),"1","1",model.get_ID(),get_TrxName()));
		
		// Calculo monto factura
		mAudit.setamtvtafacturas2(MRTTicketLineFactura.calcularMontoXTipoCabezal(getCtx(),"11",model.get_ID(),get_TrxName()));
		
		// Calculo monto nota de credito -- se comenta temporalmente porque no esta dentro del reporte diario
		//mAudit.setamtnodefinido(MRTTicketLineFactura.calcularMontoXTipoCabezal(getCtx(),"16",model.get_ID(),get_TrxName()));

		// Calculo monto FONDOS - PESOS 
		mAudit.setamtvtafondeo(MRTTicketLineFondeo.calcularMontoFondeo(getCtx(),"1",model.get_ID(),get_TrxName()));

		// Calculo monto de tarjeta ----> OJO SU HAY CAMBIO
		mAudit.setamtvtatarjeta(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"1","1",true,model.get_ID(),get_TrxName()));
		
		// Calculo monto de tarjeta en dolares  OpenUp SBT 15/12/2015 Issue #
		mAudit.setamtvtatarjetadlrs(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"2","1",true,model.get_ID(),get_TrxName()));

		// Calculo monto de tarjeta manual (ofline) OJO SU HAY CAMBIO
		mAudit.setamtvtatarjetacuota(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"1","1",false,model.get_ID(),get_TrxName()));

		// Calculo venta ticket luncheon pesos
		mAudit.setamtvtaluncheon(MRTTicketLineLuncheon.calcularMontoPorMoneda(getCtx(),"1",model.get_ID(),get_TrxName()));
		
		// Calculo cuenta corriente
		mAudit.setamtvtaclientesfidelizacion(MRTTicketLineCtaCte.calcularPorMoneda(getCtx(),"1",model.get_ID(),get_TrxName()));

		// Calculo servicios
		//SBT Issue #5329 se debe calcular la devoluci�n de servicio para restar al pago
		BigDecimal pgoServ = MRTTicketLinePagoServ.calcularMontoTotal(getCtx(),"1",model.get_ID(),get_TrxName());
		BigDecimal devPgoServ = MRTTicketLineDevPagoSer.calcularMontoTotalDevolucion(getCtx(),"1",model.get_ID(),get_TrxName());
		mAudit.setamtvtapagodeservicio(pgoServ.add(devPgoServ));
		//mAudit.setamtvtapagodeservicio(MRTTicketLinePagoServ.calcularMontoTotal(getCtx(),"1",model.get_ID(),get_TrxName()));
		
		// INI mejora OpenUp SBT 02/11/2015 Issue #4976   
		//Venta credito 
		mAudit.setAmtVtaCredito((MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","9",model.get_ID(),get_TrxName())));
	
		// Venta credito dolares  OpenUp SBT 15/12/2015 Issue #
		mAudit.setAmtVtaCreditoDlrs((MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"2","9",model.get_ID(),get_TrxName())));
		
		//Calculo monto FONDOS - DOLARES  Se debe diferenciar entre los montos en dolares y pesos 
		mAudit.setVtaFondeoDolares((MRTTicketLineFondeo.calcularMontoFondeo(getCtx(),"2",model.get_ID(),get_TrxName())));
		
		//OpenUp SBT 11/12/2015 CACULAR VENTA SODEXO/EDENRED PESOS CODIGO_MEDIO_PAGO = 23 
		mAudit.setVtaTjaEdenredSodexo((MRTTicketLineVTarjTACRE.calcularMontoEdenredSodexo(getCtx(),"00",model.get_ID(),get_TrxName())));

		//CALCULAR VENTA CREDIT EN PESOS CODIGO_MEDIO_PAGO = 9
				
		mAudit.setEndTime(new Timestamp (System.currentTimeMillis())); //Seteo fecha y hora de comienzo
		mAudit.saveEx();
	}
}

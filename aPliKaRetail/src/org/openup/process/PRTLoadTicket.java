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
import org.compiere.model.MClient;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.openup.model.MRTTicketCancelItem;
import org.openup.model.MRTTicketHeader;
import org.openup.model.MRTTicketLineItem;
import org.openup.model.MRTTicketLineClienteCC;
import org.openup.model.MRTTicketLineCtaCte;
import org.openup.model.MRTTicketLineEfectivo;
import org.openup.model.MRTTicketLineFactura;
import org.openup.model.MRTTicketLineFondeo;
import org.openup.model.MRTTicketLineItemReturn;
import org.openup.model.MRTTicketLineLuncheon;
import org.openup.model.MRTTicketLinePagoServ;
import org.openup.model.MRTTicketLineRetiro;
import org.openup.model.MRTTicketLineTarjeta;
import org.openup.model.MRTLogFile;
import org.openup.model.MRTAuditLoadTicket;
import org.openup.model.MRTLoadTicket;
import org.openup.model.MRTTicketType;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtilsRT;



/**
 * @author SBouissa
 *
 */
public class PRTLoadTicket extends SvrProcess {

	//variables
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
	private static final String LINEA_VTA_EFECTIVO = "9";
	private static final String LINEA_VTA_CTA_CTE = "104";
	private static final String LINEA_CTA_CTE = "99";
	private static final String LINEA_PAGO_SERVICIO = "47";
	
	private static final String CHAR_SEPARATOR = "#";
	//10/09/2015
	private static final String LINEA_DEVITEM = "20";
	//17/09/2015
	private static final String LINEA_CANCELITEM = "12";
	
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
	// Fin variables
	
	
	/**
	 * Constructor 
	 */
	public PRTLoadTicket() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		//Creo modelo MRTloadTicket
		this.model = new MRTLoadTicket(getCtx(), this.getRecord_ID(), null);
	//	model.setProcessed(true);
		
		if(model.isManual()){//Proceso manual seleccionando un archivo
			if(model.getFileName1()==null||model.getFileName1().equals("")){
				throw new AdempiereException("Seleccione archivo para procesar");
			}
			log.log(Level.SEVERE, "INICO PRLOADTICKET:"+new Timestamp (System.currentTimeMillis()).toString());
			MUser u = new MUser(getCtx(),this.getAD_User_ID(),null);
			this.model.setName("Manual_"+new Timestamp (System.currentTimeMillis()).toString()+"_"+u.getName());
			File aux = new File(this.model.getFileName1());
			this.model.setFileName(aux.getName());
			log.log(Level.SEVERE, "Copia Archivo:"+new Timestamp (System.currentTimeMillis()).toString());
			this.model.setisfilefound(copyFileFromSisteco(yesterday));
			log.log(Level.SEVERE, "Fin Copia Archivo:"+new Timestamp (System.currentTimeMillis()).toString());
 //			String rutatotal = this.model.getFileName1();  //152430 
//			rutatotal = rutatotal.replace("/", ",");
//			String[] res = rutatotal.split(",");
//			for(int i = 0;i<res.length-1;i++){
//				if(i==0){
//					rutaOrigen= res[i];
//				}else if(i==res.length-2){
//					rutaOrigen=rutaOrigen+File.separator+res[i]+File.separator;
//				}else{
//					rutaOrigen=rutaOrigen+File.separator+res[i];
//				}
//			}
			rutaOrigen = getRutaOrigen();  	
			rutaDestino = getRutaDestino();  
			this.model.saveEx();	
		}else{// Proceso autom�tico levanta archivo del dia anterior y procesa
			//model.setDescription("");
			log.log(Level.SEVERE, "INICO PRLOADTICKET:"+new Timestamp (System.currentTimeMillis()).toString());
			String[] fecha = new Timestamp (System.currentTimeMillis()- 1000L * 60L * 60L * 24L).toString().split("-");
			//Formato fecha YYYYMMdd
			String fechaArch = fecha[0]+fecha[1]+fecha[2].substring(0, 2);
			yesterday = fechaArch;
			//yesterday = "20150524";
			//Guardo el nombre del archivo que se debe procesar		
			this.model.setFileName(getInitFile()+yesterday);
			
			log.log(Level.SEVERE, "Copia SISTECO:"+new Timestamp (System.currentTimeMillis()).toString());
			//Traemos archivo de ayer desde servidor de sisteco si es true, se encontro el archivo.
			this.model.setisfilefound(copyFileFromSisteco(yesterday));
			log.log(Level.SEVERE, "Fin Copia SISTECO:"+new Timestamp (System.currentTimeMillis()).toString());
			//Se obtiene ruta de origen y ruta de destino
			rutaOrigen = getRutaOrigen();  	
			rutaDestino = getRutaDestino(); 
			this.model.setName("Automatico_"+new Timestamp (System.currentTimeMillis()).toString());
			client = this.getDefaultClient();
			this.model.setAD_Client_ID(client.get_ID());
			this.model.saveEx();
		}
		this.idMRTLT = this.model.get_ID();

	}

	/**Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 15/05/2015
	 * @return
	 */
	private MClient getDefaultClient() {		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
			
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
			
		return value;
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
						fileFound = true;	
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

	/**Retorna la parametrizacion del inicio del nombre de los archivos salida pazos
	 * OpenUp Ltda Issue#40324032
	 * @author Sylvie Bouissa 12/05/2015
	 * @return
	 */
	private String getInitFile() {
		String  nombre = MSysConfig.getValue("SISTECO_INIT_FILE",0); //Directorio origen prametrizado
		return nombre;
	}

	/**
	 * OpenUp Ltda Issue#40324032
	 * @author Sylvie Bouissa 12/05/2015
	 * @return
	 */
	private String getRutaOrigenSisteco() {
		String  origen = MSysConfig.getValue("SOURCE_DIRECTORY_SISTECO",0); //Directorio origen parametrizado
		return origen;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @return
	 */
	private String getRutaDestino() {
		// TODO Auto-generated method stub 
		//String destino = "C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"+File.separator;
		String  destino = MSysConfig.getValue("DESTINATION_DIRECTORY_RT",0); //Directorio dest prametrizado
		return destino;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @return
	 */
	private String getRutaOrigen() {
		// TODO Auto-generated method stub
		//String  origen ="C:"+File.separator+"FilesCovadonga"+File.separator+"RutaOrigen"+File.separator;
		String  origen = MSysConfig.getValue("SOURCE_DIRECTORY_RT",0); //Directorio origen prametrizado
		return origen;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		log.log(Level.SEVERE, "INIT DOIT: "+new Timestamp (System.currentTimeMillis()).toString());
		
		String retorno = "";
		if(this.model!=null){//1 
			//log.log(Level.SEVERE, "MODEL OK: "+new Timestamp (System.currentTimeMillis()).toString());

			File origen = null; File destino = null;
			//Consulto si se encontro el archivo del dia anterior en serv sisteco
			if(this.model.isfilefound()){//2
				//Consulto si existe ruta origen y destino
				if(!rutaOrigen.equals("") && !rutaDestino.equals("")){//3
					
					origen = new File(rutaOrigen);
					destino = new File(rutaDestino);

				//	log.log(Level.SEVERE, "ORIGEN DESTINO OK: "+new Timestamp (System.currentTimeMillis()).toString());

					// Listo los archivos que se encuentran en el directorio origen	
					String[] files = origen.list();
					//Obtengo la posicion del archivo que tengo que procesar
					int posicion = obtenerPosicionAProcesar(files);		
				//	log.log(Level.SEVERE, "ARCHIVOS: "+files.length+" "+new Timestamp (System.currentTimeMillis()).toString());
		
					if(0<=posicion){//4
						//Si el archivo no esta procesado se prosigue
						if(!archivoProcesado(files[posicion])){//5
					//		log.log(Level.SEVERE, "INIT PROC: "+new Timestamp (System.currentTimeMillis()).toString());

							//proc = true;
							fileNameProcessed = files[posicion];
							//Tener en cuenta que para servidores se debe colocar / para 
							//Windows se debe colocar \\
							
							File source = new File(origen + getFileSeparator() + files[posicion]);
							File dest = new File(destino + getFileSeparator() + "Procesado_"+ files[posicion]);
							//if(source!=null){//6
								log.log(Level.SEVERE, "INI Procesar archivo:"+new Timestamp (System.currentTimeMillis()).toString());

								retorno = procesarArchivo(source);
								log.log(Level.SEVERE, " FIN Procesar archivo:"+new Timestamp (System.currentTimeMillis()).toString());
		
								if(retorno.equals("OK")){//7
									//log.log(Level.SEVERE, "Salvo modelo:"+new Timestamp (System.currentTimeMillis()).toString());
									model.setProcessed(true);
									//model.setDescription("Archivo procesado exitosamente!!");
									model.saveEx();
									try {
										log.log(Level.SEVERE, "INI Auditoria:"+new Timestamp (System.currentTimeMillis()).toString());
										//Se crea auditoria de lo procesado
										auditarResultado(dest);
										log.log(Level.SEVERE, "FIN Auditoria:"+new Timestamp (System.currentTimeMillis()).toString());
										log.log(Level.SEVERE, "INI Copia historico:"+new Timestamp (System.currentTimeMillis()).toString());
										//Se copia archivo a carpeta historico
										copyFile(source, dest);	
										log.log(Level.SEVERE, "Fin Copia historico:"+new Timestamp (System.currentTimeMillis()).toString());

										source.delete();
										//source=null;
										dest = null;
//										model = null;
//										model = new MRTLoadTicket(getCtx(), this.idMRTLT, null);
//										model.setProcessed(true);
//										model.setDescription("Archivo procesado exitosamente!!");
									//  log.log(Level.SEVERE, "INI SAVE EX AUDIT:"+new Timestamp (System.currentTimeMillis()).toString());

										//model.save(); OJO NO GUARDA ??
										mAudit.setDescription("Archivo procesado exitosamente!!");
										mAudit.saveEx();
									//	log.log(Level.SEVERE, "FIN SAVE EX AUDIT:"+new Timestamp (System.currentTimeMillis()).toString());
										
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
//							}else {//6
//								this.model.setDescription("Archivo origen vacio");
//								model.saveEx();
//								retorno = "Archivo orgen vacio";	
//							}
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
								
//							}else{
//								this.model.setDescription("Archivo procesado exitosamente!!");
//								model.saveEx();
//								retorno= "OK";
//								continue;
//							}										
					//	}
					//}
					
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
		//return retorno;
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

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 13/05/2015
	 * @return
	 */
	private String getFileSeparator() {//separator file
		String  nombre = MSysConfig.getValue("SEPARATOR_FILE_RT",0); //Directorio origen prametrizado
		
		return nombre;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param source
	 * @param dest
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
		//BigDecimal a = MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","1",model.get_ID(),null);//21-05-2015
		BigDecimal b = MRTTicketLineEfectivo.calcularCambioMoneda(getCtx(),"2","1",model.get_ID(),get_TrxName());
		//BigDecimal b = MRTTicketLineEfectivo.calcularCambioMoneda(getCtx(),"2","1",model.get_ID(),null);//21-05-2015
		mAudit.setamtvtaefectivo(a.subtract(b));
		
		
			// Sodexo
		mAudit.setamtvtaefectivosodexo(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","10",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtaefectivosodexo(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","10",model.get_ID(),null));//21-05-2015
		
			// Cheques
		mAudit.setamtvtacheque(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","3",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtacheque(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","3",model.get_ID(),null));//21-05-2015
		
			// Tajeta manual
		mAudit.setamtvtatarjetaofline(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","14",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtatarjetaofline(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","14",model.get_ID(),null));//21-05-2015
		
			// Devolucion de envases
		mAudit.setamtvtadevenvases(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","13",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtadevenvases(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","13",model.get_ID(),null));//21-05-2015
		
		//FIN Calculos lineas ventaEfectivo Pesos----------
		
		//Calculos lineas ventaEfectivo Dolares no se le sustrae el cambio porque ya esta contemplado en el calculo en pesos porque el cambio esta dado en pesos
		mAudit.setamtvtaefectivodolares(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"2","1",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtaefectivodolares(MRTTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"2","1",model.get_ID(),null));//21-05-2015

		// Calculo monto total retiro -- Moneda pesos = 1, MedioPago = 1 efectivo
		mAudit.setamtvtaretiro(MRTTicketLineRetiro.calcularMontoRetiro(getCtx(),"1","1",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtaretiro(MRTTicketLineRetiro.calcularMontoRetiro(getCtx(),"1","1",model.get_ID(),null));//21-05-2015
		
		// Calculo monto factura
		mAudit.setamtvtafacturas2(MRTTicketLineFactura.calcularMontoXTipoCabezal(getCtx(),"11",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtafacturas2(MRTTicketLineFactura.calcularMontoXTipoCabezal(getCtx(),"11",model.get_ID(),null));//21-05-2015
		
		// Calculo monto nota de credito -- se comenta temporalmente porque no esta dentro del reporte diario
		//mAudit.setamtnodefinido(MRTTicketLineFactura.calcularMontoXTipoCabezal(getCtx(),"16",model.get_ID(),get_TrxName()));
		//mAudit.setamtnodefinido(MRTTicketLineFactura.calcularMontoXTipoCabezal(getCtx(),"16",model.get_ID(),null));//21-05-2015

		// Calculo monto  -- Se agrega la moneda por calculo nuevo 02/11/2015 SBT
		mAudit.setamtvtafondeo(MRTTicketLineFondeo.calcularMontoFondeo(getCtx(),"1",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtafondeo(MRTTicketLineFondeo.calcularMontoFondeo(getCtx(),model.get_ID(),null));//21-05-2015

		// Calculo monto de tarjeta ----> OJO SU HAY CAMBIO
		mAudit.setamtvtatarjeta(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"1","1",true,model.get_ID(),get_TrxName()));
		//mAudit.setamtvtatarjeta(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"1","1",true,model.get_ID(),null));//21-05-2015

		// Calculo monto de tarjeta manual (ofline) OJO SU HAY CAMBIO
		mAudit.setamtvtatarjetacuota(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"1","1",false,model.get_ID(),get_TrxName()));
		//mAudit.setamtvtatarjetacuota(MRTTicketLineTarjeta.calcularMontoTarjetaXCuotas(getCtx(),"1","1",false,model.get_ID(),null));//21-05-2015

		// Calculo venta ticket luncheon pesos
		mAudit.setamtvtaluncheon(MRTTicketLineLuncheon.calcularMontoPorMoneda(getCtx(),"1",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtaluncheon(MRTTicketLineLuncheon.calcularMontoPorMoneda(getCtx(),"1",model.get_ID(),null));//21-05-2015
		// Calculo cuenta corriente
		mAudit.setamtvtaclientesfidelizacion(MRTTicketLineCtaCte.calcularPorMoneda(getCtx(),"1",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtaclientesfidelizacion(MRTTicketLineCtaCte.calcularPorMoneda(getCtx(),"1",model.get_ID(),null));//21-05-2015
		// Calculo servicios
		mAudit.setamtvtapagodeservicio(MRTTicketLinePagoServ.calcularMontoTotal(getCtx(),"1",model.get_ID(),get_TrxName()));
		//mAudit.setamtvtapagodeservicio(MRTTicketLinePagoServ.calcularMontoTotal(getCtx(),"1",model.get_ID(),null));//21-05-2015
		
		mAudit.setEndTime(new Timestamp (System.currentTimeMillis())); //Seteo fecha y hora de comienzo
		mAudit.saveEx();
	}

	/**SE TIENE EN CUENTA QUE EL NOMBRE DE LOS ARCHIVOS ES DEL TIPO SalidaPazos-1-20150813.236
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 16/9/2015
	 * @param fileName
	 * @return
	 */
	private Timestamp parseDateFromName(String fileName) {
		if(fileName.length()>0){
			String res = fileName.substring(14, 22); //SalidaPazos-1-20150813.236
			
			return Convertir.converTimestampYYMMdd(res);
		}
		return null;
	}

	/**Se leen cada una de las lineas y dependiendo de si es cabezal o linea se persiste en la tabla correspondiente
	 * OpenUp Ltda Issue#4032 3952
	 * @author Sylvie Bouissa 07/05/2015
	 * @param source
	 * @return
	 */
	private String procesarArchivo(File file) {
		//String error="";
		
		FileReader fr = null;
		BufferedReader br = null;
		try{
			
			//Instancio la auditoria
			mAudit = new MRTAuditLoadTicket(getCtx(), 0, get_TrxName()); 
			//mAudit = new MRTAuditLoadTicket(getCtx(), 0, null); //21-05-2015
			//Se indica AD_Client_ID correspondiente
			if(client!=null){
				mAudit.setAD_Client_ID(client.get_ID());
			}
			//Se indica fecha y hora de comienzo
			mAudit.setStartTime(new Timestamp (System.currentTimeMillis()));
			//Se indica nombre de la auditoria indicando la fecha actual
			mAudit.setName("Auditoria"+mAudit.getStartTime());
			//Se guarda nombre de archivo
			mAudit.setFileName(fileNameProcessed);
			
			//16-09-2015 SBouissa  Se agrega campo de fecha que indica la fecha de los datos del archivo (para Arqueo NSarlabos) 
			Timestamp fechArch = parseDateFromName(mAudit.getFileName());
			mAudit.set_ValueOfColumn("DateValue", fechArch); //mAudit.set_ValueOfColumn("DateValue", fechArch);
			
			//Guardo orgen de lectura
			mAudit.setDescription("Origen: "+file.getAbsolutePath());
			//Seteo nombre de modelo con el nombre del archivo que se esta procesando
			//this.model.set_ValueOfColumn("Description", "Archivo: "+fileNameProcessed);
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
					if (tipo.equalsIgnoreCase(PRTLoadTicket.CABEZAL)){
						// Es cabezal
						//log.log(Level.SEVERE, cont+"_CabezalINI: "+new Timestamp (System.currentTimeMillis()).toString());

						cantCabezal++;
						mConH = parseHeader(line,String.valueOf(cont));	
						//log.log(Level.SEVERE, "CabezalFIN: "+new Timestamp (System.currentTimeMillis()).toString());

					} else if (tipo.equalsIgnoreCase(PRTLoadTicket.LINEA)){		// Es linea
						//log.log(Level.SEVERE,  cont+"_LineaINI: "+new Timestamp (System.currentTimeMillis()).toString());

						cantLineas++;
						parseLine(line, mConH.get_ID(),String.valueOf(cont));
						//log.log(Level.SEVERE, "LineaFIN: "+new Timestamp (System.currentTimeMillis()).toString());

					}			
				}catch(Exception ex){
					log.log(Level.SEVERE, "Error: "+ ex.getMessage());
					throw new AdempiereException(ex.getMessage());
				}
				line = br.readLine();
				cantTotal++;
			}
			
		}catch(Exception e){
			//error = e.getMessage();
			throw new AdempiereException(e.getMessage());
		}finally{
			if(br!=null){
				try {
					br.close();
					if(fr!=null){
						fr.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//OpenUpUtils.closeClosable(br);
		}
		log.log(Level.SEVERE, "ProcesarArvhico: "+new Timestamp (System.currentTimeMillis()).toString());
		return "OK";
		//return "ERROR "+error;
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param line
	 * @param get_ID
	 * @param valueOf
	 */
	private void parseLine(String line, int headerId, String posicionfila) {
		String lineSplit[] = line.split(PRTLoadTicket.CHAR_SEPARATOR);
		
		if(lineSplit[2].equals(PRTLoadTicket.LINEA_VENTA)){//Linea de venta 1
			//log.log(Level.SEVERE,  posicionfila+"INI_LineaITEM: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineItem lineTktVenta = new MRTTicketLineItem(getCtx(),0,get_TrxName());
			//MRTTicketLineItem lineTktVenta = new MRTTicketLineItem(getCtx(),0,null);//21-05-2015
			lineTktVenta.parseLineItemVta(lineSplit,fchCabezal,headerId,posicionfila);
			lineTktVenta.saveEx();
			//log.log(Level.SEVERE,  posicionfila+"FIN_LineaITEM: "+new Timestamp (System.currentTimeMillis()).toString());

//INI - OpenUp se agrega el control al tipo de linea 20 - SBouissa 10-09-2015
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_DEVITEM)){//Linea de devolucion de item 20
			MRTTicketLineItemReturn lineTktVentaDev = new MRTTicketLineItemReturn(getCtx(),0,get_TrxName());
			lineTktVentaDev.parseLineItemReturn(lineSplit,fchCabezal,headerId,posicionfila);
			lineTktVentaDev.saveEx();
			//Creo linea de item para que se contabilice negativamente 
			MRTTicketLineItem lineTktVenta = new MRTTicketLineItem(getCtx(),0,get_TrxName());
			lineTktVenta.parseLineItemVtaOpenUp(lineTktVentaDev,headerId);
			lineTktVenta.saveEx();
//FIN - OpenUp se agrega el control al tipo de linea 20 - SBouissa 10-09-2015
//INI - OpenUp se agrega el control al tipo de linea 12 - SBouissa 17-09-2015			// I 
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_CANCELITEM)){ //Linea cancel item 12

			MRTTicketCancelItem lineaCancel = new MRTTicketCancelItem(getCtx(),0,get_TrxName());
			lineaCancel.parseLineCancelItem(lineSplit,fchCabezal,headerId,posicionfila);
			lineaCancel.saveEx();
			//Se deben recorrer las lineas creadas anteriormente para ver si corresponde crear una linea que contrareste
			MRTTicketLineItem noCancelada = MRTTicketLineItem.getLineCancelItem(getCtx(),
					lineaCancel.getcodigoartsubf(),lineaCancel.getnrolineaventa(),headerId,get_TrxName());
			if(noCancelada.getlineacancelada() == 0 && noCancelada.getsiesobsequio().equals("0") ){			
				//Si no esta cancelada creao linea para contrarestar totales
				MRTTicketLineItem aux = new MRTTicketLineItem(getCtx(), 0, get_TrxName());
				aux.parseLineItemVtaOpenUpCancel(lineaCancel, noCancelada,headerId);
				aux.saveEx();
			}
			
//FIN - OpenUp se agrega el control al tipo de linea 12 - SBouissa 17-09-2015			// I 
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_RETIRO)){ //Linea retiro 97
			//log.log(Level.SEVERE,  posicionfila+"INI_LineaRETIRO: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineRetiro lineaRetiro = new MRTTicketLineRetiro(getCtx(),0,get_TrxName());
			//MRTTicketLineRetiro lineaRetiro = new MRTTicketLineRetiro(getCtx(),0,null);//21-05-2015
			lineaRetiro.parseLineRetiro(lineSplit,fchCabezal,headerId,posicionfila);
			lineaRetiro.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaRETIRO: "+new Timestamp (System.currentTimeMillis()).toString());


		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_FACTURA)){// Liena Factura "92"
		//	log.log(Level.SEVERE,  posicionfila+"INI_LineaFACTURA: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineFactura lineFactura = new MRTTicketLineFactura(getCtx(),0,get_TrxName());
			//MRTTicketLineFactura lineFactura = new MRTTicketLineFactura(getCtx(),0,null);//21-05-2015
			lineFactura.parseLineFactura(lineSplit,fchCabezal,headerId,posicionfila);
			lineFactura.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaFACTURA: "+new Timestamp (System.currentTimeMillis()).toString());

			
		}else if(lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_FONDEO)){ //linea fondeo 95
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaFONDEO: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineFondeo lineFondeo = new MRTTicketLineFondeo(getCtx(),0,get_TrxName());
			//MRTTicketLineFondeo lineFondeo = new MRTTicketLineFondeo(getCtx(),0,null);//21-05-2015
			lineFondeo.parseLineFondeo(lineSplit,fchCabezal,headerId,posicionfila);
			lineFondeo.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaFONDEO: "+new Timestamp (System.currentTimeMillis()).toString());


// ------------------- Tipos de ventas -------------------
		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_VTA_TARJETA)){ //Venta Tarjeta 37
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaTARJETA: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineTarjeta lineTjta = new MRTTicketLineTarjeta(getCtx(), 0, get_TrxName());
			//MRTTicketLineTarjeta lineTjta = new MRTTicketLineTarjeta(getCtx(), 0, null);//21-05-2015
			lineTjta.parseLineTarjeta(lineSplit,fchCabezal,headerId,posicionfila);
			lineTjta.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaTARJETA: "+new Timestamp (System.currentTimeMillis()).toString());


		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_VTA_LUNCHEON)){ //Venta Lunchon 40
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaLUNCHEON: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineLuncheon lineLuncheon = new MRTTicketLineLuncheon(getCtx(),0,get_TrxName());
			//MRTTicketLineLuncheon lineLuncheon = new MRTTicketLineLuncheon(getCtx(),0,null);//21-05-2015
			lineLuncheon.parseLineLuncheon(lineSplit,fchCabezal,headerId,posicionfila);
			lineLuncheon.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaLUNCHEON: "+new Timestamp (System.currentTimeMillis()).toString());


		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_VTA_EFECTIVO)){  //Venta venta efectivo 9
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaEFECTIVO: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineEfectivo lineEfectvio = new MRTTicketLineEfectivo(getCtx(),0,get_TrxName());
			//MRTTicketLineEfectivo lineEfectvio = new MRTTicketLineEfectivo(getCtx(),0,null);//21-05-2015
			lineEfectvio.parseLineaEfectivo(lineSplit,fchCabezal,headerId,posicionfila);
			lineEfectvio.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaEFECTIVO: "+new Timestamp (System.currentTimeMillis()).toString());

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_VTA_CTA_CTE)){  //Venta cuenta corriente 104
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaCTACTE: "+new Timestamp (System.currentTimeMillis()).toString());
			
			MRTTicketLineCtaCte lcc = new MRTTicketLineCtaCte(getCtx(),0,get_TrxName());
			//MRTTicketLineCtaCte lcc = new MRTTicketLineCtaCte(getCtx(),0,null);//21-05-2015
			lcc.parseLineCtaCte(lineSplit,fchCabezal,headerId,posicionfila);
			lcc.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaCTACTE: "+new Timestamp (System.currentTimeMillis()).toString());

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_CTA_CTE)){  //Venta cuenta corriente 99
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaCteCC: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLineClienteCC lClienteCC = new MRTTicketLineClienteCC(getCtx(),0,get_TrxName());
			//MRTTicketLineClienteCC lClienteCC = new MRTTicketLineClienteCC(getCtx(),0,null);//21-05-2015
			lClienteCC.parseLineClienteCC(lineSplit,fchCabezal,headerId,posicionfila);
			lClienteCC.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaCteCC: "+new Timestamp (System.currentTimeMillis()).toString());

		}else if (lineSplit[2].equalsIgnoreCase(PRTLoadTicket.LINEA_PAGO_SERVICIO)){ //LINEA_PAGO_SERVICIO 47
		//	log.log(Level.SEVERE,  posicionfila+"IN_LineaPAGSERV: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTTicketLinePagoServ lPgoServ = new MRTTicketLinePagoServ(getCtx(),0,get_TrxName());
			//MRTTicketLinePagoServ lPgoServ = new MRTTicketLinePagoServ(getCtx(),0,null);//21-05-2015
			lPgoServ.parsePagoServicios(lineSplit,fchCabezal,headerId,posicionfila);
			lPgoServ.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_LineaPAGSERV: "+new Timestamp (System.currentTimeMillis()).toString());

		}else {//Se guardan las restantes lineas en el log de lineas UY_LogFile
		//	log.log(Level.SEVERE,  posicionfila+"IN_GENERICA: "+new Timestamp (System.currentTimeMillis()).toString());

			MRTLogFile logF = new MRTLogFile(getCtx(),0,get_TrxName());
			//MRTLogFile logF = new MRTLogFile(getCtx(),0,null);//21-05-2015
			logF.setUY_RT_Ticket_Header_ID(headerId);
			logF.setdatofila(line);
			logF.setDescription("TipoLinea: "+lineSplit[2]);
			//Se agrega linea tipo 19-09-2015 SBouissa (control de lineas no contempladas)
			logF.settipo(lineSplit[2]);
			logF.setpositionfile(posicionfila);
			logF.setName(mAudit.getFileName());
			logF.saveEx();
		//	log.log(Level.SEVERE,  posicionfila+"FIN_GENERICA: "+new Timestamp (System.currentTimeMillis()).toString());

		}
		//}else if (lineSplit[2].equals("94")){
		//System.out.println("--------------------94");
	}

	/**
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param line
	 * @param valueOf
	 * @return
	 */
	private MRTTicketHeader parseHeader(String line, String position) {
		//Insatanciamos el modelo de cabezal
		MRTTicketHeader ret = new MRTTicketHeader(getCtx(), 0, get_TrxName());
		//MRTTicketHeader ret = new MRTTicketHeader(getCtx(), 0, null);//21-05-2015
		//log.log(Level.SEVERE, "_CabezalINI: "+new Timestamp (System.currentTimeMillis()).toString());
		//Separamos los campos de la linea 
		String lineSplit[] = line.split(PRTLoadTicket.CHAR_SEPARATOR);
		
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
				//FIN OpenUp Lda SBouissa 07-09-2015 A pedido de GV se agrega el per�odo.
				if(MRTTicketHeader.vVenta.equals(lineSplit[1])){// se trata de un tiket de venta
					//if(!lineSplit[11].equals("0")){ //si es cero es un ticket normal sino se deben guardar los siguientes datos
					if(!(MRTTicketHeader.vVentaNormal.equals(lineSplit[11]))){
						if (lineSplit[11] !=null) ret.setcodigocajadevolucion(Integer.valueOf(lineSplit[10]));
						if (lineSplit[12] !=null) ret.setnumeroticketdevolucion(Integer.valueOf(lineSplit[12]));
					}
					lineVenta = true;
				}else{
					lineVenta = false;
				}
				ret.setpositionfile(position);
				ret.saveEx();
		//		log.log(Level.SEVERE, "_CabezalFIN: "+new Timestamp (System.currentTimeMillis()).toString());

			}else{
				throw new AdempiereException("No esta parametrizado el cabezal del tipo: "+lineSplit[1]);//crear lineas con detalles
			}
			
			
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}

		return ret;
	}

	/** Retorna true si existe una auditoria con ese nombre de archivo
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 07/05/2015
	 * @param string
	 * @return
	 */
	private boolean archivoProcesado(String fileName) {
		// TODO Auto-generated method stub
		return MRTAuditLoadTicket.forFileName(getCtx(),fileName,null)!=null;
	}

}

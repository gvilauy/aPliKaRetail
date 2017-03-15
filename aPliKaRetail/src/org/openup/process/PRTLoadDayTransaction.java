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
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.openup.model.MCashConfig;
import org.openup.model.MCashRemittance;
import org.openup.model.MCashRemittanceLine;
import org.openup.model.MPaymentRule;
import org.openup.model.MRTCashBox;
import org.openup.model.MRTTransactionDay;
import org.openup.model.MRTTransactionReport;
import org.openup.util.OpenUpUtils;

/**
 * Proceso para leer archivo de transacciones diaras 
 * (Sisteco emite una archivo por dia al finalizar el d�a)
 * Ejemplo de archivo: "InformeTransacciones-1-2016-06-12-S.txt"
 * OpenUp Ltda Issue #5780
 * @author SBT 13/6/2016
 *
 */
public class PRTLoadDayTransaction extends SvrProcess {
	private Waiting waiting = null;

	//Variables
	private static final String CHAR_SEPARATOR = "#";//cambia de # a \\|
	//Modelo para persistir datos
	
	MRTTransactionReport model = null; //-->Uno por dia
//	private int idModelo = 0;
	private String fileNameProcessed = "";
	
	private int cantTotal = 0;
	
	private String rutaOrigenOpenUp = "";
	private String rutaDestinoOpenUp = "";
	
	private String yesterday = "";
	private MClient client = null;
	private int defOrg_ID = 0;
	private boolean onWindows = false;
	/**	Logger							*/
	protected CLogger			log = CLogger.getCLogger (getClass());
	
	/**
	 * 
	 */
	public PRTLoadDayTransaction() {
		// TODO Auto-generated constructor stub
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
			}
		}		
		catch(Exception e){
			e.toString();
		}
		//Obtengo Organizacion
		defOrg_ID = this.getDefaultOrg();
		
		// Creo modelo MRTloadTicket
		this.model = new MRTTransactionReport(getCtx(), this.getRecord_ID(), null);
		model.setProcessed(false);
		
		if (model.isManual()) {// Proceso manual seleccionando un archivo
			if (model.getFileName1() == null || model.getFileName1().equals("")) {
				throw new AdempiereException("Seleccione archivo para procesar");
			}
			log.log(Level.WARNING,"INICO Lectura Transacciones:"+ new Timestamp(System.currentTimeMillis()).toString());
			MUser u = new MUser(getCtx(), this.getAD_User_ID(), null);
			this.model.setName("Manual_"+ new Timestamp(System.currentTimeMillis()).toString()+ "_" + u.getName());
			File aux = new File(this.model.getFileName1());
			this.model.setFileName(aux.getName());
			log.log(Level.WARNING,"Copia Archivo:"+ new Timestamp(System.currentTimeMillis()).toString());
			this.model.setisfilefound(copyFileFromSisteco(""));
			log.log(Level.WARNING,"Fin Copia Archivo:"+ new Timestamp(System.currentTimeMillis()).toString());
			rutaOrigenOpenUp = getRutaOrigen();
			rutaDestinoOpenUp = getRutaDestino();
			
			//Seteo la fecha desde el nombre InformeTransacciones-1-2016-06-12-S.txt
			String fechaArchivo = this.model.getFileName().replace(getFileSeparator(), "");//reemplazo InformeTransacciones-1-
			//System.out.println(fechaArchivo);
			fechaArchivo = fechaArchivo.replace(getInitFile(), "");
			fechaArchivo = fechaArchivo.replace("S.txt", ""); fechaArchivo = fechaArchivo.replace("-", "");
			this.model.setDateTrx(OpenUpUtils.converTimestampYYYYMMdd(fechaArchivo));
			client = new MClient(this.getCtx(),this.getAD_Client_ID(),this.get_TrxName());
			this.model.saveEx();
		} else {// Proceso automatico levanta archivo del dia anterior y procesa
			log.log(Level.WARNING,"INICO Lectura Transacciones:"+ new Timestamp(System.currentTimeMillis()).toString());
			Timestamp today = null;String[] fecha ;
			if(model.getDateTrx()==null){
				today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis() - 1000L * 60L * 60L * 24L),
						TimeUtil.TRUNC_DAY);
				//Seteo la fecha 
				this.model.setDateTrx(today);
				
				fecha = new Timestamp(System.currentTimeMillis() - 1000L
						* 60L * 60L * 24L).toString().split("-");
			}else{
				today = model.getDateTrx();
				
				fecha = today.toString().split("-");
			}
			
			// Formato fecha YYYY MM -dd
			String fechaArch = fecha[0] +"-"+fecha[1] +"-"+ fecha[2].substring(0, 2);
			yesterday = fechaArch;
			
			//yesterday = "2016-06-12";// para testing
			// Guardo el nombre del archivo que se debe procesar
			this.model.setFileName(getInitFile()+yesterday);

			log.log(Level.WARNING,"Copia archivo transacciones de SISTECO:"+ new Timestamp(System.currentTimeMillis()).toString());
			// Traemos archivo de ayer desde servidor de sisteco si es true, se
			// encontro el archivo.
			this.model.setisfilefound(copyFileFromSisteco(yesterday));
			log.log(Level.WARNING,"Fin Copia SISTECO:"+ new Timestamp(System.currentTimeMillis()).toString());
			// Se obtiene ruta de origen y ruta de destino
			rutaOrigenOpenUp = getRutaOrigen();
			rutaDestinoOpenUp = getRutaDestino();
			this.model.setName("Automatico_"+ new Timestamp(System.currentTimeMillis()).toString());
			client = this.getDefaultClient();
			this.model.setAD_Client_ID(client.get_ID());
			
			this.model.setAD_Org_ID(defOrg_ID);//SBT Issue 7949 24/11/2016
			
			this.model.saveEx();
		}
		//this.idModelo = this.model.get_ID();
		
		
	}
	
	
	/**Copiar archivo desde servidor de sisteco a servidor local, 
	 * retorna true si la accion es exitosa
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
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
				origen = new File (getRutaOrigenSisteco()); // Obtengo archivo desde ruta compartida de sisteco
				destino = new File(getRutaOrigen());
				String start = getInitFile();
				String[] files = origen.list();
				for (int i = 0; i < files.length; i++) {
					if (files[i].startsWith(start+fecha+"-S.txt")){
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
	
	/**
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @return
	 */
	private String getFileSeparator() {
		String nombre = "";
		if(onWindows){
			nombre = MSysConfig.getValue("SEPARATOR_FILE_RT_WIN",0);
		}else{
			nombre = MSysConfig.getValue("SEPARATOR_FILE_RT_LINUX",0);
		}
		return nombre;
	}
	
	/**Copia archivo "sourceFile" al archivo destino "destFile"
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
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
	 * *Directorio donde se levantan los archivos de transacciones diarias en servidor local --\\\\10.0.0.254\\ArchivoTrxDiarias
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @return
	 */
	private String getRutaOrigen() {
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"RutaOrigenTrxDiarias"); //C:\FilesCovadonga\RutaOrigenTrxDiarias

		String  origen = MSysConfig.getValue("SOURCE_DIRECTORY_RT_TRX",0); 
		if(onWindows){///srv/share/ArchivoTrxDiarias
			origen = origen.replace("/srv/share/", "\\\\10.0.0.254\\");
		}
		return origen;
	}
	
	/**Directorio donde se guardan los archivos salida pazos 4 ya procesados --\\\\10.0.0.254\\HistoricoSalidaPazos
	 * OpenUp Ltda Issue#4032
	 * @author Sylvie Bouissa 13/6/2016
	 * @return
	 */
	private String getRutaDestino() {
//	return ("C:"+File.separator+"FilesCovadonga"+File.separator+"HistoricoTrxDiarias");
// String destino = "C:"+File.separator+"FilesCovadonga"+File.separator+"DestinoSisteco"+File.separator;
		String  destino = MSysConfig.getValue("DESTINATION_DIRECTORY_RT_TRX",0); //Directorio dest prametrizado
		if(onWindows){///srv/share/HistoricoTrxDiarias
			destino = destino.replace("/srv/share/", "\\\\10.0.0.254\\");
		}
		return destino;
	}
	
	
	/**
	 * Dirctorio del servidor de sisteco para obtener solo archivos  --\\\\10.0.0.131\\informes
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @return
	 */
	private String getRutaOrigenSisteco() {
//		return ("C:"+File.separator+"FilesCovadonga"+File.separator+"informes");

		String  origen = MSysConfig.getValue("SOURCE_DIRECTORY_SISTECO",0);
		if(onWindows){///srv/share/informes
			origen = origen.replace("/srv/share/", "\\\\10.0.0.254\\");
		}
		return origen;
		
	}
	
	private String getInitFile() {
		String  nombre = MSysConfig.getValue("SISTECO_INIT_FILE_TRANSACTION",0); 
		return nombre;
	}
	
	/**Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa  13/6/2016
	 * @return
	 */
	private MClient getDefaultClient() {		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
			
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();

		return value;
	}
	
	/**Obtiene y retorna primer organizacion que no es la System.
	 * OpenUp Ltda Issue #7949
	 * @author Sylvie Bouissa  24/11/2016
	 * @return
	 */
	private int getDefaultOrg() {		
		int iDOrg = DB.getSQLValue(null, "SELECT MIN(AD_Org_ID) FROM AD_Org WHERE isActive = 'Y' AND AD_Org_ID <> 0");
		if(iDOrg>0){
			return iDOrg;
		}else return 0;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		this.setWaiting(this.getProcessInfo().getWaiting());
		log.log(Level.WARNING, "INIT DOIT Lectura Transacciones diarias: "+new Timestamp (System.currentTimeMillis()).toString());
		
		String retorno = "";
		if(this.model!=null){//1 
			File origen = null; File destino = null;
			if(this.model.isfilefound()){//2-Consulto si se encontro el archivo del dia anterior en serv sisteco
				if(!rutaOrigenOpenUp.equals("") && !rutaDestinoOpenUp.equals("")){//3-Consulto si existe ruta origen y destino
					origen = new File(rutaOrigenOpenUp);
					destino = new File(rutaDestinoOpenUp);		
					String[] files = origen.list();// Listo los archivos que se encuentran en el directorio origen
					int posicion = obtenerPosicionAProcesar(files);	//Obtengo la posicion del archivo que tengo que procesar			
					if(0<=posicion){//4
						this.showHelp("Obteniendo archivo...");
						if(!archivoProcesado(files[posicion])){//5-Si el archivo no esta procesado se prosigue
							fileNameProcessed = files[posicion]; System.out.println(fileNameProcessed);
							//Tener en cuenta que para servidores se debe colocar / para 
							//Windows se debe colocar \\							
							File source = new File(origen + getFileSeparator() + files[posicion]);
							File dest = new File(destino + getFileSeparator() + "Procesado_"+ files[posicion]);
								log.log(Level.WARNING, "INI Se comienza a leer archivo:"+new Timestamp (System.currentTimeMillis()).toString());
								retorno = procesarArchivo(source);
								if(retorno.equals("OK")){//7
									//model.setProcessed(true);
									//model.setDescription("Archivo procesado exitosamente!!");
									//model.saveEx();
									try {
										log.log(Level.WARNING, "FIN lectura archivo:"+new Timestamp (System.currentTimeMillis()).toString());
										//Se crean los respectivos resguardos
										boolean creaOK = carearRemesas(this.getCtx(),model.get_ID(),this.get_TrxName());
										log.log(Level.WARNING, "INI Copia historico:"+new Timestamp (System.currentTimeMillis()).toString());
										copyFile(source, dest);	//Se copia archivo a carpeta historico
										source.delete();
										dest = null;

										MRTTransactionReport ahora = new MRTTransactionReport(getCtx(), model.get_ID(), get_TrxName());
										ahora.setProcessed(creaOK);
										if(creaOK){
											ahora.setProcessed(true);
											ahora.setDescription("Archivo procesado exitosamente!!");
										}else{
											ahora.setDescription("Se cargo el archivo correctamente, pero falta procesar remesas");
										}
										ahora.saveEx();

										log.log(Level.WARNING, "FIN Proceso lectura Transacciones diarias!!"+new Timestamp (System.currentTimeMillis()).toString());
				
									} catch (IOException e) {
										e.printStackTrace();
										retorno = e.getMessage();
									}finally{
										origen = null;
										destino = null;
									}
									log.log(Level.WARNING, "RETORNO OK:"+new Timestamp (System.currentTimeMillis()).toString());

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
							log.log(Level.WARNING, "Existe auditoria para el archivo:"+new Timestamp (System.currentTimeMillis()).toString());

							return "OK";
						}
					}else{//4
						model.setProcessed(false);
						this.model.setDescription("No se encontro el archivo en carpeta de destino ");
						model.saveEx();									
						log.log(Level.WARNING, "No se encontro el archivo en carpeta de destino:"+new Timestamp (System.currentTimeMillis()).toString());
						return "No se encontro el archivo en carpeta de destino";
					}
					
				}else{//3
					model.setProcessed(false);
					this.model.setDescription("No existe ruta de origen o destino");
					model.saveEx();
					log.log(Level.WARNING, "No existe ruta de origen o destino"+new Timestamp (System.currentTimeMillis()).toString());

					return "No existe ruta de origen o destino";
				}
			}else{//2
				model.setProcessed(false);
				this.model.setDescription("No se encontro archivo en servidor Sisteco:("+this.model.getFileName()+")");
				model.saveEx();
				log.log(Level.WARNING, "No se encontro archivo en servidor Sisteco:("+this.model.getFileName()+")"+new Timestamp (System.currentTimeMillis()).toString());

				return "No se encontro archivo en servidor Sisteco";
			}
			
			
		}else{//1 
			return "MRTLoadTicket null";
		}
	}

	/**
	 * Metodo que crea remesas a partir de un reporte de transacciones
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 14/6/2016
	 * @param ctx 
	 * @throws Exception 
	 */
	public boolean carearRemesas(Properties ctx, int idModelo,String trxName) throws Exception {
		//this.showHelp("Creando remesas...");
		log.log(Level.WARNING,"INICIO Creacion Remesas:"+ new Timestamp(System.currentTimeMillis()).toString());

		//MRTTransactionReport modelo = new MRTTransactionReport(ctx,idModelo,trxName);
		MCashConfig cash = MCashConfig.forClient(ctx,client.get_ID(), trxName); 
		if(cash.getAD_User_ID()>0){
			
			List<MRTTransactionDay> trxs = MRTTransactionDay.forTrxRepOrderbyMP(ctx, idModelo,client.get_ID(), trxName);
			//MCashRemittance debito = null; 16/06/2016 se acumula todo en medio de pago tarjeta de credito
			MCashRemittance credito = null; int countCred = 0;
			MCashRemittance sodEdenred = null; int countSodEd = 0;
			int mdioPago = -1;
			if(trxs!= null && trxs.size()>0){
				//Recorro las l�neas
				for(MRTTransactionDay trx : trxs){
					if(mdioPago<0 || mdioPago!=Integer.valueOf(trx.gettipotarjeta())){
						mdioPago = Integer.valueOf(trx.gettipotarjeta());
						if(mdioPago==0 && credito==null){
							credito = new MCashRemittance(ctx,0, trxName);
							credito.setUY_PaymentRule_ID(MPaymentRule.forValue(ctx, "tarjeta",client.get_ID(),null).get_ID());
							credito.setAD_User_ID(cash.getAD_User_ID());
							credito.setDateTrx(model.getDateTrx());
							credito.setC_DocType_ID(MDocType.forValueWOClinetID( ctx, "cashremittvalues", null).get_ID());
							credito.setAD_Client_ID(client.get_ID());
							credito.setAD_Org_ID(defOrg_ID);//SBT Issue #7949
							credito.saveEx();
						}else if (mdioPago==1 && sodEdenred==null) {// && debito == null ){
//							debito = new MCashRemittance(ctx,0, trxName);
//							debito.setUY_PaymentRule_ID( MPaymentRule.forValue(ctx, "tarjetadeb", null).get_ID());
//							debito.setAD_User_ID(cash.getAD_User_ID());
//							debito.setDateTrx(modelo.getDateTrx());
//							debito.setC_DocType_ID(MDocType.forValue(ctx, "cashremittvalues", null).get_ID());
//							debito.saveEx();
							
							sodEdenred = new MCashRemittance(ctx,0, trxName);
							sodEdenred.setUY_PaymentRule_ID( MPaymentRule.forValue(ctx, "sodexo", client.get_ID(),null).get_ID());
							sodEdenred.setAD_User_ID(cash.getAD_User_ID());
							sodEdenred.setDateTrx(model.getDateTrx());
							sodEdenred.setC_DocType_ID(MDocType.forValueWOClinetID(ctx, "cashremittvalues", null).get_ID());
							sodEdenred.setAD_Client_ID(client.get_ID());
							sodEdenred.setAD_Org_ID(defOrg_ID);//SBT Issue #7949
							sodEdenred.saveEx();
						}
					}
					
					//creo una MCashRemttanceLine para credito o debito
					MCashRemittanceLine line = new MCashRemittanceLine(ctx,0,trxName);
					line.setAD_Client_ID(client.get_ID());
					line.setAD_Org_ID(defOrg_ID);//SBT Issue #7949
					
					//int adOrgID = DB.getSQLValue(null, "SELECT MIN(AD_Org_ID) FROM AD_Org WHERE isActive = 'Y' AND AD_Org_ID <> 0");
					
					line.setUY_RT_CashBox_ID(MRTCashBox.forValue(ctx, trx.getcaja(), defOrg_ID, null).get_ID());
					line.setNroLote(trx.getlote());
					line.setnumeroticket(trx.getticket());
					line.setAmount2(1);
					if( trx.getmoneda().equals("1") || trx.getmoneda().equals("01")){
						line.setC_Currency_ID(142);
					}else if( trx.getmoneda().equals("2") || trx.getmoneda().equals("02")){
						line.setC_Currency_ID(100);
					}
					if(trx.gettipo().equalsIgnoreCase("1") || trx.gettipo().equalsIgnoreCase("01")){
						// 1: Venta 
						line.setAmount(trx.getmonto()); 
					}else if(trx.gettipo().equalsIgnoreCase("2") || trx.gettipo().equalsIgnoreCase("02")){
						//2:Anulaci�n
						line.setAmount(trx.getmonto().negate()); // Se setea el mismo monto pero negativo 
					}else if(trx.gettipo().equalsIgnoreCase("3") || trx.gettipo().equalsIgnoreCase("03")){
						//3: Devoluci�n
						line.setAmount(trx.getmonto().negate()); // Se setea el mismo monto pero negativo 
					}
					
					//Por ultimo indico a que remesa corresponde, banco y cuenta
					if(mdioPago ==0  ||(mdioPago ==1 &&  (!trx.getidtarjeta().equalsIgnoreCase("79") && !trx.getidtarjeta().equalsIgnoreCase("69")))){//Credito
						countCred++;
						//indico que la MCashRemttanceLine es de la remesa tarjeta credito
						line.setUY_CashRemittance_ID(credito.get_ID());
						MBank banco = MBank.forValue(ctx,"Tarjetas de Credito",client.get_ID(),null);
						if(banco!=null && banco.get_ID()>=0) {
							line.setC_Bank_ID(banco.get_ID());
						}else{
							//Agregar log
							throw new AdempiereException("No esta parametrizada el banco Tarjetas de Credito");

						}
						MBankAccount cuentaB = null;
						if(mdioPago==0){
							cuentaB = MBankAccount.forCtaAndBank(ctx,trx.getidtarjeta(),banco.get_ID(),
									line.getC_Currency_ID(),client.get_ID(),null);
						}else if(mdioPago==1){
							cuentaB = MBankAccount.forCtaAndBank(ctx,mdioPago+trx.getidtarjeta(),banco.get_ID(),
									line.getC_Currency_ID(),client.get_ID(),null);
						}				
						if(cuentaB!= null && cuentaB.get_ID()>0){
							line.setC_BankAccount_ID(cuentaB.get_ID());
						}else{
							//Agregar log
							throw new AdempiereException("No esta parametrizada la tarjeta con id"+trx.getidtarjeta());
						}	
						
					}else if(mdioPago==1 && (trx.getidtarjeta().equalsIgnoreCase("79")||trx.getidtarjeta().equalsIgnoreCase("69"))){//Debito
						
						countSodEd++;
						MBank banco = MBank.forValue(ctx,"Tarjetas de Debito",client.get_ID(),null);
						if(banco!=null && banco.get_ID()>=0) {
							line.setC_Bank_ID(banco.get_ID());
						}else{
							//Agregar log
							throw new AdempiereException("No esta parametrizada el banco Tarjetas de Debito");

						}
						//Si es debito el n�mero de tarjeta corresponde con el medio de pago mas el id de tarjeta (1 + 29)
						MBankAccount cuentaB = MBankAccount.forCtaAndBank(ctx,mdioPago+trx.getidtarjeta(),
								banco.get_ID(),line.getC_Currency_ID(),client.get_ID(),null);
						if(cuentaB!= null && cuentaB.get_ID()>0){
							line.setC_BankAccount_ID(cuentaB.get_ID());
						}else{
							//Agregar log
							throw new AdempiereException("No esta parametrizada la tarjeta con id"+mdioPago+trx.getidtarjeta());
						}
						//indico que la MCashRemittanceLine es de la remesa tarjeta debito  o sodexo
						//if(trx.getidtarjeta().equalsIgnoreCase("79")||trx.getidtarjeta().equalsIgnoreCase("69")){
							line.setUY_CashRemittance_ID(sodEdenred.get_ID());
						//}
						//else{
							//line.setUY_CashRemittance_ID(debito.get_ID());
						//}
					}
					line.saveEx();
				}
				if(countCred>0){
					credito.setDocStatus(DocumentEngine.STATUS_Approved);
					credito.setDocAction(DocAction.ACTION_Complete);
					credito.setApprovalText("Remesa automatica");
					credito.saveEx();
					//OpenUp SBT 17/06/2016 Issue #5780 --> A pedido de Rodrogo B
					if (!credito.processIt(DocumentEngine.ACTION_Complete)){
						String message = credito.getProcessMsg();
						throw new AdempiereException(message);
					}
				}
				if(countSodEd>0){
					sodEdenred.setDocStatus(DocumentEngine.STATUS_Approved);
					sodEdenred.setDocAction(DocAction.ACTION_Complete);
					sodEdenred.setApprovalText("Remesa automatica");
					sodEdenred.saveEx();
					//OpenUp SBT 17/06/2016 Issue #5780 --> A pedido de Rodrogo B
					if (!sodEdenred.processIt(DocumentEngine.ACTION_Complete)){
						String message = sodEdenred.getProcessMsg();
						throw new AdempiereException(message);
					}
				}
				log.log(Level.WARNING,"FIN Creacion Remesas:"+ new Timestamp(System.currentTimeMillis()).toString());

				return true; //
			}else{
				return false;
			}

		}
		return false;
	}

	/**Metodo recorre la lista de archivos, controla que sea el archivo correspondiente a procesar
	 * (archivo "InformeTransacciones-1-AAAA-MM-DD-S.txt", fecha del dia anterior)
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @param files
	 * @return
	 */
	private int obtenerPosicionAProcesar(String[] files) {
		if(files.length>0){
			for (int i = 0; i < files.length; i++) {
				if(!this.model.isManual()){			
					if (files[i].startsWith(getInitFile()+yesterday+"-S")){//se obtiene InformeTransacciones-1- + fecha
						return i;
					}
				}else{
					if (files[i].startsWith(this.model.getFileName())){
						return i;
					}
				}
			}			
		}
		return -1;
	}
	
	/** Retorna true si existe un !!!!!!!!!!!VER sI CORRESPONDE !!!!!!!!!!!!!!!!! con ese nombre de archivo
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @param string
	 * @return
	 */
	private boolean archivoProcesado(String fileName) {
		return false;//MRTAuditLoadTicket.forFileName(getCtx(),fileName,null)!=null;
	}
	
	

	/**Se leen cada una de las lineas y se persiste en la tabla MRTTransactionDay
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @param source
	 * @return
	 */
	private String procesarArchivo(File fileIn) {
		FileReader fr = null;
		BufferedReader br = null;
		try{
			
			// Se comienza a leer el archivo fila por fila
			fr = new FileReader (fileIn);
			br = new BufferedReader(fr);
			
			MRTTransactionDay mTrxD = null;
			int pos = 0;
			String line = br.readLine();
			while(line != null){
				try{
					pos ++;
					this.showHelp("Procesando linea" + pos);
					String lineSplit[] = line.split(CHAR_SEPARATOR);
					if(lineSplit!=null && lineSplit.length>0){
						//Creo el objeto para la linea actual
						mTrxD = new MRTTransactionDay(getCtx(), 0, get_TrxName());
						mTrxD.parseLineTrxDay(lineSplit,String.valueOf(pos),model.get_ID());
						mTrxD.setAD_Client_ID(client.get_ID());
						mTrxD.setAD_Org_ID(defOrg_ID);//SBT Issue #7949
						mTrxD.saveEx();				
					}	
				}catch(Exception ex){
					log.log(Level.WARNING, "Error: "+ ex.getMessage());
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
		log.log(Level.WARNING, "ProcesarArchivo: "+new Timestamp (System.currentTimeMillis()).toString()+" catnTotalL:"+cantTotal);
		return "OK";
	}
	
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}
	
	public Waiting getWaiting() {
		return this.waiting;
	}
	
	public void setWaiting(Waiting value) {
		this.waiting = value;
	}
	
	
	
//	private boolean escribeArchivo(String encodedMessage, String nombreArchivo)	throws SmbException, 
//				MalformedURLException, UnknownHostException, IOException{
//			String USR_SERVIDOR = "openup";String PASS_SERVIDOR = "0p3nup2015";
//			String URL_SERVIDOR = "smb://10.0.0.254/Informes/";
//			String EXT_ARCHIVO = ".txt";
//			boolean exitoArchivo = false;
//			
//			//String url = "smb://127.0.0.1/compartida/"+ nombreArchivo ;
//			//String url = "smb://usuario:usuario@127.0.0.1/compartida/"+ nombreArchivo ;
//			String url = "smb://"+ USR_SERVIDOR +":"+ PASS_SERVIDOR +"@"
//			+ URL_SERVIDOR + nombreArchivo + EXT_ARCHIVO;
//
//			try{
//				SmbFileOutputStream out = new SmbFileOutputStream(url, false);
//
//				out.write(encodedMessage.getBytes());
//				// encodeMessage es el texto que contiene el archivo
//				// aqui podemos enviar los bytes de algun archivo de la maquina local
//	
//				out.close();
//	
//				exitoArchivo = true;
//	
//				System.out.println("generado " + url );
//			} catch(IOException ex){
//					exitoArchivo = false;
//			} finally{
//				return exitoArchivo;
//			}
//	}
//
//			//Para borrar un archivo en la carpeta compartida:
//
//	private void borraArchivo(String archivo,String urlServer) throws SmbException,
//							MalformedURLException, UnknownHostException{
//
//				//String url = "smb://127.0.0.1/compartida/"+ nombreArchivo ;
//				//String url = "smb://usuario:usuario@127.0.0.1/compartida/"+ nombreArchivo ;
//				//-------String url = "smb://"+ USR_SERVIDOR +":"+ PASS_SERVIDOR +"@"+ URL_SERVIDOR ;
//				String url = urlServer;
//				String rutArchivo = url + archivo;
//				SmbFile sFile = new SmbFile(rutArchivo);
//				if(sFile.exists())
//				sFile.delete();
//	}
}

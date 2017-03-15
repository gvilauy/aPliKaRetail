/**
 * 
 */
package org.openup.process;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.SvrProcess;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openup.model.MRTAuditLoadTicket;
import org.openup.model.MRTCashBox;
import org.openup.model.MRTConfig;
import org.openup.model.MRTConfigIdOrg;
import org.openup.model.MRTLoadTicket;
import org.openup.model.MRTLogFile;
import org.openup.model.MRTMovement;
import org.openup.retail.MRTRetailInterface;
import org.openup.util.OpenUpUtils;

/**OpenUp Ltda Issue #5555 
 * @author SBT 29/2/2016
 *PRTLoadTicketScanntech
 */
public class PRTLoadTicketScanntech extends SvrProcess {

	private MRTAuditLoadTicket mAudit = null;
	private MRTLoadTicket model = null;
	private MClient client;
	private int idMRTLT;
	private MRTConfig rtConfig = null; 
	private boolean huboError= false;
	
	//Variables para obtener los datosd desde WS scanntech
	private Timestamp fechaCorrida =null;
	private Timestamp fechaInicio =null;
	private Timestamp fechaDesde = null;
	private String fechaDelDia = "";
	
	//SBT Issue # se debe consultar en el mismo per�odo de tiempo en x d�as anteriores
	private int cantDiasAnteriores = 0;
	//Constructor
	public PRTLoadTicketScanntech() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		rtConfig = MRTConfig.forValue(getCtx(),"configscanntech",get_TrxName());
		//Obtengo la fecha seteada en la configuraci�n
		fechaInicio = rtConfig.getDueDate(); 
		//fechaInicio = new Timestamp(System.currentTimeMillis());// No permitir correr manualmente 
		// Se utiliza para luego de la carga de los movimientos del d�a se haga la misma consulta por x dias anteriores
		cantDiasAnteriores = rtConfig.getDiasAtraso();  
		//Obtengo cantidad de minutos a contemplar
		int periodoTiempo = rtConfig.get_ValueAsInt("cant");
		
		//Obtengo la fecha actual--> setear� luego
		fechaCorrida = new Timestamp(System.currentTimeMillis());
		
		//Le resto cantidad de minutos configurados a la hora actual
		int min = rtConfig.get_ValueAsInt("cant");
	
		fechaDesde = OpenUpUtils.sumaTiempo(this.fechaCorrida, Calendar.MINUTE, -min);
		
		String fecha[] = fechaInicio.toString().split("-");
		// Formato fecha YYYYMMdd
		fechaDelDia = fecha[0] + fecha[1] + fecha[2].substring(0, 2);
		String nombreProceso = "Movimientos_"+ fechaDelDia;
		
		//Obtengo el modelo del d�a si existe y sino lo creo
		this.model = MRTLoadTicket.forNameAndDate(getCtx(), nombreProceso ,fechaDelDia,null);
		
		if(null == model){
			this.model = new MRTLoadTicket(getCtx(), this.getRecord_ID(), null);
			this.model.setFileName(fechaDelDia);//Solo puede haber un proceso con dicho nombre.
			this.model.setName(nombreProceso);
			this.model.setIsManual(false);
			this.model.setProcessed(false);
			
			this.model.setAD_Org_ID(0);
			
			if(!model.isManual()){
				client = this.getDefaultClient();
				
			}else{
				client = new MClient(getCtx(),this.getAD_Client_ID(),null);
				
			}
			this.model.setAD_Client_ID(client.get_ID());
			this.model.saveEx();
			
		}else{
			if(!model.isManual()){
				client = this.getDefaultClient();
				
			}else{
				client = new MClient(getCtx(),model.getAD_Client_ID(),null);
				//this.fechaInicio = (Timestamp) model.get_Value("DateWorkStart");
				
			}
		}
		if(this.model.isManual()){
			fechaDesde = new Timestamp(fechaInicio.getYear(), fechaInicio.getMonth(), Integer.valueOf(fecha[2].substring(0, 2)),
							0, 0, 01, 0);
			fechaCorrida = new Timestamp(fechaInicio.getYear(), fechaInicio.getMonth(), Integer.valueOf(fecha[2].substring(0, 2)),
					23,59,59, 0);
					
		}
		this.idMRTLT = this.model.get_ID();
		System.out.println("Movimeintos desde:"+this.fechaDesde);
		System.out.println("Movimientos hasta:"+this.fechaCorrida);

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		String retorno = ""; int count = 0;
		int countTotal = 0; String nameOrg = "";
		for(int i = 0;i<=cantDiasAnteriores;i++){
			if(i>0){
				count = 0;countTotal=0;
				this.model = null;
				//Obtengo x dia anterior
				fechaInicio = TimeUtil.addDays(fechaInicio, -1);
				
				String fecha[] = fechaInicio.toString().split("-");
				// Formato fecha YYYYMMdd
				fechaDelDia = fecha[0] + fecha[1] + fecha[2].substring(0, 2);
				String nombreProceso = "Movimientos_"+ fechaDelDia;	
				//Obtengo el modelo del d�a que paso
				
				this.model = MRTLoadTicket.forNameAndDate(getCtx(), nombreProceso ,fechaDelDia,null);
				if(this.model==null){
					continue;
				}
			}
			log.log(Level.FINE,"INICIO PRLOADTICKET SCANNTECH:"+ new Timestamp(System.currentTimeMillis()).toString());
			System.out.println("---------- INICIO LECTURA MOVIMIENTOS DEL DIA: "+fechaInicio+" -------------");
			List<MRTConfigIdOrg> lstIdsScanntech = MRTConfigIdOrg.getIds(getCtx(),client.get_ID(),get_TrxName());
			if(lstIdsScanntech.size()>0){
				int intIdEmpresa = 0;
				for(MRTConfigIdOrg conf :lstIdsScanntech){
					//Otengo las Sucursales
					intIdEmpresa = conf.getidentifempresa();
					//if(intIdEmpresa==2097)continue;
					MOrg org = new MOrg(getCtx(), conf.getAD_Org_ID_To(), null);
					nameOrg = org.getName();
					//Por cada caja obtengo los movimintos
					List<MRTCashBox> cajas = MRTCashBox.forSucursal(getCtx(),org.get_ID(),get_TrxName());
					//Por cada caja obtengo los movimintos
					for(MRTCashBox caja: cajas){
						log.log(Level.FINE,"PROCESO MOVIMIENTOS PARA SUC: "+ org.getValue()+"-"+org.getName()+", CAJA: "+
								caja.getName()+" - "+fechaInicio);//new Timestamp(System.currentTimeMillis()).toString());
						System.out.println("INFO - INI PROCESO MOVIMIENTOS PARA SUC: "+ org.getName()+"-"+org.getName()+", CAJA: "+
								caja.getName()+" - "+fechaInicio);
						//Proceso los movimientos correspondientes
						retorno = processMovimientosPorCajaSucursal(org.get_ID(),Integer.valueOf(org.getValue()),
								Integer.valueOf(caja.getValue()),fechaInicio,fechaDesde,fechaCorrida,model.get_ID(), intIdEmpresa);
						if(retorno.startsWith("OK")){
							log.log(Level.FINE,"Se procesaron correctamente "+Integer.valueOf(retorno.substring(5)));
							System.out.println("INFO - Se procesaron correctamente "+Integer.valueOf(retorno.substring(5)));
							count = count + Integer.valueOf(retorno.substring(5));
						}
					}	
				}
				
			}
			if(!huboError){
				//Se setea como fecha para la prox corrida la de ahora que hasta cuando se toman en cuenta los mov.
				rtConfig.setDueDate(this.fechaCorrida);
				rtConfig.saveEx();
			}
			//model.setDescription(Description);
			log.log(Level.FINE,"FIN PRLOADTICKET SCANNTECH DEL DIA: "+ fechaInicio);//new Timestamp(System.currentTimeMillis()).toString());
			System.out.println("FIN PRLOADTICKET SCANNTECH DEL DIA: "+fechaInicio+"");
			log.log(Level.FINE,"SE PROCESARON "+count+" MOVIEMIENTOS DEL DIA: "+fechaInicio+"");
			System.out.println("SE PROCESARON "+count+" MOVIEMIENTOS DEL DIA: "+fechaInicio+"");
			countTotal +=count;
			System.out.println("SE PROCESARON EN TOTAL PARA LA SUCURSAL "+nameOrg+":  "+countTotal);
		}
		
		return "Se procesaron "+countTotal+" movimientos";
	}
	
	/**Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda Issue #5555
	 * @author Sylvie Bouissa 15/05/2015
	 * @return
	 */
	private MClient getDefaultClient() {		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
			
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
			
		return value;
	}
	
	/**
	 * Proceso y persiste los moviemintos para la sucursal, caja y fecha recibidos.
	 * OpenUp Ltda Issue #5555
	 * @author Sylvie Bouissa 29/2/2016
	 * @param idOrg
	 * @param inIdLocal
	 * @param inIdCaja
	 * @param inFch
	 * @param idMRTLoadTicketID
	 * @param intIdEmpresa 
	 * @return
	 */
	private String processMovimientosPorCajaSucursal(int idOrg,int inIdLocal,int inIdCaja,Timestamp inFch,Timestamp inDesde,
			Timestamp inHasta,int idMRTLoadTicketID, int idEmpresa){
		//SBT 26/04/2016 Issue # (Nueva version de scantech 1.9.6)
		int pageOffSet = 0; // Cantidad de p�gingas
		int pageSize = 0; // Cantidad de registros por p�gina,cantidad de mov por consulta;
		int cantTotalMov = 0;
		JSONArray resp = null;
		int count=0;//cantidad total de movimientos
		
		if(pageOffSet==0 && pageSize==0){// caso inicial
			//Obtengo todos los movimientos para el local, caja y fecha obtenidas
			resp = MRTRetailInterface.enviarMovimientoTimestamp(getCtx(), client.get_ID(),//Env.getAD_Client_ID(getCtx()),
					get_TrxName(), inIdLocal, inIdCaja, inFch,inDesde,inHasta,idEmpresa,pageOffSet);
		}
	
		if(null==resp){
			MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
			log.setDescription("Consulta mov. del:"+inFch+",de la caja "+inIdCaja+" del local "+
					inIdLocal+" desde: "+inDesde.toString()+", hasta: "+inHasta.toString());
			log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
			log.setName("Respuesta del WS con error !!!");
			log.setdatofila(MRTRetailInterface.enviarMovimientoTimestampError(getCtx(), client.get_ID(),//Env.getAD_Client_ID(getCtx()),
					get_TrxName(), inIdLocal, inIdCaja, inFch,inDesde,inHasta,idEmpresa,pageOffSet));
			log.saveEx();
			pageSize = 0;
		}else{
			pageSize = resp.length();
		}
		cantTotalMov = pageSize;
		if(0==pageSize && null!=resp) {
			MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
			log.setDescription("No hay movimientos en la caja "+inIdCaja+" del local "+inIdLocal+" a partir de las "+fechaInicio.toString());
			log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
			log.setName("No hay movimientos");
			log.saveEx();
			//log.log(Level.SEVERE,pageSize+ " movimientos para el d�a: "+fechaDelDia+", local:  "+inIdLocal+" y caja:  "+inIdCaja);

			return "OK - 0";//"No hay datos para el d�a: "+fch+",local:"+inIdLocal+" y caja:"+inIdCaja ;		
		}
	
		count += procesarMovimientos(resp,inIdLocal, inIdCaja, idOrg, idMRTLoadTicketID,idEmpresa);
		//Realizo la misma consulta salteando p�ginads mientras la cantidad de registros obtenidos sea 100
		while(pageSize>=100){
			pageOffSet +=pageSize;
			resp = MRTRetailInterface.enviarMovimientoTimestamp(getCtx(), client.get_ID(),//Env.getAD_Client_ID(getCtx()),
					get_TrxName(), inIdLocal, inIdCaja, inFch,inDesde,inHasta,idEmpresa,pageOffSet);
			if(null==resp){
				MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
				log.setDescription("Consulta mov. del:"+inFch+",de la caja "+inIdCaja+" del local "+
						inIdLocal+" desde: "+inDesde.toString()+", hasta: "+inHasta.toString());
				log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
				log.setName("Respuesta del WS con error !!!");
				log.setdatofila(MRTRetailInterface.enviarMovimientoTimestampError(getCtx(), client.get_ID(),//Env.getAD_Client_ID(getCtx()),
						get_TrxName(), inIdLocal, inIdCaja, inFch,inDesde,inHasta,idEmpresa,pageOffSet));
				log.saveEx();
				pageSize = 0;	
			}else{
				pageSize = resp.length();
			}
			cantTotalMov = pageSize;
			if(pageSize>0){
				count += procesarMovimientos(resp,inIdLocal, inIdCaja, idOrg, idMRTLoadTicketID,idEmpresa);
			}
		}
		System.out.println("Se leen "+count+", de "+cantTotalMov);
		return "OK - "+count;

	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 26/4/2016
	 * @param resp
	 */
	private int procesarMovimientos(JSONArray resp,int inIdLocal,int inIdCaja,int idOrg,int idMRTLoadTicketID,int idEmpresa) {
		int cant =0; int count=0;
		if(null!=resp){		
			try{
				cant = resp.length();
				for(int i=0;i<cant;i++){
					JSONObject movJson = resp.getJSONObject(i);
					//System.out.println(movJson.toString());
					if(null!= movJson){
						//Se busca movimiento prara no duplicar infromaci�n
						//System.out.println(fechaInicio);
						MRTMovement movCFE = MRTMovement.forNroCupFiscal(getCtx(),movJson.get("numeroOperacion").toString()
								,this.fechaInicio,String.valueOf(idEmpresa),idOrg,
								String.valueOf(inIdLocal),String.valueOf(inIdCaja), get_TrxName());
						if(null==movCFE){
							//System.out.println("SBT-Comienzo a crear el movimiento con los datos obtenidos");
							MRTMovement mov = new MRTMovement(getCtx(), 0, get_TrxName());
							mov.setAD_Org_ID(0);
							mov.setAD_Client_ID(client.get_ID());
							mov.set_ValueOfColumn("AD_Org_ID_To", idOrg);
							mov.set_ValueOfColumn("UY_RT_LoadTicket_ID", model.get_ID());
							String parse = mov.parseMovement(getCtx(), movJson, inIdLocal, inIdCaja, idEmpresa, get_TrxName());
							if (parse.equalsIgnoreCase("OK")){
								count++;
							}else{
								//System.out.println("SBT-No se proceso el movimiento");
								MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
								log.setDescription(" "+ parse);
								log.setAD_Client_ID(client.get_ID());
								log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
								log.setName("Error al procesar movimiento ");
								log.saveEx();
								huboError = true;
								//return "Error parseando movimiento";
							}
								
						}
					}	
					
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
				huboError = true;
			}
		}
		return count;
		
	}

}

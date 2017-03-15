/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
//import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openup.model.MRTCashBox;
import org.openup.model.MRTConfig;
import org.openup.model.MRTConfigIdOrg;
import org.openup.model.MRTDiscMovementDetail;
import org.openup.model.MRTLoadTicket;
import org.openup.model.MRTLogFile;
import org.openup.model.MRTMovement;
import org.openup.model.MRTMovementDetail;
import org.openup.model.MRTPaymentMovement;
import org.openup.retail.MRTRetailInterface;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtils;

/**OpenUp Ltda Issue#
 * @author SBT 21/12/2015
 *
 */
public class PRTGetMovementsDate extends SvrProcess {

	private int idLocal = 0;
	private int mAD_Org_ID_To = 0;
	private int mUY_RT_CashBox_ID = 0;
	private String numCuponFiscal = "";
	private int idCaja = 1;
	
	private Timestamp fechaConsulta = null; //yyyy-mm-dd
	private Timestamp fechaHasta = null; //yyyy-mm-dd	hsMinSeg zh
	private Timestamp fechaDesde = null; //yyyy-mm-dd hsMinSeg zh

	private MRTConfig rtConfig = null;
	private MRTLoadTicket model = null;
	private boolean huboError= false;
	private int idEmpresa = 0;
	//28/04/2016 SBT Issue #
	boolean todas = false; //todas las cajas
	boolean todoElDia = false; //indica si se debe considerar todo el d�a
	int mCantDias = 0;
	String fechaArch = "";
	/**
	 * 
	 */
	public PRTGetMovementsDate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idLocal")){
					this.mAD_Org_ID_To =((BigDecimal)para[i].getParameter()).intValue();
					this.idLocal = getLocalFromOrg(this.mAD_Org_ID_To);
				}
			
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaConsulta = (Timestamp) para[i].getParameter();
				}
			
				if (name.equalsIgnoreCase("UY_RT_CashBox_ID")){
					this.mUY_RT_CashBox_ID =((BigDecimal)para[i].getParameter()).intValue();
				}
			
				if (name.equalsIgnoreCase("IsActive2")){
					if(para[i].getParameter().toString().equalsIgnoreCase("Y")){
						this.todas=true;
					}
				}
			
				if (name.equalsIgnoreCase("IsManual")){
					if(para[i].getParameter().toString().equalsIgnoreCase("Y")){
						this.todoElDia=true;
					}
				}
			
				if (name.equalsIgnoreCase("cant")){
					this.mCantDias =((BigDecimal)para[i].getParameter()).intValue();
				}
			}
			
		}
		MRTCashBox box = new MRTCashBox(this.getCtx(),this.mUY_RT_CashBox_ID,null);
		if(null!= box && box.get_ID()>0) this.idCaja = Integer.valueOf(box.getValue());
		
		
		if(this.idLocal<=0) throw new AdempiereException("Verifique selecci�n de local o su parametrizaci�n");
		if(this.idCaja <=0) throw new AdempiereException("La caja no puede ser 0");
		//Obtengo el id de empresa asociado a la sucursal seleccionada
		this.idEmpresa = MRTConfigIdOrg.getIdEmpresaXOrg(this.getCtx(),this.mAD_Org_ID_To,null);
		if(this.idEmpresa==0) throw new AdempiereException("La sucursal no tiene asociada un Id de empresa");
		rtConfig = MRTConfig.forValue(getCtx(),"configscanntech",get_TrxName());
		
		//Formateo la fecha que ingresaron para obtener el modelo del d�a de hoy
		String fecha[] = fechaConsulta.toString().split("-");
		fechaArch = fecha[0] + fecha[1] + fecha[2].substring(0, 2);// Formato fecha YYYYMMdd
		String nombreProceso = "Movimientos_"+ fechaArch;	
		
		//Obtengo la fecha de hoy para obtener la hora del proceso
		Timestamp now = new Timestamp (System.currentTimeMillis());
		String ahora[] = now.toString().split(" ");
		ahora = ahora[1].split(":");
		if(!todoElDia){
			fechaHasta =new Timestamp(fechaConsulta.getYear(), fechaConsulta.getMonth(), Integer.valueOf(fecha[2].substring(0, 2)),
					Integer.valueOf(ahora[0]), Integer.valueOf(ahora[1]),Integer.valueOf(ahora[2].substring(0, 2)), 0);
		}else{
			fechaHasta =new Timestamp(fechaConsulta.getYear(), fechaConsulta.getMonth(), Integer.valueOf(fecha[2].substring(0, 2)),
					23,59,59, 0);
		}
		
		fechaDesde = new Timestamp(fechaConsulta.getYear(), fechaConsulta.getMonth(), Integer.valueOf(fecha[2].substring(0, 2)),
				0, 0, 01, 0);
		System.out.println("OpUp-Movimientos desde:"+this.fechaDesde);
		System.out.println("OpUp-Movimientos hasta"+this.fechaHasta);
		//System.out.println(fechaConsulta.toString());
		//Obtengo el modelo del d�a si existe y sino lo creo
		this.model = MRTLoadTicket.forNameAndDate(getCtx(), nombreProceso ,fechaArch,null);
		if(null==this.model){
			this.model = new MRTLoadTicket(getCtx(), this.getRecord_ID(), null);
			this.model.setFileName(fechaArch);//Solo puede haber un proceso con dicho nombre.
			this.model.setName(nombreProceso);
			this.model.setIsManual(true);
			this.model.setProcessed(false);
			this.model.setAD_Org_ID(0);
			this.model.setAD_Client_ID(this.getAD_Client_ID());
			this.model.saveEx();
		}
		
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8/3/2016
	 * @param string
	 * @return
	 */
	private Timestamp getMenosXMinutos(int amount) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTimeInMillis(this.fechaConsulta.getTime());
		calendario.add(Calendar.MINUTE, amount);
		Timestamp fechaResultante = new Timestamp(calendario.getTimeInMillis());

		return fechaResultante;
	}

	/**Obtengo el id de local parametrizado en organizaciones, seg�n datos obtenidos por scanntech
	 * OpenUp Ltda Issue# 
	 * DATOS SCANNTECH Adumel-3,Solvencia-4,Cafanor-100,Bekemar-101,Alencur-102		
	 * @author Sylvie Bouissa 22/2/2016
	 * @param idLocal2
	 * @return
	 */
	private int getLocalFromOrg(int idOrg) {

		MOrg org = null;
		try{
			if(idOrg >0){
				org = new MOrg(this.getCtx(), idOrg, null);
				if(org!=null) return Integer.valueOf(org.getValue());
			}
		}catch(Exception e){
			e.getMessage();
		}
		return 0;
	}

	@Override
	protected String doIt() throws Exception {
		String ret = "Datos procesados: ("+fechaDesde+" al "+fechaHasta+")";
		for(int i =-1;i<this.mCantDias;i++){
			if(i>=0){
				this.model = null;
				//Obtengo x dia anterior
				fechaConsulta = TimeUtil.addDays(fechaConsulta, -1);
				
				String fecha[] = fechaConsulta.toString().split("-");
				// Formato fecha YYYYMMdd
				fechaArch = fecha[0] + fecha[1] + fecha[2].substring(0, 2);
				String nombreProceso = "Movimientos_"+ fechaArch;	
				//Obtengo el modelo del d�a que paso
				
				this.model = MRTLoadTicket.forNameAndDate(getCtx(), nombreProceso ,fechaArch,null);
				if(this.model==null){
					continue;
				}
			}
			int count = 0;
			//Instancio sucursal
			MOrg org = new MOrg(getCtx(), this.mAD_Org_ID_To, null);
			List<MRTCashBox> cajas = null;
			if(todas){		
				//Obtengo todas las cajas para dicha sucursal
				cajas = MRTCashBox.forSucursal(getCtx(),org.get_ID(),get_TrxName());
				//Por cada caja obtengo los movimintos
				for(MRTCashBox caja: cajas){
					int idCaja = Integer.valueOf(caja.getValue());
					String retorno = processMovimientosPorCajaSucursal(this.mAD_Org_ID_To,idLocal,
							idCaja,fechaConsulta,fechaDesde,fechaHasta,model.get_ID(), idEmpresa);
					if(retorno.startsWith("OK")){
						count = count + Integer.valueOf(retorno.substring(5));
					}
				}
			}else{
				String retorno = processMovimientosPorCajaSucursal(this.mAD_Org_ID_To,idLocal,
						idCaja,fechaConsulta,fechaDesde,fechaHasta,model.get_ID(), idEmpresa);
				if(retorno.startsWith("OK")){
					count = count + Integer.valueOf(retorno.substring(5));
				}
			}
			ret += "\n -Se leen "+count+" movimientos, del dia: "+fechaArch;
		}
		//else return retorno;
		
		return ret;
	}
	
	
	
	private String processMovimientosPorCajaSucursal(int idOrg,int inIdLocal,int inIdCaja,Timestamp inFch,
			Timestamp inDesde,Timestamp inHasta,int idMRTLoadTicketID, int idEmpresa){
		System.out.println("OpUp-Se leen movimientos de la caja: "+inIdCaja+" del local: "+inIdLocal);

		//SBT 26/04/2016 Issue # (Nueva version de scantech 1.9.6)
		int cantTotalMov = 0;
		int pageOffSet = 0; // Cantidad de p�gingas
		int pageSize = 0; // Cantidad de registros por p�gina,cantidad de mov por consulta;
		JSONArray resp = null;
		int count=0;//cantidad total de movimientos
			
		if(pageOffSet==0 && pageSize==0){// caso inicial
			//Obtengo todos los movimientos para el local, caja y fecha obtenidas
			resp = MRTRetailInterface.enviarMovimientoTimestamp(getCtx(), this.getAD_Client_ID(),//Env.getAD_Client_ID(getCtx()),
					get_TrxName(), inIdLocal, inIdCaja, inFch,inDesde,inHasta,idEmpresa,pageOffSet);
		}
			
		pageSize = resp.length();
		cantTotalMov = pageSize;
		if(0==pageSize) {
			MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
			log.setDescription("No hay movimientos en la caja "+inIdCaja+" del local "+inIdLocal+" a partir de las "+inFch.toString());
			log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
			log.setName("No hay movimientos");
			log.saveEx();
			//log.log(Level.SEVERE,pageSize+ " movimientos para el d�a: "+fechaDelDia+", local:  "+inIdLocal+" y caja:  "+inIdCaja);
			return "OK - 0";//"No hay datos para el d�a: "+fch+",local:"+inIdLocal+" y caja:"+inIdCaja ;		
		}
			
		count += procesarMovimientos(resp,inIdLocal, inIdCaja, idOrg, idMRTLoadTicketID);
		//Realizo la misma consulta salteando p�ginads mientras la cantidad de registros obtenidos sea 100
		while(pageSize>=100){
			pageOffSet +=pageSize;
			resp = MRTRetailInterface.enviarMovimientoTimestamp(getCtx(), this.getAD_Client_ID(),//Env.getAD_Client_ID(getCtx()),
					get_TrxName(), inIdLocal, inIdCaja, inFch,inDesde,inHasta,idEmpresa,pageOffSet);
			pageSize = resp.length();
			cantTotalMov += pageSize;
			if(pageSize>0){
				count += procesarMovimientos(resp,inIdLocal, inIdCaja, idOrg, idMRTLoadTicketID);
			}
		}
		if(pageOffSet==0) pageOffSet=pageSize;
		System.out.println("Se leen "+count+", de "+cantTotalMov);
		return "OK - "+count;
	}
	
	
	
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 28/4/2016
	 * @param resp
	 * @param inIdLocal
	 * @param inIdCaja
	 * @param idOrg
	 * @param idMRTLoadTicketID
	 * @return
	 */
	private int procesarMovimientos(JSONArray resp, int inIdLocal,
			int inIdCaja, int idOrg, int idMRTLoadTicketID) {
		
		if(null!=resp){
			int cant =0; int count=0;
			try{
				cant = resp.length();
				if(0==cant) return 0;//"No hay datos para el d�a: "+this.fechaConsulta+", local:  "+inIdLocal+" y caja:  "+inIdCaja ;	
				//Comienzo a proesar los moviemientos obtenidos
				for(int i=0;i<cant;i++){
					JSONObject movJson = resp.getJSONObject(i);
					if(null!= movJson){
						//Se busca movimiento prara no duplicar infromaci�n
						MRTMovement movCFE = MRTMovement.forNroCupFiscal(getCtx(),movJson.get("numeroOperacion").toString()
								,this.fechaConsulta,String.valueOf(this.idEmpresa),idOrg,
								String.valueOf(inIdLocal),String.valueOf(inIdCaja), get_TrxName());
						if(null==movCFE){
							//System.out.println("SBT-Comienzo a crear el movimiento con los datos obtenidos");
							MRTMovement mov = new MRTMovement(getCtx(), 0, get_TrxName());
							mov.setAD_Org_ID(0);
							mov.set_ValueOfColumn("AD_Org_ID_To", idOrg);
							mov.set_ValueOfColumn("UY_RT_LoadTicket_ID", model.get_ID());
							String parse = mov.parseMovement(getCtx(), movJson, inIdLocal, inIdCaja, this.idEmpresa, get_TrxName());
							if (parse.equalsIgnoreCase("OK")){
								count++;
							}else{
								System.out.println("SBT-No se proceso el movimiento");
								MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
								log.setDescription(" "+ parse);
								log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
								log.setName("Error al procesar movimiento ");
								log.saveEx();
								huboError = true;
							}
								
						}
//						else if(movCFE.get_ID()>0){
//							if(1398271==movCFE.get_ID()||1398272==movCFE.get_ID()
//									||1398279==movCFE.get_ID()
//									||1398290==movCFE.get_ID()||1398292==movCFE.get_ID()||1398293==movCFE.get_ID()){
//								System.out.println(movCFE.get_ID());//1398279  
//							}
//							 int pag = DB.getSQLValue(get_TrxName(), "SELECT COUNT(*) FROM UY_RT_PaymentMovement "
//							 		+ "	where codigoTipoPago = '10' AND codigoCredito=0 AND UY_RT_Movement_ID = "+movCFE.get_ID());
//							 if(pag>0){
//								//MRTPaymentMovement pay = null;
//								JSONArray payList = movJson.getJSONArray("pagos");//OK-UY
//								for(int j = 0;j<payList.length();j++){
//									JSONObject m_pay = payList.getJSONObject(j);
//									if(JSONObject.NULL!=m_pay.get("codigoTipoPago")){
//										if(m_pay.getInt("codigoTipoPago")==10){
//											int idPay = DB.getSQLValue(get_TrxName(), "SELECT UY_RT_PaymentMovement_ID FROM UY_RT_PaymentMovement WHERE "
//													+ " UY_RT_Movement_ID = "+movCFE.get_ID()+ " AND codigoTipoPago = '10' AND "
//															+ " importePago = "+m_pay.get("importePago").toString()+""
//																	+ " and codigomoneda = '"+m_pay.get("codigoMoneda")+"'");
//											if(idPay>0){
//												DB.executeUpdateEx("UPDATE UY_RT_PaymentMovement set codigoCredito = "+
//														m_pay.getInt("codigoCredito")+ " WHERE UY_RT_PaymentMovement_ID ="+idPay
//														,get_TrxName());
//											}
//										}
//									}
//									
//								}
//							 }
//							//select codigoCredito, * from UY_RT_PaymentMovement where codigoCredito =0  and codigotipopago = '10' 
//							 //and updated >= '2016-09-14' and updated <'2016-09-15'
//						}
					}	
					
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
				huboError = true;
			}
			//return "OK - "+count;
			return count;
		}
		MRTLogFile log = new MRTLogFile(getCtx(), 0, get_TrxName());
		log.setDescription("No hay movimientos en la caja "+inIdCaja+" del local "+inIdLocal+" a partir de las "+fechaConsulta.toString());
		log.set_ValueOfColumn("UY_RT_LoadTicket_ID", idMRTLoadTicketID);
		log.setName("No hay movimientos");
		log.saveEx();
		return 0;//return "OK - 0";//"No hay datos para el d�a: "+fch+",local:"+inIdLocal+" y caja:"+inIdCaja ;
	}

}

/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.Env;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openup.util.Convertir;

/**OpenUp Ltda Issue#
 * @author SBT 18/12/2015
 *
 */
public class MRTMovement extends X_UY_RT_Movement {

	/**
	 * @param ctx
	 * @param UY_RT_Movement_ID
	 * @param trxName
	 */
	public MRTMovement(Properties ctx, int UY_RT_Movement_ID, String trxName) {
		super(ctx, UY_RT_Movement_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTMovement(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue #5153
	 * @author Sylvie Bouissa 14/1/2016
	 * @param ctx
	 * @param string
	 * @param get_TrxName
	 * @return
	 */
	public static MRTMovement forNroCupFiscal(Properties ctx, String nroCF,Timestamp fch,String codEmresa,
			int mAD_Org_ID_To,String codLocal,String codCaja, String trxName) {
		
		String fecha[] = fch.toString().split(" ");
		String fchConsulta = fecha[0];//Se formatea la fecha a yyyy-mm-dd
		String whereClause = X_UY_RT_Movement.COLUMNNAME_numerooperacionfiscal + " = '" + nroCF + "'" +													
				" AND " + X_UY_RT_Movement.COLUMNNAME_IsActive + " = 'Y'" +
				" AND  date_trunc('day'," + X_UY_RT_Movement.COLUMNNAME_FechaOperacion + ") = '" +fchConsulta + "'" + //FechaOperacion debe ser 2016-03-08
				" AND " + X_UY_RT_Movement.COLUMNNAME_codigoempresa + " = '" +codEmresa+ "'" + // Empresa
				" AND " + X_UY_RT_Movement.COLUMNNAME_codigolocal +" = '"+codLocal+"'"+
				" AND " + X_UY_RT_Movement.COLUMNNAME_codigocaja +" = '"+codCaja+"'"+
				" AND AD_Org_ID_To = " + mAD_Org_ID_To ;//Local

		MRTMovement model = new Query(ctx, I_UY_RT_Movement.Table_Name, whereClause, trxName)
			.first();		

		return model;
	}
	
	
	/**
	 * Parseo linea de movimiento con sus detalles descuentos de cada detalle y pagos
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/2/2016
	 * @param ctx
	 * @param resp
	 * @param idLocal
	 * @param idCaja
	 * @param idEmpresa
	 * @param trxName
	 * @return
	 */
	public String parseMovement(Properties ctx,JSONObject resp,int idLocal,int idCaja,int idEmpresa,String trxName){
		String retorno = "OK";
		
		if(JSONObject.NULL==resp && (Integer)this.get_Value("UY_RT_LoadTicket_ID")<=0){
			return "El movimientos no esta asociado o no tiene datos";
		}
		try{
			if(!resp.get("codigoEmpresa").equals(JSONObject.NULL)){
				this.setcodigoempresa(Integer.valueOf((resp.get("codigoEmpresa").toString())));
			}else{
				this.setcodigoempresa(idEmpresa);
			}
			String tot = String.valueOf(resp.getDouble("total"));
			
			this.settotal(new BigDecimal(tot));
			String red = String.valueOf(resp.getDouble("redondeo"));
			
			this.setredondeo(new BigDecimal(red));
			
			this.setcotizacioncompra(new BigDecimal (resp.get("cotizacionCompra").toString()));
			
			this.setnumerocuponfiscal(resp.get("numeroMov").toString());//Hhora hace referencia al numero de cupon fiscal
			
			String fch = resp.getString("fechaOperacion");//"fechaOperacion":"2015-03-17T00:00:00.000-0300"
			String fchHra = fch.substring(0, 4)+fch.substring(5, 7)+fch.substring(8, 10)+fch.substring(11, 13)+fch.substring(14, 16)+fch.substring(17, 19);
			Timestamp fchOperacion = Convertir.convertirYYYYMMddHHMMss(fchHra);
			this.setFechaOperacion(fchOperacion);					
			
			//Se setea en el lugar de operaci�n fiscal.. el numeroOperacion (UY)
			this.setnumerooperacionfiscal(String.valueOf(resp.getInt("numeroOperacion"))); //--> Paso a obtenerlo como numeroOperacion
			
			this.setcotizacionventa(new BigDecimal (resp.get("cotizacionVenta").toString()));
			
			this.setcuponanulada(resp.getBoolean("cuponAnulada"));
			
			if(!resp.get("cantidadItems").equals(JSONObject.NULL))	
				this.setcantidaditems(new BigDecimal(resp.getDouble( "cantidadItems")));
			else
				this.setcantidaditems(Env.ZERO);
			
			this.setcodigomoneda(resp.getString("codigoMoneda"));
			if(this.getcodigomoneda().equalsIgnoreCase("858")){
				this.set_ValueOfColumn("C_Currency_ID", 142);
				//			this.setC_Currency_ID(142); 
			}else if(this.getcodigomoneda().equalsIgnoreCase("840")){
				this.set_ValueOfColumn("C_Currency_ID", 100);
//				this.setC_Currency_ID(100);
			}else if(this.getcodigomoneda().equalsIgnoreCase("986")){
				this.set_ValueOfColumn("C_Currency_ID", 297);
//				this.setC_Currency_ID(297);//REales
			}
			
			this.setcuponcancelado(resp.getBoolean("cuponCancelado"));
			//Se setea el c_currency correspondiente a la moneda ingresada
			if(resp.get("codigoCaja").equals(JSONObject.NULL)){
				this.setcodigocaja(idCaja);
			}else{
				this.setcodigocaja(resp.getInt("codigoCaja"));
			}
			if(resp.get("codigoLocal").equals(JSONObject.NULL)){
				this.setcodigolocal(idLocal);
			}else{
				this.setcodigolocal(resp.getInt("codigoLocal"));
			}
			String descTtal = String.valueOf(resp.getDouble("descuentoTotal"));
			this.setdescuentototal(new BigDecimal(descTtal));
			
			//SBT 13/04/2016 Issue #5784 Se agrega el campo nuevo tipo operaci�n versi�n Qued� liberada la versi�n 1.9.5 de la API
			if(JSONObject.NULL!=resp.get("tipoOperacion")){
				this.set_Value("tipooperacion", resp.getString("tipoOperacion"));
			}
			
			//OpenUp SBT Issue # campos nuevos para identificar eFactura
			if(JSONObject.NULL!=resp.get("serieCfe")){
				this.setseriecfe(resp.get("serieCfe").toString());
			}
			
			if(JSONObject.NULL!=resp.get("tipoCfe")){
				this.settipocfe(resp.get("tipoCfe").toString());
			}
			
			if(JSONObject.NULL!=resp.get("codigoSeguridadCfe")){
				this.setcodigoSeguridadCfe(resp.get("codigoSeguridadCfe").toString());
			}
			//FIN OpenUp SBT Issue # campos nuevos para identificar eFactura
			
			this.saveEx();
			
			//Cargo los pagos asociados
			MRTPaymentMovement pay = null;
			JSONArray payList = resp.getJSONArray("pagos");//OK-UY
			for(int i=0;i<payList.length();i++){
				JSONObject m_pay = payList.getJSONObject(i);
				if(null!=m_pay){
					pay = new MRTPaymentMovement(getCtx(),0,trxName);
					pay.setAD_Client_ID(this.getAD_Client_ID());
					pay.setAD_Org_ID(0);
					pay.set_ValueOfColumn("AD_Org_ID_To", this.get_ValueAsInt("AD_Org_ID_To"));
					pay.setUY_RT_Movement_ID(this.get_ID());
					if(!pay.parsePaymentMovement(ctx,m_pay,trxName)){
						retorno = "Error al procesar pago del Movimiento con Nro de Operacion "
							+this.getnumerooperacionfiscal() +", Nro de moviemiento "+this.getnumerocuponfiscal()
							+ " con fecha "+this.getFechaOperacion()+". Local: "+idLocal +", Caja: "+idCaja;
						this.delete(true);
						return retorno;
					}	
				}	
			}
			
			//Cargo los detalles asociados
			MRTMovementDetail detail = null;
			JSONArray detailLst = resp.getJSONArray("detalles");//OK-UY
			for(int i = 0; i<detailLst.length(); i++ ){
				JSONObject det = detailLst.getJSONObject(i);
				if(null!=det){
					detail = new MRTMovementDetail(ctx, 0, trxName);
					detail.setAD_Client_ID(this.getAD_Client_ID());
					detail.setAD_Org_ID(0);
					detail.set_ValueOfColumn("AD_Org_ID_To", this.get_ValueAsInt("AD_Org_ID_To"));
					detail.setUY_RT_Movement_ID(this.get_ID());
					if(!detail.parseMovementDetailAndDec(ctx,det,trxName)){
						
						retorno = "Error al procesar detalle del Movimiento con Nro de Operacion "
								+this.getnumerooperacionfiscal() +", Nro de moviemiento "+this.getnumerocuponfiscal()
								+ " con fecha "+this.getFechaOperacion() +". Local: "+idLocal +", Caja: "+idCaja;	
						this.delete(true);
						return retorno;
					}
					
					//Cargo los descuentos asociados al detalle
					MRTDiscMovementDetail desc= null;
					if(det.has("descuentos")){
						JSONArray descuentos = det.getJSONArray("descuentos");//descuentos
						if(null!=descuentos){
							for(int j = 0 ;j<descuentos.length();j++){
								JSONObject descO = descuentos.getJSONObject(j);
								if(null!=descO){
									desc = new MRTDiscMovementDetail(ctx, 0, trxName);
									desc.setAD_Org_ID(0);
									desc.set_ValueOfColumn("AD_Org_ID_To", this.get_ValueAsInt("AD_Org_ID_To"));
									desc.setUY_RT_Movement_ID(this.get_ID());
									desc.setUY_RT_MovementDetail_ID(detail.get_ID());
									if(!desc.parseDicountMovementDetail(ctx,descO,trxName)){
									
										retorno =  "Error al procesar Detalle del Descuento del articulo "+detail.getcodigoarticulo()+","
												+ " del Movimiento con Nro. de Operacion "+this.getnumerooperacionfiscal() 
												+", Nro. de moviemiento "+this.getnumerocuponfiscal() + ", con fecha "+this.getFechaOperacion()
												+". Local: "+idLocal +", Caja: "+idCaja;
										this.delete(true);
										return retorno;
									}
								}	
							}						
						}
					}
					
				}
	
			}
		
		}catch(Exception e){
			retorno = "Error al procesar el Movimiento con Nro. de Operacion: "+this.getnumerooperacionfiscal() 
					+", Nro. de moviemiento: "+this.getnumerocuponfiscal() + " con fecha "+this.getFechaOperacion()
					+". Local: "+idLocal +", Caja: "+idCaja +"-ERROR: "
					+e.getMessage();
		}
		return retorno;
	}

}

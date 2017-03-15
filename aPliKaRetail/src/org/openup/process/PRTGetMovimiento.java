/**
 * @author OpenUp SBT Issue#  17/12/2015 17:29:51
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openup.model.MRTDiscMovementDetail;
import org.openup.model.MRTMovement;
import org.openup.model.MRTMovementDetail;
import org.openup.model.MRTPaymentMovement;
import org.openup.retail.MRTRetailInterface;
import org.openup.util.Convertir;
import org.openup.util.OpenUpUtils;

/**
 * @author OpenUp SBT Issue#  17/12/2015 17:29:51
 *
 */
public class PRTGetMovimiento extends SvrProcess {

	private int idLocal = 1;
	private String numCuponFiscal = "";
	private int idCaja = 1;
	private Timestamp fechaInicio = null; //yyyy-mm-dd
	private Timestamp fechaFin = null; //yyyy-mm-dd
	/**
	 * @author OpenUp SBT Issue#  17/12/2015 17:29:51
	 */
	public PRTGetMovimiento() {
		
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("numerocuponfiscal")){
					this.numCuponFiscal = ((String)para[i].getParameter());
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("idCaja")){
					this.idCaja = ((BigDecimal) para[i].getParameter()).intValue();
					
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("idLocal")){
					this.idLocal = ((BigDecimal) para[i].getParameter()).intValue();
					
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaInicio = (Timestamp) para[i].getParameter();
					this.fechaFin = (Timestamp) para[i].getParameter_To();
				}
			}
		}
		System.out.println(fechaFin.toString());
		int difDias = OpenUpUtils.diffDays(fechaFin, fechaInicio);
		if(5<difDias) throw new AdempiereException("El intervalo de fechas de ser menor a 5 días");
		
	}

	@Override
	protected String doIt() throws Exception {
		return "DEPRECATED";
//		//MRTRetailInterface.getVersion(getCtx(), 0, 0, get_TrxName());
//		JSONObject resp = MRTRetailInterface.enviarMovimiento(getCtx(), Env.getAD_Client_ID(getCtx()),
//					 get_TrxName(), idLocal, idCaja, numCuponFiscal,fechaInicio,fechaFin);
//		
//		if(null!= resp){
//			//Se busca movimiento con el numero de cup fiscal recibido para no duplicar información 14/01/25015
//			MRTMovement movCFE = null; //OJO FALTA REALIZAR CAMBIOS !!!!!!!!!!!!!!!!!!!!!! POR MODIF EN SCANNTECH
//				//movCFE = MRTMovement.forNroCupFiscal(getCtx(),resp.getString("numeroCuponFiscal"), get_TrxName());
//			if(null == movCFE ){
//				MRTMovement mov = new MRTMovement(getCtx(), 0, get_TrxName());
//				mov.setnumerooperacionfiscal(
//						String.valueOf(resp.getInt("numeroOperacionFiscal")));
//				mov.setcuponcancelado(resp.getBoolean("cuponCancelado"));
//				String descTtal = String.valueOf(resp.getDouble("descuentoTotal"));
//				mov.setdescuentototal(new BigDecimal(descTtal));
//				//mov.setnumeroserieecf(resp.getString("numeroSerieEcf"));
//				mov.setnumerocuponfiscal(String.valueOf(resp.getInt("numeroCuponFiscal")));
//				mov.setcodigomoneda(resp.getString("codigoMoneda"));
//				String red = String.valueOf(resp.getDouble("redondeo"));
//				mov.setredondeo(new BigDecimal(red));
//				String tot = String.valueOf(resp.getDouble("total"));
//				mov.settotal(new BigDecimal(tot));
//				
//				if(!resp.get("cantidadItems").equals(JSONObject.NULL))
//					mov.setcantidaditems(new BigDecimal(resp.getDouble( "cantidadItems")));
//				else
//					mov.setcantidaditems(Env.ZERO);
//			
//				String fch = resp.getString("fechaOperacion");//"fechaOperacion":"2015-03-17T00:00:00.000-0300"
//
//				String fchHra = fch.substring(0, 4)+fch.substring(5, 7)+fch.substring(8, 10)+
//						fch.substring(11, 13)+fch.substring(14, 16)+fch.substring(17, 19);
//				Timestamp fchOperacion = Convertir.convertirYYYYMMddHHMMss(fchHra);
//
//				mov.setFechaOperacion(fchOperacion);
//				mov.saveEx();
//				
//				MRTPaymentMovement pay = null;
//				JSONArray payList = resp.getJSONArray("pagos");
//				for(int i=0;i<payList.length();i++){
//					JSONObject m_pay = payList.getJSONObject(i);
//					if(null!=m_pay){
//						pay = new MRTPaymentMovement(getCtx(),0,get_TrxName());
//						String imp = String.valueOf(m_pay.getDouble("importe"));
//						pay.setImporte(new BigDecimal(imp));
//						pay.setcodigotipopago(m_pay.getInt("codigoTipoPago"));
//						pay.setcodigomoneda(m_pay.getString("codigoMoneda"));//986 pesos??
//						pay.setUY_RT_Movement_ID(mov.get_ID());
//						pay.saveEx();
//					}	
//				}
//				MRTMovementDetail detail = null;
//				JSONArray detailLst = resp.getJSONArray("detalles");
//				for(int i = 0; i<detailLst.length(); i++ ){
//					JSONObject det = detailLst.getJSONObject(i);
//					if(null!=det){
//						detail = new MRTMovementDetail(getCtx(), 0, get_TrxName());
//						detail.setcodigotipodetalle(det.getInt("codigoTipoDetalle"));
//					
//						String tasaICMS = String.valueOf(det.getDouble("tasaICMS"));
//						detail.settasaicms(new BigDecimal(tasaICMS));
//						if(!"null".equalsIgnoreCase(String.valueOf(det.get("medidaVenta")))) {
//								detail.setmedidaventa(det.getString("medidaVenta"));
//						}
//									
//						String mtoICMS = String.valueOf(det.getDouble("montoICMS"));
//						detail.setmontoicms(new BigDecimal(mtoICMS));
//						if(!"null".equalsIgnoreCase(String.valueOf(det.get("tipoTributoSalida")))) {
//							detail.settipotributosalida(det.getString("tipoTributoSalida"));
//						}
//						
//						String descto = String.valueOf(det.getDouble("descuento"));
//						detail.setdescuento(new BigDecimal(descto));
//						if(!"null".equalsIgnoreCase(String.valueOf(det.get("tipoTributoSalida")))) {
//							detail.setdescripcionarticulo(det.getString("descripcionArticulo"));
//						}
//						
//						String impte = String.valueOf(det.getDouble("importe"));
//						detail.setImporte(new BigDecimal(impte));
//						detail.setcodigoarticulo(det.getString("codigoArticulo"));
//						
//						String impteU = String.valueOf(det.getDouble("importeUnitario"));
//						detail.setimporteunitario(new BigDecimal(impteU));
//						
//						String cant = String.valueOf(det.getDouble("cantidad"));
//						detail.setCantidad(new BigDecimal(cant));
//						detail.setcodigobarras(det.getString("codigoBarras"));
//						
//						detail.setUY_RT_Movement_ID(mov.get_ID());
//						detail.saveEx();
//						
//						MRTDiscMovementDetail desc= null;
//						if(resp.has("descuentos")){
//							JSONArray descuentos = resp.getJSONArray("descuentos");//descuentos
//							if(null!=descuentos){
//								for(int j = 0 ;j<descuentos.length();j++){
//									JSONObject descO = descuentos.getJSONObject(i);
//									if(null!=descO){
//										desc = new MRTDiscMovementDetail(getCtx(), 0, get_TrxName());
//										desc.setiddescuento(descO.getInt("idDescuento"));
//										
//										String impteDesc = String.valueOf(descO.getDouble("importeDescuento"));
//										desc.setimportedescuento(new BigDecimal(impteDesc));
//										desc.settipodescuento(descO.getString("tipoDescuento"));
//										desc.setidpromocion(descO.getString("idPromocion"));
//										
//										desc.setUY_RT_Movement_ID(mov.get_ID());
//										desc.setUY_RT_MovementDetail_ID(detail.get_ID());
//										
//										desc.saveEx();
//										
//									}	
//								}						
//							}
//						}
//						
//					}
//			}
//				return "Se persisten los datos OK ";
//
//			} return "Existe movimiento con Nº de Cupon Fiscal "+movCFE.getnumerocuponfiscal();
//		
//		}else return "No hay datos";
	}
}
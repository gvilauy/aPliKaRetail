/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.json.JSONObject;
import org.openup.util.Convertir;

/**OpenUp Ltda Issue#
 * @author SBT 18/12/2015
 *
 */
public class MRTMovementDetail extends X_UY_RT_MovementDetail {

	/**
	 * @param ctx
	 * @param UY_RT_MovementDetail_ID
	 * @param trxName
	 */
	public MRTMovementDetail(Properties ctx, int UY_RT_MovementDetail_ID,
			String trxName) {
		super(ctx, UY_RT_MovementDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTMovementDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/2/2016
	 * @param ctx
	 * @param det
	 * @param trxName
	 */
	public boolean parseMovementDetailAndDec(Properties ctx, JSONObject det,
			String trxName) {
		if(JSONObject.NULL!=det && this.getUY_RT_Movement_ID()>0){
			try{
				if(JSONObject.NULL!=det.get("porcentajeIVA")){//nuevo BIGDECIMAL
					//nuevo CREAR CAMPO porcentajeIVA
					//OpenUP SBT 13/04/2016 Issue #5784
					//BigDecimal pIVA = new BigDecimal(det.getDouble("porcentajeIVA"));
					this.setPorcentajeIVA(det.getInt("porcentajeIVA"));
				}
				
				if(JSONObject.NULL!=det.get("montoIVA")){//nuevo BIGDECIMAL
					//nuevo CREAR CAMPO montoIVA
					//OpenUP SBT 13/04/2016 Issue #5784
					BigDecimal mtoIVA = new BigDecimal(det.getDouble("montoIVA"));
					this.setMontoIVA(mtoIVA);
				}

				if(JSONObject.NULL!=det.get("codigoTipoDetalle"))
					this.setcodigotipodetalle(det.getInt("codigoTipoDetalle"));
				
				if(JSONObject.NULL!=det.get("codigoArticuloPadre")){
					this.setcodigoarticulopadre(det.get("codigoArticuloPadre").toString());
				}
				if(JSONObject.NULL!=det.get("codigoArticulo") && null!=det.get("codigoArticulo") && "null"!= det.get("codigoArticulo").toString().toLowerCase()){
					
					this.setcodigoarticulo(det.get("codigoArticulo").toString());
					if(!this.getcodigoarticulo().startsWith("REC")){
						int mProdID = DB.getSQLValueEx(trxName, "SELECT M_Product_ID FROM M_Product "
								+ "WHERE trim(LEADING '0' FROM value)=trim(LEADING '0' FROM '"+det.get("codigoArticulo").toString().trim()+"')"
										+ " AND AD_Client_ID IN (0,(SELECT AD_Client_ID FROM AD_Client where ad_client_id!=0))");
						if(mProdID>0){
							this.setM_Product_ID(mProdID);
						}else{			
							System.out.println("Producto desde m_prod: "+mProdID);
							System.out.println(this.getcodigoarticulo());

						}
					}
					
				}
				
				if(JSONObject.NULL!=det.get("codigoBarras") && null!=det.get("codigoBarras") && "null"!= det.get("codigoBarras").toString().toLowerCase()){
					
					this.setcodigobarras((det.get("codigoBarras").toString()));
					if(!this.getcodigobarras().startsWith("REC")){

						if(this.getM_Product_ID()==0){
							
							int mProdUpcID = DB.getSQLValueEx(trxName, "SELECT M_Product_ID FROM UY_ProductUPC WHERE "
									+ "upc='"+det.get("codigoBarras").toString().trim()+"' AND AD_Client_ID IN (0,"
									+ "(SELECT AD_Client_ID FROM AD_Client where ad_client_id!=0))");
							
							if(mProdUpcID>0){
								this.setM_Product_ID(mProdUpcID);
							}else{
								System.out.println("M_Prod desde UPC: "+mProdUpcID);
								System.out.println(this.getcodigobarras());
							}
						}
					}
				}	
				
				if(JSONObject.NULL!=det.get("descripcionArticulo")){
					this.setdescripcionarticulo((det.getString("descripcionArticulo")));
				}
				
				if(JSONObject.NULL!=det.get("cantidad") && null!=det.get("cantidad")){
					String cant = String.valueOf(det.getDouble("cantidad"));
					this.setCantidad(new BigDecimal(cant));
				}else{
					this.setCantidad(Env.ZERO);
				}

				if(JSONObject.NULL!=det.get("importeUnitario")){
					String impteU = String.valueOf(det.getDouble("importeUnitario"));
					this.setimporteunitario(new BigDecimal(impteU));
				}else{
					this.setimporteunitario(Env.ZERO);
				}
				
				
				String impte = String.valueOf(det.getDouble("importe"));
				this.setImporte(new BigDecimal(impte));
				
				String descto = String.valueOf(det.getDouble("descuento"));
				this.setdescuento(new BigDecimal(descto));
				
				if(!JSONObject.NULL.equals(det.get("medidaVenta"))) {
					this.setmedidaventa(det.getString("medidaVenta"));
				}

				if(!JSONObject.NULL.equals(det.get("codigoServicio"))) {
					this.setcodigoservicio(det.getInt("codigoServicio"));
				}
				if(!JSONObject.NULL.equals(det.get("numeroServicio"))) {
					this.setnumeroservicio("numeroServicio");
				}
				
				if(!JSONObject.NULL.equals(det.get("fechaServicio"))) {
					
					String f = det.getString("fechaServicio");//"fechaOperacion":"2015-03-17T00:00:00.000-0300"
					String fchH = f.substring(0, 4)+f.substring(5, 7)+f.substring(8, 10)+
							f.substring(11, 13)+f.substring(14, 16)+f.substring(17, 19);
					Timestamp fchOpe = Convertir.convertirYYYYMMddHHMMss(fchH);

					this.setfechaservicio(fchOpe);
				}
				//SBT 28/10/2016 Issue #7674
				if(!JSONObject.NULL.equals(det.get("codigoCategoria"))) {
					this.setcodigoCategoria(Integer.parseInt(det.get("codigoCategoria").toString()));
					
					if(this.getcodigoCategoria()>0){
						String sql = "SELECT UY_ProductGroup_ID FROM UY_ProductGroup "
								+ " WHERE POSCode = '"+det.get("codigoCategoria").toString()+"'"
								+ "	AND AD_Client_ID IN (0, (SELECT AD_Client_ID FROM AD_Client where AD_Client_ID<>0)) ";
						int prodGroupID = DB.getSQLValue(null,sql);
						if(prodGroupID>0){
							this.setUY_ProductGroup_ID(prodGroupID);
						}else if(this.getcodigoCategoria()==240){
							//SBT 21/12/2016 Issue #8259
							sql = "SELECT UY_ProductGroup_ID FROM UY_ProductGroup "
									+ " WHERE Value = 'NO DEFINIDO' "
									+ "	AND AD_Client_ID IN (0, (SELECT AD_Client_ID FROM AD_Client where AD_Client_ID<>0)) ";
							prodGroupID = DB.getSQLValue(null,sql);
							this.setUY_ProductGroup_ID(prodGroupID);
						}
					}
				}
					
				
				this.saveEx();
				return true;
			}catch(Exception e){
				System.out.println("Error en lectura de detalle:"+det.toString());
				System.out.println(e.getMessage());			
			}
		}
		return false;
	}

	/**
	 * Retorna lista de  detalles asociados a un movimiento (sistema de cajas)
	 * OpenUp Ltda Issue #6253
	 * @author Sylvie Bouissa 6/7/2016
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static List<MRTMovementDetail> getDetailListForMov(Properties ctx,
			int movementID, String trxName) {
		
		String where = X_UY_RT_MovementDetail.COLUMNNAME_IsActive + " = 'Y' "
						+" AND " + X_UY_RT_MovementDetail.COLUMNNAME_UY_RT_Movement_ID + " = "+ movementID;
		// new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		List<MRTMovementDetail> details = new Query(ctx,I_UY_RT_MovementDetail.Table_Name,where,trxName).list();
		
		if(details.size()>0){
			return details;
		}
		
		return null;
	}

}

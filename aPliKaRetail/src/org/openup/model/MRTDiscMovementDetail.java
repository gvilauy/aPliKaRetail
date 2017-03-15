/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.json.JSONObject;

/**OpenUp Ltda Issue#
 * @author SBT 18/12/2015
 *
 */
public class MRTDiscMovementDetail extends X_UY_RT_DiscMovementDetail {

	/**
	 * @param ctx
	 * @param UY_RT_DiscMovementDetail_ID
	 * @param trxName
	 */
	public MRTDiscMovementDetail(Properties ctx,
			int UY_RT_DiscMovementDetail_ID, String trxName) {
		super(ctx, UY_RT_DiscMovementDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTDiscMovementDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/2/2016
	 * @param ctx
	 * @param descO
	 * @param trxName
	 */
	public boolean parseDicountMovementDetail(Properties ctx, JSONObject descO,
			String trxName) {
		if(JSONObject.NULL!=descO && this.getUY_RT_Movement_ID()>0 && this.getUY_RT_MovementDetail_ID()>0){
			try{
				if(!JSONObject.NULL.equals(descO.get("idDescuento")))
					this.setiddescuento(descO.getInt("idDescuento"));
				
				if(!JSONObject.NULL.equals(descO.get("importeDescuento"))){
					String impteDesc = String.valueOf(descO.getDouble("importeDescuento"));
					this.setimportedescuento(new BigDecimal(impteDesc));
				}
				
				if(!JSONObject.NULL.equals(descO.get("tipoDescuento"))){
					this.settipodescuento(descO.getString("tipoDescuento"));
				}
				
				if(!JSONObject.NULL.equals(descO.get("idPromocion"))){
					this.setidpromocion(descO.get("idPromocion").toString());
					//this.setidpromocion(descO.getString("idPromocion"));
				}
	
				this.saveEx();
				return true;
			}catch(Exception e){
				e.getMessage();
			}
		}
		return false;
	}

}

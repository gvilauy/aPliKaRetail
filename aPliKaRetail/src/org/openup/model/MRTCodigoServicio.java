/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTCodigoServicio extends X_UY_RT_CodigoServicio {

	/**
	 * @param ctx
	 * @param UY_RT_CodigoServicio_ID
	 * @param trxName
	 */
	public MRTCodigoServicio(Properties ctx, int UY_RT_CodigoServicio_ID,
			String trxName) {
		super(ctx, UY_RT_CodigoServicio_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTCodigoServicio(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param string
	 * @param get_TrxName
	 * @return
	 */
	public static MRTCodigoServicio forValue(Properties ctx, String value,
			String trxName) {
		String whereClause = X_UY_RT_CodigoServicio.COLUMNNAME_Value + " = '" + value + "'" ;
		
		MRTCodigoServicio model = new Query(ctx, I_UY_RT_CodigoServicio.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

}

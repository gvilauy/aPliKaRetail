/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**OpenUp Ltda Issue #5780
 * @author SBT 13/6/2016
 *
 */
public class MRTTransactionDay extends X_UY_RT_TransactionDay {

	/**
	 * @param ctx
	 * @param UY_RT_TransactionDay_ID
	 * @param trxName
	 */
	public MRTTransactionDay(Properties ctx, int UY_RT_TransactionDay_ID,
			String trxName) {
		super(ctx, UY_RT_TransactionDay_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTransactionDay(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**Se parsea la información y por la posición se setea el dato donde corresponde.
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 13/6/2016
	 * @param lineSplit
	 * @param valueOf
	 * @param get_ID
	 */
	public void parseLineTrxDay(String[] lineSplit, String positionfile, int mUYRTTR_ID) {
		
		this.setsucursal(lineSplit[0]);//0
		this.setcaja(lineSplit[1]);//1
		this.setticket(lineSplit[2]);//2
		this.setfechatrx(lineSplit[3]);//3
		this.sethora(lineSplit[4]);//4
		this.settipo(lineSplit[5]);//5
		this.setmodo(lineSplit[6]);//6
		
		String monto = lineSplit[7]; //123456 --Largo 6
		String decimal = monto.substring(monto.length()-2, monto.length());
		monto = monto.substring(0, monto.length()-2)+"."+ decimal;
		this.setmonto(new BigDecimal (monto));
		//this.setmonto(((lineSplit[7]!=null)? new BigDecimal(lineSplit[7]):BigDecimal.ZERO));//7
		this.setmoneda(lineSplit[8]);//8
		this.setnrotarjeta(lineSplit[9]);//9
		this.setfechaexp(lineSplit[10]);//10
		this.setmdoingreso(lineSplit[11]);//11
		this.setautorizacion(lineSplit[12]);//12
		this.setidtarjeta(lineSplit[13]);//13
		this.setPlan(lineSplit[14]);//14
		this.setcuotas((lineSplit[15]!=null)? Integer.valueOf(lineSplit[15]):0);//15
		this.setvoucher(lineSplit[16]);//16
		this.setvoucherori(lineSplit[17]);//17
		this.setfechaori(lineSplit[18]);//18
		this.setterminal(lineSplit[19]);//19
		this.setcomercio(lineSplit[20]);//20
		this.setlote(lineSplit[21]);//21
		this.setfechacierre(lineSplit[22]);//22
		this.setstatuscierre(lineSplit[23]);//23
		this.settipotarjeta(lineSplit[24]);//24
		
		this.setUY_RT_TransactionReport_ID(mUYRTTR_ID);
		this.setpositionfile(positionfile);
		
		String tipo = "VENTA"; //1
		 if(this.gettipo().equals("2") || this.gettipo().equals("02")){
			tipo = "ANULACION";
		}else if(this.gettipo().equals("3") || this.gettipo().equals("03")){
			tipo = "DEVOLUCION";
		}	
		
		String modo = "OFF-LINE";//1
		if(this.getmodo().equalsIgnoreCase("0") || this.getmodo().equalsIgnoreCase("00")){
			modo = "ON-LINE";
		}
			
		String moneda = "PESOS"; //1
		if(this.getmoneda().equalsIgnoreCase("02") ||this.getmoneda().equalsIgnoreCase("2")){
			moneda = "DOLARES";
		}
		
		this.setName(this.getfechatrx()+":"+this.gethora()+"_"+tipo+"-"+modo+"-"+moneda+".");

	}
	
	/**
	 * Retorna lista de transacciones diaras para el informe de transacciones recibido
	 * OpenUp Ltda Issue #5780
	 * @author Sylvie Bouissa 14/6/2016
	 * @param ctx
	 * @param mTrxRID
	 * @param trxName
	 * @return
	 */
	public static List<MRTTransactionDay> forTrxRepOrderbyMP(Properties ctx,int mTrxRID,
			int adClientID,String trxName){
		String whereClause = X_UY_RT_TransactionDay.COLUMNNAME_UY_RT_TransactionReport_ID +"="+
				mTrxRID + " AND IsActive = 'Y' "
						+ " AND AD_Client_ID = "+adClientID;

		List<MRTTransactionDay> lines = new Query(ctx, I_UY_RT_TransactionDay.Table_Name, whereClause, trxName)
		.setOrderBy(X_UY_RT_TransactionDay.COLUMNNAME_tipotarjeta) //0-Credito, 1-Debito
		.list();
		
		return lines;
	}
}

/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.openup.model.MRTLoadTicket;

/**OpenUp Ltda Issue #
 * @author sylvie 8 feb. 2017
 *
 */
public class PRTLoadSalesMovements extends SvrProcess {
	
	private int m_LoadTicketID = 0;//ID generico para todos los clientes que contienen los mov diarios

	private Properties m_Ctx = null;
	private String m_TrxName ="";
	
	private MClient client = null;
	
	/**	Logger							*/
	protected CLogger log = CLogger.getCLogger (getClass());
	/**
	 * 
	 */
	public PRTLoadSalesMovements() {
		// TODO Auto-generated constructor stub
	}

	public PRTLoadSalesMovements(Properties ctx, String trx,int loadTicketID) {
		m_Ctx = ctx;
		m_TrxName = trx;
		m_LoadTicketID = loadTicketID;
		//Se obtiene cliente por defecto
		client = this.getDefaultClient();
	}
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_RT_LoadTicket_ID")){
					this.m_LoadTicketID = ((BigDecimal) para[i].getParameter()).intValue();
				}
			}
		}
		//Se obtiene ad_client
		client = this.getDefaultClient();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
		
		return this.execute();
	}

	public String execute() throws Exception{
		if(m_LoadTicketID>0 || (client!=null && client.get_ID()>0)){
						
			String provider = MSysConfig.getValue("UY_RT_PROVIDER", client.get_ID());
			log.log(Level.FINE,"INICIO MOVIMIENTO DE STOCK AUTOMATICO:"+ new Timestamp(System.currentTimeMillis()).toString());
			System.out.println("---------- INICIO MOVIMIENTO DE STOCK AUTOMATICO DEL DIA: "+ new Timestamp(System.currentTimeMillis()).toString()+" -------------");
			
			if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
				//Insancio el proceso del día
				MRTLoadTicket ltkt = new MRTLoadTicket(m_Ctx, m_LoadTicketID, m_TrxName);
				if(ltkt!=null && ltkt.get_ID()>0){
					PRTUpdateStockPazos movs = new PRTUpdateStockPazos(ltkt,m_Ctx,m_TrxName);
					movs.execute();
					return "OK";
				}else{
					return "No se puede obtener proceso del dia";
				}
				
			}else if (provider.equalsIgnoreCase("Planeta")) {
				PRTUpdateStockScanntech movs = new PRTUpdateStockScanntech(client,m_Ctx,m_TrxName);
				movs.execute();
				return "OK";
			}
			log.log(Level.FINE,"FIN MOVIMIENTO DE STOCK AUTOMATICO:"+ new Timestamp(System.currentTimeMillis()).toString());
			System.out.println("---------- FIN MOVIMIENTO DE STOCK AUTOMATICO DEL DIA: "+ new Timestamp(System.currentTimeMillis()).toString()+" -------------");
		
		}
		return "Proceso padre nulo:"+m_LoadTicketID;
	}
	
	/**Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda Issue #
	 * @author SBT 13/02/2016
	 * @return
	 */
	private MClient getDefaultClient() {		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
			
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
			
		return value;
	}
	
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param salida
	 * @return
	 */
	public static int getLocatorID(MInOut inout) {
		return DB.getSQLValue(null, "SELECT M_Locator_ID FROM "
				+ " M_Locator WHERE AD_Client_ID ="+inout.getAD_Client_ID()
				+ " AND AD_Org_ID ="+inout.getAD_Org_ID()+" AND M_Warehouse_ID = "
				+  inout.getM_Warehouse_ID());

	}
	
	/**
	 * OpenUp Ltda Issue #
	 * @author SBT 14 feb. 2017
	 * @param salida
	 * @return
	 */
	public  static int getWarehouseID(MInOut inout) {
		return DB.getSQLValue(null,"SELECT M_Warehouse_ID FROM "
				+ " AD_OrgInfo WHERE AD_Client_ID = "+inout.getAD_Client_ID()
				+ " AND AD_Org_ID = "+inout.getAD_Org_ID()+" AND isActive = 'Y'");
	}
	
}

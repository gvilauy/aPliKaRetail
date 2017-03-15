/**
 * 
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.openup.model.MRTAuditLoadTicket;
import org.openup.model.MRTConfigIdOrg;
import org.openup.model.MRTLoadTicket;
import org.python.core.exceptions;

/**OpenUp Ltda Issue#
 * @author sylvie 13 feb. 2017
 *
 */
public class PRTUpdateStockScanntech {
	/**	Logger							*/
	protected CLogger log = CLogger.getCLogger (getClass());
	
	private  String mCodigoSNPos = "snpos";
	private static final int CodigoTipoDoc_DEV = 1000190;
	private static final int CodigoTipoDoc_ENTREGA = 1000114;
	
	private static final String TABLA_MOLDE = "UY_Molde_RT_Stock";
	private static final int COD_VENTA = 4;
	private static final int COD_DEV = 5;
	//Variables
	private MRTLoadTicket m_loadTicket = null;
	private Properties m_ctx = null;
	private String m_trxName = "";
	private Waiting waiting = null;
	//Parametros comunes
	private MBPartner m_cbPartnerPOS=null;
	private MBPartnerLocation m_BPLocationID = null;
	
	private MClient m_client = null;
	
	List<MRTConfigIdOrg> lstIdsScanntech = null;
	
	/**
	 * Constructor
	 */
	public PRTUpdateStockScanntech(MClient cliente,Properties ctx, String trx) {
		m_ctx  = ctx;
		m_trxName =trx;
		m_client = cliente;
	}
	
	public void execute() throws Exception{
		
		//Inicio variables
		initParameters();
		
		int idEmpresa = 0; int  adOrgID_To = 0;
		// Por cada sucursal creo los movimientos que sean necesarios
		for(MRTConfigIdOrg conf :lstIdsScanntech){
			//Otengo las Sucursales
			idEmpresa = conf.getidentifempresa();
			adOrgID_To = conf.getAD_Org_ID_To();
			MOrg org = new MOrg(m_ctx, adOrgID_To, null);
			//Elimino datos 
			deleteData();
			log.log(Level.FINE,"INI STOCK EN SUCURSAL: "+ org.getValue()+"-"+org.getName());
			System.out.println("INI STOCK EN SUCURSAL: "+ org.getValue()+"-"+org.getName());
			//Cargar datos en tabla molde para dicha sucursal
			loadDataToMolde(idEmpresa,adOrgID_To);
			
			//Se crea stock de salida por ventas, de los datos que se encuentran en la tabla molde
			createMovimiento(idEmpresa,adOrgID_To,true);
			
			//Se crea stock de entrada por devoluciones, de los datos que se encuentran en la tabla molde
			createMovimiento(idEmpresa,adOrgID_To,false);
			
			setDataStocked(adOrgID_To);
			log.log(Level.FINE,"FIN STOCK EN SUCURSAL: "+ org.getValue()+"-"+org.getName());
			System.out.println("FIN STOCK EN SUCURSAL: "+ org.getValue()+"-"+org.getName());

		}
			
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 16 feb. 2017
	 */
	private void setDataStocked(int adOrgIDTo) {
		try{
			DB.executeUpdate("UPDATE UY_RT_Movement SET isstocked='Y' WHERE UY_RT_Movement_ID IN ("
					+ "SELECT UY_RT_Movement_ID FROM "+TABLA_MOLDE+" WHERE M_Product_ID>0 "
							+ " AND AD_Org_ID_To ="+adOrgIDTo
							+ " )", m_trxName);
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/**Se cargan las lineas a tener en cuenta para la creacion de los movimientos 
	 * OpenUp Ltda Issue#
	 * @author SBT 14 feb. 2017
	 */
	private void loadDataToMolde(int idEmpresa,int sucursal) {
		String sql = "", insert = ""; 
		String dateFrom = "2017-02-13";
		if(MSysConfig.getValue("UY_RT_STK_FROM", 0)!=null){
			dateFrom = MSysConfig.getValue("UY_RT_STK_FROM", 0);
		}
		try{
			sql = "select 100,now(),m.ad_org_id,m.ad_client_id,m.AD_Org_ID_To, "
					+ "	m.UY_RT_Movement_ID,md.UY_RT_MovementDetail_ID,"
					+ "	md.M_Product_ID,md.cantidad,date_trunc('day',m.FechaOperacion),"
					+ " md.codigotipodetalle,md.uy_productgroup_id "
					+ " FROM UY_RT_Movement m"
					+ "	join UY_RT_MovementDetail md on (m.UY_RT_Movement_ID=md.UY_RT_Movement_ID)"
					+ "	WHERE m.AD_Org_ID_To = "+sucursal+" AND m.isstocked = 'N'"
					+ "	AND m.tipooperacion = 'VENTA' AND m.cuponanulada = 'N' "
					+ " AND date_trunc('day',m.FechaOperacion) >= '"+dateFrom+"' "
					//+ " AND date_trunc('day',m.FechaOperacion) <= '2017-02-02' " Para testing
					+ " AND md.M_Product_ID IS NOT NULL "
					+ " AND ((md.codigotipodetalle IN ("+COD_DEV+") AND m.cuponcancelado='Y') "
					+ " OR (md.codigotipodetalle IN ("+COD_VENTA+") AND m.cuponcancelado='N')) "
					+ "	order by md.codigotipodetalle,m.FechaOperacion";
			
			insert = "INSERT INTO " + TABLA_MOLDE + "(ad_user_id,created,ad_org_id,ad_client_id,AD_Org_ID_To,UY_RT_Movement_ID,"
					+ "UY_RT_MovementDetail_ID,M_Product_ID,cantidad,FechaOperacion,codigotipodetalle,"
					+ "uy_productgroup_id )";
			
			DB.executeUpdateEx(insert + sql, null);

					
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
	}

	/**Proceso los datos de la tabla molde solo de ventas para crear los movimientos que sea necesario 
	 * agrupando por fecha
	 * OpenUp Ltda Issue#
	 * @author SBT 13 feb. 2017
	 */
	private void createMovimiento(int idEmpresa,int sucursal, boolean salida) {
		String sql = ""; 
		ResultSet rs = null; PreparedStatement pstmt =null;
		int codCta = COD_VENTA;
		if(!salida){
			codCta = COD_DEV;
		}
		try{
			
			sql = "SELECT FechaOperacion,sum(cantidad),M_Product_ID "
					+ " FROM "+TABLA_MOLDE+" "
					+ " WHERE AD_Org_ID_To = "+sucursal
					+ " AND codigotipodetalle = "+codCta
					+ " GROUP BY FechaOperacion, M_Product_ID"
					+ " ORDER BY FechaOperacion, M_Product_ID";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			System.out.println(totalRowCount);
			rs.beforeFirst();
			Timestamp fchMovimiento = null;
			MInOut mMinOut = null;
			while (rs.next()) {
				if(fchMovimiento == null || rs.getTimestamp(1).after(fchMovimiento)){
					if(mMinOut!=null && mMinOut.get_ID()>0){
						mMinOut.processIt(DocAction.ACTION_Complete);
//						System.out.println(mMinOut.getDocStatus());
//						if(mMinOut.getDocStatus().equalsIgnoreCase("CO")){
//							PRTLoadSalesMovements.setClientIDStkTrk(mMinOut);
//						}
					}
					fchMovimiento = rs.getTimestamp(1);
					m_loadTicket = obtenerCargaDelDia(rs.getTimestamp(1));
					
					mMinOut = new MInOut(m_ctx,0,m_trxName);
					createCabezalComon(mMinOut,fchMovimiento,sucursal);
					if(salida)
						createCabezalSalida(mMinOut);
					else
						createCabezalEntrada(mMinOut,fchMovimiento);
					mMinOut.saveEx();
				}
				
				MInOutLine line = new MInOutLine(m_ctx, 0, m_trxName);
				line.setAD_Client_ID(mMinOut.getAD_Client_ID());
				line.setAD_Org_ID(mMinOut.getAD_Org_ID());
				line.setM_InOut_ID(mMinOut.get_ID());
				
				MProduct prod = new MProduct(m_ctx, rs.getInt(3), null);
				if(prod==null || prod.get_ID()<=0) throw new AdempiereException("No se pudo instanciar producto");
				line.setM_Product_ID(prod.get_ID());
				line.setC_UOM_ID(prod.getC_UOM_ID());
				
				int m_MLocatorId  = PRTLoadSalesMovements.getLocatorID (mMinOut);
				
				if(m_MLocatorId<=0) throw new AdempiereException("No se encuentra localizacion"
						+ "para almacen configurado en la organizacion");
				line.setM_Locator_ID(m_MLocatorId);
				line.setDescription("Linea automática del proceso: "+m_loadTicket.getFileName());
				line.setQtyEntered(rs.getBigDecimal(2));
				line.setMovementQty(rs.getBigDecimal(2));
				line.saveEx();
			}
						
		}catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
		};
		
	}

	/**
	 * Se setean valores al cabezal del movimiento tanto para entrada como para salida
	 * OpenUp Ltda Issue#
	 * @author SBT 15 feb. 2017
	 * @param mMinOut
	 * @param fchMov
	 */
	private void createCabezalComon(MInOut mMinOut, Timestamp fchMov, int ad_org_idTo){
		
		//Defino referencia y descripcion del mocimiento
		mMinOut.setDescription("Movimiento de Stock del dia:"
				+m_loadTicket.getName().replace("Movimientos_", "")+
				", del Proceso de lecruta nro:"+m_loadTicket.getValue()+
				", del archivo:"+m_loadTicket.getName());
		mMinOut.setPOReference("ID="+m_loadTicket.get_ID());
		mMinOut.setAD_Client_ID(m_loadTicket.getAD_Client_ID());
		mMinOut.setAD_Org_ID(ad_org_idTo);
		mMinOut.setAD_User_ID(m_loadTicket.getCreatedBy());
		mMinOut.setC_BPartner_ID(m_cbPartnerPOS.get_ID());// Crear cbpartenr para este movimiento				
		mMinOut.setC_BPartner_Location_ID(m_BPLocationID.get_ID());//location del bpartner creado
		mMinOut.setMovementDate(fchMov);
		mMinOut.setDateAcct(fchMov);
		
		int m_MWarehouse_ID  = PRTLoadSalesMovements.getWarehouseID(mMinOut);
		
		if(m_MWarehouse_ID<=0) 
			throw new AdempiereException("No se encuentra almacen parametrizado"
					+ " en la organizacion: "+m_loadTicket.getAD_Org_ID());
		mMinOut.setM_Warehouse_ID(m_MWarehouse_ID);//Como lo obtengo para no quemar 1000053
		mMinOut.setPriorityRule("5");//-->Minima
		mMinOut.setDeliveryRule("A");//--Disponible
		
		mMinOut.setDeliveryViaRule("D");//Entrega
		mMinOut.setFreightCostRule("I");//Flete incluido
		
		mMinOut.setMovementType("C-");//Embarque a clientes
		mMinOut.setDocStatus("DR");//Borrador
		mMinOut.setDocAction("CO");
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author SBT 8 feb. 2017
	 * @param salida
	 */
	private void createCabezalSalida(MInOut salida) {
		
		salida.setC_DocType_ID(CodigoTipoDoc_ENTREGA);//entrega de material1000114
		salida.setIsSOTrx(true);
		salida.saveEx(m_trxName);
		
	}
	
	private void createCabezalEntrada(MInOut entrada, Timestamp fchMov) {
		
		entrada.setC_DocType_ID(CodigoTipoDoc_DEV);//entrega de material1000114
		entrada.setDateReceived(fchMov); 
		entrada.setIsSOTrx(false);
		entrada.saveEx(m_trxName);
		
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author SBT 14 feb. 2017
	 * @param timestamp
	 * @return
	 */
	private MRTLoadTicket obtenerCargaDelDia(Timestamp fechaIn) {
		MRTLoadTicket model = null;
		try{
			String fecha[] = fechaIn.toString().split("-");
			// Formato fecha YYYYMMdd
			String fechaDelDia = fecha[0] + fecha[1] + fecha[2].substring(0, 2);
			String nombreProceso = "Movimientos_"+ fechaDelDia;	
			//Obtengo el modelo del dia que se recibe
			model = MRTLoadTicket.forNameAndDate(m_ctx, nombreProceso ,fechaDelDia,null);
			if(model==null)
				throw new AdempiereException("No se encuentra carga de movimientos para el dia :"+fechaDelDia);
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		return model;
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author SBT 9 feb. 2017
	 */
	private void initParameters() {
		//Obtengo value por el cual se identifica el socio de negocio para los movimientos
		String snpos = MSysConfig.getValue("UY_RT_SNPOS", 0);
		if(snpos!=null){
			mCodigoSNPos = snpos;
		}
		
		int m_CBP = DB.getSQLValue(null, "SELECT C_BPartner_ID FROM C_BPartner WHERE value = '"+mCodigoSNPos+"' AND isActive = 'Y'"
				+ " AND AD_Client_ID = "+m_client.get_ID());
		if(m_CBP>0){
			m_cbPartnerPOS = new MBPartner(m_ctx,m_CBP,null);
		}else {
			throw new AdempiereException("No se encuentra el socio de negocio para "
					+ "movimiento de stock (codigo="+mCodigoSNPos+")");
		}
		MBPartnerLocation[] bpl = MBPartnerLocation.getForBPartner(m_ctx, m_cbPartnerPOS.get_ID(),null);
		if(bpl==null||bpl.length<=0) throw new AdempiereException("No hay parametrizada "
				+ "localizacion para el socio de negocio: "+mCodigoSNPos);
		m_BPLocationID = bpl[0];

		//Obtengo id de empresa configurados
		lstIdsScanntech = MRTConfigIdOrg.getIds(m_ctx,m_client.get_ID(),m_trxName);
		if (lstIdsScanntech == null || lstIdsScanntech.size()<=0)
			throw new AdempiereException("No se encuentran empresas parametrizadas para el cliente:"
					+m_client.getName());
			
		
	}
	
	/**
	 * Elimino los datos anteriormente procesados
	 * solo dejo los que no se procesan (los que no tienen id de producto)
	 * OpenUp Ltda Issue#
	 * @author SBT 14 feb. 2017
	 * @throws Exception
	 */
	private void deleteData() throws Exception{
		
		String sql = "";
		try{
			
			System.out.println("Eliminando datos...");
			
			sql = " DELETE FROM " + TABLA_MOLDE +"";
					//" WHERE m_product_id is not null ";
			
			DB.executeUpdateEx(sql,null);

		}
		catch (Exception e)
		{
			throw e;
		}
	}
}

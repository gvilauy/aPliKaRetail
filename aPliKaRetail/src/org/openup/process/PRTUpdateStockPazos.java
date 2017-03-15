/**
 * 
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.openup.model.MRTAuditLoadTicket;
import org.openup.model.MRTLoadTicket;

/**OpenUp Ltda Issue #8238
 * Proceso que se encarga de realizar la salida y entrada de stock
 * por las ventas realizadas en el dia.
 * 
 * Previamente debe haberse realizado lectura de archivo salida pazos.
 * @author SBT 7 feb. 2017
 *
 */
public class PRTUpdateStockPazos {

	private  String mCodigoSNPos = "snpos";
	private static final int CodigoTipoDoc_DEV = 1000190;
	private static final int CodigoTipoDoc_ENTREGA = 1000114;
	//Variables
	private MRTLoadTicket m_loadTicket = null;
	private MRTAuditLoadTicket m_audit = null;
	private Properties m_ctx = null;
	private String m_trxName = "";
	private Waiting waiting = null;
	//Parametros comunes
	private MBPartner m_cbPartnerPOS=null;
	private MBPartnerLocation m_BPLocationID = null;
	
	/**
	 * Constructor recibe carga de salida pazos sobre la cual realizar la entrada y salida de stock
	 */
	public PRTUpdateStockPazos(MRTLoadTicket loadTicketP,Properties ctx, String trx) {
		this.m_loadTicket = loadTicketP;
		this.m_ctx  = ctx;
		this.m_trxName = trx;
		MRTAuditLoadTicket audit = MRTAuditLoadTicket.forLoadTicketID(m_ctx, loadTicketP.get_ID(), trx);
		if(audit==null || audit.get_ID()<=0){
			throw new AdempiereException("No se obtiene auditoria para la lectura salida pazos");
		}
		
		this.m_audit = audit;
		
	}
	
	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}
	
	/**
	 * Metodo que se encagar de realizar todas la acciones para crear las dos movimientos de stock
	 * MInOut de salida (ventas), MInOut de entrada (devolucione)
	 * OpenUp Ltda Issue #8238
	 * @author SBT 7 feb. 2017
	 * @throws Exception
	 */
	public void execute() throws Exception {
		if(m_loadTicket.isProcessed() && !m_loadTicket.get_ValueAsBoolean("isstocked")){
			if(m_loadTicket.getAD_Org_ID()==0){
				int[] orgID  = MOrg.getAllIDs("AD_Org", " IsActive= 'Y' AND AD_Org_ID > 0 ", null);
				m_loadTicket.setAD_Org_ID(orgID[0]);
				if(m_audit.getAD_Org_ID()<=0){
					m_audit.setAD_Org_ID(m_loadTicket.getAD_Org_ID());
				}
			}
			
			initParameters();
			
			if(!existeStockOut())
				createSalidaStk();
			if(!existeStockIn())
				createEntradaStk();
			
			m_loadTicket.set_ValueOfColumn("isstocked", true);
			m_loadTicket.saveEx();
			m_audit.saveEx();
		}
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 9 feb. 2017
	 */
	private void initParameters() {
		
		String snpos = MSysConfig.getValue("UY_RT_SNPOS", m_loadTicket.getAD_Client_ID());
		if(snpos!=null){
			mCodigoSNPos = snpos;
		}
		
		int m_CBP = DB.getSQLValue(null, "SELECT C_BPartner_ID FROM C_BPartner WHERE value = '"+mCodigoSNPos+"' AND isActive = 'Y'"
				+ " AND AD_Client_ID = "+m_loadTicket.getAD_Client_ID());
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

	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 9 feb. 2017
	 * @return
	 */
	private boolean existeStockIn() {
		int aux = DB.getSQLValue(null, "SELECT count(*) FROM M_InOut where IsSotrx = 'N' AND DocStatus = 'CO' "
				+ " AND POReference = 'ID="+m_loadTicket.get_ID()+"'"
				+ " AND AD_Client_ID = "+m_loadTicket.getAD_Client_ID()
				+ " AND AD_Org_ID= "+m_loadTicket.getAD_Org_ID());
		if(aux>0) 
			return true;
		
		return false;
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 9 feb. 2017
	 * @return
	 */
	private boolean existeStockOut() {
		int aux = DB.getSQLValue(null, "SELECT count(*) FROM M_InOut where IsSotrx = 'Y' AND DocStatus = 'CO' "
				+ " AND POReference = 'ID="+m_loadTicket.get_ID()+"'"
				+ " AND AD_Client_ID = "+m_loadTicket.getAD_Client_ID()
				+ " AND AD_Org_ID= "+m_loadTicket.getAD_Org_ID());
		if(aux>0) 
			return true;
		
		return false;		
	}

	/**
	 * OpenUp Ltda Issue #8238
	 * @author Sylvie Bouissa 7 feb. 2017
	 */
	private void createSalidaStk() {
		MInOut salida = new MInOut(m_ctx,0,m_trxName);
		//Defino referencia y descripcion del mocimiento
		salida.setDescription("Movimiento de Stock lectura del dia:"+m_audit.getDateValue()+
				", del Proceso de lecruta nro:"+m_loadTicket.getValue()+", del archivo:"+m_loadTicket.getFileName());
		salida.setPOReference("ID="+m_loadTicket.get_ID());
		
		createCabezalSalida(salida);
		createLineasSalida(salida);
		
		salida.processIt(DocAction.ACTION_Complete);
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8 feb. 2017
	 */
	private void createEntradaStk() {
		MInOut entrada = new MInOut(m_ctx,0,m_trxName);
		entrada.setDescription("Movimiento de Stock lectura del dia:"+m_audit.getDateValue()+
				", del Proceso de lecruta nro:"+m_loadTicket.getValue()+", del archivo:"+m_loadTicket.getFileName());
		entrada.setPOReference("ID="+m_loadTicket.get_ID());
		createCabezalEntrada(entrada);
		createLineasEntrada(entrada);
		
		entrada.processIt(DocAction.ACTION_Complete); 

	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param salida
	 */
	private void createCabezalSalida(MInOut salida) {
		salida.setAD_Client_ID(m_loadTicket.getAD_Client_ID());
		salida.setAD_Org_ID(m_loadTicket.getAD_Org_ID());
		salida.setAD_User_ID(m_loadTicket.getCreatedBy());
		
		salida.setC_DocType_ID(CodigoTipoDoc_ENTREGA);//entrega de material1000114
		/*--> Corresponde el tipo de documento entrega de material (C_DocType_ID=1000114)
		u orden de entrega (C_DocType_ID=1000113) value deliveryorder???*/
		
		//MBPartner cbp = MBPartner.forValue(m_ctx, "ventas", m_trxName);
		//if(cbp==null || cbp.get_ID()<=0)
		//	throw new AdempiereException("No se encuentra el socio de negocio para movimiento de stock");
		salida.setC_BPartner_ID(m_cbPartnerPOS.get_ID());// Crear cbpartenr para este movimiento
		
//		MBPartnerLocation[] bpl = MBPartnerLocation.getForBPartner(m_ctx, m_cbPartnerPOS.get_ID(),null);
//		if(bpl==null||bpl.length<=0) throw new AdempiereException("No hay parametrizada localizacion para el socio de negocio Ventas");
		salida.setC_BPartner_Location_ID(m_BPLocationID.get_ID());//location del bpartner creado
	
		
		//salida.setPickDate(m_audit.getDateValue()); ???????
		//salesRep_ID
		
		//Fecha del movimietno
		salida.setMovementDate(m_audit.getDateValue());
		salida.setDateAcct(m_audit.getDateValue());
		
		int m_MWarehouse_ID  = getWarehouseID(salida);
		
		if(m_MWarehouse_ID<=0) throw new AdempiereException("No se encuentra almacen parametrizado en la organizacion: "+
				m_loadTicket.getAD_Org_ID());
		salida.setM_Warehouse_ID(m_MWarehouse_ID);//Como lo obtengo para no quemar 1000053
		salida.setPriorityRule("5");//-->Minima
		salida.setDeliveryRule("A");//--Disponible
		
		salida.setDeliveryViaRule("D");//Entrega
		salida.setFreightCostRule("I");//Flete incluido
		
		salida.setMovementType("C-");//Embarque a clientes
		salida.setDocStatus("DR");//Borrador
		salida.setDocAction("CO");
		salida.setIsSOTrx(true);
		salida.saveEx(m_trxName);
		
	}
	private void setDatosCabezalSalida(MInOut salida) {
		
		salida.setC_DocType_ID(CodigoTipoDoc_ENTREGA);//entrega de material1000114
		salida.setIsSOTrx(true);
		salida.saveEx(m_trxName);
		
	}
	
	private void setDatosCabezalEntrada(MInOut entrada) {
		
		entrada.setC_DocType_ID(CodigoTipoDoc_DEV);//devolucion directa cliente value devol 1000190
		entrada.setDateReceived(m_audit.getDateValue()); 
		entrada.setIsSOTrx(false);
		entrada.saveEx(m_trxName);
		
	}
	/**
	 * Seteo valores del cabezal tanto para salida como para entrada
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 15 feb. 2017
	 * @param mMinOut
	 * @param fchMov
	 */
	private void createCabezalComon(MInOut mMinOut, Timestamp fchMov){
		
		mMinOut.setDescription("Movimiento de Stock lectura del dia:"+m_audit.getDateValue()+
				", del Proceso de lecruta nro:"+m_loadTicket.getValue()+", del archivo:"+m_loadTicket.getFileName());
		mMinOut.setPOReference("ID="+m_loadTicket.get_ID());
		mMinOut.setAD_Client_ID(m_loadTicket.getAD_Client_ID());
		mMinOut.setAD_Org_ID(m_loadTicket.getAD_Org_ID());
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
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param entrada
	 */
	private void createCabezalEntrada(MInOut entrada) {
		
		entrada.setAD_Client_ID(m_loadTicket.getAD_Client_ID());
		entrada.setAD_Org_ID(m_loadTicket.getAD_Org_ID());
		entrada.setAD_User_ID(m_loadTicket.getCreatedBy());


		entrada.setC_DocType_ID(CodigoTipoDoc_DEV);//devolucion directa cliente value devol 1000190
		/*Consultar si corresponde otro tipo de doc*/
		
//		MBPartner cbp = MBPartner.forValue(m_ctx, "ventas", m_trxName);
//		if(cbp==null || cbp.get_ID()<=0)
//			throw new AdempiereException("No se encuentra el socio de negocio para movimiento de stock");
		entrada.setC_BPartner_ID(m_cbPartnerPOS.get_ID());// Crear cbpartenr para este movimiento
		
//		MBPartnerLocation[] bpl = MBPartnerLocation.getForBPartner(m_ctx, m_cbPartnerPOS.get_ID(),null);
//		if(bpl==null||bpl.length<=0) throw new AdempiereException("No hay parametrizada localizacion para el socio de negocio Ventas");
		entrada.setC_BPartner_Location_ID(m_BPLocationID.get_ID());//location del bpartner creado
		
		//salida.setPickDate(m_audit.getDateValue()); ???????
		//salesRep_ID
		
		//Fecha del movimietno
		entrada.setMovementDate(m_audit.getDateValue());
		entrada.setDateAcct(m_audit.getDateValue());
		entrada.setDateReceived(m_audit.getDateValue()); 
		
		
		int m_MWarehouse_ID  = getWarehouseID(entrada);
		if(m_MWarehouse_ID<=0) throw new AdempiereException("No se encuentra almacen parametrizado en la organizacion: "+
				m_loadTicket.getAD_Org_ID());
		entrada.setM_Warehouse_ID(m_MWarehouse_ID);//Como lo obtengo para no quemar 1000053
	
		entrada.setPriorityRule("5");//-->Minima
		entrada.setDeliveryRule("A");//--Disponiblidad ????????????
		
		entrada.setDeliveryViaRule("D");//Entrega
		entrada.setFreightCostRule("I");//Flete incluido
		
		
		
		entrada.setMovementType("C-");//Embarque a clientes
		entrada.setDocStatus("DR");//Borrador
		entrada.setDocAction("CO");
		entrada.setIsSOTrx(false);
		entrada.saveEx(m_trxName);
		
	}

	/**
	 * OpenUp Ltda Issue #
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param salida
	 */
	private void createLineasSalida(MInOut salida) {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;;
			
		try{
			sql = "SELECT a.m_product_id, sum(a.cantidad ) as cantidad"
					+ "	FROM uy_rt_ticket_lineitem a "
					+ " JOIN uy_rt_ticket_header b ON (a.uy_rt_ticket_header_ID=b.uy_rt_ticket_header_ID)"
					+ " WHERE b.uy_rt_loadticket_ID = ? AND b.estadoticket= 'F'"
					+ " AND a.lineacancelada = 0 AND a.cantidad >0 AND a.M_Product_ID IS NOT NULL"
					+ " GROUP BY m_product_id "
					+ " ORDER BY m_product_id ";
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, m_trxName);
			pstmt.setInt(1, m_loadTicket.get_ID());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MInOutLine line = new MInOutLine(m_ctx, 0, m_trxName);
				line.setAD_Client_ID(salida.getAD_Client_ID());
				line.setAD_Org_ID(salida.getAD_Org_ID());
				line.setM_InOut_ID(salida.get_ID());
				
				MProduct prod = new MProduct(m_ctx, rs.getInt(1), null);
				if(prod==null || prod.get_ID()<=0) throw new AdempiereException("No se pudo instanciar producto");
				line.setM_Product_ID(rs.getInt(1));
				line.setC_UOM_ID(prod.getC_UOM_ID());
				
				int m_MLocatorId  = getLocatorID (salida);
				
				if(m_MLocatorId<=0) throw new AdempiereException("No se encuentra localizacion"
						+ "para almacen configurado en la organizacion");
				line.setM_Locator_ID(m_MLocatorId);
				line.setDescription("Linea automática del archivo salida pazos: "+m_loadTicket.getFileName());
				line.setQtyEntered(rs.getBigDecimal(2));
				line.setMovementQty(rs.getBigDecimal(2));
				line.saveEx();
			}
			
		}catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
		}
		
	}
	

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param salida
	 * @return
	 */
	private int getLocatorID(MInOut inout) {
		return DB.getSQLValue(null, "SELECT M_Locator_ID FROM "
				+ " M_Locator WHERE AD_Client_ID ="+inout.getAD_Client_ID()
				+ " AND AD_Org_ID ="+inout.getAD_Org_ID()+" AND M_Warehouse_ID = "
				+  inout.getM_Warehouse_ID());

	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param salida
	 * @return
	 */
	private int getWarehouseID(MInOut inout) {
		return DB.getSQLValue(null,"SELECT M_Warehouse_ID FROM "
				+ " AD_OrgInfo WHERE AD_Client_ID = "+inout.getAD_Client_ID()
				+ " AND AD_Org_ID = "+inout.getAD_Org_ID()+" AND isActive = 'Y'");

	}

	/**
	 * OpenUp Ltda Issue #
	 * @author Sylvie Bouissa 8 feb. 2017
	 * @param entrada
	 */
	private void createLineasEntrada(MInOut entrada) {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;;
			
		try{
			sql = "SELECT a.m_product_id, sum(a.cantidad ) as cantidad"
					+ "	FROM uy_rt_ticket_lineitemreturn a "
					+ " JOIN uy_rt_ticket_header b ON (a.uy_rt_ticket_header_ID=b.uy_rt_ticket_header_ID)"
					+ " WHERE b.uy_rt_loadticket_ID = ? AND b.estadoticket= 'F'"
					+ " AND a.lineacancelada = 0 AND a.M_Product_ID IS NOT NULL"
					+ " GROUP BY m_product_id "
					+ " ORDER BY m_product_id ";
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, m_trxName);
			pstmt.setInt(1, m_loadTicket.get_ID());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MInOutLine line = new MInOutLine(m_ctx, 0, m_trxName);
				line.setAD_Client_ID(entrada.getAD_Client_ID());
				line.setAD_Org_ID(entrada.getAD_Org_ID());
				line.setM_InOut_ID(entrada.get_ID());
				
				MProduct prod = new MProduct(m_ctx, rs.getInt(1), null);
				if(prod==null || prod.get_ID()<=0) throw new AdempiereException("No se pudo instanciar producto");
				line.setM_Product_ID(rs.getInt(1));
				line.setC_UOM_ID(prod.getC_UOM_ID());
				
				int m_MLocatorId  = getLocatorID(entrada);
						
				if(m_MLocatorId<=0) throw new AdempiereException("No se encuentra localizacion"
						+ "para almacen configurado en la organizacion");
				line.setM_Locator_ID(m_MLocatorId);
				line.setDescription("Linea automática del archivo salida pazos: "+m_loadTicket.getFileName());
				line.setQtyEntered(rs.getBigDecimal(2));
				line.setMovementQty(rs.getBigDecimal(2));
				line.saveEx();
			}
			
		}catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
		}
		
	}

}

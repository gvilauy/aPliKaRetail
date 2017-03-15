/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.json.JSONObject;
import org.openup.util.Convertir;

/**OpenUp Ltda Issue#
 * @author SBT 18/12/2015
 *
 */
public class MRTPaymentMovement extends X_UY_RT_PaymentMovement {

	public static int CREDITO = 10;
	public static String VENTA = "VENTA";
	public static String VENTA_ANULADA = "VENTA ANULADA";
	//public static String COD_CRED_CASA = "772832";
	public static int COD_CRED_CASA = 90;
	
	/**
	 * @param ctx
	 * @param UY_RT_PaymentMovement_ID
	 * @param trxName
	 */
	public MRTPaymentMovement(Properties ctx, int UY_RT_PaymentMovement_ID,
			String trxName) {
		super(ctx, UY_RT_PaymentMovement_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTPaymentMovement(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/2/2016
	 * @param ctx
	 * @param m_pay
	 * @param trxName
	 */
	public boolean parsePaymentMovement(Properties ctx, JSONObject m_pay,
			String trxName) {
		if(null!=m_pay && this.getUY_RT_Movement_ID()>0){
			try{
				if(JSONObject.NULL!=m_pay.get("codigoTipoPago")) this.setcodigotipopago(m_pay.getInt("codigoTipoPago"));
				//	luego setear este a partir del anterior - No viene mas desde scanntech 7/07		
				//if(JSONObject.NULL!=m_pay.get("descripcionPlanPagos")) this.setdescripcionplanpagos(m_pay.getString("descripcionPlanPagos"));

				if(JSONObject.NULL!=m_pay.get("codigoPlanPagos")) this.setcodigoplanpagos(Integer.parseInt(m_pay.get("codigoPlanPagos").toString()));
					
				// luego setear este a partir del anterior - No viene mas desde scanntech 7/07	
				//if(JSONObject.NULL!=m_pay.get("descripcionFormaPago")) this.setdescripcionformapago(m_pay.getString("descripcionFormaPago"));
				
				if(JSONObject.NULL!=m_pay.get("numeroTarjeta"))	this.setnumerotarjeta(m_pay.getString("numeroTarjeta"));
				
				if(JSONObject.NULL!=m_pay.get("numeroAutorizacion")) this.setnumeroautorizacion(new BigDecimal(m_pay.get("numeroAutorizacion").toString()));
				
				if(JSONObject.NULL!=m_pay.get("fechaVencimiento")){ 
					String fch = m_pay.getString("fechaVencimiento");//"fechaOperacion":"2015-03-17T00:00:00.000-0300"
					String fchHra = fch.substring(0, 4)+fch.substring(5, 7)+fch.substring(8, 10)+fch.substring(11, 13)+fch.substring(14, 16)+fch.substring(17, 19);
					Timestamp fchOperacion = Convertir.convertirYYYYMMddHHMMss(fchHra);
					this.setfechavencimiento(fchOperacion);
				}
				
				if(JSONObject.NULL!=m_pay.get("codigoMoneda")){
					this.setcodigomoneda(m_pay.getString("codigoMoneda"));//986 pesos??	
				}else{
					System.out.println(m_pay.get("codigoMoneda"));
				}
				if(this.getcodigomoneda().equalsIgnoreCase("858")){
					this.set_ValueOfColumn("C_Currency_ID", 142);
//					this.setC_Currency_ID(142); 
				}else if(this.getcodigomoneda().equalsIgnoreCase("840")){
					this.set_ValueOfColumn("C_Currency_ID", 100);
//					this.setC_Currency_ID(100);
				}else if(this.getcodigomoneda().equalsIgnoreCase("986")){
					this.set_ValueOfColumn("C_Currency_ID", 297);
//					this.setC_Currency_ID(297);//REales
				}

				if(JSONObject.NULL!=m_pay.get("importe")){
					String imp = String.valueOf(m_pay.getDouble("importe"));
					this.setImporte(new BigDecimal(imp));
				}else{
					this.setImporte(Env.ZERO);
				}
				if(JSONObject.NULL!=m_pay.get("cotizacionCompra")){
					String imp = String.valueOf(m_pay.getDouble("cotizacionCompra"));
					this.setcotizacioncompra(new BigDecimal(imp));
				}else{
					this.setcotizacioncompra(Env.ZERO);
				}
				if(JSONObject.NULL!=m_pay.get("cotizacionVenta")){
					String imp = String.valueOf(m_pay.getDouble("cotizacionVenta"));
					this.setcotizacionventa(new BigDecimal(imp));
				}else{
					this.setcotizacionventa(Env.ZERO);
				}
				if(JSONObject.NULL!=m_pay.get("documentoCliente")) this.setdocumentocliente(m_pay.getString("documentoCliente"));
				
				if(JSONObject.NULL!=m_pay.get("numeroDocumentoPago")) this.setnumerodocumentopago(m_pay.getString("numeroDocumentoPago"));
				
				//Integer
				if(JSONObject.NULL!=m_pay.get("numeroCuotasPago")) this.setnumerocuotaspago(Integer.valueOf(m_pay.get("numeroCuotasPago").toString()));
				
				//07/070/16 No vine m�s ahora va a venir como terminalCredito (caja seg�n sello)
				//if(JSONObject.NULL!=m_pay.get("nsuHostAutorizador"))
				if(JSONObject.NULL!=m_pay.get("terminalCredito"))
					this.setterminalCredito(m_pay.getString("terminalCredito"));
				
				 //BIN (6 primeros digitos de la tarjeta) STRING - No viene mas desde scanntech 7/07 lo infiero desde la tarjeta	
				//if(JSONObject.NULL!=m_pay.get("bin")) this.setbin(m_pay.getString("bin")); 	
				
				 //String - 07/07/16 No viene m�s ahora va a venir como comercioCredito (local seg�n sello)
				//if(JSONObject.NULL!=m_pay.get("productoSITEF"))
				if(JSONObject.NULL!=m_pay.get("comercioCredito"))
					this.setcomercioCredito(m_pay.getString("comercioCredito")); //habilitar en la pr�x actualiza de verison

				//SBT 13/04/2016 Issue #5784 Nuevo campo booleano que indica si es un poago de tipo cambio	
				if(JSONObject.NULL!=m_pay.get("cambio")){
					this.set_ValueOfColumn("cambio", m_pay.getBoolean("cambio"));
				}
				
				if(JSONObject.NULL!=m_pay.get("importePago")){
					String impPgo = String.valueOf(m_pay.getDouble("importePago"));
					this.set_ValueOfColumn("ImportePago",new BigDecimal(impPgo));
				}//FIN SBT 13/04/2016 Issue #5784		
				
				//OpneUp SBT 06/07/2016 Issue #6253 se deduce del num de tarjeta el bin, cliente y numerador
				//System.out.println(this.getnumerotarjeta());
				if(null!= this.getnumerotarjeta() && !this.getnumerotarjeta().isEmpty()){/*BIN (6 d�gitos) + C�digo cliente (7 d�gitos) + Numerador (3 d�gitos)*/
					//System.out.println(this.getnumerotarjeta());
					//Seteo bin
					this.setbin(this.getnumerotarjeta().substring(0, 6));// 6 primeros
					//Seteo codigo cliente No se setea porque este campo pude ser variable
					//this.setcodigoCliente(this.getnumerotarjeta().substring(6, (this.getnumerotarjeta().length()-3)).toString()); //7 siguientes
					//Seteo numerador
					this.setnumeracion(this.getnumerotarjeta().substring(this.getnumerotarjeta().length()-3).toString()); //3 ultimos

					//this.setnumeracion(this.getnumerotarjeta().substring(13).toString()); //3 ultimos
				}		
				//OPenUP SBT 13/07/2016 Issue # Se obtienen campos cfe 
				if(JSONObject.NULL!=m_pay.get("codigoCliente")){
					this.setcodigoCliente(m_pay.get("codigoCliente").toString());
//					if(!this.getcodigoCliente().isEmpty())
//						if(!this.getcodigoCliente().trim().equalsIgnoreCase(m_pay.get("codigoCliente").toString().trim()))
//								throw new AdempiereException("El codigo de cliente del nro de tarjeta no coincide con el codigoCliente reicbido");
				}
				//SBT 22/09/2016 Issue #7127 Tipo de credito
				if(JSONObject.NULL!= m_pay.get("codigoCredito")){
					this.setcodigoCredito(m_pay.getInt("codigoCredito"));
				}
				
				this.saveEx();
				return true; 
			}catch(Exception e){
				System.out.println("Error al crear pago:"+m_pay.toString());
				System.out.println(e.getMessage());
			}
			
		}	
		return false;
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.model.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Se debe crear movimiento si el movimiento es una venta con pago a credito de la casa
		if(this.getcodigotipopago() == MRTPaymentMovement.CREDITO){
			MRTMovement mov = (MRTMovement) this.getUY_RT_Movement();
			if(null!=mov && mov.get_ID()>0 && mov.gettipooperacion().equalsIgnoreCase(VENTA)){
				//if(this.getbin().equalsIgnoreCase(COD_CRED_CASA)){//"772832 0003152 007"
				if(this.getcodigoCredito() == COD_CRED_CASA){ //Codigo 90 hace referencia a credito de la casa
					
					MInvoice inv = null;		
					//SBT 10-11-2016 Issue #7820
					//Se consulta si ya se creo previamente factura con este numero de ticke, cliente, organizacion,etc.
					int idInvoice = this.getE_Factura(mov,"");
					
					if(idInvoice>0){
						////SBT 10-11-2016 Issue #7820 Se instancia la factura y se actualizan datos
						inv = updateE_Facutra(mov,idInvoice);
					
					}else{
						//Se crea la factura
						inv = createE_Factura(mov);
						inv.createPaySchedule();//SBT 21/09/2016 Issue #
						inv.setDocStatus("CO");
						inv.setDocAction("--");
						inv.setPosted(true);
						inv.setProcessed(true);
						//SBT 10/11/2012 Issue #7820 Se agrega campo para asociar factura credito de la casa con movimiento  
						inv.set_ValueOfColumn("UY_RT_Movement_ID",this.getUY_RT_Movement_ID());
					}
					if(null!=inv){
						inv.saveEx();				
					}
					//No Se crean las lineas de la factura ya que puede que el pago sea parcial y no total
					
				}
			}
			
		}
		return super.afterSave(newRecord, success);
	}

	/**
	 * OpenUp Ltda Issue #7820
	 * @author SBT 10 nov. 2016
	 * @param mov
	 * @param idInvoice
	 * @return
	 */
	private MInvoice updateE_Facutra(MRTMovement mov, int idInvoice) {
		
		//Instancio la factura
		MInvoice inv = new MInvoice(getCtx(),idInvoice,get_TrxName());
		if(inv!=null && inv.get_ID()>0){
			
			if(inv.getDescription().contains("- NroPago: "+this.getnumerodocumentopago()+ ", Importe:"+this.getImporte())){
				System.out.println("Se recibe pago duplicado para el movimiento:"
						+mov.gettipocfe()+"-"+mov.getseriecfe()+"-"+mov.getnumerooperacionfiscal());
				MRTLoadTicket carga = (MRTLoadTicket) mov.getUY_RT_LoadTicket();
				//Logueo error !!
				carga.logError(mov,this.getnumerodocumentopago());
				return null;
			}
			//Agrego detalles de este pago a la factura
			String desc = inv.getDescription()+" - NroPago: "+this.getnumerodocumentopago()+ ", Importe:"+this.getImporte();
			if(desc.length()>255){
				desc = desc.substring(0, 253);
			}
			inv.setDescription(desc);
			
			BigDecimal importe = Env.ZERO;			
			if(this.getImporte().signum()<0){//
				importe = this.getImporte().negate();
			}else{
				importe = this.getImporte();
			}
			//Sumo importes
			inv.setTotalLines(inv.getTotalLines().add(importe));
			inv.setGrandTotal(inv.getGrandTotal().add(importe));
			
			inv.saveEx();
			return inv;
		}else return null;
		
	}

	/**
	 * OpenUp Ltda Issue #7820
	 * @author SBT 10 nov. 2016
	 * @param mov
	 * @return
	 */
	private int getE_Factura(MRTMovement mov,String nroDocPago) {
		String whereNroDoc = "";
		if(!nroDocPago.isEmpty()){
			 whereNroDoc = " AND Description like '%NroPago: "+nroDocPago+"%'";
		}
		//Se obtiene id del cliente al que corresponde el pago
		int idCliente = DB.getSQLValueEx(null, "SELECT C_BPartner_ID FROM C_BPartner WHERE value = '"
						+this.getcodigoCliente()+"'");
		int idDocType = 0;
		if(mov.gettipocfe().equalsIgnoreCase("101")){//e-Ticket
			idDocType = DB.getSQLValueEx(null, "SELECT C_DocType_ID FROM C_DocType WHERE value = 'custinvoicectocasa'"
					+ " AND AD_Client_ID = "+mov.getAD_Client_ID());
			
		}else if(mov.gettipocfe().equalsIgnoreCase("102")){//e-Ticket Nta Credito
			idDocType = DB.getSQLValueEx(null, "SELECT C_DocType_ID FROM C_DocType WHERE value = 'custncctocasa'"
					+ " AND AD_Client_ID = "+mov.getAD_Client_ID());
			
		}else if(mov.gettipocfe().equalsIgnoreCase("103")){//e-Ticket Nta Debito
			idDocType = DB.getSQLValueEx(null, "SELECT C_DocType_ID FROM C_DocType WHERE value = 'custndctocasa'"
					+ " AND AD_Client_ID = "+mov.getAD_Client_ID());	
		}
		//Se obtiene factura si se creo previamente para el mismo ticket y el mismo cliente 
		int idInvoice = DB.getSQLValueEx(get_TrxName(), "SELECT C_Invoice_ID FROM C_Invoice"
				+ " WHERE DocumentNo = '"+mov.gettipocfe()+"-"+mov.getseriecfe()+"-"+mov.getnumerooperacionfiscal()+"'"
				+ " AND C_BPartner_ID = "+ idCliente+" AND IsActive = 'Y' "
				+ " AND AD_Org_ID = "+mov.getAD_Org_ID_To() +" AND C_DocType_ID = "+idDocType
				+ whereNroDoc );
		
		return idInvoice;
	}

	/**Metodo que crea una factura (corresponde cuando el cliente hizo compra con cred de la casa)
	 * OpenUp Ltda Issue #6253
	 * @author Sylvie Bouissa 6/7/2016
	 */
	private MInvoice createE_Factura(MRTMovement mov) {
		/*El movimiento se identifica por: 	Empresa - Local - Caja - N�mero de Operaci�n */
		MInvoice inv = new MInvoice(getCtx(),0,get_TrxName());
		inv.setAD_Client_ID(mov.getAD_Client_ID());		
		inv.setAD_Org_ID(mov.getAD_Org_ID_To());
		
		int idDocType = 0;	
		MOrg org = new MOrg(getCtx(),mov.getAD_Org_ID_To(),null);
		
		if(mov.gettipocfe().equalsIgnoreCase("101")){//e-Ticket
			idDocType = DB.getSQLValueEx(null, "SELECT C_DocType_ID FROM C_DocType WHERE value = 'custinvoicectocasa'"
					+ " AND AD_Client_ID = "+mov.getAD_Client_ID());
			inv.setC_DocType_ID(idDocType);
			inv.setDocumentNo(mov.gettipocfe()+"-"+mov.getseriecfe()+"-"+mov.getnumerooperacionfiscal());
			inv.setDateInvoiced(mov.getFechaOperacion());
			inv.setPOReference("E-Ticket Autom.");
			
		}else if (mov.gettipocfe().equalsIgnoreCase("102")){//e-Ticket Nta Credito
			
			idDocType = DB.getSQLValueEx(null, "SELECT C_DocType_ID FROM C_DocType WHERE value = 'custncctocasa'"
					+ " AND AD_Client_ID = "+mov.getAD_Client_ID());
			inv.setC_DocType_ID(idDocType);
			inv.setDocumentNo(mov.gettipocfe()+"-"+mov.getseriecfe()+"-"+mov.getnumerooperacionfiscal());			
			inv.setDateInvoiced(mov.getFechaOperacion());
			inv.setPOReference("E-Ticket NC Autom.");
	
		}else if (mov.gettipocfe().equalsIgnoreCase("103")){//e-Ticket Nta Debito
			idDocType = DB.getSQLValueEx(null, "SELECT C_DocType_ID FROM C_DocType WHERE value = 'custndctocasa'"
					+ " AND AD_Client_ID = "+mov.getAD_Client_ID());	
			inv.setC_DocType_ID(idDocType);
			inv.setDocumentNo(mov.gettipocfe()+"-"+mov.getseriecfe()+"-"+mov.getnumerooperacionfiscal());			
			inv.setDateInvoiced(mov.getFechaOperacion());
			inv.setPOReference("E-Ticket ND Autom.");
		}
		inv.setDescription("Local: "+org.getName()+", caja: "+mov.getcodigocaja()
		+ " - NroPago: "+this.getnumerodocumentopago()
		+ " Importe:"+this.getImporte());
		
		inv.setC_DocTypeTarget_ID(inv.getC_DocType_ID());
		inv.setIsSOTrx(true);

		int mClientID = DB.getSQLValueEx(null, "SELECT C_BPartner_ID FROM C_BPartner WHERE value = '"+this.getcodigoCliente()+"'");
		
		MBPartner cli = MBPartner.forValue(getCtx(), this.getcodigoCliente(),get_TrxName());
		if(null == cli || cli.get_ID()==0){
			cli = new MBPartner(getCtx(),mClientID,null);
			if(null == cli || cli.get_ID()==0)
				System.out.println("CLIENTE NO ENCONTRADO");
		}
		inv.setC_BPartner_ID(cli.get_ID());
		inv.setC_BPartner_Location_ID(cli.firstBPLocationID());

		inv.setC_Currency_ID(this.getC_Currency_ID());
		MPriceList mpl = MPriceList.getPricListForOrg(getCtx(), mov.getAD_Org_ID_To(), mov.getC_Currency_ID(), get_TrxName());
		if(null!=mpl && mpl.get_ID()>0){
			inv.setM_PriceList_ID(mpl.get_ID());
		}else{
			System.out.println("LISTA DE PRECIO NO ENCONTRADA");
		}
		
		inv.setpaymentruletype("CR");//Credito
		
		MPaymentTerm payTerm = MPaymentTerm.forValue(getCtx(), "credito", get_TrxName());
		int payTermID = 0;
		if(null!=payTerm && payTerm.get_ID()>0){
			payTermID = payTerm.get_ID();
		}else{
			payTermID = DB.getSQLValueEx(null, "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE "
					+ " AD_Client_ID = "+mov.getAD_Client_ID()+" AND value = 'credito'");
		}
		inv.setC_PaymentTerm_ID(payTermID);
		
		if(this.getImporte().signum()<0){//
			inv.setTotalLines(this.getImporte().negate());
			inv.setGrandTotal(this.getImporte().negate());
		}else{
			inv.setTotalLines(this.getImporte());
			inv.setGrandTotal(this.getImporte());
		}
		//Verificar si hay que indicar estado
		inv.saveEx();
		return inv;
	}

	/**Crea lineas a partir de los detalles del movimiento
	 * OpenUp Ltda Issue #6253
	 * @author Sylvie Bouissa 6/7/2016
	 * @param inv
	 */
	private void crearE_Lineas(MInvoice inv,MRTMovement mov) {
		List<MRTMovementDetail> detalles = MRTMovementDetail.getDetailListForMov(getCtx(),mov.get_ID(),get_TrxName());
		if(null!=detalles){
			for(MRTMovementDetail detail : detalles){
				MInvoiceLine line = new MInvoiceLine(getCtx(),0,get_TrxName());
				line.setAD_Client_ID(inv.getAD_Client_ID());
				line.setAD_Org_ID(inv.getAD_Org_ID());
				MProduct prod = MProduct.forValue(getCtx(), detail.getcodigoarticulo(), get_TrxName());
				if(null==prod || prod.get_ID()==0){
					MProductUpc upc = MProductUpc.forUPC(getCtx(), detail.getcodigobarras(), get_TrxName());
					if(null!=upc && 0<upc.get_ID()){
						prod = (MProduct)upc.getM_Product();
					}
				}
				line.setM_Product_ID(prod.get_ID());
				line.setQtyEntered(detail.getCantidad());
				line.setC_UOM_ID(prod.getC_UOM_ID());
				
				line.setPriceEntered(detail.getimporteunitario());
				
				MTax impuesto = null;
				if(detail.getPorcentajeIVA()==22){
					impuesto = MTax.forValue(getCtx(), "basico", null);
				}else if(detail.getPorcentajeIVA()==10){
					impuesto = MTax.forValue(getCtx(), "minimo", null);
				}else if(detail.getPorcentajeIVA()==0){
					impuesto = MTax.forValue(getCtx(), "exento", null);
				}
				
				line.setC_Tax_ID(impuesto.get_ID());
				line.setTaxAmt(detail.getMontoIVA());
				line.setLineNetAmt(detail.getImporte().subtract(detail.getMontoIVA()));
				line.setLineTotalAmt(detail.getImporte());
				
				line.saveEx();
				
			}
		}
	}
}

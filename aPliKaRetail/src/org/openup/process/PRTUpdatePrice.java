/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MRTInterfaceProd;
import org.openup.retail.MRTScanntech;

/**OpenUp Ltda Issue#
 * @author SBT 27/5/2016
 *
 */
public class PRTUpdatePrice extends SvrProcess {

	private static final String PRECIOARTICULO = "PrecioArticulo"; 
	
	
	private int sbouissa =  1003360;
	private int amartinez = 1005982;
	private int calonso = 1005981;
	private String objeto = "";//rubro,familia,subfamilia,articulo
	private int cantidad =0;
	private boolean delete = false;
	
	private int sucursal = 0;
	/**
	 * 
	 */
	public PRTUpdatePrice() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		if (!(this.getAD_User_ID() == sbouissa))
			if(!(this.getAD_User_ID() == amartinez)){
				if(!(this.getAD_User_ID() == calonso))
					throw new AdempiereException("No tiene privilegios para realizar dicha tarea"); 
			}
//		if (!(Env.getAD_Org_ID(getCtx())==0))
//			throw new AdempiereException("Debe ingresar con oganizaci�n *");
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("Name")){
					this.objeto = ((String)para[i].getParameter());
				}
			}
//			if (name!= null){
//				if (name.equalsIgnoreCase("Cantidad")){
//					this.cantidad = ((BigDecimal) para[i].getParameter()).intValue();
//					
//				}
//			}
			if (name!=null){
				if (name.equalsIgnoreCase("AD_OrgAux_ID")){
					this.sucursal = ((BigDecimal) para[i].getParameter()).intValue();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		int good = 0; int error = 0; int prodAux = 0;
		String scales = "";
		//Obtengo los datos sin leer si la sucursal es cero se traen todos los precios a actualizar si la sucursal tiene 
		//valor se retornan solo los datos a actualizar para dicha sucursal
		List<MRTInterfaceProd> datos = MRTInterfaceProd.forPriceManualNotRead(getCtx(),this.sucursal,get_TrxName());

			if(null != datos){
				for (MRTInterfaceProd prodPric : datos){
					MRTScanntech sis = new MRTScanntech(getCtx(),get_TrxName());
					String retorno = sis.updateArticuloPrecio(getCtx(), prodPric.getAD_Client_ID()
							, prodPric.getM_Product_ID(),prodPric.getC_Currency_ID()
							, prodPric.getPriceList(),prodPric.get_ValueAsInt("AD_OrgAux_ID")
							, get_TrxName());
					
					prodPric.set_ValueOfColumn("ResultadoEnvioWS", retorno);
					
					if("OK".equalsIgnoreCase(retorno)){
						prodPric.setReadingDate(new Timestamp (System.currentTimeMillis()));
						prodPric.set_ValueOfColumn("resultadoenviows", retorno);
						prodPric.saveEx();
						if(prodAux == 0 || prodAux!=prodPric.getM_Product_ID() ){
							prodAux = prodPric.getM_Product_ID();
							good++;		
						}
					}else{
						prodPric.set_ValueOfColumn("resultadoenviows", retorno);
						prodPric.saveEx();
						if(prodAux == 0 || prodAux!=prodPric.getM_Product_ID() ){
							prodAux = prodPric.getM_Product_ID();
							error++;
						}
					}
				}
			}
			
			scales = sendPriceToScales();
			if(scales.startsWith("OK")){
				scales = "\n" + scales.replace("OK-", "")+", en el sistema de balanza";
			}
		//}
		if(error==0){
			return " - Se actualizaron satisfactoriamente "+good+" productos en el sistema de cajas !! - \n"+ scales;
		}else{
			return " - Se actualizaron satisfactoriamente "+good+" productos en el sistema de cajas \n"
			+"y "+error+" con error !! - \n"+scales;
			//(ver detalles de errores en log correspondiente)";
		}
	}

	/**
	 * OpenUp Ltda Issue #6337
	 * @author Sylvie Bouissa 6/7/2016
	 */
	private String sendPriceToScales() {
		try{
			PRTInterfaceScales scale = new PRTInterfaceScales();
			scale.setContexto(getCtx());
			scale.setTransaccion(get_TrxName());
			scale.setWinOrLinux();
			scale.setmAD_ClientId(this.getAD_Client_ID());
			
			return "OK-"+scale.doIt();
		}catch(Exception e){
			return e.getMessage();
		}
	}

}

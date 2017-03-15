/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.openup.model.MFamilia;
import org.openup.model.MProductGroup;
import org.openup.model.MSubFamilia;
import org.openup.retail.MRTRetailInterface;


/**
 * {@link Description} Proceso que da de alta masivamente objetos a trav�s de WS,
 *  los objetos que se encuentran creados en adempiere
 *  Actualmente se encuentran disponible la creaci�n de: Rubro - Familia - Subfamilia - Articulo
 * OpenUp Ltda Issue #5987
 * @author OpenUp 13/5/2016
 *
 */
public class PRTRegisterData extends SvrProcess {
	
	private static final String RUBRO = "rubro";
	private static final String FAMILIA = "familia";
	private static final String SUBFAMILIA = "subfamilia";
	private static final String ARTICULO = "articulo";
    private static final String CLIENTE = "cliente";
	/**
	 * Constructor
	 */
	public PRTRegisterData() {
		// TODO Auto-generated constructor stub
	}
	private int sbouissa =  1003360;
	private int amartinez = 1005982;
	private int calonso = 1005981; //SBT Issue#7001 15/09/2016
	private String objeto = "";//rubro,familia,subfamilia,articulo
	private int cantidad =0;
	private boolean delete = false;

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
		if (!(Env.getAD_Org_ID(getCtx())==0))
			throw new AdempiereException("Debe ingresar con oganizaci�n *");
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("Name")){
					this.objeto = ((String)para[i].getParameter());
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("Cantidad")){
					this.cantidad = ((BigDecimal) para[i].getParameter()).intValue();
					
				}
			}
			if (name!=null){
				if (name.equalsIgnoreCase("IsManual")){
					if("N".equalsIgnoreCase(para[i].getParameter().toString())){
						this.delete = false;
					}else{
						this.delete = true;
					}
				}
			}
		}

	}
	private	List<MProduct> products = null;
	private	List<MProductGroup> rubros = null;
	private	List<MFamilia> familias = null;
	private List<MSubFamilia> subFamilias = null;
	//SBT 05/07/2016 Issue #6253 Se agrega opci�n de crear masivamente clientes
	private List<MBPartner> clientes = null;
	
	@Override
	protected String doIt() throws Exception {
		int cant = 0; 
		if(objeto.equalsIgnoreCase(RUBRO)){
			cant = sendRubros();
		}else if(objeto.equalsIgnoreCase(FAMILIA)){
			cant = sendFamilias();
		}else if(objeto.equalsIgnoreCase(SUBFAMILIA)){
			cant = sendSubFamilias();
		}else if(objeto.equalsIgnoreCase(ARTICULO)){
			cant = sendArticulos();
		}else if(objeto.equalsIgnoreCase(CLIENTE)){//Se agrega 05/07/2016
			cant = sendClientes();
		}
		System.out.println("Se crearon "+cant+", "+objeto+"s");
		return "Se crearon "+cant+", "+objeto+"s";
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 17/5/2016
	 */
	private int sendRubros() {
		rubros = MProductGroup.getAllProductGroup(getCtx(),this.getAD_Client_ID(),0,this.get_TrxName());
		if(rubros.size()>0){
			for(MProductGroup pg:rubros){
				if(!delete){
					MRTRetailInterface.createCategory(getCtx(),this.getAD_Client_ID(),0,pg.get_ID()
							,this.get_TrxName());
				}else{
					MRTRetailInterface.deleteCategory(getCtx(),this.getAD_Client_ID(),0,pg.get_ID(),
							pg.get_ValueAsString("POSCode"),get_TrxName());
				}
			}
			return rubros.size();
		}
		return 0;
	}
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 17/5/2016
	 * @return
	 */
	private int sendFamilias() {
		//delete = true;
		familias = MFamilia.getAllFamilia(getCtx(),this.getAD_Client_ID(), 0, this.get_TrxName());
		if(familias.size()>0){
			for(MFamilia f:familias){
				if(!delete){
					MRTRetailInterface.createFamily(getCtx(),this.getAD_Client_ID(),0,f.get_ID(),this.get_TrxName());
				}else{
					MRTRetailInterface.deleteFamilys(getCtx(),this.getAD_Client_ID(),0,f.get_ID(),
							f.get_ValueAsString("POSCode"),this.get_TrxName());
				}
			}
			return familias.size();
		}
		return 0;
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 17/5/2016
	 * @return
	 */
	private int sendSubFamilias() {
		//delete = true;
		subFamilias = MSubFamilia.getAllSubFamilia(getCtx(),this.getAD_Client_ID(), 0, this.get_TrxName());
		if(subFamilias.size()>0){
			for(MSubFamilia sf:subFamilias){	
				if(!delete){
					MRTRetailInterface.createSubFamily(getCtx(),this.getAD_Client_ID(),0,sf.get_ID(),this.get_TrxName());
				}else{
					MRTRetailInterface.deleteSubFamilys(getCtx(), this.getAD_Client_ID(), 0,sf.get_ID(),
							sf.get_ValueAsString("POSCode"),this.get_TrxName());
				}
			}
			return subFamilias.size();
		}
		return 0;
	}
	/**
	 * OpenUp Ltda Issue #
	 * @author Sylvie Bouissa 17/5/2016
	 * @return
	 */
	private int sendArticulos() {
		products = MProduct.getAllproducts(getCtx(),this.getAD_Client_ID(), 0, this.get_TrxName());
		if(products.size()>0){
			int count = 0;
			if(cantidad>0){
				for(int i=0 ; i<cantidad;i++){
					MProduct prod = products.get(i);
					if(MRTRetailInterface.crearArticulo(getCtx(), this.getAD_Client_ID(), prod.get_ID(), this.get_TrxName())){
						System.out.println(count ++);
					}else 
						System.out.println("No se creo el producto "+prod.getName()+", con ID:"+prod.get_ID());
				}
				return count;
			}else{
				for(MProduct prod:products){
					System.out.println(prod.get_ID());
					if(MRTRetailInterface.crearArticulo(getCtx(), this.getAD_Client_ID(), prod.get_ID(), this.get_TrxName())){
						count++;
					}else 
						System.out.println("No se creo el producto "+prod.getName()+", con ID:"+prod.get_ID());
						
				}
			}
			
			return count;
		}
		return 0;
	}
	
	/**
	 * OpenUp Ltda Issue #6253
	 * @author Sylvie Bouissa 5/7/2016
	 * @return
	 */
	private int sendClientes() {
		clientes = MBPartner.getAllIDsClients(getCtx(),this.getAD_Client_ID(), 0, this.get_TrxName());
		if(clientes.size()>0){
			int count = 0;
			if(cantidad>0){
				for(int i=0 ; i<cantidad;i++){
					MBPartner bp = clientes.get(i);
					MBPartnerLocation bpl = new MBPartnerLocation(getCtx(),bp.firstBPLocationID(),get_TrxName());
					if(null!= bpl && 0<bpl.get_ID()){//Si tiene localidad se envia
						if(MRTRetailInterface.crearCliente(getCtx(), this.getAD_Client_ID(), bp.get_ID(),bpl.get_ID(), this.get_TrxName())){
							//SBT 31/08/2016 Issue #6861
							if(bp.get_ValueAsBoolean("IsCreditApproved") && Env.ZERO.compareTo(bp.getSO_CreditLimit())<0){
								MRTRetailInterface.modificarCreditoCliente(getCtx(), this.getAD_Client_ID(), bp.get_ID(),
										bp.getValue(), bp.getSO_CreditLimit(),this.get_TrxName());
							}						
							count ++; //Properties ctx,int mClientID, int mCBPartnerID,int mCBPartnerLocationID,String trxName
						}else 
							System.out.println("No se creo el cliente "+bp.getName()+", con ID:"+bp.get_ID());
					}
					
				}
				return count;
			}else{
				for(MBPartner bp : clientes){
					MBPartnerLocation bpl = new MBPartnerLocation(getCtx(),bp.firstBPLocationID(),get_TrxName());
					if(null!= bpl && 0<bpl.get_ID()){//Si tiene localidad se envia
						if(MRTRetailInterface.crearCliente(getCtx(), this.getAD_Client_ID(), bp.get_ID(),bpl.get_ID(), this.get_TrxName())){
							//SBT 31/08/2016 Issue #6861
							if(bp.get_ValueAsBoolean("IsCreditApproved") && Env.ZERO.compareTo(bp.getSO_CreditLimit())<0){
								MRTRetailInterface.modificarCreditoCliente(getCtx(), this.getAD_Client_ID(), bp.get_ID(),
										bp.getValue(), bp.getSO_CreditLimit(),this.get_TrxName());
							}
							count ++; 
						}else 
							System.out.println("No se creo el cliente "+bp.getName()+", con ID:"+bp.get_ID());
					}
						
				}
			}
			
			return count;
		}
		return 0;
	}
	
	

}

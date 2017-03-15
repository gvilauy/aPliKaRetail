/**
 * 
 */
package org.openup.cargamasiva;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MProductUpc;
import org.openup.model.MRTAction;
import org.openup.model.MRTInterfaceProd;
import org.openup.retail.MRTRetailInterface;

/**
 * @author Nicolas
 *
 */
public class PRTLoadUPC extends SvrProcess{

	/**
	 * 
	 */
	public PRTLoadUPC() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		throw new AdempiereException("No tiene permisos para correr el proceso");
	}

	@Override
	protected String doIt() throws Exception {
		int cantidad = 0;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt2 = null;			
		try{
			
			//String sql = "select m_product_id,upc from uy_loadupc";
//			String sql = "SELECT m_product_id ,M_ProductAttribute_ID FROM M_ProductAttribute where "
//					+ "isselected = 'N' AND IsActive = 'Y' AND UY_ProdAttribute_ID IN "
//					+ "(SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN "
//					+ "('attr_1')) ";
//			String sql = " SELECT a.m_product_id,a.M_ProductAttribute_id,b.isactive as prodact FROM M_ProductAttribute a JOIN M_Product b ON (a.M_Product_ID = b.M_Product_ID)"
//					+ "	and b.issold = 'Y' and a.isactive='Y' and a.isselected = 'N' and a.UY_ProdAttribute_ID = 1000000 order by prodact";
			String sql = "Select m_product_id from m_product where isActive = 'Y' and "
					+ "issold = 'Y' and m_product_id >=  1046087";
			
			
			
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();			
			
		    while(rs.next()){
		    	
		    	//MProductUpc prodUpc = null;
		    	MProduct prod = null;
		    	int prodID = rs.getInt("m_product_id");
		    	//int mProdAtt_ID = rs.getInt("M_ProductAttribute_ID");
		    	//String activo = rs.getString("prodact");
		    	//String upc = rs.getString("upc");
		    	//prod = new MProduct(getCtx(),prodID,get_TrxName());
		    	if(prodID>0){
		    		
		    		MProduct p = new MProduct(getCtx(), prodID, get_TrxName());
		    		p.updateVerssionNo(true);
		    		p.setName(p.getName()+".");
		    		p.saveEx();
		    				
//		    		try{
//		    			String updt = "UPDATE M_ProductAttribute SET IsSelected = 'Y' "+
//			    				" WHERE M_Product_ID = "+prodID+
//			    				" AND IsActive='Y' AND  M_ProductAttribute_ID = "+mProdAtt_ID;
//		    			System.out.println(updt);
//			    		//pstmt2 = DB.prepareStatement (updt, get_TrxName());
//					    int cant = DB.executeUpdate(updt,get_TrxName());	
//					    
//					    cantidad = cantidad +cant;
//					    
//		    		}catch(Exception e){
//		    			System.out.println(e.getMessage());
//		    		} finally {
//						DB.close(rs2, pstmt2);
//						rs2 = null; pstmt2 = null;
//					}
//		    			
//		    		if(activo.equalsIgnoreCase("Y")){
//		    			MRTRetailInterface.actualizarProducto(getCtx(), this.getAD_Client_ID(),prodID,get_TrxName());
//		    			
////		    			MRTInterfaceProd it  = new MRTInterfaceProd(getCtx(), 0, get_TrxName());
////		    				it.setM_Product_ID(prodID);
////		    				it.insertInterface(false);
//		    
//		    			
//		    		}
				   
		    	}

		    	//prodUpc = MProductUpc.forUPCProduct(getCtx(), upc, prodID, get_TrxName());
		    	
//		    	if(prodUpc==null){
//		    		
//		    		prodUpc = new MProductUpc(getCtx(),0,get_TrxName());
//		    		prodUpc.setM_Product_ID(prodID);
//		    		prodUpc.setUPC(upc);
//		    		prodUpc.saveEx();		    		
//		    		
//		    	}  	
		    	
		    }			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}				
		
		return "ok";
	}

}

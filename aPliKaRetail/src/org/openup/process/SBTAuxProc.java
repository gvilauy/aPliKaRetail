/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MRTAction;
import org.openup.model.MRTInterfaceBP;
import org.openup.retail.MRTRetailInterface;
import org.openup.util.OpenUpUtils;

/**OpenUp Ltda Issue#
 * @author SBT 26/11/2015
 *
 */
public class SBTAuxProc extends SvrProcess{

	/**
	 * 
	 */
	public SBTAuxProc() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		getAD_User_ID();
		MUser u = MUser.forName(getCtx(), "sbouissa", null) ;
		if(1003360!=getAD_User_ID()){
			throw new AdempiereException("No tiene permisos para correr este proceso");
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		MPriceList mp = new MPriceList(getCtx(),1000038,get_TrxName());
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		MPriceListVersion lv = mp.getVersionVigente(today);
		int cant =0;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			
//			sql = "select m_product_id from ( "+
//"select m_product_id, count(pricelist) from ( "+
//"Select distinct m_product_id, pricelist from M_ProductPrice "
//+ "where m_pricelist_version_id in "+
//"(Select distinct(M_PriceList_Version_ID) from M_PriceList pl join M_PriceListOrg plO "+
//"on pl.ad_org_id = plO.ad_orgaux_id "+
//"join M_PriceList_Version plV "+
//"on plV.M_PriceList_ID = pl.M_PriceList_ID "+
//"where plO.M_PriceList_ID =  1000223 "+
//"and pl.issoPricelist = 'Y' "+
//"and plV.isactive = 'Y')) a "+
//"group by m_product_id "+
//"having count(pricelist) > 1) b ";
				
			sql = " select m_product_id from m_product where isactive='Y' and issold='Y' and  m_product_id not in "
		   		+ "(select a.m_product_id from uy_productupc a join m_product b on (a.m_product_id=b.m_product_id) "
			 +"group by a.m_product_id "
			 +"having count(a.m_product_id)>1) ";
//		   sql = "select a.m_product_id from uy_productupc a join m_product b on (a.m_product_id=b.m_product_id) "
//			 +"group by a.m_product_id "
//			 +"having count(a.m_product_id)>1 ";
		   
				pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
				
				rs = pstmt.executeQuery();

				rs.last();
				System.out.println("LOG-Inicio carga upcs, cantidad= "+rs.getRow());
				rs.beforeFirst();

				while (rs.next()) {
//1					MProduct prod = new MProduct(this.getCtx(),rs.getInt("M_Product_ID"),this.get_TrxName());
//	1				BigDecimal pr = Env.ZERO; 
//	1				pr = prod.getSalePrice(142, 1000015);
//					if(pr.compareTo(Env.ZERO)>0){
//	1					MRTRetailInterface.actualizaPrecioArticulo(getCtx(), this.getAD_Client_ID(),
//								rs.getInt("M_Product_ID"), 142, pr, 1000015, this.get_TrxName());
//	1				}
					
					String sql2 = "";
					ResultSet rs2 = null;
					PreparedStatement pstmt2 = null;
					
					sql2= "select upc from uy_productupc where m_product_id = "+rs.getInt("M_Product_ID");
					pstmt2 = DB.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					
					rs2 = pstmt2.executeQuery();

					rs2.last();
					System.out.println("LOG-Inicio carga upcs, cantidad= "+rs2.getRow());
					rs2.beforeFirst();
					while (rs2.next()) {
						MRTRetailInterface.crearUPC(getCtx(), this.getAD_Client_ID(),
								rs2.getString("UPC"), rs.getInt("M_Product_ID"), this.get_TrxName());
					}
					pstmt2.close();

					cant++;
				}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}
		

		return "Cantidad:"+cant;
	}

}

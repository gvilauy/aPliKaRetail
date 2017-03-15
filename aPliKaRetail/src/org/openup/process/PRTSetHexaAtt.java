/**
 * 
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTTReceiptBox;

/**OpenUp Ltda Issue#
 * @author SBT 6/10/2015
 *
 */
public class PRTSetHexaAtt extends SvrProcess {

	/**
	 * 
	 */
	public PRTSetHexaAtt() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		int bit1_1000000 = 0;	int bit2_1000001 = 0;	int bit11_1000008 = 0;	int bit13_1000010 = 0;	int bit14_1000011 = 0;	
		int bit19_1000015 = 0;	int bit24_1000019=0; int bit32_1000025 = 0; int bit9_1000007 = 0;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			sql = " SELECT prod.m_product_id as id, at.uy_prodAttribute_id as attrId "
				+ " FROM M_Product prod "
				+ " inner join M_ProductAttribute at on prod.m_product_id = at.m_product_id "
				+ " WHERE prod.isactive = 'Y' "
				+ " and prod.issold='Y' "
				+ " and at.isSelected = 'Y' "
				+ " and at.IsActive ='Y' " 
				+ " order by prod.m_product_id";
			
			//pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			int idProdAux = 0; int count = 1;
			boolean firstRow = true;
			while (rs.next()) {				
				count++;
				System.out.println("Procesando linea " + rowCount++ + " de " + totalRowCount + " - " + rs.getInt("id"));
			if(rs.getInt("id")==1007663){
				System.out.println("1007663");
			}
				if (rs.getInt("id") != idProdAux){ //si estoy en un registro diferente controlo si no es el primero!!!!!
					//controlo	
					if (!firstRow){
						if(bit2_1000001 == 1 && bit13_1000010 ==  1 && bit14_1000011 == 1 && bit32_1000025 == 1 && bit1_1000000 == 0 && bit19_1000015 == 0 
								&& bit11_1000008 == 0 && bit24_1000019==0 && bit9_1000007 == 0){
							DB.executeUpdate("UPDATE M_Product SET versionno = '400C000100000000' WHERE M_Product_ID = "+idProdAux,null);
						}else if (bit1_1000000 == 1 && bit2_1000001 == 1 && bit32_1000025 == 1 &&bit11_1000008 == 0 
								&& bit13_1000010 == 0 && bit14_1000011 == 0  && bit19_1000015 == 0 && bit24_1000019==0 && bit9_1000007 == 0){
							DB.executeUpdate("UPDATE M_Product SET versionno = 'C000000100000000' WHERE M_Product_ID ="+idProdAux,null);	
						}else if (bit11_1000008 == 1 && bit24_1000019==1 && bit32_1000025 == 1 && bit1_1000000 == 0 
								&& bit2_1000001 == 0 && bit13_1000010 == 0 && bit14_1000011 == 0 && bit9_1000007 == 0 ){
							DB.executeUpdate("UPDATE M_Product SET versionno = '0020010100000000' WHERE M_Product_ID ="+idProdAux,null);
						}else if(bit11_1000008 == 1 && bit32_1000025 == 1 && bit1_1000000 == 0 && bit2_1000001 == 0 
								&& bit13_1000010 == 0 && bit14_1000011 == 0 && bit19_1000015 == 0 && bit24_1000019==0 && bit9_1000007 == 0){
							DB.executeUpdate("UPDATE M_Product SET versionno = '0020000100000000' WHERE M_Product_ID ="+idProdAux,null);
						}else if(bit9_1000007 == 1 && bit11_1000008 ==1 && bit32_1000025 == 1 &&  bit1_1000000 == 0 
								&& bit2_1000001 == 0 && bit13_1000010 == 0 && bit14_1000011 == 0 && bit24_1000019==0 && bit19_1000015 == 0){
							//00A0000100000000 bit 11, 32 y 9 -- TANDEM 07-10-2015 SBT
							//System.out.println("Update 00A:"+idProdAux);
							DB.executeUpdate("UPDATE M_Product SET versionno = '00A0000100000000' WHERE M_Product_ID ="+idProdAux,null);
						}
						//inicializo las variables que controle
						 bit1_1000000 = 0;	 bit2_1000001 = 0;	 bit11_1000008 = 0;	 bit13_1000010 = 0;	 bit14_1000011 = 0;	
						 bit19_1000015 = 0; bit32_1000025 = 0; bit9_1000007=0;
					}else{
						firstRow = false;
					}			
				
					idProdAux = rs.getInt("id"); 
				}
				//Asigno el valor que corresponde
				if(rs.getInt("attrId") ==  1000000){
						bit1_1000000 = 1;
				}else if(rs.getInt("attrId") ==  1000001){
						bit2_1000001 = 1;
				}else if(rs.getInt("attrId") ==  1000007){
						bit9_1000007 = 1;
				}else if(rs.getInt("attrId") ==  1000008){
						bit11_1000008 = 1;
				}else if(rs.getInt("attrId") ==  1000010){
						bit13_1000010 = 1;
				}else if(rs.getInt("attrId") ==  1000011){
						bit14_1000011 = 1;
				}else if(rs.getInt("attrId") ==  1000015){
						bit19_1000015 = 1;
				}else if(rs.getInt("attrId") ==  1000025){
						bit32_1000025 = 1;
				}

			
			//controlo
			} 	
			if(bit2_1000001 == 1 && bit13_1000010 ==  1 && bit14_1000011 == 1 && bit32_1000025 == 1 && bit1_1000000 == 0 && bit19_1000015 == 0 
					&& bit11_1000008 == 0 && bit24_1000019==0 && bit9_1000007 == 0){
				DB.executeUpdate("UPDATE M_Product SET versionno = '400C000100000000' WHERE M_Product_ID = "+idProdAux,null);
			}else if (bit1_1000000 == 1 && bit2_1000001 == 1 && bit32_1000025 == 1 &&bit11_1000008 == 0 
					&& bit13_1000010 == 0 && bit14_1000011 == 0  && bit19_1000015 == 0 && bit24_1000019==0 && bit9_1000007 == 0){
				DB.executeUpdate("UPDATE M_Product SET versionno = 'C000000100000000' WHERE M_Product_ID ="+idProdAux,null);	
			}else if (bit11_1000008 == 1 && bit24_1000019==1 && bit32_1000025 == 1 && bit1_1000000 == 0 
					&& bit2_1000001 == 0 && bit13_1000010 == 0 && bit14_1000011 == 0 && bit9_1000007 == 0 ){
				DB.executeUpdate("UPDATE M_Product SET versionno = '0020010100000000' WHERE M_Product_ID ="+idProdAux,null);
			}else if(bit11_1000008 == 1 && bit32_1000025 == 1 && bit1_1000000 == 0 && bit2_1000001 == 0 
					&& bit13_1000010 == 0 && bit14_1000011 == 0 && bit19_1000015 == 0 && bit24_1000019==0 && bit9_1000007 == 0){
				DB.executeUpdate("UPDATE M_Product SET versionno = '0020000100000000' WHERE M_Product_ID ="+idProdAux,null);
			}else if(bit9_1000007 == 1 && bit11_1000008 ==1 && bit32_1000025 == 1 &&  bit1_1000000 == 0 
						&& bit2_1000001 == 0 && bit13_1000010 == 0 && bit14_1000011 == 0 && bit24_1000019==0 && bit19_1000015 == 0){
					//00A0000100000000 bit 11, 32 y 9 -- TANDEM 07-10-2015 SBT
					//System.out.println("Update 00A:"+idProdAux);
					DB.executeUpdate("UPDATE M_Product SET versionno = '00A0000100000000' WHERE M_Product_ID ="+idProdAux,null);
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return "OK";
		
		
		
	}

}

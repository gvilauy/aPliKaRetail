/**
 * 
 */
package org.openup.util;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**OpenUp Ltda Issue#
 * @author SBouissa 17/05/2015
 *
 */
public class OpenUpUtilsRT {

	/**Varaibles RT 
	 * 
	 */
	public static final String isHeader = "Y";
	public static final String isNotHeader = "N";
	public static final String autorizaSupervisora = "1";
	
	/**
	 * 
	 */
	public OpenUpUtilsRT() {
		// TODO Auto-generated constructor stub
	}

	
	/**Cerrar Preparestatement se consulta si es null para realizar el cerrado
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 17/05/2015
	 * @param pstm
	 */
//	public static void closePS(PreparedStatement pstm){
//		try{
//			if(pstm!=null){
//				pstm.close();
//			}
//		}catch(Exception e){
//			
//		}
//	}
//	
//	/**Cerrar Objetos se consulta si es null para realizar el cerrado
//	 * OpenUp Ltda Issue#
//	 * @author Sylvie Bouissa 17/05/2015
//	 * @param rs
//	 */
//	public static void closeClosable(Closeable obj){
//		try{
//			if(obj!=null){
//				obj.close();
//			}
//		}catch(Exception e){
//			
//		}
//	}
//
//	/**Cerrar ResultSet se consulta si es null para realizar el cerrado
//	 * OpenUp Ltda Issue#
//	 * @author Sylvie Bouissa 17/05/2015
//	 * @param rs
//	 */
//	public static void closeRS(ResultSet rs) {
//		// TODO Auto-generated method stub
//		try{
//			if(rs!=null){
//				rs.close();
//			}
//		}catch(Exception e){
//			
//		}
//	}
}

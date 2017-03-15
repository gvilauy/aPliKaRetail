/**
 * 
 */
package org.openup.util;

import java.sql.Timestamp;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class Convertir {

	/**
	 * 
	 */
	public Convertir() {
		// TODO Auto-generated constructor stub
	}

	
	/**
     * 
     * OpenUp Ltda Issue#
     * @author Sylvie Bouissa 23/04/2015
     * @param dato
     * @param fch - Fecha en formato "2015-04-23"
     * @param dato - Hora en formato  HHMMss
     * @return
     */
    
	public static Timestamp convertirHHMMss(String dato,String fch){
	    Timestamp ret;
		if(dato.length()==6){
//			String hra = dato.substring(0, 2); //HH-->Incluse pos 0 y 1
//			String min = dato.substring(2,4); //MM
//			String seg = dato.substring(4,6); //ss
			
//			return (Timestamp.valueOf(fch+hra+":"+min+":"+seg));	
			return convertirYYYYMMddHHMMss(fch+dato);
		}
		
		return null;
		//ret.settimestampticket(Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));	    
	}
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 23/04/2015
	 * @param string
	 * @return
	 */
	public static Timestamp convertirYYYYMMddHHMMss(String fecha){
	    Timestamp ret;
		if(fecha.length()==14){			
			String anio = fecha.substring(0, 4);
			String mes = fecha.substring(4,6);
			String dia = fecha.substring(6,8);
			String hra = fecha.substring(8,10);
			String min = fecha.substring(10,12);
			String seg = fecha.substring(12,14);
			
			return (Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));	
		}
		
		return null;
		//ret.settimestampticket(Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));	    
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 24/04/2015
	 * @param string fecha en formato 24042015
	 * @return
	 */
	public static Timestamp convertirddMMYYYY(String fecha) {
		 Timestamp ret;
			if(fecha.length()==8){
				
				String dd = fecha.substring(0, 2); //dia
				String mm = fecha.substring(2,4); //Mes
				String yyyy = fecha.substring(4,8); //Año
				
				return (Timestamp.valueOf(yyyy+"-"+mm+"-"+dd+" 00:00:00"));	
			}
			
			return null;
	}
	
	/***
	 * 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 16/9/2015
	 * @param fecha
	 * @return
	 */
	public static Timestamp converTimestampYYMMdd(String fecha){
		 Timestamp ret;
			if(fecha.length()==8){
				String yyyy = fecha.substring(0,4); //Año 1234 56 78
				String mm = fecha.substring(4,6); //Mes
				String dd = fecha.substring(6,8); //dia
				
//				String dd = fecha.substring(0, 2); //dia
//				String mm = fecha.substring(2,4); //Mes
//				String yyyy = fecha.substring(4,8); //Año
				
				return (Timestamp.valueOf(yyyy+"-"+mm+"-"+dd+" 00:00:00"));	
			}
			
			return null;
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 27/04/2015
	 * @param string ingresa en formato 27/04/2015
	 * @return
	 */
	public static Timestamp convertirddMMYYYY_YYYYMMdd(String fecha) {
		 Timestamp ret;
			if(fecha.length()==10){
				String[] fch = fecha.split("/");
				
				String dd = fch[0]; //dia
				String mm = fch[1]; //Mes
				String yyyy = fch[2]; //Año
				
				return (Timestamp.valueOf(yyyy+"-"+mm+"-"+dd+" 00:00:00"));	
			}
			
			return null;
	}
}

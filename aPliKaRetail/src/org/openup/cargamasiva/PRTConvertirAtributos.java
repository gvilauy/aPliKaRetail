/**
 * 
 */
package org.openup.cargamasiva;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MTTChequeraLine;
import org.openup.model.MXLSIssue;
import org.openup.util.ItalcredSystem;

/**OpenUp Ltda Issue#
 * @author SBT 1/9/2015
 *
 */
public class PRTConvertirAtributos extends SvrProcess{

	Sheet hoja=null;
	String fileName = null;

	Workbook workbook=null;
	Integer tope =0;
	
	/**
	 * 
	 */
	public PRTConvertirAtributos() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		MUser sbt = MUser.forNameAndSystem(getCtx(), "sbouissa", null);
		if(sbt == null || this.getAD_User_ID()!=sbt.get_ID()){
			throw new AdempiereException("El usuario no es administrador");
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		//fileName = "/srv/share/ATRIBUTOS_FIN070915.xls";//directorio produccion
		fileName = "D:\\Sylvie_OpenUP\\3-Covadonga\\0-AtriburosSetiembre\\ATRIBUTOS_FIN070915.xls";//directorio sbt
		// Creo el objeto
		File file = new File(fileName);
		// Si el objeto no existe
		if (file.exists()) {
			try {

				// Get de workbook
				workbook = AuxWorkCellXLS.getReadWorkbook(file);

				// Abro la primer hoja
				hoja = workbook.getSheet(0);

				if (hoja.getColumns() < 1) {
					return ("La primer hoja de la planilla Excel no tiene columnas");
				}

				if (hoja.getRows() < 1) {
					return ("La primer hoja de la planilla Excel no tiene columnas");
				}
			}catch (Exception e) {
				return ("Error al abrir planilla (TRY)");
			}
			String count = readXLS();
			return "OK";
		}
		return "Error";
	}

	
	private String readXLS() throws Exception {
		
		String message = "";
		Cell cell = null;
		tope = hoja.getRows();
		//instancio clase auxiliar
		//utiles = new AuxWorkCellXLS(getCtx(), get_Table_ID(), get_ID(),null, hoja,null);
		MProduct unProducto = null;
		int cantVacias = 0;
		

		for (int recorrido = 0; recorrido < tope; recorrido++) {
			System.out.println("Linea "+recorrido);
			message = "";
//			String ctaVacio = null;
//			String msgCuentaVacio = "";
//			String cedulaVacio = null;
//			String msgCedulaVacio = "";
//			String fechaVacio = null;
//			String msgFechaVacio = "";
//			String nombreVacio = null;
//			String msgNombreVacio = "";
//			String importeVacio = null;
//			String msgImporteVacio = "";
//			String literalVacio = null;
//			String msgLiteralVacio = "";
//		

			try {
				//Se lee si es Insert (I) en la columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String accion =cell.getContents();		
				if(accion==null || accion.equalsIgnoreCase("")){
					accion=null;
					message="Accion vacía";
					//msgCuentaVacio = message;
					
				} else  {
					//debo validar que la accion see insert
					if(accion.equals("I")){
						//continúo leyendo celdas
					}else{
						accion=null;
						message="Accion invalida";
						//msgCuentaVacio = message;
					}
					
				}
				//---------------------------------------------------------------
				
				//Se lee la tabla tiene que corresponder a ARTICULO sino no se considera la linea 
				//q es la columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String tabla = cell.getContents();	
				if(tabla==null || tabla.equalsIgnoreCase("")){
					tabla=null;
					message="Tabla vacía";
					//msgCedulaVacio = message;
				} else {
										
					//debo validar que la tabla sea ARTICULO
					if(!tabla.trim().equals("ARTICULOS")){
						tabla=null;
						message="Tabla no es Articulo";
					//	msgCedulaVacio = message;
					}						
				}
				//-----------------------------------------------------------------------------
				
				//Se lee el codigo de articulo en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));				
				String codArticulo=cell.getContents();
				if(codArticulo!=null){
					try{
						//Verifico que exista Producto con digo código
						codArticulo=codArticulo.trim();
						int codInt = Integer.parseInt(codArticulo);
						unProducto = MProduct.forValue(getCtx(), String.valueOf(codInt), null);
						if(null==unProducto){
							message="No se encontro producto con codigo "+codArticulo;
							codArticulo = null;
						}else{
							//Continuo leyendo
						}

					}catch (Exception e){
						codArticulo = null;
						//MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						message="Value incorrecto";
						//fechaVal=null;
					}
				} else {
					codArticulo = null;
					message="Value vacio";
					//fechaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					//msgFechaVacio = message;
				}
				//---------------------------------------------------------------

				//Se lee atributos en formato hexagesimal en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String atributosHexa=cell.getContents();
				if(atributosHexa==null || atributosHexa.equalsIgnoreCase("")){
					atributosHexa=null;
					message="Atributos vacios";
					//nombreVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					//msgNombreVacio = message;
				} else atributosHexa = atributosHexa.trim();
				//-----------------------------------------------------------------------------

				if(accion!=null && tabla!=null && codArticulo!=null && atributosHexa!=null 
						&& message.equalsIgnoreCase("")){
				 //	convierto codigo de atributos de hexa a bit
					String listaAtributos = convertirHexaABits(atributosHexa);			
					unProducto.setAtributosBitABit(listaAtributos);
				}else{
					
					System.out.println(message);
				}
				
//				if(accion==null && tabla==null && codArticulo==null && atributosHexa==null 
//						&& tabla==null) cantVacias ++; //aumento contador de filas vacias
//							
//				if(cantVacias == 5) recorrido = tope; //permito un maximo de 5 lineas vacias leidas, superado ese tope salgo del FOR
				
			} catch (Exception e) {
				//Errores no contemplados
				throw new AdempiereException("exepcion al leer datos");
			}
		
		}				

		if (workbook!=null){
			workbook.close();
		}

		if(message.equalsIgnoreCase("")) message="Proceso Finalizado OK";

		return message;
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 2/9/2015
	 * @param atributosHexa
	 * @return
	 */
	private String convertirHexaABits(String atributosHexa) {
		String hex=atributosHexa;
		String recopilo = "";
		for(int i =0; i<hex.length();i++){
			char a = hex.charAt(i);
			String valA = String.valueOf(a);
			if(valA.toUpperCase().equals("A")||valA.toUpperCase().equals("B")||valA.toUpperCase().equals("C")
					||valA.toUpperCase().equals("D")||valA.toUpperCase().equals("E")||valA.toUpperCase().equals("F")){	
				recopilo = recopilo + obtenerBinarioDeNum(valA);
			}else{
				int num1 = Integer.valueOf(String.valueOf(a));
				recopilo = recopilo + obtengoBinarioExacto(Integer.toBinaryString(num1));
			}
			
		}
		System.out.println("Resultado: "+recopilo);
		if(recopilo.length()==64){
			return recopilo;
		}else {
			throw new AdempiereException("Error al convertir hexa a bit");
		}
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 7/9/2015
	 * @param valA
	 * @return
	 */
	private String obtenerBinarioDeNum(String valA) {
		if(valA.equals("A")){
			return "1010";
		}else if(valA.equals("B")){
			return "1011";
		}else if(valA.equals("C")){
			return "1100";
		}else if(valA.equals("D")){
			return "1101";
		}else if(valA.equals("E")){
			return "1110";
		}else if(valA.equals("F")){
			return "1111";
		}
		return "";
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 7/9/2015
	 * @param binaryString
	 * @return
	 */
	private String obtengoBinarioExacto(String bin) {
		if(bin.length()==1){
			return "000"+bin;
		}else if(bin.length()==2){
			return "00"+bin;
		}else if (bin.length()==3){
			return "0"+bin;
		}else return bin;
	}
}

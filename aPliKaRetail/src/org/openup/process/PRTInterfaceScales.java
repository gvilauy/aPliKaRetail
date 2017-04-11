/**
 * 
 */
package org.openup.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOM;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MFamilia;
import org.openup.model.MProductGroup;
import org.openup.model.MRTInterfaceProd;
import org.openup.model.MRTInterfaceScales;
import org.openup.model.MRTSendPosLog;
import org.openup.model.MSubFamilia;
import org.openup.model.X_UY_RT_SendPosLog;
import org.openup.util.OpenUpUtils;

/**OpenUp Ltda Issue#
 * @author sevans Jan 22, 2016
 *
 */
public class PRTInterfaceScales extends SvrProcess {
	
	
	private String fchToday;
	private	List<MRTInterfaceScales> scalescodes = null; // datos de los códigos de barra de los productos a procesar
	private Boolean onWindows = true;
	private File fBatch = null;
	private File fBatchTarget = null;
	
	private static final String SEPARATOR_L = ":";
	private static final String NAME_BATCH = "MantSistBalanza.txt";
	private static final String TIENDA = "0000";
	private static final String SECCION = "0000";
	private static final String FLIA = "001";
	private static final String SUBFLIA = "001";			
	private static final String TIPOIVA = "0";
	private static final String CODRAPIDO = "000";
	private static final String CODPLU = "1";
	private static final String TAGFORMAT = "21";
	private static final String DEFAULTACTION = "M";
	
	private static final int DESCLENGTH = 30;
	private static final int CODLENGTH = 6;
	private static final int PRICELENGTH = 10;
	private static final Object DUELENGTH = 3;	
	Properties ctx = null; String trxName = "";
	int mAD_ClientId=0;
	/**
	 * 
	 */
	public PRTInterfaceScales() {
		// TODO Auto-generated constructor stub
	}
	
	public void setContexto(Properties c){
		ctx = c;
	}

	public void setTransaccion(String trx){
		trxName = trx;
	}
	
	/**
	 * @param mAD_ClientId the mAD_ClientId to set
	 */
	public void setmAD_ClientId(int mAD_ClientId) {
		this.mAD_ClientId = mAD_ClientId;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		onWindows = true;
		try{
//			String a = System.getProperty("os.name" );
//			if(a.contains("Linux")){
//				onWindows = false;
//				System.out.println(System.getProperty("os.name" ));
//			} 
			setWinOrLinux();
			setContexto(getCtx());
			setTransaccion(get_TrxName());
			setmAD_ClientId(this.getAD_Client_ID());

		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new AdempiereException(e.getMessage());
		}

	}

	/**
	 * OpenUp Ltda Issue #6337
	 * @author Sylvie Bouissa 6/7/2016
	 */
	public void setWinOrLinux() {
		String a = System.getProperty("os.name" );
		if(a.contains("Linux")){
			this.onWindows = false;
			System.out.println(System.getProperty("os.name" ));
		}else{
			this.onWindows = true;
			System.out.println(System.getProperty("os.name" ));
		}
	}

	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/1/2016
	 * @return Directorio origen 
	 */
	private String getRutaOrigen() {
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_SCALES_HISTORY_MANT",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_SCALES_HISTORY_MANT",0); //Directorio origen prametrizado para linux
		}
		return origen;		

	}	
	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/1/2016
	 * @return Directorio origen 
	 */
	private String getRutaDestino() {
		String  origen = "";
		if(onWindows){
			origen = MSysConfig.getValue("UY_DESTINATION_SCALES_HISTORY_MANT_DEST",0); //Directorio origen prametrizado para windows
		}else{
			origen = MSysConfig.getValue("DESTINATION_SCALES_HISTORY_MANT_DEST",0); //Directorio origen prametrizado para linux
		}
		return origen;		

	}		
	
	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/1/2016
	 * @return
	 */
	private File createBatchFile(boolean isOrigin) {
		File fb=null;
		if(isOrigin){
			fb = new File(getRutaOrigen()+File.separator+fchToday+PRTInterfaceScales.NAME_BATCH);
		}else{
			fb = new File(getRutaDestino()+File.separator+PRTInterfaceScales.NAME_BATCH);
		}
		return fb;
	}	
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		
		String[] hra = (new Timestamp (System.currentTimeMillis()).toString().split(SEPARATOR_L));
		String fecha =hra[0].replace("-", "").replace(" ", "_")+hra[1];
		fchToday = fecha;
		fBatch = createBatchFile(true);
		int count = 0; int countError = 0;
		MRTInterfaceScales scales = new MRTInterfaceScales(ctx, 0, trxName);
		String line;
		scalescodes = MRTInterfaceScales.forScalesNotRead(ctx, trxName);
		if(scalescodes != null){
			for (MRTInterfaceScales row : scalescodes){
				//Se paresea la l�nea como corresponde seg�n manual
				line = createLine(row);
				if(line != null){
					writeFile(line, fBatch, true);
					MRTInterfaceScales.markScalesAsRead(ctx, row, trxName);
					count++;
				}else{
					countError++;
					
					MRTSendPosLog spl = new MRTSendPosLog(Env.getCtx(), 0, null);
					spl.setM_Product_ID(row.getM_Product_ID());
					spl.setDateTrx(today);
					spl.setSourceType(X_UY_RT_SendPosLog.SOURCETYPE_BALANZA);
					spl.saveEx();
					
				}	
			}
			if(count>0){
				fBatchTarget = createBatchFile(false);
				copyFile(fBatch, fBatchTarget);
			}
			
		}
		String datos = "Se procesaron "+count+" lineas correctamente - ";
		if(countError>0){
			datos = datos + " No se procesaron "+countError+" lineas";
		}
		return datos;
		//return "Hay "+count+" lineas para procesar";
	}
	
	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 22/1/2016
	 * Se genera una línea para escribir en el archivo de mantenimiento
	 * @return linea a escribir
	 */	
	private String createLine(MRTInterfaceScales scale){
		
		//Instancio el Producti
		MProduct prod = new MProduct(ctx, scale.getM_Product_ID(), trxName);
		//Inscatncio la Unidad de medida
		MUOM uom = new MUOM(ctx, prod.getC_UOM_ID(), trxName);
		if(uom == null || uom.get_ID() == 0)
			return null;
		
		String codProdFormat = String.format("%%0%dd", 6);
		String result = "";
		
		String tienda = TIENDA; // Por ahora siempre tiene ese valor
		String seccion = SECCION; // Valor por defecto para covadonga
		String flia = FLIA; // Valor por defecto para covadonga
		String subflia = SUBFLIA; // Valor por defecto para covadonga
		String codRapido = CODRAPIDO; // Valor por defecto
		String tipoIVA = TIPOIVA;
		String venc = "";
		
		
		//OpenUp. SBT 23/05/2016 Issue #5953 Se implementa para que funcione con planeta
		//Si es planeta -->se debe setear tienda = local , seccion = rubro, familia y subfamilia

		//Obtengo el proveedor
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", this.mAD_ClientId);
		if (provider.equalsIgnoreCase("Planeta")) {
			seccion = "0001"; flia="0001"; subflia = "0001"; // Valores por defecto para planeta
			MOrg org = new MOrg(ctx,scale.getAD_OrgAux_ID(),null); 
			if(null!=org && org.get_ID()>0){
				tienda = org.getValue();
			}else{
				System.out.println("Se tiene que indicar una sucursal v�lida");
				return null;
			}
			
			if(null!=prod.get_Value("UY_ProductGroup_ID")){
				if(prod.get_ValueAsInt("UY_ProductGroup_ID")>0){
					MProductGroup pg = new MProductGroup(ctx,prod.get_ValueAsInt("UY_ProductGroup_ID"),null);
					if(null!=pg && pg.get_ID()>0){
						seccion = pg.get_ValueAsString("POSCode");
					}
				}
			}
			
			if(0<prod.getUY_Familia_ID()){
				MFamilia f = new MFamilia(ctx,prod.getUY_Familia_ID(),null);
				if(null!=f && f.get_ID()>0){
					flia = f.get_ValueAsString("POSCode");
					
					if(0<prod.getUY_SubFamilia_ID()){
						MSubFamilia sf = new MSubFamilia(ctx,prod.getUY_SubFamilia_ID(),null);
						if(null!=sf && sf.get_ID()>0){
							subflia = sf.get_ValueAsString("POSCode");
						}
					}
				}
			}
			
			if(null!=prod.get_Value("CodigoBalanza")){
				//Codigo r�pido entre 001 al 138
				codRapido = prod.get_ValueAsString("CodigoBalanza");
				//Se controla si el codigo es menor a tres digitos ya que hay que completarlo
				if(codRapido.length()>0 && codRapido.length()<3){
					if(codRapido.length()==2){
						codRapido = "0"+codRapido;
					}else if(codRapido.length()==1){
						codRapido = "00"+codRapido;
					}
				}
			}
			
		}// Fin si es planeta Issue #5953
		
		Timestamp today_aux = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), 
				TimeUtil.TRUNC_DAY);
						
//		Timestamp dueDate_aux = (Timestamp)prod.get_Value("dueDate");
		//calculo los d�as que faltan para el vencimiento 
//		if(dueDate_aux != null){
//			String dueDateFormat = String.format("%%0%dd", DUELENGTH);
//			GregorianCalendar today = new GregorianCalendar();		
//			GregorianCalendar dueDate= new GregorianCalendar();
//			today.setTime(today_aux);
//			dueDate.setTime(dueDate_aux);
//			int days = (dueDate.get(Calendar.DAY_OF_YEAR)- today.get(Calendar.DAY_OF_YEAR))+1;			
//			venc = String.format(dueDateFormat, days);
		//SBT 15/09/2016 Issue #7029
		if(prod.get_Value("Expiration_Days")!=null){
				venc = prod.get_Value("Expiration_Days").toString();
				if(venc.length()<2){
					venc = "00"+venc;
				}else if(venc.length()<3){
					venc = "0"+venc;
				}
				/*if(venc.length()<3){
					if(venc.length()<2){
						venc = "00"+venc;
					}
					venc = "0"+venc;
				}*/	
			}else{// En caso de que no haya una fecha de caducidad se setea en "000"
			venc = "000";
		}
		
		String codProd = scale.getcodprod(); //SBT 26-02-2016 Se cambia x' lo hablado con Rodrigo B el 25/02/2016
		//String codProd = scale.getcodupc(); 
		codProd = String.format(codProdFormat, Integer.parseInt(codProd));
		if(codProd.length() > CODLENGTH){
			System.out.println("ERROR_NO EL CODIGO ES MAYOR A 999999 - "+scale.getDescription()+"-"+scale.getcodprod());
			//scale.saveEx();
			return null;
		}
		
		
		String desc = String.format("%1$-30s", scale.getDescription());
		if (desc.length() > DESCLENGTH){
			desc = desc.substring(0, DESCLENGTH);
		}		
		String priceFormat = String.format("%%0%dd", 10);
		//SBT 07-04-2016 ISsue # (Se agrega lista de venta en USD)
		int currID = OpenUpUtils.getSchemaCurrencyID(ctx, this.mAD_ClientId, null);
		
		int mAdOrgAux = scale.getAD_OrgAux_ID();
		
		if(mAdOrgAux==0){
			System.out.println("No se puede interfacear product "+prod.getValue()+" "
					+ ",ya que tiente como organizaci�n 0");
			return null;
		}
		
		if(prod.getSalePrice(currID,mAdOrgAux)==null){
			if(142 == currID){
				currID=100;
			}else{
				currID = 142;
			}
			if(prod.getSalePrice(currID,mAdOrgAux)==null || prod.getSalePrice(currID,mAdOrgAux).compareTo(Env.ZERO)==0){
				System.out.println("El producto "+prod.getValue()+" no esta en la lista de precio de venta vigente");
				return null;
			}
		}
		
		String price = prod.getSalePrice(currID,mAdOrgAux).setScale(2, BigDecimal.ROUND_UP).toString();
		price = price.replace(".", "");
		price = String.format(priceFormat, Integer.parseInt(price));
		if(price.length() > PRICELENGTH)
			return null;
		
		String action = scale.getAction();
		String codPLU = "";
		
		if (uom.getUOMSymbol().equalsIgnoreCase("KG")){
			codPLU = "1";
		}else{
			codPLU = "2";
		}
		
		String tagFormat = TAGFORMAT;
		//Formo la linea, le pongo salto de linea de Windows para que no haya problemas en el software que levanta el archivo 
		result = tienda + seccion + codProd + flia + subflia + desc + price + tipoIVA + action + codRapido + codPLU + tagFormat + venc + "\r\n";
		//System.out.println(result);
		return result;
	}


	
	/**
	 * OpenUp Ltda Issue# 5319
	 * @author Santiago Evans 22/1/2016
	 * @param lineIn
	 * @param fBatch
	 */
	private void writeFile(String lineIn, File fBatch, Boolean continuo) {
		BufferedWriter bw=null;
		File f = fBatch;
		//Escritura
		if(null!=f){
			try{
				
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fBatch, continuo), "ISO-8859-15"));
				bw.append(lineIn);
				
				/*
				
				//FileWriter w = new FileWriter(f,continuo); 
				//SBT 19/09/2016 Issue #7051 se agrega codificacion ya que por defecto crea con ISO-8859-1
				//MClient clte = new MClient(ctx,Env.getAD_Client_ID(ctx),get_TrxName());//Issue #7412 se cambia la forma de obtener el contexto
				//if(clte.getName().equalsIgnoreCase("planeta")){
					//org.zkoss.io.FileWriter wp = new org.zkoss.io.FileWriter(f, "ISO-8859-15", continuo);
					//bw = new BufferedWriter(wp);
				//}else{//Issue #
					FileWriter w = new FileWriter(f,continuo);
					bw = new BufferedWriter(w);
				//}
				//bw = new BufferedWriter(w);
				bw.append(lineIn);
				
				*/
				
			}catch(IOException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null!=bw) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	}	
	
	/**
	 * OpenUp Ltda Issue #5319
	 * @author Santiago Evans 25/01/2016
	 * @param source
	 * @param dest
	 */
	@SuppressWarnings("resource")
	private void copyFile(File sourceFile, File destFile)
			throws IOException {
			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			FileChannel origen = null;
			FileChannel destino = null;
			try {
				origen = new FileInputStream(sourceFile).getChannel();
				destino = new FileOutputStream(destFile).getChannel();

				long count = 0;
				long size = origen.size();
				while ((count += destino.transferFrom(origen, count, size - count)) < size)
					;
			} finally {
				if (origen != null) {
					origen.close();
				}
				if (destino != null) {
					destino.close();
				}
			}
	}	

}

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_RT_AuditLoadTicket
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_AuditLoadTicket 
{

    /** TableName=UY_RT_AuditLoadTicket */
    public static final String Table_Name = "UY_RT_AuditLoadTicket";

    /** AD_Table_ID=1000905 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name amtapgaveta */
    public static final String COLUMNNAME_amtapgaveta = "amtapgaveta";

	/** Set amtapgaveta	  */
	public void setamtapgaveta (BigDecimal amtapgaveta);

	/** Get amtapgaveta	  */
	public BigDecimal getamtapgaveta();

    /** Column name amtcajera */
    public static final String COLUMNNAME_amtcajera = "amtcajera";

	/** Set amtcajera	  */
	public void setamtcajera (BigDecimal amtcajera);

	/** Get amtcajera	  */
	public BigDecimal getamtcajera();

    /** Column name amtcanje */
    public static final String COLUMNNAME_amtcanje = "amtcanje";

	/** Set amtcanje	  */
	public void setamtcanje (BigDecimal amtcanje);

	/** Get amtcanje	  */
	public BigDecimal getamtcanje();

    /** Column name amtconsulta */
    public static final String COLUMNNAME_amtconsulta = "amtconsulta";

	/** Set amtconsulta	  */
	public void setamtconsulta (BigDecimal amtconsulta);

	/** Get amtconsulta	  */
	public BigDecimal getamtconsulta();

    /** Column name amtcreditodevolucion */
    public static final String COLUMNNAME_amtcreditodevolucion = "amtcreditodevolucion";

	/** Set amtcreditodevolucion	  */
	public void setamtcreditodevolucion (BigDecimal amtcreditodevolucion);

	/** Get amtcreditodevolucion	  */
	public BigDecimal getamtcreditodevolucion();

    /** Column name amtdevolucion */
    public static final String COLUMNNAME_amtdevolucion = "amtdevolucion";

	/** Set amtdevolucion	  */
	public void setamtdevolucion (BigDecimal amtdevolucion);

	/** Get amtdevolucion	  */
	public BigDecimal getamtdevolucion();

    /** Column name amtestadocta */
    public static final String COLUMNNAME_amtestadocta = "amtestadocta";

	/** Set amtestadocta	  */
	public void setamtestadocta (BigDecimal amtestadocta);

	/** Get amtestadocta	  */
	public BigDecimal getamtestadocta();

    /** Column name amtexentoiva */
    public static final String COLUMNNAME_amtexentoiva = "amtexentoiva";

	/** Set amtexentoiva	  */
	public void setamtexentoiva (BigDecimal amtexentoiva);

	/** Get amtexentoiva	  */
	public BigDecimal getamtexentoiva();

    /** Column name amtfactura */
    public static final String COLUMNNAME_amtfactura = "amtfactura";

	/** Set amtfactura	  */
	public void setamtfactura (BigDecimal amtfactura);

	/** Get amtfactura	  */
	public BigDecimal getamtfactura();

    /** Column name amtfondeo */
    public static final String COLUMNNAME_amtfondeo = "amtfondeo";

	/** Set amtfondeo	  */
	public void setamtfondeo (BigDecimal amtfondeo);

	/** Get amtfondeo	  */
	public BigDecimal getamtfondeo();

    /** Column name amtgift */
    public static final String COLUMNNAME_amtgift = "amtgift";

	/** Set amtgift	  */
	public void setamtgift (BigDecimal amtgift);

	/** Get amtgift	  */
	public BigDecimal getamtgift();

    /** Column name amtingresopersonal */
    public static final String COLUMNNAME_amtingresopersonal = "amtingresopersonal";

	/** Set amtingresopersonal	  */
	public void setamtingresopersonal (BigDecimal amtingresopersonal);

	/** Get amtingresopersonal	  */
	public BigDecimal getamtingresopersonal();

    /** Column name amtinventario */
    public static final String COLUMNNAME_amtinventario = "amtinventario";

	/** Set amtinventario	  */
	public void setamtinventario (BigDecimal amtinventario);

	/** Get amtinventario	  */
	public BigDecimal getamtinventario();

    /** Column name amtnodefinido */
    public static final String COLUMNNAME_amtnodefinido = "amtnodefinido";

	/** Set amtnodefinido	  */
	public void setamtnodefinido (BigDecimal amtnodefinido);

	/** Get amtnodefinido	  */
	public BigDecimal getamtnodefinido();

    /** Column name amtnogenerado */
    public static final String COLUMNNAME_amtnogenerado = "amtnogenerado";

	/** Set amtnogenerado	  */
	public void setamtnogenerado (BigDecimal amtnogenerado);

	/** Get amtnogenerado	  */
	public BigDecimal getamtnogenerado();

    /** Column name amtpagocaja */
    public static final String COLUMNNAME_amtpagocaja = "amtpagocaja";

	/** Set amtpagocaja	  */
	public void setamtpagocaja (BigDecimal amtpagocaja);

	/** Get amtpagocaja	  */
	public BigDecimal getamtpagocaja();

    /** Column name amtpedido */
    public static final String COLUMNNAME_amtpedido = "amtpedido";

	/** Set amtpedido	  */
	public void setamtpedido (BigDecimal amtpedido);

	/** Get amtpedido	  */
	public BigDecimal getamtpedido();

    /** Column name amtretiro */
    public static final String COLUMNNAME_amtretiro = "amtretiro";

	/** Set amtretiro	  */
	public void setamtretiro (BigDecimal amtretiro);

	/** Get amtretiro	  */
	public BigDecimal getamtretiro();

    /** Column name amtventas */
    public static final String COLUMNNAME_amtventas = "amtventas";

	/** Set amtventas	  */
	public void setamtventas (BigDecimal amtventas);

	/** Get amtventas	  */
	public BigDecimal getamtventas();

    /** Column name amtvtacheque */
    public static final String COLUMNNAME_amtvtacheque = "amtvtacheque";

	/** Set amtvtacheque	  */
	public void setamtvtacheque (BigDecimal amtvtacheque);

	/** Get amtvtacheque	  */
	public BigDecimal getamtvtacheque();

    /** Column name amtvtachequecobranza */
    public static final String COLUMNNAME_amtvtachequecobranza = "amtvtachequecobranza";

	/** Set amtvtachequecobranza	  */
	public void setamtvtachequecobranza (BigDecimal amtvtachequecobranza);

	/** Get amtvtachequecobranza	  */
	public BigDecimal getamtvtachequecobranza();

    /** Column name amtvtachequedlrs */
    public static final String COLUMNNAME_amtvtachequedlrs = "amtvtachequedlrs";

	/** Set amtvtachequedlrs	  */
	public void setamtvtachequedlrs (BigDecimal amtvtachequedlrs);

	/** Get amtvtachequedlrs	  */
	public BigDecimal getamtvtachequedlrs();

    /** Column name amtvtaclientesfidelizacion */
    public static final String COLUMNNAME_amtvtaclientesfidelizacion = "amtvtaclientesfidelizacion";

	/** Set amtvtaclientesfidelizacion	  */
	public void setamtvtaclientesfidelizacion (BigDecimal amtvtaclientesfidelizacion);

	/** Get amtvtaclientesfidelizacion	  */
	public BigDecimal getamtvtaclientesfidelizacion();

    /** Column name AmtVtaCredito */
    public static final String COLUMNNAME_AmtVtaCredito = "AmtVtaCredito";

	/** Set AmtVtaCredito	  */
	public void setAmtVtaCredito (BigDecimal AmtVtaCredito);

	/** Get AmtVtaCredito	  */
	public BigDecimal getAmtVtaCredito();

    /** Column name AmtVtaCreditoDlrs */
    public static final String COLUMNNAME_AmtVtaCreditoDlrs = "AmtVtaCreditoDlrs";

	/** Set AmtVtaCreditoDlrs	  */
	public void setAmtVtaCreditoDlrs (BigDecimal AmtVtaCreditoDlrs);

	/** Get AmtVtaCreditoDlrs	  */
	public BigDecimal getAmtVtaCreditoDlrs();

    /** Column name amtvtadevenvases */
    public static final String COLUMNNAME_amtvtadevenvases = "amtvtadevenvases";

	/** Set amtvtadevenvases	  */
	public void setamtvtadevenvases (BigDecimal amtvtadevenvases);

	/** Get amtvtadevenvases	  */
	public BigDecimal getamtvtadevenvases();

    /** Column name amtvtaefectivo */
    public static final String COLUMNNAME_amtvtaefectivo = "amtvtaefectivo";

	/** Set amtvtaefectivo	  */
	public void setamtvtaefectivo (BigDecimal amtvtaefectivo);

	/** Get amtvtaefectivo	  */
	public BigDecimal getamtvtaefectivo();

    /** Column name amtvtaefectivodolares */
    public static final String COLUMNNAME_amtvtaefectivodolares = "amtvtaefectivodolares";

	/** Set amtvtaefectivodolares	  */
	public void setamtvtaefectivodolares (BigDecimal amtvtaefectivodolares);

	/** Get amtvtaefectivodolares	  */
	public BigDecimal getamtvtaefectivodolares();

    /** Column name amtvtaefectivosodexo */
    public static final String COLUMNNAME_amtvtaefectivosodexo = "amtvtaefectivosodexo";

	/** Set amtvtaefectivosodexo	  */
	public void setamtvtaefectivosodexo (BigDecimal amtvtaefectivosodexo);

	/** Get amtvtaefectivosodexo	  */
	public BigDecimal getamtvtaefectivosodexo();

    /** Column name amtvtafacturas2 */
    public static final String COLUMNNAME_amtvtafacturas2 = "amtvtafacturas2";

	/** Set amtvtafacturas2	  */
	public void setamtvtafacturas2 (BigDecimal amtvtafacturas2);

	/** Get amtvtafacturas2	  */
	public BigDecimal getamtvtafacturas2();

    /** Column name amtvtafondeo */
    public static final String COLUMNNAME_amtvtafondeo = "amtvtafondeo";

	/** Set amtvtafondeo	  */
	public void setamtvtafondeo (BigDecimal amtvtafondeo);

	/** Get amtvtafondeo	  */
	public BigDecimal getamtvtafondeo();

    /** Column name amtvtaluncheon */
    public static final String COLUMNNAME_amtvtaluncheon = "amtvtaluncheon";

	/** Set amtvtaluncheon	  */
	public void setamtvtaluncheon (BigDecimal amtvtaluncheon);

	/** Get amtvtaluncheon	  */
	public BigDecimal getamtvtaluncheon();

    /** Column name amtvtapagodeservicio */
    public static final String COLUMNNAME_amtvtapagodeservicio = "amtvtapagodeservicio";

	/** Set amtvtapagodeservicio	  */
	public void setamtvtapagodeservicio (BigDecimal amtvtapagodeservicio);

	/** Get amtvtapagodeservicio	  */
	public BigDecimal getamtvtapagodeservicio();

    /** Column name amtvtaretiro */
    public static final String COLUMNNAME_amtvtaretiro = "amtvtaretiro";

	/** Set amtvtaretiro	  */
	public void setamtvtaretiro (BigDecimal amtvtaretiro);

	/** Get amtvtaretiro	  */
	public BigDecimal getamtvtaretiro();

    /** Column name amtvtatarjeta */
    public static final String COLUMNNAME_amtvtatarjeta = "amtvtatarjeta";

	/** Set amtvtatarjeta	  */
	public void setamtvtatarjeta (BigDecimal amtvtatarjeta);

	/** Get amtvtatarjeta	  */
	public BigDecimal getamtvtatarjeta();

    /** Column name amtvtatarjetacuota */
    public static final String COLUMNNAME_amtvtatarjetacuota = "amtvtatarjetacuota";

	/** Set amtvtatarjetacuota	  */
	public void setamtvtatarjetacuota (BigDecimal amtvtatarjetacuota);

	/** Get amtvtatarjetacuota	  */
	public BigDecimal getamtvtatarjetacuota();

    /** Column name amtvtatarjetadlrs */
    public static final String COLUMNNAME_amtvtatarjetadlrs = "amtvtatarjetadlrs";

	/** Set amtvtatarjetadlrs	  */
	public void setamtvtatarjetadlrs (BigDecimal amtvtatarjetadlrs);

	/** Get amtvtatarjetadlrs	  */
	public BigDecimal getamtvtatarjetadlrs();

    /** Column name amtvtatarjetaofline */
    public static final String COLUMNNAME_amtvtatarjetaofline = "amtvtatarjetaofline";

	/** Set amtvtatarjetaofline	  */
	public void setamtvtatarjetaofline (BigDecimal amtvtatarjetaofline);

	/** Get amtvtatarjetaofline	  */
	public BigDecimal getamtvtatarjetaofline();

    /** Column name amtvtatktalimentacion */
    public static final String COLUMNNAME_amtvtatktalimentacion = "amtvtatktalimentacion";

	/** Set amtvtatktalimentacion	  */
	public void setamtvtatktalimentacion (BigDecimal amtvtatktalimentacion);

	/** Get amtvtatktalimentacion	  */
	public BigDecimal getamtvtatktalimentacion();

    /** Column name amtz */
    public static final String COLUMNNAME_amtz = "amtz";

	/** Set amtz	  */
	public void setamtz (BigDecimal amtz);

	/** Get amtz	  */
	public BigDecimal getamtz();

    /** Column name codigo */
    public static final String COLUMNNAME_codigo = "codigo";

	/** Set codigo	  */
	public void setcodigo (String codigo);

	/** Get codigo	  */
	public String getcodigo();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateValue */
    public static final String COLUMNNAME_DateValue = "DateValue";

	/** Set Valuation Date.
	  * Date of valuation
	  */
	public void setDateValue (Timestamp DateValue);

	/** Get Valuation Date.
	  * Date of valuation
	  */
	public Timestamp getDateValue();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EndTime */
    public static final String COLUMNNAME_EndTime = "EndTime";

	/** Set End Time.
	  * End of the time span
	  */
	public void setEndTime (Timestamp EndTime);

	/** Get End Time.
	  * End of the time span
	  */
	public Timestamp getEndTime();

    /** Column name FileName */
    public static final String COLUMNNAME_FileName = "FileName";

	/** Set File Name.
	  * Name of the local file or URL
	  */
	public void setFileName (String FileName);

	/** Get File Name.
	  * Name of the local file or URL
	  */
	public String getFileName();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name path */
    public static final String COLUMNNAME_path = "path";

	/** Set path	  */
	public void setpath (String path);

	/** Get path	  */
	public String getpath();

    /** Column name qtytiketapgaveta */
    public static final String COLUMNNAME_qtytiketapgaveta = "qtytiketapgaveta";

	/** Set qtytiketapgaveta	  */
	public void setqtytiketapgaveta (BigDecimal qtytiketapgaveta);

	/** Get qtytiketapgaveta	  */
	public BigDecimal getqtytiketapgaveta();

    /** Column name qtytiketcajera */
    public static final String COLUMNNAME_qtytiketcajera = "qtytiketcajera";

	/** Set qtytiketcajera	  */
	public void setqtytiketcajera (BigDecimal qtytiketcajera);

	/** Get qtytiketcajera	  */
	public BigDecimal getqtytiketcajera();

    /** Column name qtytiketcanje */
    public static final String COLUMNNAME_qtytiketcanje = "qtytiketcanje";

	/** Set qtytiketcanje	  */
	public void setqtytiketcanje (BigDecimal qtytiketcanje);

	/** Get qtytiketcanje	  */
	public BigDecimal getqtytiketcanje();

    /** Column name qtytiketconsulta */
    public static final String COLUMNNAME_qtytiketconsulta = "qtytiketconsulta";

	/** Set qtytiketconsulta	  */
	public void setqtytiketconsulta (BigDecimal qtytiketconsulta);

	/** Get qtytiketconsulta	  */
	public BigDecimal getqtytiketconsulta();

    /** Column name qtytiketcreditodevolucion */
    public static final String COLUMNNAME_qtytiketcreditodevolucion = "qtytiketcreditodevolucion";

	/** Set qtytiketcreditodevolucion	  */
	public void setqtytiketcreditodevolucion (BigDecimal qtytiketcreditodevolucion);

	/** Get qtytiketcreditodevolucion	  */
	public BigDecimal getqtytiketcreditodevolucion();

    /** Column name qtytiketdevolucion */
    public static final String COLUMNNAME_qtytiketdevolucion = "qtytiketdevolucion";

	/** Set qtytiketdevolucion	  */
	public void setqtytiketdevolucion (BigDecimal qtytiketdevolucion);

	/** Get qtytiketdevolucion	  */
	public BigDecimal getqtytiketdevolucion();

    /** Column name qtytiketestadocta */
    public static final String COLUMNNAME_qtytiketestadocta = "qtytiketestadocta";

	/** Set qtytiketestadocta	  */
	public void setqtytiketestadocta (BigDecimal qtytiketestadocta);

	/** Get qtytiketestadocta	  */
	public BigDecimal getqtytiketestadocta();

    /** Column name qtytiketexentoiva */
    public static final String COLUMNNAME_qtytiketexentoiva = "qtytiketexentoiva";

	/** Set qtytiketexentoiva	  */
	public void setqtytiketexentoiva (BigDecimal qtytiketexentoiva);

	/** Get qtytiketexentoiva	  */
	public BigDecimal getqtytiketexentoiva();

    /** Column name qtytiketfactura */
    public static final String COLUMNNAME_qtytiketfactura = "qtytiketfactura";

	/** Set qtytiketfactura	  */
	public void setqtytiketfactura (BigDecimal qtytiketfactura);

	/** Get qtytiketfactura	  */
	public BigDecimal getqtytiketfactura();

    /** Column name qtytiketfondeo */
    public static final String COLUMNNAME_qtytiketfondeo = "qtytiketfondeo";

	/** Set qtytiketfondeo	  */
	public void setqtytiketfondeo (BigDecimal qtytiketfondeo);

	/** Get qtytiketfondeo	  */
	public BigDecimal getqtytiketfondeo();

    /** Column name qtytiketgift */
    public static final String COLUMNNAME_qtytiketgift = "qtytiketgift";

	/** Set qtytiketgift	  */
	public void setqtytiketgift (BigDecimal qtytiketgift);

	/** Get qtytiketgift	  */
	public BigDecimal getqtytiketgift();

    /** Column name qtytiketingresopersonal */
    public static final String COLUMNNAME_qtytiketingresopersonal = "qtytiketingresopersonal";

	/** Set qtytiketingresopersonal	  */
	public void setqtytiketingresopersonal (BigDecimal qtytiketingresopersonal);

	/** Get qtytiketingresopersonal	  */
	public BigDecimal getqtytiketingresopersonal();

    /** Column name qtytiketinventario */
    public static final String COLUMNNAME_qtytiketinventario = "qtytiketinventario";

	/** Set qtytiketinventario	  */
	public void setqtytiketinventario (BigDecimal qtytiketinventario);

	/** Get qtytiketinventario	  */
	public BigDecimal getqtytiketinventario();

    /** Column name qtytiketnodefinido */
    public static final String COLUMNNAME_qtytiketnodefinido = "qtytiketnodefinido";

	/** Set qtytiketnodefinido	  */
	public void setqtytiketnodefinido (BigDecimal qtytiketnodefinido);

	/** Get qtytiketnodefinido	  */
	public BigDecimal getqtytiketnodefinido();

    /** Column name qtytiketnogenerado */
    public static final String COLUMNNAME_qtytiketnogenerado = "qtytiketnogenerado";

	/** Set qtytiketnogenerado	  */
	public void setqtytiketnogenerado (BigDecimal qtytiketnogenerado);

	/** Get qtytiketnogenerado	  */
	public BigDecimal getqtytiketnogenerado();

    /** Column name qtytiketpagocaja */
    public static final String COLUMNNAME_qtytiketpagocaja = "qtytiketpagocaja";

	/** Set qtytiketpagocaja	  */
	public void setqtytiketpagocaja (BigDecimal qtytiketpagocaja);

	/** Get qtytiketpagocaja	  */
	public BigDecimal getqtytiketpagocaja();

    /** Column name qtytiketpedido */
    public static final String COLUMNNAME_qtytiketpedido = "qtytiketpedido";

	/** Set qtytiketpedido	  */
	public void setqtytiketpedido (BigDecimal qtytiketpedido);

	/** Get qtytiketpedido	  */
	public BigDecimal getqtytiketpedido();

    /** Column name qtytiketretiro */
    public static final String COLUMNNAME_qtytiketretiro = "qtytiketretiro";

	/** Set qtytiketretiro	  */
	public void setqtytiketretiro (BigDecimal qtytiketretiro);

	/** Get qtytiketretiro	  */
	public BigDecimal getqtytiketretiro();

    /** Column name qtytiketventas */
    public static final String COLUMNNAME_qtytiketventas = "qtytiketventas";

	/** Set qtytiketventas	  */
	public void setqtytiketventas (BigDecimal qtytiketventas);

	/** Get qtytiketventas	  */
	public BigDecimal getqtytiketventas();

    /** Column name qtytiketz */
    public static final String COLUMNNAME_qtytiketz = "qtytiketz";

	/** Set qtytiketz	  */
	public void setqtytiketz (BigDecimal qtytiketz);

	/** Get qtytiketz	  */
	public BigDecimal getqtytiketz();

    /** Column name qtyvtacheque */
    public static final String COLUMNNAME_qtyvtacheque = "qtyvtacheque";

	/** Set qtyvtacheque	  */
	public void setqtyvtacheque (BigDecimal qtyvtacheque);

	/** Get qtyvtacheque	  */
	public BigDecimal getqtyvtacheque();

    /** Column name qtyvtachequecobranza */
    public static final String COLUMNNAME_qtyvtachequecobranza = "qtyvtachequecobranza";

	/** Set qtyvtachequecobranza	  */
	public void setqtyvtachequecobranza (BigDecimal qtyvtachequecobranza);

	/** Get qtyvtachequecobranza	  */
	public BigDecimal getqtyvtachequecobranza();

    /** Column name qtyvtaclientesfidelizacion */
    public static final String COLUMNNAME_qtyvtaclientesfidelizacion = "qtyvtaclientesfidelizacion";

	/** Set qtyvtaclientesfidelizacion	  */
	public void setqtyvtaclientesfidelizacion (BigDecimal qtyvtaclientesfidelizacion);

	/** Get qtyvtaclientesfidelizacion	  */
	public BigDecimal getqtyvtaclientesfidelizacion();

    /** Column name qtyvtadevenvases */
    public static final String COLUMNNAME_qtyvtadevenvases = "qtyvtadevenvases";

	/** Set qtyvtadevenvases	  */
	public void setqtyvtadevenvases (BigDecimal qtyvtadevenvases);

	/** Get qtyvtadevenvases	  */
	public BigDecimal getqtyvtadevenvases();

    /** Column name qtyvtaefectivo */
    public static final String COLUMNNAME_qtyvtaefectivo = "qtyvtaefectivo";

	/** Set qtyvtaefectivo	  */
	public void setqtyvtaefectivo (BigDecimal qtyvtaefectivo);

	/** Get qtyvtaefectivo	  */
	public BigDecimal getqtyvtaefectivo();

    /** Column name qtyvtaefectivodolares */
    public static final String COLUMNNAME_qtyvtaefectivodolares = "qtyvtaefectivodolares";

	/** Set qtyvtaefectivodolares	  */
	public void setqtyvtaefectivodolares (BigDecimal qtyvtaefectivodolares);

	/** Get qtyvtaefectivodolares	  */
	public BigDecimal getqtyvtaefectivodolares();

    /** Column name qtyvtaefectivosodexo */
    public static final String COLUMNNAME_qtyvtaefectivosodexo = "qtyvtaefectivosodexo";

	/** Set qtyvtaefectivosodexo	  */
	public void setqtyvtaefectivosodexo (BigDecimal qtyvtaefectivosodexo);

	/** Get qtyvtaefectivosodexo	  */
	public BigDecimal getqtyvtaefectivosodexo();

    /** Column name qtyvtafacturas2 */
    public static final String COLUMNNAME_qtyvtafacturas2 = "qtyvtafacturas2";

	/** Set qtyvtafacturas2	  */
	public void setqtyvtafacturas2 (BigDecimal qtyvtafacturas2);

	/** Get qtyvtafacturas2	  */
	public BigDecimal getqtyvtafacturas2();

    /** Column name qtyvtafondeo */
    public static final String COLUMNNAME_qtyvtafondeo = "qtyvtafondeo";

	/** Set qtyvtafondeo	  */
	public void setqtyvtafondeo (BigDecimal qtyvtafondeo);

	/** Get qtyvtafondeo	  */
	public BigDecimal getqtyvtafondeo();

    /** Column name qtyvtaluncheon */
    public static final String COLUMNNAME_qtyvtaluncheon = "qtyvtaluncheon";

	/** Set qtyvtaluncheon	  */
	public void setqtyvtaluncheon (BigDecimal qtyvtaluncheon);

	/** Get qtyvtaluncheon	  */
	public BigDecimal getqtyvtaluncheon();

    /** Column name qtyvtapagodeservici */
    public static final String COLUMNNAME_qtyvtapagodeservici = "qtyvtapagodeservici";

	/** Set qtyvtapagodeservici	  */
	public void setqtyvtapagodeservici (BigDecimal qtyvtapagodeservici);

	/** Get qtyvtapagodeservici	  */
	public BigDecimal getqtyvtapagodeservici();

    /** Column name qtyvtaretiro */
    public static final String COLUMNNAME_qtyvtaretiro = "qtyvtaretiro";

	/** Set qtyvtaretiro	  */
	public void setqtyvtaretiro (BigDecimal qtyvtaretiro);

	/** Get qtyvtaretiro	  */
	public BigDecimal getqtyvtaretiro();

    /** Column name qtyvtatarjeta */
    public static final String COLUMNNAME_qtyvtatarjeta = "qtyvtatarjeta";

	/** Set qtyvtatarjeta	  */
	public void setqtyvtatarjeta (BigDecimal qtyvtatarjeta);

	/** Get qtyvtatarjeta	  */
	public BigDecimal getqtyvtatarjeta();

    /** Column name qtyvtatarjetacuota */
    public static final String COLUMNNAME_qtyvtatarjetacuota = "qtyvtatarjetacuota";

	/** Set qtyvtatarjetacuota	  */
	public void setqtyvtatarjetacuota (BigDecimal qtyvtatarjetacuota);

	/** Get qtyvtatarjetacuota	  */
	public BigDecimal getqtyvtatarjetacuota();

    /** Column name qtyvtatarjetaofline */
    public static final String COLUMNNAME_qtyvtatarjetaofline = "qtyvtatarjetaofline";

	/** Set qtyvtatarjetaofline	  */
	public void setqtyvtatarjetaofline (BigDecimal qtyvtatarjetaofline);

	/** Get qtyvtatarjetaofline	  */
	public BigDecimal getqtyvtatarjetaofline();

    /** Column name qtyvtatktalimentacion */
    public static final String COLUMNNAME_qtyvtatktalimentacion = "qtyvtatktalimentacion";

	/** Set qtyvtatktalimentacion	  */
	public void setqtyvtatktalimentacion (BigDecimal qtyvtatktalimentacion);

	/** Get qtyvtatktalimentacion	  */
	public BigDecimal getqtyvtatktalimentacion();

    /** Column name StartTime */
    public static final String COLUMNNAME_StartTime = "StartTime";

	/** Set Start Time.
	  * Time started
	  */
	public void setStartTime (Timestamp StartTime);

	/** Get Start Time.
	  * Time started
	  */
	public Timestamp getStartTime();

    /** Column name total1 */
    public static final String COLUMNNAME_total1 = "total1";

	/** Set total1	  */
	public void settotal1 (BigDecimal total1);

	/** Get total1	  */
	public BigDecimal gettotal1();

    /** Column name total2 */
    public static final String COLUMNNAME_total2 = "total2";

	/** Set total2	  */
	public void settotal2 (BigDecimal total2);

	/** Get total2	  */
	public BigDecimal gettotal2();

    /** Column name total3 */
    public static final String COLUMNNAME_total3 = "total3";

	/** Set total3	  */
	public void settotal3 (BigDecimal total3);

	/** Get total3	  */
	public BigDecimal gettotal3();

    /** Column name total4 */
    public static final String COLUMNNAME_total4 = "total4";

	/** Set total4	  */
	public void settotal4 (BigDecimal total4);

	/** Get total4	  */
	public BigDecimal gettotal4();

    /** Column name totalheaders */
    public static final String COLUMNNAME_totalheaders = "totalheaders";

	/** Set totalheaders	  */
	public void settotalheaders (String totalheaders);

	/** Get totalheaders	  */
	public String gettotalheaders();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (String TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public String getTotalLines();

    /** Column name totallinesfile */
    public static final String COLUMNNAME_totallinesfile = "totallinesfile";

	/** Set totallinesfile	  */
	public void settotallinesfile (String totallinesfile);

	/** Get totallinesfile	  */
	public String gettotallinesfile();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UY_RT_AuditLoadTicket_ID */
    public static final String COLUMNNAME_UY_RT_AuditLoadTicket_ID = "UY_RT_AuditLoadTicket_ID";

	/** Set UY_RT_AuditLoadTicket	  */
	public void setUY_RT_AuditLoadTicket_ID (int UY_RT_AuditLoadTicket_ID);

	/** Get UY_RT_AuditLoadTicket	  */
	public int getUY_RT_AuditLoadTicket_ID();

    /** Column name UY_RT_LoadTicket_ID */
    public static final String COLUMNNAME_UY_RT_LoadTicket_ID = "UY_RT_LoadTicket_ID";

	/** Set UY_RT_LoadTicket	  */
	public void setUY_RT_LoadTicket_ID (int UY_RT_LoadTicket_ID);

	/** Get UY_RT_LoadTicket	  */
	public int getUY_RT_LoadTicket_ID();

	public I_UY_RT_LoadTicket getUY_RT_LoadTicket() throws RuntimeException;

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name VtaFondeoDolares */
    public static final String COLUMNNAME_VtaFondeoDolares = "VtaFondeoDolares";

	/** Set VtaFondeoDolares	  */
	public void setVtaFondeoDolares (BigDecimal VtaFondeoDolares);

	/** Get VtaFondeoDolares	  */
	public BigDecimal getVtaFondeoDolares();

    /** Column name VtaTjaEdenredSodexo */
    public static final String COLUMNNAME_VtaTjaEdenredSodexo = "VtaTjaEdenredSodexo";

	/** Set VtaTjaEdenredSodexo	  */
	public void setVtaTjaEdenredSodexo (BigDecimal VtaTjaEdenredSodexo);

	/** Get VtaTjaEdenredSodexo	  */
	public BigDecimal getVtaTjaEdenredSodexo();
}

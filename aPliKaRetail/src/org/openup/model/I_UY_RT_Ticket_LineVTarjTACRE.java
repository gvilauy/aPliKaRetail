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

/** Generated Interface for UY_RT_Ticket_LineVTarjTACRE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Ticket_LineVTarjTACRE 
{

    /** TableName=UY_RT_Ticket_LineVTarjTACRE */
    public static final String Table_Name = "UY_RT_Ticket_LineVTarjTACRE";

    /** AD_Table_ID=1000988 */
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

    /** Column name autorizacion */
    public static final String COLUMNNAME_autorizacion = "autorizacion";

	/** Set autorizacion	  */
	public void setautorizacion (String autorizacion);

	/** Get autorizacion	  */
	public String getautorizacion();

    /** Column name codigocaja */
    public static final String COLUMNNAME_codigocaja = "codigocaja";

	/** Set codigocaja	  */
	public void setcodigocaja (String codigocaja);

	/** Get codigocaja	  */
	public String getcodigocaja();

    /** Column name codigocajera */
    public static final String COLUMNNAME_codigocajera = "codigocajera";

	/** Set codigocajera	  */
	public void setcodigocajera (String codigocajera);

	/** Get codigocajera	  */
	public String getcodigocajera();

    /** Column name codigocomercio */
    public static final String COLUMNNAME_codigocomercio = "codigocomercio";

	/** Set codigocomercio	  */
	public void setcodigocomercio (String codigocomercio);

	/** Get codigocomercio	  */
	public String getcodigocomercio();

    /** Column name codigomoneda */
    public static final String COLUMNNAME_codigomoneda = "codigomoneda";

	/** Set codigomoneda	  */
	public void setcodigomoneda (String codigomoneda);

	/** Get codigomoneda	  */
	public String getcodigomoneda();

    /** Column name codigoterminal */
    public static final String COLUMNNAME_codigoterminal = "codigoterminal";

	/** Set codigoterminal	  */
	public void setcodigoterminal (String codigoterminal);

	/** Get codigoterminal	  */
	public String getcodigoterminal();

    /** Column name comprobante */
    public static final String COLUMNNAME_comprobante = "comprobante";

	/** Set comprobante	  */
	public void setcomprobante (String comprobante);

	/** Get comprobante	  */
	public String getcomprobante();

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

    /** Column name descuentoiva */
    public static final String COLUMNNAME_descuentoiva = "descuentoiva";

	/** Set descuentoiva	  */
	public void setdescuentoiva (String descuentoiva);

	/** Get descuentoiva	  */
	public String getdescuentoiva();

    /** Column name fechatransaccion */
    public static final String COLUMNNAME_fechatransaccion = "fechatransaccion";

	/** Set fechatransaccion	  */
	public void setfechatransaccion (Timestamp fechatransaccion);

	/** Get fechatransaccion	  */
	public Timestamp getfechatransaccion();

    /** Column name flagimprimefirma */
    public static final String COLUMNNAME_flagimprimefirma = "flagimprimefirma";

	/** Set flagimprimefirma	  */
	public void setflagimprimefirma (String flagimprimefirma);

	/** Get flagimprimefirma	  */
	public String getflagimprimefirma();

    /** Column name ImportePago */
    public static final String COLUMNNAME_ImportePago = "ImportePago";

	/** Set ImportePago.
	  * ImportePago
	  */
	public void setImportePago (BigDecimal ImportePago);

	/** Get ImportePago.
	  * ImportePago
	  */
	public BigDecimal getImportePago();

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

    /** Column name montodescuentoleyiva */
    public static final String COLUMNNAME_montodescuentoleyiva = "montodescuentoleyiva";

	/** Set montodescuentoleyiva	  */
	public void setmontodescuentoleyiva (BigDecimal montodescuentoleyiva);

	/** Get montodescuentoleyiva	  */
	public BigDecimal getmontodescuentoleyiva();

    /** Column name montofactura */
    public static final String COLUMNNAME_montofactura = "montofactura";

	/** Set montofactura	  */
	public void setmontofactura (BigDecimal montofactura);

	/** Get montofactura	  */
	public BigDecimal getmontofactura();

    /** Column name montogravado */
    public static final String COLUMNNAME_montogravado = "montogravado";

	/** Set montogravado	  */
	public void setmontogravado (BigDecimal montogravado);

	/** Get montogravado	  */
	public BigDecimal getmontogravado();

    /** Column name nombrepropuetario */
    public static final String COLUMNNAME_nombrepropuetario = "nombrepropuetario";

	/** Set nombrepropuetario	  */
	public void setnombrepropuetario (String nombrepropuetario);

	/** Get nombrepropuetario	  */
	public String getnombrepropuetario();

    /** Column name nombretarjeta */
    public static final String COLUMNNAME_nombretarjeta = "nombretarjeta";

	/** Set nombretarjeta	  */
	public void setnombretarjeta (String nombretarjeta);

	/** Get nombretarjeta	  */
	public String getnombretarjeta();

    /** Column name NroLote */
    public static final String COLUMNNAME_NroLote = "NroLote";

	/** Set NroLote	  */
	public void setNroLote (String NroLote);

	/** Get NroLote	  */
	public String getNroLote();

    /** Column name numerodelinea */
    public static final String COLUMNNAME_numerodelinea = "numerodelinea";

	/** Set numerodelinea	  */
	public void setnumerodelinea (String numerodelinea);

	/** Get numerodelinea	  */
	public String getnumerodelinea();

    /** Column name numerotarjeta */
    public static final String COLUMNNAME_numerotarjeta = "numerotarjeta";

	/** Set numerotarjeta	  */
	public void setnumerotarjeta (String numerotarjeta);

	/** Get numerotarjeta	  */
	public String getnumerotarjeta();

    /** Column name positionfile */
    public static final String COLUMNNAME_positionfile = "positionfile";

	/** Set positionfile	  */
	public void setpositionfile (String positionfile);

	/** Get positionfile	  */
	public String getpositionfile();

    /** Column name siaplicaleydesciva */
    public static final String COLUMNNAME_siaplicaleydesciva = "siaplicaleydesciva";

	/** Set siaplicaleydesciva	  */
	public void setsiaplicaleydesciva (String siaplicaleydesciva);

	/** Get siaplicaleydesciva	  */
	public String getsiaplicaleydesciva();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

    /** Column name tipoautorizacion */
    public static final String COLUMNNAME_tipoautorizacion = "tipoautorizacion";

	/** Set tipoautorizacion	  */
	public void settipoautorizacion (String tipoautorizacion);

	/** Get tipoautorizacion	  */
	public String gettipoautorizacion();

    /** Column name tipoingreso */
    public static final String COLUMNNAME_tipoingreso = "tipoingreso";

	/** Set tipoingreso	  */
	public void settipoingreso (String tipoingreso);

	/** Get tipoingreso	  */
	public String gettipoingreso();

    /** Column name tipotransaccion */
    public static final String COLUMNNAME_tipotransaccion = "tipotransaccion";

	/** Set tipotransaccion	  */
	public void settipotransaccion (String tipotransaccion);

	/** Get tipotransaccion	  */
	public String gettipotransaccion();

    /** Column name tipovaucher */
    public static final String COLUMNNAME_tipovaucher = "tipovaucher";

	/** Set tipovaucher	  */
	public void settipovaucher (String tipovaucher);

	/** Get tipovaucher	  */
	public String gettipovaucher();

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

    /** Column name UY_RT_Ticket_Header_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_Header_ID = "UY_RT_Ticket_Header_ID";

	/** Set UY_RT_Ticket_Header	  */
	public void setUY_RT_Ticket_Header_ID (int UY_RT_Ticket_Header_ID);

	/** Get UY_RT_Ticket_Header	  */
	public int getUY_RT_Ticket_Header_ID();

	public I_UY_RT_Ticket_Header getUY_RT_Ticket_Header() throws RuntimeException;

    /** Column name UY_RT_Ticket_LineVTarjTACRE_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_LineVTarjTACRE_ID = "UY_RT_Ticket_LineVTarjTACRE_ID";

	/** Set UY_RT_Ticket_LineVTarjTACRE	  */
	public void setUY_RT_Ticket_LineVTarjTACRE_ID (int UY_RT_Ticket_LineVTarjTACRE_ID);

	/** Get UY_RT_Ticket_LineVTarjTACRE	  */
	public int getUY_RT_Ticket_LineVTarjTACRE_ID();

    /** Column name UY_RT_TicketType_ID */
    public static final String COLUMNNAME_UY_RT_TicketType_ID = "UY_RT_TicketType_ID";

	/** Set UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID);

	/** Get UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID();

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException;

    /** Column name Vencimiento */
    public static final String COLUMNNAME_Vencimiento = "Vencimiento";

	/** Set Vencimiento	  */
	public void setVencimiento (String Vencimiento);

	/** Get Vencimiento	  */
	public String getVencimiento();
}

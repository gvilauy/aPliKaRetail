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

/** Generated Interface for UY_RT_Ticket_LineItem
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Ticket_LineItem 
{

    /** TableName=UY_RT_Ticket_LineItem */
    public static final String Table_Name = "UY_RT_Ticket_LineItem";

    /** AD_Table_ID=1000894 */
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

    /** Column name cantdescmanuales */
    public static final String COLUMNNAME_cantdescmanuales = "cantdescmanuales";

	/** Set cantdescmanuales	  */
	public void setcantdescmanuales (int cantdescmanuales);

	/** Get cantdescmanuales	  */
	public int getcantdescmanuales();

    /** Column name Cantidad */
    public static final String COLUMNNAME_Cantidad = "Cantidad";

	/** Set Cantidad	  */
	public void setCantidad (BigDecimal Cantidad);

	/** Get Cantidad	  */
	public BigDecimal getCantidad();

    /** Column name cantidadartobsequio */
    public static final String COLUMNNAME_cantidadartobsequio = "cantidadartobsequio";

	/** Set cantidadartobsequio	  */
	public void setcantidadartobsequio (int cantidadartobsequio);

	/** Get cantidadartobsequio	  */
	public int getcantidadartobsequio();

    /** Column name codigoarticulo */
    public static final String COLUMNNAME_codigoarticulo = "codigoarticulo";

	/** Set codigoarticulo	  */
	public void setcodigoarticulo (String codigoarticulo);

	/** Get codigoarticulo	  */
	public String getcodigoarticulo();

    /** Column name codigoarticulooriginal */
    public static final String COLUMNNAME_codigoarticulooriginal = "codigoarticulooriginal";

	/** Set codigoarticulooriginal	  */
	public void setcodigoarticulooriginal (String codigoarticulooriginal);

	/** Get codigoarticulooriginal	  */
	public String getcodigoarticulooriginal();

    /** Column name codigoiva */
    public static final String COLUMNNAME_codigoiva = "codigoiva";

	/** Set codigoiva	  */
	public void setcodigoiva (String codigoiva);

	/** Get codigoiva	  */
	public String getcodigoiva();

    /** Column name codigomediodepago */
    public static final String COLUMNNAME_codigomediodepago = "codigomediodepago";

	/** Set codigomediodepago	  */
	public void setcodigomediodepago (String codigomediodepago);

	/** Get codigomediodepago	  */
	public String getcodigomediodepago();

    /** Column name codigovendedor */
    public static final String COLUMNNAME_codigovendedor = "codigovendedor";

	/** Set codigovendedor	  */
	public void setcodigovendedor (String codigovendedor);

	/** Get codigovendedor	  */
	public String getcodigovendedor();

    /** Column name color */
    public static final String COLUMNNAME_color = "color";

	/** Set color	  */
	public void setcolor (String color);

	/** Get color	  */
	public String getcolor();

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

    /** Column name iva */
    public static final String COLUMNNAME_iva = "iva";

	/** Set iva	  */
	public void setiva (BigDecimal iva);

	/** Get iva	  */
	public BigDecimal getiva();

    /** Column name ivadescuento */
    public static final String COLUMNNAME_ivadescuento = "ivadescuento";

	/** Set ivadescuento	  */
	public void setivadescuento (BigDecimal ivadescuento);

	/** Get ivadescuento	  */
	public BigDecimal getivadescuento();

    /** Column name ivadescuentocombo */
    public static final String COLUMNNAME_ivadescuentocombo = "ivadescuentocombo";

	/** Set ivadescuentocombo	  */
	public void setivadescuentocombo (BigDecimal ivadescuentocombo);

	/** Get ivadescuentocombo	  */
	public BigDecimal getivadescuentocombo();

    /** Column name ivadescuentomarca */
    public static final String COLUMNNAME_ivadescuentomarca = "ivadescuentomarca";

	/** Set ivadescuentomarca	  */
	public void setivadescuentomarca (BigDecimal ivadescuentomarca);

	/** Get ivadescuentomarca	  */
	public BigDecimal getivadescuentomarca();

    /** Column name ivadescuentototal */
    public static final String COLUMNNAME_ivadescuentototal = "ivadescuentototal";

	/** Set ivadescuentototal	  */
	public void setivadescuentototal (BigDecimal ivadescuentototal);

	/** Get ivadescuentototal	  */
	public BigDecimal getivadescuentototal();

    /** Column name lineacancelada */
    public static final String COLUMNNAME_lineacancelada = "lineacancelada";

	/** Set lineacancelada	  */
	public void setlineacancelada (int lineacancelada);

	/** Get lineacancelada	  */
	public int getlineacancelada();

    /** Column name marca */
    public static final String COLUMNNAME_marca = "marca";

	/** Set marca	  */
	public void setmarca (String marca);

	/** Get marca	  */
	public String getmarca();

    /** Column name modelo */
    public static final String COLUMNNAME_modelo = "modelo";

	/** Set modelo	  */
	public void setmodelo (String modelo);

	/** Get modelo	  */
	public String getmodelo();

    /** Column name modoingreso */
    public static final String COLUMNNAME_modoingreso = "modoingreso";

	/** Set modoingreso	  */
	public void setmodoingreso (String modoingreso);

	/** Get modoingreso	  */
	public String getmodoingreso();

    /** Column name montorealdescfidel */
    public static final String COLUMNNAME_montorealdescfidel = "montorealdescfidel";

	/** Set montorealdescfidel	  */
	public void setmontorealdescfidel (BigDecimal montorealdescfidel);

	/** Get montorealdescfidel	  */
	public BigDecimal getmontorealdescfidel();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name nrolineaconvenio */
    public static final String COLUMNNAME_nrolineaconvenio = "nrolineaconvenio";

	/** Set nrolineaconvenio	  */
	public void setnrolineaconvenio (int nrolineaconvenio);

	/** Get nrolineaconvenio	  */
	public int getnrolineaconvenio();

    /** Column name numerodelinea */
    public static final String COLUMNNAME_numerodelinea = "numerodelinea";

	/** Set numerodelinea	  */
	public void setnumerodelinea (String numerodelinea);

	/** Get numerodelinea	  */
	public String getnumerodelinea();

    /** Column name positionfile */
    public static final String COLUMNNAME_positionfile = "positionfile";

	/** Set positionfile	  */
	public void setpositionfile (String positionfile);

	/** Get positionfile	  */
	public String getpositionfile();

    /** Column name preciodescuento */
    public static final String COLUMNNAME_preciodescuento = "preciodescuento";

	/** Set preciodescuento	  */
	public void setpreciodescuento (BigDecimal preciodescuento);

	/** Get preciodescuento	  */
	public BigDecimal getpreciodescuento();

    /** Column name preciodescuentocombo */
    public static final String COLUMNNAME_preciodescuentocombo = "preciodescuentocombo";

	/** Set preciodescuentocombo	  */
	public void setpreciodescuentocombo (BigDecimal preciodescuentocombo);

	/** Get preciodescuentocombo	  */
	public BigDecimal getpreciodescuentocombo();

    /** Column name preciodescuentomarca */
    public static final String COLUMNNAME_preciodescuentomarca = "preciodescuentomarca";

	/** Set preciodescuentomarca	  */
	public void setpreciodescuentomarca (BigDecimal preciodescuentomarca);

	/** Get preciodescuentomarca	  */
	public BigDecimal getpreciodescuentomarca();

    /** Column name preciodescuentototal */
    public static final String COLUMNNAME_preciodescuentototal = "preciodescuentototal";

	/** Set preciodescuentototal	  */
	public void setpreciodescuentototal (BigDecimal preciodescuentototal);

	/** Get preciodescuentototal	  */
	public BigDecimal getpreciodescuentototal();

    /** Column name preciounitario */
    public static final String COLUMNNAME_preciounitario = "preciounitario";

	/** Set preciounitario	  */
	public void setpreciounitario (BigDecimal preciounitario);

	/** Get preciounitario	  */
	public BigDecimal getpreciounitario();

    /** Column name PrecioUnitarioConIva */
    public static final String COLUMNNAME_PrecioUnitarioConIva = "PrecioUnitarioConIva";

	/** Set PrecioUnitarioConIva.
	  * Precio unitario del item (precio de lista que incluye IVA).
	  */
	public void setPrecioUnitarioConIva (BigDecimal PrecioUnitarioConIva);

	/** Get PrecioUnitarioConIva.
	  * Precio unitario del item (precio de lista que incluye IVA).
	  */
	public BigDecimal getPrecioUnitarioConIva();

    /** Column name puntosoferta */
    public static final String COLUMNNAME_puntosoferta = "puntosoferta";

	/** Set puntosoferta	  */
	public void setpuntosoferta (BigDecimal puntosoferta);

	/** Get puntosoferta	  */
	public BigDecimal getpuntosoferta();

    /** Column name siaplicadescfidel */
    public static final String COLUMNNAME_siaplicadescfidel = "siaplicadescfidel";

	/** Set siaplicadescfidel	  */
	public void setsiaplicadescfidel (String siaplicadescfidel);

	/** Get siaplicadescfidel	  */
	public String getsiaplicadescfidel();

    /** Column name siesconvenio */
    public static final String COLUMNNAME_siesconvenio = "siesconvenio";

	/** Set siesconvenio	  */
	public void setsiesconvenio (String siesconvenio);

	/** Get siesconvenio	  */
	public String getsiesconvenio();

    /** Column name siesobsequio */
    public static final String COLUMNNAME_siesobsequio = "siesobsequio";

	/** Set siesobsequio	  */
	public void setsiesobsequio (String siesobsequio);

	/** Get siesobsequio	  */
	public String getsiesobsequio();

    /** Column name siestandem */
    public static final String COLUMNNAME_siestandem = "siestandem";

	/** Set siestandem	  */
	public void setsiestandem (String siestandem);

	/** Get siestandem	  */
	public String getsiestandem();

    /** Column name talle */
    public static final String COLUMNNAME_talle = "talle";

	/** Set talle	  */
	public void settalle (String talle);

	/** Get talle	  */
	public String gettalle();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

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

    /** Column name UY_RT_Ticket_CancelItem_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_CancelItem_ID = "UY_RT_Ticket_CancelItem_ID";

	/** Set UY_RT_Ticket_CancelItem	  */
	public void setUY_RT_Ticket_CancelItem_ID (int UY_RT_Ticket_CancelItem_ID);

	/** Get UY_RT_Ticket_CancelItem	  */
	public int getUY_RT_Ticket_CancelItem_ID();

	public I_UY_RT_Ticket_CancelItem getUY_RT_Ticket_CancelItem() throws RuntimeException;

    /** Column name UY_RT_Ticket_Header_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_Header_ID = "UY_RT_Ticket_Header_ID";

	/** Set UY_RT_Ticket_Header	  */
	public void setUY_RT_Ticket_Header_ID (int UY_RT_Ticket_Header_ID);

	/** Get UY_RT_Ticket_Header	  */
	public int getUY_RT_Ticket_Header_ID();

	public I_UY_RT_Ticket_Header getUY_RT_Ticket_Header() throws RuntimeException;

    /** Column name UY_RT_Ticket_LineItem_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_LineItem_ID = "UY_RT_Ticket_LineItem_ID";

	/** Set UY_RT_Ticket_LineItem	  */
	public void setUY_RT_Ticket_LineItem_ID (int UY_RT_Ticket_LineItem_ID);

	/** Get UY_RT_Ticket_LineItem	  */
	public int getUY_RT_Ticket_LineItem_ID();

    /** Column name UY_RT_Ticket_LineItemReturn_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID = "UY_RT_Ticket_LineItemReturn_ID";

	/** Set UY_RT_Ticket_LineItemReturn.
	  * Campo especifico para las lineas de itmem generadas por openup que hace referencia a una devolucion de articulo
	  */
	public void setUY_RT_Ticket_LineItemReturn_ID (int UY_RT_Ticket_LineItemReturn_ID);

	/** Get UY_RT_Ticket_LineItemReturn.
	  * Campo especifico para las lineas de itmem generadas por openup que hace referencia a una devolucion de articulo
	  */
	public int getUY_RT_Ticket_LineItemReturn_ID();

	public I_UY_RT_Ticket_LineItemReturn getUY_RT_Ticket_LineItemReturn() throws RuntimeException;

    /** Column name UY_RT_TicketType_ID */
    public static final String COLUMNNAME_UY_RT_TicketType_ID = "UY_RT_TicketType_ID";

	/** Set UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID);

	/** Get UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID();

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException;
}

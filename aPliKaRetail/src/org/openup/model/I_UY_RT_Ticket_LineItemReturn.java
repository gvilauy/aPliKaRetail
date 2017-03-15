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

/** Generated Interface for UY_RT_Ticket_LineItemReturn
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Ticket_LineItemReturn 
{

    /** TableName=UY_RT_Ticket_LineItemReturn */
    public static final String Table_Name = "UY_RT_Ticket_LineItemReturn";

    /** AD_Table_ID=1000952 */
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

    /** Column name Cantidad */
    public static final String COLUMNNAME_Cantidad = "Cantidad";

	/** Set Cantidad	  */
	public void setCantidad (BigDecimal Cantidad);

	/** Get Cantidad	  */
	public BigDecimal getCantidad();

    /** Column name codigoarticulooriginal */
    public static final String COLUMNNAME_codigoarticulooriginal = "codigoarticulooriginal";

	/** Set codigoarticulooriginal	  */
	public void setcodigoarticulooriginal (String codigoarticulooriginal);

	/** Get codigoarticulooriginal	  */
	public String getcodigoarticulooriginal();

    /** Column name codigoartsubf */
    public static final String COLUMNNAME_codigoartsubf = "codigoartsubf";

	/** Set codigoartsubf	  */
	public void setcodigoartsubf (String codigoartsubf);

	/** Get codigoartsubf	  */
	public String getcodigoartsubf();

    /** Column name codigosupervisora */
    public static final String COLUMNNAME_codigosupervisora = "codigosupervisora";

	/** Set codigosupervisora	  */
	public void setcodigosupervisora (String codigosupervisora);

	/** Get codigosupervisora	  */
	public String getcodigosupervisora();

    /** Column name codigovendedor */
    public static final String COLUMNNAME_codigovendedor = "codigovendedor";

	/** Set codigovendedor	  */
	public void setcodigovendedor (String codigovendedor);

	/** Get codigovendedor	  */
	public String getcodigovendedor();

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

    /** Column name indicadorartsubf */
    public static final String COLUMNNAME_indicadorartsubf = "indicadorartsubf";

	/** Set indicadorartsubf	  */
	public void setindicadorartsubf (String indicadorartsubf);

	/** Get indicadorartsubf	  */
	public String getindicadorartsubf();

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

    /** Column name lineacancelada */
    public static final String COLUMNNAME_lineacancelada = "lineacancelada";

	/** Set lineacancelada	  */
	public void setlineacancelada (int lineacancelada);

	/** Get lineacancelada	  */
	public int getlineacancelada();

    /** Column name modoingreso */
    public static final String COLUMNNAME_modoingreso = "modoingreso";

	/** Set modoingreso	  */
	public void setmodoingreso (String modoingreso);

	/** Get modoingreso	  */
	public String getmodoingreso();

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

    /** Column name precio */
    public static final String COLUMNNAME_precio = "precio";

	/** Set precio	  */
	public void setprecio (BigDecimal precio);

	/** Get precio	  */
	public BigDecimal getprecio();

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

    /** Column name UY_RT_Ticket_Header_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_Header_ID = "UY_RT_Ticket_Header_ID";

	/** Set UY_RT_Ticket_Header	  */
	public void setUY_RT_Ticket_Header_ID (int UY_RT_Ticket_Header_ID);

	/** Get UY_RT_Ticket_Header	  */
	public int getUY_RT_Ticket_Header_ID();

	public I_UY_RT_Ticket_Header getUY_RT_Ticket_Header() throws RuntimeException;

    /** Column name UY_RT_Ticket_LineItemReturn_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID = "UY_RT_Ticket_LineItemReturn_ID";

	/** Set UY_RT_Ticket_LineItemReturn	  */
	public void setUY_RT_Ticket_LineItemReturn_ID (int UY_RT_Ticket_LineItemReturn_ID);

	/** Get UY_RT_Ticket_LineItemReturn	  */
	public int getUY_RT_Ticket_LineItemReturn_ID();

    /** Column name UY_RT_TicketType_ID */
    public static final String COLUMNNAME_UY_RT_TicketType_ID = "UY_RT_TicketType_ID";

	/** Set UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID);

	/** Get UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID();

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException;
}

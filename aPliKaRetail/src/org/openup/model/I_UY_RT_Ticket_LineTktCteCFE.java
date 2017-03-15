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

/** Generated Interface for UY_RT_Ticket_LineTktCteCFE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Ticket_LineTktCteCFE 
{

    /** TableName=UY_RT_Ticket_LineTktCteCFE */
    public static final String Table_Name = "UY_RT_Ticket_LineTktCteCFE";

    /** AD_Table_ID=1000993 */
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

    /** Column name ciudad */
    public static final String COLUMNNAME_ciudad = "ciudad";

	/** Set ciudad	  */
	public void setciudad (String ciudad);

	/** Get ciudad	  */
	public String getciudad();

    /** Column name codigodepartamento */
    public static final String COLUMNNAME_codigodepartamento = "codigodepartamento";

	/** Set codigodepartamento	  */
	public void setcodigodepartamento (String codigodepartamento);

	/** Get codigodepartamento	  */
	public String getcodigodepartamento();

    /** Column name codigopais */
    public static final String COLUMNNAME_codigopais = "codigopais";

	/** Set codigopais	  */
	public void setcodigopais (String codigopais);

	/** Get codigopais	  */
	public String getcodigopais();

    /** Column name codigopostal */
    public static final String COLUMNNAME_codigopostal = "codigopostal";

	/** Set codigopostal	  */
	public void setcodigopostal (String codigopostal);

	/** Get codigopostal	  */
	public String getcodigopostal();

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

    /** Column name direccion */
    public static final String COLUMNNAME_direccion = "direccion";

	/** Set direccion	  */
	public void setdireccion (String direccion);

	/** Get direccion	  */
	public String getdireccion();

    /** Column name documento */
    public static final String COLUMNNAME_documento = "documento";

	/** Set documento	  */
	public void setdocumento (String documento);

	/** Get documento	  */
	public String getdocumento();

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

    /** Column name nombre */
    public static final String COLUMNNAME_nombre = "nombre";

	/** Set nombre	  */
	public void setnombre (String nombre);

	/** Get nombre	  */
	public String getnombre();

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

    /** Column name rut */
    public static final String COLUMNNAME_rut = "rut";

	/** Set rut	  */
	public void setrut (String rut);

	/** Get rut	  */
	public String getrut();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

    /** Column name tipodocumento */
    public static final String COLUMNNAME_tipodocumento = "tipodocumento";

	/** Set tipodocumento	  */
	public void settipodocumento (String tipodocumento);

	/** Get tipodocumento	  */
	public String gettipodocumento();

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

    /** Column name UY_RT_Ticket_LineTktCteCFE_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_LineTktCteCFE_ID = "UY_RT_Ticket_LineTktCteCFE_ID";

	/** Set UY_RT_Ticket_LineTktCteCFE	  */
	public void setUY_RT_Ticket_LineTktCteCFE_ID (int UY_RT_Ticket_LineTktCteCFE_ID);

	/** Get UY_RT_Ticket_LineTktCteCFE	  */
	public int getUY_RT_Ticket_LineTktCteCFE_ID();

    /** Column name UY_RT_TicketType_ID */
    public static final String COLUMNNAME_UY_RT_TicketType_ID = "UY_RT_TicketType_ID";

	/** Set UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID);

	/** Get UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID();

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException;
}

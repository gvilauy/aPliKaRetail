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

/** Generated Interface for UY_RT_Ticket_LineCabezalCFE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Ticket_LineCabezalCFE 
{

    /** TableName=UY_RT_Ticket_LineCabezalCFE */
    public static final String Table_Name = "UY_RT_Ticket_LineCabezalCFE";

    /** AD_Table_ID=1000981 */
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

    /** Column name ciudadreceptor */
    public static final String COLUMNNAME_ciudadreceptor = "ciudadreceptor";

	/** Set ciudadreceptor	  */
	public void setciudadreceptor (String ciudadreceptor);

	/** Get ciudadreceptor	  */
	public String getciudadreceptor();

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

    /** Column name descripcioncfe */
    public static final String COLUMNNAME_descripcioncfe = "descripcioncfe";

	/** Set descripcioncfe	  */
	public void setdescripcioncfe (String descripcioncfe);

	/** Get descripcioncfe	  */
	public String getdescripcioncfe();

    /** Column name direccionreceptor */
    public static final String COLUMNNAME_direccionreceptor = "direccionreceptor";

	/** Set direccionreceptor	  */
	public void setdireccionreceptor (String direccionreceptor);

	/** Get direccionreceptor	  */
	public String getdireccionreceptor();

    /** Column name documentoreceptor */
    public static final String COLUMNNAME_documentoreceptor = "documentoreceptor";

	/** Set documentoreceptor	  */
	public void setdocumentoreceptor (String documentoreceptor);

	/** Get documentoreceptor	  */
	public String getdocumentoreceptor();

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

    /** Column name nombrereceptor */
    public static final String COLUMNNAME_nombrereceptor = "nombrereceptor";

	/** Set nombrereceptor	  */
	public void setnombrereceptor (String nombrereceptor);

	/** Get nombrereceptor	  */
	public String getnombrereceptor();

    /** Column name numerocfe */
    public static final String COLUMNNAME_numerocfe = "numerocfe";

	/** Set numerocfe	  */
	public void setnumerocfe (BigDecimal numerocfe);

	/** Get numerocfe	  */
	public BigDecimal getnumerocfe();

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

    /** Column name seriecfe */
    public static final String COLUMNNAME_seriecfe = "seriecfe";

	/** Set seriecfe	  */
	public void setseriecfe (String seriecfe);

	/** Get seriecfe	  */
	public String getseriecfe();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

    /** Column name tipocfe */
    public static final String COLUMNNAME_tipocfe = "tipocfe";

	/** Set tipocfe	  */
	public void settipocfe (String tipocfe);

	/** Get tipocfe	  */
	public String gettipocfe();

    /** Column name tipodocumentoreceptor */
    public static final String COLUMNNAME_tipodocumentoreceptor = "tipodocumentoreceptor";

	/** Set tipodocumentoreceptor	  */
	public void settipodocumentoreceptor (String tipodocumentoreceptor);

	/** Get tipodocumentoreceptor	  */
	public String gettipodocumentoreceptor();

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

    /** Column name UY_RT_Ticket_LineCabezalCFE_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_LineCabezalCFE_ID = "UY_RT_Ticket_LineCabezalCFE_ID";

	/** Set UY_RT_Ticket_LineCabezalCFE	  */
	public void setUY_RT_Ticket_LineCabezalCFE_ID (int UY_RT_Ticket_LineCabezalCFE_ID);

	/** Get UY_RT_Ticket_LineCabezalCFE	  */
	public int getUY_RT_Ticket_LineCabezalCFE_ID();

    /** Column name UY_RT_TicketType_ID */
    public static final String COLUMNNAME_UY_RT_TicketType_ID = "UY_RT_TicketType_ID";

	/** Set UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID);

	/** Get UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID();

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException;
}

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

/** Generated Interface for UY_RT_Ticket_BenefAlTotal
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Ticket_BenefAlTotal 
{

    /** TableName=UY_RT_Ticket_BenefAlTotal */
    public static final String Table_Name = "UY_RT_Ticket_BenefAlTotal";

    /** AD_Table_ID=1000989 */
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

    /** Column name cantidadarticuloregalo */
    public static final String COLUMNNAME_cantidadarticuloregalo = "cantidadarticuloregalo";

	/** Set cantidadarticuloregalo	  */
	public void setcantidadarticuloregalo (String cantidadarticuloregalo);

	/** Get cantidadarticuloregalo	  */
	public String getcantidadarticuloregalo();

    /** Column name cantidadcupones */
    public static final String COLUMNNAME_cantidadcupones = "cantidadcupones";

	/** Set cantidadcupones	  */
	public void setcantidadcupones (String cantidadcupones);

	/** Get cantidadcupones	  */
	public String getcantidadcupones();

    /** Column name codigoarticuloregalo */
    public static final String COLUMNNAME_codigoarticuloregalo = "codigoarticuloregalo";

	/** Set codigoarticuloregalo	  */
	public void setcodigoarticuloregalo (String codigoarticuloregalo);

	/** Get codigoarticuloregalo	  */
	public String getcodigoarticuloregalo();

    /** Column name codigobeneficio */
    public static final String COLUMNNAME_codigobeneficio = "codigobeneficio";

	/** Set codigobeneficio	  */
	public void setcodigobeneficio (String codigobeneficio);

	/** Get codigobeneficio	  */
	public String getcodigobeneficio();

    /** Column name codigodescuento */
    public static final String COLUMNNAME_codigodescuento = "codigodescuento";

	/** Set codigodescuento	  */
	public void setcodigodescuento (String codigodescuento);

	/** Get codigodescuento	  */
	public String getcodigodescuento();

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

    /** Column name importedescuento */
    public static final String COLUMNNAME_importedescuento = "importedescuento";

	/** Set importedescuento	  */
	public void setimportedescuento (BigDecimal importedescuento);

	/** Get importedescuento	  */
	public BigDecimal getimportedescuento();

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

    /** Column name ivadescuento */
    public static final String COLUMNNAME_ivadescuento = "ivadescuento";

	/** Set ivadescuento	  */
	public void setivadescuento (BigDecimal ivadescuento);

	/** Get ivadescuento	  */
	public BigDecimal getivadescuento();

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

    /** Column name puntosextras */
    public static final String COLUMNNAME_puntosextras = "puntosextras";

	/** Set puntosextras	  */
	public void setpuntosextras (String puntosextras);

	/** Get puntosextras	  */
	public String getpuntosextras();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

    /** Column name tipobeneficio */
    public static final String COLUMNNAME_tipobeneficio = "tipobeneficio";

	/** Set tipobeneficio	  */
	public void settipobeneficio (String tipobeneficio);

	/** Get tipobeneficio	  */
	public String gettipobeneficio();

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

    /** Column name UY_RT_Ticket_BenefAlTotal_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_BenefAlTotal_ID = "UY_RT_Ticket_BenefAlTotal_ID";

	/** Set UY_RT_Ticket_BenefAlTotal	  */
	public void setUY_RT_Ticket_BenefAlTotal_ID (int UY_RT_Ticket_BenefAlTotal_ID);

	/** Get UY_RT_Ticket_BenefAlTotal	  */
	public int getUY_RT_Ticket_BenefAlTotal_ID();

    /** Column name UY_RT_Ticket_Header_ID */
    public static final String COLUMNNAME_UY_RT_Ticket_Header_ID = "UY_RT_Ticket_Header_ID";

	/** Set UY_RT_Ticket_Header	  */
	public void setUY_RT_Ticket_Header_ID (int UY_RT_Ticket_Header_ID);

	/** Get UY_RT_Ticket_Header	  */
	public int getUY_RT_Ticket_Header_ID();

	public I_UY_RT_Ticket_Header getUY_RT_Ticket_Header() throws RuntimeException;

    /** Column name UY_RT_TicketType_ID */
    public static final String COLUMNNAME_UY_RT_TicketType_ID = "UY_RT_TicketType_ID";

	/** Set UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID);

	/** Get UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID();

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException;

    /** Column name vecesarticuloregalo */
    public static final String COLUMNNAME_vecesarticuloregalo = "vecesarticuloregalo";

	/** Set vecesarticuloregalo	  */
	public void setvecesarticuloregalo (String vecesarticuloregalo);

	/** Get vecesarticuloregalo	  */
	public String getvecesarticuloregalo();

    /** Column name vecescupones */
    public static final String COLUMNNAME_vecescupones = "vecescupones";

	/** Set vecescupones	  */
	public void setvecescupones (String vecescupones);

	/** Get vecescupones	  */
	public String getvecescupones();

    /** Column name vecespuntosextras */
    public static final String COLUMNNAME_vecespuntosextras = "vecespuntosextras";

	/** Set vecespuntosextras	  */
	public void setvecespuntosextras (String vecespuntosextras);

	/** Get vecespuntosextras	  */
	public String getvecespuntosextras();
}

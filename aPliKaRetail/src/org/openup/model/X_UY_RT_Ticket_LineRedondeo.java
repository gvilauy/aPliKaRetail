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
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_RT_Ticket_LineRedondeo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineRedondeo extends PO implements I_UY_RT_Ticket_LineRedondeo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160112L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineRedondeo (Properties ctx, int UY_RT_Ticket_LineRedondeo_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineRedondeo_ID, trxName);
      /** if (UY_RT_Ticket_LineRedondeo_ID == 0)
        {
			setUY_RT_Ticket_LineRedondeo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineRedondeo (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineRedondeo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set codigomediodepago.
		@param codigomediodepago codigomediodepago	  */
	public void setcodigomediodepago (String codigomediodepago)
	{
		set_Value (COLUMNNAME_codigomediodepago, codigomediodepago);
	}

	/** Get codigomediodepago.
		@return codigomediodepago	  */
	public String getcodigomediodepago () 
	{
		return (String)get_Value(COLUMNNAME_codigomediodepago);
	}

	/** Set codigomoneda.
		@param codigomoneda codigomoneda	  */
	public void setcodigomoneda (String codigomoneda)
	{
		set_Value (COLUMNNAME_codigomoneda, codigomoneda);
	}

	/** Get codigomoneda.
		@return codigomoneda	  */
	public String getcodigomoneda () 
	{
		return (String)get_Value(COLUMNNAME_codigomoneda);
	}

	/** Set importeredondeo.
		@param importeredondeo importeredondeo	  */
	public void setimporteredondeo (BigDecimal importeredondeo)
	{
		set_Value (COLUMNNAME_importeredondeo, importeredondeo);
	}

	/** Get importeredondeo.
		@return importeredondeo	  */
	public BigDecimal getimporteredondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_importeredondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set importeticket.
		@param importeticket importeticket	  */
	public void setimporteticket (BigDecimal importeticket)
	{
		set_Value (COLUMNNAME_importeticket, importeticket);
	}

	/** Get importeticket.
		@return importeticket	  */
	public BigDecimal getimporteticket () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_importeticket);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set importetotalticket.
		@param importetotalticket importetotalticket	  */
	public void setimportetotalticket (BigDecimal importetotalticket)
	{
		set_Value (COLUMNNAME_importetotalticket, importetotalticket);
	}

	/** Get importetotalticket.
		@return importetotalticket	  */
	public BigDecimal getimportetotalticket () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_importetotalticket);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set numerodelinea.
		@param numerodelinea numerodelinea	  */
	public void setnumerodelinea (String numerodelinea)
	{
		set_Value (COLUMNNAME_numerodelinea, numerodelinea);
	}

	/** Get numerodelinea.
		@return numerodelinea	  */
	public String getnumerodelinea () 
	{
		return (String)get_Value(COLUMNNAME_numerodelinea);
	}

	/** Set positionfile.
		@param positionfile positionfile	  */
	public void setpositionfile (String positionfile)
	{
		set_Value (COLUMNNAME_positionfile, positionfile);
	}

	/** Get positionfile.
		@return positionfile	  */
	public String getpositionfile () 
	{
		return (String)get_Value(COLUMNNAME_positionfile);
	}

	/** Set timestamplinea.
		@param timestamplinea timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea)
	{
		set_Value (COLUMNNAME_timestamplinea, timestamplinea);
	}

	/** Get timestamplinea.
		@return timestamplinea	  */
	public Timestamp gettimestamplinea () 
	{
		return (Timestamp)get_Value(COLUMNNAME_timestamplinea);
	}

	public I_UY_RT_CodigoMedioPago getUY_RT_CodigoMedioPago() throws RuntimeException
    {
		return (I_UY_RT_CodigoMedioPago)MTable.get(getCtx(), I_UY_RT_CodigoMedioPago.Table_Name)
			.getPO(getUY_RT_CodigoMedioPago_ID(), get_TrxName());	}

	/** Set UY_RT_CodigoMedioPago.
		@param UY_RT_CodigoMedioPago_ID UY_RT_CodigoMedioPago	  */
	public void setUY_RT_CodigoMedioPago_ID (int UY_RT_CodigoMedioPago_ID)
	{
		if (UY_RT_CodigoMedioPago_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_CodigoMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_CodigoMedioPago_ID, Integer.valueOf(UY_RT_CodigoMedioPago_ID));
	}

	/** Get UY_RT_CodigoMedioPago.
		@return UY_RT_CodigoMedioPago	  */
	public int getUY_RT_CodigoMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_CodigoMedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_Ticket_Header getUY_RT_Ticket_Header() throws RuntimeException
    {
		return (I_UY_RT_Ticket_Header)MTable.get(getCtx(), I_UY_RT_Ticket_Header.Table_Name)
			.getPO(getUY_RT_Ticket_Header_ID(), get_TrxName());	}

	/** Set UY_RT_Ticket_Header.
		@param UY_RT_Ticket_Header_ID UY_RT_Ticket_Header	  */
	public void setUY_RT_Ticket_Header_ID (int UY_RT_Ticket_Header_ID)
	{
		if (UY_RT_Ticket_Header_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Ticket_Header_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Ticket_Header_ID, Integer.valueOf(UY_RT_Ticket_Header_ID));
	}

	/** Get UY_RT_Ticket_Header.
		@return UY_RT_Ticket_Header	  */
	public int getUY_RT_Ticket_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_Ticket_LineRedondeo.
		@param UY_RT_Ticket_LineRedondeo_ID UY_RT_Ticket_LineRedondeo	  */
	public void setUY_RT_Ticket_LineRedondeo_ID (int UY_RT_Ticket_LineRedondeo_ID)
	{
		if (UY_RT_Ticket_LineRedondeo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineRedondeo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineRedondeo_ID, Integer.valueOf(UY_RT_Ticket_LineRedondeo_ID));
	}

	/** Get UY_RT_Ticket_LineRedondeo.
		@return UY_RT_Ticket_LineRedondeo	  */
	public int getUY_RT_Ticket_LineRedondeo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineRedondeo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_TicketType getUY_RT_TicketType() throws RuntimeException
    {
		return (I_UY_RT_TicketType)MTable.get(getCtx(), I_UY_RT_TicketType.Table_Name)
			.getPO(getUY_RT_TicketType_ID(), get_TrxName());	}

	/** Set UY_RT_TicketType.
		@param UY_RT_TicketType_ID UY_RT_TicketType	  */
	public void setUY_RT_TicketType_ID (int UY_RT_TicketType_ID)
	{
		if (UY_RT_TicketType_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_TicketType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_TicketType_ID, Integer.valueOf(UY_RT_TicketType_ID));
	}

	/** Get UY_RT_TicketType.
		@return UY_RT_TicketType	  */
	public int getUY_RT_TicketType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_TicketType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
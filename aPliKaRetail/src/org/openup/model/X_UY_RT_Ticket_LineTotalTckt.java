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

/** Generated Model for UY_RT_Ticket_LineTotalTckt
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineTotalTckt extends PO implements I_UY_RT_Ticket_LineTotalTckt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151216L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineTotalTckt (Properties ctx, int UY_RT_Ticket_LineTotalTckt_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineTotalTckt_ID, trxName);
      /** if (UY_RT_Ticket_LineTotalTckt_ID == 0)
        {
			setUY_RT_Ticket_LineTotalTckt_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineTotalTckt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineTotalTckt[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ivatotaltcktsinpagoserv.
		@param ivatotaltcktsinpagoserv ivatotaltcktsinpagoserv	  */
	public void setivatotaltcktsinpagoserv (BigDecimal ivatotaltcktsinpagoserv)
	{
		set_Value (COLUMNNAME_ivatotaltcktsinpagoserv, ivatotaltcktsinpagoserv);
	}

	/** Get ivatotaltcktsinpagoserv.
		@return ivatotaltcktsinpagoserv	  */
	public BigDecimal getivatotaltcktsinpagoserv () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivatotaltcktsinpagoserv);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivatotalticket.
		@param ivatotalticket ivatotalticket	  */
	public void setivatotalticket (BigDecimal ivatotalticket)
	{
		set_Value (COLUMNNAME_ivatotalticket, ivatotalticket);
	}

	/** Get ivatotalticket.
		@return ivatotalticket	  */
	public BigDecimal getivatotalticket () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivatotalticket);
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

	/** Set totaltcktsinpagoserv.
		@param totaltcktsinpagoserv totaltcktsinpagoserv	  */
	public void settotaltcktsinpagoserv (BigDecimal totaltcktsinpagoserv)
	{
		set_Value (COLUMNNAME_totaltcktsinpagoserv, totaltcktsinpagoserv);
	}

	/** Get totaltcktsinpagoserv.
		@return totaltcktsinpagoserv	  */
	public BigDecimal gettotaltcktsinpagoserv () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totaltcktsinpagoserv);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalticket.
		@param totalticket totalticket	  */
	public void settotalticket (BigDecimal totalticket)
	{
		set_Value (COLUMNNAME_totalticket, totalticket);
	}

	/** Get totalticket.
		@return totalticket	  */
	public BigDecimal gettotalticket () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalticket);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_RT_Ticket_LineTotalTckt.
		@param UY_RT_Ticket_LineTotalTckt_ID UY_RT_Ticket_LineTotalTckt	  */
	public void setUY_RT_Ticket_LineTotalTckt_ID (int UY_RT_Ticket_LineTotalTckt_ID)
	{
		if (UY_RT_Ticket_LineTotalTckt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineTotalTckt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineTotalTckt_ID, Integer.valueOf(UY_RT_Ticket_LineTotalTckt_ID));
	}

	/** Get UY_RT_Ticket_LineTotalTckt.
		@return UY_RT_Ticket_LineTotalTckt	  */
	public int getUY_RT_Ticket_LineTotalTckt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineTotalTckt_ID);
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
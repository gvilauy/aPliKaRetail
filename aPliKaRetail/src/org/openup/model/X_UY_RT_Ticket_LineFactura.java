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

/** Generated Model for UY_RT_Ticket_LineFactura
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineFactura extends PO implements I_UY_RT_Ticket_LineFactura, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150506L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineFactura (Properties ctx, int UY_RT_Ticket_LineFactura_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineFactura_ID, trxName);
      /** if (UY_RT_Ticket_LineFactura_ID == 0)
        {
			setUY_RT_Ticket_LineFactura_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineFactura (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineFactura[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set caja.
		@param caja caja	  */
	public void setcaja (String caja)
	{
		set_Value (COLUMNNAME_caja, caja);
	}

	/** Get caja.
		@return caja	  */
	public String getcaja () 
	{
		return (String)get_Value(COLUMNNAME_caja);
	}

	/** Set cajaticket.
		@param cajaticket cajaticket	  */
	public void setcajaticket (String cajaticket)
	{
		set_Value (COLUMNNAME_cajaticket, cajaticket);
	}

	/** Get cajaticket.
		@return cajaticket	  */
	public String getcajaticket () 
	{
		return (String)get_Value(COLUMNNAME_cajaticket);
	}

	/** Set fechafactura.
		@param fechafactura fechafactura	  */
	public void setfechafactura (Timestamp fechafactura)
	{
		set_Value (COLUMNNAME_fechafactura, fechafactura);
	}

	/** Get fechafactura.
		@return fechafactura	  */
	public Timestamp getfechafactura () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechafactura);
	}

	/** Set impoteivacodigo.
		@param impoteivacodigo impoteivacodigo	  */
	public void setimpoteivacodigo (BigDecimal impoteivacodigo)
	{
		set_Value (COLUMNNAME_impoteivacodigo, impoteivacodigo);
	}

	/** Get impoteivacodigo.
		@return impoteivacodigo	  */
	public BigDecimal getimpoteivacodigo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo1.
		@param impoteivacodigo1 impoteivacodigo1	  */
	public void setimpoteivacodigo1 (BigDecimal impoteivacodigo1)
	{
		set_Value (COLUMNNAME_impoteivacodigo1, impoteivacodigo1);
	}

	/** Get impoteivacodigo1.
		@return impoteivacodigo1	  */
	public BigDecimal getimpoteivacodigo1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo2.
		@param impoteivacodigo2 impoteivacodigo2	  */
	public void setimpoteivacodigo2 (BigDecimal impoteivacodigo2)
	{
		set_Value (COLUMNNAME_impoteivacodigo2, impoteivacodigo2);
	}

	/** Get impoteivacodigo2.
		@return impoteivacodigo2	  */
	public BigDecimal getimpoteivacodigo2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo3.
		@param impoteivacodigo3 impoteivacodigo3	  */
	public void setimpoteivacodigo3 (BigDecimal impoteivacodigo3)
	{
		set_Value (COLUMNNAME_impoteivacodigo3, impoteivacodigo3);
	}

	/** Get impoteivacodigo3.
		@return impoteivacodigo3	  */
	public BigDecimal getimpoteivacodigo3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo4.
		@param impoteivacodigo4 impoteivacodigo4	  */
	public void setimpoteivacodigo4 (BigDecimal impoteivacodigo4)
	{
		set_Value (COLUMNNAME_impoteivacodigo4, impoteivacodigo4);
	}

	/** Get impoteivacodigo4.
		@return impoteivacodigo4	  */
	public BigDecimal getimpoteivacodigo4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo5.
		@param impoteivacodigo5 impoteivacodigo5	  */
	public void setimpoteivacodigo5 (BigDecimal impoteivacodigo5)
	{
		set_Value (COLUMNNAME_impoteivacodigo5, impoteivacodigo5);
	}

	/** Get impoteivacodigo5.
		@return impoteivacodigo5	  */
	public BigDecimal getimpoteivacodigo5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo6.
		@param impoteivacodigo6 impoteivacodigo6	  */
	public void setimpoteivacodigo6 (BigDecimal impoteivacodigo6)
	{
		set_Value (COLUMNNAME_impoteivacodigo6, impoteivacodigo6);
	}

	/** Get impoteivacodigo6.
		@return impoteivacodigo6	  */
	public BigDecimal getimpoteivacodigo6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set impoteivacodigo7.
		@param impoteivacodigo7 impoteivacodigo7	  */
	public void setimpoteivacodigo7 (BigDecimal impoteivacodigo7)
	{
		set_Value (COLUMNNAME_impoteivacodigo7, impoteivacodigo7);
	}

	/** Get impoteivacodigo7.
		@return impoteivacodigo7	  */
	public BigDecimal getimpoteivacodigo7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_impoteivacodigo7);
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

	/** Set numeroticket.
		@param numeroticket numeroticket	  */
	public void setnumeroticket (String numeroticket)
	{
		set_Value (COLUMNNAME_numeroticket, numeroticket);
	}

	/** Get numeroticket.
		@return numeroticket	  */
	public String getnumeroticket () 
	{
		return (String)get_Value(COLUMNNAME_numeroticket);
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

	/** Set ruc.
		@param ruc ruc	  */
	public void setruc (String ruc)
	{
		set_Value (COLUMNNAME_ruc, ruc);
	}

	/** Get ruc.
		@return ruc	  */
	public String getruc () 
	{
		return (String)get_Value(COLUMNNAME_ruc);
	}

	/** Set serienrofactura.
		@param serienrofactura serienrofactura	  */
	public void setserienrofactura (String serienrofactura)
	{
		set_Value (COLUMNNAME_serienrofactura, serienrofactura);
	}

	/** Get serienrofactura.
		@return serienrofactura	  */
	public String getserienrofactura () 
	{
		return (String)get_Value(COLUMNNAME_serienrofactura);
	}

	/** Set ticketreferencia.
		@param ticketreferencia ticketreferencia	  */
	public void setticketreferencia (String ticketreferencia)
	{
		set_Value (COLUMNNAME_ticketreferencia, ticketreferencia);
	}

	/** Get ticketreferencia.
		@return ticketreferencia	  */
	public String getticketreferencia () 
	{
		return (String)get_Value(COLUMNNAME_ticketreferencia);
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

	/** Set totalfactura.
		@param totalfactura totalfactura	  */
	public void settotalfactura (BigDecimal totalfactura)
	{
		set_Value (COLUMNNAME_totalfactura, totalfactura);
	}

	/** Get totalfactura.
		@return totalfactura	  */
	public BigDecimal gettotalfactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalfactura);
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

	/** Set UY_RT_Ticket_LineFactura.
		@param UY_RT_Ticket_LineFactura_ID UY_RT_Ticket_LineFactura	  */
	public void setUY_RT_Ticket_LineFactura_ID (int UY_RT_Ticket_LineFactura_ID)
	{
		if (UY_RT_Ticket_LineFactura_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineFactura_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineFactura_ID, Integer.valueOf(UY_RT_Ticket_LineFactura_ID));
	}

	/** Get UY_RT_Ticket_LineFactura.
		@return UY_RT_Ticket_LineFactura	  */
	public int getUY_RT_Ticket_LineFactura_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineFactura_ID);
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
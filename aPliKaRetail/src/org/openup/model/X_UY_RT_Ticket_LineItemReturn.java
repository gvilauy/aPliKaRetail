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

/** Generated Model for UY_RT_Ticket_LineItemReturn
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineItemReturn extends PO implements I_UY_RT_Ticket_LineItemReturn, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160301L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineItemReturn (Properties ctx, int UY_RT_Ticket_LineItemReturn_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineItemReturn_ID, trxName);
      /** if (UY_RT_Ticket_LineItemReturn_ID == 0)
        {
			setUY_RT_Ticket_LineItemReturn_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineItemReturn (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineItemReturn[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Cantidad.
		@param Cantidad Cantidad	  */
	public void setCantidad (BigDecimal Cantidad)
	{
		set_Value (COLUMNNAME_Cantidad, Cantidad);
	}

	/** Get Cantidad.
		@return Cantidad	  */
	public BigDecimal getCantidad () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cantidad);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set codigoarticulooriginal.
		@param codigoarticulooriginal codigoarticulooriginal	  */
	public void setcodigoarticulooriginal (String codigoarticulooriginal)
	{
		set_Value (COLUMNNAME_codigoarticulooriginal, codigoarticulooriginal);
	}

	/** Get codigoarticulooriginal.
		@return codigoarticulooriginal	  */
	public String getcodigoarticulooriginal () 
	{
		return (String)get_Value(COLUMNNAME_codigoarticulooriginal);
	}

	/** Set codigoartsubf.
		@param codigoartsubf codigoartsubf	  */
	public void setcodigoartsubf (String codigoartsubf)
	{
		set_Value (COLUMNNAME_codigoartsubf, codigoartsubf);
	}

	/** Get codigoartsubf.
		@return codigoartsubf	  */
	public String getcodigoartsubf () 
	{
		return (String)get_Value(COLUMNNAME_codigoartsubf);
	}

	/** Set codigosupervisora.
		@param codigosupervisora codigosupervisora	  */
	public void setcodigosupervisora (String codigosupervisora)
	{
		set_Value (COLUMNNAME_codigosupervisora, codigosupervisora);
	}

	/** Get codigosupervisora.
		@return codigosupervisora	  */
	public String getcodigosupervisora () 
	{
		return (String)get_Value(COLUMNNAME_codigosupervisora);
	}

	/** Set codigovendedor.
		@param codigovendedor codigovendedor	  */
	public void setcodigovendedor (String codigovendedor)
	{
		set_Value (COLUMNNAME_codigovendedor, codigovendedor);
	}

	/** Get codigovendedor.
		@return codigovendedor	  */
	public String getcodigovendedor () 
	{
		return (String)get_Value(COLUMNNAME_codigovendedor);
	}

	/** indicadorartsubf AD_Reference_ID=1000511 */
	public static final int INDICADORARTSUBF_AD_Reference_ID=1000511;
	/** Devolucion de articulo = A */
	public static final String INDICADORARTSUBF_DevolucionDeArticulo = "A";
	/** Devolucion de subfamilia = S */
	public static final String INDICADORARTSUBF_DevolucionDeSubfamilia = "S";
	/** Set indicadorartsubf.
		@param indicadorartsubf indicadorartsubf	  */
	public void setindicadorartsubf (String indicadorartsubf)
	{

		set_Value (COLUMNNAME_indicadorartsubf, indicadorartsubf);
	}

	/** Get indicadorartsubf.
		@return indicadorartsubf	  */
	public String getindicadorartsubf () 
	{
		return (String)get_Value(COLUMNNAME_indicadorartsubf);
	}

	/** Set iva.
		@param iva iva	  */
	public void setiva (BigDecimal iva)
	{
		set_Value (COLUMNNAME_iva, iva);
	}

	/** Get iva.
		@return iva	  */
	public BigDecimal getiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_iva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set lineacancelada.
		@param lineacancelada lineacancelada	  */
	public void setlineacancelada (int lineacancelada)
	{
		set_Value (COLUMNNAME_lineacancelada, Integer.valueOf(lineacancelada));
	}

	/** Get lineacancelada.
		@return lineacancelada	  */
	public int getlineacancelada () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_lineacancelada);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set modoingreso.
		@param modoingreso modoingreso	  */
	public void setmodoingreso (String modoingreso)
	{
		set_Value (COLUMNNAME_modoingreso, modoingreso);
	}

	/** Get modoingreso.
		@return modoingreso	  */
	public String getmodoingreso () 
	{
		return (String)get_Value(COLUMNNAME_modoingreso);
	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set precio.
		@param precio precio	  */
	public void setprecio (BigDecimal precio)
	{
		set_Value (COLUMNNAME_precio, precio);
	}

	/** Get precio.
		@return precio	  */
	public BigDecimal getprecio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_precio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_RT_Ticket_LineItemReturn.
		@param UY_RT_Ticket_LineItemReturn_ID UY_RT_Ticket_LineItemReturn	  */
	public void setUY_RT_Ticket_LineItemReturn_ID (int UY_RT_Ticket_LineItemReturn_ID)
	{
		if (UY_RT_Ticket_LineItemReturn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID, Integer.valueOf(UY_RT_Ticket_LineItemReturn_ID));
	}

	/** Get UY_RT_Ticket_LineItemReturn.
		@return UY_RT_Ticket_LineItemReturn	  */
	public int getUY_RT_Ticket_LineItemReturn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID);
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
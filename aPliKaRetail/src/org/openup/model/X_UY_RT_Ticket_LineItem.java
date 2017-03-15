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

/** Generated Model for UY_RT_Ticket_LineItem
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineItem extends PO implements I_UY_RT_Ticket_LineItem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160301L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineItem (Properties ctx, int UY_RT_Ticket_LineItem_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineItem_ID, trxName);
      /** if (UY_RT_Ticket_LineItem_ID == 0)
        {
			setUY_RT_Ticket_LineItem_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineItem (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineItem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set cantdescmanuales.
		@param cantdescmanuales cantdescmanuales	  */
	public void setcantdescmanuales (int cantdescmanuales)
	{
		set_Value (COLUMNNAME_cantdescmanuales, Integer.valueOf(cantdescmanuales));
	}

	/** Get cantdescmanuales.
		@return cantdescmanuales	  */
	public int getcantdescmanuales () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantdescmanuales);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set cantidadartobsequio.
		@param cantidadartobsequio cantidadartobsequio	  */
	public void setcantidadartobsequio (int cantidadartobsequio)
	{
		set_Value (COLUMNNAME_cantidadartobsequio, Integer.valueOf(cantidadartobsequio));
	}

	/** Get cantidadartobsequio.
		@return cantidadartobsequio	  */
	public int getcantidadartobsequio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantidadartobsequio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigoarticulo.
		@param codigoarticulo codigoarticulo	  */
	public void setcodigoarticulo (String codigoarticulo)
	{
		set_Value (COLUMNNAME_codigoarticulo, codigoarticulo);
	}

	/** Get codigoarticulo.
		@return codigoarticulo	  */
	public String getcodigoarticulo () 
	{
		return (String)get_Value(COLUMNNAME_codigoarticulo);
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

	/** Set codigoiva.
		@param codigoiva codigoiva	  */
	public void setcodigoiva (String codigoiva)
	{
		set_Value (COLUMNNAME_codigoiva, codigoiva);
	}

	/** Get codigoiva.
		@return codigoiva	  */
	public String getcodigoiva () 
	{
		return (String)get_Value(COLUMNNAME_codigoiva);
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

	/** Set color.
		@param color color	  */
	public void setcolor (String color)
	{
		set_Value (COLUMNNAME_color, color);
	}

	/** Get color.
		@return color	  */
	public String getcolor () 
	{
		return (String)get_Value(COLUMNNAME_color);
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

	/** Set ivadescuento.
		@param ivadescuento ivadescuento	  */
	public void setivadescuento (BigDecimal ivadescuento)
	{
		set_Value (COLUMNNAME_ivadescuento, ivadescuento);
	}

	/** Get ivadescuento.
		@return ivadescuento	  */
	public BigDecimal getivadescuento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuentocombo.
		@param ivadescuentocombo ivadescuentocombo	  */
	public void setivadescuentocombo (BigDecimal ivadescuentocombo)
	{
		set_Value (COLUMNNAME_ivadescuentocombo, ivadescuentocombo);
	}

	/** Get ivadescuentocombo.
		@return ivadescuentocombo	  */
	public BigDecimal getivadescuentocombo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuentocombo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuentomarca.
		@param ivadescuentomarca ivadescuentomarca	  */
	public void setivadescuentomarca (BigDecimal ivadescuentomarca)
	{
		set_Value (COLUMNNAME_ivadescuentomarca, ivadescuentomarca);
	}

	/** Get ivadescuentomarca.
		@return ivadescuentomarca	  */
	public BigDecimal getivadescuentomarca () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuentomarca);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuentototal.
		@param ivadescuentototal ivadescuentototal	  */
	public void setivadescuentototal (BigDecimal ivadescuentototal)
	{
		set_Value (COLUMNNAME_ivadescuentototal, ivadescuentototal);
	}

	/** Get ivadescuentototal.
		@return ivadescuentototal	  */
	public BigDecimal getivadescuentototal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuentototal);
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

	/** Set marca.
		@param marca marca	  */
	public void setmarca (String marca)
	{
		set_Value (COLUMNNAME_marca, marca);
	}

	/** Get marca.
		@return marca	  */
	public String getmarca () 
	{
		return (String)get_Value(COLUMNNAME_marca);
	}

	/** Set modelo.
		@param modelo modelo	  */
	public void setmodelo (String modelo)
	{
		set_Value (COLUMNNAME_modelo, modelo);
	}

	/** Get modelo.
		@return modelo	  */
	public String getmodelo () 
	{
		return (String)get_Value(COLUMNNAME_modelo);
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

	/** Set montorealdescfidel.
		@param montorealdescfidel montorealdescfidel	  */
	public void setmontorealdescfidel (BigDecimal montorealdescfidel)
	{
		set_Value (COLUMNNAME_montorealdescfidel, montorealdescfidel);
	}

	/** Get montorealdescfidel.
		@return montorealdescfidel	  */
	public BigDecimal getmontorealdescfidel () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montorealdescfidel);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

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

	/** Set nrolineaconvenio.
		@param nrolineaconvenio nrolineaconvenio	  */
	public void setnrolineaconvenio (int nrolineaconvenio)
	{
		set_Value (COLUMNNAME_nrolineaconvenio, Integer.valueOf(nrolineaconvenio));
	}

	/** Get nrolineaconvenio.
		@return nrolineaconvenio	  */
	public int getnrolineaconvenio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_nrolineaconvenio);
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

	/** Set preciodescuento.
		@param preciodescuento preciodescuento	  */
	public void setpreciodescuento (BigDecimal preciodescuento)
	{
		set_Value (COLUMNNAME_preciodescuento, preciodescuento);
	}

	/** Get preciodescuento.
		@return preciodescuento	  */
	public BigDecimal getpreciodescuento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciodescuentocombo.
		@param preciodescuentocombo preciodescuentocombo	  */
	public void setpreciodescuentocombo (BigDecimal preciodescuentocombo)
	{
		set_Value (COLUMNNAME_preciodescuentocombo, preciodescuentocombo);
	}

	/** Get preciodescuentocombo.
		@return preciodescuentocombo	  */
	public BigDecimal getpreciodescuentocombo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuentocombo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciodescuentomarca.
		@param preciodescuentomarca preciodescuentomarca	  */
	public void setpreciodescuentomarca (BigDecimal preciodescuentomarca)
	{
		set_Value (COLUMNNAME_preciodescuentomarca, preciodescuentomarca);
	}

	/** Get preciodescuentomarca.
		@return preciodescuentomarca	  */
	public BigDecimal getpreciodescuentomarca () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuentomarca);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciodescuentototal.
		@param preciodescuentototal preciodescuentototal	  */
	public void setpreciodescuentototal (BigDecimal preciodescuentototal)
	{
		set_Value (COLUMNNAME_preciodescuentototal, preciodescuentototal);
	}

	/** Get preciodescuentototal.
		@return preciodescuentototal	  */
	public BigDecimal getpreciodescuentototal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuentototal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciounitario.
		@param preciounitario preciounitario	  */
	public void setpreciounitario (BigDecimal preciounitario)
	{
		set_Value (COLUMNNAME_preciounitario, preciounitario);
	}

	/** Get preciounitario.
		@return preciounitario	  */
	public BigDecimal getpreciounitario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciounitario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PrecioUnitarioConIva.
		@param PrecioUnitarioConIva 
		Precio unitario del item (precio de lista que incluye IVA).
	  */
	public void setPrecioUnitarioConIva (BigDecimal PrecioUnitarioConIva)
	{
		set_Value (COLUMNNAME_PrecioUnitarioConIva, PrecioUnitarioConIva);
	}

	/** Get PrecioUnitarioConIva.
		@return Precio unitario del item (precio de lista que incluye IVA).
	  */
	public BigDecimal getPrecioUnitarioConIva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrecioUnitarioConIva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set puntosoferta.
		@param puntosoferta puntosoferta	  */
	public void setpuntosoferta (BigDecimal puntosoferta)
	{
		set_Value (COLUMNNAME_puntosoferta, puntosoferta);
	}

	/** Get puntosoferta.
		@return puntosoferta	  */
	public BigDecimal getpuntosoferta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_puntosoferta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set siaplicadescfidel.
		@param siaplicadescfidel siaplicadescfidel	  */
	public void setsiaplicadescfidel (String siaplicadescfidel)
	{
		set_Value (COLUMNNAME_siaplicadescfidel, siaplicadescfidel);
	}

	/** Get siaplicadescfidel.
		@return siaplicadescfidel	  */
	public String getsiaplicadescfidel () 
	{
		return (String)get_Value(COLUMNNAME_siaplicadescfidel);
	}

	/** Set siesconvenio.
		@param siesconvenio siesconvenio	  */
	public void setsiesconvenio (String siesconvenio)
	{
		set_Value (COLUMNNAME_siesconvenio, siesconvenio);
	}

	/** Get siesconvenio.
		@return siesconvenio	  */
	public String getsiesconvenio () 
	{
		return (String)get_Value(COLUMNNAME_siesconvenio);
	}

	/** Set siesobsequio.
		@param siesobsequio siesobsequio	  */
	public void setsiesobsequio (String siesobsequio)
	{
		set_Value (COLUMNNAME_siesobsequio, siesobsequio);
	}

	/** Get siesobsequio.
		@return siesobsequio	  */
	public String getsiesobsequio () 
	{
		return (String)get_Value(COLUMNNAME_siesobsequio);
	}

	/** Set siestandem.
		@param siestandem siestandem	  */
	public void setsiestandem (String siestandem)
	{
		set_Value (COLUMNNAME_siestandem, siestandem);
	}

	/** Get siestandem.
		@return siestandem	  */
	public String getsiestandem () 
	{
		return (String)get_Value(COLUMNNAME_siestandem);
	}

	/** Set talle.
		@param talle talle	  */
	public void settalle (String talle)
	{
		set_Value (COLUMNNAME_talle, talle);
	}

	/** Get talle.
		@return talle	  */
	public String gettalle () 
	{
		return (String)get_Value(COLUMNNAME_talle);
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

	public I_UY_RT_Ticket_CancelItem getUY_RT_Ticket_CancelItem() throws RuntimeException
    {
		return (I_UY_RT_Ticket_CancelItem)MTable.get(getCtx(), I_UY_RT_Ticket_CancelItem.Table_Name)
			.getPO(getUY_RT_Ticket_CancelItem_ID(), get_TrxName());	}

	/** Set UY_RT_Ticket_CancelItem.
		@param UY_RT_Ticket_CancelItem_ID UY_RT_Ticket_CancelItem	  */
	public void setUY_RT_Ticket_CancelItem_ID (int UY_RT_Ticket_CancelItem_ID)
	{
		if (UY_RT_Ticket_CancelItem_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Ticket_CancelItem_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Ticket_CancelItem_ID, Integer.valueOf(UY_RT_Ticket_CancelItem_ID));
	}

	/** Get UY_RT_Ticket_CancelItem.
		@return UY_RT_Ticket_CancelItem	  */
	public int getUY_RT_Ticket_CancelItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_CancelItem_ID);
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

	/** Set UY_RT_Ticket_LineItem.
		@param UY_RT_Ticket_LineItem_ID UY_RT_Ticket_LineItem	  */
	public void setUY_RT_Ticket_LineItem_ID (int UY_RT_Ticket_LineItem_ID)
	{
		if (UY_RT_Ticket_LineItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineItem_ID, Integer.valueOf(UY_RT_Ticket_LineItem_ID));
	}

	/** Get UY_RT_Ticket_LineItem.
		@return UY_RT_Ticket_LineItem	  */
	public int getUY_RT_Ticket_LineItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_Ticket_LineItemReturn getUY_RT_Ticket_LineItemReturn() throws RuntimeException
    {
		return (I_UY_RT_Ticket_LineItemReturn)MTable.get(getCtx(), I_UY_RT_Ticket_LineItemReturn.Table_Name)
			.getPO(getUY_RT_Ticket_LineItemReturn_ID(), get_TrxName());	}

	/** Set UY_RT_Ticket_LineItemReturn.
		@param UY_RT_Ticket_LineItemReturn_ID 
		Campo especifico para las lineas de itmem generadas por openup que hace referencia a una devolucion de articulo
	  */
	public void setUY_RT_Ticket_LineItemReturn_ID (int UY_RT_Ticket_LineItemReturn_ID)
	{
		if (UY_RT_Ticket_LineItemReturn_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Ticket_LineItemReturn_ID, Integer.valueOf(UY_RT_Ticket_LineItemReturn_ID));
	}

	/** Get UY_RT_Ticket_LineItemReturn.
		@return Campo especifico para las lineas de itmem generadas por openup que hace referencia a una devolucion de articulo
	  */
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
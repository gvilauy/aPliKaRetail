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

/** Generated Model for UY_RT_Ticket_LineVTarjTACRE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineVTarjTACRE extends PO implements I_UY_RT_Ticket_LineVTarjTACRE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151211L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineVTarjTACRE (Properties ctx, int UY_RT_Ticket_LineVTarjTACRE_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineVTarjTACRE_ID, trxName);
      /** if (UY_RT_Ticket_LineVTarjTACRE_ID == 0)
        {
			setUY_RT_Ticket_LineVTarjTACRE_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineVTarjTACRE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineVTarjTACRE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set autorizacion.
		@param autorizacion autorizacion	  */
	public void setautorizacion (String autorizacion)
	{
		set_Value (COLUMNNAME_autorizacion, autorizacion);
	}

	/** Get autorizacion.
		@return autorizacion	  */
	public String getautorizacion () 
	{
		return (String)get_Value(COLUMNNAME_autorizacion);
	}

	/** Set codigocaja.
		@param codigocaja codigocaja	  */
	public void setcodigocaja (String codigocaja)
	{
		set_Value (COLUMNNAME_codigocaja, codigocaja);
	}

	/** Get codigocaja.
		@return codigocaja	  */
	public String getcodigocaja () 
	{
		return (String)get_Value(COLUMNNAME_codigocaja);
	}

	/** Set codigocajera.
		@param codigocajera codigocajera	  */
	public void setcodigocajera (String codigocajera)
	{
		set_Value (COLUMNNAME_codigocajera, codigocajera);
	}

	/** Get codigocajera.
		@return codigocajera	  */
	public String getcodigocajera () 
	{
		return (String)get_Value(COLUMNNAME_codigocajera);
	}

	/** Set codigocomercio.
		@param codigocomercio codigocomercio	  */
	public void setcodigocomercio (String codigocomercio)
	{
		set_Value (COLUMNNAME_codigocomercio, codigocomercio);
	}

	/** Get codigocomercio.
		@return codigocomercio	  */
	public String getcodigocomercio () 
	{
		return (String)get_Value(COLUMNNAME_codigocomercio);
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

	/** Set codigoterminal.
		@param codigoterminal codigoterminal	  */
	public void setcodigoterminal (String codigoterminal)
	{
		set_Value (COLUMNNAME_codigoterminal, codigoterminal);
	}

	/** Get codigoterminal.
		@return codigoterminal	  */
	public String getcodigoterminal () 
	{
		return (String)get_Value(COLUMNNAME_codigoterminal);
	}

	/** Set comprobante.
		@param comprobante comprobante	  */
	public void setcomprobante (String comprobante)
	{
		set_Value (COLUMNNAME_comprobante, comprobante);
	}

	/** Get comprobante.
		@return comprobante	  */
	public String getcomprobante () 
	{
		return (String)get_Value(COLUMNNAME_comprobante);
	}

	/** Set descuentoiva.
		@param descuentoiva descuentoiva	  */
	public void setdescuentoiva (String descuentoiva)
	{
		set_Value (COLUMNNAME_descuentoiva, descuentoiva);
	}

	/** Get descuentoiva.
		@return descuentoiva	  */
	public String getdescuentoiva () 
	{
		return (String)get_Value(COLUMNNAME_descuentoiva);
	}

	/** Set fechatransaccion.
		@param fechatransaccion fechatransaccion	  */
	public void setfechatransaccion (Timestamp fechatransaccion)
	{
		set_Value (COLUMNNAME_fechatransaccion, fechatransaccion);
	}

	/** Get fechatransaccion.
		@return fechatransaccion	  */
	public Timestamp getfechatransaccion () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechatransaccion);
	}

	/** Set flagimprimefirma.
		@param flagimprimefirma flagimprimefirma	  */
	public void setflagimprimefirma (String flagimprimefirma)
	{
		set_Value (COLUMNNAME_flagimprimefirma, flagimprimefirma);
	}

	/** Get flagimprimefirma.
		@return flagimprimefirma	  */
	public String getflagimprimefirma () 
	{
		return (String)get_Value(COLUMNNAME_flagimprimefirma);
	}

	/** Set ImportePago.
		@param ImportePago 
		ImportePago
	  */
	public void setImportePago (BigDecimal ImportePago)
	{
		set_Value (COLUMNNAME_ImportePago, ImportePago);
	}

	/** Get ImportePago.
		@return ImportePago
	  */
	public BigDecimal getImportePago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ImportePago);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set montodescuentoleyiva.
		@param montodescuentoleyiva montodescuentoleyiva	  */
	public void setmontodescuentoleyiva (BigDecimal montodescuentoleyiva)
	{
		set_Value (COLUMNNAME_montodescuentoleyiva, montodescuentoleyiva);
	}

	/** Get montodescuentoleyiva.
		@return montodescuentoleyiva	  */
	public BigDecimal getmontodescuentoleyiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montodescuentoleyiva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set montofactura.
		@param montofactura montofactura	  */
	public void setmontofactura (BigDecimal montofactura)
	{
		set_Value (COLUMNNAME_montofactura, montofactura);
	}

	/** Get montofactura.
		@return montofactura	  */
	public BigDecimal getmontofactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montofactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set montogravado.
		@param montogravado montogravado	  */
	public void setmontogravado (BigDecimal montogravado)
	{
		set_Value (COLUMNNAME_montogravado, montogravado);
	}

	/** Get montogravado.
		@return montogravado	  */
	public BigDecimal getmontogravado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montogravado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set nombrepropuetario.
		@param nombrepropuetario nombrepropuetario	  */
	public void setnombrepropuetario (String nombrepropuetario)
	{
		set_Value (COLUMNNAME_nombrepropuetario, nombrepropuetario);
	}

	/** Get nombrepropuetario.
		@return nombrepropuetario	  */
	public String getnombrepropuetario () 
	{
		return (String)get_Value(COLUMNNAME_nombrepropuetario);
	}

	/** Set nombretarjeta.
		@param nombretarjeta nombretarjeta	  */
	public void setnombretarjeta (String nombretarjeta)
	{
		set_Value (COLUMNNAME_nombretarjeta, nombretarjeta);
	}

	/** Get nombretarjeta.
		@return nombretarjeta	  */
	public String getnombretarjeta () 
	{
		return (String)get_Value(COLUMNNAME_nombretarjeta);
	}

	/** Set NroLote.
		@param NroLote NroLote	  */
	public void setNroLote (String NroLote)
	{
		set_Value (COLUMNNAME_NroLote, NroLote);
	}

	/** Get NroLote.
		@return NroLote	  */
	public String getNroLote () 
	{
		return (String)get_Value(COLUMNNAME_NroLote);
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

	/** Set numerotarjeta.
		@param numerotarjeta numerotarjeta	  */
	public void setnumerotarjeta (String numerotarjeta)
	{
		set_Value (COLUMNNAME_numerotarjeta, numerotarjeta);
	}

	/** Get numerotarjeta.
		@return numerotarjeta	  */
	public String getnumerotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_numerotarjeta);
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

	/** Set siaplicaleydesciva.
		@param siaplicaleydesciva siaplicaleydesciva	  */
	public void setsiaplicaleydesciva (String siaplicaleydesciva)
	{
		set_Value (COLUMNNAME_siaplicaleydesciva, siaplicaleydesciva);
	}

	/** Get siaplicaleydesciva.
		@return siaplicaleydesciva	  */
	public String getsiaplicaleydesciva () 
	{
		return (String)get_Value(COLUMNNAME_siaplicaleydesciva);
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

	/** Set tipoautorizacion.
		@param tipoautorizacion tipoautorizacion	  */
	public void settipoautorizacion (String tipoautorizacion)
	{
		set_Value (COLUMNNAME_tipoautorizacion, tipoautorizacion);
	}

	/** Get tipoautorizacion.
		@return tipoautorizacion	  */
	public String gettipoautorizacion () 
	{
		return (String)get_Value(COLUMNNAME_tipoautorizacion);
	}

	/** Set tipoingreso.
		@param tipoingreso tipoingreso	  */
	public void settipoingreso (String tipoingreso)
	{
		set_Value (COLUMNNAME_tipoingreso, tipoingreso);
	}

	/** Get tipoingreso.
		@return tipoingreso	  */
	public String gettipoingreso () 
	{
		return (String)get_Value(COLUMNNAME_tipoingreso);
	}

	/** Set tipotransaccion.
		@param tipotransaccion tipotransaccion	  */
	public void settipotransaccion (String tipotransaccion)
	{
		set_Value (COLUMNNAME_tipotransaccion, tipotransaccion);
	}

	/** Get tipotransaccion.
		@return tipotransaccion	  */
	public String gettipotransaccion () 
	{
		return (String)get_Value(COLUMNNAME_tipotransaccion);
	}

	/** Set tipovaucher.
		@param tipovaucher tipovaucher	  */
	public void settipovaucher (String tipovaucher)
	{
		set_Value (COLUMNNAME_tipovaucher, tipovaucher);
	}

	/** Get tipovaucher.
		@return tipovaucher	  */
	public String gettipovaucher () 
	{
		return (String)get_Value(COLUMNNAME_tipovaucher);
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

	/** Set UY_RT_Ticket_LineVTarjTACRE.
		@param UY_RT_Ticket_LineVTarjTACRE_ID UY_RT_Ticket_LineVTarjTACRE	  */
	public void setUY_RT_Ticket_LineVTarjTACRE_ID (int UY_RT_Ticket_LineVTarjTACRE_ID)
	{
		if (UY_RT_Ticket_LineVTarjTACRE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineVTarjTACRE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineVTarjTACRE_ID, Integer.valueOf(UY_RT_Ticket_LineVTarjTACRE_ID));
	}

	/** Get UY_RT_Ticket_LineVTarjTACRE.
		@return UY_RT_Ticket_LineVTarjTACRE	  */
	public int getUY_RT_Ticket_LineVTarjTACRE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineVTarjTACRE_ID);
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

	/** Set Vencimiento.
		@param Vencimiento Vencimiento	  */
	public void setVencimiento (String Vencimiento)
	{
		set_Value (COLUMNNAME_Vencimiento, Vencimiento);
	}

	/** Get Vencimiento.
		@return Vencimiento	  */
	public String getVencimiento () 
	{
		return (String)get_Value(COLUMNNAME_Vencimiento);
	}
}
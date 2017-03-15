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

/** Generated Model for UY_RT_Ticket_LinePagoTACRE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LinePagoTACRE extends PO implements I_UY_RT_Ticket_LinePagoTACRE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151211L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LinePagoTACRE (Properties ctx, int UY_RT_Ticket_LinePagoTACRE_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LinePagoTACRE_ID, trxName);
      /** if (UY_RT_Ticket_LinePagoTACRE_ID == 0)
        {
			setUY_RT_Ticket_LinePagoTACRE_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LinePagoTACRE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LinePagoTACRE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set autorizasupervisora.
		@param autorizasupervisora autorizasupervisora	  */
	public void setautorizasupervisora (String autorizasupervisora)
	{
		set_Value (COLUMNNAME_autorizasupervisora, autorizasupervisora);
	}

	/** Get autorizasupervisora.
		@return autorizasupervisora	  */
	public String getautorizasupervisora () 
	{
		return (String)get_Value(COLUMNNAME_autorizasupervisora);
	}

	/** Set cambio.
		@param cambio cambio	  */
	public void setcambio (BigDecimal cambio)
	{
		set_Value (COLUMNNAME_cambio, cambio);
	}

	/** Get cambio.
		@return cambio	  */
	public BigDecimal getcambio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cambio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set lineacancelada.
		@param lineacancelada lineacancelada	  */
	public void setlineacancelada (String lineacancelada)
	{
		set_Value (COLUMNNAME_lineacancelada, lineacancelada);
	}

	/** Get lineacancelada.
		@return lineacancelada	  */
	public String getlineacancelada () 
	{
		return (String)get_Value(COLUMNNAME_lineacancelada);
	}

	/** Set lineaultimopago.
		@param lineaultimopago lineaultimopago	  */
	public void setlineaultimopago (String lineaultimopago)
	{
		set_Value (COLUMNNAME_lineaultimopago, lineaultimopago);
	}

	/** Get lineaultimopago.
		@return lineaultimopago	  */
	public String getlineaultimopago () 
	{
		return (String)get_Value(COLUMNNAME_lineaultimopago);
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

	/** Set numeroautorizacion.
		@param numeroautorizacion numeroautorizacion	  */
	public void setnumeroautorizacion (String numeroautorizacion)
	{
		set_Value (COLUMNNAME_numeroautorizacion, numeroautorizacion);
	}

	/** Get numeroautorizacion.
		@return numeroautorizacion	  */
	public String getnumeroautorizacion () 
	{
		return (String)get_Value(COLUMNNAME_numeroautorizacion);
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

	/** Set textoley.
		@param textoley textoley	  */
	public void settextoley (String textoley)
	{
		set_Value (COLUMNNAME_textoley, textoley);
	}

	/** Get textoley.
		@return textoley	  */
	public String gettextoley () 
	{
		return (String)get_Value(COLUMNNAME_textoley);
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

	/** Set tipooperacion.
		@param tipooperacion tipooperacion	  */
	public void settipooperacion (String tipooperacion)
	{
		set_Value (COLUMNNAME_tipooperacion, tipooperacion);
	}

	/** Get tipooperacion.
		@return tipooperacion	  */
	public String gettipooperacion () 
	{
		return (String)get_Value(COLUMNNAME_tipooperacion);
	}

	/** Set totalmediopagomonedareferencia.
		@param totalmediopagomonedareferencia totalmediopagomonedareferencia	  */
	public void settotalmediopagomonedareferencia (BigDecimal totalmediopagomonedareferencia)
	{
		set_Value (COLUMNNAME_totalmediopagomonedareferencia, totalmediopagomonedareferencia);
	}

	/** Get totalmediopagomonedareferencia.
		@return totalmediopagomonedareferencia	  */
	public BigDecimal gettotalmediopagomonedareferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalmediopagomonedareferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalmeidopagomoneda.
		@param totalmeidopagomoneda totalmeidopagomoneda	  */
	public void settotalmeidopagomoneda (BigDecimal totalmeidopagomoneda)
	{
		set_Value (COLUMNNAME_totalmeidopagomoneda, totalmeidopagomoneda);
	}

	/** Get totalmeidopagomoneda.
		@return totalmeidopagomoneda	  */
	public BigDecimal gettotalmeidopagomoneda () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalmeidopagomoneda);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalpagado.
		@param totalpagado totalpagado	  */
	public void settotalpagado (BigDecimal totalpagado)
	{
		set_Value (COLUMNNAME_totalpagado, totalpagado);
	}

	/** Get totalpagado.
		@return totalpagado	  */
	public BigDecimal gettotalpagado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalpagado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalpagadomonedareferencia.
		@param totalpagadomonedareferencia totalpagadomonedareferencia	  */
	public void settotalpagadomonedareferencia (BigDecimal totalpagadomonedareferencia)
	{
		set_Value (COLUMNNAME_totalpagadomonedareferencia, totalpagadomonedareferencia);
	}

	/** Get totalpagadomonedareferencia.
		@return totalpagadomonedareferencia	  */
	public BigDecimal gettotalpagadomonedareferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalpagadomonedareferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_RT_Ticket_LinePagoTACRE.
		@param UY_RT_Ticket_LinePagoTACRE_ID UY_RT_Ticket_LinePagoTACRE	  */
	public void setUY_RT_Ticket_LinePagoTACRE_ID (int UY_RT_Ticket_LinePagoTACRE_ID)
	{
		if (UY_RT_Ticket_LinePagoTACRE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LinePagoTACRE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LinePagoTACRE_ID, Integer.valueOf(UY_RT_Ticket_LinePagoTACRE_ID));
	}

	/** Get UY_RT_Ticket_LinePagoTACRE.
		@return UY_RT_Ticket_LinePagoTACRE	  */
	public int getUY_RT_Ticket_LinePagoTACRE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LinePagoTACRE_ID);
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
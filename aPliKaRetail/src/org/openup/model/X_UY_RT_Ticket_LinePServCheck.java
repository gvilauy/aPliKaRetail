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

/** Generated Model for UY_RT_Ticket_LinePServCheck
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LinePServCheck extends PO implements I_UY_RT_Ticket_LinePServCheck, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151202L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LinePServCheck (Properties ctx, int UY_RT_Ticket_LinePServCheck_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LinePServCheck_ID, trxName);
      /** if (UY_RT_Ticket_LinePServCheck_ID == 0)
        {
			setUY_RT_Ticket_LinePServCheck_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LinePServCheck (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LinePServCheck[")
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set idcliente.
		@param idcliente idcliente	  */
	public void setidcliente (String idcliente)
	{
		set_Value (COLUMNNAME_idcliente, idcliente);
	}

	/** Get idcliente.
		@return idcliente	  */
	public String getidcliente () 
	{
		return (String)get_Value(COLUMNNAME_idcliente);
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

	/** Set tipocliente.
		@param tipocliente tipocliente	  */
	public void settipocliente (String tipocliente)
	{
		set_Value (COLUMNNAME_tipocliente, tipocliente);
	}

	/** Get tipocliente.
		@return tipocliente	  */
	public String gettipocliente () 
	{
		return (String)get_Value(COLUMNNAME_tipocliente);
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

	/** Set totalmeidopago.
		@param totalmeidopago totalmeidopago	  */
	public void settotalmeidopago (BigDecimal totalmeidopago)
	{
		set_Value (COLUMNNAME_totalmeidopago, totalmeidopago);
	}

	/** Get totalmeidopago.
		@return totalmeidopago	  */
	public BigDecimal gettotalmeidopago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalmeidopago);
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

	/** Set UY_RT_Ticket_LinePServCheck.
		@param UY_RT_Ticket_LinePServCheck_ID UY_RT_Ticket_LinePServCheck	  */
	public void setUY_RT_Ticket_LinePServCheck_ID (int UY_RT_Ticket_LinePServCheck_ID)
	{
		if (UY_RT_Ticket_LinePServCheck_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LinePServCheck_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LinePServCheck_ID, Integer.valueOf(UY_RT_Ticket_LinePServCheck_ID));
	}

	/** Get UY_RT_Ticket_LinePServCheck.
		@return UY_RT_Ticket_LinePServCheck	  */
	public int getUY_RT_Ticket_LinePServCheck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LinePServCheck_ID);
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
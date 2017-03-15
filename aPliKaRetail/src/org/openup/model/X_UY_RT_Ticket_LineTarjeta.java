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

/** Generated Model for UY_RT_Ticket_LineTarjeta
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Ticket_LineTarjeta extends PO implements I_UY_RT_Ticket_LineTarjeta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151127L;

    /** Standard Constructor */
    public X_UY_RT_Ticket_LineTarjeta (Properties ctx, int UY_RT_Ticket_LineTarjeta_ID, String trxName)
    {
      super (ctx, UY_RT_Ticket_LineTarjeta_ID, trxName);
      /** if (UY_RT_Ticket_LineTarjeta_ID == 0)
        {
			setUY_RT_Ticket_LineTarjeta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Ticket_LineTarjeta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Ticket_LineTarjeta[")
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

	/** Set cuotastarjetacredito.
		@param cuotastarjetacredito cuotastarjetacredito	  */
	public void setcuotastarjetacredito (String cuotastarjetacredito)
	{
		set_Value (COLUMNNAME_cuotastarjetacredito, cuotastarjetacredito);
	}

	/** Get cuotastarjetacredito.
		@return cuotastarjetacredito	  */
	public String getcuotastarjetacredito () 
	{
		return (String)get_Value(COLUMNNAME_cuotastarjetacredito);
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

	/** Set NroComercio.
		@param NroComercio 
		NroComercio
	  */
	public void setNroComercio (String NroComercio)
	{
		set_Value (COLUMNNAME_NroComercio, NroComercio);
	}

	/** Get NroComercio.
		@return NroComercio
	  */
	public String getNroComercio () 
	{
		return (String)get_Value(COLUMNNAME_NroComercio);
	}

	/** Set numautorizaciontjtacred.
		@param numautorizaciontjtacred numautorizaciontjtacred	  */
	public void setnumautorizaciontjtacred (String numautorizaciontjtacred)
	{
		set_Value (COLUMNNAME_numautorizaciontjtacred, numautorizaciontjtacred);
	}

	/** Get numautorizaciontjtacred.
		@return numautorizaciontjtacred	  */
	public String getnumautorizaciontjtacred () 
	{
		return (String)get_Value(COLUMNNAME_numautorizaciontjtacred);
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

	/** Set numerotarjetacredito.
		@param numerotarjetacredito numerotarjetacredito	  */
	public void setnumerotarjetacredito (String numerotarjetacredito)
	{
		set_Value (COLUMNNAME_numerotarjetacredito, numerotarjetacredito);
	}

	/** Get numerotarjetacredito.
		@return numerotarjetacredito	  */
	public String getnumerotarjetacredito () 
	{
		return (String)get_Value(COLUMNNAME_numerotarjetacredito);
	}

	/** Set Plan.
		@param Plan 
		Plan utilizado por la tarjeta.
	  */
	public void setPlan (String Plan)
	{
		set_Value (COLUMNNAME_Plan, Plan);
	}

	/** Get Plan.
		@return Plan utilizado por la tarjeta.
	  */
	public String getPlan () 
	{
		return (String)get_Value(COLUMNNAME_Plan);
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

	/** Set SiEsDebitoCredito.
		@param SiEsDebitoCredito 
		Si es 1 indica que la tarjeta es de credito, si es 2 de débit y 0-no está especificado
	  */
	public void setSiEsDebitoCredito (String SiEsDebitoCredito)
	{
		set_Value (COLUMNNAME_SiEsDebitoCredito, SiEsDebitoCredito);
	}

	/** Get SiEsDebitoCredito.
		@return Si es 1 indica que la tarjeta es de credito, si es 2 de débit y 0-no está especificado
	  */
	public String getSiEsDebitoCredito () 
	{
		return (String)get_Value(COLUMNNAME_SiEsDebitoCredito);
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

	/** Set tipotarjetacredito.
		@param tipotarjetacredito tipotarjetacredito	  */
	public void settipotarjetacredito (String tipotarjetacredito)
	{
		set_Value (COLUMNNAME_tipotarjetacredito, tipotarjetacredito);
	}

	/** Get tipotarjetacredito.
		@return tipotarjetacredito	  */
	public String gettipotarjetacredito () 
	{
		return (String)get_Value(COLUMNNAME_tipotarjetacredito);
	}

	/** Set totalentregado.
		@param totalentregado totalentregado	  */
	public void settotalentregado (BigDecimal totalentregado)
	{
		set_Value (COLUMNNAME_totalentregado, totalentregado);
	}

	/** Get totalentregado.
		@return totalentregado	  */
	public BigDecimal gettotalentregado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalentregado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalentregadomonedareferencia.
		@param totalentregadomonedareferencia totalentregadomonedareferencia	  */
	public void settotalentregadomonedareferencia (BigDecimal totalentregadomonedareferencia)
	{
		set_Value (COLUMNNAME_totalentregadomonedareferencia, totalentregadomonedareferencia);
	}

	/** Get totalentregadomonedareferencia.
		@return totalentregadomonedareferencia	  */
	public BigDecimal gettotalentregadomonedareferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalentregadomonedareferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_RT_Ticket_LineTarjeta.
		@param UY_RT_Ticket_LineTarjeta_ID UY_RT_Ticket_LineTarjeta	  */
	public void setUY_RT_Ticket_LineTarjeta_ID (int UY_RT_Ticket_LineTarjeta_ID)
	{
		if (UY_RT_Ticket_LineTarjeta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineTarjeta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Ticket_LineTarjeta_ID, Integer.valueOf(UY_RT_Ticket_LineTarjeta_ID));
	}

	/** Get UY_RT_Ticket_LineTarjeta.
		@return UY_RT_Ticket_LineTarjeta	  */
	public int getUY_RT_Ticket_LineTarjeta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Ticket_LineTarjeta_ID);
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
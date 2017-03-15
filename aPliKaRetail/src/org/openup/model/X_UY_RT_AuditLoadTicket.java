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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_RT_AuditLoadTicket
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_AuditLoadTicket extends PO implements I_UY_RT_AuditLoadTicket, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151215L;

    /** Standard Constructor */
    public X_UY_RT_AuditLoadTicket (Properties ctx, int UY_RT_AuditLoadTicket_ID, String trxName)
    {
      super (ctx, UY_RT_AuditLoadTicket_ID, trxName);
      /** if (UY_RT_AuditLoadTicket_ID == 0)
        {
			setamtapgaveta (Env.ZERO);
			setamtcajera (Env.ZERO);
			setamtcanje (Env.ZERO);
			setamtconsulta (Env.ZERO);
			setamtcreditodevolucion (Env.ZERO);
			setamtdevolucion (Env.ZERO);
			setamtestadocta (Env.ZERO);
			setamtexentoiva (Env.ZERO);
			setamtfactura (Env.ZERO);
			setamtfondeo (Env.ZERO);
			setamtgift (Env.ZERO);
			setamtingresopersonal (Env.ZERO);
			setamtinventario (Env.ZERO);
			setamtnodefinido (Env.ZERO);
			setamtnogenerado (Env.ZERO);
			setamtpagocaja (Env.ZERO);
			setamtpedido (Env.ZERO);
			setamtretiro (Env.ZERO);
			setamtventas (Env.ZERO);
			setamtz (Env.ZERO);
			setName (null);
			setUY_RT_AuditLoadTicket_ID (0);
			setUY_RT_LoadTicket_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_AuditLoadTicket (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_AuditLoadTicket[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtapgaveta.
		@param amtapgaveta amtapgaveta	  */
	public void setamtapgaveta (BigDecimal amtapgaveta)
	{
		set_Value (COLUMNNAME_amtapgaveta, amtapgaveta);
	}

	/** Get amtapgaveta.
		@return amtapgaveta	  */
	public BigDecimal getamtapgaveta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtapgaveta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcajera.
		@param amtcajera amtcajera	  */
	public void setamtcajera (BigDecimal amtcajera)
	{
		set_Value (COLUMNNAME_amtcajera, amtcajera);
	}

	/** Get amtcajera.
		@return amtcajera	  */
	public BigDecimal getamtcajera () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcajera);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcanje.
		@param amtcanje amtcanje	  */
	public void setamtcanje (BigDecimal amtcanje)
	{
		set_Value (COLUMNNAME_amtcanje, amtcanje);
	}

	/** Get amtcanje.
		@return amtcanje	  */
	public BigDecimal getamtcanje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcanje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtconsulta.
		@param amtconsulta amtconsulta	  */
	public void setamtconsulta (BigDecimal amtconsulta)
	{
		set_Value (COLUMNNAME_amtconsulta, amtconsulta);
	}

	/** Get amtconsulta.
		@return amtconsulta	  */
	public BigDecimal getamtconsulta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtconsulta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcreditodevolucion.
		@param amtcreditodevolucion amtcreditodevolucion	  */
	public void setamtcreditodevolucion (BigDecimal amtcreditodevolucion)
	{
		set_Value (COLUMNNAME_amtcreditodevolucion, amtcreditodevolucion);
	}

	/** Get amtcreditodevolucion.
		@return amtcreditodevolucion	  */
	public BigDecimal getamtcreditodevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcreditodevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtdevolucion.
		@param amtdevolucion amtdevolucion	  */
	public void setamtdevolucion (BigDecimal amtdevolucion)
	{
		set_Value (COLUMNNAME_amtdevolucion, amtdevolucion);
	}

	/** Get amtdevolucion.
		@return amtdevolucion	  */
	public BigDecimal getamtdevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtestadocta.
		@param amtestadocta amtestadocta	  */
	public void setamtestadocta (BigDecimal amtestadocta)
	{
		set_Value (COLUMNNAME_amtestadocta, amtestadocta);
	}

	/** Get amtestadocta.
		@return amtestadocta	  */
	public BigDecimal getamtestadocta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtestadocta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtexentoiva.
		@param amtexentoiva amtexentoiva	  */
	public void setamtexentoiva (BigDecimal amtexentoiva)
	{
		set_Value (COLUMNNAME_amtexentoiva, amtexentoiva);
	}

	/** Get amtexentoiva.
		@return amtexentoiva	  */
	public BigDecimal getamtexentoiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtexentoiva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtfactura.
		@param amtfactura amtfactura	  */
	public void setamtfactura (BigDecimal amtfactura)
	{
		set_Value (COLUMNNAME_amtfactura, amtfactura);
	}

	/** Get amtfactura.
		@return amtfactura	  */
	public BigDecimal getamtfactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtfactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtfondeo.
		@param amtfondeo amtfondeo	  */
	public void setamtfondeo (BigDecimal amtfondeo)
	{
		set_Value (COLUMNNAME_amtfondeo, amtfondeo);
	}

	/** Get amtfondeo.
		@return amtfondeo	  */
	public BigDecimal getamtfondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtfondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtgift.
		@param amtgift amtgift	  */
	public void setamtgift (BigDecimal amtgift)
	{
		set_Value (COLUMNNAME_amtgift, amtgift);
	}

	/** Get amtgift.
		@return amtgift	  */
	public BigDecimal getamtgift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtgift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtingresopersonal.
		@param amtingresopersonal amtingresopersonal	  */
	public void setamtingresopersonal (BigDecimal amtingresopersonal)
	{
		set_Value (COLUMNNAME_amtingresopersonal, amtingresopersonal);
	}

	/** Get amtingresopersonal.
		@return amtingresopersonal	  */
	public BigDecimal getamtingresopersonal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtingresopersonal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtinventario.
		@param amtinventario amtinventario	  */
	public void setamtinventario (BigDecimal amtinventario)
	{
		set_Value (COLUMNNAME_amtinventario, amtinventario);
	}

	/** Get amtinventario.
		@return amtinventario	  */
	public BigDecimal getamtinventario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtinventario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtnodefinido.
		@param amtnodefinido amtnodefinido	  */
	public void setamtnodefinido (BigDecimal amtnodefinido)
	{
		set_Value (COLUMNNAME_amtnodefinido, amtnodefinido);
	}

	/** Get amtnodefinido.
		@return amtnodefinido	  */
	public BigDecimal getamtnodefinido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtnodefinido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtnogenerado.
		@param amtnogenerado amtnogenerado	  */
	public void setamtnogenerado (BigDecimal amtnogenerado)
	{
		set_Value (COLUMNNAME_amtnogenerado, amtnogenerado);
	}

	/** Get amtnogenerado.
		@return amtnogenerado	  */
	public BigDecimal getamtnogenerado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtnogenerado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtpagocaja.
		@param amtpagocaja amtpagocaja	  */
	public void setamtpagocaja (BigDecimal amtpagocaja)
	{
		set_Value (COLUMNNAME_amtpagocaja, amtpagocaja);
	}

	/** Get amtpagocaja.
		@return amtpagocaja	  */
	public BigDecimal getamtpagocaja () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtpagocaja);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtpedido.
		@param amtpedido amtpedido	  */
	public void setamtpedido (BigDecimal amtpedido)
	{
		set_Value (COLUMNNAME_amtpedido, amtpedido);
	}

	/** Get amtpedido.
		@return amtpedido	  */
	public BigDecimal getamtpedido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtpedido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtretiro.
		@param amtretiro amtretiro	  */
	public void setamtretiro (BigDecimal amtretiro)
	{
		set_Value (COLUMNNAME_amtretiro, amtretiro);
	}

	/** Get amtretiro.
		@return amtretiro	  */
	public BigDecimal getamtretiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtretiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtventas.
		@param amtventas amtventas	  */
	public void setamtventas (BigDecimal amtventas)
	{
		set_Value (COLUMNNAME_amtventas, amtventas);
	}

	/** Get amtventas.
		@return amtventas	  */
	public BigDecimal getamtventas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtventas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtacheque.
		@param amtvtacheque amtvtacheque	  */
	public void setamtvtacheque (BigDecimal amtvtacheque)
	{
		set_Value (COLUMNNAME_amtvtacheque, amtvtacheque);
	}

	/** Get amtvtacheque.
		@return amtvtacheque	  */
	public BigDecimal getamtvtacheque () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtacheque);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtachequecobranza.
		@param amtvtachequecobranza amtvtachequecobranza	  */
	public void setamtvtachequecobranza (BigDecimal amtvtachequecobranza)
	{
		set_Value (COLUMNNAME_amtvtachequecobranza, amtvtachequecobranza);
	}

	/** Get amtvtachequecobranza.
		@return amtvtachequecobranza	  */
	public BigDecimal getamtvtachequecobranza () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtachequecobranza);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtachequedlrs.
		@param amtvtachequedlrs amtvtachequedlrs	  */
	public void setamtvtachequedlrs (BigDecimal amtvtachequedlrs)
	{
		set_Value (COLUMNNAME_amtvtachequedlrs, amtvtachequedlrs);
	}

	/** Get amtvtachequedlrs.
		@return amtvtachequedlrs	  */
	public BigDecimal getamtvtachequedlrs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtachequedlrs);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtaclientesfidelizacion.
		@param amtvtaclientesfidelizacion amtvtaclientesfidelizacion	  */
	public void setamtvtaclientesfidelizacion (BigDecimal amtvtaclientesfidelizacion)
	{
		set_Value (COLUMNNAME_amtvtaclientesfidelizacion, amtvtaclientesfidelizacion);
	}

	/** Get amtvtaclientesfidelizacion.
		@return amtvtaclientesfidelizacion	  */
	public BigDecimal getamtvtaclientesfidelizacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtaclientesfidelizacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVtaCredito.
		@param AmtVtaCredito AmtVtaCredito	  */
	public void setAmtVtaCredito (BigDecimal AmtVtaCredito)
	{
		set_Value (COLUMNNAME_AmtVtaCredito, AmtVtaCredito);
	}

	/** Get AmtVtaCredito.
		@return AmtVtaCredito	  */
	public BigDecimal getAmtVtaCredito () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVtaCredito);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVtaCreditoDlrs.
		@param AmtVtaCreditoDlrs AmtVtaCreditoDlrs	  */
	public void setAmtVtaCreditoDlrs (BigDecimal AmtVtaCreditoDlrs)
	{
		set_Value (COLUMNNAME_AmtVtaCreditoDlrs, AmtVtaCreditoDlrs);
	}

	/** Get AmtVtaCreditoDlrs.
		@return AmtVtaCreditoDlrs	  */
	public BigDecimal getAmtVtaCreditoDlrs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVtaCreditoDlrs);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtadevenvases.
		@param amtvtadevenvases amtvtadevenvases	  */
	public void setamtvtadevenvases (BigDecimal amtvtadevenvases)
	{
		set_Value (COLUMNNAME_amtvtadevenvases, amtvtadevenvases);
	}

	/** Get amtvtadevenvases.
		@return amtvtadevenvases	  */
	public BigDecimal getamtvtadevenvases () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtadevenvases);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtaefectivo.
		@param amtvtaefectivo amtvtaefectivo	  */
	public void setamtvtaefectivo (BigDecimal amtvtaefectivo)
	{
		set_Value (COLUMNNAME_amtvtaefectivo, amtvtaefectivo);
	}

	/** Get amtvtaefectivo.
		@return amtvtaefectivo	  */
	public BigDecimal getamtvtaefectivo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtaefectivo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtaefectivodolares.
		@param amtvtaefectivodolares amtvtaefectivodolares	  */
	public void setamtvtaefectivodolares (BigDecimal amtvtaefectivodolares)
	{
		set_Value (COLUMNNAME_amtvtaefectivodolares, amtvtaefectivodolares);
	}

	/** Get amtvtaefectivodolares.
		@return amtvtaefectivodolares	  */
	public BigDecimal getamtvtaefectivodolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtaefectivodolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtaefectivosodexo.
		@param amtvtaefectivosodexo amtvtaefectivosodexo	  */
	public void setamtvtaefectivosodexo (BigDecimal amtvtaefectivosodexo)
	{
		set_Value (COLUMNNAME_amtvtaefectivosodexo, amtvtaefectivosodexo);
	}

	/** Get amtvtaefectivosodexo.
		@return amtvtaefectivosodexo	  */
	public BigDecimal getamtvtaefectivosodexo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtaefectivosodexo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtafacturas2.
		@param amtvtafacturas2 amtvtafacturas2	  */
	public void setamtvtafacturas2 (BigDecimal amtvtafacturas2)
	{
		set_Value (COLUMNNAME_amtvtafacturas2, amtvtafacturas2);
	}

	/** Get amtvtafacturas2.
		@return amtvtafacturas2	  */
	public BigDecimal getamtvtafacturas2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtafacturas2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtafondeo.
		@param amtvtafondeo amtvtafondeo	  */
	public void setamtvtafondeo (BigDecimal amtvtafondeo)
	{
		set_Value (COLUMNNAME_amtvtafondeo, amtvtafondeo);
	}

	/** Get amtvtafondeo.
		@return amtvtafondeo	  */
	public BigDecimal getamtvtafondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtafondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtaluncheon.
		@param amtvtaluncheon amtvtaluncheon	  */
	public void setamtvtaluncheon (BigDecimal amtvtaluncheon)
	{
		set_Value (COLUMNNAME_amtvtaluncheon, amtvtaluncheon);
	}

	/** Get amtvtaluncheon.
		@return amtvtaluncheon	  */
	public BigDecimal getamtvtaluncheon () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtaluncheon);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtapagodeservicio.
		@param amtvtapagodeservicio amtvtapagodeservicio	  */
	public void setamtvtapagodeservicio (BigDecimal amtvtapagodeservicio)
	{
		set_Value (COLUMNNAME_amtvtapagodeservicio, amtvtapagodeservicio);
	}

	/** Get amtvtapagodeservicio.
		@return amtvtapagodeservicio	  */
	public BigDecimal getamtvtapagodeservicio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtapagodeservicio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtaretiro.
		@param amtvtaretiro amtvtaretiro	  */
	public void setamtvtaretiro (BigDecimal amtvtaretiro)
	{
		set_Value (COLUMNNAME_amtvtaretiro, amtvtaretiro);
	}

	/** Get amtvtaretiro.
		@return amtvtaretiro	  */
	public BigDecimal getamtvtaretiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtaretiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatarjeta.
		@param amtvtatarjeta amtvtatarjeta	  */
	public void setamtvtatarjeta (BigDecimal amtvtatarjeta)
	{
		set_Value (COLUMNNAME_amtvtatarjeta, amtvtatarjeta);
	}

	/** Get amtvtatarjeta.
		@return amtvtatarjeta	  */
	public BigDecimal getamtvtatarjeta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatarjeta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatarjetacuota.
		@param amtvtatarjetacuota amtvtatarjetacuota	  */
	public void setamtvtatarjetacuota (BigDecimal amtvtatarjetacuota)
	{
		set_Value (COLUMNNAME_amtvtatarjetacuota, amtvtatarjetacuota);
	}

	/** Get amtvtatarjetacuota.
		@return amtvtatarjetacuota	  */
	public BigDecimal getamtvtatarjetacuota () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatarjetacuota);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatarjetadlrs.
		@param amtvtatarjetadlrs amtvtatarjetadlrs	  */
	public void setamtvtatarjetadlrs (BigDecimal amtvtatarjetadlrs)
	{
		set_Value (COLUMNNAME_amtvtatarjetadlrs, amtvtatarjetadlrs);
	}

	/** Get amtvtatarjetadlrs.
		@return amtvtatarjetadlrs	  */
	public BigDecimal getamtvtatarjetadlrs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatarjetadlrs);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatarjetaofline.
		@param amtvtatarjetaofline amtvtatarjetaofline	  */
	public void setamtvtatarjetaofline (BigDecimal amtvtatarjetaofline)
	{
		set_Value (COLUMNNAME_amtvtatarjetaofline, amtvtatarjetaofline);
	}

	/** Get amtvtatarjetaofline.
		@return amtvtatarjetaofline	  */
	public BigDecimal getamtvtatarjetaofline () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatarjetaofline);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatktalimentacion.
		@param amtvtatktalimentacion amtvtatktalimentacion	  */
	public void setamtvtatktalimentacion (BigDecimal amtvtatktalimentacion)
	{
		set_Value (COLUMNNAME_amtvtatktalimentacion, amtvtatktalimentacion);
	}

	/** Get amtvtatktalimentacion.
		@return amtvtatktalimentacion	  */
	public BigDecimal getamtvtatktalimentacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatktalimentacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtz.
		@param amtz amtz	  */
	public void setamtz (BigDecimal amtz)
	{
		set_Value (COLUMNNAME_amtz, amtz);
	}

	/** Get amtz.
		@return amtz	  */
	public BigDecimal getamtz () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtz);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (String codigo)
	{
		set_Value (COLUMNNAME_codigo, codigo);
	}

	/** Get codigo.
		@return codigo	  */
	public String getcodigo () 
	{
		return (String)get_Value(COLUMNNAME_codigo);
	}

	/** Set Valuation Date.
		@param DateValue 
		Date of valuation
	  */
	public void setDateValue (Timestamp DateValue)
	{
		set_Value (COLUMNNAME_DateValue, DateValue);
	}

	/** Get Valuation Date.
		@return Date of valuation
	  */
	public Timestamp getDateValue () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValue);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set path.
		@param path path	  */
	public void setpath (String path)
	{
		set_Value (COLUMNNAME_path, path);
	}

	/** Get path.
		@return path	  */
	public String getpath () 
	{
		return (String)get_Value(COLUMNNAME_path);
	}

	/** Set qtytiketapgaveta.
		@param qtytiketapgaveta qtytiketapgaveta	  */
	public void setqtytiketapgaveta (BigDecimal qtytiketapgaveta)
	{
		set_Value (COLUMNNAME_qtytiketapgaveta, qtytiketapgaveta);
	}

	/** Get qtytiketapgaveta.
		@return qtytiketapgaveta	  */
	public BigDecimal getqtytiketapgaveta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketapgaveta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketcajera.
		@param qtytiketcajera qtytiketcajera	  */
	public void setqtytiketcajera (BigDecimal qtytiketcajera)
	{
		set_Value (COLUMNNAME_qtytiketcajera, qtytiketcajera);
	}

	/** Get qtytiketcajera.
		@return qtytiketcajera	  */
	public BigDecimal getqtytiketcajera () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketcajera);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketcanje.
		@param qtytiketcanje qtytiketcanje	  */
	public void setqtytiketcanje (BigDecimal qtytiketcanje)
	{
		set_Value (COLUMNNAME_qtytiketcanje, qtytiketcanje);
	}

	/** Get qtytiketcanje.
		@return qtytiketcanje	  */
	public BigDecimal getqtytiketcanje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketcanje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketconsulta.
		@param qtytiketconsulta qtytiketconsulta	  */
	public void setqtytiketconsulta (BigDecimal qtytiketconsulta)
	{
		set_Value (COLUMNNAME_qtytiketconsulta, qtytiketconsulta);
	}

	/** Get qtytiketconsulta.
		@return qtytiketconsulta	  */
	public BigDecimal getqtytiketconsulta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketconsulta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketcreditodevolucion.
		@param qtytiketcreditodevolucion qtytiketcreditodevolucion	  */
	public void setqtytiketcreditodevolucion (BigDecimal qtytiketcreditodevolucion)
	{
		set_Value (COLUMNNAME_qtytiketcreditodevolucion, qtytiketcreditodevolucion);
	}

	/** Get qtytiketcreditodevolucion.
		@return qtytiketcreditodevolucion	  */
	public BigDecimal getqtytiketcreditodevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketcreditodevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketdevolucion.
		@param qtytiketdevolucion qtytiketdevolucion	  */
	public void setqtytiketdevolucion (BigDecimal qtytiketdevolucion)
	{
		set_Value (COLUMNNAME_qtytiketdevolucion, qtytiketdevolucion);
	}

	/** Get qtytiketdevolucion.
		@return qtytiketdevolucion	  */
	public BigDecimal getqtytiketdevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketdevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketestadocta.
		@param qtytiketestadocta qtytiketestadocta	  */
	public void setqtytiketestadocta (BigDecimal qtytiketestadocta)
	{
		set_Value (COLUMNNAME_qtytiketestadocta, qtytiketestadocta);
	}

	/** Get qtytiketestadocta.
		@return qtytiketestadocta	  */
	public BigDecimal getqtytiketestadocta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketestadocta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketexentoiva.
		@param qtytiketexentoiva qtytiketexentoiva	  */
	public void setqtytiketexentoiva (BigDecimal qtytiketexentoiva)
	{
		set_Value (COLUMNNAME_qtytiketexentoiva, qtytiketexentoiva);
	}

	/** Get qtytiketexentoiva.
		@return qtytiketexentoiva	  */
	public BigDecimal getqtytiketexentoiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketexentoiva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketfactura.
		@param qtytiketfactura qtytiketfactura	  */
	public void setqtytiketfactura (BigDecimal qtytiketfactura)
	{
		set_Value (COLUMNNAME_qtytiketfactura, qtytiketfactura);
	}

	/** Get qtytiketfactura.
		@return qtytiketfactura	  */
	public BigDecimal getqtytiketfactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketfactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketfondeo.
		@param qtytiketfondeo qtytiketfondeo	  */
	public void setqtytiketfondeo (BigDecimal qtytiketfondeo)
	{
		set_Value (COLUMNNAME_qtytiketfondeo, qtytiketfondeo);
	}

	/** Get qtytiketfondeo.
		@return qtytiketfondeo	  */
	public BigDecimal getqtytiketfondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketfondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketgift.
		@param qtytiketgift qtytiketgift	  */
	public void setqtytiketgift (BigDecimal qtytiketgift)
	{
		set_Value (COLUMNNAME_qtytiketgift, qtytiketgift);
	}

	/** Get qtytiketgift.
		@return qtytiketgift	  */
	public BigDecimal getqtytiketgift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketgift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketingresopersonal.
		@param qtytiketingresopersonal qtytiketingresopersonal	  */
	public void setqtytiketingresopersonal (BigDecimal qtytiketingresopersonal)
	{
		set_Value (COLUMNNAME_qtytiketingresopersonal, qtytiketingresopersonal);
	}

	/** Get qtytiketingresopersonal.
		@return qtytiketingresopersonal	  */
	public BigDecimal getqtytiketingresopersonal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketingresopersonal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketinventario.
		@param qtytiketinventario qtytiketinventario	  */
	public void setqtytiketinventario (BigDecimal qtytiketinventario)
	{
		set_Value (COLUMNNAME_qtytiketinventario, qtytiketinventario);
	}

	/** Get qtytiketinventario.
		@return qtytiketinventario	  */
	public BigDecimal getqtytiketinventario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketinventario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketnodefinido.
		@param qtytiketnodefinido qtytiketnodefinido	  */
	public void setqtytiketnodefinido (BigDecimal qtytiketnodefinido)
	{
		set_Value (COLUMNNAME_qtytiketnodefinido, qtytiketnodefinido);
	}

	/** Get qtytiketnodefinido.
		@return qtytiketnodefinido	  */
	public BigDecimal getqtytiketnodefinido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketnodefinido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketnogenerado.
		@param qtytiketnogenerado qtytiketnogenerado	  */
	public void setqtytiketnogenerado (BigDecimal qtytiketnogenerado)
	{
		set_Value (COLUMNNAME_qtytiketnogenerado, qtytiketnogenerado);
	}

	/** Get qtytiketnogenerado.
		@return qtytiketnogenerado	  */
	public BigDecimal getqtytiketnogenerado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketnogenerado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketpagocaja.
		@param qtytiketpagocaja qtytiketpagocaja	  */
	public void setqtytiketpagocaja (BigDecimal qtytiketpagocaja)
	{
		set_Value (COLUMNNAME_qtytiketpagocaja, qtytiketpagocaja);
	}

	/** Get qtytiketpagocaja.
		@return qtytiketpagocaja	  */
	public BigDecimal getqtytiketpagocaja () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketpagocaja);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketpedido.
		@param qtytiketpedido qtytiketpedido	  */
	public void setqtytiketpedido (BigDecimal qtytiketpedido)
	{
		set_Value (COLUMNNAME_qtytiketpedido, qtytiketpedido);
	}

	/** Get qtytiketpedido.
		@return qtytiketpedido	  */
	public BigDecimal getqtytiketpedido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketpedido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketretiro.
		@param qtytiketretiro qtytiketretiro	  */
	public void setqtytiketretiro (BigDecimal qtytiketretiro)
	{
		set_Value (COLUMNNAME_qtytiketretiro, qtytiketretiro);
	}

	/** Get qtytiketretiro.
		@return qtytiketretiro	  */
	public BigDecimal getqtytiketretiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketretiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketventas.
		@param qtytiketventas qtytiketventas	  */
	public void setqtytiketventas (BigDecimal qtytiketventas)
	{
		set_Value (COLUMNNAME_qtytiketventas, qtytiketventas);
	}

	/** Get qtytiketventas.
		@return qtytiketventas	  */
	public BigDecimal getqtytiketventas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketventas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtytiketz.
		@param qtytiketz qtytiketz	  */
	public void setqtytiketz (BigDecimal qtytiketz)
	{
		set_Value (COLUMNNAME_qtytiketz, qtytiketz);
	}

	/** Get qtytiketz.
		@return qtytiketz	  */
	public BigDecimal getqtytiketz () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtytiketz);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtacheque.
		@param qtyvtacheque qtyvtacheque	  */
	public void setqtyvtacheque (BigDecimal qtyvtacheque)
	{
		set_Value (COLUMNNAME_qtyvtacheque, qtyvtacheque);
	}

	/** Get qtyvtacheque.
		@return qtyvtacheque	  */
	public BigDecimal getqtyvtacheque () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtacheque);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtachequecobranza.
		@param qtyvtachequecobranza qtyvtachequecobranza	  */
	public void setqtyvtachequecobranza (BigDecimal qtyvtachequecobranza)
	{
		set_Value (COLUMNNAME_qtyvtachequecobranza, qtyvtachequecobranza);
	}

	/** Get qtyvtachequecobranza.
		@return qtyvtachequecobranza	  */
	public BigDecimal getqtyvtachequecobranza () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtachequecobranza);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtaclientesfidelizacion.
		@param qtyvtaclientesfidelizacion qtyvtaclientesfidelizacion	  */
	public void setqtyvtaclientesfidelizacion (BigDecimal qtyvtaclientesfidelizacion)
	{
		set_Value (COLUMNNAME_qtyvtaclientesfidelizacion, qtyvtaclientesfidelizacion);
	}

	/** Get qtyvtaclientesfidelizacion.
		@return qtyvtaclientesfidelizacion	  */
	public BigDecimal getqtyvtaclientesfidelizacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtaclientesfidelizacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtadevenvases.
		@param qtyvtadevenvases qtyvtadevenvases	  */
	public void setqtyvtadevenvases (BigDecimal qtyvtadevenvases)
	{
		set_Value (COLUMNNAME_qtyvtadevenvases, qtyvtadevenvases);
	}

	/** Get qtyvtadevenvases.
		@return qtyvtadevenvases	  */
	public BigDecimal getqtyvtadevenvases () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtadevenvases);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtaefectivo.
		@param qtyvtaefectivo qtyvtaefectivo	  */
	public void setqtyvtaefectivo (BigDecimal qtyvtaefectivo)
	{
		set_Value (COLUMNNAME_qtyvtaefectivo, qtyvtaefectivo);
	}

	/** Get qtyvtaefectivo.
		@return qtyvtaefectivo	  */
	public BigDecimal getqtyvtaefectivo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtaefectivo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtaefectivodolares.
		@param qtyvtaefectivodolares qtyvtaefectivodolares	  */
	public void setqtyvtaefectivodolares (BigDecimal qtyvtaefectivodolares)
	{
		set_Value (COLUMNNAME_qtyvtaefectivodolares, qtyvtaefectivodolares);
	}

	/** Get qtyvtaefectivodolares.
		@return qtyvtaefectivodolares	  */
	public BigDecimal getqtyvtaefectivodolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtaefectivodolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtaefectivosodexo.
		@param qtyvtaefectivosodexo qtyvtaefectivosodexo	  */
	public void setqtyvtaefectivosodexo (BigDecimal qtyvtaefectivosodexo)
	{
		set_Value (COLUMNNAME_qtyvtaefectivosodexo, qtyvtaefectivosodexo);
	}

	/** Get qtyvtaefectivosodexo.
		@return qtyvtaefectivosodexo	  */
	public BigDecimal getqtyvtaefectivosodexo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtaefectivosodexo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtafacturas2.
		@param qtyvtafacturas2 qtyvtafacturas2	  */
	public void setqtyvtafacturas2 (BigDecimal qtyvtafacturas2)
	{
		set_Value (COLUMNNAME_qtyvtafacturas2, qtyvtafacturas2);
	}

	/** Get qtyvtafacturas2.
		@return qtyvtafacturas2	  */
	public BigDecimal getqtyvtafacturas2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtafacturas2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtafondeo.
		@param qtyvtafondeo qtyvtafondeo	  */
	public void setqtyvtafondeo (BigDecimal qtyvtafondeo)
	{
		set_Value (COLUMNNAME_qtyvtafondeo, qtyvtafondeo);
	}

	/** Get qtyvtafondeo.
		@return qtyvtafondeo	  */
	public BigDecimal getqtyvtafondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtafondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtaluncheon.
		@param qtyvtaluncheon qtyvtaluncheon	  */
	public void setqtyvtaluncheon (BigDecimal qtyvtaluncheon)
	{
		set_Value (COLUMNNAME_qtyvtaluncheon, qtyvtaluncheon);
	}

	/** Get qtyvtaluncheon.
		@return qtyvtaluncheon	  */
	public BigDecimal getqtyvtaluncheon () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtaluncheon);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtapagodeservici.
		@param qtyvtapagodeservici qtyvtapagodeservici	  */
	public void setqtyvtapagodeservici (BigDecimal qtyvtapagodeservici)
	{
		set_Value (COLUMNNAME_qtyvtapagodeservici, qtyvtapagodeservici);
	}

	/** Get qtyvtapagodeservici.
		@return qtyvtapagodeservici	  */
	public BigDecimal getqtyvtapagodeservici () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtapagodeservici);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtaretiro.
		@param qtyvtaretiro qtyvtaretiro	  */
	public void setqtyvtaretiro (BigDecimal qtyvtaretiro)
	{
		set_Value (COLUMNNAME_qtyvtaretiro, qtyvtaretiro);
	}

	/** Get qtyvtaretiro.
		@return qtyvtaretiro	  */
	public BigDecimal getqtyvtaretiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtaretiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtatarjeta.
		@param qtyvtatarjeta qtyvtatarjeta	  */
	public void setqtyvtatarjeta (BigDecimal qtyvtatarjeta)
	{
		set_Value (COLUMNNAME_qtyvtatarjeta, qtyvtatarjeta);
	}

	/** Get qtyvtatarjeta.
		@return qtyvtatarjeta	  */
	public BigDecimal getqtyvtatarjeta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtatarjeta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtatarjetacuota.
		@param qtyvtatarjetacuota qtyvtatarjetacuota	  */
	public void setqtyvtatarjetacuota (BigDecimal qtyvtatarjetacuota)
	{
		set_Value (COLUMNNAME_qtyvtatarjetacuota, qtyvtatarjetacuota);
	}

	/** Get qtyvtatarjetacuota.
		@return qtyvtatarjetacuota	  */
	public BigDecimal getqtyvtatarjetacuota () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtatarjetacuota);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtatarjetaofline.
		@param qtyvtatarjetaofline qtyvtatarjetaofline	  */
	public void setqtyvtatarjetaofline (BigDecimal qtyvtatarjetaofline)
	{
		set_Value (COLUMNNAME_qtyvtatarjetaofline, qtyvtatarjetaofline);
	}

	/** Get qtyvtatarjetaofline.
		@return qtyvtatarjetaofline	  */
	public BigDecimal getqtyvtatarjetaofline () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtatarjetaofline);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyvtatktalimentacion.
		@param qtyvtatktalimentacion qtyvtatktalimentacion	  */
	public void setqtyvtatktalimentacion (BigDecimal qtyvtatktalimentacion)
	{
		set_Value (COLUMNNAME_qtyvtatktalimentacion, qtyvtatktalimentacion);
	}

	/** Get qtyvtatktalimentacion.
		@return qtyvtatktalimentacion	  */
	public BigDecimal getqtyvtatktalimentacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyvtatktalimentacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}

	/** Set total1.
		@param total1 total1	  */
	public void settotal1 (BigDecimal total1)
	{
		throw new IllegalArgumentException ("total1 is virtual column");	}

	/** Get total1.
		@return total1	  */
	public BigDecimal gettotal1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_total1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set total2.
		@param total2 total2	  */
	public void settotal2 (BigDecimal total2)
	{
		throw new IllegalArgumentException ("total2 is virtual column");	}

	/** Get total2.
		@return total2	  */
	public BigDecimal gettotal2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_total2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set total3.
		@param total3 total3	  */
	public void settotal3 (BigDecimal total3)
	{
		throw new IllegalArgumentException ("total3 is virtual column");	}

	/** Get total3.
		@return total3	  */
	public BigDecimal gettotal3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_total3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set total4.
		@param total4 total4	  */
	public void settotal4 (BigDecimal total4)
	{
		throw new IllegalArgumentException ("total4 is virtual column");	}

	/** Get total4.
		@return total4	  */
	public BigDecimal gettotal4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_total4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalheaders.
		@param totalheaders totalheaders	  */
	public void settotalheaders (String totalheaders)
	{
		set_Value (COLUMNNAME_totalheaders, totalheaders);
	}

	/** Get totalheaders.
		@return totalheaders	  */
	public String gettotalheaders () 
	{
		return (String)get_Value(COLUMNNAME_totalheaders);
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (String TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public String getTotalLines () 
	{
		return (String)get_Value(COLUMNNAME_TotalLines);
	}

	/** Set totallinesfile.
		@param totallinesfile totallinesfile	  */
	public void settotallinesfile (String totallinesfile)
	{
		set_Value (COLUMNNAME_totallinesfile, totallinesfile);
	}

	/** Get totallinesfile.
		@return totallinesfile	  */
	public String gettotallinesfile () 
	{
		return (String)get_Value(COLUMNNAME_totallinesfile);
	}

	/** Set UY_RT_AuditLoadTicket.
		@param UY_RT_AuditLoadTicket_ID UY_RT_AuditLoadTicket	  */
	public void setUY_RT_AuditLoadTicket_ID (int UY_RT_AuditLoadTicket_ID)
	{
		if (UY_RT_AuditLoadTicket_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_AuditLoadTicket_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_AuditLoadTicket_ID, Integer.valueOf(UY_RT_AuditLoadTicket_ID));
	}

	/** Get UY_RT_AuditLoadTicket.
		@return UY_RT_AuditLoadTicket	  */
	public int getUY_RT_AuditLoadTicket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_AuditLoadTicket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_LoadTicket getUY_RT_LoadTicket() throws RuntimeException
    {
		return (I_UY_RT_LoadTicket)MTable.get(getCtx(), I_UY_RT_LoadTicket.Table_Name)
			.getPO(getUY_RT_LoadTicket_ID(), get_TrxName());	}

	/** Set UY_RT_LoadTicket.
		@param UY_RT_LoadTicket_ID UY_RT_LoadTicket	  */
	public void setUY_RT_LoadTicket_ID (int UY_RT_LoadTicket_ID)
	{
		if (UY_RT_LoadTicket_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_LoadTicket_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_LoadTicket_ID, Integer.valueOf(UY_RT_LoadTicket_ID));
	}

	/** Get UY_RT_LoadTicket.
		@return UY_RT_LoadTicket	  */
	public int getUY_RT_LoadTicket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_LoadTicket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set VtaFondeoDolares.
		@param VtaFondeoDolares VtaFondeoDolares	  */
	public void setVtaFondeoDolares (BigDecimal VtaFondeoDolares)
	{
		set_Value (COLUMNNAME_VtaFondeoDolares, VtaFondeoDolares);
	}

	/** Get VtaFondeoDolares.
		@return VtaFondeoDolares	  */
	public BigDecimal getVtaFondeoDolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VtaFondeoDolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set VtaTjaEdenredSodexo.
		@param VtaTjaEdenredSodexo VtaTjaEdenredSodexo	  */
	public void setVtaTjaEdenredSodexo (BigDecimal VtaTjaEdenredSodexo)
	{
		set_Value (COLUMNNAME_VtaTjaEdenredSodexo, VtaTjaEdenredSodexo);
	}

	/** Get VtaTjaEdenredSodexo.
		@return VtaTjaEdenredSodexo	  */
	public BigDecimal getVtaTjaEdenredSodexo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VtaTjaEdenredSodexo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
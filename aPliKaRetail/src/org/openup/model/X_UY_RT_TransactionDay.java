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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_RT_TransactionDay
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_TransactionDay extends PO implements I_UY_RT_TransactionDay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160614L;

    /** Standard Constructor */
    public X_UY_RT_TransactionDay (Properties ctx, int UY_RT_TransactionDay_ID, String trxName)
    {
      super (ctx, UY_RT_TransactionDay_ID, trxName);
      /** if (UY_RT_TransactionDay_ID == 0)
        {
			setName (null);
			setUY_RT_TransactionDay_ID (0);
			setUY_RT_TransactionReport_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_TransactionDay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_TransactionDay[")
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

	/** Set comercio.
		@param comercio comercio	  */
	public void setcomercio (String comercio)
	{
		set_Value (COLUMNNAME_comercio, comercio);
	}

	/** Get comercio.
		@return comercio	  */
	public String getcomercio () 
	{
		return (String)get_Value(COLUMNNAME_comercio);
	}

	/** Set cuotas.
		@param cuotas cuotas	  */
	public void setcuotas (int cuotas)
	{
		set_Value (COLUMNNAME_cuotas, Integer.valueOf(cuotas));
	}

	/** Get cuotas.
		@return cuotas	  */
	public int getcuotas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cuotas);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set fechacierre.
		@param fechacierre fechacierre	  */
	public void setfechacierre (String fechacierre)
	{
		set_Value (COLUMNNAME_fechacierre, fechacierre);
	}

	/** Get fechacierre.
		@return fechacierre	  */
	public String getfechacierre () 
	{
		return (String)get_Value(COLUMNNAME_fechacierre);
	}

	/** Set fechaexp.
		@param fechaexp fechaexp	  */
	public void setfechaexp (String fechaexp)
	{
		set_Value (COLUMNNAME_fechaexp, fechaexp);
	}

	/** Get fechaexp.
		@return fechaexp	  */
	public String getfechaexp () 
	{
		return (String)get_Value(COLUMNNAME_fechaexp);
	}

	/** Set fechaori.
		@param fechaori fechaori	  */
	public void setfechaori (String fechaori)
	{
		set_Value (COLUMNNAME_fechaori, fechaori);
	}

	/** Get fechaori.
		@return fechaori	  */
	public String getfechaori () 
	{
		return (String)get_Value(COLUMNNAME_fechaori);
	}

	/** Set fechatrx.
		@param fechatrx fechatrx	  */
	public void setfechatrx (String fechatrx)
	{
		set_Value (COLUMNNAME_fechatrx, fechatrx);
	}

	/** Get fechatrx.
		@return fechatrx	  */
	public String getfechatrx () 
	{
		return (String)get_Value(COLUMNNAME_fechatrx);
	}

	/** Set hora.
		@param hora hora	  */
	public void sethora (String hora)
	{
		set_Value (COLUMNNAME_hora, hora);
	}

	/** Get hora.
		@return hora	  */
	public String gethora () 
	{
		return (String)get_Value(COLUMNNAME_hora);
	}

	/** Set idtarjeta.
		@param idtarjeta idtarjeta	  */
	public void setidtarjeta (String idtarjeta)
	{
		set_Value (COLUMNNAME_idtarjeta, idtarjeta);
	}

	/** Get idtarjeta.
		@return idtarjeta	  */
	public String getidtarjeta () 
	{
		return (String)get_Value(COLUMNNAME_idtarjeta);
	}

	/** Set lote.
		@param lote lote	  */
	public void setlote (String lote)
	{
		set_Value (COLUMNNAME_lote, lote);
	}

	/** Get lote.
		@return lote	  */
	public String getlote () 
	{
		return (String)get_Value(COLUMNNAME_lote);
	}

	/** Set mdoingreso.
		@param mdoingreso mdoingreso	  */
	public void setmdoingreso (String mdoingreso)
	{
		set_Value (COLUMNNAME_mdoingreso, mdoingreso);
	}

	/** Get mdoingreso.
		@return mdoingreso	  */
	public String getmdoingreso () 
	{
		return (String)get_Value(COLUMNNAME_mdoingreso);
	}

	/** Set modo.
		@param modo modo	  */
	public void setmodo (String modo)
	{
		set_Value (COLUMNNAME_modo, modo);
	}

	/** Get modo.
		@return modo	  */
	public String getmodo () 
	{
		return (String)get_Value(COLUMNNAME_modo);
	}

	/** Set moneda.
		@param moneda moneda	  */
	public void setmoneda (String moneda)
	{
		set_Value (COLUMNNAME_moneda, moneda);
	}

	/** Get moneda.
		@return moneda	  */
	public String getmoneda () 
	{
		return (String)get_Value(COLUMNNAME_moneda);
	}

	/** Set monto.
		@param monto monto	  */
	public void setmonto (BigDecimal monto)
	{
		set_Value (COLUMNNAME_monto, monto);
	}

	/** Get monto.
		@return monto	  */
	public BigDecimal getmonto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_monto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set nrotarjeta.
		@param nrotarjeta nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta)
	{
		set_Value (COLUMNNAME_nrotarjeta, nrotarjeta);
	}

	/** Get nrotarjeta.
		@return nrotarjeta	  */
	public String getnrotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_nrotarjeta);
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

	/** Set statuscierre.
		@param statuscierre statuscierre	  */
	public void setstatuscierre (String statuscierre)
	{
		set_Value (COLUMNNAME_statuscierre, statuscierre);
	}

	/** Get statuscierre.
		@return statuscierre	  */
	public String getstatuscierre () 
	{
		return (String)get_Value(COLUMNNAME_statuscierre);
	}

	/** Set sucursal.
		@param sucursal sucursal	  */
	public void setsucursal (String sucursal)
	{
		set_Value (COLUMNNAME_sucursal, sucursal);
	}

	/** Get sucursal.
		@return sucursal	  */
	public String getsucursal () 
	{
		return (String)get_Value(COLUMNNAME_sucursal);
	}

	/** Set terminal.
		@param terminal terminal	  */
	public void setterminal (String terminal)
	{
		set_Value (COLUMNNAME_terminal, terminal);
	}

	/** Get terminal.
		@return terminal	  */
	public String getterminal () 
	{
		return (String)get_Value(COLUMNNAME_terminal);
	}

	/** Set ticket.
		@param ticket ticket	  */
	public void setticket (String ticket)
	{
		set_Value (COLUMNNAME_ticket, ticket);
	}

	/** Get ticket.
		@return ticket	  */
	public String getticket () 
	{
		return (String)get_Value(COLUMNNAME_ticket);
	}

	/** Set tipo.
		@param tipo tipo	  */
	public void settipo (String tipo)
	{
		set_Value (COLUMNNAME_tipo, tipo);
	}

	/** Get tipo.
		@return tipo	  */
	public String gettipo () 
	{
		return (String)get_Value(COLUMNNAME_tipo);
	}

	/** Set tipotarjeta.
		@param tipotarjeta tipotarjeta	  */
	public void settipotarjeta (String tipotarjeta)
	{
		set_Value (COLUMNNAME_tipotarjeta, tipotarjeta);
	}

	/** Get tipotarjeta.
		@return tipotarjeta	  */
	public String gettipotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_tipotarjeta);
	}

	/** Set UY_RT_TransactionDay.
		@param UY_RT_TransactionDay_ID UY_RT_TransactionDay	  */
	public void setUY_RT_TransactionDay_ID (int UY_RT_TransactionDay_ID)
	{
		if (UY_RT_TransactionDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_TransactionDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_TransactionDay_ID, Integer.valueOf(UY_RT_TransactionDay_ID));
	}

	/** Get UY_RT_TransactionDay.
		@return UY_RT_TransactionDay	  */
	public int getUY_RT_TransactionDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_TransactionDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_TransactionReport getUY_RT_TransactionReport() throws RuntimeException
    {
		return (I_UY_RT_TransactionReport)MTable.get(getCtx(), I_UY_RT_TransactionReport.Table_Name)
			.getPO(getUY_RT_TransactionReport_ID(), get_TrxName());	}

	/** Set UY_RT_TransactionReport.
		@param UY_RT_TransactionReport_ID UY_RT_TransactionReport	  */
	public void setUY_RT_TransactionReport_ID (int UY_RT_TransactionReport_ID)
	{
		if (UY_RT_TransactionReport_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_TransactionReport_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_TransactionReport_ID, Integer.valueOf(UY_RT_TransactionReport_ID));
	}

	/** Get UY_RT_TransactionReport.
		@return UY_RT_TransactionReport	  */
	public int getUY_RT_TransactionReport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_TransactionReport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set voucher.
		@param voucher voucher	  */
	public void setvoucher (String voucher)
	{
		set_Value (COLUMNNAME_voucher, voucher);
	}

	/** Get voucher.
		@return voucher	  */
	public String getvoucher () 
	{
		return (String)get_Value(COLUMNNAME_voucher);
	}

	/** Set voucherori.
		@param voucherori voucherori	  */
	public void setvoucherori (String voucherori)
	{
		set_Value (COLUMNNAME_voucherori, voucherori);
	}

	/** Get voucherori.
		@return voucherori	  */
	public String getvoucherori () 
	{
		return (String)get_Value(COLUMNNAME_voucherori);
	}
}
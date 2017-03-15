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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_RT_TrxReportLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_TrxReportLog extends PO implements I_UY_RT_TrxReportLog, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160613L;

    /** Standard Constructor */
    public X_UY_RT_TrxReportLog (Properties ctx, int UY_RT_TrxReportLog_ID, String trxName)
    {
      super (ctx, UY_RT_TrxReportLog_ID, trxName);
      /** if (UY_RT_TrxReportLog_ID == 0)
        {
			setName (null);
			setUY_RT_TrxReportLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_TrxReportLog (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_TrxReportLog[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set datofila.
		@param datofila datofila	  */
	public void setdatofila (String datofila)
	{
		set_Value (COLUMNNAME_datofila, datofila);
	}

	/** Get datofila.
		@return datofila	  */
	public String getdatofila () 
	{
		return (String)get_Value(COLUMNNAME_datofila);
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

	/** Set UY_RT_TrxReportLog.
		@param UY_RT_TrxReportLog_ID UY_RT_TrxReportLog	  */
	public void setUY_RT_TrxReportLog_ID (int UY_RT_TrxReportLog_ID)
	{
		if (UY_RT_TrxReportLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_TrxReportLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_TrxReportLog_ID, Integer.valueOf(UY_RT_TrxReportLog_ID));
	}

	/** Get UY_RT_TrxReportLog.
		@return UY_RT_TrxReportLog	  */
	public int getUY_RT_TrxReportLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_TrxReportLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_RT_SendPosLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_SendPosLog extends PO implements I_UY_RT_SendPosLog, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170321L;

    /** Standard Constructor */
    public X_UY_RT_SendPosLog (Properties ctx, int UY_RT_SendPosLog_ID, String trxName)
    {
      super (ctx, UY_RT_SendPosLog_ID, trxName);
      /** if (UY_RT_SendPosLog_ID == 0)
        {
			setUY_RT_SendPosLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_SendPosLog (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_SendPosLog[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** SourceType AD_Reference_ID=1000560 */
	public static final int SOURCETYPE_AD_Reference_ID=1000560;
	/** POS = POS */
	public static final String SOURCETYPE_POS = "POS";
	/** BALANZA = BALANZA */
	public static final String SOURCETYPE_BALANZA = "BALANZA";
	/** Set SourceType.
		@param SourceType SourceType	  */
	public void setSourceType (String SourceType)
	{

		set_Value (COLUMNNAME_SourceType, SourceType);
	}

	/** Get SourceType.
		@return SourceType	  */
	public String getSourceType () 
	{
		return (String)get_Value(COLUMNNAME_SourceType);
	}

	/** Set UY_RT_SendPosLog.
		@param UY_RT_SendPosLog_ID UY_RT_SendPosLog	  */
	public void setUY_RT_SendPosLog_ID (int UY_RT_SendPosLog_ID)
	{
		if (UY_RT_SendPosLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_SendPosLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_SendPosLog_ID, Integer.valueOf(UY_RT_SendPosLog_ID));
	}

	/** Get UY_RT_SendPosLog.
		@return UY_RT_SendPosLog	  */
	public int getUY_RT_SendPosLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_SendPosLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
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
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_RT_TransactionDay
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_TransactionDay 
{

    /** TableName=UY_RT_TransactionDay */
    public static final String Table_Name = "UY_RT_TransactionDay";

    /** AD_Table_ID=1001049 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name autorizacion */
    public static final String COLUMNNAME_autorizacion = "autorizacion";

	/** Set autorizacion	  */
	public void setautorizacion (String autorizacion);

	/** Get autorizacion	  */
	public String getautorizacion();

    /** Column name caja */
    public static final String COLUMNNAME_caja = "caja";

	/** Set caja	  */
	public void setcaja (String caja);

	/** Get caja	  */
	public String getcaja();

    /** Column name comercio */
    public static final String COLUMNNAME_comercio = "comercio";

	/** Set comercio	  */
	public void setcomercio (String comercio);

	/** Get comercio	  */
	public String getcomercio();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name cuotas */
    public static final String COLUMNNAME_cuotas = "cuotas";

	/** Set cuotas	  */
	public void setcuotas (int cuotas);

	/** Get cuotas	  */
	public int getcuotas();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name fechacierre */
    public static final String COLUMNNAME_fechacierre = "fechacierre";

	/** Set fechacierre	  */
	public void setfechacierre (String fechacierre);

	/** Get fechacierre	  */
	public String getfechacierre();

    /** Column name fechaexp */
    public static final String COLUMNNAME_fechaexp = "fechaexp";

	/** Set fechaexp	  */
	public void setfechaexp (String fechaexp);

	/** Get fechaexp	  */
	public String getfechaexp();

    /** Column name fechaori */
    public static final String COLUMNNAME_fechaori = "fechaori";

	/** Set fechaori	  */
	public void setfechaori (String fechaori);

	/** Get fechaori	  */
	public String getfechaori();

    /** Column name fechatrx */
    public static final String COLUMNNAME_fechatrx = "fechatrx";

	/** Set fechatrx	  */
	public void setfechatrx (String fechatrx);

	/** Get fechatrx	  */
	public String getfechatrx();

    /** Column name hora */
    public static final String COLUMNNAME_hora = "hora";

	/** Set hora	  */
	public void sethora (String hora);

	/** Get hora	  */
	public String gethora();

    /** Column name idtarjeta */
    public static final String COLUMNNAME_idtarjeta = "idtarjeta";

	/** Set idtarjeta	  */
	public void setidtarjeta (String idtarjeta);

	/** Get idtarjeta	  */
	public String getidtarjeta();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name lote */
    public static final String COLUMNNAME_lote = "lote";

	/** Set lote	  */
	public void setlote (String lote);

	/** Get lote	  */
	public String getlote();

    /** Column name mdoingreso */
    public static final String COLUMNNAME_mdoingreso = "mdoingreso";

	/** Set mdoingreso	  */
	public void setmdoingreso (String mdoingreso);

	/** Get mdoingreso	  */
	public String getmdoingreso();

    /** Column name modo */
    public static final String COLUMNNAME_modo = "modo";

	/** Set modo	  */
	public void setmodo (String modo);

	/** Get modo	  */
	public String getmodo();

    /** Column name moneda */
    public static final String COLUMNNAME_moneda = "moneda";

	/** Set moneda	  */
	public void setmoneda (String moneda);

	/** Get moneda	  */
	public String getmoneda();

    /** Column name monto */
    public static final String COLUMNNAME_monto = "monto";

	/** Set monto	  */
	public void setmonto (BigDecimal monto);

	/** Get monto	  */
	public BigDecimal getmonto();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name nrotarjeta */
    public static final String COLUMNNAME_nrotarjeta = "nrotarjeta";

	/** Set nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta);

	/** Get nrotarjeta	  */
	public String getnrotarjeta();

    /** Column name Plan */
    public static final String COLUMNNAME_Plan = "Plan";

	/** Set Plan.
	  * Plan utilizado por la tarjeta.
	  */
	public void setPlan (String Plan);

	/** Get Plan.
	  * Plan utilizado por la tarjeta.
	  */
	public String getPlan();

    /** Column name positionfile */
    public static final String COLUMNNAME_positionfile = "positionfile";

	/** Set positionfile	  */
	public void setpositionfile (String positionfile);

	/** Get positionfile	  */
	public String getpositionfile();

    /** Column name statuscierre */
    public static final String COLUMNNAME_statuscierre = "statuscierre";

	/** Set statuscierre	  */
	public void setstatuscierre (String statuscierre);

	/** Get statuscierre	  */
	public String getstatuscierre();

    /** Column name sucursal */
    public static final String COLUMNNAME_sucursal = "sucursal";

	/** Set sucursal	  */
	public void setsucursal (String sucursal);

	/** Get sucursal	  */
	public String getsucursal();

    /** Column name terminal */
    public static final String COLUMNNAME_terminal = "terminal";

	/** Set terminal	  */
	public void setterminal (String terminal);

	/** Get terminal	  */
	public String getterminal();

    /** Column name ticket */
    public static final String COLUMNNAME_ticket = "ticket";

	/** Set ticket	  */
	public void setticket (String ticket);

	/** Get ticket	  */
	public String getticket();

    /** Column name tipo */
    public static final String COLUMNNAME_tipo = "tipo";

	/** Set tipo	  */
	public void settipo (String tipo);

	/** Get tipo	  */
	public String gettipo();

    /** Column name tipotarjeta */
    public static final String COLUMNNAME_tipotarjeta = "tipotarjeta";

	/** Set tipotarjeta	  */
	public void settipotarjeta (String tipotarjeta);

	/** Get tipotarjeta	  */
	public String gettipotarjeta();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UY_RT_TransactionDay_ID */
    public static final String COLUMNNAME_UY_RT_TransactionDay_ID = "UY_RT_TransactionDay_ID";

	/** Set UY_RT_TransactionDay	  */
	public void setUY_RT_TransactionDay_ID (int UY_RT_TransactionDay_ID);

	/** Get UY_RT_TransactionDay	  */
	public int getUY_RT_TransactionDay_ID();

    /** Column name UY_RT_TransactionReport_ID */
    public static final String COLUMNNAME_UY_RT_TransactionReport_ID = "UY_RT_TransactionReport_ID";

	/** Set UY_RT_TransactionReport	  */
	public void setUY_RT_TransactionReport_ID (int UY_RT_TransactionReport_ID);

	/** Get UY_RT_TransactionReport	  */
	public int getUY_RT_TransactionReport_ID();

	public I_UY_RT_TransactionReport getUY_RT_TransactionReport() throws RuntimeException;

    /** Column name voucher */
    public static final String COLUMNNAME_voucher = "voucher";

	/** Set voucher	  */
	public void setvoucher (String voucher);

	/** Get voucher	  */
	public String getvoucher();

    /** Column name voucherori */
    public static final String COLUMNNAME_voucherori = "voucherori";

	/** Set voucherori	  */
	public void setvoucherori (String voucherori);

	/** Get voucherori	  */
	public String getvoucherori();
}

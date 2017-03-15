/**
 * 
 */
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.retail.MRTRetailInterface;

/**OpenUp Ltda Issue#6862
 * @author SBT 1/9/2016
 *
 */
public class PRTGetVersionNoScanntechWS extends SvrProcess {

	/**
	 * 
	 */
	public PRTGetVersionNoScanntechWS() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String version = MRTRetailInterface.getVersion(getCtx(), 0, 0, get_TrxName());
		return version;
	}

}

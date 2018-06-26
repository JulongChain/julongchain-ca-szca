
package org.bcia.javachain.ca.szca.common.bcca.core.model.approval;

import javax.xml.ws.WebFault;

/**
 * Exception thrown from actions that stop to wait for approvals
 * 
 * @version $Id: WaitingForApprovalException.java 22117 2015-10-29 10:53:42Z mikekushner $
 */
@WebFault
public class WaitingForApprovalException extends Exception {

	private static final long serialVersionUID = 6808192333114783496L;
    private int approvalId = 0;

	public WaitingForApprovalException(String message, Throwable cause) {
		super(message, cause);
	}

	public WaitingForApprovalException(String message) {
		super(message);
	}
	
	public WaitingForApprovalException(String message, int approvalId) {
		super(message);
		this.approvalId = approvalId;
	}
	
	public int getApprovalId(){
		return approvalId;
	}
	
	public void setApprovalId(int approvalId){
		this.approvalId = approvalId;
	}

}

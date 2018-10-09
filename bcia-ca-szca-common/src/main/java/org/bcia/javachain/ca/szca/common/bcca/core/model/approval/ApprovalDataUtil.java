
package org.bcia.javachain.ca.szca.common.bcca.core.model.approval;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;

import org.apache.log4j.Logger;
import org.cesecore.util.Base64;
import org.ejbca.core.model.approval.Approval;
import org.ejbca.core.model.approval.ApprovalRequest;

/**
 * Class containing utils for extracting data from the approvaldata table.
 * is used by the Entity and Session bean only.
 * 
 * @version $Id: ApprovalDataUtil.java 22117 2015-10-29 10:53:42Z mikekushner $
 */
public class ApprovalDataUtil  { 
	
	private static final Logger log = Logger.getLogger(ApprovalDataUtil.class);

	public static Collection<Approval> getApprovals(String stringdata) {
    	ArrayList<Approval> retval = new ArrayList<Approval>();
    	try{
    		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(stringdata.getBytes())));
    		int size = ois.readInt();
    		for(int i=0;i<size;i++){
    			Approval next = (Approval) ois.readObject();
    			retval.add(next);
    		}
    	} catch (IOException e) {
    		log.error("Error building approvals.",e);
    		throw new EJBException(e);
    	} catch (ClassNotFoundException e) {
    		log.error("Error building approvals.",e);
    		throw new EJBException(e);
    	}
    	return retval;
    }
    
    public static ApprovalRequest getApprovalRequest(String stringdata) {
    	ApprovalRequest retval = null;    	
    	try {
    		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(stringdata.getBytes())));
			retval= (ApprovalRequest) ois.readObject();
		} catch (IOException e) {
			log.error("Error building approval request.",e);
			throw new EJBException(e);
		} catch (ClassNotFoundException e) {
			log.error("Error building approval request.",e);
			throw new EJBException(e);
		}
		return retval;
    }
}

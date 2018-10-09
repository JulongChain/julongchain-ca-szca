

package org.bcia.javachain.ca.szca.common.bcca.cesecoreintegration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.bcia.javachain.ca.szca.common.cesecore.time.TrustedTimeWatcherSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.time.TrustedTimeWatcherSessionLocal;
import org.cesecore.time.TrustedTime;
//import org.cesecore.time.TrustedTimeWatcherSessionLocal;
import org.cesecore.time.providers.TrustedTimeProviderException;
import org.springframework.stereotype.Repository;

import org.bcia.javachain.ca.szca.common.cesecore.time.TrustedTimeWatcherSessionLocal;

/**
 * This is the trusted time watcher implementation.
 * 
 *
 * @version $Id: ServerTimeWatcherSessionBean.java 22822 2016-02-16 13:51:22Z mikekushner $
 */
//@Stateless
//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Repository
public class ServerTimeWatcherSessionBean implements TrustedTimeWatcherSessionLocal {

	@Override
	public TrustedTime getTrustedTime(final boolean force) throws TrustedTimeProviderException {
		final TrustedTime tt = new TrustedTime();
		tt.setSync(false);
		return tt;
	}


}

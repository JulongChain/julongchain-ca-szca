
package org.bcia.javachain.ca.szca.common.cesecore.time;

import org.cesecore.time.TrustedTime;
import org.cesecore.time.providers.TrustedTimeProviderException;


public interface TrustedTimeWatcherSession {


    /**
     * 
     * Retrieves the current TrustedTime instance made available in the watcher.
     * The first time this method is invoked it will update the current TrustedTime by making a direct call to 
     * the provider and it will schedule a new update interval based on information provided by ntp protocol. 
     *
     * @param force forces the TrustedTime update from the provider.
     * @return Watcher TrustedTime current instance.
     * 
     * @throws TrustedTimeProviderException
     */
    TrustedTime getTrustedTime(boolean force) throws TrustedTimeProviderException;

}

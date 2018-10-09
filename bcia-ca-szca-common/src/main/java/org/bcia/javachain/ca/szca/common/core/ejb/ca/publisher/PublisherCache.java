
package org.bcia.javachain.ca.szca.common.core.ejb.ca.publisher;

import java.util.List;
import java.util.Map;

import org.cesecore.internal.CommonCache;
import org.cesecore.internal.CommonCacheBase;
import org.ejbca.config.EjbcaConfiguration;
import org.ejbca.core.model.ca.publisher.BasePublisher;


public enum PublisherCache implements CommonCache<BasePublisher> {
    INSTANCE;

    final private CommonCache<BasePublisher> cache = new CommonCacheBase<BasePublisher>() {
        @Override
        protected long getCacheTime() {
            return EjbcaConfiguration.getCachePublisherTime();
        };
        @Override
        protected long getMaxCacheLifeTime() {
            // Publishers are not short-lived objects with long cache times so we disable it
            return 0L;
        };
    };

    @Override
    public BasePublisher getEntry(final int id) {
        return cache.getEntry(id);
    }

    @Override
    public boolean shouldCheckForUpdates(final int id) {
        return cache.shouldCheckForUpdates(id);
    }
    
    @Override
    public void updateWith(int id, int digest, String name, BasePublisher object) {
        cache.updateWith(id, digest, name, object);
    }

    @Override
    public void removeEntry(int id) {
        cache.removeEntry(id);
    }

    @Override
    public String getName(int id) {
        return cache.getName(id);
    }

    @Override
    public Map<String,Integer> getNameToIdMap() {
        return cache.getNameToIdMap();
    }
    
    @Override
    public void flush() {
        cache.flush();
    }
    
    @Override
    public void replaceCacheWith(List<Integer> keys) {
        cache.replaceCacheWith(keys);
    }
}

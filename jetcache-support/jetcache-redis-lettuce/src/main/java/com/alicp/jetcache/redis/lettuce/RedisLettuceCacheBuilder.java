package com.alicp.jetcache.redis.lettuce;

import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.external.ExternalCacheBuilder;
import com.alicp.jetcache.support.BroadcastManager;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

/**
 * Created on 2017/4/28.
 *
 * @author huangli
 */
public class RedisLettuceCacheBuilder<T extends ExternalCacheBuilder<T>> extends ExternalCacheBuilder<T> {
    public static class RedisLettuceCacheBuilderImpl extends RedisLettuceCacheBuilder<RedisLettuceCacheBuilderImpl> {
    }

    public static RedisLettuceCacheBuilderImpl createRedisLettuceCacheBuilder() {
        return new RedisLettuceCacheBuilderImpl();
    }

    protected RedisLettuceCacheBuilder() {
        buildFunc(config -> new RedisLettuceCache((RedisLettuceCacheConfig) config));
    }

    @Override
    public RedisLettuceCacheConfig getConfig() {
        if (config == null) {
            config = new RedisLettuceCacheConfig();
        }
        return (RedisLettuceCacheConfig) config;
    }

    @Override
    public boolean supportBroadcast() {
        return true;
    }

    @Override
    public BroadcastManager createBroadcastManager(CacheManager cacheManager) {
        RedisLettuceCacheConfig c = (RedisLettuceCacheConfig) getConfig().clone();
        return new LettuceBroadcastManager(cacheManager, c);
    }

    public T redisClient(AbstractRedisClient redisClient){
        getConfig().setRedisClient(redisClient);
        return self();
    }

    public void setRedisClient(AbstractRedisClient redisClient) {
        getConfig().setRedisClient(redisClient);
    }

    public T connection(StatefulConnection connection) {
        getConfig().setConnection(connection);
        return self();
    }

    public void setConnection(StatefulConnection connection) {
        getConfig().setConnection(connection);
    }

    public T pubSubConnection(StatefulRedisPubSubConnection pubSubConnection) {
        getConfig().setPubSubConnection(pubSubConnection);
        return self();
    }

    public void setPubSubConnection(StatefulRedisPubSubConnection pubSubConnection) {
        getConfig().setPubSubConnection(pubSubConnection);
    }

    public T connectionManager(LettuceConnectionManager connectionManager) {
        getConfig().setConnectionManager(connectionManager);
        return self();
    }

    public void setConnectionManager(LettuceConnectionManager connectionManager) {
        getConfig().setConnectionManager(connectionManager);
    }

    public T asyncResultTimeoutInMillis(long asyncResultTimeoutInMillis) {
        getConfig().setAsyncResultTimeoutInMillis(asyncResultTimeoutInMillis);
        return self();
    }

    public void setAsyncResultTimeoutInMillis(long asyncResultTimeoutInMillis) {
        getConfig().setAsyncResultTimeoutInMillis(asyncResultTimeoutInMillis);
    }
}

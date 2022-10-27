package com.bcs.cache;

import com.bcs.adapter.AsynMethodInvoke;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCache extends AbstractCache<List<AsynMethodInvoke>> {

    private Cache<Long, List<AsynMethodInvoke>> cache;

    public GuavaCache() {
        cache = CacheBuilder.newBuilder().initialCapacity(10)
                //最大值
                .maximumSize(100)
                //并发数设置
                .concurrencyLevel(5)
                //缓存过期时间，写入后1分钟过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 此缓存对象经过多少秒没有被访问则过期。
                .expireAfterAccess(60, TimeUnit.SECONDS).build();
    }


    @Override
    public List<AsynMethodInvoke> getCache() {
        return cache.getIfPresent(Thread.currentThread().getId());
    }

    @Override
    public void setCache(List<AsynMethodInvoke> asynMethodInvokes) {
        cache.put(Thread.currentThread().getId(), asynMethodInvokes);
    }
}

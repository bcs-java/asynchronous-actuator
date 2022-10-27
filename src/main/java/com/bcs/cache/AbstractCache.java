package com.bcs.cache;

/**
 * Abstract Cache
 *
 * @author shenbangchen
 * @date 2022/10/26 10:45
 */
public abstract class AbstractCache<E> {

    /**
     * get cache
     *
     * @return {@link E }
     * @author shenbangchen
     * @date 2022/10/26 11:17
     */
    public abstract E getCache();

    /**
     * set Cache
     *
     * @param e e
     * @author shenbangchen
     * @date 2022/10/26 10:55
     */
    public abstract void setCache(E e);

}


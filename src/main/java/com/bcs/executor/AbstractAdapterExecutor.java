package com.bcs.executor;

import com.bcs.adapter.AbstractAsynAdapter;
import com.bcs.adapter.AsynMethodInvoke;
import com.bcs.cache.AbstractCache;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract adapter executor
 *
 * @author shenbangchen
 * @date 2022/10/25 11:09
 */
@SuppressWarnings("ALL")
public abstract class AbstractAdapterExecutor {

    /**
     * locle缓存
     */
    private static AbstractCache<List<AsynMethodInvoke>> locleCache;

    /**
     * Method Invoke
     */
    private static ThreadLocal<List<AbstractAsynAdapter>> TL = new ThreadLocal();

    /**
     * thread pool
     */
    private static ThreadPoolExecutor POOL =
            new ThreadPoolExecutor(10, 30, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(100));

    /**
     * initialize enable
     *
     * @return boolean
     * @author shenbangchen
     * @date 2022/10/25 11:17
     */
    public static boolean initializeEnable() {
        return TL.get() != null;
    }

    /**
     * initialize
     *
     * @author shenbangchen
     * @date 2022/10/25 11:13
     */
    public static void initialize() {
        TL.set(new LinkedList());
    }

    /**
     * add
     *
     * @param asynMethodInvoke asyn方法调用
     * @author shenbangchen
     * @date 2022/10/25 16:39
     */
    public static void add(AbstractAsynAdapter abstractAsynAdapter) {
        TL.get().add(abstractAsynAdapter);
    }

    /**
     * close
     *
     * @author shenbangchen
     * @date 2022/10/25 11:13
     */
    private static void close() {
        TL.remove();
    }

    /**
     * wait execute
     *
     * @author shenbangchen
     * @date 2022/10/25 14:07
     */
    public static void execute() {
        AbstractAdapterExecutor.execute(false);
    }

    /**
     * execute
     *
     * @param await
     * @author shenbangchen
     * @date 2022/10/25 14:22
     */
    public static void execute(Boolean await) {
        try {
            List<AbstractAsynAdapter> runnables;
            if ((runnables = TL.get()) == null || runnables.size() == 0) {
                return;
            }
            CountDownLatch countDownLatch = new CountDownLatch(runnables.size());
            CyclicBarrier cyclicBarrier = new CyclicBarrier(runnables.size());
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            for (AbstractAsynAdapter runnable : runnables) {
                runnable.
                        setCountDownLatch(countDownLatch).
                        setCyclicBarrier(cyclicBarrier).
                        setRollBackEnable(atomicBoolean);
                POOL.execute(runnable);
            }
            if (await) countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            AbstractAdapterExecutor.cache();
            AbstractAdapterExecutor.close();
        }
    }

    private static void cache() {
        List<AbstractAsynAdapter> abstractAsynAdapters = TL.get();
        List<AsynMethodInvoke> asynMethodInvokes = new ArrayList<>();
        for (AbstractAsynAdapter abstractAsynAdapter : abstractAsynAdapters) {
            asynMethodInvokes.add(abstractAsynAdapter.getAsynMethodInvoke());
        }
        locleCache.setCache(asynMethodInvokes);
    }

    /**
     * Get the return value
     *
     * @param methodName 方法名称
     * @return {@link T }
     * @author shenbangchen
     * @date 2022/10/26 11:42
     */
    public static <T> T getReturn(String methodName) {
        if (methodName == null || methodName.equalsIgnoreCase("")) {
            throw new NullPointerException();
        }
        List<AsynMethodInvoke> cache;
        if (locleCache == null || (cache = locleCache.getCache()) == null) {
            return null;
        }
        for (AsynMethodInvoke asynMethodInvoke : cache) {
            if (asynMethodInvoke.comparaMethodName(methodName)) {
                return (T) asynMethodInvoke.getReturn();
            }
        }
        return null;
    }

    public static void setLocleCache(AbstractCache locleCache) {
        AbstractAdapterExecutor.locleCache = locleCache;
    }
}


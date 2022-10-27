package com.bcs.adapter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Define an abstract asynchronous mode adapter.
 */
@SuppressWarnings("ALL")
public abstract class AbstractAsynAdapter<R> implements Runnable {
    /**
     * countDownLatch
     */
    protected CountDownLatch countDownLatch;
    /**
     * cyclicBarrier
     */
    protected CyclicBarrier cyclicBarrier;
    /**
     * asynMethodInvoke
     */
    protected AsynMethodInvoke asynMethodInvoke;

    /**
     * whether to roll back
     */
    private AtomicBoolean rollBackEnable;

    /**
     * Define an adapter method with no return task.
     */
    abstract void asynTask(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier, AsynMethodInvoke asynMethodInvoke);

    /**
     * Subclasses manipulate state through this method
     */
    protected boolean alterCallBackEnableState() {
        return rollBackEnable.compareAndSet(false, true);
    }

    /**
     * get roll back states
     */
    protected boolean getCallBackEnableState() {
        return rollBackEnable.get();
    }

    /**
     * implement Runnable
     *
     * @author shenbangchen
     * @date 2022/10/25 15:52
     */
    public void run() {
        this.asynTask(countDownLatch, cyclicBarrier, asynMethodInvoke);
    }

    public AbstractAsynAdapter setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        return this;
    }

    public AbstractAsynAdapter setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
        return this;
    }

    public AbstractAsynAdapter setAsynMethodInvoke(AsynMethodInvoke asynMethodInvoke) {
        this.asynMethodInvoke = asynMethodInvoke;
        return this;
    }

    public AbstractAsynAdapter setRollBackEnable(AtomicBoolean rollBackEnable) {
        this.rollBackEnable = rollBackEnable;
        return this;
    }

    public AsynMethodInvoke getAsynMethodInvoke() {
        return asynMethodInvoke;
    }
}


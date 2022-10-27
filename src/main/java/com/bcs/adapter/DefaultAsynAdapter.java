package com.bcs.adapter;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 * DefaultAsynAdapter
 *
 * @author shenbangchen
 * @date 2022/10/25 16:01
 */
public class DefaultAsynAdapter extends AbstractAsynAdapter {

    @Override
    public void asynTask(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier, AsynMethodInvoke asynMethodInvoke) {
        try {
            asynMethodInvoke.invoke();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (cyclicBarrier != null) {
                try {
                    cyclicBarrier.await(10, TimeUnit.SECONDS);
                } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}

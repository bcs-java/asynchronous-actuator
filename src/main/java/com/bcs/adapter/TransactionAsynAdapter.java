package com.bcs.adapter;

import com.bcs.bean.ApplicationContextHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.net.URL;
import java.util.concurrent.*;

/**
 * Asynchronous methods are associated with Transaction
 *
 * @author shenbangchen
 * @date 2022/10/25 14:48
 */
public class TransactionAsynAdapter extends AbstractAsynAdapter {

    @Override
    public void asynTask(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier, AsynMethodInvoke asynMethodInvoke) {
        DataSourceTransactionManager dataSourceTransactionManager = null;
        TransactionStatus transaction = null;
        try {
            dataSourceTransactionManager = ApplicationContextHolder.getBean(DataSourceTransactionManager.class);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            transaction = dataSourceTransactionManager.getTransaction(def);
            asynMethodInvoke.invoke();
        } catch (Exception ignored) {
            this.alterCallBackEnableState();
        } finally {
            if (cyclicBarrier != null) {
                try {
                    cyclicBarrier.await(10, TimeUnit.SECONDS);
                    if (this.getCallBackEnableState()) {
                        if (dataSourceTransactionManager != null && transaction != null)
                            dataSourceTransactionManager.rollback(transaction);
                    } else {
                        if (dataSourceTransactionManager != null && transaction != null)
                            dataSourceTransactionManager.commit(transaction);
                    }
                } catch (InterruptedException | TimeoutException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }

        }
    }
}

package com.bcs.asynconst;

import com.bcs.adapter.AbstractAsynAdapter;
import com.bcs.adapter.DefaultAsynAdapter;
import com.bcs.adapter.TransactionAsynAdapter;

/**
 * Adapter Define
 *
 * @author shenbangchen
 * @date 2022/10/25 21:07
 */
@SuppressWarnings("ALL")
public enum AdapterDefine {
    DEFAULT_ASYN("DefaultAsynAdapter", new DefaultAsynAdapter()),
    TRANSACTION_ASYN("TransactionAsynAdapter", new TransactionAsynAdapter());

    private final String name;
    private AbstractAsynAdapter adapter;

    private AdapterDefine(String name, AbstractAsynAdapter adapter) {
        this.name = name;
        this.adapter = adapter;
    }

    public AbstractAsynAdapter getAdapter() {
        return this.adapter;
    }

}

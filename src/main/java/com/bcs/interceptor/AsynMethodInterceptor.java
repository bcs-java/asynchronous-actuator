package com.bcs.interceptor;

import com.bcs.adapter.AbstractAsynAdapter;
import com.bcs.adapter.AsynMethodInvoke;
import com.bcs.annotation.AsynOperation;
import com.bcs.asynconst.AdapterDefine;
import com.bcs.bean.ApplicationContextHolder;
import com.bcs.executor.AbstractAdapterExecutor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


/**
 * AsynMethodInterceptor
 *
 * @author shenbangchen
 * @date 2022/10/25 16:41
 */
public class AsynMethodInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!AbstractAdapterExecutor.initializeEnable()) {
            return invocation.proceed();
        }
        AsynOperation asyn = invocation.getMethod().getAnnotation(AsynOperation.class);
        if (asyn == null) {
            return invocation.proceed();
        }
        try {
            AdapterDefine adapter = asyn.adapter();
            AbstractAsynAdapter buildAdapter = adapter.getAdapter();
            Method method = invocation.getMethod();
            Parameter[] parameters = method.getParameters();
            Object bean = ApplicationContextHolder.getBean(method.getDeclaringClass());
            AsynMethodInvoke asynMethodInvoke = new AsynMethodInvoke();
            asynMethodInvoke.setBean(bean);
            asynMethodInvoke.setMethod(method);
            asynMethodInvoke.setParam(parameters);
            buildAdapter.setAsynMethodInvoke(asynMethodInvoke);
            AbstractAdapterExecutor.add(buildAdapter);
            return null;
        } catch (Exception e) {
            return invocation.proceed();
        }
    }


}

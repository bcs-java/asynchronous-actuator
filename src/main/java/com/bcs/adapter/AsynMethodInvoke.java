package com.bcs.adapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Method execution wrapper instead of method execution.
 */
@SuppressWarnings("ALL")
public class AsynMethodInvoke {

    /**
     * Perform method
     */
    private Method method;

    /**
     * Perform object
     */
    private Object bean;

    /**
     * Perform parameter
     */
    private Parameter[] param;

    /**
     * Perform retrun
     */
    private Object result;

    /**
     * AsynMethodInvoke constructor
     *
     * @param method
     * @param bean
     * @param param
     * @return
     * @author shenbangchen
     * @date 2022/10/25 10:09
     */
    public AsynMethodInvoke(Method method, Object bean, Parameter[] param) {
        this.method = method;
        this.bean = bean;
        this.param = param;
    }

    /**
     * AsynMethodInvoke constructor
     *
     * @return
     * @author shenbangchen
     * @date 2022/10/25 16:24
     */
    public AsynMethodInvoke() {

    }

    /**
     * The agent to perform
     *
     * @return {@link T }
     * @author shenbangchen
     * @date 2022/10/25 10:10
     */
    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        this.result = this.method.invoke(bean, param);
        return this.result;
    }

    public Boolean comparaMethodName(String methodName) {
        return method.getName().equalsIgnoreCase(methodName);
    }

    public Object getReturn() {
        return result;
    }

    public AsynMethodInvoke setMethod(Method method) {
        this.method = method;
        return this;
    }

    public AsynMethodInvoke setBean(Object bean) {
        this.bean = bean;
        return this;
    }

    public AsynMethodInvoke setParam(Parameter[] param) {
        this.param = param;
        return this;
    }
}

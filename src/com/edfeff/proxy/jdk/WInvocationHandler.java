package com.edfeff.proxy.jdk;

import java.lang.reflect.Method;

/**
 * 代理类生成接口，所有的动态代理必须实现此接口
 *
 * @author wangpp
 */
public interface WInvocationHandler {
    /**
     * 代理处理接口
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}

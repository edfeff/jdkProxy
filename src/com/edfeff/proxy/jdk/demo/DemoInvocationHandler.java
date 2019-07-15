package com.edfeff.proxy.jdk.demo;

import com.edfeff.proxy.jdk.WClassLoader;
import com.edfeff.proxy.jdk.WInvocationHandler;
import com.edfeff.proxy.jdk.WProxy;

import java.lang.reflect.Method;

/**
 * @author wangpp
 */
public class DemoInvocationHandler implements WInvocationHandler {

    private Object target;

    public DemoInvocationHandler() {
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxy(Object target) {
        this.target = target;
        Object o = WProxy.newProxyInstance(new WClassLoader(), target.getClass().getInterfaces(), this);
        return o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy before");
        Object result = method.invoke(target, args);
        System.out.println("proxy after");
        return result;
    }
}

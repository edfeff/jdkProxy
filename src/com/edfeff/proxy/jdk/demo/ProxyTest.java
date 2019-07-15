package com.edfeff.proxy.jdk.demo;

/**
 * 用法和JDK的一模一样
 *
 * @author wangpp
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {

        IA proxy = (IA) new DemoInvocationHandler().getProxy(new Demo());
        IA proxy2 = (IA) new DemoInvocationHandler().getProxy(new Demo());

        proxy.m2("x", "y");
        System.out.println();
        proxy2.m2("1", "2");

//        proxy before
//        Demo m2 xy
//        proxy after
    }
}

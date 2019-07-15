package com.edfeff.proxy.jdk.demo;

import com.edfeff.proxy.jdk.WInvocationHandler;

import java.lang.reflect.Method;

/**
 * 生成的代理类的基本类似
 *
 * @author wangpp
 */
public class ProxyTemplate implements IA {

    static {
        Method[] methods = IA.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i]);
        }
    }

    private WInvocationHandler h;

    public ProxyTemplate(WInvocationHandler h) {
        this.h = h;
    }

    @Override
    public String m1(String a, String b) {
        String result = null;
        try {
            result = (String) h.invoke(this, IA.class.getMethod("m1", String.class, String.class), new Object[]{a, b});
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void m2(String a, String b) throws Exception {
        try {
            h.invoke(this, this.getClass().getMethod("m2"), new Object[]{a, b});

        } catch (Exception e) {
            throw e;
        } catch (Throwable e) {
            e.printStackTrace();

        }
    }
}


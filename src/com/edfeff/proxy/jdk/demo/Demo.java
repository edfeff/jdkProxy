package com.edfeff.proxy.jdk.demo;

/**
 * @author wangpp
 */
public class Demo implements IA {
    @Override
    public String m1(String a, String b) {
        System.out.println("Demo m1 " + a + b);
        return a + b;
    }

    @Override
    public void m2(String a, String b) throws Exception {
        System.out.println("Demo m2 " + a + b);
    }
}

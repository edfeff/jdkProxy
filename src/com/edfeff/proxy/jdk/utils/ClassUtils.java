package com.edfeff.proxy.jdk.utils;

import com.edfeff.proxy.jdk.support.KeyWord;

/**
 * @author wangpp
 */
public class ClassUtils {
    public static String joinClassName(Class<?>[] classes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < classes.length; i++) {
            Class<?> clazz = classes[i];
            s.append(clazz.getName() + ",");
        }
        String substring = s.substring(0, s.length() - 1);
        return substring;
    }

    public static String getClassSignature(Class<?>[] interfaces, String className) {
        StringBuilder s = new StringBuilder();
        s.append("public class ").append(className).append(" implements ");
        for (int i = 0; i < interfaces.length; i++) {
            Class<?> clazz = interfaces[i];
            s.append(clazz.getName() + ",");
        }
        String substring = s.substring(0, s.length() - 1);
        return substring;
    }

    public static String getDefaultFiledAndConstructor(String className) {
        StringBuilder s = new StringBuilder();
        s.append("private WInvocationHandler h;").append(KeyWord.LN);
        s.append("public ").append(className)
                .append(" (WInvocationHandler h){this.h=h;}");
        return s.toString();
    }
}

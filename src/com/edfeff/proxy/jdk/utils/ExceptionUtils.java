package com.edfeff.proxy.jdk.utils;

import com.edfeff.proxy.jdk.demo.IB;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author wangpp
 */
public class ExceptionUtils {
    public static int getRank(Class<?> e) {
        int rank = 0;
        Class t = e;
        while (!t.equals(Object.class)) {
            rank++;
            t = t.getSuperclass();
        }
        return rank;
    }

    public static List<Class> sortExceptionOfMethod(Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes == null || exceptionTypes.length < 1) {
            return null;
        }

        List<Class> list = new ArrayList<>();
        for (Class c : exceptionTypes) {
            list.add(c);
        }
        list.sort(new ClassRankComparator());
        return list;
    }

    //异常比较类
    static class ClassRankComparator implements Comparator<Class> {
        @Override
        public int compare(Class o1, Class o2) {
            return getRank(o2) - getRank(o1);
        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
//        System.out.println(getRank(Object.class)); // 0
//        System.out.println(getRank(Throwable.class)); //1
//        System.out.println(getRank(Exception.class));//2
//        System.out.println(getRank(IOException.class));//3
//        System.out.println(getRank(RuntimeException.class));//3
//        System.out.println(getRank(IndexOutOfBoundsException.class));//4

        System.out.println(sortExceptionOfMethod(IB.class.getMethod("m4")));

    }

}
